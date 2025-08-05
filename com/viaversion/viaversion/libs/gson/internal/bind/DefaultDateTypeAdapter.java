package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.internal.JavaVersion;
import com.viaversion.viaversion.libs.gson.internal.PreJava9DateFormatProvider;
import com.viaversion.viaversion.libs.gson.internal.bind.util.ISO8601Utils;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public final class DefaultDateTypeAdapter extends TypeAdapter {
   private static final String SIMPLE_NAME = "DefaultDateTypeAdapter";
   public static final TypeAdapterFactory DEFAULT_STYLE_FACTORY = new TypeAdapterFactory() {
      public TypeAdapter create(Gson gson, TypeToken typeToken) {
         return typeToken.getRawType() == Date.class ? new DefaultDateTypeAdapter(DefaultDateTypeAdapter.DateType.DATE, 2, 2) : null;
      }

      public String toString() {
         return "DefaultDateTypeAdapter#DEFAULT_STYLE_FACTORY";
      }
   };
   private final DateType dateType;
   private final List dateFormats;

   private DefaultDateTypeAdapter(DateType dateType, String datePattern) {
      this.dateFormats = new ArrayList();
      this.dateType = (DateType)Objects.requireNonNull(dateType);
      this.dateFormats.add(new SimpleDateFormat(datePattern, Locale.US));
      if (!Locale.getDefault().equals(Locale.US)) {
         this.dateFormats.add(new SimpleDateFormat(datePattern));
      }

   }

   private DefaultDateTypeAdapter(DateType dateType, int dateStyle, int timeStyle) {
      this.dateFormats = new ArrayList();
      this.dateType = (DateType)Objects.requireNonNull(dateType);
      this.dateFormats.add(DateFormat.getDateTimeInstance(dateStyle, timeStyle, Locale.US));
      if (!Locale.getDefault().equals(Locale.US)) {
         this.dateFormats.add(DateFormat.getDateTimeInstance(dateStyle, timeStyle));
      }

      if (JavaVersion.isJava9OrLater()) {
         this.dateFormats.add(PreJava9DateFormatProvider.getUsDateTimeFormat(dateStyle, timeStyle));
      }

   }

   public void write(JsonWriter out, Date value) throws IOException {
      if (value == null) {
         out.nullValue();
      } else {
         DateFormat dateFormat = (DateFormat)this.dateFormats.get(0);
         String dateFormatAsString;
         synchronized(this.dateFormats) {
            dateFormatAsString = dateFormat.format(value);
         }

         out.value(dateFormatAsString);
      }
   }

   public Date read(JsonReader in) throws IOException {
      if (in.peek() == JsonToken.NULL) {
         in.nextNull();
         return null;
      } else {
         Date date = this.deserializeToDate(in);
         return this.dateType.deserialize(date);
      }
   }

   private Date deserializeToDate(JsonReader in) throws IOException {
      String s = in.nextString();
      synchronized(this.dateFormats) {
         for(DateFormat dateFormat : this.dateFormats) {
            TimeZone originalTimeZone = dateFormat.getTimeZone();

            try {
               Date var7 = dateFormat.parse(s);
               return var7;
            } catch (ParseException var15) {
            } finally {
               dateFormat.setTimeZone(originalTimeZone);
            }
         }
      }

      try {
         return ISO8601Utils.parse(s, new ParsePosition(0));
      } catch (ParseException e) {
         throw new JsonSyntaxException("Failed parsing '" + s + "' as Date; at path " + in.getPreviousPath(), e);
      }
   }

   public String toString() {
      DateFormat defaultFormat = (DateFormat)this.dateFormats.get(0);
      return defaultFormat instanceof SimpleDateFormat ? "DefaultDateTypeAdapter(" + ((SimpleDateFormat)defaultFormat).toPattern() + ')' : "DefaultDateTypeAdapter(" + defaultFormat.getClass().getSimpleName() + ')';
   }

   public abstract static class DateType {
      public static final DateType DATE = new DateType(Date.class) {
         protected Date deserialize(Date date) {
            return date;
         }
      };
      private final Class dateClass;

      protected DateType(Class dateClass) {
         this.dateClass = dateClass;
      }

      protected abstract Date deserialize(Date var1);

      private TypeAdapterFactory createFactory(DefaultDateTypeAdapter adapter) {
         return TypeAdapters.newFactory((Class)this.dateClass, adapter);
      }

      public final TypeAdapterFactory createAdapterFactory(String datePattern) {
         return this.createFactory(new DefaultDateTypeAdapter(this, datePattern));
      }

      public final TypeAdapterFactory createAdapterFactory(int dateStyle, int timeStyle) {
         return this.createFactory(new DefaultDateTypeAdapter(this, dateStyle, timeStyle));
      }
   }
}
