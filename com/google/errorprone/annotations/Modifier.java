package com.google.errorprone.annotations;

public enum Modifier {
   PUBLIC,
   PROTECTED,
   PRIVATE,
   ABSTRACT,
   DEFAULT,
   STATIC,
   FINAL,
   TRANSIENT,
   VOLATILE,
   SYNCHRONIZED,
   NATIVE,
   STRICTFP;

   // $FF: synthetic method
   private static Modifier[] $values() {
      return new Modifier[]{PUBLIC, PROTECTED, PRIVATE, ABSTRACT, DEFAULT, STATIC, FINAL, TRANSIENT, VOLATILE, SYNCHRONIZED, NATIVE, STRICTFP};
   }
}
