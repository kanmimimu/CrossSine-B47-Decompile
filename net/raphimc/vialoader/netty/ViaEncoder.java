package net.raphimc.vialoader.netty;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelEncoderException;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

public class ViaEncoder extends MessageToMessageEncoder {
   protected final UserConnection user;

   public ViaEncoder(UserConnection user) {
      this.user = user;
   }

   protected void encode(ChannelHandlerContext ctx, ByteBuf byteBuf, List out) throws Exception {
      if (!this.user.checkOutgoingPacket()) {
         throw CancelEncoderException.generate((Throwable)null);
      } else if (!this.user.shouldTransformPacket()) {
         out.add(byteBuf.retain());
      } else {
         ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(byteBuf);

         try {
            this.user.transformOutgoing(transformedBuf, CancelEncoderException::generate);
            out.add(transformedBuf.retain());
         } finally {
            transformedBuf.release();
         }

      }
   }

   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
      try {
         super.write(ctx, msg, promise);
      } catch (Throwable e) {
         if (!PipelineUtil.containsCause(e, CancelCodecException.class)) {
            throw e;
         }

         promise.setSuccess();
      }

   }

   public boolean isSharable() {
      return this.user != null;
   }
}
