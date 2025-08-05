package jdk.nashorn.api.scripting;

import java.nio.ByteBuffer;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import javax.script.Bindings;
import jdk.Exported;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.ConsString;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ECMAException;
import jdk.nashorn.internal.runtime.JSONListAdapter;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.arrays.ArrayData;

@Exported
public final class ScriptObjectMirror extends AbstractJSObject implements Bindings {
   private static final AccessControlContext GET_CONTEXT_ACC_CTXT = getContextAccCtxt();
   private final ScriptObject sobj;
   private final Global global;
   private final boolean strict;
   private final boolean jsonCompatible;

   private static AccessControlContext getContextAccCtxt() {
      Permissions perms = new Permissions();
      perms.add(new RuntimePermission("nashorn.getContext"));
      return new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain((CodeSource)null, perms)});
   }

   public boolean equals(Object other) {
      return other instanceof ScriptObjectMirror ? this.sobj.equals(((ScriptObjectMirror)other).sobj) : false;
   }

   public int hashCode() {
      return this.sobj.hashCode();
   }

   public String toString() {
      return (String)this.inGlobal(new Callable() {
         public String call() {
            return ScriptRuntime.safeToString(ScriptObjectMirror.this.sobj);
         }
      });
   }

   public Object call(Object thiz, Object... args) {
      Global oldGlobal = Context.getGlobal();
      boolean globalChanged = oldGlobal != this.global;

      Object var7;
      try {
         if (globalChanged) {
            Context.setGlobal(this.global);
         }

         if (!(this.sobj instanceof ScriptFunction)) {
            throw new RuntimeException("not a function: " + this.toString());
         }

         Object[] modArgs = globalChanged ? this.wrapArrayLikeMe(args, oldGlobal) : args;
         Object self = globalChanged ? this.wrapLikeMe(thiz, oldGlobal) : thiz;
         var7 = this.wrapLikeMe(ScriptRuntime.apply((ScriptFunction)this.sobj, unwrap(self, this.global), unwrapArray(modArgs, this.global)));
      } catch (NashornException ne) {
         throw ne.initEcmaError(this.global);
      } catch (Error | RuntimeException e) {
         throw e;
      } catch (Throwable t) {
         throw new RuntimeException(t);
      } finally {
         if (globalChanged) {
            Context.setGlobal(oldGlobal);
         }

      }

      return var7;
   }

   public Object newObject(Object... args) {
      Global oldGlobal = Context.getGlobal();
      boolean globalChanged = oldGlobal != this.global;

      Object var5;
      try {
         if (globalChanged) {
            Context.setGlobal(this.global);
         }

         if (!(this.sobj instanceof ScriptFunction)) {
            throw new RuntimeException("not a constructor: " + this.toString());
         }

         Object[] modArgs = globalChanged ? this.wrapArrayLikeMe(args, oldGlobal) : args;
         var5 = this.wrapLikeMe(ScriptRuntime.construct((ScriptFunction)this.sobj, unwrapArray(modArgs, this.global)));
      } catch (NashornException ne) {
         throw ne.initEcmaError(this.global);
      } catch (Error | RuntimeException e) {
         throw e;
      } catch (Throwable t) {
         throw new RuntimeException(t);
      } finally {
         if (globalChanged) {
            Context.setGlobal(oldGlobal);
         }

      }

      return var5;
   }

   public Object eval(final String s) {
      return this.inGlobal(new Callable() {
         public Object call() {
            Context context = (Context)AccessController.doPrivileged(new PrivilegedAction() {
               public Context run() {
                  return Context.getContext();
               }
            }, ScriptObjectMirror.GET_CONTEXT_ACC_CTXT);
            return ScriptObjectMirror.this.wrapLikeMe(context.eval(ScriptObjectMirror.this.global, s, ScriptObjectMirror.this.sobj, (Object)null));
         }
      });
   }

   public Object callMember(String functionName, Object... args) {
      Objects.requireNonNull(functionName);
      Global oldGlobal = Context.getGlobal();
      boolean globalChanged = oldGlobal != this.global;

      Object var7;
      try {
         if (globalChanged) {
            Context.setGlobal(this.global);
         }

         Object val = this.sobj.get(functionName);
         if (!(val instanceof ScriptFunction)) {
            if (val instanceof JSObject && ((JSObject)val).isFunction()) {
               Object var17 = ((JSObject)val).call(this.sobj, args);
               return var17;
            }

            throw new NoSuchMethodException("No such function " + functionName);
         }

         Object[] modArgs = globalChanged ? this.wrapArrayLikeMe(args, oldGlobal) : args;
         var7 = this.wrapLikeMe(ScriptRuntime.apply((ScriptFunction)val, this.sobj, unwrapArray(modArgs, this.global)));
      } catch (NashornException ne) {
         throw ne.initEcmaError(this.global);
      } catch (Error | RuntimeException e) {
         throw e;
      } catch (Throwable t) {
         throw new RuntimeException(t);
      } finally {
         if (globalChanged) {
            Context.setGlobal(oldGlobal);
         }

      }

      return var7;
   }

   public Object getMember(final String name) {
      Objects.requireNonNull(name);
      return this.inGlobal(new Callable() {
         public Object call() {
            return ScriptObjectMirror.this.wrapLikeMe(ScriptObjectMirror.this.sobj.get(name));
         }
      });
   }

   public Object getSlot(final int index) {
      return this.inGlobal(new Callable() {
         public Object call() {
            return ScriptObjectMirror.this.wrapLikeMe(ScriptObjectMirror.this.sobj.get(index));
         }
      });
   }

   public boolean hasMember(final String name) {
      Objects.requireNonNull(name);
      return (Boolean)this.inGlobal(new Callable() {
         public Boolean call() {
            return ScriptObjectMirror.this.sobj.has(name);
         }
      });
   }

   public boolean hasSlot(final int slot) {
      return (Boolean)this.inGlobal(new Callable() {
         public Boolean call() {
            return ScriptObjectMirror.this.sobj.has(slot);
         }
      });
   }

   public void removeMember(String name) {
      this.remove(Objects.requireNonNull(name));
   }

   public void setMember(String name, Object value) {
      this.put((String)Objects.requireNonNull(name), value);
   }

   public void setSlot(final int index, final Object value) {
      this.inGlobal(new Callable() {
         public Void call() {
            ScriptObjectMirror.this.sobj.set(index, ScriptObjectMirror.unwrap(value, ScriptObjectMirror.this.global), ScriptObjectMirror.this.getCallSiteFlags());
            return null;
         }
      });
   }

   public void setIndexedPropertiesToExternalArrayData(final ByteBuffer buf) {
      this.inGlobal(new Callable() {
         public Void call() {
            ScriptObjectMirror.this.sobj.setArray(ArrayData.allocate(buf));
            return null;
         }
      });
   }

   public boolean isInstance(Object instance) {
      if (!(instance instanceof ScriptObjectMirror)) {
         return false;
      } else {
         final ScriptObjectMirror mirror = (ScriptObjectMirror)instance;
         return this.global != mirror.global ? false : (Boolean)this.inGlobal(new Callable() {
            public Boolean call() {
               return ScriptObjectMirror.this.sobj.isInstance(mirror.sobj);
            }
         });
      }
   }

   public String getClassName() {
      return this.sobj.getClassName();
   }

   public boolean isFunction() {
      return this.sobj instanceof ScriptFunction;
   }

   public boolean isStrictFunction() {
      return this.isFunction() && ((ScriptFunction)this.sobj).isStrict();
   }

   public boolean isArray() {
      return this.sobj.isArray();
   }

   public void clear() {
      this.inGlobal(new Callable() {
         public Object call() {
            ScriptObjectMirror.this.sobj.clear(ScriptObjectMirror.this.strict);
            return null;
         }
      });
   }

   public boolean containsKey(final Object key) {
      checkKey(key);
      return (Boolean)this.inGlobal(new Callable() {
         public Boolean call() {
            return ScriptObjectMirror.this.sobj.containsKey(key);
         }
      });
   }

   public boolean containsValue(final Object value) {
      return (Boolean)this.inGlobal(new Callable() {
         public Boolean call() {
            return ScriptObjectMirror.this.sobj.containsValue(ScriptObjectMirror.unwrap(value, ScriptObjectMirror.this.global));
         }
      });
   }

   public Set entrySet() {
      return (Set)this.inGlobal(new Callable() {
         public Set call() {
            Iterator<String> iter = ScriptObjectMirror.this.sobj.propertyIterator();
            Set<Map.Entry<String, Object>> entries = new LinkedHashSet();

            while(iter.hasNext()) {
               String key = (String)iter.next();
               Object value = ScriptObjectMirror.translateUndefined(ScriptObjectMirror.this.wrapLikeMe(ScriptObjectMirror.this.sobj.get(key)));
               entries.add(new AbstractMap.SimpleImmutableEntry(key, value));
            }

            return Collections.unmodifiableSet(entries);
         }
      });
   }

   public Object get(final Object key) {
      checkKey(key);
      return this.inGlobal(new Callable() {
         public Object call() {
            return ScriptObjectMirror.translateUndefined(ScriptObjectMirror.this.wrapLikeMe(ScriptObjectMirror.this.sobj.get(key)));
         }
      });
   }

   public boolean isEmpty() {
      return (Boolean)this.inGlobal(new Callable() {
         public Boolean call() {
            return ScriptObjectMirror.this.sobj.isEmpty();
         }
      });
   }

   public Set keySet() {
      return (Set)this.inGlobal(new Callable() {
         public Set call() {
            Iterator<String> iter = ScriptObjectMirror.this.sobj.propertyIterator();
            Set<String> keySet = new LinkedHashSet();

            while(iter.hasNext()) {
               keySet.add(iter.next());
            }

            return Collections.unmodifiableSet(keySet);
         }
      });
   }

   public Object put(final String key, final Object value) {
      checkKey(key);
      final ScriptObject oldGlobal = Context.getGlobal();
      final boolean globalChanged = oldGlobal != this.global;
      return this.inGlobal(new Callable() {
         public Object call() {
            Object modValue = globalChanged ? ScriptObjectMirror.this.wrapLikeMe(value, oldGlobal) : value;
            return ScriptObjectMirror.translateUndefined(ScriptObjectMirror.this.wrapLikeMe(ScriptObjectMirror.this.sobj.put(key, ScriptObjectMirror.unwrap(modValue, ScriptObjectMirror.this.global), ScriptObjectMirror.this.strict)));
         }
      });
   }

   public void putAll(final Map map) {
      Objects.requireNonNull(map);
      final ScriptObject oldGlobal = Context.getGlobal();
      final boolean globalChanged = oldGlobal != this.global;
      this.inGlobal(new Callable() {
         public Object call() {
            for(Map.Entry entry : map.entrySet()) {
               Object value = entry.getValue();
               Object modValue = globalChanged ? ScriptObjectMirror.this.wrapLikeMe(value, oldGlobal) : value;
               String key = (String)entry.getKey();
               ScriptObjectMirror.checkKey(key);
               ScriptObjectMirror.this.sobj.set(key, ScriptObjectMirror.unwrap(modValue, ScriptObjectMirror.this.global), ScriptObjectMirror.this.getCallSiteFlags());
            }

            return null;
         }
      });
   }

   public Object remove(final Object key) {
      checkKey(key);
      return this.inGlobal(new Callable() {
         public Object call() {
            return ScriptObjectMirror.translateUndefined(ScriptObjectMirror.this.wrapLikeMe(ScriptObjectMirror.this.sobj.remove(key, ScriptObjectMirror.this.strict)));
         }
      });
   }

   public boolean delete(final Object key) {
      return (Boolean)this.inGlobal(new Callable() {
         public Boolean call() {
            return ScriptObjectMirror.this.sobj.delete(ScriptObjectMirror.unwrap(key, ScriptObjectMirror.this.global), ScriptObjectMirror.this.strict);
         }
      });
   }

   public int size() {
      return (Integer)this.inGlobal(new Callable() {
         public Integer call() {
            return ScriptObjectMirror.this.sobj.size();
         }
      });
   }

   public Collection values() {
      return (Collection)this.inGlobal(new Callable() {
         public Collection call() {
            List<Object> values = new ArrayList(ScriptObjectMirror.this.size());
            Iterator<Object> iter = ScriptObjectMirror.this.sobj.valueIterator();

            while(iter.hasNext()) {
               values.add(ScriptObjectMirror.translateUndefined(ScriptObjectMirror.this.wrapLikeMe(iter.next())));
            }

            return Collections.unmodifiableList(values);
         }
      });
   }

   public Object getProto() {
      return this.inGlobal(new Callable() {
         public Object call() {
            return ScriptObjectMirror.this.wrapLikeMe(ScriptObjectMirror.this.sobj.getProto());
         }
      });
   }

   public void setProto(final Object proto) {
      this.inGlobal(new Callable() {
         public Void call() {
            ScriptObjectMirror.this.sobj.setPrototypeOf(ScriptObjectMirror.unwrap(proto, ScriptObjectMirror.this.global));
            return null;
         }
      });
   }

   public Object getOwnPropertyDescriptor(final String key) {
      return this.inGlobal(new Callable() {
         public Object call() {
            return ScriptObjectMirror.this.wrapLikeMe(ScriptObjectMirror.this.sobj.getOwnPropertyDescriptor(key));
         }
      });
   }

   public String[] getOwnKeys(final boolean all) {
      return (String[])this.inGlobal(new Callable() {
         public String[] call() {
            return ScriptObjectMirror.this.sobj.getOwnKeys(all);
         }
      });
   }

   public ScriptObjectMirror preventExtensions() {
      return (ScriptObjectMirror)this.inGlobal(new Callable() {
         public ScriptObjectMirror call() {
            ScriptObjectMirror.this.sobj.preventExtensions();
            return ScriptObjectMirror.this;
         }
      });
   }

   public boolean isExtensible() {
      return (Boolean)this.inGlobal(new Callable() {
         public Boolean call() {
            return ScriptObjectMirror.this.sobj.isExtensible();
         }
      });
   }

   public ScriptObjectMirror seal() {
      return (ScriptObjectMirror)this.inGlobal(new Callable() {
         public ScriptObjectMirror call() {
            ScriptObjectMirror.this.sobj.seal();
            return ScriptObjectMirror.this;
         }
      });
   }

   public boolean isSealed() {
      return (Boolean)this.inGlobal(new Callable() {
         public Boolean call() {
            return ScriptObjectMirror.this.sobj.isSealed();
         }
      });
   }

   public ScriptObjectMirror freeze() {
      return (ScriptObjectMirror)this.inGlobal(new Callable() {
         public ScriptObjectMirror call() {
            ScriptObjectMirror.this.sobj.freeze();
            return ScriptObjectMirror.this;
         }
      });
   }

   public boolean isFrozen() {
      return (Boolean)this.inGlobal(new Callable() {
         public Boolean call() {
            return ScriptObjectMirror.this.sobj.isFrozen();
         }
      });
   }

   public static boolean isUndefined(Object obj) {
      return obj == ScriptRuntime.UNDEFINED;
   }

   public Object to(final Class type) {
      return this.inGlobal(new Callable() {
         public Object call() {
            return type.cast(ScriptUtils.convert(ScriptObjectMirror.this.sobj, type));
         }
      });
   }

   public static Object wrap(Object obj, Object homeGlobal) {
      return wrap(obj, homeGlobal, false);
   }

   public static Object wrapAsJSONCompatible(Object obj, Object homeGlobal) {
      return wrap(obj, homeGlobal, true);
   }

   private static Object wrap(Object obj, Object homeGlobal, boolean jsonCompatible) {
      if (obj instanceof ScriptObject) {
         if (!(homeGlobal instanceof Global)) {
            return obj;
         } else {
            ScriptObject sobj = (ScriptObject)obj;
            Global global = (Global)homeGlobal;
            ScriptObjectMirror mirror = new ScriptObjectMirror(sobj, global, jsonCompatible);
            return jsonCompatible && sobj.isArray() ? new JSONListAdapter(mirror, global) : mirror;
         }
      } else if (obj instanceof ConsString) {
         return obj.toString();
      } else {
         return jsonCompatible && obj instanceof ScriptObjectMirror ? ((ScriptObjectMirror)obj).asJSONCompatible() : obj;
      }
   }

   private Object wrapLikeMe(Object obj, Object homeGlobal) {
      return wrap(obj, homeGlobal, this.jsonCompatible);
   }

   private Object wrapLikeMe(Object obj) {
      return this.wrapLikeMe(obj, this.global);
   }

   public static Object unwrap(Object obj, Object homeGlobal) {
      if (obj instanceof ScriptObjectMirror) {
         ScriptObjectMirror mirror = (ScriptObjectMirror)obj;
         return mirror.global == homeGlobal ? mirror.sobj : obj;
      } else {
         return obj instanceof JSONListAdapter ? ((JSONListAdapter)obj).unwrap(homeGlobal) : obj;
      }
   }

   public static Object[] wrapArray(Object[] args, Object homeGlobal) {
      return wrapArray(args, homeGlobal, false);
   }

   private static Object[] wrapArray(Object[] args, Object homeGlobal, boolean jsonCompatible) {
      if (args != null && args.length != 0) {
         Object[] newArgs = new Object[args.length];
         int index = 0;

         for(Object obj : args) {
            newArgs[index] = wrap(obj, homeGlobal, jsonCompatible);
            ++index;
         }

         return newArgs;
      } else {
         return args;
      }
   }

   private Object[] wrapArrayLikeMe(Object[] args, Object homeGlobal) {
      return wrapArray(args, homeGlobal, this.jsonCompatible);
   }

   public static Object[] unwrapArray(Object[] args, Object homeGlobal) {
      if (args != null && args.length != 0) {
         Object[] newArgs = new Object[args.length];
         int index = 0;

         for(Object obj : args) {
            newArgs[index] = unwrap(obj, homeGlobal);
            ++index;
         }

         return newArgs;
      } else {
         return args;
      }
   }

   public static boolean identical(Object obj1, Object obj2) {
      Object o1 = obj1 instanceof ScriptObjectMirror ? ((ScriptObjectMirror)obj1).sobj : obj1;
      Object o2 = obj2 instanceof ScriptObjectMirror ? ((ScriptObjectMirror)obj2).sobj : obj2;
      return o1 == o2;
   }

   ScriptObjectMirror(ScriptObject sobj, Global global) {
      this(sobj, global, false);
   }

   private ScriptObjectMirror(ScriptObject sobj, Global global, boolean jsonCompatible) {
      assert sobj != null : "ScriptObjectMirror on null!";

      assert global != null : "home Global is null";

      this.sobj = sobj;
      this.global = global;
      this.strict = global.isStrictContext();
      this.jsonCompatible = jsonCompatible;
   }

   ScriptObject getScriptObject() {
      return this.sobj;
   }

   Global getHomeGlobal() {
      return this.global;
   }

   static Object translateUndefined(Object obj) {
      return obj == ScriptRuntime.UNDEFINED ? null : obj;
   }

   private int getCallSiteFlags() {
      return this.strict ? 2 : 0;
   }

   private Object inGlobal(Callable callable) {
      Global oldGlobal = Context.getGlobal();
      boolean globalChanged = oldGlobal != this.global;
      if (globalChanged) {
         Context.setGlobal(this.global);
      }

      Object var4;
      try {
         var4 = callable.call();
      } catch (NashornException ne) {
         throw ne.initEcmaError(this.global);
      } catch (RuntimeException e) {
         throw e;
      } catch (Exception e) {
         throw new AssertionError("Cannot happen", e);
      } finally {
         if (globalChanged) {
            Context.setGlobal(oldGlobal);
         }

      }

      return var4;
   }

   private static void checkKey(Object key) {
      Objects.requireNonNull(key, "key can not be null");
      if (!(key instanceof String)) {
         throw new ClassCastException("key should be a String. It is " + key.getClass().getName() + " instead.");
      } else if (((String)key).length() == 0) {
         throw new IllegalArgumentException("key can not be empty");
      }
   }

   /** @deprecated */
   @Deprecated
   public double toNumber() {
      return (Double)this.inGlobal(new Callable() {
         public Double call() {
            return JSType.toNumber(ScriptObjectMirror.this.sobj);
         }
      });
   }

   public Object getDefaultValue(final Class hint) {
      return this.inGlobal(new Callable() {
         public Object call() {
            try {
               return ScriptObjectMirror.this.sobj.getDefaultValue(hint);
            } catch (ECMAException e) {
               throw new UnsupportedOperationException(e.getMessage(), e);
            }
         }
      });
   }

   private ScriptObjectMirror asJSONCompatible() {
      return this.jsonCompatible ? this : new ScriptObjectMirror(this.sobj, this.global, true);
   }
}
