package kotlin.io;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin._Assertions;
import kotlin.collections.AbstractIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\b\u0006\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0003\u001a\u001b\u001cB\u0019\b\u0010\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u0089\u0001\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u0014\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\t\u0018\u00010\b\u0012\u0014\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u000b\u0018\u00010\b\u00128\u0010\f\u001a4\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b\u0018\u00010\r\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014¢\u0006\u0002\u0010\u0015J\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00020\u0017H\u0096\u0002J\u000e\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0018\u001a\u00020\u0014J\u001a\u0010\u0007\u001a\u00020\u00002\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\t0\bJ \u0010\f\u001a\u00020\u00002\u0018\u0010\u0019\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u000b0\rJ\u001a\u0010\n\u001a\u00020\u00002\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u000b0\bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R@\u0010\f\u001a4\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b\u0018\u00010\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u000b\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"},
   d2 = {"Lkotlin/io/FileTreeWalk;", "Lkotlin/sequences/Sequence;", "Ljava/io/File;", "start", "direction", "Lkotlin/io/FileWalkDirection;", "(Ljava/io/File;Lkotlin/io/FileWalkDirection;)V", "onEnter", "Lkotlin/Function1;", "", "onLeave", "", "onFail", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "f", "Ljava/io/IOException;", "e", "maxDepth", "", "(Ljava/io/File;Lkotlin/io/FileWalkDirection;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;I)V", "iterator", "", "depth", "function", "DirectoryState", "FileTreeWalkIterator", "WalkState", "kotlin-stdlib"}
)
public final class FileTreeWalk implements Sequence {
   @NotNull
   private final File start;
   @NotNull
   private final FileWalkDirection direction;
   @Nullable
   private final Function1 onEnter;
   @Nullable
   private final Function1 onLeave;
   @Nullable
   private final Function2 onFail;
   private final int maxDepth;

   private FileTreeWalk(File start, FileWalkDirection direction, Function1 onEnter, Function1 onLeave, Function2 onFail, int maxDepth) {
      this.start = start;
      this.direction = direction;
      this.onEnter = onEnter;
      this.onLeave = onLeave;
      this.onFail = onFail;
      this.maxDepth = maxDepth;
   }

   // $FF: synthetic method
   FileTreeWalk(File var1, FileWalkDirection var2, Function1 var3, Function1 var4, Function2 var5, int var6, int var7, DefaultConstructorMarker var8) {
      if ((var7 & 2) != 0) {
         var2 = FileWalkDirection.TOP_DOWN;
      }

      if ((var7 & 32) != 0) {
         var6 = Integer.MAX_VALUE;
      }

      this(var1, var2, var3, var4, var5, var6);
   }

   public FileTreeWalk(@NotNull File start, @NotNull FileWalkDirection direction) {
      Intrinsics.checkNotNullParameter(start, "start");
      Intrinsics.checkNotNullParameter(direction, "direction");
      this(start, direction, (Function1)null, (Function1)null, (Function2)null, 0, 32, (DefaultConstructorMarker)null);
   }

   // $FF: synthetic method
   public FileTreeWalk(File var1, FileWalkDirection var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = FileWalkDirection.TOP_DOWN;
      }

      this(var1, var2);
   }

   @NotNull
   public Iterator iterator() {
      return new FileTreeWalkIterator();
   }

   @NotNull
   public final FileTreeWalk onEnter(@NotNull Function1 function) {
      Intrinsics.checkNotNullParameter(function, "function");
      return new FileTreeWalk(this.start, this.direction, function, this.onLeave, this.onFail, this.maxDepth);
   }

   @NotNull
   public final FileTreeWalk onLeave(@NotNull Function1 function) {
      Intrinsics.checkNotNullParameter(function, "function");
      return new FileTreeWalk(this.start, this.direction, this.onEnter, function, this.onFail, this.maxDepth);
   }

   @NotNull
   public final FileTreeWalk onFail(@NotNull Function2 function) {
      Intrinsics.checkNotNullParameter(function, "function");
      return new FileTreeWalk(this.start, this.direction, this.onEnter, this.onLeave, function, this.maxDepth);
   }

   @NotNull
   public final FileTreeWalk maxDepth(int depth) {
      if (depth <= 0) {
         throw new IllegalArgumentException("depth must be positive, but was " + depth + '.');
      } else {
         return new FileTreeWalk(this.start, this.direction, this.onEnter, this.onLeave, this.onFail, depth);
      }
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\"\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\u0007\u001a\u0004\u0018\u00010\u0003H&R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\b"},
      d2 = {"Lkotlin/io/FileTreeWalk$WalkState;", "", "root", "Ljava/io/File;", "(Ljava/io/File;)V", "getRoot", "()Ljava/io/File;", "step", "kotlin-stdlib"}
   )
   private abstract static class WalkState {
      @NotNull
      private final File root;

      public WalkState(@NotNull File root) {
         Intrinsics.checkNotNullParameter(root, "root");
         super();
         this.root = root;
      }

      @NotNull
      public final File getRoot() {
         return this.root;
      }

      @Nullable
      public abstract File step();
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\"\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"},
      d2 = {"Lkotlin/io/FileTreeWalk$DirectoryState;", "Lkotlin/io/FileTreeWalk$WalkState;", "rootDir", "Ljava/io/File;", "(Ljava/io/File;)V", "kotlin-stdlib"}
   )
   private abstract static class DirectoryState extends WalkState {
      public DirectoryState(@NotNull File rootDir) {
         Intrinsics.checkNotNullParameter(rootDir, "rootDir");
         super(rootDir);
         if (_Assertions.ENABLED) {
            boolean var2 = rootDir.isDirectory();
            if (_Assertions.ENABLED && !var2) {
               int var3 = 0;
               String var4 = "rootDir must be verified to be directory beforehand.";
               throw new AssertionError(var4);
            }
         }

      }
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0082\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0003\r\u000e\u000fB\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0007\u001a\u00020\bH\u0014J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0002J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0002H\u0082\u0010R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"},
      d2 = {"Lkotlin/io/FileTreeWalk$FileTreeWalkIterator;", "Lkotlin/collections/AbstractIterator;", "Ljava/io/File;", "(Lkotlin/io/FileTreeWalk;)V", "state", "Ljava/util/ArrayDeque;", "Lkotlin/io/FileTreeWalk$WalkState;", "computeNext", "", "directoryState", "Lkotlin/io/FileTreeWalk$DirectoryState;", "root", "gotoNext", "BottomUpDirectoryState", "SingleFileState", "TopDownDirectoryState", "kotlin-stdlib"}
   )
   private final class FileTreeWalkIterator extends AbstractIterator {
      @NotNull
      private final ArrayDeque state;

      public FileTreeWalkIterator() {
         Intrinsics.checkNotNullParameter(FileTreeWalk.this, "this$0");
         super();
         this.state = new ArrayDeque();
         if (FileTreeWalk.this.start.isDirectory()) {
            this.state.push(this.directoryState(FileTreeWalk.this.start));
         } else if (FileTreeWalk.this.start.isFile()) {
            this.state.push(new SingleFileState(FileTreeWalk.this.start));
         } else {
            this.done();
         }

      }

      protected void computeNext() {
         File nextFile = this.gotoNext();
         if (nextFile != null) {
            this.setNext(nextFile);
         } else {
            this.done();
         }

      }

      private final DirectoryState directoryState(File root) {
         FileWalkDirection var2 = FileTreeWalk.this.direction;
         int var3 = FileTreeWalk.FileTreeWalkIterator.WhenMappings.$EnumSwitchMapping$0[var2.ordinal()];
         DirectoryState var10000;
         switch (var3) {
            case 1:
               var10000 = new TopDownDirectoryState(root);
               break;
            case 2:
               var10000 = new BottomUpDirectoryState(root);
               break;
            default:
               throw new NoWhenBranchMatchedException();
         }

         return var10000;
      }

      private final File gotoNext() {
         FileTreeWalkIterator var1 = this;

         while(true) {
            WalkState var4 = (WalkState)var1.state.peek();
            if (var4 == null) {
               return null;
            }

            WalkState topState = var4;
            File file = var4.step();
            if (file != null) {
               if (Intrinsics.areEqual((Object)file, (Object)topState.getRoot()) || !file.isDirectory() || var1.state.size() >= FileTreeWalk.this.maxDepth) {
                  return file;
               }

               var1.state.push(var1.directoryState(file));
               var1 = var1;
            } else {
               var1.state.pop();
               var1 = var1;
            }
         }
      }

      @Metadata(
         mv = {1, 6, 0},
         k = 1,
         xi = 48,
         d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\r\u001a\u0004\u0018\u00010\u0003H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\nX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u000bR\u000e\u0010\f\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"},
         d2 = {"Lkotlin/io/FileTreeWalk$FileTreeWalkIterator$BottomUpDirectoryState;", "Lkotlin/io/FileTreeWalk$DirectoryState;", "rootDir", "Ljava/io/File;", "(Lkotlin/io/FileTreeWalk$FileTreeWalkIterator;Ljava/io/File;)V", "failed", "", "fileIndex", "", "fileList", "", "[Ljava/io/File;", "rootVisited", "step", "kotlin-stdlib"}
      )
      private final class BottomUpDirectoryState extends DirectoryState {
         private boolean rootVisited;
         @Nullable
         private File[] fileList;
         private int fileIndex;
         private boolean failed;

         public BottomUpDirectoryState(@NotNull File rootDir) {
            Intrinsics.checkNotNullParameter(FileTreeWalkIterator.this, "this$0");
            Intrinsics.checkNotNullParameter(rootDir, "rootDir");
            super(rootDir);
         }

         @Nullable
         public File step() {
            if (!this.failed && this.fileList == null) {
               Function1 var1 = FileTreeWalk.this.onEnter;
               if (var1 == null ? false : !(Boolean)var1.invoke(this.getRoot())) {
                  return null;
               }

               this.fileList = this.getRoot().listFiles();
               if (this.fileList == null) {
                  Function2 var3 = FileTreeWalk.this.onFail;
                  if (var3 != null) {
                     var3.invoke(this.getRoot(), new AccessDeniedException(this.getRoot(), (File)null, "Cannot list files in a directory", 2, (DefaultConstructorMarker)null));
                  }

                  this.failed = true;
               }
            }

            if (this.fileList != null) {
               int var10000 = this.fileIndex;
               File[] var10001 = this.fileList;
               Intrinsics.checkNotNull(var10001);
               if (var10000 < var10001.length) {
                  File[] var5 = this.fileList;
                  Intrinsics.checkNotNull(var5);
                  int var2 = this.fileIndex++;
                  return var5[var2];
               }
            }

            if (!this.rootVisited) {
               this.rootVisited = true;
               return this.getRoot();
            } else {
               Function1 var4 = FileTreeWalk.this.onLeave;
               if (var4 != null) {
                  var4.invoke(this.getRoot());
               }

               return null;
            }
         }
      }

      @Metadata(
         mv = {1, 6, 0},
         k = 1,
         xi = 48,
         d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\bX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\r"},
         d2 = {"Lkotlin/io/FileTreeWalk$FileTreeWalkIterator$TopDownDirectoryState;", "Lkotlin/io/FileTreeWalk$DirectoryState;", "rootDir", "Ljava/io/File;", "(Lkotlin/io/FileTreeWalk$FileTreeWalkIterator;Ljava/io/File;)V", "fileIndex", "", "fileList", "", "[Ljava/io/File;", "rootVisited", "", "step", "kotlin-stdlib"}
      )
      private final class TopDownDirectoryState extends DirectoryState {
         private boolean rootVisited;
         @Nullable
         private File[] fileList;
         private int fileIndex;

         public TopDownDirectoryState(@NotNull File rootDir) {
            Intrinsics.checkNotNullParameter(FileTreeWalkIterator.this, "this$0");
            Intrinsics.checkNotNullParameter(rootDir, "rootDir");
            super(rootDir);
         }

         @Nullable
         public File step() {
            if (!this.rootVisited) {
               Function1 var5 = FileTreeWalk.this.onEnter;
               if (var5 == null ? false : !(Boolean)var5.invoke(this.getRoot())) {
                  return null;
               } else {
                  this.rootVisited = true;
                  return this.getRoot();
               }
            } else {
               if (this.fileList != null) {
                  int var10000 = this.fileIndex;
                  File[] var10001 = this.fileList;
                  Intrinsics.checkNotNull(var10001);
                  if (var10000 >= var10001.length) {
                     Function1 var4 = FileTreeWalk.this.onLeave;
                     if (var4 != null) {
                        var4.invoke(this.getRoot());
                     }

                     return null;
                  }
               }

               if (this.fileList == null) {
                  label62: {
                     this.fileList = this.getRoot().listFiles();
                     if (this.fileList == null) {
                        Function2 var1 = FileTreeWalk.this.onFail;
                        if (var1 != null) {
                           var1.invoke(this.getRoot(), new AccessDeniedException(this.getRoot(), (File)null, "Cannot list files in a directory", 2, (DefaultConstructorMarker)null));
                        }
                     }

                     if (this.fileList != null) {
                        File[] var6 = this.fileList;
                        Intrinsics.checkNotNull(var6);
                        if (var6.length != 0) {
                           break label62;
                        }
                     }

                     Function1 var3 = FileTreeWalk.this.onLeave;
                     if (var3 != null) {
                        var3.invoke(this.getRoot());
                     }

                     return null;
                  }
               }

               File[] var7 = this.fileList;
               Intrinsics.checkNotNull(var7);
               int var2 = this.fileIndex++;
               return var7[var2];
            }
         }
      }

      @Metadata(
         mv = {1, 6, 0},
         k = 1,
         xi = 48,
         d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\b"},
         d2 = {"Lkotlin/io/FileTreeWalk$FileTreeWalkIterator$SingleFileState;", "Lkotlin/io/FileTreeWalk$WalkState;", "rootFile", "Ljava/io/File;", "(Lkotlin/io/FileTreeWalk$FileTreeWalkIterator;Ljava/io/File;)V", "visited", "", "step", "kotlin-stdlib"}
      )
      private final class SingleFileState extends WalkState {
         private boolean visited;

         public SingleFileState(@NotNull File rootFile) {
            Intrinsics.checkNotNullParameter(FileTreeWalkIterator.this, "this$0");
            Intrinsics.checkNotNullParameter(rootFile, "rootFile");
            super(rootFile);
            if (_Assertions.ENABLED) {
               boolean var3 = rootFile.isFile();
               if (_Assertions.ENABLED && !var3) {
                  int var4 = 0;
                  String var5 = "rootFile must be verified to be file beforehand.";
                  throw new AssertionError(var5);
               }
            }

         }

         @Nullable
         public File step() {
            if (this.visited) {
               return null;
            } else {
               this.visited = true;
               return this.getRoot();
            }
         }
      }

      // $FF: synthetic class
      @Metadata(
         mv = {1, 6, 0},
         k = 3,
         xi = 48
      )
      public class WhenMappings {
         // $FF: synthetic field
         public static final int[] $EnumSwitchMapping$0;

         static {
            int[] var0 = new int[FileWalkDirection.values().length];
            var0[FileWalkDirection.TOP_DOWN.ordinal()] = 1;
            var0[FileWalkDirection.BOTTOM_UP.ordinal()] = 2;
            $EnumSwitchMapping$0 = var0;
         }
      }
   }
}
