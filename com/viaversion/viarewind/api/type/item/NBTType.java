package com.viaversion.viarewind.api.type.item;

import com.viaversion.nbt.io.NBTIO;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class NBTType extends Type {
   public NBTType() {
      super(CompoundTag.class);
   }

   public CompoundTag read(ByteBuf buffer) {
      short length = buffer.readShort();
      if (length <= 0) {
         return null;
      } else {
         ByteBuf compressed = buffer.readSlice(length);

         try {
            GZIPInputStream gzipStream = new GZIPInputStream(new ByteBufInputStream(compressed));

            CompoundTag var5;
            try {
               var5 = (CompoundTag)NBTIO.reader(CompoundTag.class).named().read((InputStream)gzipStream);
            } catch (Throwable var8) {
               try {
                  gzipStream.close();
               } catch (Throwable var7) {
                  var8.addSuppressed(var7);
               }

               throw var8;
            }

            gzipStream.close();
            return var5;
         } catch (IOException e) {
            throw new RuntimeException(e);
         }
      }
   }

   public void write(ByteBuf buffer, CompoundTag nbt) {
      if (nbt == null) {
         buffer.writeShort(-1);
      } else {
         ByteBuf compressedBuf = buffer.alloc().buffer();

         try {
            try {
               GZIPOutputStream gzipStream = new GZIPOutputStream(new ByteBufOutputStream(compressedBuf));

               try {
                  NBTIO.writer().named().write((OutputStream)gzipStream, nbt);
               } catch (Throwable var13) {
                  try {
                     gzipStream.close();
                  } catch (Throwable var12) {
                     var13.addSuppressed(var12);
                  }

                  throw var13;
               }

               gzipStream.close();
            } catch (IOException e) {
               throw new RuntimeException(e);
            }

            buffer.writeShort(compressedBuf.readableBytes());
            buffer.writeBytes(compressedBuf);
         } finally {
            compressedBuf.release();
         }

      }
   }
}
