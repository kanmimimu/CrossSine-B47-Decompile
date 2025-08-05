package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import jdk.internal.dynalink.beans.BeansLinker;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.internal.dynalink.support.LinkRequestImpl;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.ECMAException;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.Property;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import jdk.nashorn.internal.runtime.linker.InvokeByName;
import jdk.nashorn.internal.runtime.linker.NashornBeansLinker;

public final class NativeObject {
   public static final MethodHandle GET__PROTO__ = findOwnMH("get__proto__", ScriptObject.class, Object.class);
   public static final MethodHandle SET__PROTO__ = findOwnMH("set__proto__", Object.class, Object.class, Object.class);
   private static final Object TO_STRING = new Object();
   private static final MethodType MIRROR_GETTER_TYPE = MethodType.methodType(Object.class, ScriptObjectMirror.class);
   private static final MethodType MIRROR_SETTER_TYPE = MethodType.methodType(Object.class, ScriptObjectMirror.class, Object.class);
   private static PropertyMap $nasgenmap$;

   private static InvokeByName getTO_STRING() {
      return Global.instance().getInvokeByName(TO_STRING, new Callable() {
         public InvokeByName call() {
            return new InvokeByName("toString", ScriptObject.class);
         }
      });
   }

   private static ScriptObject get__proto__(Object self) {
      ScriptObject sobj = Global.checkObject(Global.toObject(self));
      return sobj.getProto();
   }

   private static Object set__proto__(Object self, Object proto) {
      Global.checkObjectCoercible(self);
      if (!(self instanceof ScriptObject)) {
         return ScriptRuntime.UNDEFINED;
      } else {
         ScriptObject sobj = (ScriptObject)self;
         if (proto == null || proto instanceof ScriptObject) {
            sobj.setPrototypeOf(proto);
         }

         return ScriptRuntime.UNDEFINED;
      }
   }

   private NativeObject() {
      throw new UnsupportedOperationException();
   }

   private static ECMAException notAnObject(Object obj) {
      return ECMAErrors.typeError("not.an.object", ScriptRuntime.safeToString(obj));
   }

   public static ScriptObject setIndexedPropertiesToExternalArrayData(Object self, Object obj, Object buf) {
      Global.checkObject(obj);
      ScriptObject sobj = (ScriptObject)obj;
      if (buf instanceof ByteBuffer) {
         sobj.setArray(ArrayData.allocate((ByteBuffer)buf));
         return sobj;
      } else {
         throw ECMAErrors.typeError("not.a.bytebuffer", "setIndexedPropertiesToExternalArrayData's buf argument");
      }
   }

   public static Object getPrototypeOf(Object self, Object obj) {
      if (obj instanceof ScriptObject) {
         return ((ScriptObject)obj).getProto();
      } else if (obj instanceof ScriptObjectMirror) {
         return ((ScriptObjectMirror)obj).getProto();
      } else {
         JSType type = JSType.of(obj);
         if (type == JSType.OBJECT) {
            return null;
         } else {
            throw notAnObject(obj);
         }
      }
   }

   public static Object setPrototypeOf(Object self, Object obj, Object proto) {
      if (obj instanceof ScriptObject) {
         ((ScriptObject)obj).setPrototypeOf(proto);
         return obj;
      } else if (obj instanceof ScriptObjectMirror) {
         ((ScriptObjectMirror)obj).setProto(proto);
         return obj;
      } else {
         throw notAnObject(obj);
      }
   }

   public static Object getOwnPropertyDescriptor(Object self, Object obj, Object prop) {
      if (obj instanceof ScriptObject) {
         String key = JSType.toString(prop);
         ScriptObject sobj = (ScriptObject)obj;
         return sobj.getOwnPropertyDescriptor(key);
      } else if (obj instanceof ScriptObjectMirror) {
         String key = JSType.toString(prop);
         ScriptObjectMirror sobjMirror = (ScriptObjectMirror)obj;
         return sobjMirror.getOwnPropertyDescriptor(key);
      } else {
         throw notAnObject(obj);
      }
   }

   public static ScriptObject getOwnPropertyNames(Object self, Object obj) {
      if (obj instanceof ScriptObject) {
         return new NativeArray(((ScriptObject)obj).getOwnKeys(true));
      } else if (obj instanceof ScriptObjectMirror) {
         return new NativeArray(((ScriptObjectMirror)obj).getOwnKeys(true));
      } else {
         throw notAnObject(obj);
      }
   }

   public static ScriptObject create(Object self, Object proto, Object props) {
      if (proto != null) {
         Global.checkObject(proto);
      }

      ScriptObject newObj = Global.newEmptyInstance();
      newObj.setProto((ScriptObject)proto);
      if (props != ScriptRuntime.UNDEFINED) {
         defineProperties(self, newObj, props);
      }

      return newObj;
   }

   public static ScriptObject defineProperty(Object self, Object obj, Object prop, Object attr) {
      ScriptObject sobj = Global.checkObject(obj);
      sobj.defineOwnProperty(JSType.toString(prop), attr, true);
      return sobj;
   }

   public static ScriptObject defineProperties(Object self, Object obj, Object props) {
      ScriptObject sobj = Global.checkObject(obj);
      Object propsObj = Global.toObject(props);
      if (propsObj instanceof ScriptObject) {
         Object[] keys = ((ScriptObject)propsObj).getOwnKeys(false);

         for(Object key : keys) {
            String prop = JSType.toString(key);
            sobj.defineOwnProperty(prop, ((ScriptObject)propsObj).get(prop), true);
         }
      }

      return sobj;
   }

   public static Object seal(Object self, Object obj) {
      if (obj instanceof ScriptObject) {
         return ((ScriptObject)obj).seal();
      } else if (obj instanceof ScriptObjectMirror) {
         return ((ScriptObjectMirror)obj).seal();
      } else {
         throw notAnObject(obj);
      }
   }

   public static Object freeze(Object self, Object obj) {
      if (obj instanceof ScriptObject) {
         return ((ScriptObject)obj).freeze();
      } else if (obj instanceof ScriptObjectMirror) {
         return ((ScriptObjectMirror)obj).freeze();
      } else {
         throw notAnObject(obj);
      }
   }

   public static Object preventExtensions(Object self, Object obj) {
      if (obj instanceof ScriptObject) {
         return ((ScriptObject)obj).preventExtensions();
      } else if (obj instanceof ScriptObjectMirror) {
         return ((ScriptObjectMirror)obj).preventExtensions();
      } else {
         throw notAnObject(obj);
      }
   }

   public static boolean isSealed(Object self, Object obj) {
      if (obj instanceof ScriptObject) {
         return ((ScriptObject)obj).isSealed();
      } else if (obj instanceof ScriptObjectMirror) {
         return ((ScriptObjectMirror)obj).isSealed();
      } else {
         throw notAnObject(obj);
      }
   }

   public static boolean isFrozen(Object self, Object obj) {
      if (obj instanceof ScriptObject) {
         return ((ScriptObject)obj).isFrozen();
      } else if (obj instanceof ScriptObjectMirror) {
         return ((ScriptObjectMirror)obj).isFrozen();
      } else {
         throw notAnObject(obj);
      }
   }

   public static boolean isExtensible(Object self, Object obj) {
      if (obj instanceof ScriptObject) {
         return ((ScriptObject)obj).isExtensible();
      } else if (obj instanceof ScriptObjectMirror) {
         return ((ScriptObjectMirror)obj).isExtensible();
      } else {
         throw notAnObject(obj);
      }
   }

   public static ScriptObject keys(Object self, Object obj) {
      if (obj instanceof ScriptObject) {
         ScriptObject sobj = (ScriptObject)obj;
         return new NativeArray(sobj.getOwnKeys(false));
      } else if (obj instanceof ScriptObjectMirror) {
         ScriptObjectMirror sobjMirror = (ScriptObjectMirror)obj;
         return new NativeArray(sobjMirror.getOwnKeys(false));
      } else {
         throw notAnObject(obj);
      }
   }

   public static Object construct(boolean newObj, Object self, Object value) {
      JSType type = JSType.ofNoFunction(value);
      if (!newObj && type != JSType.NULL && type != JSType.UNDEFINED) {
         return Global.toObject(value);
      } else {
         switch (type) {
            case BOOLEAN:
            case NUMBER:
            case STRING:
               return Global.toObject(value);
            case OBJECT:
               return value;
            case NULL:
            case UNDEFINED:
            default:
               return Global.newEmptyInstance();
         }
      }
   }

   public static String toString(Object self) {
      return ScriptRuntime.builtinObjectToString(self);
   }

   public static Object toLocaleString(Object self) {
      Object obj = JSType.toScriptObject(self);
      if (obj instanceof ScriptObject) {
         InvokeByName toStringInvoker = getTO_STRING();
         ScriptObject sobj = (ScriptObject)obj;

         try {
            Object toString = toStringInvoker.getGetter().invokeExact(sobj);
            if (Bootstrap.isCallable(toString)) {
               return toStringInvoker.getInvoker().invokeExact(toString, sobj);
            }
         } catch (Error | RuntimeException e) {
            throw e;
         } catch (Throwable t) {
            throw new RuntimeException(t);
         }

         throw ECMAErrors.typeError("not.a.function", "toString");
      } else {
         return ScriptRuntime.builtinObjectToString(self);
      }
   }

   public static Object valueOf(Object self) {
      return Global.toObject(self);
   }

   public static boolean hasOwnProperty(Object self, Object v) {
      Object key = JSType.toPrimitive(v, String.class);
      Object obj = Global.toObject(self);
      return obj instanceof ScriptObject && ((ScriptObject)obj).hasOwnProperty(key);
   }

   public static boolean isPrototypeOf(Object self, Object v) {
      if (!(v instanceof ScriptObject)) {
         return false;
      } else {
         Object obj = Global.toObject(self);
         ScriptObject proto = (ScriptObject)v;

         do {
            proto = proto.getProto();
            if (proto == obj) {
               return true;
            }
         } while(proto != null);

         return false;
      }
   }

   public static boolean propertyIsEnumerable(Object self, Object v) {
      String str = JSType.toString(v);
      Object obj = Global.toObject(self);
      if (!(obj instanceof ScriptObject)) {
         return false;
      } else {
         Property property = ((ScriptObject)obj).getMap().findProperty(str);
         return property != null && property.isEnumerable();
      }
   }

   public static Object bindProperties(Object self, Object target, Object source) {
      ScriptObject targetObj = Global.checkObject(target);
      Global.checkObjectCoercible(source);
      if (source instanceof ScriptObject) {
         ScriptObject sourceObj = (ScriptObject)source;
         PropertyMap sourceMap = sourceObj.getMap();
         Property[] properties = sourceMap.getProperties();
         ArrayList<Property> propList = new ArrayList();

         for(Property prop : properties) {
            if (prop.isEnumerable()) {
               Object value = sourceObj.get(prop.getKey());
               prop.setType(Object.class);
               prop.setValue(sourceObj, sourceObj, value, false);
               propList.add(prop);
            }
         }

         if (!propList.isEmpty()) {
            targetObj.addBoundProperties(sourceObj, (Property[])propList.toArray(new Property[propList.size()]));
         }
      } else if (source instanceof ScriptObjectMirror) {
         ScriptObjectMirror mirror = (ScriptObjectMirror)source;
         String[] keys = mirror.getOwnKeys(false);
         if (keys.length == 0) {
            return target;
         }

         AccessorProperty[] props = new AccessorProperty[keys.length];

         for(int idx = 0; idx < keys.length; ++idx) {
            String name = keys[idx];
            MethodHandle getter = Bootstrap.createDynamicInvoker("dyn:getMethod|getProp|getElem:" + name, MIRROR_GETTER_TYPE);
            MethodHandle setter = Bootstrap.createDynamicInvoker("dyn:setProp|setElem:" + name, MIRROR_SETTER_TYPE);
            props[idx] = AccessorProperty.create(name, 0, getter, setter);
         }

         targetObj.addBoundProperties(source, props);
      } else if (source instanceof StaticClass) {
         Class<?> clazz = ((StaticClass)source).getRepresentedClass();
         Bootstrap.checkReflectionAccess(clazz, true);
         bindBeanProperties(targetObj, source, BeansLinker.getReadableStaticPropertyNames(clazz), BeansLinker.getWritableStaticPropertyNames(clazz), BeansLinker.getStaticMethodNames(clazz));
      } else {
         Class<?> clazz = source.getClass();
         Bootstrap.checkReflectionAccess(clazz, false);
         bindBeanProperties(targetObj, source, BeansLinker.getReadableInstancePropertyNames(clazz), BeansLinker.getWritableInstancePropertyNames(clazz), BeansLinker.getInstanceMethodNames(clazz));
      }

      return target;
   }

   public static Object bindAllProperties(ScriptObject target, ScriptObjectMirror source) {
      Set<String> keys = source.keySet();
      AccessorProperty[] props = new AccessorProperty[keys.size()];
      int idx = 0;

      for(String name : keys) {
         MethodHandle getter = Bootstrap.createDynamicInvoker("dyn:getMethod|getProp|getElem:" + name, MIRROR_GETTER_TYPE);
         MethodHandle setter = Bootstrap.createDynamicInvoker("dyn:setProp|setElem:" + name, MIRROR_SETTER_TYPE);
         props[idx] = AccessorProperty.create(name, 0, getter, setter);
         ++idx;
      }

      target.addBoundProperties((Object)source, (AccessorProperty[])props);
      return target;
   }

   private static void bindBeanProperties(ScriptObject targetObj, Object source, Collection readablePropertyNames, Collection writablePropertyNames, Collection methodNames) {
      Set<String> propertyNames = new HashSet(readablePropertyNames);
      propertyNames.addAll(writablePropertyNames);
      Class<?> clazz = source.getClass();
      MethodType getterType = MethodType.methodType(Object.class, clazz);
      MethodType setterType = MethodType.methodType(Object.class, clazz, Object.class);
      GuardingDynamicLinker linker = BeansLinker.getLinkerForClass(clazz);
      List<AccessorProperty> properties = new ArrayList(propertyNames.size() + methodNames.size());

      for(String methodName : methodNames) {
         MethodHandle method;
         try {
            method = getBeanOperation(linker, "dyn:getMethod:" + methodName, getterType, source);
         } catch (IllegalAccessError var19) {
            continue;
         }

         properties.add(AccessorProperty.create(methodName, 1, getBoundBeanMethodGetter(source, method), Lookup.EMPTY_SETTER));
      }

      for(String propertyName : propertyNames) {
         MethodHandle getter;
         if (readablePropertyNames.contains(propertyName)) {
            try {
               getter = getBeanOperation(linker, "dyn:getProp:" + propertyName, getterType, source);
            } catch (IllegalAccessError var17) {
               getter = Lookup.EMPTY_GETTER;
            }
         } else {
            getter = Lookup.EMPTY_GETTER;
         }

         boolean isWritable = writablePropertyNames.contains(propertyName);
         MethodHandle setter;
         if (isWritable) {
            try {
               setter = getBeanOperation(linker, "dyn:setProp:" + propertyName, setterType, source);
            } catch (IllegalAccessError var18) {
               setter = Lookup.EMPTY_SETTER;
            }
         } else {
            setter = Lookup.EMPTY_SETTER;
         }

         if (getter != Lookup.EMPTY_GETTER || setter != Lookup.EMPTY_SETTER) {
            properties.add(AccessorProperty.create(propertyName, isWritable ? 0 : 1, getter, setter));
         }
      }

      targetObj.addBoundProperties(source, (AccessorProperty[])properties.toArray(new AccessorProperty[properties.size()]));
   }

   private static MethodHandle getBoundBeanMethodGetter(Object source, MethodHandle methodGetter) {
      try {
         return MethodHandles.dropArguments(MethodHandles.constant(Object.class, Bootstrap.bindCallable(methodGetter.invoke(source), source, (Object[])null)), 0, new Class[]{Object.class});
      } catch (Error | RuntimeException e) {
         throw e;
      } catch (Throwable t) {
         throw new RuntimeException(t);
      }
   }

   private static MethodHandle getBeanOperation(GuardingDynamicLinker linker, String operation, MethodType methodType, Object source) {
      GuardedInvocation inv;
      try {
         inv = NashornBeansLinker.getGuardedInvocation(linker, createLinkRequest(operation, methodType, source), Bootstrap.getLinkerServices());

         assert passesGuard(source, inv.getGuard());
      } catch (Error | RuntimeException e) {
         throw e;
      } catch (Throwable t) {
         throw new RuntimeException(t);
      }

      assert inv.getSwitchPoints() == null;

      return inv.getInvocation();
   }

   private static boolean passesGuard(Object obj, MethodHandle guard) throws Throwable {
      return guard == null || guard.invoke(obj);
   }

   private static LinkRequest createLinkRequest(String operation, MethodType methodType, Object source) {
      return new LinkRequestImpl(CallSiteDescriptorFactory.create(MethodHandles.publicLookup(), operation, methodType), (Object)null, 0, false, new Object[]{source});
   }

   private static MethodHandle findOwnMH(String name, Class rtype, Class... types) {
      return Lookup.MH.findStatic(MethodHandles.lookup(), NativeObject.class, name, Lookup.MH.type(rtype, types));
   }

   static {
      $clinit$();
   }

   public static void $clinit$() {
      $nasgenmap$ = PropertyMap.newMap((Collection)Collections.EMPTY_LIST);
   }
}
