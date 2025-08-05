package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.internal.Streams;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.MalformedJsonException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class JsonStreamParser implements Iterator {
   private final JsonReader parser;
   private final Object lock;

   public JsonStreamParser(String json) {
      this((Reader)(new StringReader(json)));
   }

   public JsonStreamParser(Reader reader) {
      this.parser = new JsonReader(reader);
      this.parser.setStrictness(Strictness.LENIENT);
      this.lock = new Object();
   }

   public JsonElement next() throws JsonParseException {
      if (!this.hasNext()) {
         throw new NoSuchElementException();
      } else {
         try {
            return Streams.parse(this.parser);
         } catch (StackOverflowError e) {
            throw new JsonParseException("Failed parsing JSON source to Json", e);
         } catch (OutOfMemoryError e) {
            throw new JsonParseException("Failed parsing JSON source to Json", e);
         }
      }
   }

   public boolean hasNext() {
      synchronized(this.lock) {
         boolean var10000;
         try {
            var10000 = this.parser.peek() != JsonToken.END_DOCUMENT;
         } catch (MalformedJsonException e) {
            throw new JsonSyntaxException(e);
         } catch (IOException e) {
            throw new JsonIOException(e);
         }

         return var10000;
      }
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }
}
