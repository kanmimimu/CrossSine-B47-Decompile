package jdk.nashorn.internal.runtime;

import jdk.nashorn.api.scripting.NashornException;
import jdk.nashorn.internal.parser.Lexer;
import jdk.nashorn.internal.parser.Token;
import jdk.nashorn.internal.parser.TokenStream;
import jdk.nashorn.internal.parser.TokenType;

public final class Debug {
   private Debug() {
   }

   public static String firstJSFrame(Throwable t) {
      for(StackTraceElement ste : t.getStackTrace()) {
         if (ECMAErrors.isScriptFrame(ste)) {
            return ste.toString();
         }
      }

      return "<native code>";
   }

   public static String firstJSFrame() {
      return firstJSFrame(new Throwable());
   }

   public static String scriptStack() {
      return NashornException.getScriptStackString(new Throwable());
   }

   public static String id(Object x) {
      return String.format("0x%08x", System.identityHashCode(x));
   }

   public static int intId(Object x) {
      return System.identityHashCode(x);
   }

   public static String stackTraceElementAt(int depth) {
      return (new Throwable()).getStackTrace()[depth + 1].toString();
   }

   public static String caller(int depth, int count, String... ignores) {
      String result = "";
      StackTraceElement[] callers = Thread.currentThread().getStackTrace();
      int c = count;

      label33:
      for(int i = depth + 1; i < callers.length && c != 0; ++i) {
         StackTraceElement element = callers[i];
         String method = element.getMethodName();

         for(String ignore : ignores) {
            if (method.compareTo(ignore) == 0) {
               continue label33;
            }
         }

         result = result + (method + ":" + element.getLineNumber() + "                              ").substring(0, 30);
         --c;
      }

      return result.isEmpty() ? "<no caller>" : result;
   }

   public static void dumpTokens(Source source, Lexer lexer, TokenStream stream) {
      int k = 0;

      while(true) {
         while(k <= stream.last()) {
            long token = stream.get(k);
            TokenType type = Token.descType(token);
            System.out.println("" + k + ": " + Token.toString(source, token, true));
            ++k;
            if (type == TokenType.EOF) {
               return;
            }
         }

         lexer.lexify();
      }
   }
}
