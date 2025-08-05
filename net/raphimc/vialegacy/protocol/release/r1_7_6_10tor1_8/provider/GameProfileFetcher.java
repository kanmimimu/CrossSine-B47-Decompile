package net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.provider;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.viaversion.viaversion.api.platform.providers.Provider;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import net.raphimc.vialegacy.ViaLegacy;
import net.raphimc.vialegacy.api.util.UuidUtil;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.model.GameProfile;

public abstract class GameProfileFetcher implements Provider {
   protected static final Pattern PATTERN_CONTROL_CODE = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");
   static final ThreadPoolExecutor LOADING_POOL = (ThreadPoolExecutor)Executors.newFixedThreadPool(2, (new ThreadFactoryBuilder()).setNameFormat("ViaLegacy GameProfile Loader #%d").setDaemon(true).build());
   final LoadingCache UUID_CACHE;
   final LoadingCache GAMEPROFILE_CACHE;

   public GameProfileFetcher() {
      this.UUID_CACHE = CacheBuilder.newBuilder().expireAfterWrite(6L, TimeUnit.HOURS).build(new CacheLoader() {
         public UUID load(String key) throws Exception {
            return GameProfileFetcher.this.loadMojangUUID(key);
         }
      });
      this.GAMEPROFILE_CACHE = CacheBuilder.newBuilder().expireAfterWrite(6L, TimeUnit.HOURS).build(new CacheLoader() {
         public GameProfile load(UUID key) throws Exception {
            return GameProfileFetcher.this.loadGameProfile(key);
         }
      });
   }

   public boolean isUUIDLoaded(String playerName) {
      return this.UUID_CACHE.getIfPresent(playerName) != null;
   }

   public UUID getMojangUUID(String playerName) {
      playerName = PATTERN_CONTROL_CODE.matcher(playerName).replaceAll("");

      try {
         return (UUID)this.UUID_CACHE.get(playerName);
      } catch (Throwable var6) {
         Throwable e;
         for(e = var6; e instanceof ExecutionException || e instanceof UncheckedExecutionException || e instanceof CompletionException; e = e.getCause()) {
         }

         Logger var10000 = ViaLegacy.getPlatform().getLogger();
         Level var10001 = Level.WARNING;
         String var5 = e.getClass().getName();
         var10000.log(var10001, "Failed to load uuid for player '" + playerName + "' (" + var5 + ")");
         UUID uuid = UuidUtil.createOfflinePlayerUuid(playerName);
         this.UUID_CACHE.put(playerName, uuid);
         return uuid;
      }
   }

   public CompletableFuture getMojangUUIDAsync(String playerName) {
      CompletableFuture<UUID> future = new CompletableFuture();
      if (this.isUUIDLoaded(playerName)) {
         future.complete(this.getMojangUUID(playerName));
      } else {
         LOADING_POOL.submit(() -> future.complete(this.getMojangUUID(playerName)));
      }

      return future;
   }

   public boolean isGameProfileLoaded(UUID uuid) {
      return this.GAMEPROFILE_CACHE.getIfPresent(uuid) != null;
   }

   public GameProfile getGameProfile(UUID uuid) {
      try {
         GameProfile value = (GameProfile)this.GAMEPROFILE_CACHE.get(uuid);
         return GameProfile.NULL.equals(value) ? null : value;
      } catch (Throwable var6) {
         Throwable e;
         for(e = var6; e instanceof ExecutionException || e instanceof UncheckedExecutionException || e instanceof CompletionException; e = e.getCause()) {
         }

         Logger var10000 = ViaLegacy.getPlatform().getLogger();
         Level var10001 = Level.WARNING;
         String var5 = e.getClass().getName();
         var10000.log(var10001, "Failed to load game profile for uuid '" + uuid + "' (" + var5 + ")");
         this.GAMEPROFILE_CACHE.put(uuid, GameProfile.NULL);
         return null;
      }
   }

   public CompletableFuture getGameProfileAsync(UUID uuid) {
      CompletableFuture<GameProfile> future = new CompletableFuture();
      if (this.isGameProfileLoaded(uuid)) {
         future.complete(this.getGameProfile(uuid));
      } else {
         LOADING_POOL.submit(() -> future.complete(this.getGameProfile(uuid)));
      }

      return future;
   }

   public abstract UUID loadMojangUUID(String var1) throws Exception;

   public abstract GameProfile loadGameProfile(UUID var1) throws Exception;
}
