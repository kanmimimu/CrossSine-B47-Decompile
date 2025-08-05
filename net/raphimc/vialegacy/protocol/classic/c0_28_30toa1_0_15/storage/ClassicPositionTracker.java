package net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import net.raphimc.vialegacy.api.model.ChunkCoord;

public class ClassicPositionTracker implements StorableObject {
   public double posX;
   public double stance;
   public double posZ;
   public float yaw;
   public float pitch;
   public boolean spawned;

   public void writeToPacket(PacketWrapper wrapper) {
      int x = (int)(this.posX * (double)32.0F);
      int y = (int)(this.stance * (double)32.0F);
      int z = (int)(this.posZ * (double)32.0F);
      int yaw = (int)(this.yaw * 256.0F / 360.0F) & 255;
      int pitch = (int)(this.pitch * 256.0F / 360.0F) & 255;
      wrapper.write(Types.BYTE, -1);
      wrapper.write(Types.SHORT, (short)x);
      wrapper.write(Types.SHORT, (short)y);
      wrapper.write(Types.SHORT, (short)z);
      wrapper.write(Types.BYTE, (byte)(yaw - 128));
      wrapper.write(Types.BYTE, (byte)pitch);
   }

   public BlockPosition getBlockPosition() {
      return new BlockPosition(floor(this.posX), floor(this.stance), floor(this.posZ));
   }

   public ChunkCoord getChunkPosition() {
      BlockPosition pos = this.getBlockPosition();
      return new ChunkCoord(pos.x() >> 4, pos.z() >> 4);
   }

   private static int floor(double f) {
      int i = (int)f;
      return f < (double)i ? i - 1 : i;
   }
}
