package com.viaversion.viaversion.libs.snakeyaml.constructor;

import com.viaversion.viaversion.libs.snakeyaml.LoaderOptions;
import com.viaversion.viaversion.libs.snakeyaml.error.Mark;
import com.viaversion.viaversion.libs.snakeyaml.error.YAMLException;
import com.viaversion.viaversion.libs.snakeyaml.external.biz.base64Coder.Base64Coder;
import com.viaversion.viaversion.libs.snakeyaml.nodes.MappingNode;
import com.viaversion.viaversion.libs.snakeyaml.nodes.Node;
import com.viaversion.viaversion.libs.snakeyaml.nodes.NodeId;
import com.viaversion.viaversion.libs.snakeyaml.nodes.NodeTuple;
import com.viaversion.viaversion.libs.snakeyaml.nodes.ScalarNode;
import com.viaversion.viaversion.libs.snakeyaml.nodes.SequenceNode;
import com.viaversion.viaversion.libs.snakeyaml.nodes.Tag;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SafeConstructor extends BaseConstructor {
   public static final ConstructUndefined undefinedConstructor = new ConstructUndefined();
   private static final Map BOOL_VALUES = new HashMap();
   private static final int[][] RADIX_MAX;
   private static final Pattern TIMESTAMP_REGEXP;
   private static final Pattern YMD_REGEXP;

   public SafeConstructor(LoaderOptions loaderOptions) {
      super(loaderOptions);
      this.yamlConstructors.put(Tag.NULL, new ConstructYamlNull());
      this.yamlConstructors.put(Tag.BOOL, new ConstructYamlBool());
      this.yamlConstructors.put(Tag.INT, new ConstructYamlInt());
      this.yamlConstructors.put(Tag.FLOAT, new ConstructYamlFloat());
      this.yamlConstructors.put(Tag.BINARY, new ConstructYamlBinary());
      this.yamlConstructors.put(Tag.TIMESTAMP, new ConstructYamlTimestamp());
      this.yamlConstructors.put(Tag.OMAP, new ConstructYamlOmap());
      this.yamlConstructors.put(Tag.PAIRS, new ConstructYamlPairs());
      this.yamlConstructors.put(Tag.SET, new ConstructYamlSet());
      this.yamlConstructors.put(Tag.STR, new ConstructYamlStr());
      this.yamlConstructors.put(Tag.SEQ, new ConstructYamlSeq());
      this.yamlConstructors.put(Tag.MAP, new ConstructYamlMap());
      this.yamlConstructors.put((Object)null, undefinedConstructor);
      this.yamlClassConstructors.put(NodeId.scalar, undefinedConstructor);
      this.yamlClassConstructors.put(NodeId.sequence, undefinedConstructor);
      this.yamlClassConstructors.put(NodeId.mapping, undefinedConstructor);
   }

   protected void flattenMapping(MappingNode node) {
      this.flattenMapping(node, false);
   }

   protected void flattenMapping(MappingNode node, boolean forceStringKeys) {
      this.processDuplicateKeys(node, forceStringKeys);
      if (node.isMerged()) {
         node.setValue(this.mergeNode(node, true, new HashMap(), new ArrayList(), forceStringKeys));
      }

   }

   protected void processDuplicateKeys(MappingNode node) {
      this.processDuplicateKeys(node, false);
   }

   protected void processDuplicateKeys(MappingNode node, boolean forceStringKeys) {
      List<NodeTuple> nodeValue = node.getValue();
      Map<Object, Integer> keys = new HashMap(nodeValue.size());
      TreeSet<Integer> toRemove = new TreeSet();
      int i = 0;

      for(NodeTuple tuple : nodeValue) {
         Node keyNode = tuple.getKeyNode();
         if (!keyNode.getTag().equals(Tag.MERGE)) {
            if (forceStringKeys) {
               if (!(keyNode instanceof ScalarNode)) {
                  throw new YAMLException("Keys must be scalars but found: " + keyNode);
               }

               keyNode.setType(String.class);
               keyNode.setTag(Tag.STR);
            }

            Object key = this.constructObject(keyNode);
            if (key != null && !forceStringKeys && keyNode.isTwoStepsConstruction()) {
               if (!this.loadingConfig.getAllowRecursiveKeys()) {
                  throw new YAMLException("Recursive key for mapping is detected but it is not configured to be allowed.");
               }

               try {
                  key.hashCode();
               } catch (Exception e) {
                  throw new ConstructorException("while constructing a mapping", node.getStartMark(), "found unacceptable key " + key, tuple.getKeyNode().getStartMark(), e);
               }
            }

            Integer prevIndex = (Integer)keys.put(key, i);
            if (prevIndex != null) {
               if (!this.isAllowDuplicateKeys()) {
                  throw new DuplicateKeyException(node.getStartMark(), key, tuple.getKeyNode().getStartMark());
               }

               toRemove.add(prevIndex);
            }
         }

         ++i;
      }

      Iterator<Integer> indices2remove = toRemove.descendingIterator();

      while(indices2remove.hasNext()) {
         nodeValue.remove((Integer)indices2remove.next());
      }

   }

   private List mergeNode(MappingNode node, boolean isPreffered, Map key2index, List values, boolean forceStringKeys) {
      Iterator<NodeTuple> iter = node.getValue().iterator();

      while(iter.hasNext()) {
         NodeTuple nodeTuple = (NodeTuple)iter.next();
         Node keyNode = nodeTuple.getKeyNode();
         Node valueNode = nodeTuple.getValueNode();
         if (keyNode.getTag().equals(Tag.MERGE)) {
            iter.remove();
            switch (valueNode.getNodeId()) {
               case mapping:
                  MappingNode mn = (MappingNode)valueNode;
                  this.mergeNode(mn, false, key2index, values, forceStringKeys);
                  break;
               case sequence:
                  SequenceNode sn = (SequenceNode)valueNode;

                  for(Node subnode : sn.getValue()) {
                     if (!(subnode instanceof MappingNode)) {
                        throw new ConstructorException("while constructing a mapping", node.getStartMark(), "expected a mapping for merging, but found " + subnode.getNodeId(), subnode.getStartMark());
                     }

                     MappingNode mnode = (MappingNode)subnode;
                     this.mergeNode(mnode, false, key2index, values, forceStringKeys);
                  }
                  break label1;
               default:
                  throw new ConstructorException("while constructing a mapping", node.getStartMark(), "expected a mapping or list of mappings for merging, but found " + valueNode.getNodeId(), valueNode.getStartMark());
            }
         } else {
            if (forceStringKeys) {
               if (!(keyNode instanceof ScalarNode)) {
                  throw new YAMLException("Keys must be scalars but found: " + keyNode);
               }

               keyNode.setType(String.class);
               keyNode.setTag(Tag.STR);
            }

            Object key = this.constructObject(keyNode);
            if (!key2index.containsKey(key)) {
               values.add(nodeTuple);
               key2index.put(key, values.size() - 1);
            } else if (isPreffered) {
               values.set((Integer)key2index.get(key), nodeTuple);
            }
         }
      }

      return values;
   }

   protected void constructMapping2ndStep(MappingNode node, Map mapping) {
      this.flattenMapping(node);
      super.constructMapping2ndStep(node, mapping);
   }

   protected void constructSet2ndStep(MappingNode node, Set set) {
      this.flattenMapping(node);
      super.constructSet2ndStep(node, set);
   }

   private static int maxLen(int max, int radix) {
      return Integer.toString(max, radix).length();
   }

   private static int maxLen(long max, int radix) {
      return Long.toString(max, radix).length();
   }

   private Number createNumber(int sign, String number, int radix) {
      int len = number != null ? number.length() : 0;
      if (sign < 0) {
         number = "-" + number;
      }

      int[] maxArr = radix < RADIX_MAX.length ? RADIX_MAX[radix] : null;
      if (maxArr != null) {
         boolean gtInt = len > maxArr[0];
         if (gtInt) {
            if (len > maxArr[1]) {
               return new BigInteger(number, radix);
            }

            return createLongOrBigInteger(number, radix);
         }
      }

      Number result;
      try {
         result = Integer.valueOf(number, radix);
      } catch (NumberFormatException var8) {
         result = createLongOrBigInteger(number, radix);
      }

      return result;
   }

   protected static Number createLongOrBigInteger(String number, int radix) {
      try {
         return Long.valueOf(number, radix);
      } catch (NumberFormatException var3) {
         return new BigInteger(number, radix);
      }
   }

   static {
      BOOL_VALUES.put("yes", Boolean.TRUE);
      BOOL_VALUES.put("no", Boolean.FALSE);
      BOOL_VALUES.put("true", Boolean.TRUE);
      BOOL_VALUES.put("false", Boolean.FALSE);
      BOOL_VALUES.put("on", Boolean.TRUE);
      BOOL_VALUES.put("off", Boolean.FALSE);
      RADIX_MAX = new int[17][2];
      int[] radixList = new int[]{2, 8, 10, 16};

      for(int radix : radixList) {
         RADIX_MAX[radix] = new int[]{maxLen(Integer.MAX_VALUE, radix), maxLen(Long.MAX_VALUE, radix)};
      }

      TIMESTAMP_REGEXP = Pattern.compile("^([0-9][0-9][0-9][0-9])-([0-9][0-9]?)-([0-9][0-9]?)(?:(?:[Tt]|[ \t]+)([0-9][0-9]?):([0-9][0-9]):([0-9][0-9])(?:\\.([0-9]*))?(?:[ \t]*(?:Z|([-+][0-9][0-9]?)(?::([0-9][0-9])?)?))?)?$");
      YMD_REGEXP = Pattern.compile("^([0-9][0-9][0-9][0-9])-([0-9][0-9]?)-([0-9][0-9]?)$");
   }

   public class ConstructYamlNull extends AbstractConstruct {
      public Object construct(Node node) {
         if (node != null) {
            SafeConstructor.this.constructScalar((ScalarNode)node);
         }

         return null;
      }
   }

   public class ConstructYamlBool extends AbstractConstruct {
      public Object construct(Node node) {
         String val = SafeConstructor.this.constructScalar((ScalarNode)node);
         return SafeConstructor.BOOL_VALUES.get(val.toLowerCase());
      }
   }

   public class ConstructYamlInt extends AbstractConstruct {
      public Object construct(Node node) {
         String value = SafeConstructor.this.constructScalar((ScalarNode)node).replaceAll("_", "");
         if (value.isEmpty()) {
            throw new ConstructorException("while constructing an int", node.getStartMark(), "found empty value", node.getStartMark());
         } else {
            int sign = 1;
            char first = value.charAt(0);
            if (first == '-') {
               sign = -1;
               value = value.substring(1);
            } else if (first == '+') {
               value = value.substring(1);
            }

            int base = 10;
            if ("0".equals(value)) {
               return 0;
            } else {
               if (value.startsWith("0b")) {
                  value = value.substring(2);
                  base = 2;
               } else if (value.startsWith("0x")) {
                  value = value.substring(2);
                  base = 16;
               } else {
                  if (!value.startsWith("0")) {
                     if (value.indexOf(58) == -1) {
                        return SafeConstructor.this.createNumber(sign, value, 10);
                     }

                     String[] digits = value.split(":");
                     int bes = 1;
                     int val = 0;
                     int i = 0;

                     for(int j = digits.length; i < j; ++i) {
                        val = (int)((long)val + Long.parseLong(digits[j - i - 1]) * (long)bes);
                        bes *= 60;
                     }

                     return SafeConstructor.this.createNumber(sign, String.valueOf(val), 10);
                  }

                  value = value.substring(1);
                  base = 8;
               }

               return SafeConstructor.this.createNumber(sign, value, base);
            }
         }
      }
   }

   public class ConstructYamlFloat extends AbstractConstruct {
      public Object construct(Node node) {
         String value = SafeConstructor.this.constructScalar((ScalarNode)node).replaceAll("_", "");
         if (value.isEmpty()) {
            throw new ConstructorException("while constructing a float", node.getStartMark(), "found empty value", node.getStartMark());
         } else {
            int sign = 1;
            char first = value.charAt(0);
            if (first == '-') {
               sign = -1;
               value = value.substring(1);
            } else if (first == '+') {
               value = value.substring(1);
            }

            String valLower = value.toLowerCase();
            if (".inf".equals(valLower)) {
               return sign == -1 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
            } else if (".nan".equals(valLower)) {
               return Double.NaN;
            } else if (value.indexOf(58) == -1) {
               Double d = Double.valueOf(value);
               return d * (double)sign;
            } else {
               String[] digits = value.split(":");
               int bes = 1;
               double val = (double)0.0F;
               int i = 0;

               for(int j = digits.length; i < j; ++i) {
                  val += Double.parseDouble(digits[j - i - 1]) * (double)bes;
                  bes *= 60;
               }

               return (double)sign * val;
            }
         }
      }
   }

   public class ConstructYamlBinary extends AbstractConstruct {
      public Object construct(Node node) {
         String noWhiteSpaces = SafeConstructor.this.constructScalar((ScalarNode)node).replaceAll("\\s", "");
         byte[] decoded = Base64Coder.decode(noWhiteSpaces.toCharArray());
         return decoded;
      }
   }

   public static class ConstructYamlTimestamp extends AbstractConstruct {
      private Calendar calendar;

      public Calendar getCalendar() {
         return this.calendar;
      }

      public Object construct(Node node) {
         ScalarNode scalar = (ScalarNode)node;
         String nodeValue = scalar.getValue();
         Matcher match = SafeConstructor.YMD_REGEXP.matcher(nodeValue);
         if (match.matches()) {
            String year_s = match.group(1);
            String month_s = match.group(2);
            String day_s = match.group(3);
            this.calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            this.calendar.clear();
            this.calendar.set(1, Integer.parseInt(year_s));
            this.calendar.set(2, Integer.parseInt(month_s) - 1);
            this.calendar.set(5, Integer.parseInt(day_s));
            return this.calendar.getTime();
         } else {
            match = SafeConstructor.TIMESTAMP_REGEXP.matcher(nodeValue);
            if (!match.matches()) {
               throw new YAMLException("Unexpected timestamp: " + nodeValue);
            } else {
               String year_s = match.group(1);
               String month_s = match.group(2);
               String day_s = match.group(3);
               String hour_s = match.group(4);
               String min_s = match.group(5);
               String seconds = match.group(6);
               String millis = match.group(7);
               if (millis != null) {
                  seconds = seconds + "." + millis;
               }

               double fractions = Double.parseDouble(seconds);
               int sec_s = (int)Math.round(Math.floor(fractions));
               int usec = (int)Math.round((fractions - (double)sec_s) * (double)1000.0F);
               String timezoneh_s = match.group(8);
               String timezonem_s = match.group(9);
               TimeZone timeZone;
               if (timezoneh_s != null) {
                  String time = timezonem_s != null ? ":" + timezonem_s : "00";
                  timeZone = TimeZone.getTimeZone("GMT" + timezoneh_s + time);
               } else {
                  timeZone = TimeZone.getTimeZone("UTC");
               }

               this.calendar = Calendar.getInstance(timeZone);
               this.calendar.set(1, Integer.parseInt(year_s));
               this.calendar.set(2, Integer.parseInt(month_s) - 1);
               this.calendar.set(5, Integer.parseInt(day_s));
               this.calendar.set(11, Integer.parseInt(hour_s));
               this.calendar.set(12, Integer.parseInt(min_s));
               this.calendar.set(13, sec_s);
               this.calendar.set(14, usec);
               return this.calendar.getTime();
            }
         }
      }
   }

   public class ConstructYamlOmap extends AbstractConstruct {
      public Object construct(Node node) {
         Map<Object, Object> omap = new LinkedHashMap();
         if (!(node instanceof SequenceNode)) {
            throw new ConstructorException("while constructing an ordered map", node.getStartMark(), "expected a sequence, but found " + node.getNodeId(), node.getStartMark());
         } else {
            SequenceNode snode = (SequenceNode)node;

            for(Node subnode : snode.getValue()) {
               if (!(subnode instanceof MappingNode)) {
                  throw new ConstructorException("while constructing an ordered map", node.getStartMark(), "expected a mapping of length 1, but found " + subnode.getNodeId(), subnode.getStartMark());
               }

               MappingNode mnode = (MappingNode)subnode;
               if (mnode.getValue().size() != 1) {
                  throw new ConstructorException("while constructing an ordered map", node.getStartMark(), "expected a single mapping item, but found " + mnode.getValue().size() + " items", mnode.getStartMark());
               }

               Node keyNode = ((NodeTuple)mnode.getValue().get(0)).getKeyNode();
               Node valueNode = ((NodeTuple)mnode.getValue().get(0)).getValueNode();
               Object key = SafeConstructor.this.constructObject(keyNode);
               Object value = SafeConstructor.this.constructObject(valueNode);
               omap.put(key, value);
            }

            return omap;
         }
      }
   }

   public class ConstructYamlPairs extends AbstractConstruct {
      public Object construct(Node node) {
         if (!(node instanceof SequenceNode)) {
            throw new ConstructorException("while constructing pairs", node.getStartMark(), "expected a sequence, but found " + node.getNodeId(), node.getStartMark());
         } else {
            SequenceNode snode = (SequenceNode)node;
            List<Object[]> pairs = new ArrayList(snode.getValue().size());

            for(Node subnode : snode.getValue()) {
               if (!(subnode instanceof MappingNode)) {
                  throw new ConstructorException("while constructingpairs", node.getStartMark(), "expected a mapping of length 1, but found " + subnode.getNodeId(), subnode.getStartMark());
               }

               MappingNode mnode = (MappingNode)subnode;
               if (mnode.getValue().size() != 1) {
                  throw new ConstructorException("while constructing pairs", node.getStartMark(), "expected a single mapping item, but found " + mnode.getValue().size() + " items", mnode.getStartMark());
               }

               Node keyNode = ((NodeTuple)mnode.getValue().get(0)).getKeyNode();
               Node valueNode = ((NodeTuple)mnode.getValue().get(0)).getValueNode();
               Object key = SafeConstructor.this.constructObject(keyNode);
               Object value = SafeConstructor.this.constructObject(valueNode);
               pairs.add(new Object[]{key, value});
            }

            return pairs;
         }
      }
   }

   public class ConstructYamlSet implements Construct {
      public Object construct(Node node) {
         if (node.isTwoStepsConstruction()) {
            return SafeConstructor.this.constructedObjects.containsKey(node) ? SafeConstructor.this.constructedObjects.get(node) : SafeConstructor.this.createDefaultSet(((MappingNode)node).getValue().size());
         } else {
            return SafeConstructor.this.constructSet((MappingNode)node);
         }
      }

      public void construct2ndStep(Node node, Object object) {
         if (node.isTwoStepsConstruction()) {
            SafeConstructor.this.constructSet2ndStep((MappingNode)node, (Set)object);
         } else {
            throw new YAMLException("Unexpected recursive set structure. Node: " + node);
         }
      }
   }

   public class ConstructYamlStr extends AbstractConstruct {
      public Object construct(Node node) {
         return SafeConstructor.this.constructScalar((ScalarNode)node);
      }
   }

   public class ConstructYamlSeq implements Construct {
      public Object construct(Node node) {
         SequenceNode seqNode = (SequenceNode)node;
         return node.isTwoStepsConstruction() ? SafeConstructor.this.newList(seqNode) : SafeConstructor.this.constructSequence(seqNode);
      }

      public void construct2ndStep(Node node, Object data) {
         if (node.isTwoStepsConstruction()) {
            SafeConstructor.this.constructSequenceStep2((SequenceNode)node, (List)data);
         } else {
            throw new YAMLException("Unexpected recursive sequence structure. Node: " + node);
         }
      }
   }

   public class ConstructYamlMap implements Construct {
      public Object construct(Node node) {
         MappingNode mnode = (MappingNode)node;
         return node.isTwoStepsConstruction() ? SafeConstructor.this.createDefaultMap(mnode.getValue().size()) : SafeConstructor.this.constructMapping(mnode);
      }

      public void construct2ndStep(Node node, Object object) {
         if (node.isTwoStepsConstruction()) {
            SafeConstructor.this.constructMapping2ndStep((MappingNode)node, (Map)object);
         } else {
            throw new YAMLException("Unexpected recursive mapping structure. Node: " + node);
         }
      }
   }

   public static final class ConstructUndefined extends AbstractConstruct {
      public Object construct(Node node) {
         throw new ConstructorException((String)null, (Mark)null, "could not determine a constructor for the tag " + node.getTag(), node.getStartMark());
      }
   }
}
