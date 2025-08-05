package net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types;

import com.viaversion.nbt.io.NBTIO;
import com.viaversion.nbt.limiter.TagLimiter;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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
      if (length < 0) {
         return null;
      } else {
         ByteBuf data = buffer.readSlice(length);

         try {
            InputStream in = new GZIPInputStream(new ByteBufInputStream(data));

            CompoundTag var5;
            try {
               var5 = (CompoundTag)NBTIO.readTag(new DataInputStream(in), TagLimiter.create(2097152, 512), true, CompoundTag.class);
            } catch (Throwable var8) {
               try {
                  in.close();
               } catch (Throwable var7) {
                  var8.addSuppressed(var7);
               }

               throw var8;
            }

            in.close();
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
         ByteBuf data = buffer.alloc().buffer();

         try {
            try {
               OutputStream out = new GZIPOutputStream(new ByteBufOutputStream(data));

               try {
                  NBTIO.writeTag(new DataOutputStream(out), nbt, true);
               } catch (Throwable var13) {
                  try {
                     out.close();
                  } catch (Throwable var12) {
                     var13.addSuppressed(var12);
                  }

                  throw var13;
               }

               out.close();
            } catch (IOException e) {
               throw new RuntimeException(e);
            }

            buffer.writeShort(data.readableBytes());
            buffer.writeBytes(data);
         } finally {
            data.release();
         }

      }
   }
}
