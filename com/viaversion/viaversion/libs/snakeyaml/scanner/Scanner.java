package com.viaversion.viaversion.libs.snakeyaml.scanner;

import com.viaversion.viaversion.libs.snakeyaml.tokens.Token;

public interface Scanner {
   boolean checkToken(Token.ID... var1);

   Token peekToken();

   Token getToken();

   void resetDocumentIndex();
}
