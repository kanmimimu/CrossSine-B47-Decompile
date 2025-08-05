package jdk.nashorn.internal.ir;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jdk.nashorn.internal.codegen.types.ArrayType;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;
import jdk.nashorn.internal.objects.NativeArray;
import jdk.nashorn.internal.parser.Lexer;
import jdk.nashorn.internal.parser.Token;
import jdk.nashorn.internal.parser.TokenType;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.Undefined;

@Immutable
public abstract class LiteralNode extends Expression implements PropertyKey {
   private static final long serialVersionUID = 1L;
   protected final Object value;
   public static final Object POSTSET_MARKER = new Object();

   protected LiteralNode(long token, int finish, Object value) {
      super(token, finish);
      this.value = value;
   }

   protected LiteralNode(LiteralNode literalNode) {
      this(literalNode, literalNode.value);
   }

   protected LiteralNode(LiteralNode literalNode, Object newValue) {
      super(literalNode);
      this.value = newValue;
   }

   public LiteralNode initialize(LexicalContext lc) {
      return this;
   }

   public boolean isNull() {
      return this.value == null;
   }

   public Type getType() {
      return Type.typeFor(this.value.getClass());
   }

   public String getPropertyName() {
      return JSType.toString(this.getObject());
   }

   public boolean getBoolean() {
      return JSType.toBoolean(this.value);
   }

   public int getInt32() {
      return JSType.toInt32(this.value);
   }

   public long getUint32() {
      return JSType.toUint32(this.value);
   }

   public long getLong() {
      return JSType.toLong(this.value);
   }

   public double getNumber() {
      return JSType.toNumber(this.value);
   }

   public String getString() {
      return JSType.toString(this.value);
   }

   public Object getObject() {
      return this.value;
   }

   public boolean isString() {
      return this.value instanceof String;
   }

   public boolean isNumeric() {
      return this.value instanceof Number;
   }

   public Node accept(NodeVisitor visitor) {
      return (Node)(visitor.enterLiteralNode(this) ? visitor.leaveLiteralNode(this) : this);
   }

   public void toString(StringBuilder sb, boolean printType) {
      if (this.value == null) {
         sb.append("null");
      } else {
         sb.append(this.value.toString());
      }

   }

   public final Object getValue() {
      return this.value;
   }

   private static Expression[] valueToArray(List value) {
      return (Expression[])value.toArray(new Expression[value.size()]);
   }

   public static LiteralNode newInstance(long token, int finish) {
      return new NullLiteralNode(token, finish);
   }

   public static LiteralNode newInstance(Node parent) {
      return new NullLiteralNode(parent.getToken(), parent.getFinish());
   }

   public static LiteralNode newInstance(long token, int finish, boolean value) {
      return new BooleanLiteralNode(token, finish, value);
   }

   public static LiteralNode newInstance(Node parent, boolean value) {
      return new BooleanLiteralNode(parent.getToken(), parent.getFinish(), value);
   }

   public static LiteralNode newInstance(long token, int finish, Number value) {
      assert !(value instanceof Long);

      return new NumberLiteralNode(token, finish, value);
   }

   public static LiteralNode newInstance(Node parent, Number value) {
      return new NumberLiteralNode(parent.getToken(), parent.getFinish(), value);
   }

   public static LiteralNode newInstance(long token, int finish, Undefined value) {
      return new UndefinedLiteralNode(token, finish);
   }

   public static LiteralNode newInstance(Node parent, Undefined value) {
      return new UndefinedLiteralNode(parent.getToken(), parent.getFinish());
   }

   public static LiteralNode newInstance(long token, int finish, String value) {
      return new StringLiteralNode(token, finish, value);
   }

   public static LiteralNode newInstance(Node parent, String value) {
      return new StringLiteralNode(parent.getToken(), parent.getFinish(), value);
   }

   public static LiteralNode newInstance(long token, int finish, Lexer.LexerToken value) {
      return new LexerTokenLiteralNode(token, finish, value);
   }

   public static LiteralNode newInstance(Node parent, Lexer.LexerToken value) {
      return new LexerTokenLiteralNode(parent.getToken(), parent.getFinish(), value);
   }

   public static Object objectAsConstant(Object object) {
      if (object == null) {
         return null;
      } else if (!(object instanceof Number) && !(object instanceof String) && !(object instanceof Boolean)) {
         return object instanceof LiteralNode ? objectAsConstant(((LiteralNode)object).getValue()) : POSTSET_MARKER;
      } else {
         return object;
      }
   }

   public static boolean isConstant(Object object) {
      return objectAsConstant(object) != POSTSET_MARKER;
   }

   public static LiteralNode newInstance(long token, int finish, List value) {
      return new ArrayLiteralNode(token, finish, valueToArray(value));
   }

   public static LiteralNode newInstance(Node parent, List value) {
      return new ArrayLiteralNode(parent.getToken(), parent.getFinish(), valueToArray(value));
   }

   public static LiteralNode newInstance(long token, int finish, Expression[] value) {
      return new ArrayLiteralNode(token, finish, value);
   }

   public static class PrimitiveLiteralNode extends LiteralNode {
      private static final long serialVersionUID = 1L;

      private PrimitiveLiteralNode(long token, int finish, Object value) {
         super(token, finish, value);
      }

      private PrimitiveLiteralNode(PrimitiveLiteralNode literalNode) {
         super(literalNode);
      }

      public boolean isTrue() {
         return JSType.toBoolean(this.value);
      }

      public boolean isLocal() {
         return true;
      }

      public boolean isAlwaysFalse() {
         return !this.isTrue();
      }

      public boolean isAlwaysTrue() {
         return this.isTrue();
      }
   }

   @Immutable
   private static final class BooleanLiteralNode extends PrimitiveLiteralNode {
      private static final long serialVersionUID = 1L;

      private BooleanLiteralNode(long token, int finish, boolean value) {
         super(Token.recast(token, value ? TokenType.TRUE : TokenType.FALSE), finish, value, null);
      }

      private BooleanLiteralNode(BooleanLiteralNode literalNode) {
         super(literalNode, null);
      }

      public boolean isTrue() {
         return (Boolean)this.value;
      }

      public Type getType() {
         return Type.BOOLEAN;
      }

      public Type getWidestOperationType() {
         return Type.BOOLEAN;
      }
   }

   @Immutable
   private static final class NumberLiteralNode extends PrimitiveLiteralNode {
      private static final long serialVersionUID = 1L;
      private final Type type;

      private NumberLiteralNode(long token, int finish, Number value) {
         super(Token.recast(token, TokenType.DECIMAL), finish, value, null);
         this.type = numberGetType((Number)this.value);
      }

      private NumberLiteralNode(NumberLiteralNode literalNode) {
         super(literalNode, null);
         this.type = numberGetType((Number)this.value);
      }

      private static Type numberGetType(Number number) {
         if (number instanceof Integer) {
            return Type.INT;
         } else if (number instanceof Double) {
            return Type.NUMBER;
         } else {
            assert false;

            return null;
         }
      }

      public Type getType() {
         return this.type;
      }

      public Type getWidestOperationType() {
         return this.getType();
      }
   }

   private static class UndefinedLiteralNode extends PrimitiveLiteralNode {
      private static final long serialVersionUID = 1L;

      private UndefinedLiteralNode(long token, int finish) {
         super(Token.recast(token, TokenType.OBJECT), finish, ScriptRuntime.UNDEFINED, null);
      }

      private UndefinedLiteralNode(UndefinedLiteralNode literalNode) {
         super(literalNode, null);
      }
   }

   @Immutable
   private static class StringLiteralNode extends PrimitiveLiteralNode {
      private static final long serialVersionUID = 1L;

      private StringLiteralNode(long token, int finish, String value) {
         super(Token.recast(token, TokenType.STRING), finish, value, null);
      }

      private StringLiteralNode(StringLiteralNode literalNode) {
         super(literalNode, null);
      }

      public void toString(StringBuilder sb, boolean printType) {
         sb.append('"');
         sb.append((String)this.value);
         sb.append('"');
      }
   }

   @Immutable
   private static class LexerTokenLiteralNode extends LiteralNode {
      private static final long serialVersionUID = 1L;

      private LexerTokenLiteralNode(long token, int finish, Lexer.LexerToken value) {
         super(Token.recast(token, TokenType.STRING), finish, value);
      }

      private LexerTokenLiteralNode(LexerTokenLiteralNode literalNode) {
         super(literalNode);
      }

      public Type getType() {
         return Type.OBJECT;
      }

      public void toString(StringBuilder sb, boolean printType) {
         sb.append(((Lexer.LexerToken)this.value).toString());
      }
   }

   private static final class NullLiteralNode extends PrimitiveLiteralNode {
      private static final long serialVersionUID = 1L;

      private NullLiteralNode(long token, int finish) {
         super(Token.recast(token, TokenType.OBJECT), finish, (Object)null, null);
      }

      public Node accept(NodeVisitor visitor) {
         return (Node)(visitor.enterLiteralNode(this) ? visitor.leaveLiteralNode(this) : this);
      }

      public Type getType() {
         return Type.OBJECT;
      }

      public Type getWidestOperationType() {
         return Type.OBJECT;
      }
   }

   @Immutable
   public static final class ArrayLiteralNode extends LiteralNode implements LexicalContextNode, Splittable {
      private static final long serialVersionUID = 1L;
      private final Type elementType;
      private final Object presets;
      private final int[] postsets;
      private final List splitRanges;

      protected ArrayLiteralNode(long token, int finish, Expression[] value) {
         super(Token.recast(token, TokenType.ARRAY), finish, value);
         this.elementType = Type.UNKNOWN;
         this.presets = null;
         this.postsets = null;
         this.splitRanges = null;
      }

      private ArrayLiteralNode(ArrayLiteralNode node, Expression[] value, Type elementType, int[] postsets, Object presets, List splitRanges) {
         super(node, value);
         this.elementType = elementType;
         this.postsets = postsets;
         this.presets = presets;
         this.splitRanges = splitRanges;
      }

      public List getElementExpressions() {
         return Collections.unmodifiableList(Arrays.asList(this.value));
      }

      public ArrayLiteralNode initialize(LexicalContext lc) {
         return (ArrayLiteralNode)Node.replaceInLexicalContext(lc, this, LiteralNode.ArrayLiteralNode.ArrayLiteralInitializer.initialize(this));
      }

      public ArrayType getArrayType() {
         return getArrayType(this.getElementType());
      }

      private static ArrayType getArrayType(Type elementType) {
         if (elementType.isInteger()) {
            return Type.INT_ARRAY;
         } else {
            return elementType.isNumeric() ? Type.NUMBER_ARRAY : Type.OBJECT_ARRAY;
         }
      }

      public Type getType() {
         return Type.typeFor(NativeArray.class);
      }

      public Type getElementType() {
         assert !this.elementType.isUnknown() : this + " has elementType=unknown";

         return this.elementType;
      }

      public int[] getPostsets() {
         assert this.postsets != null : this + " elementType=" + this.elementType + " has no postsets";

         return this.postsets;
      }

      private boolean presetsMatchElementType() {
         if (this.elementType == Type.INT) {
            return this.presets instanceof int[];
         } else {
            return this.elementType == Type.NUMBER ? this.presets instanceof double[] : this.presets instanceof Object[];
         }
      }

      public Object getPresets() {
         assert this.presets != null && this.presetsMatchElementType() : this + " doesn't have presets, or invalid preset type: " + this.presets;

         return this.presets;
      }

      public List getSplitRanges() {
         return this.splitRanges == null ? null : Collections.unmodifiableList(this.splitRanges);
      }

      public ArrayLiteralNode setSplitRanges(LexicalContext lc, List splitRanges) {
         return this.splitRanges == splitRanges ? this : (ArrayLiteralNode)Node.replaceInLexicalContext(lc, this, new ArrayLiteralNode(this, (Expression[])this.value, this.elementType, this.postsets, this.presets, splitRanges));
      }

      public Node accept(NodeVisitor visitor) {
         return LexicalContextNode.Acceptor.accept(this, visitor);
      }

      public Node accept(LexicalContext lc, NodeVisitor visitor) {
         if (visitor.enterLiteralNode(this)) {
            List<Expression> oldValue = Arrays.asList(this.value);
            List<Expression> newValue = Node.accept(visitor, oldValue);
            return visitor.leaveLiteralNode(oldValue != newValue ? this.setValue(lc, newValue) : this);
         } else {
            return this;
         }
      }

      private ArrayLiteralNode setValue(LexicalContext lc, Expression[] value) {
         return this.value == value ? this : (ArrayLiteralNode)Node.replaceInLexicalContext(lc, this, new ArrayLiteralNode(this, value, this.elementType, this.postsets, this.presets, this.splitRanges));
      }

      private ArrayLiteralNode setValue(LexicalContext lc, List value) {
         return this.setValue(lc, (Expression[])value.toArray(new Expression[value.size()]));
      }

      public void toString(StringBuilder sb, boolean printType) {
         sb.append('[');
         boolean first = true;

         for(Node node : (Expression[])this.value) {
            if (!first) {
               sb.append(',');
               sb.append(' ');
            }

            if (node == null) {
               sb.append("undefined");
            } else {
               node.toString(sb, printType);
            }

            first = false;
         }

         sb.append(']');
      }

      private static final class ArrayLiteralInitializer {
         static ArrayLiteralNode initialize(ArrayLiteralNode node) {
            Type elementType = computeElementType((Expression[])node.value);
            int[] postsets = computePostsets((Expression[])node.value);
            Object presets = computePresets((Expression[])node.value, elementType, postsets);
            return new ArrayLiteralNode(node, (Expression[])node.value, elementType, postsets, presets, node.splitRanges);
         }

         private static Type computeElementType(Expression[] value) {
            Type widestElementType = Type.INT;

            for(Expression elem : value) {
               if (elem == null) {
                  widestElementType = widestElementType.widest(Type.OBJECT);
                  break;
               }

               Type type = elem.getType().isUnknown() ? Type.OBJECT : elem.getType();
               if (type.isBoolean()) {
                  widestElementType = widestElementType.widest(Type.OBJECT);
                  break;
               }

               widestElementType = widestElementType.widest(type);
               if (widestElementType.isObject()) {
                  break;
               }
            }

            return widestElementType;
         }

         private static int[] computePostsets(Expression[] value) {
            int[] computed = new int[value.length];
            int nComputed = 0;

            for(int i = 0; i < value.length; ++i) {
               Expression element = value[i];
               if (element == null || !LiteralNode.isConstant(element)) {
                  computed[nComputed++] = i;
               }
            }

            return Arrays.copyOf(computed, nComputed);
         }

         private static boolean setArrayElement(int[] array, int i, Object n) {
            if (n instanceof Number) {
               array[i] = ((Number)n).intValue();
               return true;
            } else {
               return false;
            }
         }

         private static boolean setArrayElement(long[] array, int i, Object n) {
            if (n instanceof Number) {
               array[i] = ((Number)n).longValue();
               return true;
            } else {
               return false;
            }
         }

         private static boolean setArrayElement(double[] array, int i, Object n) {
            if (n instanceof Number) {
               array[i] = ((Number)n).doubleValue();
               return true;
            } else {
               return false;
            }
         }

         private static int[] presetIntArray(Expression[] value, int[] postsets) {
            int[] array = new int[value.length];
            int nComputed = 0;

            for(int i = 0; i < value.length; ++i) {
               assert setArrayElement(array, i, LiteralNode.objectAsConstant(value[i])) || postsets[nComputed++] == i;
            }

            assert postsets.length == nComputed;

            return array;
         }

         private static long[] presetLongArray(Expression[] value, int[] postsets) {
            long[] array = new long[value.length];
            int nComputed = 0;

            for(int i = 0; i < value.length; ++i) {
               assert setArrayElement(array, i, LiteralNode.objectAsConstant(value[i])) || postsets[nComputed++] == i;
            }

            assert postsets.length == nComputed;

            return array;
         }

         private static double[] presetDoubleArray(Expression[] value, int[] postsets) {
            double[] array = new double[value.length];
            int nComputed = 0;

            for(int i = 0; i < value.length; ++i) {
               assert setArrayElement(array, i, LiteralNode.objectAsConstant(value[i])) || postsets[nComputed++] == i;
            }

            assert postsets.length == nComputed;

            return array;
         }

         private static Object[] presetObjectArray(Expression[] value, int[] postsets) {
            Object[] array = new Object[value.length];
            int nComputed = 0;

            for(int i = 0; i < value.length; ++i) {
               Node node = value[i];
               if (node == null) {
                  assert postsets[nComputed++] == i;
               } else {
                  Object element = LiteralNode.objectAsConstant(node);
                  if (element != LiteralNode.POSTSET_MARKER) {
                     array[i] = element;
                  } else {
                     assert postsets[nComputed++] == i;
                  }
               }
            }

            assert postsets.length == nComputed;

            return array;
         }

         static Object computePresets(Expression[] value, Type elementType, int[] postsets) {
            assert !elementType.isUnknown();

            if (elementType.isInteger()) {
               return presetIntArray(value, postsets);
            } else {
               return elementType.isNumeric() ? presetDoubleArray(value, postsets) : presetObjectArray(value, postsets);
            }
         }
      }
   }
}
