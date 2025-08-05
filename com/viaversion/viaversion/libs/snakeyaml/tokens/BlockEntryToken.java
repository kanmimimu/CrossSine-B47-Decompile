package com.viaversion.viaversion.libs.snakeyaml.tokens;

import com.viaversion.viaversion.libs.snakeyaml.error.Mark;

public final class BlockEntryToken extends Token {
   public BlockEntryToken(Mark startMark, Mark endMark) {
      super(startMark, endMark);
   }

   public Token.ID getTokenId() {
      return Token.ID.BlockEntry;
   }
}
