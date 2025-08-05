package net.ccbluex.liquidbounce.ui.client.gui.clickgui;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000b\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\r\u001a\u00020\u00052\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0012"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/SelectedConfig;", "", "name", "", "isOnline", "", "(Ljava/lang/String;Z)V", "()Z", "getName", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "other", "hashCode", "", "toString", "CrossSine"}
)
public final class SelectedConfig {
   @NotNull
   private final String name;
   private final boolean isOnline;

   public SelectedConfig(@NotNull String name, boolean isOnline) {
      Intrinsics.checkNotNullParameter(name, "name");
      super();
      this.name = name;
      this.isOnline = isOnline;
   }

   @NotNull
   public final String getName() {
      return this.name;
   }

   public final boolean isOnline() {
      return this.isOnline;
   }

   @NotNull
   public final String component1() {
      return this.name;
   }

   public final boolean component2() {
      return this.isOnline;
   }

   @NotNull
   public final SelectedConfig copy(@NotNull String name, boolean isOnline) {
      Intrinsics.checkNotNullParameter(name, "name");
      return new SelectedConfig(name, isOnline);
   }

   // $FF: synthetic method
   public static SelectedConfig copy$default(SelectedConfig var0, String var1, boolean var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = var0.name;
      }

      if ((var3 & 2) != 0) {
         var2 = var0.isOnline;
      }

      return var0.copy(var1, var2);
   }

   @NotNull
   public String toString() {
      return "SelectedConfig(name=" + this.name + ", isOnline=" + this.isOnline + ')';
   }

   public int hashCode() {
      int result = this.name.hashCode();
      int var10000 = result * 31;
      byte var10001 = this.isOnline;
      if (var10001 != 0) {
         var10001 = 1;
      }

      result = var10000 + var10001;
      return result;
   }

   public boolean equals(@Nullable Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof SelectedConfig)) {
         return false;
      } else {
         SelectedConfig var2 = (SelectedConfig)other;
         if (!Intrinsics.areEqual((Object)this.name, (Object)var2.name)) {
            return false;
         } else {
            return this.isOnline == var2.isOnline;
         }
      }
   }
}
