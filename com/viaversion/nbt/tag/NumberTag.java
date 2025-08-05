package com.viaversion.nbt.tag;

public interface NumberTag extends Tag {
   Number getValue();

   byte asByte();

   short asShort();

   int asInt();

   long asLong();

   float asFloat();

   double asDouble();

   default boolean asBoolean() {
      return this.asByte() != 0;
   }

   default NumberTag copy() {
      return this;
   }
}
