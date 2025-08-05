package net.raphimc.vialoader.netty;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelDecoderException;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;

public class ViaDecoder extends MessageToMessageDecoder {
   protected final UserConnection user;

   public ViaDecoder(UserConnection user) {
      this.user = user;
   }

   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
      if (!this.user.checkIncomingPacket()) {
         throw CancelDecoderException.generate((Throwable)null);
      } else {
         ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(in);

         try {
            if (this.user.shouldTransformPacket()) {
               this.user.transformIncoming(transformedBuf, CancelDecoderException::generate);
            }

            out.add(transformedBuf.retain());
         } finally {
            transformedBuf.release();
         }

      }
   }

   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      try {
         super.channelRead(ctx, msg);
      } catch (Throwable e) {
         if (!PipelineUtil.containsCause(e, CancelCodecException.class)) {
            throw e;
         }
      }

   }

   public boolean isSharable() {
      return this.user != null;
   }
}
