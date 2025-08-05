package net.raphimc.vialegacy.protocol.beta.b1_2_0_2tob1_3_0_1.data;

import com.viaversion.viaversion.util.IdAndData;
import java.util.HashMap;
import java.util.Map;

public class BlockHardnessList {
   private static final Map HARDNESS_TABLE = new HashMap(82, 0.99F);

   public static boolean canBeBrokenInstantly(int blockID) {
      return (Float)HARDNESS_TABLE.getOrDefault(blockID, 0.0F) == 0.0F;
   }

   public static boolean canBeBrokenInstantly(IdAndData block) {
      return canBeBrokenInstantly(block.getId());
   }

   static {
      HARDNESS_TABLE.put(1, 1.5F);
      HARDNESS_TABLE.put(2, 0.6F);
      HARDNESS_TABLE.put(3, 0.5F);
      HARDNESS_TABLE.put(4, 2.0F);
      HARDNESS_TABLE.put(5, 2.0F);
      HARDNESS_TABLE.put(6, 0.0F);
      HARDNESS_TABLE.put(7, -1.0F);
      HARDNESS_TABLE.put(8, 100.0F);
      HARDNESS_TABLE.put(9, 100.0F);
      HARDNESS_TABLE.put(10, 0.0F);
      HARDNESS_TABLE.put(11, 100.0F);
      HARDNESS_TABLE.put(12, 0.5F);
      HARDNESS_TABLE.put(13, 0.6F);
      HARDNESS_TABLE.put(14, 3.0F);
      HARDNESS_TABLE.put(15, 3.0F);
      HARDNESS_TABLE.put(16, 3.0F);
      HARDNESS_TABLE.put(17, 2.0F);
      HARDNESS_TABLE.put(18, 0.2F);
      HARDNESS_TABLE.put(19, 0.6F);
      HARDNESS_TABLE.put(20, 0.3F);
      HARDNESS_TABLE.put(21, 3.0F);
      HARDNESS_TABLE.put(22, 3.0F);
      HARDNESS_TABLE.put(23, 3.5F);
      HARDNESS_TABLE.put(24, 0.8F);
      HARDNESS_TABLE.put(25, 0.8F);
      HARDNESS_TABLE.put(35, 0.8F);
      HARDNESS_TABLE.put(37, 0.0F);
      HARDNESS_TABLE.put(38, 0.0F);
      HARDNESS_TABLE.put(39, 0.0F);
      HARDNESS_TABLE.put(40, 0.0F);
      HARDNESS_TABLE.put(41, 3.0F);
      HARDNESS_TABLE.put(42, 5.0F);
      HARDNESS_TABLE.put(43, 2.0F);
      HARDNESS_TABLE.put(44, 2.0F);
      HARDNESS_TABLE.put(45, 2.0F);
      HARDNESS_TABLE.put(46, 0.0F);
      HARDNESS_TABLE.put(47, 1.5F);
      HARDNESS_TABLE.put(48, 2.0F);
      HARDNESS_TABLE.put(49, 10.0F);
      HARDNESS_TABLE.put(50, 0.0F);
      HARDNESS_TABLE.put(51, 0.0F);
      HARDNESS_TABLE.put(52, 5.0F);
      HARDNESS_TABLE.put(53, 2.0F);
      HARDNESS_TABLE.put(54, 2.5F);
      HARDNESS_TABLE.put(55, 0.0F);
      HARDNESS_TABLE.put(56, 3.0F);
      HARDNESS_TABLE.put(57, 5.0F);
      HARDNESS_TABLE.put(58, 2.5F);
      HARDNESS_TABLE.put(59, 0.0F);
      HARDNESS_TABLE.put(60, 0.6F);
      HARDNESS_TABLE.put(61, 3.5F);
      HARDNESS_TABLE.put(62, 3.5F);
      HARDNESS_TABLE.put(63, 1.0F);
      HARDNESS_TABLE.put(64, 3.0F);
      HARDNESS_TABLE.put(65, 0.4F);
      HARDNESS_TABLE.put(66, 0.7F);
      HARDNESS_TABLE.put(67, 2.0F);
      HARDNESS_TABLE.put(68, 1.0F);
      HARDNESS_TABLE.put(69, 0.5F);
      HARDNESS_TABLE.put(70, 0.5F);
      HARDNESS_TABLE.put(71, 5.0F);
      HARDNESS_TABLE.put(72, 0.5F);
      HARDNESS_TABLE.put(73, 3.0F);
      HARDNESS_TABLE.put(74, 3.0F);
      HARDNESS_TABLE.put(75, 0.0F);
      HARDNESS_TABLE.put(76, 0.0F);
      HARDNESS_TABLE.put(77, 0.5F);
      HARDNESS_TABLE.put(78, 0.1F);
      HARDNESS_TABLE.put(79, 0.5F);
      HARDNESS_TABLE.put(80, 0.2F);
      HARDNESS_TABLE.put(81, 0.4F);
      HARDNESS_TABLE.put(82, 0.6F);
      HARDNESS_TABLE.put(83, 0.0F);
      HARDNESS_TABLE.put(84, 2.0F);
      HARDNESS_TABLE.put(85, 2.0F);
      HARDNESS_TABLE.put(86, 1.0F);
      HARDNESS_TABLE.put(87, 0.4F);
      HARDNESS_TABLE.put(88, 0.5F);
      HARDNESS_TABLE.put(89, 0.3F);
      HARDNESS_TABLE.put(90, -1.0F);
      HARDNESS_TABLE.put(91, 1.0F);
      HARDNESS_TABLE.put(92, 0.5F);
   }
}
