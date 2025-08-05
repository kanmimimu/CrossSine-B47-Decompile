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
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

final class SqlDateTypeAdapter extends TypeAdapter {
   static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
      public TypeAdapter create(Gson gson, TypeToken typeToken) {
         return typeToken.getRawType() == Date.class ? new SqlDateTypeAdapter() : null;
      }
   };
   private final DateFormat format;

   private SqlDateTypeAdapter() {
      this.format = new SimpleDateFormat("MMM d, yyyy");
   }

   public Date read(JsonReader in) throws IOException {
      if (in.peek() == JsonToken.NULL) {
         in.nextNull();
         return null;
      } else {
         String s = in.nextString();
         synchronized(this) {
            TimeZone originalTimeZone = this.format.getTimeZone();

            Date var6;
            try {
               java.util.Date utilDate = this.format.parse(s);
               var6 = new Date(utilDate.getTime());
            } catch (ParseException e) {
               throw new JsonSyntaxException("Failed parsing '" + s + "' as SQL Date; at path " + in.getPreviousPath(), e);
            } finally {
               this.format.setTimeZone(originalTimeZone);
            }

            return var6;
         }
      }
   }

   public void write(JsonWriter out, Date value) throws IOException {
      if (value == null) {
         out.nullValue();
      } else {
         String dateString;
         synchronized(this) {
            dateString = this.format.format(value);
         }

         out.value(dateString);
      }
   }
}
