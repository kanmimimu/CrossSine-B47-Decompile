package com.viaversion.viaversion.api.minecraft;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.util.IdHolder;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;

public final class Particle implements IdHolder {
   final List arguments = new ArrayList(4);
   int id;

   public Particle(int id) {
      this.id = id;
   }

   public int id() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public ParticleData getArgument(int index) {
      return (ParticleData)this.arguments.get(index);
   }

   public ParticleData removeArgument(int index) {
      return (ParticleData)this.arguments.remove(index);
   }

   public List getArguments() {
      return this.arguments;
   }

   public void add(Type type, Object value) {
      this.arguments.add(new ParticleData(type, value));
   }

   public void add(int index, Type type, Object value) {
      this.arguments.add(index, new ParticleData(type, value));
   }

   public void set(int index, Type type, Object value) {
      this.arguments.set(index, new ParticleData(type, value));
   }

   public Particle copy() {
      Particle particle = new Particle(this.id);

      for(ParticleData argument : this.arguments) {
         particle.arguments.add(argument.copy());
      }

      return particle;
   }

   public String toString() {
      int var4 = this.id;
      List var3 = this.arguments;
      return "Particle{arguments=" + var3 + ", id=" + var4 + "}";
   }

   public static final class ParticleData {
      final Type type;
      Object value;

      public ParticleData(Type type, Object value) {
         this.type = type;
         this.value = value;
      }

      public Type getType() {
         return this.type;
      }

      public Object getValue() {
         return this.value;
      }

      public void setValue(Object value) {
         this.value = value;
      }

      public void write(ByteBuf buf) {
         this.type.write(buf, this.value);
      }

      public void write(PacketWrapper wrapper) {
         wrapper.write(this.type, this.value);
      }

      public ParticleData copy() {
         Object var2 = this.value;
         if (var2 instanceof Item) {
            Item item = (Item)var2;
            return new ParticleData(this.type, item.copy());
         } else {
            return new ParticleData(this.type, this.value);
         }
      }

      public String toString() {
         Object var4 = this.value;
         Type var3 = this.type;
         return "ParticleData{type=" + var3 + ", value=" + var4 + "}";
      }
   }
}
