package net.ccbluex.liquidbounce.script.remapper.injection.transformers.handlers;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.script.ScriptSafetyManager;
import net.ccbluex.liquidbounce.script.remapper.Remapper;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\u0004H\u0007J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0007J\u001c\u0010\n\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\u0004H\u0007¨\u0006\u000b"},
   d2 = {"Lnet/ccbluex/liquidbounce/script/remapper/injection/transformers/handlers/AbstractJavaLinkerHandler;", "", "()V", "addMember", "", "clazz", "Ljava/lang/Class;", "name", "accessibleObject", "Ljava/lang/reflect/AccessibleObject;", "setPropertyGetter", "CrossSine"}
)
public final class AbstractJavaLinkerHandler {
   @NotNull
   public static final AbstractJavaLinkerHandler INSTANCE = new AbstractJavaLinkerHandler();

   private AbstractJavaLinkerHandler() {
   }

   @JvmStatic
   @NotNull
   public static final String addMember(@NotNull Class clazz, @NotNull String name, @NotNull AccessibleObject accessibleObject) {
      Intrinsics.checkNotNullParameter(clazz, "clazz");
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(accessibleObject, "accessibleObject");
      if (!(accessibleObject instanceof Method)) {
         return name;
      } else {
         Class currentClass = clazz;

         while(true) {
            if (!Intrinsics.areEqual((Object)currentClass.getName(), (Object)"java.lang.Object")) {
               if (ScriptSafetyManager.INSTANCE.isRestrictedSimple(currentClass, name)) {
                  return "RESTRICTED";
               }

               Remapper var10000 = Remapper.INSTANCE;
               String var5 = Type.getMethodDescriptor((Method)accessibleObject);
               Intrinsics.checkNotNullExpressionValue(var5, "getMethodDescriptor(accessibleObject)");
               String remapped = var10000.remapMethod(currentClass, name, var5);
               if (!Intrinsics.areEqual((Object)remapped, (Object)name)) {
                  return remapped;
               }

               if (currentClass.getSuperclass() != null) {
                  Class var6 = currentClass.getSuperclass();
                  Intrinsics.checkNotNullExpressionValue(var6, "currentClass.superclass");
                  currentClass = var6;
                  continue;
               }
            }

            return name;
         }
      }
   }

   @JvmStatic
   @NotNull
   public static final String addMember(@NotNull Class clazz, @NotNull String name) {
      Intrinsics.checkNotNullParameter(clazz, "clazz");
      Intrinsics.checkNotNullParameter(name, "name");
      Class currentClass = clazz;

      while(true) {
         if (!Intrinsics.areEqual((Object)currentClass.getName(), (Object)"java.lang.Object")) {
            if (ScriptSafetyManager.INSTANCE.isRestrictedSimple(currentClass, name)) {
               return "RESTRICTED";
            }

            String remapped = Remapper.INSTANCE.remapField(currentClass, name);
            if (!Intrinsics.areEqual((Object)remapped, (Object)name)) {
               return remapped;
            }

            if (currentClass.getSuperclass() != null) {
               Class var4 = currentClass.getSuperclass();
               Intrinsics.checkNotNullExpressionValue(var4, "currentClass.superclass");
               currentClass = var4;
               continue;
            }
         }

         return name;
      }
   }

   @JvmStatic
   @NotNull
   public static final String setPropertyGetter(@NotNull Class clazz, @NotNull String name) {
      Intrinsics.checkNotNullParameter(clazz, "clazz");
      Intrinsics.checkNotNullParameter(name, "name");
      Class currentClass = clazz;

      while(true) {
         if (!Intrinsics.areEqual((Object)currentClass.getName(), (Object)"java.lang.Object")) {
            if (ScriptSafetyManager.INSTANCE.isRestrictedSimple(currentClass, name)) {
               return "RESTRICTED";
            }

            String remapped = Remapper.INSTANCE.remapField(currentClass, name);
            if (!Intrinsics.areEqual((Object)remapped, (Object)name)) {
               return remapped;
            }

            if (currentClass.getSuperclass() != null) {
               Class var4 = currentClass.getSuperclass();
               Intrinsics.checkNotNullExpressionValue(var4, "currentClass.superclass");
               currentClass = var4;
               continue;
            }
         }

         return name;
      }
   }
}
