package kotlin.io.path;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.UserPrincipal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.Unit;
import kotlin.WasExperimental;
import kotlin.collections.CollectionsKt;
import kotlin.internal.InlineOnly;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 5,
   xi = 49,
   d1 = {"\u0000²\u0001\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0011\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a*\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u00012\u0012\u0010\u0019\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u001a\"\u00020\u0001H\u0087\b¢\u0006\u0002\u0010\u001b\u001a?\u0010\u001c\u001a\u00020\u00022\b\u0010\u001d\u001a\u0004\u0018\u00010\u00022\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00012\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0007¢\u0006\u0002\u0010!\u001a6\u0010\u001c\u001a\u00020\u00022\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00012\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0087\b¢\u0006\u0002\u0010\"\u001aK\u0010#\u001a\u00020\u00022\b\u0010\u001d\u001a\u0004\u0018\u00010\u00022\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\u00012\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0007¢\u0006\u0002\u0010%\u001aB\u0010#\u001a\u00020\u00022\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\u00012\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0087\b¢\u0006\u0002\u0010&\u001a\u001c\u0010'\u001a\u00020(2\u0006\u0010\u0017\u001a\u00020\u00022\n\u0010)\u001a\u0006\u0012\u0002\b\u00030*H\u0001\u001a\r\u0010+\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\r\u0010,\u001a\u00020\u0001*\u00020\u0002H\u0087\b\u001a.\u0010-\u001a\u00020\u0002*\u00020\u00022\u0006\u0010.\u001a\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u0002000\u001a\"\u000200H\u0087\b¢\u0006\u0002\u00101\u001a\u001f\u0010-\u001a\u00020\u0002*\u00020\u00022\u0006\u0010.\u001a\u00020\u00022\b\b\u0002\u00102\u001a\u000203H\u0087\b\u001a.\u00104\u001a\u00020\u0002*\u00020\u00022\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0087\b¢\u0006\u0002\u00105\u001a.\u00106\u001a\u00020\u0002*\u00020\u00022\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0087\b¢\u0006\u0002\u00105\u001a.\u00107\u001a\u00020\u0002*\u00020\u00022\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0087\b¢\u0006\u0002\u00105\u001a\u0015\u00108\u001a\u00020\u0002*\u00020\u00022\u0006\u0010.\u001a\u00020\u0002H\u0087\b\u001a6\u00109\u001a\u00020\u0002*\u00020\u00022\u0006\u0010.\u001a\u00020\u00022\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0087\b¢\u0006\u0002\u0010:\u001a\r\u0010;\u001a\u00020<*\u00020\u0002H\u0087\b\u001a\r\u0010=\u001a\u000203*\u00020\u0002H\u0087\b\u001a\u0015\u0010>\u001a\u00020\u0002*\u00020\u00022\u0006\u0010?\u001a\u00020\u0002H\u0087\n\u001a\u0015\u0010>\u001a\u00020\u0002*\u00020\u00022\u0006\u0010?\u001a\u00020\u0001H\u0087\n\u001a&\u0010@\u001a\u000203*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\u0087\b¢\u0006\u0002\u0010B\u001a2\u0010C\u001a\u0002HD\"\n\b\u0000\u0010D\u0018\u0001*\u00020E*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\u0087\b¢\u0006\u0002\u0010F\u001a4\u0010G\u001a\u0004\u0018\u0001HD\"\n\b\u0000\u0010D\u0018\u0001*\u00020E*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\u0087\b¢\u0006\u0002\u0010F\u001a\r\u0010H\u001a\u00020I*\u00020\u0002H\u0087\b\u001a\r\u0010J\u001a\u00020K*\u00020\u0002H\u0087\b\u001a.\u0010L\u001a\u00020<*\u00020\u00022\b\b\u0002\u0010M\u001a\u00020\u00012\u0012\u0010N\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020<0OH\u0087\bø\u0001\u0000\u001a0\u0010P\u001a\u0004\u0018\u00010Q*\u00020\u00022\u0006\u0010R\u001a\u00020\u00012\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\u0087\b¢\u0006\u0002\u0010S\u001a&\u0010T\u001a\u00020U*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\u0087\b¢\u0006\u0002\u0010V\u001a(\u0010W\u001a\u0004\u0018\u00010X*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\u0087\b¢\u0006\u0002\u0010Y\u001a,\u0010Z\u001a\b\u0012\u0004\u0012\u00020\\0[*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\u0087\b¢\u0006\u0002\u0010]\u001a&\u0010^\u001a\u000203*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\u0087\b¢\u0006\u0002\u0010B\u001a\r\u0010_\u001a\u000203*\u00020\u0002H\u0087\b\u001a\r\u0010`\u001a\u000203*\u00020\u0002H\u0087\b\u001a\r\u0010a\u001a\u000203*\u00020\u0002H\u0087\b\u001a&\u0010b\u001a\u000203*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\u0087\b¢\u0006\u0002\u0010B\u001a\u0015\u0010c\u001a\u000203*\u00020\u00022\u0006\u0010?\u001a\u00020\u0002H\u0087\b\u001a\r\u0010d\u001a\u000203*\u00020\u0002H\u0087\b\u001a\r\u0010e\u001a\u000203*\u00020\u0002H\u0087\b\u001a\u001c\u0010f\u001a\b\u0012\u0004\u0012\u00020\u00020g*\u00020\u00022\b\b\u0002\u0010M\u001a\u00020\u0001H\u0007\u001a.\u0010h\u001a\u00020\u0002*\u00020\u00022\u0006\u0010.\u001a\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u0002000\u001a\"\u000200H\u0087\b¢\u0006\u0002\u00101\u001a\u001f\u0010h\u001a\u00020\u0002*\u00020\u00022\u0006\u0010.\u001a\u00020\u00022\b\b\u0002\u00102\u001a\u000203H\u0087\b\u001a&\u0010i\u001a\u000203*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\u0087\b¢\u0006\u0002\u0010B\u001a2\u0010j\u001a\u0002Hk\"\n\b\u0000\u0010k\u0018\u0001*\u00020l*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\u0087\b¢\u0006\u0002\u0010m\u001a<\u0010j\u001a\u0010\u0012\u0004\u0012\u00020\u0001\u0012\u0006\u0012\u0004\u0018\u00010Q0n*\u00020\u00022\u0006\u0010\u001f\u001a\u00020\u00012\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\u0087\b¢\u0006\u0002\u0010o\u001a\r\u0010p\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\u0014\u0010q\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0002H\u0007\u001a\u0016\u0010r\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0002H\u0007\u001a\u0014\u0010s\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0002H\u0007\u001a8\u0010t\u001a\u00020\u0002*\u00020\u00022\u0006\u0010R\u001a\u00020\u00012\b\u0010u\u001a\u0004\u0018\u00010Q2\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\u0087\b¢\u0006\u0002\u0010v\u001a\u0015\u0010w\u001a\u00020\u0002*\u00020\u00022\u0006\u0010u\u001a\u00020UH\u0087\b\u001a\u0015\u0010x\u001a\u00020\u0002*\u00020\u00022\u0006\u0010u\u001a\u00020XH\u0087\b\u001a\u001b\u0010y\u001a\u00020\u0002*\u00020\u00022\f\u0010u\u001a\b\u0012\u0004\u0012\u00020\\0[H\u0087\b\u001a\r\u0010z\u001a\u00020\u0002*\u00020{H\u0087\b\u001a@\u0010|\u001a\u0002H}\"\u0004\b\u0000\u0010}*\u00020\u00022\b\b\u0002\u0010M\u001a\u00020\u00012\u0018\u0010~\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u007f\u0012\u0004\u0012\u0002H}0OH\u0087\bø\u0001\u0000¢\u0006\u0003\u0010\u0080\u0001\"\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u001f\u0010\u0007\u001a\u00020\u0001*\u00020\u00028Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\b\u0010\u0004\u001a\u0004\b\t\u0010\u0006\"\u001e\u0010\n\u001a\u00020\u0001*\u00020\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u000b\u0010\u0004\u001a\u0004\b\f\u0010\u0006\"\u001e\u0010\r\u001a\u00020\u0001*\u00020\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u000e\u0010\u0004\u001a\u0004\b\u000f\u0010\u0006\"\u001e\u0010\u0010\u001a\u00020\u0001*\u00020\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0011\u0010\u0004\u001a\u0004\b\u0012\u0010\u0006\"\u001f\u0010\u0013\u001a\u00020\u0001*\u00020\u00028Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u0014\u0010\u0004\u001a\u0004\b\u0015\u0010\u0006\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0081\u0001"},
   d2 = {"extension", "", "Ljava/nio/file/Path;", "getExtension$annotations", "(Ljava/nio/file/Path;)V", "getExtension", "(Ljava/nio/file/Path;)Ljava/lang/String;", "invariantSeparatorsPath", "getInvariantSeparatorsPath$annotations", "getInvariantSeparatorsPath", "invariantSeparatorsPathString", "getInvariantSeparatorsPathString$annotations", "getInvariantSeparatorsPathString", "name", "getName$annotations", "getName", "nameWithoutExtension", "getNameWithoutExtension$annotations", "getNameWithoutExtension", "pathString", "getPathString$annotations", "getPathString", "Path", "path", "base", "subpaths", "", "(Ljava/lang/String;[Ljava/lang/String;)Ljava/nio/file/Path;", "createTempDirectory", "directory", "prefix", "attributes", "Ljava/nio/file/attribute/FileAttribute;", "(Ljava/nio/file/Path;Ljava/lang/String;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "(Ljava/lang/String;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "createTempFile", "suffix", "(Ljava/nio/file/Path;Ljava/lang/String;Ljava/lang/String;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "(Ljava/lang/String;Ljava/lang/String;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "fileAttributeViewNotAvailable", "", "attributeViewClass", "Ljava/lang/Class;", "absolute", "absolutePathString", "copyTo", "target", "options", "Ljava/nio/file/CopyOption;", "(Ljava/nio/file/Path;Ljava/nio/file/Path;[Ljava/nio/file/CopyOption;)Ljava/nio/file/Path;", "overwrite", "", "createDirectories", "(Ljava/nio/file/Path;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "createDirectory", "createFile", "createLinkPointingTo", "createSymbolicLinkPointingTo", "(Ljava/nio/file/Path;Ljava/nio/file/Path;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "deleteExisting", "", "deleteIfExists", "div", "other", "exists", "Ljava/nio/file/LinkOption;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Z", "fileAttributesView", "V", "Ljava/nio/file/attribute/FileAttributeView;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/nio/file/attribute/FileAttributeView;", "fileAttributesViewOrNull", "fileSize", "", "fileStore", "Ljava/nio/file/FileStore;", "forEachDirectoryEntry", "glob", "action", "Lkotlin/Function1;", "getAttribute", "", "attribute", "(Ljava/nio/file/Path;Ljava/lang/String;[Ljava/nio/file/LinkOption;)Ljava/lang/Object;", "getLastModifiedTime", "Ljava/nio/file/attribute/FileTime;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/nio/file/attribute/FileTime;", "getOwner", "Ljava/nio/file/attribute/UserPrincipal;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/nio/file/attribute/UserPrincipal;", "getPosixFilePermissions", "", "Ljava/nio/file/attribute/PosixFilePermission;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/util/Set;", "isDirectory", "isExecutable", "isHidden", "isReadable", "isRegularFile", "isSameFileAs", "isSymbolicLink", "isWritable", "listDirectoryEntries", "", "moveTo", "notExists", "readAttributes", "A", "Ljava/nio/file/attribute/BasicFileAttributes;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/nio/file/attribute/BasicFileAttributes;", "", "(Ljava/nio/file/Path;Ljava/lang/String;[Ljava/nio/file/LinkOption;)Ljava/util/Map;", "readSymbolicLink", "relativeTo", "relativeToOrNull", "relativeToOrSelf", "setAttribute", "value", "(Ljava/nio/file/Path;Ljava/lang/String;Ljava/lang/Object;[Ljava/nio/file/LinkOption;)Ljava/nio/file/Path;", "setLastModifiedTime", "setOwner", "setPosixFilePermissions", "toPath", "Ljava/net/URI;", "useDirectoryEntries", "T", "block", "Lkotlin/sequences/Sequence;", "(Ljava/nio/file/Path;Ljava/lang/String;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-stdlib-jdk7"},
   xs = "kotlin/io/path/PathsKt"
)
class PathsKt__PathUtilsKt extends PathsKt__PathReadWriteKt {
   @NotNull
   public static final String getName(@NotNull Path $this$name) {
      Intrinsics.checkNotNullParameter($this$name, "<this>");
      Path var1 = $this$name.getFileName();
      String var2 = var1 == null ? null : var1.toString();
      return var2 == null ? "" : var2;
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   public static void getName$annotations(Path var0) {
   }

   @NotNull
   public static final String getNameWithoutExtension(@NotNull Path $this$nameWithoutExtension) {
      Intrinsics.checkNotNullParameter($this$nameWithoutExtension, "<this>");
      Path var1 = $this$nameWithoutExtension.getFileName();
      String var10000;
      if (var1 == null) {
         var10000 = "";
      } else {
         String var2 = var1.toString();
         var10000 = StringsKt.substringBeforeLast$default(var2, ".", (String)null, 2, (Object)null);
      }

      return var10000;
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   public static void getNameWithoutExtension$annotations(Path var0) {
   }

   @NotNull
   public static final String getExtension(@NotNull Path $this$extension) {
      Intrinsics.checkNotNullParameter($this$extension, "<this>");
      Path var1 = $this$extension.getFileName();
      String var10000;
      if (var1 == null) {
         var10000 = "";
      } else {
         String var2 = var1.toString();
         var10000 = StringsKt.substringAfterLast(var2, '.', "");
      }

      return var10000;
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   public static void getExtension$annotations(Path var0) {
   }

   private static final String getPathString(Path $this$pathString) {
      Intrinsics.checkNotNullParameter($this$pathString, "<this>");
      return $this$pathString.toString();
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   public static void getPathString$annotations(Path var0) {
   }

   @NotNull
   public static final String getInvariantSeparatorsPathString(@NotNull Path $this$invariantSeparatorsPathString) {
      Intrinsics.checkNotNullParameter($this$invariantSeparatorsPathString, "<this>");
      String separator = $this$invariantSeparatorsPathString.getFileSystem().getSeparator();
      String var2;
      if (!Intrinsics.areEqual((Object)separator, (Object)"/")) {
         var2 = $this$invariantSeparatorsPathString.toString();
         Intrinsics.checkNotNullExpressionValue(separator, "separator");
         var2 = StringsKt.replace$default(var2, separator, "/", false, 4, (Object)null);
      } else {
         var2 = $this$invariantSeparatorsPathString.toString();
      }

      return var2;
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   public static void getInvariantSeparatorsPathString$annotations(Path var0) {
   }

   /** @deprecated */
   private static final String getInvariantSeparatorsPath(Path $this$invariantSeparatorsPath) {
      Intrinsics.checkNotNullParameter($this$invariantSeparatorsPath, "<this>");
      return PathsKt.getInvariantSeparatorsPathString($this$invariantSeparatorsPath);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use invariantSeparatorsPathString property instead.",
      replaceWith = @ReplaceWith(
   expression = "invariantSeparatorsPathString",
   imports = {}
),
      level = DeprecationLevel.ERROR
   )
   @SinceKotlin(
      version = "1.4"
   )
   @ExperimentalPathApi
   @InlineOnly
   public static void getInvariantSeparatorsPath$annotations(Path var0) {
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Path absolute(Path $this$absolute) {
      Intrinsics.checkNotNullParameter($this$absolute, "<this>");
      Path var1 = $this$absolute.toAbsolutePath();
      Intrinsics.checkNotNullExpressionValue(var1, "toAbsolutePath()");
      return var1;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final String absolutePathString(Path $this$absolutePathString) {
      Intrinsics.checkNotNullParameter($this$absolutePathString, "<this>");
      return $this$absolutePathString.toAbsolutePath().toString();
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @NotNull
   public static final Path relativeTo(@NotNull Path $this$relativeTo, @NotNull Path base) {
      Intrinsics.checkNotNullParameter($this$relativeTo, "<this>");
      Intrinsics.checkNotNullParameter(base, "base");

      try {
         Path var2 = PathRelativizer.INSTANCE.tryRelativeTo($this$relativeTo, base);
         return var2;
      } catch (IllegalArgumentException e) {
         throw new IllegalArgumentException(e.getMessage() + "\nthis path: " + $this$relativeTo + "\nbase path: " + base, (Throwable)e);
      }
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @NotNull
   public static final Path relativeToOrSelf(@NotNull Path $this$relativeToOrSelf, @NotNull Path base) {
      Intrinsics.checkNotNullParameter($this$relativeToOrSelf, "<this>");
      Intrinsics.checkNotNullParameter(base, "base");
      Path var2 = PathsKt.relativeToOrNull($this$relativeToOrSelf, base);
      return var2 == null ? $this$relativeToOrSelf : var2;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @Nullable
   public static final Path relativeToOrNull(@NotNull Path $this$relativeToOrNull, @NotNull Path base) {
      Intrinsics.checkNotNullParameter($this$relativeToOrNull, "<this>");
      Intrinsics.checkNotNullParameter(base, "base");

      Path var2;
      try {
         var2 = PathRelativizer.INSTANCE.tryRelativeTo($this$relativeToOrNull, base);
      } catch (IllegalArgumentException var4) {
         var2 = (Path)null;
      }

      return var2;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Path copyTo(Path $this$copyTo, Path target, boolean overwrite) throws IOException {
      Intrinsics.checkNotNullParameter($this$copyTo, "<this>");
      Intrinsics.checkNotNullParameter(target, "target");
      CopyOption[] var10000;
      if (overwrite) {
         CopyOption[] var4 = new CopyOption[]{(CopyOption)StandardCopyOption.REPLACE_EXISTING};
         var10000 = var4;
      } else {
         int $i$f$emptyArray = 0;
         var10000 = new CopyOption[0];
      }

      CopyOption[] options = var10000;
      Path var6 = Files.copy($this$copyTo, target, (CopyOption[])Arrays.copyOf(options, options.length));
      Intrinsics.checkNotNullExpressionValue(var6, "copy(this, target, *options)");
      return var6;
   }

   // $FF: synthetic method
   static Path copyTo$default(Path $this$copyTo_u24default, Path target, boolean overwrite, int options, Object $i$f$emptyArray) throws IOException {
      if ((options & 2) != 0) {
         overwrite = false;
      }

      Intrinsics.checkNotNullParameter($this$copyTo_u24default, "<this>");
      Intrinsics.checkNotNullParameter(target, "target");
      CopyOption[] var10000;
      if (overwrite) {
         CopyOption[] var6 = new CopyOption[]{(CopyOption)StandardCopyOption.REPLACE_EXISTING};
         var10000 = var6;
      } else {
         int $i$f$emptyArray = 0;
         var10000 = new CopyOption[0];
      }

      CopyOption[] options = var10000;
      Path var8 = Files.copy($this$copyTo_u24default, target, (CopyOption[])Arrays.copyOf(options, options.length));
      Intrinsics.checkNotNullExpressionValue(var8, "copy(this, target, *options)");
      return var8;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Path copyTo(Path $this$copyTo, Path target, CopyOption... options) throws IOException {
      Intrinsics.checkNotNullParameter($this$copyTo, "<this>");
      Intrinsics.checkNotNullParameter(target, "target");
      Intrinsics.checkNotNullParameter(options, "options");
      Path var3 = Files.copy($this$copyTo, target, (CopyOption[])Arrays.copyOf(options, options.length));
      Intrinsics.checkNotNullExpressionValue(var3, "copy(this, target, *options)");
      return var3;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final boolean exists(Path $this$exists, LinkOption... options) {
      Intrinsics.checkNotNullParameter($this$exists, "<this>");
      Intrinsics.checkNotNullParameter(options, "options");
      return Files.exists($this$exists, (LinkOption[])Arrays.copyOf(options, options.length));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final boolean notExists(Path $this$notExists, LinkOption... options) {
      Intrinsics.checkNotNullParameter($this$notExists, "<this>");
      Intrinsics.checkNotNullParameter(options, "options");
      return Files.notExists($this$notExists, (LinkOption[])Arrays.copyOf(options, options.length));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final boolean isRegularFile(Path $this$isRegularFile, LinkOption... options) {
      Intrinsics.checkNotNullParameter($this$isRegularFile, "<this>");
      Intrinsics.checkNotNullParameter(options, "options");
      return Files.isRegularFile($this$isRegularFile, (LinkOption[])Arrays.copyOf(options, options.length));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final boolean isDirectory(Path $this$isDirectory, LinkOption... options) {
      Intrinsics.checkNotNullParameter($this$isDirectory, "<this>");
      Intrinsics.checkNotNullParameter(options, "options");
      return Files.isDirectory($this$isDirectory, (LinkOption[])Arrays.copyOf(options, options.length));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final boolean isSymbolicLink(Path $this$isSymbolicLink) {
      Intrinsics.checkNotNullParameter($this$isSymbolicLink, "<this>");
      return Files.isSymbolicLink($this$isSymbolicLink);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final boolean isExecutable(Path $this$isExecutable) {
      Intrinsics.checkNotNullParameter($this$isExecutable, "<this>");
      return Files.isExecutable($this$isExecutable);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final boolean isHidden(Path $this$isHidden) throws IOException {
      Intrinsics.checkNotNullParameter($this$isHidden, "<this>");
      return Files.isHidden($this$isHidden);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final boolean isReadable(Path $this$isReadable) {
      Intrinsics.checkNotNullParameter($this$isReadable, "<this>");
      return Files.isReadable($this$isReadable);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final boolean isWritable(Path $this$isWritable) {
      Intrinsics.checkNotNullParameter($this$isWritable, "<this>");
      return Files.isWritable($this$isWritable);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final boolean isSameFileAs(Path $this$isSameFileAs, Path other) throws IOException {
      Intrinsics.checkNotNullParameter($this$isSameFileAs, "<this>");
      Intrinsics.checkNotNullParameter(other, "other");
      return Files.isSameFile($this$isSameFileAs, other);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @NotNull
   public static final List listDirectoryEntries(@NotNull Path $this$listDirectoryEntries, @NotNull String glob) throws IOException {
      Intrinsics.checkNotNullParameter($this$listDirectoryEntries, "<this>");
      Intrinsics.checkNotNullParameter(glob, "glob");
      Closeable var2 = (Closeable)Files.newDirectoryStream($this$listDirectoryEntries, glob);
      Throwable var3 = null;

      List var10;
      try {
         DirectoryStream it = (DirectoryStream)var2;
         int var5 = 0;
         Intrinsics.checkNotNullExpressionValue(it, "it");
         var10 = CollectionsKt.toList((Iterable)it);
      } catch (Throwable var8) {
         var3 = var8;
         throw var8;
      } finally {
         CloseableKt.closeFinally(var2, var3);
      }

      return var10;
   }

   // $FF: synthetic method
   public static List listDirectoryEntries$default(Path var0, String var1, int var2, Object var3) throws IOException {
      if ((var2 & 1) != 0) {
         var1 = "*";
      }

      return PathsKt.listDirectoryEntries(var0, var1);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Object useDirectoryEntries(Path $this$useDirectoryEntries, String glob, Function1 block) throws IOException {
      Intrinsics.checkNotNullParameter($this$useDirectoryEntries, "<this>");
      Intrinsics.checkNotNullParameter(glob, "glob");
      Intrinsics.checkNotNullParameter(block, "block");
      Closeable var3 = (Closeable)Files.newDirectoryStream($this$useDirectoryEntries, glob);
      Throwable var4 = null;

      Object var11;
      try {
         DirectoryStream it = (DirectoryStream)var3;
         int var6 = 0;
         Intrinsics.checkNotNullExpressionValue(it, "it");
         var11 = block.invoke(CollectionsKt.asSequence((Iterable)it));
      } catch (Throwable var9) {
         var4 = var9;
         throw var9;
      } finally {
         InlineMarker.finallyStart(1);
         CloseableKt.closeFinally(var3, var4);
         InlineMarker.finallyEnd(1);
      }

      return var11;
   }

   // $FF: synthetic method
   static Object useDirectoryEntries$default(Path $this$useDirectoryEntries_u24default, String glob, Function1 block, int var3, Object var4) throws IOException {
      if ((var3 & 1) != 0) {
         glob = "*";
      }

      Intrinsics.checkNotNullParameter($this$useDirectoryEntries_u24default, "<this>");
      Intrinsics.checkNotNullParameter(glob, "glob");
      Intrinsics.checkNotNullParameter(block, "block");
      Closeable var11 = (Closeable)Files.newDirectoryStream($this$useDirectoryEntries_u24default, glob);
      Throwable var12 = null;

      Object var13;
      try {
         DirectoryStream it = (DirectoryStream)var11;
         int var6 = 0;
         Intrinsics.checkNotNullExpressionValue(it, "it");
         var13 = block.invoke(CollectionsKt.asSequence((Iterable)it));
      } catch (Throwable var9) {
         var12 = var9;
         throw var9;
      } finally {
         InlineMarker.finallyStart(1);
         CloseableKt.closeFinally(var11, var12);
         InlineMarker.finallyEnd(1);
      }

      return var13;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final void forEachDirectoryEntry(Path $this$forEachDirectoryEntry, String glob, Function1 action) throws IOException {
      Intrinsics.checkNotNullParameter($this$forEachDirectoryEntry, "<this>");
      Intrinsics.checkNotNullParameter(glob, "glob");
      Intrinsics.checkNotNullParameter(action, "action");
      Closeable var3 = (Closeable)Files.newDirectoryStream($this$forEachDirectoryEntry, glob);
      Throwable var4 = null;

      try {
         DirectoryStream it = (DirectoryStream)var3;
         int var6 = 0;
         Intrinsics.checkNotNullExpressionValue(it, "it");
         Iterable $this$forEach$iv = (Iterable)it;
         int $i$f$forEach = 0;

         for(Object element$iv : $this$forEach$iv) {
            action.invoke(element$iv);
         }

         Unit var15 = Unit.INSTANCE;
      } catch (Throwable var13) {
         var4 = var13;
         throw var13;
      } finally {
         InlineMarker.finallyStart(1);
         CloseableKt.closeFinally(var3, var4);
         InlineMarker.finallyEnd(1);
      }

   }

   // $FF: synthetic method
   static void forEachDirectoryEntry$default(Path $this$forEachDirectoryEntry_u24default, String glob, Function1 action, int var3, Object var4) throws IOException {
      if ((var3 & 1) != 0) {
         glob = "*";
      }

      Intrinsics.checkNotNullParameter($this$forEachDirectoryEntry_u24default, "<this>");
      Intrinsics.checkNotNullParameter(glob, "glob");
      Intrinsics.checkNotNullParameter(action, "action");
      Closeable var15 = (Closeable)Files.newDirectoryStream($this$forEachDirectoryEntry_u24default, glob);
      Throwable var16 = null;

      try {
         DirectoryStream it = (DirectoryStream)var15;
         int var6 = 0;
         Intrinsics.checkNotNullExpressionValue(it, "it");
         Iterable $this$forEach$iv = (Iterable)it;
         int $i$f$forEach = 0;

         for(Object element$iv : $this$forEach$iv) {
            action.invoke(element$iv);
         }

         Unit var17 = Unit.INSTANCE;
      } catch (Throwable var13) {
         var16 = var13;
         throw var13;
      } finally {
         InlineMarker.finallyStart(1);
         CloseableKt.closeFinally(var15, var16);
         InlineMarker.finallyEnd(1);
      }

   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final long fileSize(Path $this$fileSize) throws IOException {
      Intrinsics.checkNotNullParameter($this$fileSize, "<this>");
      return Files.size($this$fileSize);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final void deleteExisting(Path $this$deleteExisting) throws IOException {
      Intrinsics.checkNotNullParameter($this$deleteExisting, "<this>");
      Files.delete($this$deleteExisting);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final boolean deleteIfExists(Path $this$deleteIfExists) throws IOException {
      Intrinsics.checkNotNullParameter($this$deleteIfExists, "<this>");
      return Files.deleteIfExists($this$deleteIfExists);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Path createDirectory(Path $this$createDirectory, FileAttribute... attributes) throws IOException {
      Intrinsics.checkNotNullParameter($this$createDirectory, "<this>");
      Intrinsics.checkNotNullParameter(attributes, "attributes");
      Path var2 = Files.createDirectory($this$createDirectory, (FileAttribute[])Arrays.copyOf(attributes, attributes.length));
      Intrinsics.checkNotNullExpressionValue(var2, "createDirectory(this, *attributes)");
      return var2;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Path createDirectories(Path $this$createDirectories, FileAttribute... attributes) throws IOException {
      Intrinsics.checkNotNullParameter($this$createDirectories, "<this>");
      Intrinsics.checkNotNullParameter(attributes, "attributes");
      Path var2 = Files.createDirectories($this$createDirectories, (FileAttribute[])Arrays.copyOf(attributes, attributes.length));
      Intrinsics.checkNotNullExpressionValue(var2, "createDirectories(this, *attributes)");
      return var2;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Path moveTo(Path $this$moveTo, Path target, CopyOption... options) throws IOException {
      Intrinsics.checkNotNullParameter($this$moveTo, "<this>");
      Intrinsics.checkNotNullParameter(target, "target");
      Intrinsics.checkNotNullParameter(options, "options");
      Path var3 = Files.move($this$moveTo, target, (CopyOption[])Arrays.copyOf(options, options.length));
      Intrinsics.checkNotNullExpressionValue(var3, "move(this, target, *options)");
      return var3;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Path moveTo(Path $this$moveTo, Path target, boolean overwrite) throws IOException {
      Intrinsics.checkNotNullParameter($this$moveTo, "<this>");
      Intrinsics.checkNotNullParameter(target, "target");
      CopyOption[] var10000;
      if (overwrite) {
         CopyOption[] var4 = new CopyOption[]{(CopyOption)StandardCopyOption.REPLACE_EXISTING};
         var10000 = var4;
      } else {
         int $i$f$emptyArray = 0;
         var10000 = new CopyOption[0];
      }

      CopyOption[] options = var10000;
      Path var6 = Files.move($this$moveTo, target, (CopyOption[])Arrays.copyOf(options, options.length));
      Intrinsics.checkNotNullExpressionValue(var6, "move(this, target, *options)");
      return var6;
   }

   // $FF: synthetic method
   static Path moveTo$default(Path $this$moveTo_u24default, Path target, boolean overwrite, int options, Object $i$f$emptyArray) throws IOException {
      if ((options & 2) != 0) {
         overwrite = false;
      }

      Intrinsics.checkNotNullParameter($this$moveTo_u24default, "<this>");
      Intrinsics.checkNotNullParameter(target, "target");
      CopyOption[] var10000;
      if (overwrite) {
         CopyOption[] var6 = new CopyOption[]{(CopyOption)StandardCopyOption.REPLACE_EXISTING};
         var10000 = var6;
      } else {
         int $i$f$emptyArray = 0;
         var10000 = new CopyOption[0];
      }

      CopyOption[] options = var10000;
      Path var8 = Files.move($this$moveTo_u24default, target, (CopyOption[])Arrays.copyOf(options, options.length));
      Intrinsics.checkNotNullExpressionValue(var8, "move(this, target, *options)");
      return var8;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final FileStore fileStore(Path $this$fileStore) throws IOException {
      Intrinsics.checkNotNullParameter($this$fileStore, "<this>");
      FileStore var1 = Files.getFileStore($this$fileStore);
      Intrinsics.checkNotNullExpressionValue(var1, "getFileStore(this)");
      return var1;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Object getAttribute(Path $this$getAttribute, String attribute, LinkOption... options) throws IOException {
      Intrinsics.checkNotNullParameter($this$getAttribute, "<this>");
      Intrinsics.checkNotNullParameter(attribute, "attribute");
      Intrinsics.checkNotNullParameter(options, "options");
      return Files.getAttribute($this$getAttribute, attribute, (LinkOption[])Arrays.copyOf(options, options.length));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Path setAttribute(Path $this$setAttribute, String attribute, Object value, LinkOption... options) throws IOException {
      Intrinsics.checkNotNullParameter($this$setAttribute, "<this>");
      Intrinsics.checkNotNullParameter(attribute, "attribute");
      Intrinsics.checkNotNullParameter(options, "options");
      Path var4 = Files.setAttribute($this$setAttribute, attribute, value, (LinkOption[])Arrays.copyOf(options, options.length));
      Intrinsics.checkNotNullExpressionValue(var4, "setAttribute(this, attribute, value, *options)");
      return var4;
   }

   // $FF: synthetic method
   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final FileAttributeView fileAttributesViewOrNull(Path $this$fileAttributesViewOrNull, LinkOption... options) {
      Intrinsics.checkNotNullParameter($this$fileAttributesViewOrNull, "<this>");
      Intrinsics.checkNotNullParameter(options, "options");
      Intrinsics.reifiedOperationMarker(4, "V");
      return Files.getFileAttributeView($this$fileAttributesViewOrNull, FileAttributeView.class, (LinkOption[])Arrays.copyOf(options, options.length));
   }

   // $FF: synthetic method
   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final FileAttributeView fileAttributesView(Path $this$fileAttributesView, LinkOption... options) {
      Intrinsics.checkNotNullParameter($this$fileAttributesView, "<this>");
      Intrinsics.checkNotNullParameter(options, "options");
      Intrinsics.reifiedOperationMarker(4, "V");
      FileAttributeView var2 = Files.getFileAttributeView($this$fileAttributesView, FileAttributeView.class, (LinkOption[])Arrays.copyOf(options, options.length));
      if (var2 == null) {
         Intrinsics.reifiedOperationMarker(4, "V");
         PathsKt.fileAttributeViewNotAvailable($this$fileAttributesView, FileAttributeView.class);
         throw new KotlinNothingValueException();
      } else {
         return var2;
      }
   }

   @PublishedApi
   @NotNull
   public static final Void fileAttributeViewNotAvailable(@NotNull Path path, @NotNull Class attributeViewClass) {
      Intrinsics.checkNotNullParameter(path, "path");
      Intrinsics.checkNotNullParameter(attributeViewClass, "attributeViewClass");
      throw new UnsupportedOperationException("The desired attribute view type " + attributeViewClass + " is not available for the file " + path + '.');
   }

   // $FF: synthetic method
   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final BasicFileAttributes readAttributes(Path $this$readAttributes, LinkOption... options) throws IOException {
      Intrinsics.checkNotNullParameter($this$readAttributes, "<this>");
      Intrinsics.checkNotNullParameter(options, "options");
      Intrinsics.reifiedOperationMarker(4, "A");
      BasicFileAttributes var2 = Files.readAttributes($this$readAttributes, BasicFileAttributes.class, (LinkOption[])Arrays.copyOf(options, options.length));
      Intrinsics.checkNotNullExpressionValue(var2, "readAttributes(this, A::class.java, *options)");
      return var2;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Map readAttributes(Path $this$readAttributes, String attributes, LinkOption... options) throws IOException {
      Intrinsics.checkNotNullParameter($this$readAttributes, "<this>");
      Intrinsics.checkNotNullParameter(attributes, "attributes");
      Intrinsics.checkNotNullParameter(options, "options");
      Map var3 = Files.readAttributes($this$readAttributes, attributes, (LinkOption[])Arrays.copyOf(options, options.length));
      Intrinsics.checkNotNullExpressionValue(var3, "readAttributes(this, attributes, *options)");
      return var3;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final FileTime getLastModifiedTime(Path $this$getLastModifiedTime, LinkOption... options) throws IOException {
      Intrinsics.checkNotNullParameter($this$getLastModifiedTime, "<this>");
      Intrinsics.checkNotNullParameter(options, "options");
      FileTime var2 = Files.getLastModifiedTime($this$getLastModifiedTime, (LinkOption[])Arrays.copyOf(options, options.length));
      Intrinsics.checkNotNullExpressionValue(var2, "getLastModifiedTime(this, *options)");
      return var2;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Path setLastModifiedTime(Path $this$setLastModifiedTime, FileTime value) throws IOException {
      Intrinsics.checkNotNullParameter($this$setLastModifiedTime, "<this>");
      Intrinsics.checkNotNullParameter(value, "value");
      Path var2 = Files.setLastModifiedTime($this$setLastModifiedTime, value);
      Intrinsics.checkNotNullExpressionValue(var2, "setLastModifiedTime(this, value)");
      return var2;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final UserPrincipal getOwner(Path $this$getOwner, LinkOption... options) throws IOException {
      Intrinsics.checkNotNullParameter($this$getOwner, "<this>");
      Intrinsics.checkNotNullParameter(options, "options");
      return Files.getOwner($this$getOwner, (LinkOption[])Arrays.copyOf(options, options.length));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Path setOwner(Path $this$setOwner, UserPrincipal value) throws IOException {
      Intrinsics.checkNotNullParameter($this$setOwner, "<this>");
      Intrinsics.checkNotNullParameter(value, "value");
      Path var2 = Files.setOwner($this$setOwner, value);
      Intrinsics.checkNotNullExpressionValue(var2, "setOwner(this, value)");
      return var2;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Set getPosixFilePermissions(Path $this$getPosixFilePermissions, LinkOption... options) throws IOException {
      Intrinsics.checkNotNullParameter($this$getPosixFilePermissions, "<this>");
      Intrinsics.checkNotNullParameter(options, "options");
      Set var2 = Files.getPosixFilePermissions($this$getPosixFilePermissions, (LinkOption[])Arrays.copyOf(options, options.length));
      Intrinsics.checkNotNullExpressionValue(var2, "getPosixFilePermissions(this, *options)");
      return var2;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Path setPosixFilePermissions(Path $this$setPosixFilePermissions, Set value) throws IOException {
      Intrinsics.checkNotNullParameter($this$setPosixFilePermissions, "<this>");
      Intrinsics.checkNotNullParameter(value, "value");
      Path var2 = Files.setPosixFilePermissions($this$setPosixFilePermissions, value);
      Intrinsics.checkNotNullExpressionValue(var2, "setPosixFilePermissions(this, value)");
      return var2;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Path createLinkPointingTo(Path $this$createLinkPointingTo, Path target) throws IOException {
      Intrinsics.checkNotNullParameter($this$createLinkPointingTo, "<this>");
      Intrinsics.checkNotNullParameter(target, "target");
      Path var2 = Files.createLink($this$createLinkPointingTo, target);
      Intrinsics.checkNotNullExpressionValue(var2, "createLink(this, target)");
      return var2;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Path createSymbolicLinkPointingTo(Path $this$createSymbolicLinkPointingTo, Path target, FileAttribute... attributes) throws IOException {
      Intrinsics.checkNotNullParameter($this$createSymbolicLinkPointingTo, "<this>");
      Intrinsics.checkNotNullParameter(target, "target");
      Intrinsics.checkNotNullParameter(attributes, "attributes");
      Path var3 = Files.createSymbolicLink($this$createSymbolicLinkPointingTo, target, (FileAttribute[])Arrays.copyOf(attributes, attributes.length));
      Intrinsics.checkNotNullExpressionValue(var3, "createSymbolicLink(this, target, *attributes)");
      return var3;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Path readSymbolicLink(Path $this$readSymbolicLink) throws IOException {
      Intrinsics.checkNotNullParameter($this$readSymbolicLink, "<this>");
      Path var1 = Files.readSymbolicLink($this$readSymbolicLink);
      Intrinsics.checkNotNullExpressionValue(var1, "readSymbolicLink(this)");
      return var1;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Path createFile(Path $this$createFile, FileAttribute... attributes) throws IOException {
      Intrinsics.checkNotNullParameter($this$createFile, "<this>");
      Intrinsics.checkNotNullParameter(attributes, "attributes");
      Path var2 = Files.createFile($this$createFile, (FileAttribute[])Arrays.copyOf(attributes, attributes.length));
      Intrinsics.checkNotNullExpressionValue(var2, "createFile(this, *attributes)");
      return var2;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Path createTempFile(String prefix, String suffix, FileAttribute... attributes) throws IOException {
      Intrinsics.checkNotNullParameter(attributes, "attributes");
      Path var3 = Files.createTempFile(prefix, suffix, (FileAttribute[])Arrays.copyOf(attributes, attributes.length));
      Intrinsics.checkNotNullExpressionValue(var3, "createTempFile(prefix, suffix, *attributes)");
      return var3;
   }

   // $FF: synthetic method
   static Path createTempFile$default(String prefix, String suffix, FileAttribute[] attributes, int var3, Object var4) throws IOException {
      if ((var3 & 1) != 0) {
         prefix = null;
      }

      if ((var3 & 2) != 0) {
         suffix = null;
      }

      Intrinsics.checkNotNullParameter(attributes, "attributes");
      Path var5 = Files.createTempFile(prefix, suffix, (FileAttribute[])Arrays.copyOf(attributes, attributes.length));
      Intrinsics.checkNotNullExpressionValue(var5, "createTempFile(prefix, suffix, *attributes)");
      return var5;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @NotNull
   public static final Path createTempFile(@Nullable Path directory, @Nullable String prefix, @Nullable String suffix, @NotNull FileAttribute... attributes) throws IOException {
      Intrinsics.checkNotNullParameter(attributes, "attributes");
      Path var10000;
      if (directory != null) {
         Path var4 = Files.createTempFile(directory, prefix, suffix, (FileAttribute[])Arrays.copyOf(attributes, attributes.length));
         Intrinsics.checkNotNullExpressionValue(var4, "createTempFile(directory…fix, suffix, *attributes)");
         var10000 = var4;
      } else {
         Path var5 = Files.createTempFile(prefix, suffix, (FileAttribute[])Arrays.copyOf(attributes, attributes.length));
         Intrinsics.checkNotNullExpressionValue(var5, "createTempFile(prefix, suffix, *attributes)");
         var10000 = var5;
      }

      return var10000;
   }

   // $FF: synthetic method
   public static Path createTempFile$default(Path var0, String var1, String var2, FileAttribute[] var3, int var4, Object var5) throws IOException {
      if ((var4 & 2) != 0) {
         var1 = null;
      }

      if ((var4 & 4) != 0) {
         var2 = null;
      }

      return PathsKt.createTempFile(var0, var1, var2, var3);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Path createTempDirectory(String prefix, FileAttribute... attributes) throws IOException {
      Intrinsics.checkNotNullParameter(attributes, "attributes");
      Path var2 = Files.createTempDirectory(prefix, (FileAttribute[])Arrays.copyOf(attributes, attributes.length));
      Intrinsics.checkNotNullExpressionValue(var2, "createTempDirectory(prefix, *attributes)");
      return var2;
   }

   // $FF: synthetic method
   static Path createTempDirectory$default(String prefix, FileAttribute[] attributes, int var2, Object var3) throws IOException {
      if ((var2 & 1) != 0) {
         prefix = null;
      }

      Intrinsics.checkNotNullParameter(attributes, "attributes");
      Path var4 = Files.createTempDirectory(prefix, (FileAttribute[])Arrays.copyOf(attributes, attributes.length));
      Intrinsics.checkNotNullExpressionValue(var4, "createTempDirectory(prefix, *attributes)");
      return var4;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @NotNull
   public static final Path createTempDirectory(@Nullable Path directory, @Nullable String prefix, @NotNull FileAttribute... attributes) throws IOException {
      Intrinsics.checkNotNullParameter(attributes, "attributes");
      Path var10000;
      if (directory != null) {
         Path var3 = Files.createTempDirectory(directory, prefix, (FileAttribute[])Arrays.copyOf(attributes, attributes.length));
         Intrinsics.checkNotNullExpressionValue(var3, "createTempDirectory(dire…ory, prefix, *attributes)");
         var10000 = var3;
      } else {
         Path var4 = Files.createTempDirectory(prefix, (FileAttribute[])Arrays.copyOf(attributes, attributes.length));
         Intrinsics.checkNotNullExpressionValue(var4, "createTempDirectory(prefix, *attributes)");
         var10000 = var4;
      }

      return var10000;
   }

   // $FF: synthetic method
   public static Path createTempDirectory$default(Path var0, String var1, FileAttribute[] var2, int var3, Object var4) throws IOException {
      if ((var3 & 2) != 0) {
         var1 = null;
      }

      return PathsKt.createTempDirectory(var0, var1, var2);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Path div(Path $this$div, Path other) {
      Intrinsics.checkNotNullParameter($this$div, "<this>");
      Intrinsics.checkNotNullParameter(other, "other");
      Path var2 = $this$div.resolve(other);
      Intrinsics.checkNotNullExpressionValue(var2, "this.resolve(other)");
      return var2;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Path div(Path $this$div, String other) {
      Intrinsics.checkNotNullParameter($this$div, "<this>");
      Intrinsics.checkNotNullParameter(other, "other");
      Path var2 = $this$div.resolve(other);
      Intrinsics.checkNotNullExpressionValue(var2, "this.resolve(other)");
      return var2;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Path Path(String path) {
      Intrinsics.checkNotNullParameter(path, "path");
      Path var1 = Paths.get(path);
      Intrinsics.checkNotNullExpressionValue(var1, "get(path)");
      return var1;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Path Path(String base, String... subpaths) {
      Intrinsics.checkNotNullParameter(base, "base");
      Intrinsics.checkNotNullParameter(subpaths, "subpaths");
      Path var2 = Paths.get(base, (String[])Arrays.copyOf(subpaths, subpaths.length));
      Intrinsics.checkNotNullExpressionValue(var2, "get(base, *subpaths)");
      return var2;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalPathApi.class}
   )
   @InlineOnly
   private static final Path toPath(URI $this$toPath) {
      Intrinsics.checkNotNullParameter($this$toPath, "<this>");
      Path var1 = Paths.get($this$toPath);
      Intrinsics.checkNotNullExpressionValue(var1, "get(this)");
      return var1;
   }

   public PathsKt__PathUtilsKt() {
   }
}
