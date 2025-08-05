package jdk.internal.dynalink.beans;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import sun.reflect.CallerSensitive;

public class CallerSensitiveDetector {
   private static final DetectionStrategy DETECTION_STRATEGY = getDetectionStrategy();

   static boolean isCallerSensitive(AccessibleObject ao) {
      return DETECTION_STRATEGY.isCallerSensitive(ao);
   }

   private static DetectionStrategy getDetectionStrategy() {
      try {
         return new PrivilegedDetectionStrategy();
      } catch (Throwable var1) {
         return new UnprivilegedDetectionStrategy();
      }
   }

   private abstract static class DetectionStrategy {
      private DetectionStrategy() {
      }

      abstract boolean isCallerSensitive(AccessibleObject var1);
   }

   private static class PrivilegedDetectionStrategy extends DetectionStrategy {
      private static final Class CALLER_SENSITIVE_ANNOTATION_CLASS = CallerSensitive.class;

      private PrivilegedDetectionStrategy() {
      }

      boolean isCallerSensitive(AccessibleObject ao) {
         return ao.getAnnotation(CALLER_SENSITIVE_ANNOTATION_CLASS) != null;
      }
   }

   private static class UnprivilegedDetectionStrategy extends DetectionStrategy {
      private static final String CALLER_SENSITIVE_ANNOTATION_STRING = "@sun.reflect.CallerSensitive()";

      private UnprivilegedDetectionStrategy() {
      }

      boolean isCallerSensitive(AccessibleObject o) {
         for(Annotation a : o.getAnnotations()) {
            if (String.valueOf(a).equals("@sun.reflect.CallerSensitive()")) {
               return true;
            }
         }

         return false;
      }
   }
}
