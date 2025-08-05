package com.viaversion.viaversion.update;

import com.google.common.base.Joiner;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Version implements Comparable {
   private static final Pattern semVer = Pattern.compile("(?<a>0|[1-9]\\d*)\\.(?<b>0|[1-9]\\d*)(?:\\.(?<c>0|[1-9]\\d*))?(?:-(?<tag>[A-z0-9.-]*))?");
   private final int[] parts = new int[3];
   private final String tag;

   public Version(String value) {
      if (value == null) {
         throw new IllegalArgumentException("Version can not be null");
      } else {
         Matcher matcher = semVer.matcher(value);
         if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid version format");
         } else {
            this.parts[0] = Integer.parseInt(matcher.group("a"));
            this.parts[1] = Integer.parseInt(matcher.group("b"));
            this.parts[2] = matcher.group("c") == null ? 0 : Integer.parseInt(matcher.group("c"));
            this.tag = matcher.group("tag") == null ? "" : matcher.group("tag");
         }
      }
   }

   public static int compare(Version verA, Version verB) {
      if (verA == verB) {
         return 0;
      } else if (verA == null) {
         return -1;
      } else if (verB == null) {
         return 1;
      } else {
         int max = Math.max(verA.parts.length, verB.parts.length);

         for(int i = 0; i < max; ++i) {
            int partA = i < verA.parts.length ? verA.parts[i] : 0;
            int partB = i < verB.parts.length ? verB.parts[i] : 0;
            if (partA < partB) {
               return -1;
            }

            if (partA > partB) {
               return 1;
            }
         }

         if (verA.tag.isEmpty() && !verB.tag.isEmpty()) {
            return 1;
         } else if (!verA.tag.isEmpty() && verB.tag.isEmpty()) {
            return -1;
         } else {
            return 0;
         }
      }
   }

   public static boolean equals(Version verA, Version verB) {
      return verA == verB || verA != null && verB != null && compare(verA, verB) == 0;
   }

   public String toString() {
      String[] split = new String[this.parts.length];

      for(int i = 0; i < this.parts.length; ++i) {
         split[i] = String.valueOf(this.parts[i]);
      }

      String var10000 = Joiner.on(".").join(split);
      String var10001;
      if (!this.tag.isEmpty()) {
         String var4 = this.tag;
         var10001 = "-" + var4;
      } else {
         var10001 = "";
      }

      String var7 = var10001;
      String var6 = var10000;
      return var6 + var7;
   }

   public int compareTo(Version that) {
      return compare(this, that);
   }

   public boolean equals(Object that) {
      boolean var10000;
      if (that instanceof Version) {
         Version version = (Version)that;
         if (equals(this, version)) {
            var10000 = true;
            return var10000;
         }
      }

      var10000 = false;
      return var10000;
   }

   public int hashCode() {
      int result = Objects.hash(new Object[]{this.tag});
      result = 31 * result + Arrays.hashCode(this.parts);
      return result;
   }

   public String getTag() {
      return this.tag;
   }
}
