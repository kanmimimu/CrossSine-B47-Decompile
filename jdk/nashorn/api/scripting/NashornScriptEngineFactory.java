package jdk.nashorn.api.scripting;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import jdk.Exported;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.Version;

@Exported
public final class NashornScriptEngineFactory implements ScriptEngineFactory {
   private static final String[] DEFAULT_OPTIONS = new String[]{"-doe"};
   private static final List names = immutableList("nashorn", "Nashorn", "js", "JS", "JavaScript", "javascript", "ECMAScript", "ecmascript");
   private static final List mimeTypes = immutableList("application/javascript", "application/ecmascript", "text/javascript", "text/ecmascript");
   private static final List extensions = immutableList("js");

   public String getEngineName() {
      return (String)this.getParameter("javax.script.engine");
   }

   public String getEngineVersion() {
      return (String)this.getParameter("javax.script.engine_version");
   }

   public List getExtensions() {
      return Collections.unmodifiableList(extensions);
   }

   public String getLanguageName() {
      return (String)this.getParameter("javax.script.language");
   }

   public String getLanguageVersion() {
      return (String)this.getParameter("javax.script.language_version");
   }

   public String getMethodCallSyntax(String obj, String method, String... args) {
      StringBuilder sb = (new StringBuilder()).append(obj).append('.').append(method).append('(');
      int len = args.length;
      if (len > 0) {
         sb.append(args[0]);
      }

      for(int i = 1; i < len; ++i) {
         sb.append(',').append(args[i]);
      }

      sb.append(')');
      return sb.toString();
   }

   public List getMimeTypes() {
      return Collections.unmodifiableList(mimeTypes);
   }

   public List getNames() {
      return Collections.unmodifiableList(names);
   }

   public String getOutputStatement(String toDisplay) {
      return "print(" + toDisplay + ")";
   }

   public Object getParameter(String key) {
      switch (key) {
         case "javax.script.name":
            return "javascript";
         case "javax.script.engine":
            return "Oracle Nashorn";
         case "javax.script.engine_version":
            return Version.version();
         case "javax.script.language":
            return "ECMAScript";
         case "javax.script.language_version":
            return "ECMA - 262 Edition 5.1";
         case "THREADING":
            return null;
         default:
            return null;
      }
   }

   public String getProgram(String... statements) {
      StringBuilder sb = new StringBuilder();

      for(String statement : statements) {
         sb.append(statement).append(';');
      }

      return sb.toString();
   }

   public ScriptEngine getScriptEngine() {
      try {
         return new NashornScriptEngine(this, DEFAULT_OPTIONS, getAppClassLoader(), (ClassFilter)null);
      } catch (RuntimeException var2) {
         if (Context.DEBUG) {
            var2.printStackTrace();
         }

         throw var2;
      }
   }

   public ScriptEngine getScriptEngine(ClassLoader appLoader) {
      return this.newEngine(DEFAULT_OPTIONS, appLoader, (ClassFilter)null);
   }

   public ScriptEngine getScriptEngine(ClassFilter classFilter) {
      return this.newEngine(DEFAULT_OPTIONS, getAppClassLoader(), (ClassFilter)Objects.requireNonNull(classFilter));
   }

   public ScriptEngine getScriptEngine(String... args) {
      return this.newEngine((String[])Objects.requireNonNull(args), getAppClassLoader(), (ClassFilter)null);
   }

   public ScriptEngine getScriptEngine(String[] args, ClassLoader appLoader) {
      return this.newEngine((String[])Objects.requireNonNull(args), appLoader, (ClassFilter)null);
   }

   public ScriptEngine getScriptEngine(String[] args, ClassLoader appLoader, ClassFilter classFilter) {
      return this.newEngine((String[])Objects.requireNonNull(args), appLoader, (ClassFilter)Objects.requireNonNull(classFilter));
   }

   private ScriptEngine newEngine(String[] args, ClassLoader appLoader, ClassFilter classFilter) {
      checkConfigPermission();

      try {
         return new NashornScriptEngine(this, args, appLoader, classFilter);
      } catch (RuntimeException var5) {
         if (Context.DEBUG) {
            var5.printStackTrace();
         }

         throw var5;
      }
   }

   private static void checkConfigPermission() {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new RuntimePermission("nashorn.setConfig"));
      }

   }

   private static List immutableList(String... elements) {
      return Collections.unmodifiableList(Arrays.asList(elements));
   }

   private static ClassLoader getAppClassLoader() {
      ClassLoader ccl = Thread.currentThread().getContextClassLoader();
      return ccl == null ? NashornScriptEngineFactory.class.getClassLoader() : ccl;
   }
}
