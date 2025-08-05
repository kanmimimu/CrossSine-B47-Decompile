package jdk.nashorn.internal.codegen;

import java.util.List;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;

public abstract class ObjectCreator implements CodeGenerator.SplitLiteralCreator {
   final List tuples;
   final CodeGenerator codegen;
   protected PropertyMap propertyMap;
   private final boolean isScope;
   private final boolean hasArguments;

   ObjectCreator(CodeGenerator codegen, List tuples, boolean isScope, boolean hasArguments) {
      this.codegen = codegen;
      this.tuples = tuples;
      this.isScope = isScope;
      this.hasArguments = hasArguments;
   }

   public void makeObject(MethodEmitter method) {
      this.createObject(method);
      int objectSlot = method.getUsedSlotsWithLiveTemporaries();
      Type objectType = method.peekType();
      method.storeTemp(objectType, objectSlot);
      this.populateRange(method, objectType, objectSlot, 0, this.tuples.size());
   }

   protected abstract void createObject(MethodEmitter var1);

   protected abstract PropertyMap makeMap();

   protected MapCreator newMapCreator(Class clazz) {
      return new MapCreator(clazz, this.tuples);
   }

   protected void loadScope(MethodEmitter method) {
      method.loadCompilerConstant(CompilerConstants.SCOPE);
   }

   protected MethodEmitter loadMap(MethodEmitter method) {
      this.codegen.loadConstant((Object)this.propertyMap);
      return method;
   }

   PropertyMap getMap() {
      return this.propertyMap;
   }

   protected boolean isScope() {
      return this.isScope;
   }

   protected boolean hasArguments() {
      return this.hasArguments;
   }

   protected abstract Class getAllocatorClass();

   protected abstract void loadValue(Object var1, Type var2);

   MethodEmitter loadTuple(MethodEmitter method, MapTuple tuple, boolean pack) {
      this.loadValue(tuple.value, tuple.type);
      if (this.codegen.useDualFields() && tuple.isPrimitive()) {
         if (pack) {
            method.pack();
         }
      } else {
         method.convert(Type.OBJECT);
      }

      return method;
   }

   MethodEmitter loadIndex(MethodEmitter method, long index) {
      return JSType.isRepresentableAsInt(index) ? method.load((int)index) : method.load((double)index);
   }
}
