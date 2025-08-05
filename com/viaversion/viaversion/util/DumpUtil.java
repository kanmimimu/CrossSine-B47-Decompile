package com.viaversion.viaversion.util;

import com.google.common.io.CharStreams;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.dump.DumpTemplate;
import com.viaversion.viaversion.libs.gson.GsonBuilder;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InvalidObjectException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class DumpUtil {
   public static CompletableFuture postDump(@Nullable UUID playerToSample) {
      ProtocolVersion protocolVersion = Via.getAPI().getServerVersion().lowestSupportedProtocolVersion();
      ViaPlatform<?> platform = Via.getPlatform();
      com.viaversion.viaversion.dump.VersionInfo version = new com.viaversion.viaversion.dump.VersionInfo(System.getProperty("java.version"), System.getProperty("os.name"), protocolVersion.getVersion(), protocolVersion.getName(), (Set)Via.getManager().getProtocolManager().getSupportedVersions().stream().map(ProtocolVersion::toString).collect(Collectors.toCollection(LinkedHashSet::new)), platform.getPlatformName(), platform.getPlatformVersion(), platform.getPluginVersion(), VersionInfo.getImplementationVersion(), Via.getManager().getSubPlatforms());
      Map<String, Object> configuration = ((Config)Via.getConfig()).getValues();
      DumpTemplate template = new DumpTemplate(version, configuration, platform.getDump(), Via.getManager().getInjector().getDump(), getPlayerSample(playerToSample));
      CompletableFuture<String> result = new CompletableFuture();
      platform.runAsync(() -> {
         HttpURLConnection con;
         try {
            con = (HttpURLConnection)(new URL("https://dump.viaversion.com/documents")).openConnection();
         } catch (IOException e) {
            platform.getLogger().log(Level.SEVERE, "Error when opening connection to ViaVersion dump service", e);
            result.completeExceptionally(new DumpException(DumpUtil.DumpErrorType.CONNECTION, e));
            return;
         }

         try {
            con.setRequestProperty("Content-Type", "application/json");
            String var10002 = platform.getPlatformName();
            String var11 = version.pluginVersion();
            String var10 = var10002;
            con.addRequestProperty("User-Agent", "ViaVersion-" + var10 + "/" + var11);
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            OutputStream out = con.getOutputStream();

            try {
               out.write((new GsonBuilder()).setPrettyPrinting().create().toJson((Object)template).getBytes(StandardCharsets.UTF_8));
            } catch (Throwable var16) {
               if (out != null) {
                  try {
                     out.close();
                  } catch (Throwable var13) {
                     var16.addSuppressed(var13);
                  }
               }

               throw var16;
            }

            if (out != null) {
               out.close();
            }

            if (con.getResponseCode() == 429) {
               result.completeExceptionally(new DumpException(DumpUtil.DumpErrorType.RATE_LIMITED));
               return;
            }

            InputStream inputStream = con.getInputStream();

            try {
               rawOutput = CharStreams.toString(new InputStreamReader(inputStream));
            } catch (Throwable var15) {
               if (inputStream != null) {
                  try {
                     inputStream.close();
                  } catch (Throwable var12) {
                     var15.addSuppressed(var12);
                  }
               }

               throw var15;
            }

            if (inputStream != null) {
               inputStream.close();
            }

            JsonObject output = (JsonObject)GsonUtil.getGson().fromJson(rawOutput, JsonObject.class);
            if (!output.has("key")) {
               throw new InvalidObjectException("Key is not given in Hastebin output");
            }

            result.complete(urlForId(output.get("key").getAsString()));
         } catch (Exception e) {
            platform.getLogger().log(Level.SEVERE, "Error when posting ViaVersion dump", e);
            result.completeExceptionally(new DumpException(DumpUtil.DumpErrorType.POST, e));
            printFailureInfo(con);
         }

      });
      return result;
   }

   static void printFailureInfo(HttpURLConnection connection) {
      try {
         if (connection.getResponseCode() < 200 || connection.getResponseCode() > 400) {
            InputStream errorStream = connection.getErrorStream();

            try {
               String rawOutput = CharStreams.toString(new InputStreamReader(errorStream));
               Via.getPlatform().getLogger().log(Level.SEVERE, "Page returned: " + rawOutput);
            } catch (Throwable var7) {
               if (errorStream != null) {
                  try {
                     errorStream.close();
                  } catch (Throwable var6) {
                     var7.addSuppressed(var6);
                  }
               }

               throw var7;
            }

            if (errorStream != null) {
               errorStream.close();
            }
         }
      } catch (IOException e) {
         Via.getPlatform().getLogger().log(Level.SEVERE, "Failed to capture further info", e);
      }

   }

   public static String urlForId(String id) {
      return String.format("https://dump.viaversion.com/%s", id);
   }

   static JsonObject getPlayerSample(@Nullable UUID uuid) {
      JsonObject playerSample = new JsonObject();
      JsonObject versions = new JsonObject();
      playerSample.add("versions", versions);
      Map<ProtocolVersion, Integer> playerVersions = new TreeMap(ProtocolVersion::compareTo);

      for(UserConnection connection : Via.getManager().getConnectionManager().getConnections()) {
         ProtocolVersion protocolVersion = connection.getProtocolInfo().protocolVersion();
         playerVersions.compute(protocolVersion, (v, num) -> num != null ? num + 1 : 1);
      }

      for(Map.Entry entry : playerVersions.entrySet()) {
         versions.addProperty(((ProtocolVersion)entry.getKey()).getName(), (Number)entry.getValue());
      }

      Set<List<String>> pipelines = new HashSet();
      if (uuid != null) {
         UserConnection senderConnection = Via.getAPI().getConnection(uuid);
         if (senderConnection != null && senderConnection.getChannel() != null) {
            pipelines.add(senderConnection.getChannel().pipeline().names());
         }
      }

      for(UserConnection connection : Via.getManager().getConnectionManager().getConnections()) {
         if (connection.getChannel() != null) {
            List<String> names = connection.getChannel().pipeline().names();
            if (pipelines.add(names) && pipelines.size() == 3) {
               break;
            }
         }
      }

      int i = 0;

      for(List pipeline : pipelines) {
         JsonArray senderPipeline = new JsonArray(pipeline.size());

         for(String name : pipeline) {
            senderPipeline.add(name);
         }

         int var12 = i++;
         playerSample.add("pipeline-" + var12, senderPipeline);
      }

      return playerSample;
   }

   public static final class DumpException extends RuntimeException {
      final DumpErrorType errorType;

      DumpException(DumpErrorType errorType, Throwable cause) {
         super(errorType.message(), cause);
         this.errorType = errorType;
      }

      DumpException(DumpErrorType errorType) {
         super(errorType.message());
         this.errorType = errorType;
      }

      public DumpErrorType errorType() {
         return this.errorType;
      }
   }

   public static enum DumpErrorType {
      CONNECTION("Failed to dump, please check the console for more information"),
      RATE_LIMITED("Please wait before creating another dump"),
      POST("Failed to dump, please check the console for more information");

      final String message;

      DumpErrorType(String message) {
         this.message = message;
      }

      public String message() {
         return this.message;
      }

      // $FF: synthetic method
      static DumpErrorType[] $values() {
         return new DumpErrorType[]{CONNECTION, RATE_LIMITED, POST};
      }
   }
}
