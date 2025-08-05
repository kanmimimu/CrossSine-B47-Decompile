package net.ccbluex.liquidbounce.features.module.modules.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EntityKilledEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.utils.FileUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "KillSay",
   category = ModuleCategory.PLAYER
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0015\u001a\u00020\u0007J\u0006\u0010\u0016\u001a\u00020\u0017J\u0010\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001aH\u0007J\u0018\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u00072\u0006\u0010\u001d\u001a\u00020\u0007H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/KillSay;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "insultFile", "Ljava/io/File;", "insultWords", "", "", "getInsultWords", "()Ljava/util/List;", "setInsultWords", "(Ljava/util/List;)V", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "tag", "getTag", "()Ljava/lang/String;", "waterMarkValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getRandomOne", "loadFile", "", "onKilled", "event", "Lnet/ccbluex/liquidbounce/event/EntityKilledEvent;", "sendInsultWords", "msg", "name", "CrossSine"}
)
public final class KillSay extends Module {
   @NotNull
   public static final KillSay INSTANCE = new KillSay();
   @NotNull
   private static final ListValue modeValue;
   @NotNull
   private static final BoolValue waterMarkValue;
   @NotNull
   private static final File insultFile;
   @NotNull
   private static List insultWords;

   private KillSay() {
   }

   @NotNull
   public final ListValue getModeValue() {
      return modeValue;
   }

   @NotNull
   public final List getInsultWords() {
      return insultWords;
   }

   public final void setInsultWords(@NotNull List var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      insultWords = var1;
   }

   public final void loadFile() {
      try {
         if (!insultFile.exists()) {
            FileUtils.INSTANCE.unpackFile(insultFile, "assets/minecraft/crosssine/misc/insult.json");
         }

         JsonElement json = (new JsonParser()).parse(FilesKt.readText(insultFile, Charsets.UTF_8));
         if (json.isJsonArray()) {
            insultWords.clear();
            JsonArray var2 = json.getAsJsonArray();
            Intrinsics.checkNotNullExpressionValue(var2, "json.asJsonArray");
            Iterable $this$forEach$iv = (Iterable)var2;
            int $i$f$forEach = 0;

            for(Object element$iv : $this$forEach$iv) {
               JsonElement it = (JsonElement)element$iv;
               int var7 = 0;
               List var10000 = INSTANCE.getInsultWords();
               String var8 = it.getAsString();
               Intrinsics.checkNotNullExpressionValue(var8, "it.asString");
               var10000.add(var8);
            }
         } else {
            loadFile$convertJson();
         }
      } catch (Throwable e) {
         e.printStackTrace();
         loadFile$convertJson();
      }

   }

   @NotNull
   public final String getRandomOne() {
      return (String)insultWords.get(RandomUtils.nextInt(0, insultWords.size() - 1));
   }

   @EventTarget
   public final void onKilled(@NotNull EntityKilledEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      EntityLivingBase target = event.getTargetEntity();
      if (target instanceof EntityPlayer) {
         Intrinsics.checkNotNullExpressionValue(var5, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         switch (var5) {
            case "clear":
               String var9 = Intrinsics.stringPlus("L ", ((EntityPlayer)target).func_70005_c_());
               String var7 = ((EntityPlayer)target).func_70005_c_();
               Intrinsics.checkNotNullExpressionValue(var7, "target.name");
               this.sendInsultWords(var9, var7);
               break;
            case "rawwords":
               String var8 = this.getRandomOne();
               String var6 = ((EntityPlayer)target).func_70005_c_();
               Intrinsics.checkNotNullExpressionValue(var6, "target.name");
               this.sendInsultWords(var8, var6);
               break;
            case "withwords":
               String var10001 = "L " + ((EntityPlayer)target).func_70005_c_() + ' ' + this.getRandomOne();
               String var4 = ((EntityPlayer)target).func_70005_c_();
               Intrinsics.checkNotNullExpressionValue(var4, "target.name");
               this.sendInsultWords(var10001, var4);
         }

      }
   }

   private final void sendInsultWords(String msg, String name) {
      String message = StringsKt.replace$default(msg, "%name%", name, false, 4, (Object)null);
      if ((Boolean)waterMarkValue.get()) {
         message = Intrinsics.stringPlus("[CrossSine] ", message);
      }

      MinecraftInstance.mc.field_71439_g.func_71165_d(message);
   }

   @NotNull
   public String getTag() {
      return (String)modeValue.get();
   }

   private static final void loadFile$convertJson() {
      KillSay var10000 = INSTANCE;
      insultWords.clear();
      var10000 = INSTANCE;
      List var26 = insultWords;
      Iterable $this$filter$iv = (Iterable)FilesKt.readLines(insultFile, Charsets.UTF_8);
      List var10 = var26;
      int $i$f$filter = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      int $i$f$filterTo = 0;

      for(Object element$iv$iv : $this$filter$iv) {
         String it = (String)element$iv$iv;
         int var8 = 0;
         if (!StringsKt.isBlank((CharSequence)it)) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      var10.addAll((Collection)((List)destination$iv$iv));
      JsonArray json = new JsonArray();
      KillSay var27 = INSTANCE;
      Iterable $this$map$iv = (Iterable)insultWords;
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      int $i$f$mapTo = 0;

      for(Object item$iv$iv : $this$map$iv) {
         String it = (String)item$iv$iv;
         int var9 = 0;
         destination$iv$iv.add(new JsonPrimitive(it));
      }

      $this$map$iv = (Iterable)((List)destination$iv$iv);
      $i$f$map = 0;

      for(Object element$iv : $this$map$iv) {
         JsonElement p0 = (JsonElement)element$iv;
         int var22 = 0;
         json.add(p0);
      }

      File var28 = insultFile;
      String var14 = FileManager.Companion.getPRETTY_GSON().toJson((JsonElement)json);
      Intrinsics.checkNotNullExpressionValue(var14, "FileManager.PRETTY_GSON.toJson(json)");
      FilesKt.writeText(var28, var14, Charsets.UTF_8);
   }

   static {
      String[] var0 = new String[]{"Clear", "WithWords", "RawWords"};
      modeValue = new ListValue("Mode", var0, "RawWords");
      waterMarkValue = new BoolValue("WaterMark", true);
      insultFile = new File(CrossSine.INSTANCE.getFileManager().getDir(), "insult.json");
      insultWords = (List)(new ArrayList());
      INSTANCE.loadFile();
   }
}
