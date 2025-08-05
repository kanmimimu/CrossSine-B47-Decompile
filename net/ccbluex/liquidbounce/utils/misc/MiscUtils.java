package net.ccbluex.liquidbounce.utils.misc;

import java.awt.Component;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Consumer;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J'\u0010\u0003\u001a\u0002H\u0004\"\u0004\b\u0000\u0010\u00042\u0006\u0010\u0005\u001a\u0002H\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0007¢\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u0004\u0018\u00010\nJ\b\u0010\u000b\u001a\u0004\u0018\u00010\nJ\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u000f¨\u0006\u0013"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/misc/MiscUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "make", "T", "object", "consumer", "Ljava/util/function/Consumer;", "(Ljava/lang/Object;Ljava/util/function/Consumer;)Ljava/lang/Object;", "openFileChooser", "Ljava/io/File;", "saveFileChooser", "showErrorPopup", "", "message", "", "title", "showURL", "url", "CrossSine"}
)
public final class MiscUtils extends MinecraftInstance {
   @NotNull
   public static final MiscUtils INSTANCE = new MiscUtils();

   private MiscUtils() {
   }

   public final void showErrorPopup(@NotNull String message) {
      Intrinsics.checkNotNullParameter(message, "message");
      JOptionPane.showMessageDialog((Component)null, message, "Alert", 0);
   }

   public final void showErrorPopup(@NotNull String title, @NotNull String message) {
      Intrinsics.checkNotNullParameter(title, "title");
      Intrinsics.checkNotNullParameter(message, "message");
      JOptionPane.showMessageDialog((Component)null, message, title, 0);
   }

   public final void showURL(@NotNull String url) {
      Intrinsics.checkNotNullParameter(url, "url");

      try {
         Desktop.getDesktop().browse(new URI(url));
      } catch (IOException e) {
         e.printStackTrace();
      } catch (URISyntaxException e) {
         e.printStackTrace();
      }

   }

   @Nullable
   public final File openFileChooser() {
      if (MinecraftInstance.mc.func_71372_G()) {
         MinecraftInstance.mc.func_71352_k();
      }

      JFileChooser fileChooser = new JFileChooser();
      JFrame frame = new JFrame();
      fileChooser.setFileSelectionMode(0);
      frame.setVisible(true);
      frame.toFront();
      frame.setVisible(false);
      int action = fileChooser.showOpenDialog((Component)frame);
      frame.dispose();
      return action == 0 ? fileChooser.getSelectedFile() : null;
   }

   @Nullable
   public final File saveFileChooser() {
      if (MinecraftInstance.mc.func_71372_G()) {
         MinecraftInstance.mc.func_71352_k();
      }

      JFileChooser fileChooser = new JFileChooser();
      JFrame frame = new JFrame();
      fileChooser.setFileSelectionMode(0);
      frame.setVisible(true);
      frame.toFront();
      frame.setVisible(false);
      int action = fileChooser.showSaveDialog((Component)frame);
      frame.dispose();
      return action == 0 ? fileChooser.getSelectedFile() : null;
   }

   public final Object make(Object object, @NotNull Consumer consumer) {
      Intrinsics.checkNotNullParameter(consumer, "consumer");
      consumer.accept(object);
      return object;
   }
}
