package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappingDataLoader;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public class TranslatableRewriter extends ComponentRewriter {
   private static final Map TRANSLATABLES = new HashMap();
   private final Map translatables;

   public static void loadTranslatables() {
      if (!TRANSLATABLES.isEmpty()) {
         throw new IllegalStateException("Translatables already loaded!");
      } else {
         fillTranslatables(BackwardsMappingDataLoader.INSTANCE.loadFromDataDir("translation-mappings.json"), TRANSLATABLES);
      }
   }

   public static void fillTranslatables(JsonObject jsonObject, Map translatables) {
      for(Map.Entry entry : jsonObject.entrySet()) {
         Map<String, String> versionMappings = new HashMap();
         translatables.put((String)entry.getKey(), versionMappings);

         for(Map.Entry translationEntry : ((JsonElement)entry.getValue()).getAsJsonObject().entrySet()) {
            versionMappings.put((String)translationEntry.getKey(), ((JsonElement)translationEntry.getValue()).getAsString());
         }
      }

   }

   public TranslatableRewriter(BackwardsProtocol protocol, ComponentRewriter.ReadType type) {
      this(protocol, type, protocol.getClass().getSimpleName().replace("Protocol", "").split("To")[0].replace("_", "."));
   }

   public TranslatableRewriter(BackwardsProtocol protocol, ComponentRewriter.ReadType type, String version) {
      super(protocol, type);
      Map<String, String> translatableMappings = getTranslatableMappings(version);
      if (translatableMappings == null) {
         protocol.getLogger().warning("Missing " + version + " translatables!");
         this.translatables = new HashMap();
      } else {
         this.translatables = translatableMappings;
      }

   }

   protected void handleTranslate(JsonObject root, String translate) {
      String newTranslate = this.mappedTranslationKey(translate);
      if (newTranslate != null) {
         root.addProperty("translate", newTranslate);
      }

   }

   protected void handleTranslate(UserConnection connection, CompoundTag parentTag, StringTag translateTag) {
      String newTranslate = this.mappedTranslationKey(translateTag.getValue());
      if (newTranslate != null) {
         parentTag.put("translate", new StringTag(newTranslate));
      }

   }

   public @Nullable String mappedTranslationKey(String translationKey) {
      return (String)this.translatables.get(translationKey);
   }

   public static Map getTranslatableMappings(String sectionIdentifier) {
      return (Map)TRANSLATABLES.get(sectionIdentifier);
   }
}
