package com.viaversion.viaversion.libs.snakeyaml.tokens;

import com.viaversion.viaversion.libs.snakeyaml.error.Mark;

public final class TagToken extends Token {
   private final TagTuple value;

   public TagToken(TagTuple value, Mark startMark, Mark endMark) {
      super(startMark, endMark);
      this.value = value;
   }

   public TagTuple getValue() {
      return this.value;
   }

   public Token.ID getTokenId() {
      return Token.ID.Tag;
   }
}
