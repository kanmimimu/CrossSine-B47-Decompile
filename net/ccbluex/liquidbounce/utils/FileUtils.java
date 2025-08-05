package net.ccbluex.liquidbounce.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006J\u0016\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0006J\u0016\u0010\b\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\n\u001a\u00020\u0006J\u0016\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0010J\u0016\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0010¨\u0006\u0014"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/FileUtils;", "", "()V", "copyDir", "", "fromDir", "Ljava/io/File;", "toDir", "extractZip", "zipFile", "folder", "zipStream", "Ljava/io/InputStream;", "unpackFile", "file", "name", "", "writeFile", "str", "path", "CrossSine"}
)
public final class FileUtils {
   @NotNull
   public static final FileUtils INSTANCE = new FileUtils();

   private FileUtils() {
   }

   public final void unpackFile(@NotNull File file, @NotNull String name) {
      Intrinsics.checkNotNullParameter(file, "file");
      Intrinsics.checkNotNullParameter(name, "name");
      FileOutputStream fos = new FileOutputStream(file);
      IOUtils.copy(FileUtils.class.getClassLoader().getResourceAsStream(name), (OutputStream)fos);
      fos.close();
   }

   public final void writeFile(@NotNull String str, @NotNull String path) {
      Intrinsics.checkNotNullParameter(str, "str");
      Intrinsics.checkNotNullParameter(path, "path");
      File file = new File(path);
      FilesKt.writeText(file, str, Charsets.UTF_8);
   }

   public final void extractZip(@NotNull InputStream zipStream, @NotNull File folder) {
      Intrinsics.checkNotNullParameter(zipStream, "zipStream");
      Intrinsics.checkNotNullParameter(folder, "folder");
      if (!folder.exists()) {
         folder.mkdir();
      }

      Closeable var3 = (Closeable)(new ZipInputStream(zipStream));
      Throwable var4 = null;

      try {
         ZipInputStream zipInputStream = (ZipInputStream)var3;
         int var6 = 0;
         ZipEntry zipEntry = zipInputStream.getNextEntry();

         while(zipEntry != null) {
            if (zipEntry.isDirectory()) {
               zipEntry = zipInputStream.getNextEntry();
            } else {
               File newFile = new File(folder, zipEntry.getName());
               (new File(newFile.getParent())).mkdirs();
               Closeable var9 = (Closeable)(new FileOutputStream(newFile));
               Throwable var10 = null;

               try {
                  FileOutputStream it = (FileOutputStream)var9;
                  int var13 = 0;
                  long var27 = ByteStreamsKt.copyTo$default((InputStream)zipInputStream, (OutputStream)it, 0, 2, (Object)null);
               } catch (Throwable var22) {
                  var10 = var22;
                  throw var22;
               } finally {
                  CloseableKt.closeFinally(var9, var10);
               }

               zipEntry = zipInputStream.getNextEntry();
            }
         }

         zipInputStream.closeEntry();
         Unit var26 = Unit.INSTANCE;
      } catch (Throwable var24) {
         var4 = var24;
         throw var24;
      } finally {
         CloseableKt.closeFinally(var3, var4);
      }

   }

   public final void extractZip(@NotNull File zipFile, @NotNull File folder) {
      Intrinsics.checkNotNullParameter(zipFile, "zipFile");
      Intrinsics.checkNotNullParameter(folder, "folder");
      this.extractZip((InputStream)(new FileInputStream(zipFile)), folder);
   }

   public final void copyDir(@NotNull File fromDir, @NotNull File toDir) {
      Intrinsics.checkNotNullParameter(fromDir, "fromDir");
      Intrinsics.checkNotNullParameter(toDir, "toDir");
      if (!fromDir.exists()) {
         throw new IllegalArgumentException("From dir not exists");
      } else if (fromDir.isDirectory() && (!toDir.exists() || toDir.isDirectory())) {
         if (!toDir.exists()) {
            toDir.mkdirs();
         }

         File[] var3 = fromDir.listFiles();
         Intrinsics.checkNotNullExpressionValue(var3, "fromDir.listFiles()");
         Object[] $this$forEach$iv = (Object[])var3;
         int $i$f$forEach = 0;
         Object[] var5 = $this$forEach$iv;
         int var6 = 0;
         int var7 = $this$forEach$iv.length;

         while(var6 < var7) {
            Object element$iv = var5[var6];
            ++var6;
            File it = (File)element$iv;
            int var10 = 0;
            File toFile = new File(toDir, it.getName());
            if (it.isDirectory()) {
               FileUtils var10000 = INSTANCE;
               Intrinsics.checkNotNullExpressionValue(it, "it");
               var10000.copyDir(it, toFile);
            } else {
               Path var14 = it.toPath();
               Path var10001 = toFile.toPath();
               CopyOption[] var12 = new CopyOption[]{(CopyOption)StandardCopyOption.REPLACE_EXISTING};
               Files.copy(var14, var10001, var12);
            }
         }

      } else {
         throw new IllegalArgumentException("Arguments MUST be a directory");
      }
   }
}
