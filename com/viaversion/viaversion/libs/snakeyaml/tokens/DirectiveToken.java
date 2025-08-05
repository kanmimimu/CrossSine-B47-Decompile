package com.viaversion.viaversion.libs.snakeyaml.tokens;

import com.viaversion.viaversion.libs.snakeyaml.error.Mark;
import com.viaversion.viaversion.libs.snakeyaml.error.YAMLException;
import java.util.List;

public final class DirectiveToken extends Token {
   private final String name;
   private final List value;

   public DirectiveToken(String name, List value, Mark startMark, Mark endMark) {
      super(startMark, endMark);
      this.name = name;
      if (value != null && value.size() != 2) {
         throw new YAMLException("Two strings must be provided instead of " + value.size());
      } else {
         this.value = value;
      }
   }

   public String getName() {
      return this.name;
   }

   public List getValue() {
      return this.value;
   }

   public Token.ID getTokenId() {
      return Token.ID.Directive;
   }
}
