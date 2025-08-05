package net.ccbluex.liquidbounce.utils.misc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import kotlin.text.Charsets;
import org.apache.commons.io.IOUtils;

public final class StringUtils {
   private static final Map pinyinMap = new HashMap();
   private static HashMap airCache = new HashMap();
   private static HashMap stringCache = new HashMap();

   public static String toCompleteString(String[] args) {
      return toCompleteString(args, 0);
   }

   public static String toCompleteString(String[] args, int start) {
      return toCompleteString(args, start, " ");
   }

   public static String toCompleteString(String[] args, int start, String join) {
      return args.length <= start ? "" : String.join(join, (CharSequence[])Arrays.copyOfRange(args, start, args.length));
   }

   public static String replace(String string, String searchChars, String replaceChars) {
      if (!string.isEmpty() && !searchChars.isEmpty() && !searchChars.equals(replaceChars)) {
         if (replaceChars == null) {
            replaceChars = "";
         }

         int stringLength = string.length();
         int searchCharsLength = searchChars.length();
         StringBuilder stringBuilder = new StringBuilder(string);

         for(int i = 0; i < stringLength; ++i) {
            int start = stringBuilder.indexOf(searchChars, i);
            if (start == -1) {
               if (i == 0) {
                  return string;
               }

               return stringBuilder.toString();
            }

            stringBuilder.replace(start, start + searchCharsLength, replaceChars);
         }

         return stringBuilder.toString();
      } else {
         return string;
      }
   }

   public static String toPinyin(String inString, String fill) {
      if (pinyinMap.isEmpty()) {
         try {
            String[] dict = IOUtils.toString(StringUtils.class.getClassLoader().getResourceAsStream("assets/minecraft/crosssine/misc/pinyin"), Charsets.UTF_8).split(";");

            for(String word : dict) {
               String[] wordData = word.split(",");
               pinyinMap.put(wordData[0], wordData[1]);
            }
         } catch (Exception e) {
            e.printStackTrace();
         }
      }

      String[] strSections = inString.split("");
      StringBuilder result = new StringBuilder();
      boolean lastIsPinyin = false;

      for(String section : strSections) {
         if (pinyinMap.containsKey(section)) {
            result.append(fill);
            result.append((String)pinyinMap.get(section));
            lastIsPinyin = true;
         } else {
            if (lastIsPinyin) {
               result.append(fill);
            }

            result.append(section);
            lastIsPinyin = false;
         }
      }

      return result.toString();
   }

   public static String injectAirString(String str) {
      if (airCache.containsKey(str)) {
         return (String)airCache.get(str);
      } else {
         StringBuilder stringBuilder = new StringBuilder();
         boolean hasAdded = false;

         for(char c : str.toCharArray()) {
            stringBuilder.append(c);
            if (!hasAdded) {
               stringBuilder.append('\uf8ff');
            }

            hasAdded = true;
         }

         String result = stringBuilder.toString();
         airCache.put(str, result);
         return result;
      }
   }

   public static String fixString(String str) {
      if (stringCache.containsKey(str)) {
         return (String)stringCache.get(str);
      } else {
         str = str.replaceAll("\uf8ff", "");
         StringBuilder sb = new StringBuilder();

         for(char c : str.toCharArray()) {
            if (c > '！' && c < '｠') {
               sb.append(Character.toChars(c - 'ﻠ'));
            } else {
               sb.append(c);
            }
         }

         String result = sb.toString();
         stringCache.put(str, result);
         return result;
      }
   }
}
