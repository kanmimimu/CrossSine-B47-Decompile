package jdk.nashorn.internal.ir;

import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;

@Immutable
public final class ObjectNode extends Expression implements LexicalContextNode, Splittable {
   private static final long serialVersionUID = 1L;
   private final List elements;
   private final List splitRanges;

   public ObjectNode(long token, int finish, List elements) {
      super(token, finish);
      this.elements = elements;
      this.splitRanges = null;

      assert elements instanceof RandomAccess : "Splitting requires random access lists";

   }

   private ObjectNode(ObjectNode objectNode, List elements, List splitRanges) {
      super(objectNode);
      this.elements = elements;
      this.splitRanges = splitRanges;
   }

   public Node accept(NodeVisitor visitor) {
      return LexicalContextNode.Acceptor.accept(this, visitor);
   }

   public Node accept(LexicalContext lc, NodeVisitor visitor) {
      return (Node)(visitor.enterObjectNode(this) ? visitor.leaveObjectNode(this.setElements(lc, Node.accept(visitor, this.elements))) : this);
   }

   public Type getType() {
      return Type.OBJECT;
   }

   public void toString(StringBuilder sb, boolean printType) {
      sb.append('{');
      if (!this.elements.isEmpty()) {
         sb.append(' ');
         boolean first = true;

         for(Node element : this.elements) {
            if (!first) {
               sb.append(", ");
            }

            first = false;
            element.toString(sb, printType);
         }

         sb.append(' ');
      }

      sb.append('}');
   }

   public List getElements() {
      return Collections.unmodifiableList(this.elements);
   }

   private ObjectNode setElements(LexicalContext lc, List elements) {
      return this.elements == elements ? this : (ObjectNode)Node.replaceInLexicalContext(lc, this, new ObjectNode(this, elements, this.splitRanges));
   }

   public ObjectNode setSplitRanges(LexicalContext lc, List splitRanges) {
      return this.splitRanges == splitRanges ? this : (ObjectNode)Node.replaceInLexicalContext(lc, this, new ObjectNode(this, this.elements, splitRanges));
   }

   public List getSplitRanges() {
      return this.splitRanges == null ? null : Collections.unmodifiableList(this.splitRanges);
   }
}
