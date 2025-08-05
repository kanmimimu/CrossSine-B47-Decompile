package jdk.nashorn.internal.runtime.regexp.joni.encoding;

public final class ObjPtr {
   public Object p;

   public ObjPtr() {
      this((Object)null);
   }

   public ObjPtr(Object p) {
      this.p = p;
   }
}
