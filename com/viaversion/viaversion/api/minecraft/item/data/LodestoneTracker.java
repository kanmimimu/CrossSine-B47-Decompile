package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.minecraft.GlobalBlockPosition;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class LodestoneTracker {
   final @Nullable GlobalBlockPosition position;
   final boolean tracked;
   public static final Type TYPE = new Type(LodestoneTracker.class) {
      public LodestoneTracker read(ByteBuf buffer) {
         GlobalBlockPosition position = (GlobalBlockPosition)Types.OPTIONAL_GLOBAL_POSITION.read(buffer);
         boolean tracked = buffer.readBoolean();
         return new LodestoneTracker(position, tracked);
      }

      public void write(ByteBuf buffer, LodestoneTracker value) {
         Types.OPTIONAL_GLOBAL_POSITION.write(buffer, value.position);
         buffer.writeBoolean(value.tracked);
      }
   };

   public LodestoneTracker(@Nullable GlobalBlockPosition position, boolean tracked) {
      this.position = position;
      this.tracked = tracked;
   }

   public @Nullable GlobalBlockPosition position() {
      return this.position;
   }

   public boolean tracked() {
      return this.tracked;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof LodestoneTracker)) {
         return false;
      } else {
         LodestoneTracker var2 = (LodestoneTracker)var1;
         return Objects.equals(this.position, var2.position) && this.tracked == var2.tracked;
      }
   }

   public int hashCode() {
      return (0 * 31 + Objects.hashCode(this.position)) * 31 + Boolean.hashCode(this.tracked);
   }

   public String toString() {
      return String.format("%s[position=%s, tracked=%s]", this.getClass().getSimpleName(), Objects.toString(this.position), Boolean.toString(this.tracked));
   }
}
