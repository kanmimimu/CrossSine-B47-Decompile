package com.viaversion.viaversion.libs.snakeyaml.emitter;

import java.io.IOException;

interface EmitterState {
   void expect() throws IOException;
}
