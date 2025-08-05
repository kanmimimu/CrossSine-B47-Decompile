package com.viaversion.viarewind.protocol.v1_8to1_7_6_10.provider.compression;

import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;
import java.util.zip.Inflater;

public class CompressionDecoder extends MessageToMessageDecoder {
   private final Inflater inflater = new Inflater();
   private final int threshold;

   public CompressionDecoder(int threshold) {
      this.threshold = threshold;
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
               temp = ByteBufAllocator.DEFAULT.heapBuffer().writeBytes(in);
            } else {
               in.retain();
            }

            ByteBuf output = ByteBufAllocator.DEFAULT.heapBuffer(outLength, outLength);

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
}
