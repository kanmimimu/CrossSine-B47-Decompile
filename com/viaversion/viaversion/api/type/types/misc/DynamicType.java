package com.viaversion.viaversion.api.type.types.misc;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.data.FullMappings;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.util.IdHolder;
import io.netty.buffer.ByteBuf;

public abstract class DynamicType extends Type {
   protected final Int2ObjectMap readers;

   protected DynamicType(Int2ObjectMap readers, Class outputClass) {
      super(outputClass.getSimpleName(), outputClass);
      this.readers = readers;
   }

   protected DynamicType(Class outputClass) {
      this(new Int2ObjectOpenHashMap(), outputClass);
   }

   public DataFiller filler(Protocol protocol) {
      return this.filler(protocol, true);
   }

   public DataFiller filler(Protocol protocol, boolean useMappedNames) {
      return new DataFiller(protocol, useMappedNames);
   }

   protected void readData(ByteBuf buffer, IdHolder value) {
      DataReader<T> reader = (DataReader)this.readers.get(value.id());
      if (reader != null) {
         reader.read(buffer, value);
      }

   }

   public RawDataFiller rawFiller() {
      return new RawDataFiller();
   }

   protected abstract FullMappings mappings(Protocol var1);

   public final class DataFiller {
      final FullMappings mappings;
      final boolean useMappedNames;

      DataFiller(Protocol protocol, boolean useMappedNames) {
         this.mappings = DynamicType.this.mappings(protocol);
         Preconditions.checkNotNull(this.mappings, "Mappings for %s are null", new Object[]{protocol.getClass()});
         this.useMappedNames = useMappedNames;
      }

      public DataFiller reader(String identifier, DataReader reader) {
         DynamicType.this.readers.put(this.useMappedNames ? this.mappings.mappedId(identifier) : this.mappings.id(identifier), reader);
         return this;
      }
   }

   public final class RawDataFiller {
      public RawDataFiller reader(int id, DataReader reader) {
         DynamicType.this.readers.put(id, reader);
         return this;
      }
   }

   @FunctionalInterface
   public interface DataReader {
      void read(ByteBuf var1, Object var2);
   }
}
