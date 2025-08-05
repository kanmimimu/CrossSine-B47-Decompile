package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.minecraft.Holder;
import com.viaversion.viaversion.api.minecraft.SoundEvent;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.HolderType;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;
import com.viaversion.viaversion.util.Either;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class JukeboxPlayable {
   final Either song;
   final boolean showInTooltip;
   public static final Type TYPE = new Type(JukeboxPlayable.class) {
      public JukeboxPlayable read(ByteBuf buffer) {
         Either<Holder<JukeboxSong>, String> position = Type.readEither(buffer, JukeboxPlayable.JukeboxSong.TYPE, Types.STRING);
         boolean showInTooltip = buffer.readBoolean();
         return new JukeboxPlayable(position, showInTooltip);
      }

      public void write(ByteBuf buffer, JukeboxPlayable value) {
         Type.writeEither(buffer, value.song, JukeboxPlayable.JukeboxSong.TYPE, Types.STRING);
         buffer.writeBoolean(value.showInTooltip);
      }
   };

   public JukeboxPlayable(Holder song, boolean showInTooltip) {
      this(Either.left(song), showInTooltip);
   }

   public JukeboxPlayable(String resourceKey, boolean showInTooltip) {
      this(Either.right(resourceKey), showInTooltip);
   }

   public JukeboxPlayable(Either song, boolean showInTooltip) {
      this.song = song;
      this.showInTooltip = showInTooltip;
   }

   public JukeboxPlayable rewrite(Int2IntFunction soundIdRewriteFunction) {
      if (this.song.isRight()) {
         return this;
      } else {
         Holder<JukeboxSong> songHolder = (Holder)this.song.left();
         if (songHolder.hasId()) {
            return this;
         } else {
            JukeboxSong rewrittenSong = ((JukeboxSong)songHolder.value()).rewrite(soundIdRewriteFunction);
            return rewrittenSong == songHolder.value() ? this : new JukeboxPlayable(Holder.of(rewrittenSong), this.showInTooltip);
         }
      }
   }

   public Either song() {
      return this.song;
   }

   public boolean showInTooltip() {
      return this.showInTooltip;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof JukeboxPlayable)) {
         return false;
      } else {
         JukeboxPlayable var2 = (JukeboxPlayable)var1;
         return Objects.equals(this.song, var2.song) && this.showInTooltip == var2.showInTooltip;
      }
   }

   public int hashCode() {
      return (0 * 31 + Objects.hashCode(this.song)) * 31 + Boolean.hashCode(this.showInTooltip);
   }

   public String toString() {
      return String.format("%s[song=%s, showInTooltip=%s]", this.getClass().getSimpleName(), Objects.toString(this.song), Boolean.toString(this.showInTooltip));
   }

   public static final class JukeboxSong {
      final Holder soundEvent;
      final Tag description;
      final float lengthInSeconds;
      final int comparatorOutput;
      public static final HolderType TYPE = new HolderType() {
         public JukeboxSong readDirect(ByteBuf buffer) {
            Holder<SoundEvent> soundEvent = Types.SOUND_EVENT.read(buffer);
            Tag description = (Tag)Types.TAG.read(buffer);
            float lengthInSeconds = buffer.readFloat();
            int useDuration = Types.VAR_INT.readPrimitive(buffer);
            return new JukeboxSong(soundEvent, description, lengthInSeconds, useDuration);
         }

         public void writeDirect(ByteBuf buffer, JukeboxSong value) {
            Types.SOUND_EVENT.write(buffer, value.soundEvent);
            Types.TAG.write(buffer, value.description);
            buffer.writeFloat(value.lengthInSeconds);
            Types.VAR_INT.writePrimitive(buffer, value.comparatorOutput);
         }
      };

      public JukeboxSong(Holder soundEvent, Tag description, float lengthInSeconds, int comparatorOutput) {
         this.soundEvent = soundEvent;
         this.description = description;
         this.lengthInSeconds = lengthInSeconds;
         this.comparatorOutput = comparatorOutput;
      }

      public JukeboxSong rewrite(Int2IntFunction soundIdRewriteFunction) {
         Holder<SoundEvent> soundEvent = this.soundEvent.updateId(soundIdRewriteFunction);
         return soundEvent == this.soundEvent ? this : new JukeboxSong(soundEvent, this.description, this.lengthInSeconds, this.comparatorOutput);
      }

      public Holder soundEvent() {
         return this.soundEvent;
      }

      public Tag description() {
         return this.description;
      }

      public float lengthInSeconds() {
         return this.lengthInSeconds;
      }

      public int comparatorOutput() {
         return this.comparatorOutput;
      }

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof JukeboxSong)) {
            return false;
         } else {
            JukeboxSong var2 = (JukeboxSong)var1;
            return Objects.equals(this.soundEvent, var2.soundEvent) && Objects.equals(this.description, var2.description) && Float.compare(this.lengthInSeconds, var2.lengthInSeconds) == 0 && this.comparatorOutput == var2.comparatorOutput;
         }
      }

      public int hashCode() {
         return (((0 * 31 + Objects.hashCode(this.soundEvent)) * 31 + Objects.hashCode(this.description)) * 31 + Float.hashCode(this.lengthInSeconds)) * 31 + Integer.hashCode(this.comparatorOutput);
      }

      public String toString() {
         return String.format("%s[soundEvent=%s, description=%s, lengthInSeconds=%s, comparatorOutput=%s]", this.getClass().getSimpleName(), Objects.toString(this.soundEvent), Objects.toString(this.description), Float.toString(this.lengthInSeconds), Integer.toString(this.comparatorOutput));
      }
   }
}
