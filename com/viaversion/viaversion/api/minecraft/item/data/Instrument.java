package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.minecraft.Holder;
import com.viaversion.viaversion.api.minecraft.SoundEvent;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.HolderType;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class Instrument {
   final Holder soundEvent;
   final int useDuration;
   final float range;
   public static final HolderType TYPE = new HolderType() {
      public Instrument readDirect(ByteBuf buffer) {
         Holder<SoundEvent> soundEvent = Types.SOUND_EVENT.read(buffer);
         int useDuration = Types.VAR_INT.readPrimitive(buffer);
         float range = buffer.readFloat();
         return new Instrument(soundEvent, useDuration, range);
      }

      public void writeDirect(ByteBuf buffer, Instrument value) {
         Types.SOUND_EVENT.write(buffer, value.soundEvent());
         Types.VAR_INT.writePrimitive(buffer, value.useDuration());
         buffer.writeFloat(value.range());
      }
   };

   public Instrument(Holder soundEvent, int useDuration, float range) {
      this.soundEvent = soundEvent;
      this.useDuration = useDuration;
      this.range = range;
   }

   public Instrument rewrite(Int2IntFunction soundIdRewriteFunction) {
      Holder<SoundEvent> soundEvent = this.soundEvent.updateId(soundIdRewriteFunction);
      return soundEvent == this.soundEvent ? this : new Instrument(soundEvent, this.useDuration, this.range);
   }

   public Holder soundEvent() {
      return this.soundEvent;
   }

   public int useDuration() {
      return this.useDuration;
   }

   public float range() {
      return this.range;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof Instrument)) {
         return false;
      } else {
         Instrument var2 = (Instrument)var1;
         return Objects.equals(this.soundEvent, var2.soundEvent) && this.useDuration == var2.useDuration && Float.compare(this.range, var2.range) == 0;
      }
   }

   public int hashCode() {
      return ((0 * 31 + Objects.hashCode(this.soundEvent)) * 31 + Integer.hashCode(this.useDuration)) * 31 + Float.hashCode(this.range);
   }

   public String toString() {
      return String.format("%s[soundEvent=%s, useDuration=%s, range=%s]", this.getClass().getSimpleName(), Objects.toString(this.soundEvent), Integer.toString(this.useDuration), Float.toString(this.range));
   }
}
