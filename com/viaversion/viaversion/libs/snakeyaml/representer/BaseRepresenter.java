package com.viaversion.viaversion.libs.snakeyaml.representer;

import com.viaversion.viaversion.libs.snakeyaml.DumperOptions;
import com.viaversion.viaversion.libs.snakeyaml.error.Mark;
import com.viaversion.viaversion.libs.snakeyaml.introspector.PropertyUtils;
import com.viaversion.viaversion.libs.snakeyaml.nodes.AnchorNode;
import com.viaversion.viaversion.libs.snakeyaml.nodes.MappingNode;
import com.viaversion.viaversion.libs.snakeyaml.nodes.Node;
import com.viaversion.viaversion.libs.snakeyaml.nodes.NodeTuple;
import com.viaversion.viaversion.libs.snakeyaml.nodes.ScalarNode;
import com.viaversion.viaversion.libs.snakeyaml.nodes.SequenceNode;
import com.viaversion.viaversion.libs.snakeyaml.nodes.Tag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseRepresenter {
   protected final Map representers = new HashMap();
   protected Represent nullRepresenter;
   protected final Map multiRepresenters = new LinkedHashMap();
   protected DumperOptions.ScalarStyle defaultScalarStyle;
   protected DumperOptions.FlowStyle defaultFlowStyle;
   protected final Map representedObjects;
   protected Object objectToRepresent;
   private PropertyUtils propertyUtils;
   private boolean explicitPropertyUtils;

   public BaseRepresenter() {
      this.defaultScalarStyle = DumperOptions.ScalarStyle.PLAIN;
      this.defaultFlowStyle = DumperOptions.FlowStyle.AUTO;
      this.representedObjects = new IdentityHashMap() {
         private static final long serialVersionUID = -5576159264232131854L;

         public Node put(Object key, Node value) {
            return (Node)super.put(key, new AnchorNode(value));
         }
      };
      this.explicitPropertyUtils = false;
   }

   public Node represent(Object data) {
      Node node = this.representData(data);
      this.representedObjects.clear();
      this.objectToRepresent = null;
      return node;
   }

   protected final Node representData(Object data) {
      this.objectToRepresent = data;
      if (this.representedObjects.containsKey(this.objectToRepresent)) {
         Node node = (Node)this.representedObjects.get(this.objectToRepresent);
         return node;
      } else if (data == null) {
         Node node = this.nullRepresenter.representData((Object)null);
         return node;
      } else {
         Class<?> clazz = data.getClass();
         Node node;
         if (this.representers.containsKey(clazz)) {
            Represent representer = (Represent)this.representers.get(clazz);
            node = representer.representData(data);
         } else {
            for(Class repr : this.multiRepresenters.keySet()) {
               if (repr != null && repr.isInstance(data)) {
                  Represent representer = (Represent)this.multiRepresenters.get(repr);
                  node = representer.representData(data);
                  return node;
               }
            }

            if (this.multiRepresenters.containsKey((Object)null)) {
               Represent representer = (Represent)this.multiRepresenters.get((Object)null);
               node = representer.representData(data);
            } else {
               Represent representer = (Represent)this.representers.get((Object)null);
               node = representer.representData(data);
            }
         }

         return node;
      }
   }

   protected Node representScalar(Tag tag, String value, DumperOptions.ScalarStyle style) {
      if (style == null) {
         style = this.defaultScalarStyle;
      }

      Node node = new ScalarNode(tag, value, (Mark)null, (Mark)null, style);
      return node;
   }

   protected Node representScalar(Tag tag, String value) {
      return this.representScalar(tag, value, this.defaultScalarStyle);
   }

   protected Node representSequence(Tag tag, Iterable sequence, DumperOptions.FlowStyle flowStyle) {
      int size = 10;
      if (sequence instanceof List) {
         size = ((List)sequence).size();
      }

      List<Node> value = new ArrayList(size);
      SequenceNode node = new SequenceNode(tag, value, flowStyle);
      this.representedObjects.put(this.objectToRepresent, node);
      DumperOptions.FlowStyle bestStyle = DumperOptions.FlowStyle.FLOW;

      for(Object item : sequence) {
         Node nodeItem = this.representData(item);
         if (!(nodeItem instanceof ScalarNode) || !((ScalarNode)nodeItem).isPlain()) {
            bestStyle = DumperOptions.FlowStyle.BLOCK;
         }

         value.add(nodeItem);
      }

      if (flowStyle == DumperOptions.FlowStyle.AUTO) {
         if (this.defaultFlowStyle != DumperOptions.FlowStyle.AUTO) {
            node.setFlowStyle(this.defaultFlowStyle);
         } else {
            node.setFlowStyle(bestStyle);
         }
      }

      return node;
   }

   protected Node representMapping(Tag tag, Map mapping, DumperOptions.FlowStyle flowStyle) {
      List<NodeTuple> value = new ArrayList(mapping.size());
      MappingNode node = new MappingNode(tag, value, flowStyle);
      this.representedObjects.put(this.objectToRepresent, node);
      DumperOptions.FlowStyle bestStyle = DumperOptions.FlowStyle.FLOW;

      for(Map.Entry entry : mapping.entrySet()) {
         Node nodeKey = this.representData(entry.getKey());
         Node nodeValue = this.representData(entry.getValue());
         if (!(nodeKey instanceof ScalarNode) || !((ScalarNode)nodeKey).isPlain()) {
            bestStyle = DumperOptions.FlowStyle.BLOCK;
         }

         if (!(nodeValue instanceof ScalarNode) || !((ScalarNode)nodeValue).isPlain()) {
            bestStyle = DumperOptions.FlowStyle.BLOCK;
         }

         value.add(new NodeTuple(nodeKey, nodeValue));
      }

      if (flowStyle == DumperOptions.FlowStyle.AUTO) {
         if (this.defaultFlowStyle != DumperOptions.FlowStyle.AUTO) {
            node.setFlowStyle(this.defaultFlowStyle);
         } else {
            node.setFlowStyle(bestStyle);
         }
      }

      return node;
   }

   public void setDefaultScalarStyle(DumperOptions.ScalarStyle defaultStyle) {
      this.defaultScalarStyle = defaultStyle;
   }

   public DumperOptions.ScalarStyle getDefaultScalarStyle() {
      return this.defaultScalarStyle == null ? DumperOptions.ScalarStyle.PLAIN : this.defaultScalarStyle;
   }

   public void setDefaultFlowStyle(DumperOptions.FlowStyle defaultFlowStyle) {
      this.defaultFlowStyle = defaultFlowStyle;
   }

   public DumperOptions.FlowStyle getDefaultFlowStyle() {
      return this.defaultFlowStyle;
   }

   public void setPropertyUtils(PropertyUtils propertyUtils) {
      this.propertyUtils = propertyUtils;
      this.explicitPropertyUtils = true;
   }

   public final PropertyUtils getPropertyUtils() {
      if (this.propertyUtils == null) {
         this.propertyUtils = new PropertyUtils();
      }

      return this.propertyUtils;
   }

   public final boolean isExplicitPropertyUtils() {
      return this.explicitPropertyUtils;
   }
}
