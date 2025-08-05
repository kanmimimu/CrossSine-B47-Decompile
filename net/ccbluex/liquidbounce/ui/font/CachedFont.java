package net.ccbluex.liquidbounce.ui.font;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0012\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0007HÆ\u0003J'\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00072\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\b\u0010\u0019\u001a\u00020\u001aH\u0004J\t\u0010\u001b\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u001e"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/font/CachedFont;", "", "displayList", "", "lastUsage", "", "deleted", "", "(IJZ)V", "getDeleted", "()Z", "setDeleted", "(Z)V", "getDisplayList", "()I", "getLastUsage", "()J", "setLastUsage", "(J)V", "component1", "component2", "component3", "copy", "equals", "other", "finalize", "", "hashCode", "toString", "", "CrossSine"}
)
public final class CachedFont {
   private final int displayList;
   private long lastUsage;
   private boolean deleted;

   public CachedFont(int displayList, long lastUsage, boolean deleted) {
      this.displayList = displayList;
      this.lastUsage = lastUsage;
      this.deleted = deleted;
   }

   // $FF: synthetic method
   public CachedFont(int var1, long var2, boolean var4, int var5, DefaultConstructorMarker var6) {
      if ((var5 & 4) != 0) {
         var4 = false;
      }

      this(var1, var2, var4);
   }

   public final int getDisplayList() {
      return this.displayList;
   }

   public final long getLastUsage() {
      return this.lastUsage;
   }

   public final void setLastUsage(long var1) {
      this.lastUsage = var1;
   }

   public final boolean getDeleted() {
      return this.deleted;
   }

   public final void setDeleted(boolean var1) {
      this.deleted = var1;
   }

   protected final void finalize() {
      if (!this.deleted) {
         GL11.glDeleteLists(this.displayList, 1);
      }

   }

   public final int component1() {
      return this.displayList;
   }

   public final long component2() {
      return this.lastUsage;
   }

   public final boolean component3() {
      return this.deleted;
   }

   @NotNull
   public final CachedFont copy(int displayList, long lastUsage, boolean deleted) {
      return new CachedFont(displayList, lastUsage, deleted);
   }

   // $FF: synthetic method
   public static CachedFont copy$default(CachedFont var0, int var1, long var2, boolean var4, int var5, Object var6) {
      if ((var5 & 1) != 0) {
         var1 = var0.displayList;
      }

      if ((var5 & 2) != 0) {
         var2 = var0.lastUsage;
      }

      if ((var5 & 4) != 0) {
         var4 = var0.deleted;
      }

      return var0.copy(var1, var2, var4);
   }

   @NotNull
   public String toString() {
      return "CachedFont(displayList=" + this.displayList + ", lastUsage=" + this.lastUsage + ", deleted=" + this.deleted + ')';
   }

   public int hashCode() {
      int result = Integer.hashCode(this.displayList);
      result = result * 31 + Long.hashCode(this.lastUsage);
      int var10000 = result * 31;
      byte var10001 = this.deleted;
      if (var10001 != 0) {
         var10001 = 1;
      }

      result = var10000 + var10001;
      return result;
   }

   public boolean equals(@Nullable Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof CachedFont)) {
         return false;
      } else {
         CachedFont var2 = (CachedFont)other;
         if (this.displayList != var2.displayList) {
            return false;
         } else if (this.lastUsage != var2.lastUsage) {
            return false;
         } else {
            return this.deleted == var2.deleted;
         }
      }
   }
}
