package net.ccbluex.liquidbounce.utils;

import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.modules.combat.AntiBot;
import net.ccbluex.liquidbounce.features.module.modules.combat.NoFriends;
import net.ccbluex.liquidbounce.features.module.modules.world.Target;
import net.ccbluex.liquidbounce.file.configs.FriendsConfig;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\"\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\b2\b\b\u0002\u0010\u000f\u001a\u00020\bJ\u000e\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u0012J\u0010\u0010\u0013\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u000e\u0010\u0014\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0010\u0010\u0015\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0016\u0010\u0016\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\bR\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006¨\u0006\u0018"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/EntityUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "healthSubstrings", "", "", "[Ljava/lang/String;", "canRayCast", "", "entity", "Lnet/minecraft/entity/Entity;", "getHealth", "", "Lnet/minecraft/entity/EntityLivingBase;", "fromScoreboard", "absorption", "getName", "networkPlayerInfoIn", "Lnet/minecraft/client/network/NetworkPlayerInfo;", "isAnimal", "isFriend", "isMob", "isSelected", "canAttackCheck", "CrossSine"}
)
public final class EntityUtils extends MinecraftInstance {
   @NotNull
   public static final EntityUtils INSTANCE = new EntityUtils();
   @NotNull
   private static final String[] healthSubstrings;

   private EntityUtils() {
   }

   public final boolean isSelected(@NotNull Entity entity, boolean canAttackCheck) {
      Intrinsics.checkNotNullParameter(entity, "entity");
      if (entity instanceof EntityLivingBase && ((Boolean)Target.INSTANCE.getDeadValue().get() || entity.func_70089_S()) && entity != MinecraftInstance.mc.field_71439_g && ((Boolean)Target.INSTANCE.getInvisibleValue().get() || !entity.func_82150_aj())) {
         if ((Boolean)Target.INSTANCE.getPlayerValue().get() && entity instanceof EntityPlayer) {
            if (!canAttackCheck) {
               return true;
            } else if (AntiBot.isBot((EntityLivingBase)entity)) {
               return false;
            } else if (this.isFriend(entity) && !NoFriends.INSTANCE.getState()) {
               return false;
            } else if (Intrinsics.areEqual((Object)((EntityPlayer)entity).func_70005_c_(), (Object)MinecraftInstance.mc.field_71439_g.func_70005_c_())) {
               return false;
            } else if (((EntityPlayer)entity).func_175149_v()) {
               return false;
            } else if (((EntityPlayer)entity).func_70608_bn()) {
               return false;
            } else if (!CrossSine.INSTANCE.getCombatManager().isFocusEntity((EntityPlayer)entity)) {
               return false;
            } else {
               return !(Boolean)Target.INSTANCE.getFriendValue().get() || !Target.INSTANCE.isInYourTeam((EntityLivingBase)entity);
            }
         } else {
            return (Boolean)Target.INSTANCE.getMobValue().get() && this.isMob(entity) || (Boolean)Target.INSTANCE.getAnimalValue().get() && this.isAnimal(entity);
         }
      } else {
         return false;
      }
   }

   public final boolean canRayCast(@NotNull Entity entity) {
      Intrinsics.checkNotNullParameter(entity, "entity");
      if (!(entity instanceof EntityLivingBase)) {
         return false;
      } else {
         return entity instanceof EntityPlayer ? !(Boolean)Target.INSTANCE.getFriendValue().get() || !Target.INSTANCE.isInYourTeam((EntityLivingBase)entity) : (Boolean)Target.INSTANCE.getMobValue().get() && this.isMob(entity) || (Boolean)Target.INSTANCE.getAnimalValue().get() && this.isAnimal(entity);
      }
   }

   public final boolean isFriend(@NotNull Entity entity) {
      Intrinsics.checkNotNullParameter(entity, "entity");
      boolean var3;
      if (entity instanceof EntityPlayer && entity.func_70005_c_() != null) {
         FriendsConfig var10000 = CrossSine.INSTANCE.getFileManager().getFriendsConfig();
         String var2 = entity.func_70005_c_();
         Intrinsics.checkNotNullExpressionValue(var2, "entity.getName()");
         if (var10000.isFriend(ColorUtils.stripColor(var2))) {
            var3 = true;
            return var3;
         }
      }

      var3 = false;
      return var3;
   }

   private final boolean isAnimal(Entity entity) {
      return entity instanceof EntityAnimal || entity instanceof EntitySquid || entity instanceof EntityGolem || entity instanceof EntityVillager || entity instanceof EntityBat;
   }

   private final boolean isMob(Entity entity) {
      return entity instanceof EntityMob || entity instanceof EntitySlime || entity instanceof EntityGhast || entity instanceof EntityDragon;
   }

   @NotNull
   public final String getName(@NotNull NetworkPlayerInfo networkPlayerInfoIn) {
      Intrinsics.checkNotNullParameter(networkPlayerInfoIn, "networkPlayerInfoIn");
      String var10000;
      if (networkPlayerInfoIn.func_178854_k() != null) {
         String var2 = networkPlayerInfoIn.func_178854_k().func_150254_d();
         Intrinsics.checkNotNullExpressionValue(var2, "networkPlayerInfoIn.displayName.formattedText");
         var10000 = var2;
      } else {
         String var3 = ScorePlayerTeam.func_96667_a((Team)networkPlayerInfoIn.func_178850_i(), networkPlayerInfoIn.func_178845_a().getName());
         Intrinsics.checkNotNullExpressionValue(var3, "formatPlayerName(\n      …ameProfile.name\n        )");
         var10000 = var3;
      }

      return var10000;
   }

   public final float getHealth(@NotNull EntityLivingBase entity, boolean fromScoreboard, boolean absorption) {
      Intrinsics.checkNotNullParameter(entity, "entity");
      if (fromScoreboard && entity instanceof EntityPlayer) {
         EntityUtils $this$getHealth_u24lambda_u2d0 = this;
         int var6 = 0;
         Scoreboard scoreboard = ((EntityPlayer)entity).func_96123_co();
         Score objective = scoreboard.func_96529_a(((EntityPlayer)entity).func_70005_c_(), scoreboard.func_96539_a(2));
         String[] var10000 = healthSubstrings;
         ScoreObjective var10001 = objective.func_96645_d();
         if (ArraysKt.contains(var10000, var10001 == null ? null : var10001.func_96678_d())) {
            int scoreboardHealth = objective.func_96652_c();
            if (scoreboardHealth > 0) {
               return (float)scoreboardHealth;
            }
         }
      }

      float health = entity.func_110143_aJ();
      if (absorption) {
         health += entity.func_110139_bj();
      }

      return health > 0.0F ? health : 20.0F;
   }

   // $FF: synthetic method
   public static float getHealth$default(EntityUtils var0, EntityLivingBase var1, boolean var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = false;
      }

      if ((var4 & 4) != 0) {
         var3 = true;
      }

      return var0.getHealth(var1, var2, var3);
   }

   static {
      String[] var0 = new String[]{"hp", "health", "❤", "lives"};
      healthSubstrings = var0;
   }
}
