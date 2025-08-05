package jdk.nashorn.internal.runtime.options;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;

public class LoggingOption extends KeyValueOption {
   private final Map loggers = new HashMap();

   LoggingOption(String value) {
      super(value);
      this.initialize(this.getValues());
   }

   public Map getLoggers() {
      return Collections.unmodifiableMap(this.loggers);
   }

   private void initialize(Map logMap) throws IllegalArgumentException {
      try {
         for(Map.Entry entry : logMap.entrySet()) {
            String name = lastPart((String)entry.getKey());
            String levelString = ((String)entry.getValue()).toUpperCase(Locale.ENGLISH);
            Level level;
            boolean isQuiet;
            if ("".equals(levelString)) {
               level = Level.INFO;
               isQuiet = false;
            } else if ("QUIET".equals(levelString)) {
               level = Level.INFO;
               isQuiet = true;
            } else {
               level = Level.parse(levelString);
               isQuiet = false;
            }

            this.loggers.put(name, new LoggerInfo(level, isQuiet));
         }

      } catch (SecurityException | IllegalArgumentException e) {
         throw e;
      }
   }

   private static String lastPart(String packageName) {
      String[] parts = packageName.split("\\.");
      return parts.length == 0 ? packageName : parts[parts.length - 1];
   }

   public static class LoggerInfo {
      private final Level level;
      private final boolean isQuiet;

      LoggerInfo(Level level, boolean isQuiet) {
         this.level = level;
         this.isQuiet = isQuiet;
      }

      public Level getLevel() {
         return this.level;
      }

      public boolean isQuiet() {
         return this.isQuiet;
      }
   }
}
