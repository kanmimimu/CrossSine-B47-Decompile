package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;

/** @deprecated */
@Deprecated
public abstract class LegacySoundRewriter extends RewriterBase {
   protected final Int2ObjectMap soundRewrites = new Int2ObjectOpenHashMap(64);

   protected LegacySoundRewriter(BackwardsProtocol protocol) {
      super(protocol);
   }

   public SoundData added(int id, int replacement) {
      return this.added(id, replacement, -1.0F);
   }

   public SoundData added(int id, int replacement, float newPitch) {
      SoundData data = new SoundData(replacement, true, newPitch, true);
      this.soundRewrites.put(id, data);
      return data;
   }

   public SoundData removed(int id) {
      SoundData data = new SoundData(-1, false, -1.0F, false);
      this.soundRewrites.put(id, data);
      return data;
   }

   public int handleSounds(int soundId) {
      int newSoundId = soundId;
      SoundData data = (SoundData)this.soundRewrites.get(soundId);
      if (data != null) {
         return data.replacementSound();
      } else {
         for(Int2ObjectMap.Entry entry : this.soundRewrites.int2ObjectEntrySet()) {
            if (soundId > entry.getIntKey()) {
               if (((SoundData)entry.getValue()).added()) {
                  --newSoundId;
               } else {
                  ++newSoundId;
               }
            }
         }

         return newSoundId;
      }
   }

   public boolean hasPitch(int soundId) {
      SoundData data = (SoundData)this.soundRewrites.get(soundId);
      return data != null && data.changePitch();
   }

   public float handlePitch(int soundId) {
      SoundData data = (SoundData)this.soundRewrites.get(soundId);
      return data != null ? data.newPitch() : 1.0F;
   }

   public static final class SoundData {
      final int replacementSound;
      final boolean changePitch;
      final float newPitch;
      final boolean added;

      public SoundData(int replacementSound, boolean changePitch, float newPitch, boolean added) {
         this.replacementSound = replacementSound;
         this.changePitch = changePitch;
         this.newPitch = newPitch;
         this.added = added;
      }

      public int replacementSound() {
         return this.replacementSound;
      }

      public boolean changePitch() {
         return this.changePitch;
      }

      public float newPitch() {
         return this.newPitch;
      }

      public boolean added() {
         return this.added;
      }

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof SoundData)) {
            return false;
         } else {
            SoundData var2 = (SoundData)var1;
            return this.replacementSound == var2.replacementSound && this.changePitch == var2.changePitch && Float.compare(this.newPitch, var2.newPitch) == 0 && this.added == var2.added;
         }
      }

      public int hashCode() {
         return (((0 * 31 + Integer.hashCode(this.replacementSound)) * 31 + Boolean.hashCode(this.changePitch)) * 31 + Float.hashCode(this.newPitch)) * 31 + Boolean.hashCode(this.added);
      }

      public String toString() {
         return String.format("%s[replacementSound=%s, changePitch=%s, newPitch=%s, added=%s]", this.getClass().getSimpleName(), Integer.toString(this.replacementSound), Boolean.toString(this.changePitch), Float.toString(this.newPitch), Boolean.toString(this.added));
      }
   }
}
