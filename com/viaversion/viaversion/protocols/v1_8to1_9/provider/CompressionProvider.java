package com.viaversion.viaversion.protocols.v1_8to1_9.provider;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.providers.Provider;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class CompressionProvider implements Provider {
   public void handlePlayCompression(UserConnection user, int threshold) {
      if (!user.isClientSide()) {
         throw new IllegalStateException("PLAY state Compression packet is unsupported");
      } else {
         ChannelPipeline pipe = user.getChannel().pipeline();
         if (threshold < 0) {
            if (pipe.get("compress") != null) {
               pipe.remove("compress");
               pipe.remove("decompress");
            }
         } else if (pipe.get("compress") == null) {
            pipe.addBefore(Via.getManager().getInjector().getEncoderName(), "compress", this.getEncoder(threshold));
            pipe.addBefore(Via.getManager().getInjector().getDecoderName(), "decompress", this.getDecoder(threshold));
         } else {
            ((CompressionHandler)pipe.get("compress")).setCompressionThreshold(threshold);
            ((CompressionHandler)pipe.get("decompress")).setCompressionThreshold(threshold);
         }

      }
   }

   protected CompressionHandler getEncoder(int threshold) {
      return new Compressor(threshold);
   }

   protected CompressionHandler getDecoder(int threshold) {
      return new Decompressor(threshold);
   }

   private static class Decompressor extends MessageToMessageDecoder implements CompressionHandler {
      final Inflater inflater;
      int threshold;

      public Decompressor(int var1) {
         this.threshold = var1;
         this.inflater = new Inflater();
      }

      protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
         if (in.isReadable()) {
            int outLength = Types.VAR_INT.readPrimitive(in);
            if (outLength == 0) {
               out.add(in.readBytes(in.readableBytes()));
            } else if (outLength < this.threshold) {
               int var10 = this.threshold;
               throw new DecoderException("Badly compressed packet - size of " + outLength + " is below server threshold of " + var10);
            } else if (outLength > 2097152) {
               throw new DecoderException("Badly compressed packet - size of " + outLength + " is larger than protocol maximum of 2097152");
            } else {
               ByteBuf temp = in;
               if (!in.hasArray()) {
                  temp = ctx.alloc().heapBuffer().writeBytes(in);
               } else {
                  in.retain();
               }

               ByteBuf output = ctx.alloc().heapBuffer(outLength, outLength);

               try {
                  this.inflater.setInput(temp.array(), temp.arrayOffset() + temp.readerIndex(), temp.readableBytes());
                  output.writerIndex(output.writerIndex() + this.inflater.inflate(output.array(), output.arrayOffset(), outLength));
                  out.add(output.retain());
               } finally {
                  output.release();
                  temp.release();
                  this.inflater.reset();
               }

            }
         }
      }

      public void setCompressionThreshold(int threshold) {
         this.threshold = threshold;
      }
   }

   private static class Compressor extends MessageToByteEncoder implements CompressionHandler {
      final Deflater deflater;
      int threshold;

      public Compressor(int var1) {
         this.threshold = var1;
         this.deflater = new Deflater();
      }

      protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
         int frameLength = in.readableBytes();
         if (frameLength < this.threshold) {
            out.writeByte(0);
            out.writeBytes(in);
         } else {
            Types.VAR_INT.writePrimitive(out, frameLength);
            ByteBuf temp = in;
            if (!in.hasArray()) {
               temp = ctx.alloc().heapBuffer().writeBytes(in);
            } else {
               in.retain();
            }

            ByteBuf output = ctx.alloc().heapBuffer();

            try {
               this.deflater.setInput(temp.array(), temp.arrayOffset() + temp.readerIndex(), temp.readableBytes());
               this.deflater.finish();

               while(!this.deflater.finished()) {
                  output.ensureWritable(4096);
                  output.writerIndex(output.writerIndex() + this.deflater.deflate(output.array(), output.arrayOffset() + output.writerIndex(), output.writableBytes()));
               }

               out.writeBytes(output);
            } finally {
               output.release();
               temp.release();
               this.deflater.reset();
            }

         }
      }

      public void setCompressionThreshold(int threshold) {
         this.threshold = threshold;
      }
   }

   public interface CompressionHandler extends ChannelHandler {
      void setCompressionThreshold(int var1);
   }
}
