package com.viaversion.viaversion.rewriter;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.data.FullMappings;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Types;
import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public class CommandRewriter {
   protected final Protocol protocol;
   protected final Map parserHandlers = new HashMap();

   public CommandRewriter(Protocol protocol) {
      this.protocol = protocol;
      this.parserHandlers.put("brigadier:double", (CommandArgumentConsumer)(wrapper) -> {
         byte propertyFlags = (Byte)wrapper.passthrough(Types.BYTE);
         if ((propertyFlags & 1) != 0) {
            wrapper.passthrough(Types.DOUBLE);
         }

         if ((propertyFlags & 2) != 0) {
            wrapper.passthrough(Types.DOUBLE);
         }

      });
      this.parserHandlers.put("brigadier:float", (CommandArgumentConsumer)(wrapper) -> {
         byte propertyFlags = (Byte)wrapper.passthrough(Types.BYTE);
         if ((propertyFlags & 1) != 0) {
            wrapper.passthrough(Types.FLOAT);
         }

         if ((propertyFlags & 2) != 0) {
            wrapper.passthrough(Types.FLOAT);
         }

      });
      this.parserHandlers.put("brigadier:integer", (CommandArgumentConsumer)(wrapper) -> {
         byte propertyFlags = (Byte)wrapper.passthrough(Types.BYTE);
         if ((propertyFlags & 1) != 0) {
            wrapper.passthrough(Types.INT);
         }

         if ((propertyFlags & 2) != 0) {
            wrapper.passthrough(Types.INT);
         }

      });
      this.parserHandlers.put("brigadier:long", (CommandArgumentConsumer)(wrapper) -> {
         byte propertyFlags = (Byte)wrapper.passthrough(Types.BYTE);
         if ((propertyFlags & 1) != 0) {
            wrapper.passthrough(Types.LONG);
         }

         if ((propertyFlags & 2) != 0) {
            wrapper.passthrough(Types.LONG);
         }

      });
      this.parserHandlers.put("brigadier:string", (CommandArgumentConsumer)(wrapper) -> wrapper.passthrough(Types.VAR_INT));
      this.parserHandlers.put("minecraft:entity", (CommandArgumentConsumer)(wrapper) -> wrapper.passthrough(Types.BYTE));
      this.parserHandlers.put("minecraft:score_holder", (CommandArgumentConsumer)(wrapper) -> wrapper.passthrough(Types.BYTE));
      this.parserHandlers.put("minecraft:resource", (CommandArgumentConsumer)(wrapper) -> wrapper.passthrough(Types.STRING));
      this.parserHandlers.put("minecraft:resource_or_tag", (CommandArgumentConsumer)(wrapper) -> wrapper.passthrough(Types.STRING));
      this.parserHandlers.put("minecraft:resource_or_tag_key", (CommandArgumentConsumer)(wrapper) -> wrapper.passthrough(Types.STRING));
      this.parserHandlers.put("minecraft:resource_key", (CommandArgumentConsumer)(wrapper) -> wrapper.passthrough(Types.STRING));
   }

   public void registerDeclareCommands(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)((wrapper) -> {
         int size = (Integer)wrapper.passthrough(Types.VAR_INT);

         for(int i = 0; i < size; ++i) {
            byte flags = (Byte)wrapper.passthrough(Types.BYTE);
            wrapper.passthrough(Types.VAR_INT_ARRAY_PRIMITIVE);
            if ((flags & 8) != 0) {
               wrapper.passthrough(Types.VAR_INT);
            }

            byte nodeType = (byte)(flags & 3);
            if (nodeType == 1 || nodeType == 2) {
               wrapper.passthrough(Types.STRING);
            }

            if (nodeType == 2) {
               String argumentType = (String)wrapper.read(Types.STRING);
               String newArgumentType = this.handleArgumentType(argumentType);
               if (newArgumentType != null) {
                  wrapper.write(Types.STRING, newArgumentType);
               }

               this.handleArgument(wrapper, argumentType);
            }

            if ((flags & 16) != 0) {
               wrapper.passthrough(Types.STRING);
            }
         }

         wrapper.passthrough(Types.VAR_INT);
      }));
   }

   public void registerDeclareCommands1_19(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)((wrapper) -> {
         int size = (Integer)wrapper.passthrough(Types.VAR_INT);

         for(int i = 0; i < size; ++i) {
            byte flags = (Byte)wrapper.passthrough(Types.BYTE);
            wrapper.passthrough(Types.VAR_INT_ARRAY_PRIMITIVE);
            if ((flags & 8) != 0) {
               wrapper.passthrough(Types.VAR_INT);
            }

            byte nodeType = (byte)(flags & 3);
            if (nodeType == 1 || nodeType == 2) {
               wrapper.passthrough(Types.STRING);
            }

            if (nodeType == 2) {
               int argumentTypeId = (Integer)wrapper.read(Types.VAR_INT);
               String argumentType = this.argumentType(argumentTypeId);
               if (argumentType == null) {
                  wrapper.write(Types.VAR_INT, this.mapInvalidArgumentType(argumentTypeId));
               } else {
                  String newArgumentType = this.handleArgumentType(argumentType);
                  Preconditions.checkNotNull(newArgumentType, "No mapping for argument type %s", new Object[]{argumentType});
                  wrapper.write(Types.VAR_INT, this.mappedArgumentTypeId(newArgumentType));
                  this.handleArgument(wrapper, argumentType);
                  if ((flags & 16) != 0) {
                     wrapper.passthrough(Types.STRING);
                  }
               }
            }
         }

         wrapper.passthrough(Types.VAR_INT);
      }));
   }

   public void handleArgument(PacketWrapper wrapper, String argumentType) {
      CommandArgumentConsumer handler = (CommandArgumentConsumer)this.parserHandlers.get(argumentType);
      if (handler != null) {
         handler.accept(wrapper);
      }

   }

   public String handleArgumentType(String argumentType) {
      return this.protocol.getMappingData() != null && this.protocol.getMappingData().getArgumentTypeMappings() != null ? this.protocol.getMappingData().getArgumentTypeMappings().mappedIdentifier(argumentType) : argumentType;
   }

   protected @Nullable String argumentType(int argumentTypeId) {
      FullMappings mappings = this.protocol.getMappingData().getArgumentTypeMappings();
      String identifier = mappings.identifier(argumentTypeId);
      Preconditions.checkArgument(identifier != null || argumentTypeId >= mappings.size(), "Unknown argument type id %s", new Object[]{argumentTypeId});
      return identifier;
   }

   protected int mappedArgumentTypeId(String mappedArgumentType) {
      return this.protocol.getMappingData().getArgumentTypeMappings().mappedId(mappedArgumentType);
   }

   int mapInvalidArgumentType(int id) {
      if (id < 0) {
         return id;
      } else {
         FullMappings mappings = this.protocol.getMappingData().getArgumentTypeMappings();
         int idx = id - mappings.size();
         return mappings.mappedSize() + idx;
      }
   }

   @FunctionalInterface
   public interface CommandArgumentConsumer {
      void accept(PacketWrapper var1);
   }
}
