package net.ccbluex.liquidbounce.features.module;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.features.special.NotificationUtil;
import net.ccbluex.liquidbounce.features.special.TYPE;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\f\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010V\u001a\u00020W2\u0006\u0010X\u001a\u00020(H\u0004J\u0010\u0010Y\u001a\u00020W2\u0006\u0010X\u001a\u00020(H\u0004J\u0016\u0010Z\u001a\b\u0012\u0002\b\u0003\u0018\u00010P2\u0006\u0010[\u001a\u00020(H\u0016J\b\u0010\\\u001a\u00020\u0005H\u0016J\b\u0010]\u001a\u00020WH\u0016J\b\u0010^\u001a\u00020WH\u0016J\b\u0010_\u001a\u00020WH\u0016J\b\u0010`\u001a\u00020WH\u0016J\u0010\u0010a\u001a\u00020W2\u0006\u0010@\u001a\u00020\u0005H\u0016J\u0006\u0010b\u001a\u00020WR$\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0016\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0007R\u001a\u0010\u0018\u001a\u00020\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR$\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001e\u001a\u00020\u001f@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001a\u0010$\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0007\"\u0004\b&\u0010\tR\u001c\u0010'\u001a\u00020(8FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u001c\u0010-\u001a\u0004\u0018\u00010(X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010*\"\u0004\b/\u0010,R\u0011\u00100\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b1\u0010\u0007R\u0011\u00102\u001a\u000203¢\u0006\b\n\u0000\u001a\u0004\b4\u00105R\u001a\u00106\u001a\u00020(X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b7\u0010*\"\u0004\b8\u0010,R\u001a\u00109\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010\r\"\u0004\b;\u0010\u000fR\u001a\u0010<\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b=\u0010\r\"\u0004\b>\u0010\u000fR$\u0010@\u001a\u00020\u00052\u0006\u0010?\u001a\u00020\u0005@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bA\u0010\u0007\"\u0004\bB\u0010\tR\u0016\u0010C\u001a\u0004\u0018\u00010(8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bD\u0010*R\u001a\u0010E\u001a\u00020FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bG\u0010H\"\u0004\bI\u0010JR\u001a\u0010K\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bL\u0010\u0007\"\u0004\bM\u0010\tR\u001e\u0010N\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030P0O8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bQ\u0010RR\u001a\u0010S\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bT\u0010\r\"\u0004\bU\u0010\u000f¨\u0006c"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/Module;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "array", "", "getArray", "()Z", "setArray", "(Z)V", "arrayY", "", "getArrayY", "()F", "setArrayY", "(F)V", "autoDisable", "Lnet/ccbluex/liquidbounce/features/module/EnumAutoDisableType;", "getAutoDisable", "()Lnet/ccbluex/liquidbounce/features/module/EnumAutoDisableType;", "setAutoDisable", "(Lnet/ccbluex/liquidbounce/features/module/EnumAutoDisableType;)V", "canEnable", "getCanEnable", "category", "Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;", "getCategory", "()Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;", "setCategory", "(Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;)V", "keyBind", "", "getKeyBind", "()I", "setKeyBind", "(I)V", "loadConfig", "getLoadConfig", "setLoadConfig", "localizedName", "", "getLocalizedName", "()Ljava/lang/String;", "setLocalizedName", "(Ljava/lang/String;)V", "module", "getModule", "setModule", "moduleCommand", "getModuleCommand", "moduleInfo", "Lnet/ccbluex/liquidbounce/features/module/ModuleInfo;", "getModuleInfo", "()Lnet/ccbluex/liquidbounce/features/module/ModuleInfo;", "name", "getName", "setName", "slide", "getSlide", "setSlide", "slideStep", "getSlideStep", "setSlideStep", "value", "state", "getState", "setState", "tag", "getTag", "triggerType", "Lnet/ccbluex/liquidbounce/features/module/EnumTriggerType;", "getTriggerType", "()Lnet/ccbluex/liquidbounce/features/module/EnumTriggerType;", "setTriggerType", "(Lnet/ccbluex/liquidbounce/features/module/EnumTriggerType;)V", "update", "getUpdate", "setUpdate", "values", "", "Lnet/ccbluex/liquidbounce/features/value/Value;", "getValues", "()Ljava/util/List;", "zoom", "getZoom", "setZoom", "alert", "", "msg", "chat", "getValue", "valueName", "handleEvents", "onDisable", "onEnable", "onInitialize", "onLoad", "onToggle", "toggle", "CrossSine"}
)
public class Module extends MinecraftInstance implements Listenable {
   @NotNull
   private String name;
   private boolean update;
   @NotNull
   private String localizedName = "";
   @NotNull
   private ModuleCategory category;
   private int keyBind;
   private boolean array = true;
   private final boolean canEnable;
   @NotNull
   private EnumAutoDisableType autoDisable;
   @NotNull
   private EnumTriggerType triggerType;
   private final boolean moduleCommand;
   @NotNull
   private final ModuleInfo moduleInfo;
   private float slideStep;
   @Nullable
   private String module;
   private boolean loadConfig;
   private boolean state;
   private float slide;
   private float arrayY;
   private float zoom;

   public Module() {
      Annotation var10001 = this.getClass().getAnnotation(ModuleInfo.class);
      Intrinsics.checkNotNull(var10001);
      this.moduleInfo = (ModuleInfo)var10001;
      this.loadConfig = true;
      this.name = this.moduleInfo.name();
      this.category = this.moduleInfo.category();
      this.setKeyBind(this.moduleInfo.keyBind());
      this.setArray(this.moduleInfo.array());
      this.canEnable = this.moduleInfo.canEnable();
      this.autoDisable = this.moduleInfo.autoDisable();
      this.moduleCommand = this.moduleInfo.moduleCommand();
      this.triggerType = this.moduleInfo.triggerType();
      this.module = this.moduleInfo.module();
      this.loadConfig = this.moduleInfo.loadConfig();
   }

   @NotNull
   public final String getName() {
      return this.name;
   }

   public final void setName(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.name = var1;
   }

   public final boolean getUpdate() {
      return this.update;
   }

   public final void setUpdate(boolean var1) {
      this.update = var1;
   }

   @NotNull
   public final String getLocalizedName() {
      CharSequence var1 = (CharSequence)this.localizedName;
      Object var10000;
      if (var1.length() == 0) {
         int var2 = 0;
         var10000 = this.getName();
      } else {
         var10000 = var1;
      }

      return (String)var10000;
   }

   public final void setLocalizedName(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.localizedName = var1;
   }

   @NotNull
   public final ModuleCategory getCategory() {
      return this.category;
   }

   public final void setCategory(@NotNull ModuleCategory var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.category = var1;
   }

   public final int getKeyBind() {
      return this.keyBind;
   }

   public final void setKeyBind(int keyBind) {
      this.keyBind = keyBind;
      if (!CrossSine.INSTANCE.isStarting()) {
         CrossSine.INSTANCE.getConfigManager().smartSave();
      }

   }

   public final boolean getArray() {
      return this.array;
   }

   public final void setArray(boolean array) {
      this.array = array;
      if (!CrossSine.INSTANCE.isStarting()) {
         CrossSine.INSTANCE.getConfigManager().smartSave();
      }

   }

   public final boolean getCanEnable() {
      return this.canEnable;
   }

   @NotNull
   public final EnumAutoDisableType getAutoDisable() {
      return this.autoDisable;
   }

   public final void setAutoDisable(@NotNull EnumAutoDisableType var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.autoDisable = var1;
   }

   @NotNull
   public final EnumTriggerType getTriggerType() {
      return this.triggerType;
   }

   public final void setTriggerType(@NotNull EnumTriggerType var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.triggerType = var1;
   }

   public final boolean getModuleCommand() {
      return this.moduleCommand;
   }

   @NotNull
   public final ModuleInfo getModuleInfo() {
      return this.moduleInfo;
   }

   public final float getSlideStep() {
      return this.slideStep;
   }

   public final void setSlideStep(float var1) {
      this.slideStep = var1;
   }

   @Nullable
   public final String getModule() {
      return this.module;
   }

   public final void setModule(@Nullable String var1) {
      this.module = var1;
   }

   public final boolean getLoadConfig() {
      return this.loadConfig;
   }

   public final void setLoadConfig(boolean var1) {
      this.loadConfig = var1;
   }

   public void onLoad() {
      this.localizedName = this.name;
   }

   public final boolean getState() {
      return this.state;
   }

   public final void setState(boolean value) {
      if (this.state != value) {
         this.onToggle(value);

         try {
            this.state = this.canEnable && value;
            if (value) {
               this.onEnable();
               if (this.array && !CrossSine.INSTANCE.isLoadingConfig()) {
                  CrossSine.INSTANCE.getNotification().getList().add(new NotificationUtil("Module", Intrinsics.stringPlus("Enable ", this.name), TYPE.SUCCESS, System.currentTimeMillis(), 1000));
               }
            } else {
               this.onDisable();
               if (this.array && !CrossSine.INSTANCE.isLoadingConfig()) {
                  CrossSine.INSTANCE.getNotification().getList().add(new NotificationUtil("Module", Intrinsics.stringPlus("Disable ", this.name), TYPE.ERROR, System.currentTimeMillis(), 1000));
               }
            }
         } catch (Throwable e) {
            e.printStackTrace();
         }

         CrossSine.INSTANCE.getConfigManager().smartSave();
      }
   }

   public final float getSlide() {
      return this.slide;
   }

   public final void setSlide(float var1) {
      this.slide = var1;
   }

   public final float getArrayY() {
      return this.arrayY;
   }

   public final void setArrayY(float var1) {
      this.arrayY = var1;
   }

   public final float getZoom() {
      return this.zoom;
   }

   public final void setZoom(float var1) {
      this.zoom = var1;
   }

   @Nullable
   public String getTag() {
      return null;
   }

   public final void toggle() {
      this.setState(!this.state);
   }

   protected final void alert(@NotNull String msg) {
      Intrinsics.checkNotNullParameter(msg, "msg");
      ClientUtils.INSTANCE.displayAlert(msg);
   }

   protected final void chat(@NotNull String msg) {
      Intrinsics.checkNotNullParameter(msg, "msg");
      ClientUtils.INSTANCE.displayChatMessage(msg);
   }

   public void onToggle(boolean state) {
   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   public void onInitialize() {
   }

   @NotNull
   public List getValues() {
      return ClassUtils.INSTANCE.getValues(this.getClass(), this);
   }

   @Nullable
   public Value getValue(@NotNull String valueName) {
      Intrinsics.checkNotNullParameter(valueName, "valueName");
      Iterator var2 = ((Iterable)this.getValues()).iterator();

      Object var10000;
      while(true) {
         if (var2.hasNext()) {
            Object var3 = var2.next();
            Value it = (Value)var3;
            int var5 = 0;
            if (!StringsKt.equals(it.getName(), valueName, true)) {
               continue;
            }

            var10000 = var3;
            break;
         }

         var10000 = null;
         break;
      }

      return (Value)var10000;
   }

   public boolean handleEvents() {
      return this.state;
   }
}
