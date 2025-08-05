package kotlin.jvm.internal;

import kotlin.SinceKotlin;

@SinceKotlin(
   version = "1.2"
)
public class MagicApiIntrinsics {
   public static Object anyMagicApiCall(int id) {
      return null;
   }

   public static void voidMagicApiCall(int id) {
   }

   public static int intMagicApiCall(int id) {
      return 0;
   }

   public static Object anyMagicApiCall(Object data) {
      return null;
   }

   public static void voidMagicApiCall(Object data) {
   }

   public static int intMagicApiCall(Object data) {
      return 0;
   }

   public static int intMagicApiCall(int id, long longData, Object anyData) {
      return 0;
   }

   public static int intMagicApiCall(int id, long longData1, long longData2, Object anyData) {
      return 0;
   }

   public static int intMagicApiCall(int id, Object anyData1, Object anyData2) {
      return 0;
   }

   public static int intMagicApiCall(int id, Object anyData1, Object anyData2, Object anyData3, Object anyData4) {
      return 0;
   }

   public static Object anyMagicApiCall(int id, long longData, Object anyData) {
      return null;
   }

   public static Object anyMagicApiCall(int id, long longData1, long longData2, Object anyData) {
      return null;
   }

   public static Object anyMagicApiCall(int id, Object anyData1, Object anyData2) {
      return null;
   }

   public static Object anyMagicApiCall(int id, Object anyData1, Object anyData2, Object anyData3, Object anyData4) {
      return null;
   }
}
