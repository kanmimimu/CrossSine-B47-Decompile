package com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class DoorConnectionHandler implements ConnectionHandler {
   static final Int2ObjectMap DOOR_DATA_MAP = new Int2ObjectOpenHashMap();
   static final Map CONNECTED_STATES = new HashMap();

   static ConnectionData.ConnectorInitAction init() {
      List<String> baseDoors = new LinkedList();
      baseDoors.add("minecraft:oak_door");
      baseDoors.add("minecraft:birch_door");
      baseDoors.add("minecraft:jungle_door");
      baseDoors.add("minecraft:dark_oak_door");
      baseDoors.add("minecraft:acacia_door");
      baseDoors.add("minecraft:spruce_door");
      baseDoors.add("minecraft:iron_door");
      DoorConnectionHandler connectionHandler = new DoorConnectionHandler();
      return (blockData) -> {
         int type = baseDoors.indexOf(blockData.getMinecraftKey());
         if (type != -1) {
            int id = blockData.getSavedBlockStateId();
            DoorData doorData = new DoorData(blockData.getValue("half").equals("lower"), blockData.getValue("hinge").equals("right"), blockData.getValue("powered").equals("true"), blockData.getValue("open").equals("true"), BlockFace.valueOf(blockData.getValue("facing").toUpperCase(Locale.ROOT)), type);
            DOOR_DATA_MAP.put(id, doorData);
            CONNECTED_STATES.put(getStates(doorData), id);
            ConnectionData.connectionHandlerMap.put(id, connectionHandler);
         }
      };
   }

   static short getStates(DoorData doorData) {
      short s = 0;
      if (doorData.lower()) {
         s = (short)(s | 1);
      }

      if (doorData.open()) {
         s = (short)(s | 2);
      }

      if (doorData.powered()) {
         s = (short)(s | 4);
      }

      if (doorData.rightHinge()) {
         s = (short)(s | 8);
      }

      s = (short)(s | doorData.facing().ordinal() << 4);
      s = (short)(s | (doorData.type() & 7) << 6);
      return s;
   }

   public int connect(UserConnection user, BlockPosition position, int blockState) {
      DoorData doorData = (DoorData)DOOR_DATA_MAP.get(blockState);
      if (doorData == null) {
         return blockState;
      } else {
         short s = 0;
         s = (short)(s | (doorData.type() & 7) << 6);
         if (doorData.lower()) {
            DoorData upperHalf = (DoorData)DOOR_DATA_MAP.get(this.getBlockData(user, position.getRelative(BlockFace.TOP)));
            if (upperHalf == null) {
               return blockState;
            }

            s = (short)(s | 1);
            if (doorData.open()) {
               s = (short)(s | 2);
            }

            if (upperHalf.powered()) {
               s = (short)(s | 4);
            }

            if (upperHalf.rightHinge()) {
               s = (short)(s | 8);
            }

            s = (short)(s | doorData.facing().ordinal() << 4);
         } else {
            DoorData lowerHalf = (DoorData)DOOR_DATA_MAP.get(this.getBlockData(user, position.getRelative(BlockFace.BOTTOM)));
            if (lowerHalf == null) {
               return blockState;
            }

            if (lowerHalf.open()) {
               s = (short)(s | 2);
            }

            if (doorData.powered()) {
               s = (short)(s | 4);
            }

            if (doorData.rightHinge()) {
               s = (short)(s | 8);
            }

            s = (short)(s | lowerHalf.facing().ordinal() << 4);
         }

         Integer newBlockState = (Integer)CONNECTED_STATES.get(s);
         return newBlockState == null ? blockState : newBlockState;
      }
   }

   private static final class DoorData {
      final boolean lower;
      final boolean rightHinge;
      final boolean powered;
      final boolean open;
      final BlockFace facing;
      final int type;

      DoorData(boolean lower, boolean rightHinge, boolean powered, boolean open, BlockFace facing, int type) {
         this.lower = lower;
         this.rightHinge = rightHinge;
         this.powered = powered;
         this.open = open;
         this.facing = facing;
         this.type = type;
      }

      public boolean lower() {
         return this.lower;
      }

      public boolean rightHinge() {
         return this.rightHinge;
      }

      public boolean powered() {
         return this.powered;
      }

      public boolean open() {
         return this.open;
      }

      public BlockFace facing() {
         return this.facing;
      }

      public int type() {
         return this.type;
      }

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof DoorData)) {
            return false;
         } else {
            DoorData var2 = (DoorData)var1;
            return this.lower == var2.lower && this.rightHinge == var2.rightHinge && this.powered == var2.powered && this.open == var2.open && Objects.equals(this.facing, var2.facing) && this.type == var2.type;
         }
      }

      public int hashCode() {
         return (((((0 * 31 + Boolean.hashCode(this.lower)) * 31 + Boolean.hashCode(this.rightHinge)) * 31 + Boolean.hashCode(this.powered)) * 31 + Boolean.hashCode(this.open)) * 31 + Objects.hashCode(this.facing)) * 31 + Integer.hashCode(this.type);
      }

      public String toString() {
         return String.format("%s[lower=%s, rightHinge=%s, powered=%s, open=%s, facing=%s, type=%s]", this.getClass().getSimpleName(), Boolean.toString(this.lower), Boolean.toString(this.rightHinge), Boolean.toString(this.powered), Boolean.toString(this.open), Objects.toString(this.facing), Integer.toString(this.type));
      }
   }
}
