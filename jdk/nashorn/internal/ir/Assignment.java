package jdk.nashorn.internal.ir;

public interface Assignment {
   Expression getAssignmentDest();

   Expression getAssignmentSource();

   Node setAssignmentDest(Expression var1);
}
