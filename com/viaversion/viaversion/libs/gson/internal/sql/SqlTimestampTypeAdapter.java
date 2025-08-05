package com.viaversion.viaversion.libs.gson.internal.sql;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

class SqlTimestampTypeAdapter extends TypeAdapter {
   static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
      public TypeAdapter create(Gson gson, TypeToken typeToken) {
         if (typeToken.getRawType() == Timestamp.class) {
            TypeAdapter<Date> dateTypeAdapter = gson.getAdapter(Date.class);
            return new SqlTimestampTypeAdapter(dateTypeAdapter);
         } else {
            return null;
         }
      }
   };
   private final TypeAdapter dateTypeAdapter;

   private SqlTimestampTypeAdapter(TypeAdapter dateTypeAdapter) {
      this.dateTypeAdapter = dateTypeAdapter;
   }

   public Timestamp read(JsonReader in) throws IOException {
      Date date = (Date)this.dateTypeAdapter.read(in);
      return date != null ? new Timestamp(date.getTime()) : null;
   }

   public void write(JsonWriter out, Timestamp value) throws IOException {
      this.dateTypeAdapter.write(out, value);
   }
}
