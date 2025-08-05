package com.viaversion.viaversion.libs.gson.internal.sql;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

final class SqlTimeTypeAdapter extends TypeAdapter {
   static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
      public TypeAdapter create(Gson gson, TypeToken typeToken) {
         return typeToken.getRawType() == Time.class ? new SqlTimeTypeAdapter() : null;
      }
   };
   private final DateFormat format;

   private SqlTimeTypeAdapter() {
      this.format = new SimpleDateFormat("hh:mm:ss a");
   }

   public Time read(JsonReader in) throws IOException {
      if (in.peek() == JsonToken.NULL) {
         in.nextNull();
         return null;
      } else {
         String s = in.nextString();
         synchronized(this) {
            TimeZone originalTimeZone = this.format.getTimeZone();

            Time var6;
            try {
               Date date = this.format.parse(s);
               var6 = new Time(date.getTime());
            } catch (ParseException e) {
               throw new JsonSyntaxException("Failed parsing '" + s + "' as SQL Time; at path " + in.getPreviousPath(), e);
            } finally {
               this.format.setTimeZone(originalTimeZone);
            }

            return var6;
         }
      }
   }

   public void write(JsonWriter out, Time value) throws IOException {
      if (value == null) {
         out.nullValue();
      } else {
         String timeString;
         synchronized(this) {
            timeString = this.format.format(value);
         }

         out.value(timeString);
      }
   }
}
