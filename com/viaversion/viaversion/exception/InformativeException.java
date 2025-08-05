package com.viaversion.viaversion.exception;

import java.util.HashMap;
import java.util.Map;

public class InformativeException extends RuntimeException {
   private final Map info = new HashMap();
   private boolean shouldBePrinted = true;
   private int sources;

   public InformativeException(Throwable cause) {
      super(cause);
   }

   public InformativeException set(String key, Object value) {
      this.info.put(key, value);
      return this;
   }

   public InformativeException addSource(Class sourceClazz) {
      int var4 = this.sources++;
      return this.set("Source " + var4, this.getSource(sourceClazz));
   }

   private String getSource(Class sourceClazz) {
      String var10000;
      if (sourceClazz.isAnonymousClass()) {
         String var4 = sourceClazz.getName();
         var10000 = var4 + " (Anonymous)";
      } else {
         var10000 = sourceClazz.getName();
      }

      return var10000;
   }

   public boolean shouldBePrinted() {
      return this.shouldBePrinted;
   }

   public void setShouldBePrinted(boolean shouldBePrinted) {
      this.shouldBePrinted = shouldBePrinted;
   }

   public String getMessage() {
      StringBuilder builder = new StringBuilder("Please report this on the Via support Discord or open an issue on the relevant GitHub repository\n");
      boolean first = true;

      for(Map.Entry entry : this.info.entrySet()) {
         if (!first) {
            builder.append(", ");
         }

         builder.append((String)entry.getKey()).append(": ").append(entry.getValue());
         first = false;
      }

      return builder.toString();
   }

   public Throwable fillInStackTrace() {
      return this;
   }
}
