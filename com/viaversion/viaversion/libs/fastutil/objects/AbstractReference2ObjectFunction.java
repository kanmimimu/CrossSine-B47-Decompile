package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.Serializable;

public abstract class AbstractReference2ObjectFunction implements Reference2ObjectFunction, Serializable {
   private static final long serialVersionUID = -4940583368468432370L;
   protected Object defRetValue;

   protected AbstractReference2ObjectFunction() {
   }

   public void defaultReturnValue(Object rv) {
      this.defRetValue = rv;
   }

   public Object defaultReturnValue() {
      return this.defRetValue;
   }
}
