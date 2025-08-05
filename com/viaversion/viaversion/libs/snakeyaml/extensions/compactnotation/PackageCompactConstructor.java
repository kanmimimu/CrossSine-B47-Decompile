package com.viaversion.viaversion.libs.snakeyaml.extensions.compactnotation;

import com.viaversion.viaversion.libs.snakeyaml.LoaderOptions;

public class PackageCompactConstructor extends CompactConstructor {
   private final String packageName;

   public PackageCompactConstructor(String packageName) {
      super(new LoaderOptions());
      this.packageName = packageName;
   }

   protected Class getClassForName(String name) throws ClassNotFoundException {
      if (name.indexOf(46) < 0) {
         try {
            Class<?> clazz = Class.forName(this.packageName + "." + name);
            return clazz;
         } catch (ClassNotFoundException var3) {
         }
      }

      return super.getClassForName(name);
   }
}
