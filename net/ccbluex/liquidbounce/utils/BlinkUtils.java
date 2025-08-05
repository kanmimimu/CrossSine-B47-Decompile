package net.ccbluex.liquidbounce.utils;

import java.math.BigInteger;
import java.util.LinkedList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.text.StringsKt;
import net.minecraft.network.Packet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0018\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0014\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u001a\u001a\u00020\u000e2\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001cJ&\u0010\u001d\u001a\u00020\u001e2\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\b\u0002\u0010\u001f\u001a\u00020\u00042\b\b\u0002\u0010 \u001a\u00020\u000eJ\u0012\u0010!\u001a\u00020\u00042\b\b\u0002\u0010\u001b\u001a\u00020\u001cH\u0002J\u0012\u0010\"\u001a\u00020\u00042\n\u0010#\u001a\u0006\u0012\u0002\b\u00030\u0015J0\u0010$\u001a\u00020\u001e2\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\b\u0002\u0010\u001f\u001a\u00020\u00042\b\b\u0002\u0010 \u001a\u00020\u000e2\b\b\u0002\u0010%\u001a\u00020\u000eJt\u0010&\u001a\u00020\u001e2\b\b\u0002\u0010'\u001a\u00020\u00042\b\b\u0002\u0010(\u001a\u00020\u00042\b\b\u0002\u0010)\u001a\u00020\u00042\b\b\u0002\u0010*\u001a\u00020\u00042\b\b\u0002\u0010+\u001a\u00020\u00042\b\b\u0002\u0010,\u001a\u00020\u00042\b\b\u0002\u0010-\u001a\u00020\u00042\b\b\u0002\u0010.\u001a\u00020\u00042\b\b\u0002\u0010/\u001a\u00020\u00042\b\b\u0002\u00100\u001a\u00020\u00042\b\b\u0002\u00101\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00150\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\n\"\u0004\b\u0019\u0010\f¨\u00062"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/BlinkUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "abilitiesStat", "", "actionStat", "interactStat", "invStat", "keepAliveStat", "getKeepAliveStat", "()Z", "setKeepAliveStat", "(Z)V", "misMatch_Type", "", "movingPacketStat", "otherPacket", "packetToggleStat", "", "playerBuffer", "Ljava/util/LinkedList;", "Lnet/minecraft/network/Packet;", "Lnet/minecraft/network/play/INetHandlerPlayServer;", "transactionStat", "getTransactionStat", "setTransactionStat", "bufferSize", "packetType", "", "clearPacket", "", "onlySelected", "amount", "isBlacklisted", "pushPacket", "packets", "releasePacket", "minBuff", "setBlinkState", "off", "release", "all", "packetMoving", "packetTransaction", "packetKeepAlive", "packetAction", "packetAbilities", "packetInventory", "packetInteract", "other", "CrossSine"}
)
public final class BlinkUtils extends MinecraftInstance {
   @NotNull
   public static final BlinkUtils INSTANCE = new BlinkUtils();
   @NotNull
   private static final LinkedList playerBuffer = new LinkedList();
   private static final int misMatch_Type = -302;
   private static boolean movingPacketStat;
   private static boolean transactionStat;
   private static boolean keepAliveStat;
   private static boolean actionStat;
   private static boolean abilitiesStat;
   private static boolean invStat;
   private static boolean interactStat;
   private static boolean otherPacket;
   @NotNull
   private static boolean[] packetToggleStat;

   private BlinkUtils() {
   }

   public final boolean getTransactionStat() {
      return transactionStat;
   }

   public final void setTransactionStat(boolean var1) {
      transactionStat = var1;
   }

   public final boolean getKeepAliveStat() {
      return keepAliveStat;
   }

   public final void setKeepAliveStat(boolean var1) {
      keepAliveStat = var1;
   }

   public final void releasePacket(@Nullable String packetType, boolean onlySelected, int amount, int minBuff) {
      int count = 0;
      if (packetType == null) {
         count = -1;

         for(Packet packets : playerBuffer) {
            String var9 = packets.getClass().getSimpleName();
            Intrinsics.checkNotNullExpressionValue(var9, "packets.javaClass.simpleName");
            int packetID = (new BigInteger(StringsKt.substring(var9, new IntRange(1, 2)), 16)).intValue();
            if (packetToggleStat[packetID] || !onlySelected) {
               Intrinsics.checkNotNullExpressionValue(packets, "packets");
               PacketUtils.sendPacketNoEvent(packets);
            }
         }
      } else {
         LinkedList tempBuffer = new LinkedList();

         for(Packet packets : playerBuffer) {
            String className = packets.getClass().getSimpleName();
            if (StringsKt.equals(className, packetType, true)) {
               tempBuffer.add(packets);
            }
         }

         while(tempBuffer.size() > minBuff && (count < amount || amount <= 0)) {
            Object var12 = tempBuffer.pop();
            Intrinsics.checkNotNullExpressionValue(var12, "tempBuffer.pop()");
            PacketUtils.sendPacketNoEvent((Packet)var12);
            ++count;
         }
      }

      this.clearPacket(packetType, onlySelected, count);
   }

   // $FF: synthetic method
   public static void releasePacket$default(BlinkUtils var0, String var1, boolean var2, int var3, int var4, int var5, Object var6) {
      if ((var5 & 1) != 0) {
         var1 = null;
      }

      if ((var5 & 2) != 0) {
         var2 = false;
      }

      if ((var5 & 4) != 0) {
         var3 = -1;
      }

      if ((var5 & 8) != 0) {
         var4 = 0;
      }

      var0.releasePacket(var1, var2, var3, var4);
   }

   public final void clearPacket(@Nullable String packetType, boolean onlySelected, int amount) {
      if (packetType == null) {
         LinkedList tempBuffer = new LinkedList();

         for(Packet packets : playerBuffer) {
            String var8 = packets.getClass().getSimpleName();
            Intrinsics.checkNotNullExpressionValue(var8, "packets.javaClass.simpleName");
            int packetID = (new BigInteger(StringsKt.substring(var8, new IntRange(1, 2)), 16)).intValue();
            if (!packetToggleStat[packetID] && onlySelected) {
               tempBuffer.add(packets);
            }
         }

         playerBuffer.clear();

         for(Packet packets : tempBuffer) {
            playerBuffer.add(packets);
         }
      } else {
         int count = 0;
         LinkedList tempBuffer = new LinkedList();

         for(Packet packets : playerBuffer) {
            String className = packets.getClass().getSimpleName();
            if (!StringsKt.equals(className, packetType, true)) {
               tempBuffer.add(packets);
            } else {
               ++count;
               if (count > amount) {
                  tempBuffer.add(packets);
               }
            }
         }

         playerBuffer.clear();

         for(Packet packets : tempBuffer) {
            playerBuffer.add(packets);
         }
      }

   }

   // $FF: synthetic method
   public static void clearPacket$default(BlinkUtils var0, String var1, boolean var2, int var3, int var4, Object var5) {
      if ((var4 & 1) != 0) {
         var1 = null;
      }

      if ((var4 & 2) != 0) {
         var2 = false;
      }

      if ((var4 & 4) != 0) {
         var3 = -1;
      }

      var0.clearPacket(var1, var2, var3);
   }

   public final boolean pushPacket(@NotNull Packet packets) {
      Intrinsics.checkNotNullParameter(packets, "packets");
      String var3 = packets.getClass().getSimpleName();
      Intrinsics.checkNotNullExpressionValue(var3, "packets.javaClass.simpleName");
      int packetID = (new BigInteger(StringsKt.substring(var3, new IntRange(1, 2)), 16)).intValue();
      if (packetToggleStat[packetID]) {
         var3 = packets.getClass().getSimpleName();
         Intrinsics.checkNotNullExpressionValue(var3, "packets.javaClass.simpleName");
         if (!this.isBlacklisted(var3)) {
            playerBuffer.add(packets);
            return true;
         }
      }

      return false;
   }

   private final boolean isBlacklisted(String packetType) {
      boolean var10000;
      label38: {
         switch (packetType) {
            case "C00PacketServerQuery":
            case "C01PacketPing":
            case "C00PacketLoginStart":
            case "C00Handshake":
            case "C01PacketChatMessage":
            case "C01PacketEncryptionResponse":
         }

         var10000 = false;
         return var10000;
      }

      var10000 = true;
      return var10000;
   }

   // $FF: synthetic method
   static boolean isBlacklisted$default(BlinkUtils var0, String var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = "";
      }

      return var0.isBlacklisted(var1);
   }

   public final void setBlinkState(boolean off, boolean release, boolean all, boolean packetMoving, boolean packetTransaction, boolean packetKeepAlive, boolean packetAction, boolean packetAbilities, boolean packetInventory, boolean packetInteract, boolean other) {
      if (release) {
         releasePacket$default(this, (String)null, false, 0, 0, 15, (Object)null);
      }

      movingPacketStat = packetMoving && !off || all;
      transactionStat = packetTransaction && !off || all;
      keepAliveStat = packetKeepAlive && !off || all;
      actionStat = packetAction && !off || all;
      abilitiesStat = packetAbilities && !off || all;
      invStat = packetInventory && !off || all;
      interactStat = packetInteract && !off || all;
      otherPacket = other && !off || all;
      if (all) {
         int var12 = 0;

         int i;
         for(int var13 = packetToggleStat.length; var12 < var13; packetToggleStat[i] = true) {
            i = var12++;
         }
      } else {
         int var15 = 0;
         int var16 = packetToggleStat.length;

         while(var15 < var16) {
            int i = var15++;
            switch (i) {
               case 0:
                  packetToggleStat[i] = keepAliveStat;
                  break;
               case 1:
               case 17:
               case 18:
               case 20:
               case 21:
               case 23:
               case 24:
               case 25:
                  packetToggleStat[i] = otherPacket;
                  break;
               case 2:
               case 9:
               case 10:
               case 11:
                  packetToggleStat[i] = actionStat;
                  break;
               case 3:
               case 4:
               case 5:
               case 6:
                  packetToggleStat[i] = movingPacketStat;
                  break;
               case 7:
               case 8:
                  packetToggleStat[i] = interactStat;
                  break;
               case 12:
               case 19:
                  packetToggleStat[i] = abilitiesStat;
                  break;
               case 13:
               case 14:
               case 16:
               case 22:
                  packetToggleStat[i] = invStat;
                  break;
               case 15:
                  packetToggleStat[i] = transactionStat;
            }
         }
      }

   }

   // $FF: synthetic method
   public static void setBlinkState$default(BlinkUtils var0, boolean var1, boolean var2, boolean var3, boolean var4, boolean var5, boolean var6, boolean var7, boolean var8, boolean var9, boolean var10, boolean var11, int var12, Object var13) {
      if ((var12 & 1) != 0) {
         var1 = false;
      }

      if ((var12 & 2) != 0) {
         var2 = false;
      }

      if ((var12 & 4) != 0) {
         var3 = false;
      }

      if ((var12 & 8) != 0) {
         var4 = movingPacketStat;
      }

      if ((var12 & 16) != 0) {
         var5 = transactionStat;
      }

      if ((var12 & 32) != 0) {
         var6 = keepAliveStat;
      }

      if ((var12 & 64) != 0) {
         var7 = actionStat;
      }

      if ((var12 & 128) != 0) {
         var8 = abilitiesStat;
      }

      if ((var12 & 256) != 0) {
         var9 = invStat;
      }

      if ((var12 & 512) != 0) {
         var10 = interactStat;
      }

      if ((var12 & 1024) != 0) {
         var11 = otherPacket;
      }

      var0.setBlinkState(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11);
   }

   public final int bufferSize(@Nullable String packetType) {
      int var10000;
      if (packetType == null) {
         var10000 = playerBuffer.size();
      } else {
         int packetCount = 0;
         boolean flag = false;

         for(Packet packets : playerBuffer) {
            String className = packets.getClass().getSimpleName();
            if (StringsKt.equals(className, packetType, true)) {
               flag = true;
               ++packetCount;
            }
         }

         var10000 = flag ? packetCount : -302;
      }

      return var10000;
   }

   // $FF: synthetic method
   public static int bufferSize$default(BlinkUtils var0, String var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = null;
      }

      return var0.bufferSize(var1);
   }

   static {
      boolean[] var0 = new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
      packetToggleStat = var0;
      setBlinkState$default(INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, (Object)null);
      clearPacket$default(INSTANCE, (String)null, false, 0, 7, (Object)null);
   }
}
