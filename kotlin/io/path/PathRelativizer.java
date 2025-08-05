package kotlin.io.path;

import java.nio.file.Path;
import java.nio.file.Paths;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0004R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"},
   d2 = {"Lkotlin/io/path/PathRelativizer;", "", "()V", "emptyPath", "Ljava/nio/file/Path;", "kotlin.jvm.PlatformType", "parentPath", "tryRelativeTo", "path", "base", "kotlin-stdlib-jdk7"}
)
final class PathRelativizer {
   @NotNull
   public static final PathRelativizer INSTANCE = new PathRelativizer();
   private static final Path emptyPath = Paths.get("");
   private static final Path parentPath = Paths.get("..");

   private PathRelativizer() {
   }

   @NotNull
   public final Path tryRelativeTo(@NotNull Path path, @NotNull Path base) {
      Intrinsics.checkNotNullParameter(path, "path");
      Intrinsics.checkNotNullParameter(base, "base");
      Path bn = base.normalize();
      Path pn = path.normalize();
      Path rn = bn.relativize(pn);
      int var6 = 0;
      int var7 = Math.min(bn.getNameCount(), pn.getNameCount());

      while(var6 < var7) {
         int i = var6++;
         if (!Intrinsics.areEqual((Object)bn.getName(i), (Object)parentPath)) {
            break;
         }

         if (!Intrinsics.areEqual((Object)pn.getName(i), (Object)parentPath)) {
            throw new IllegalArgumentException("Unable to compute relative path");
         }
      }

      Path var10000;
      if (!Intrinsics.areEqual((Object)pn, (Object)bn) && Intrinsics.areEqual((Object)bn, (Object)emptyPath)) {
         var10000 = pn;
      } else {
         String rnString = rn.toString();
         String var11 = rn.getFileSystem().getSeparator();
         Intrinsics.checkNotNullExpressionValue(var11, "rn.fileSystem.separator");
         var10000 = StringsKt.endsWith$default(rnString, var11, false, 2, (Object)null) ? rn.getFileSystem().getPath(StringsKt.dropLast(rnString, rn.getFileSystem().getSeparator().length())) : rn;
      }

      Path r = var10000;
      Intrinsics.checkNotNullExpressionValue(r, "r");
      return r;
   }
}
