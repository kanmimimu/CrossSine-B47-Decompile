package net.ccbluex.liquidbounce.file.configs;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.file.FileConfig;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001\u001bB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u0012H\u0007J\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0012J\u0010\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u0012H\u0016J\u000e\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0012J\b\u0010\u001a\u001a\u00020\u0012H\u0016R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001c"},
   d2 = {"Lnet/ccbluex/liquidbounce/file/configs/FriendsConfig;", "Lnet/ccbluex/liquidbounce/file/FileConfig;", "file", "Ljava/io/File;", "(Ljava/io/File;)V", "aura", "", "getAura", "()Z", "setAura", "(Z)V", "friends", "", "Lnet/ccbluex/liquidbounce/file/configs/FriendsConfig$Friend;", "getFriends", "()Ljava/util/List;", "addFriend", "playerName", "", "alias", "clearFriends", "", "isFriend", "loadConfig", "config", "removeFriend", "saveConfig", "Friend", "CrossSine"}
)
public final class FriendsConfig extends FileConfig {
   @NotNull
   private final List friends;
   private boolean aura;

   public FriendsConfig(@NotNull File file) {
      Intrinsics.checkNotNullParameter(file, "file");
      super(file);
      this.friends = (List)(new ArrayList());
   }

   @NotNull
   public final List getFriends() {
      return this.friends;
   }

   public final boolean getAura() {
      return this.aura;
   }

   public final void setAura(boolean var1) {
      this.aura = var1;
   }

   public void loadConfig(@NotNull String config) {
      Intrinsics.checkNotNullParameter(config, "config");
      this.clearFriends();
      CharSequence var10000 = (CharSequence)config;
      String[] var2 = new String[]{"\n"};
      Iterable $this$forEach$iv = (Iterable)StringsKt.split$default(var10000, var2, false, 0, 6, (Object)null);
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         String line = (String)element$iv;
         int var7 = 0;
         if (StringsKt.contains$default((CharSequence)line, (CharSequence)":", false, 2, (Object)null)) {
            var10000 = (CharSequence)line;
            String[] thisCollection$iv = new String[]{":"};
            Collection $this$toTypedArray$iv = (Collection)StringsKt.split$default(var10000, thisCollection$iv, false, 0, 6, (Object)null);
            int $i$f$toTypedArray = 0;
            Object[] var15 = $this$toTypedArray$iv.toArray(new String[0]);
            if (var15 == null) {
               throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
            }

            String[] data = (String[])var15;
            this.addFriend(data[0], data[1]);
         } else {
            addFriend$default(this, line, (String)null, 2, (Object)null);
         }
      }

   }

   @NotNull
   public String saveConfig() {
      StringBuilder builder = new StringBuilder();

      for(Friend friend : this.friends) {
         builder.append(friend.getPlayerName()).append(":").append(friend.getAlias()).append("\n");
      }

      String var4 = builder.toString();
      Intrinsics.checkNotNullExpressionValue(var4, "builder.toString()");
      return var4;
   }

   @JvmOverloads
   public final boolean addFriend(@NotNull String playerName, @NotNull String alias) {
      Intrinsics.checkNotNullParameter(playerName, "playerName");
      Intrinsics.checkNotNullParameter(alias, "alias");
      if (this.isFriend(playerName)) {
         return false;
      } else {
         this.friends.add(new Friend(playerName, alias));
         return true;
      }
   }

   // $FF: synthetic method
   public static boolean addFriend$default(FriendsConfig var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var1;
      }

      return var0.addFriend(var1, var2);
   }

   public final boolean removeFriend(@NotNull String playerName) {
      Intrinsics.checkNotNullParameter(playerName, "playerName");
      if (!this.isFriend(playerName)) {
         return false;
      } else {
         this.friends.removeIf(FriendsConfig::removeFriend$lambda-1);
         return true;
      }
   }

   public final boolean isFriend(@NotNull String playerName) {
      Intrinsics.checkNotNullParameter(playerName, "playerName");

      for(Friend friend : this.friends) {
         if (Intrinsics.areEqual((Object)friend.getPlayerName(), (Object)playerName)) {
            return true;
         }
      }

      return false;
   }

   public final void clearFriends() {
      this.friends.clear();
   }

   @JvmOverloads
   public final boolean addFriend(@NotNull String playerName) {
      Intrinsics.checkNotNullParameter(playerName, "playerName");
      return addFriend$default(this, playerName, (String)null, 2, (Object)null);
   }

   private static final boolean removeFriend$lambda_1/* $FF was: removeFriend$lambda-1*/(String $playerName, Friend friend) {
      Intrinsics.checkNotNullParameter($playerName, "$playerName");
      Intrinsics.checkNotNullParameter(friend, "friend");
      return Intrinsics.areEqual((Object)friend.getPlayerName(), (Object)$playerName);
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\t"},
      d2 = {"Lnet/ccbluex/liquidbounce/file/configs/FriendsConfig$Friend;", "", "playerName", "", "alias", "(Ljava/lang/String;Ljava/lang/String;)V", "getAlias", "()Ljava/lang/String;", "getPlayerName", "CrossSine"}
   )
   public static final class Friend {
      @NotNull
      private final String playerName;
      @NotNull
      private final String alias;

      public Friend(@NotNull String playerName, @NotNull String alias) {
         Intrinsics.checkNotNullParameter(playerName, "playerName");
         Intrinsics.checkNotNullParameter(alias, "alias");
         super();
         this.playerName = playerName;
         this.alias = alias;
      }

      @NotNull
      public final String getPlayerName() {
         return this.playerName;
      }

      @NotNull
      public final String getAlias() {
         return this.alias;
      }
   }
}
