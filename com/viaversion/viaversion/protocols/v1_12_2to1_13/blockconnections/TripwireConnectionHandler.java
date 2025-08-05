package com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectArrayMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Arrays;
import java.util.Locale;

public class TripwireConnectionHandler implements ConnectionHandler {
   static final Int2ObjectMap TRIPWIRE_DATA_MAP = new Int2ObjectOpenHashMap();
   static final Int2ObjectMap TRIPWIRE_HOOKS = new Int2ObjectArrayMap();
   static final int[] CONNECTED_BLOCKS = new int[128];

   static ConnectionData.ConnectorInitAction init() {
      Arrays.fill(CONNECTED_BLOCKS, -1);
      TripwireConnectionHandler connectionHandler = new TripwireConnectionHandler();
      return (blockData) -> {
         if (blockData.getMinecraftKey().equals("minecraft:tripwire_hook")) {
            TRIPWIRE_HOOKS.put(blockData.getSavedBlockStateId(), BlockFace.valueOf(blockData.getValue("facing").toUpperCase(Locale.ROOT)));
         } else if (blockData.getMinecraftKey().equals("minecraft:tripwire")) {
            TripwireData tripwireData = new TripwireData(blockData.getValue("attached").equals("true"), blockData.getValue("disarmed").equals("true"), blockData.getValue("powered").equals("true"));
            TRIPWIRE_DATA_MAP.put(blockData.getSavedBlockStateId(), tripwireData);
            CONNECTED_BLOCKS[getStates(blockData)] = blockData.getSavedBlockStateId();
            ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), connectionHandler);
         }

      };
   }

   static byte getStates(WrappedBlockData blockData) {
      byte b = 0;
      if (blockData.getValue("attached").equals("true")) {
         b = (byte)(b | 1);
      }

      if (blockData.getValue("disarmed").equals("true")) {
         b = (byte)(b | 2);
      }

      if (blockData.getValue("powered").equals("true")) {
         b = (byte)(b | 4);
      }

      if (blockData.getValue("east").equals("true")) {
         b = (byte)(b | 8);
      }

      if (blockData.getValue("north").equals("true")) {
         b = (byte)(b | 16);
      }

      if (blockData.getValue("south").equals("true")) {
         b = (byte)(b | 32);
      }

      if (blockData.getValue("west").equals("true")) {
         b = (byte)(b | 64);
      }

      return b;
   }

   public int connect(UserConnection user, BlockPosition position, int blockState) {
      TripwireData tripwireData = (TripwireData)TRIPWIRE_DATA_MAP.get(blockState);
      if (tripwireData == null) {
         return blockState;
      } else {
         byte b = 0;
         if (tripwireData.attached()) {
            b = (byte)(b | 1);
         }

         if (tripwireData.disarmed()) {
            b = (byte)(b | 2);
         }

         if (tripwireData.powered()) {
            b = (byte)(b | 4);
         }

         int east = this.getBlockData(user, position.getRelative(BlockFace.EAST));
         int north = this.getBlockData(user, position.getRelative(BlockFace.NORTH));
         int south = this.getBlockData(user, position.getRelative(BlockFace.SOUTH));
         int west = this.getBlockData(user, position.getRelative(BlockFace.WEST));
         if (TRIPWIRE_DATA_MAP.containsKey(east) || TRIPWIRE_HOOKS.get(east) == BlockFace.WEST) {
            b = (byte)(b | 8);
         }

         if (TRIPWIRE_DATA_MAP.containsKey(north) || TRIPWIRE_HOOKS.get(north) == BlockFace.SOUTH) {
            b = (byte)(b | 16);
         }

         if (TRIPWIRE_DATA_MAP.containsKey(south) || TRIPWIRE_HOOKS.get(south) == BlockFace.NORTH) {
            b = (byte)(b | 32);
         }

         if (TRIPWIRE_DATA_MAP.containsKey(west) || TRIPWIRE_HOOKS.get(west) == BlockFace.EAST) {
            b = (byte)(b | 64);
         }

         int newBlockState = CONNECTED_BLOCKS[b];
         return newBlockState == -1 ? blockState : newBlockState;
      }
   }

   private static final class TripwireData {
      final boolean attached;
      final boolean disarmed;
      final boolean powered;

      TripwireData(boolean attached, boolean disarmed, boolean powered) {
         this.attached = attached;
         this.disarmed = disarmed;
         this.powered = powered;
      }

      public boolean attached() {
         return this.attached;
      }

      public boolean disarmed() {
         return this.disarmed;
      }

      public boolean powered() {
         return this.powered;
      }

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof TripwireData)) {
            return false;
         } else {
            TripwireData var2 = (TripwireData)var1;
            return this.attached == var2.attached && this.disarmed == var2.disarmed && this.powered == var2.powered;
         }
      }

      public int hashCode() {
         return ((0 * 31 + Boolean.hashCode(this.attached)) * 31 + Boolean.hashCode(this.disarmed)) * 31 + Boolean.hashCode(this.powered);
      }

      public String toString() {
         return String.format("%s[attached=%s, disarmed=%s, powered=%s]", this.getClass().getSimpleName(), Boolean.toString(this.attached), Boolean.toString(this.disarmed), Boolean.toString(this.powered));
      }
   }
}
