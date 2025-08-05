package jdk.internal.dynalink.support;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.WrongMethodTypeException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.LinkedList;
import java.util.List;
import jdk.internal.dynalink.linker.ConversionComparator;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardedTypeConversion;
import jdk.internal.dynalink.linker.GuardingTypeConverterFactory;
import jdk.internal.dynalink.linker.MethodTypeConversionStrategy;

public class TypeConverterFactory {
   private final GuardingTypeConverterFactory[] factories;
   private final ConversionComparator[] comparators;
   private final MethodTypeConversionStrategy autoConversionStrategy;
   private final ClassValue converterMap = new ClassValue() {
      protected ClassMap computeValue(final Class sourceType) {
         return new ClassMap(TypeConverterFactory.getClassLoader(sourceType)) {
            protected MethodHandle computeValue(Class targetType) {
               try {
                  return TypeConverterFactory.this.createConverter(sourceType, targetType);
               } catch (RuntimeException e) {
                  throw e;
               } catch (Exception e) {
                  throw new RuntimeException(e);
               }
            }
         };
      }
   };
   private final ClassValue converterIdentityMap = new ClassValue() {
      protected ClassMap computeValue(final Class sourceType) {
         return new ClassMap(TypeConverterFactory.getClassLoader(sourceType)) {
            protected MethodHandle computeValue(Class targetType) {
               if (!TypeConverterFactory.canAutoConvert(sourceType, targetType)) {
                  MethodHandle converter = TypeConverterFactory.this.getCacheableTypeConverter(sourceType, targetType);
                  if (converter != TypeConverterFactory.IDENTITY_CONVERSION) {
                     return converter;
                  }
               }

               return TypeConverterFactory.IDENTITY_CONVERSION.asType(MethodType.methodType(targetType, sourceType));
            }
         };
      }
   };
   private final ClassValue canConvert = new ClassValue() {
      protected ClassMap computeValue(final Class sourceType) {
         return new ClassMap(TypeConverterFactory.getClassLoader(sourceType)) {
            protected Boolean computeValue(Class targetType) {
               try {
                  return TypeConverterFactory.this.getTypeConverterNull(sourceType, targetType) != null;
               } catch (RuntimeException e) {
                  throw e;
               } catch (Exception e) {
                  throw new RuntimeException(e);
               }
            }
         };
      }
   };
   static final MethodHandle IDENTITY_CONVERSION = MethodHandles.identity(Object.class);

   private static final ClassLoader getClassLoader(final Class clazz) {
      return (ClassLoader)AccessController.doPrivileged(new PrivilegedAction() {
         public ClassLoader run() {
            return clazz.getClassLoader();
         }
      }, ClassLoaderGetterContextProvider.GET_CLASS_LOADER_CONTEXT);
   }

   public TypeConverterFactory(Iterable factories, MethodTypeConversionStrategy autoConversionStrategy) {
      List<GuardingTypeConverterFactory> l = new LinkedList();
      List<ConversionComparator> c = new LinkedList();

      for(GuardingTypeConverterFactory factory : factories) {
         l.add(factory);
         if (factory instanceof ConversionComparator) {
            c.add((ConversionComparator)factory);
         }
      }

      this.factories = (GuardingTypeConverterFactory[])l.toArray(new GuardingTypeConverterFactory[l.size()]);
      this.comparators = (ConversionComparator[])c.toArray(new ConversionComparator[c.size()]);
      this.autoConversionStrategy = autoConversionStrategy;
   }

   public MethodHandle asType(MethodHandle handle, MethodType fromType) {
      MethodHandle newHandle = handle;
      MethodType toType = handle.type();
      int l = toType.parameterCount();
      if (l != fromType.parameterCount()) {
         throw new WrongMethodTypeException("Parameter counts differ: " + handle.type() + " vs. " + fromType);
      } else {
         int pos = 0;
         List<MethodHandle> converters = new LinkedList();

         for(int i = 0; i < l; ++i) {
            Class<?> fromParamType = fromType.parameterType(i);
            Class<?> toParamType = toType.parameterType(i);
            if (canAutoConvert(fromParamType, toParamType)) {
               newHandle = applyConverters(newHandle, pos, converters);
            } else {
               MethodHandle converter = this.getTypeConverterNull(fromParamType, toParamType);
               if (converter != null) {
                  if (converters.isEmpty()) {
                     pos = i;
                  }

                  converters.add(converter);
               } else {
                  newHandle = applyConverters(newHandle, pos, converters);
               }
            }
         }

         newHandle = applyConverters(newHandle, pos, converters);
         Class<?> fromRetType = fromType.returnType();
         Class<?> toRetType = toType.returnType();
         if (fromRetType != Void.TYPE && toRetType != Void.TYPE && !canAutoConvert(toRetType, fromRetType)) {
            MethodHandle converter = this.getTypeConverterNull(toRetType, fromRetType);
            if (converter != null) {
               newHandle = MethodHandles.filterReturnValue(newHandle, converter);
            }
         }

         MethodHandle autoConvertedHandle = this.autoConversionStrategy != null ? this.autoConversionStrategy.asType(newHandle, fromType) : newHandle;
         return autoConvertedHandle.asType(fromType);
      }
   }

   private static MethodHandle applyConverters(MethodHandle handle, int pos, List converters) {
      if (converters.isEmpty()) {
         return handle;
      } else {
         MethodHandle newHandle = MethodHandles.filterArguments(handle, pos, (MethodHandle[])converters.toArray(new MethodHandle[converters.size()]));
         converters.clear();
         return newHandle;
      }
   }

   public boolean canConvert(Class from, Class to) {
      return canAutoConvert(from, to) || (Boolean)((ClassMap)this.canConvert.get(from)).get(to);
   }

   public ConversionComparator.Comparison compareConversion(Class sourceType, Class targetType1, Class targetType2) {
      for(ConversionComparator comparator : this.comparators) {
         ConversionComparator.Comparison result = comparator.compareConversion(sourceType, targetType1, targetType2);
         if (result != ConversionComparator.Comparison.INDETERMINATE) {
            return result;
         }
      }

      if (TypeUtilities.isMethodInvocationConvertible(sourceType, targetType1)) {
         if (!TypeUtilities.isMethodInvocationConvertible(sourceType, targetType2)) {
            return ConversionComparator.Comparison.TYPE_1_BETTER;
         }
      } else if (TypeUtilities.isMethodInvocationConvertible(sourceType, targetType2)) {
         return ConversionComparator.Comparison.TYPE_2_BETTER;
      }

      return ConversionComparator.Comparison.INDETERMINATE;
   }

   static boolean canAutoConvert(Class fromType, Class toType) {
      return TypeUtilities.isMethodInvocationConvertible(fromType, toType);
   }

   MethodHandle getCacheableTypeConverterNull(Class sourceType, Class targetType) {
      MethodHandle converter = this.getCacheableTypeConverter(sourceType, targetType);
      return converter == IDENTITY_CONVERSION ? null : converter;
   }

   MethodHandle getTypeConverterNull(Class sourceType, Class targetType) {
      try {
         return this.getCacheableTypeConverterNull(sourceType, targetType);
      } catch (NotCacheableConverter e) {
         return e.converter;
      }
   }

   MethodHandle getCacheableTypeConverter(Class sourceType, Class targetType) {
      return (MethodHandle)((ClassMap)this.converterMap.get(sourceType)).get(targetType);
   }

   public MethodHandle getTypeConverter(Class sourceType, Class targetType) {
      try {
         return (MethodHandle)((ClassMap)this.converterIdentityMap.get(sourceType)).get(targetType);
      } catch (NotCacheableConverter e) {
         return e.converter;
      }
   }

   MethodHandle createConverter(Class sourceType, Class targetType) throws Exception {
      MethodType type = MethodType.methodType(targetType, sourceType);
      MethodHandle identity = IDENTITY_CONVERSION.asType(type);
      MethodHandle last = identity;
      boolean cacheable = true;
      int i = this.factories.length;

      while(i-- > 0) {
         GuardedTypeConversion next = this.factories[i].convertToType(sourceType, targetType);
         if (next != null) {
            cacheable = cacheable && next.isCacheable();
            GuardedInvocation conversionInvocation = next.getConversionInvocation();
            conversionInvocation.assertType(type);
            last = conversionInvocation.compose(last);
         }
      }

      if (last == identity) {
         return IDENTITY_CONVERSION;
      } else if (cacheable) {
         return last;
      } else {
         throw new NotCacheableConverter(last);
      }
   }

   private static class NotCacheableConverter extends RuntimeException {
      final MethodHandle converter;

      NotCacheableConverter(MethodHandle converter) {
         super("", (Throwable)null, false, false);
         this.converter = converter;
      }
   }
}
