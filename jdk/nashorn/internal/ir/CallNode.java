package jdk.nashorn.internal.ir;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.ir.annotations.Ignore;
import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;

@Immutable
public final class CallNode extends LexicalContextExpression implements Optimistic {
   private static final long serialVersionUID = 1L;
   private final Expression function;
   private final List args;
   private static final int IS_NEW = 1;
   private static final int IS_APPLY_TO_CALL = 2;
   private final int flags;
   private final int lineNumber;
   private final int programPoint;
   private final Type optimisticType;
   @Ignore
   private final EvalArgs evalArgs;

   public CallNode(int lineNumber, long token, int finish, Expression function, List args, boolean isNew) {
      super(token, finish);
      this.function = function;
      this.args = args;
      this.flags = isNew ? 1 : 0;
      this.evalArgs = null;
      this.lineNumber = lineNumber;
      this.programPoint = -1;
      this.optimisticType = null;
   }

   private CallNode(CallNode callNode, Expression function, List args, int flags, Type optimisticType, EvalArgs evalArgs, int programPoint) {
      super(callNode);
      this.lineNumber = callNode.lineNumber;
      this.function = function;
      this.args = args;
      this.flags = flags;
      this.evalArgs = evalArgs;
      this.programPoint = programPoint;
      this.optimisticType = optimisticType;
   }

   public int getLineNumber() {
      return this.lineNumber;
   }

   public Type getType() {
      return this.optimisticType == null ? Type.OBJECT : this.optimisticType;
   }

   public Optimistic setType(Type optimisticType) {
      return this.optimisticType == optimisticType ? this : new CallNode(this, this.function, this.args, this.flags, optimisticType, this.evalArgs, this.programPoint);
   }

   public Node accept(LexicalContext lc, NodeVisitor visitor) {
      if (visitor.enterCallNode(this)) {
         CallNode newCallNode = (CallNode)visitor.leaveCallNode(this.setFunction((Expression)this.function.accept(visitor)).setArgs(Node.accept(visitor, this.args)).setEvalArgs(this.evalArgs == null ? null : this.evalArgs.setArgs(Node.accept(visitor, this.evalArgs.getArgs()))));
         if (this != newCallNode) {
            return (Node)Node.replaceInLexicalContext(lc, this, newCallNode);
         }
      }

      return this;
   }

   public void toString(StringBuilder sb, boolean printType) {
      if (printType) {
         this.optimisticTypeToString(sb);
      }

      StringBuilder fsb = new StringBuilder();
      this.function.toString(fsb, printType);
      if (this.isApplyToCall()) {
         sb.append(fsb.toString().replace("apply", "[apply => call]"));
      } else {
         sb.append(fsb);
      }

      sb.append('(');
      boolean first = true;

      for(Node arg : this.args) {
         if (!first) {
            sb.append(", ");
         } else {
            first = false;
         }

         arg.toString(sb, printType);
      }

      sb.append(')');
   }

   public List getArgs() {
      return Collections.unmodifiableList(this.args);
   }

   public CallNode setArgs(List args) {
      return this.args == args ? this : new CallNode(this, this.function, args, this.flags, this.optimisticType, this.evalArgs, this.programPoint);
   }

   public EvalArgs getEvalArgs() {
      return this.evalArgs;
   }

   public CallNode setEvalArgs(EvalArgs evalArgs) {
      return this.evalArgs == evalArgs ? this : new CallNode(this, this.function, this.args, this.flags, this.optimisticType, evalArgs, this.programPoint);
   }

   public boolean isEval() {
      return this.evalArgs != null;
   }

   public boolean isApplyToCall() {
      return (this.flags & 2) != 0;
   }

   public CallNode setIsApplyToCall() {
      return this.setFlags(this.flags | 2);
   }

   public Expression getFunction() {
      return this.function;
   }

   public CallNode setFunction(Expression function) {
      return this.function == function ? this : new CallNode(this, function, this.args, this.flags, this.optimisticType, this.evalArgs, this.programPoint);
   }

   public boolean isNew() {
      return (this.flags & 1) != 0;
   }

   private CallNode setFlags(int flags) {
      return this.flags == flags ? this : new CallNode(this, this.function, this.args, flags, this.optimisticType, this.evalArgs, this.programPoint);
   }

   public int getProgramPoint() {
      return this.programPoint;
   }

   public CallNode setProgramPoint(int programPoint) {
      return this.programPoint == programPoint ? this : new CallNode(this, this.function, this.args, this.flags, this.optimisticType, this.evalArgs, programPoint);
   }

   public Type getMostOptimisticType() {
      return Type.INT;
   }

   public Type getMostPessimisticType() {
      return Type.OBJECT;
   }

   public boolean canBeOptimistic() {
      return true;
   }

   public static class EvalArgs implements Serializable {
      private static final long serialVersionUID = 1L;
      private final List args;
      private final String location;

      public EvalArgs(List args, String location) {
         this.args = args;
         this.location = location;
      }

      public List getArgs() {
         return Collections.unmodifiableList(this.args);
      }

      private EvalArgs setArgs(List args) {
         return this.args == args ? this : new EvalArgs(args, this.location);
      }

      public String getLocation() {
         return this.location;
      }
   }
}
