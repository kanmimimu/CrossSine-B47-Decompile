package net.raphimc.vialegacy.api.splitter;

import io.netty.buffer.ByteBuf;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.data.NbtItemList1_2_4;

public class PreNettyTypes {
   public static void readString(ByteBuf buffer) {
      short s = buffer.readShort();

      for(int i = 0; i < s; ++i) {
         buffer.readShort();
      }

   }

   public static void readUTF(ByteBuf buffer) {
      int l = buffer.readUnsignedShort();

      for(int i = 0; i < l; ++i) {
         buffer.readByte();
      }

   }

   public static void readString64(ByteBuf buffer) {
      for(int i = 0; i < 64; ++i) {
         buffer.readByte();
      }

   }

   public static void readItemStack1_3_1(ByteBuf buffer) {
      short s = buffer.readShort();
      if (s >= 0) {
         buffer.readByte();
         buffer.readShort();
         readTag(buffer);
      }

   }

   public static void readItemStack1_0(ByteBuf buffer) {
      short s = buffer.readShort();
      if (s >= 0) {
         buffer.readByte();
         buffer.readShort();
         if (NbtItemList1_2_4.hasNbt(s)) {
            readTag(buffer);
         }
      }

   }

   public static void readItemStackb1_2(ByteBuf buffer) {
      short s = buffer.readShort();
      if (s >= 0) {
         buffer.readByte();
         buffer.readShort();
      }

   }

   public static void readItemStackb1_1(ByteBuf buffer) {
      short s = buffer.readShort();
      if (s >= 0) {
         buffer.readByte();
         buffer.readByte();
      }

   }

   public static void readByteArray(ByteBuf buffer) {
      short s = buffer.readShort();

      for(int i = 0; i < s; ++i) {
         buffer.readByte();
      }

   }

   public static void readByteArray1024(ByteBuf buffer) {
      for(int i = 0; i < 1024; ++i) {
         buffer.readByte();
      }

   }

   public static void readTag(ByteBuf buffer) {
      int s = buffer.readShort();

      for(int i = 0; i < s; ++i) {
         buffer.readByte();
      }

   }

   public static void readEntityDataList1_4_4(ByteBuf buffer) {
      for(byte b = buffer.readByte(); b != 127; b = buffer.readByte()) {
         int i = (b & 224) >> 5;
         switch (i) {
            case 0:
               buffer.readByte();
               break;
            case 1:
               buffer.readShort();
               break;
            case 2:
               buffer.readInt();
               break;
            case 3:
               buffer.readFloat();
               break;
            case 4:
               readString(buffer);
               break;
            case 5:
               readItemStack1_3_1(buffer);
               break;
            case 6:
               buffer.readInt();
               buffer.readInt();
               buffer.readInt();
         }
      }

   }

   public static void readEntityDataList1_4_2(ByteBuf buffer) {
      for(byte b = buffer.readByte(); b != 127; b = buffer.readByte()) {
         int i = (b & 224) >> 5;
         switch (i) {
            case 0:
               buffer.readByte();
               break;
            case 1:
               buffer.readShort();
               break;
            case 2:
               buffer.readInt();
               break;
            case 3:
               buffer.readFloat();
               break;
            case 4:
               readString(buffer);
               break;
            case 5:
               short x = buffer.readShort();
               if (x > -1) {
                  buffer.readByte();
                  buffer.readShort();
               }
               break;
            case 6:
               buffer.readInt();
               buffer.readInt();
               buffer.readInt();
         }
      }

   }

   public static void readEntityDataListb1_5(ByteBuf buffer) {
      for(byte b = buffer.readByte(); b != 127; b = buffer.readByte()) {
         int i = (b & 224) >> 5;
         switch (i) {
            case 0:
               buffer.readByte();
               break;
            case 1:
               buffer.readShort();
               break;
            case 2:
               buffer.readInt();
               break;
            case 3:
               buffer.readFloat();
               break;
            case 4:
               readString(buffer);
               break;
            case 5:
               buffer.readShort();
               buffer.readByte();
               buffer.readShort();
               break;
            case 6:
               buffer.readInt();
               buffer.readInt();
               buffer.readInt();
         }
      }

   }

   public static void readEntityDataListb1_3(ByteBuf buffer) {
      for(byte b = buffer.readByte(); b != 127; b = buffer.readByte()) {
         int i = (b & 224) >> 5;
         switch (i) {
            case 0:
               buffer.readByte();
               break;
            case 1:
               buffer.readShort();
               break;
            case 2:
               buffer.readInt();
               break;
            case 3:
               buffer.readFloat();
               break;
            case 4:
               readUTF(buffer);
               break;
            case 5:
               buffer.readShort();
               buffer.readByte();
               buffer.readShort();
               break;
            case 6:
               buffer.readInt();
               buffer.readInt();
               buffer.readInt();
         }
      }

   }

   public static void readEntityDataListb1_2(ByteBuf buffer) {
      for(byte b = buffer.readByte(); b != 127; b = buffer.readByte()) {
         int i = (b & 224) >> 5;
         switch (i) {
            case 0:
               buffer.readByte();
               break;
            case 1:
               buffer.readShort();
               break;
            case 2:
               buffer.readInt();
               break;
            case 3:
               buffer.readFloat();
               break;
            case 4:
               readUTF(buffer);
               break;
            case 5:
               buffer.readShort();
               buffer.readByte();
               buffer.readShort();
         }
      }

   }
}
