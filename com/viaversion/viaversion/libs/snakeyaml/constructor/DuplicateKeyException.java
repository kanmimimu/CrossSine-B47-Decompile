package com.viaversion.viaversion.libs.snakeyaml.constructor;

import com.viaversion.viaversion.libs.snakeyaml.error.Mark;

public class DuplicateKeyException extends ConstructorException {
   protected DuplicateKeyException(Mark contextMark, Object key, Mark problemMark) {
      super("while constructing a mapping", contextMark, "found duplicate key " + key, problemMark);
   }
}
