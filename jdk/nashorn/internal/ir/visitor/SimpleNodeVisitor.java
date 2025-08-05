package jdk.nashorn.internal.ir.visitor;

import jdk.nashorn.internal.ir.LexicalContext;

public abstract class SimpleNodeVisitor extends NodeVisitor {
   public SimpleNodeVisitor() {
      super(new LexicalContext());
   }
}
