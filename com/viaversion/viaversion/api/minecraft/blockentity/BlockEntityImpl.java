package com.viaversion.viaversion.api.minecraft.blockentity;

import com.viaversion.nbt.tag.CompoundTag;
import java.util.Objects;

public final class BlockEntityImpl implements BlockEntity {
   private final byte packedXZ;
   private final short y;
   private final int typeId;
   private final CompoundTag tag;

   public BlockEntityImpl(byte packedXZ, short y, int typeId, CompoundTag tag) {
      this.packedXZ = packedXZ;
      this.y = y;
      this.typeId = typeId;
      this.tag = tag;
   }

   public BlockEntity withTypeId(int typeId) {
      return new BlockEntityImpl(this.packedXZ, this.y, typeId, this.tag);
   }

   public byte packedXZ() {
      return this.packedXZ;
   }

   public short y() {
      return this.y;
   }

   public int typeId() {
      return this.typeId;
   }

   public CompoundTag tag() {
      return this.tag;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof BlockEntityImpl)) {
         return false;
      } else {
         BlockEntityImpl var2 = (BlockEntityImpl)var1;
         return this.packedXZ == var2.packedXZ && this.y == var2.y && this.typeId == var2.typeId && Objects.equals(this.tag, var2.tag);
      }
   }

   public int hashCode() {
      return (((0 * 31 + Byte.hashCode(this.packedXZ)) * 31 + Short.hashCode(this.y)) * 31 + Integer.hashCode(this.typeId)) * 31 + Objects.hashCode(this.tag);
   }

   public String toString() {
      return String.format("%s[packedXZ=%s, y=%s, typeId=%s, tag=%s]", this.getClass().getSimpleName(), Byte.toString(this.packedXZ), Short.toString(this.y), Integer.toString(this.typeId), Objects.toString(this.tag));
   }
}
