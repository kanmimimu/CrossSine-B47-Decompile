package com.viaversion.viaversion.libs.snakeyaml.tokens;

import com.viaversion.viaversion.libs.snakeyaml.error.Mark;

public final class ValueToken extends Token {
   public ValueToken(Mark startMark, Mark endMark) {
      super(startMark, endMark);
   }

   public Token.ID getTokenId() {
      return Token.ID.Value;
   }
}
