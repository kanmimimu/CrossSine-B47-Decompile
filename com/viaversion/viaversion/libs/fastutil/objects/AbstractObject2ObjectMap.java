package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Size64;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class AbstractObject2ObjectMap extends AbstractObject2ObjectFunction implements Object2ObjectMap, Serializable {
   private static final long serialVersionUID = -4940583368468432370L;

   protected AbstractObject2ObjectMap() {
   }

   public boolean containsKey(Object k) {
      ObjectIterator<Object2ObjectMap.Entry<K, V>> i = this.object2ObjectEntrySet().iterator();

      while(i.hasNext()) {
         if (((Object2ObjectMap.Entry)i.next()).getKey() == k) {
            return true;
         }
      }

      return false;
   }

   public boolean containsValue(Object v) {
      ObjectIterator<Object2ObjectMap.Entry<K, V>> i = this.object2ObjectEntrySet().iterator();

      while(i.hasNext()) {
         if (((Object2ObjectMap.Entry)i.next()).getValue() == v) {
            return true;
         }
      }

      return false;
   }

   public boolean isEmpty() {
      return this.size() == 0;
   }

   public ObjectSet keySet() {
      return new AbstractObjectSet() {
         public boolean contains(Object k) {
            return AbstractObject2ObjectMap.this.containsKey(k);
         }

         public int size() {
            return AbstractObject2ObjectMap.this.size();
         }

         public void clear() {
            AbstractObject2ObjectMap.this.clear();
         }

         public ObjectIterator iterator() {
            return new ObjectIterator() {
               private final ObjectIterator i = Object2ObjectMaps.fastIterator(AbstractObject2ObjectMap.this);

               public Object next() {
                  return ((Object2ObjectMap.Entry)this.i.next()).getKey();
               }

               public boolean hasNext() {
                  return this.i.hasNext();
               }

               public void remove() {
                  this.i.remove();
               }

               public void forEachRemaining(Consumer action) {
                  this.i.forEachRemaining((entry) -> action.accept(entry.getKey()));
               }
            };
         }

         public ObjectSpliterator spliterator() {
            return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf((Map)AbstractObject2ObjectMap.this), 65);
         }
      };
   }

   public ObjectCollection values() {
      return new AbstractObjectCollection() {
         public boolean contains(Object k) {
            return AbstractObject2ObjectMap.this.containsValue(k);
         }

         public int size() {
            return AbstractObject2ObjectMap.this.size();
         }

         public void clear() {
            AbstractObject2ObjectMap.this.clear();
         }

         public ObjectIterator iterator() {
            return new ObjectIterator() {
               private final ObjectIterator i = Object2ObjectMaps.fastIterator(AbstractObject2ObjectMap.this);

               public Object next() {
                  return ((Object2ObjectMap.Entry)this.i.next()).getValue();
               }

               public boolean hasNext() {
                  return this.i.hasNext();
               }

               public void remove() {
                  this.i.remove();
               }

               public void forEachRemaining(Consumer action) {
                  this.i.forEachRemaining((entry) -> action.accept(entry.getValue()));
               }
            };
         }

         public ObjectSpliterator spliterator() {
            return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf((Map)AbstractObject2ObjectMap.this), 64);
         }
      };
   }

   public void putAll(Map m) {
      if (m instanceof Object2ObjectMap) {
         ObjectIterator<Object2ObjectMap.Entry<K, V>> i = Object2ObjectMaps.fastIterator((Object2ObjectMap)m);

         while(i.hasNext()) {
            Object2ObjectMap.Entry<? extends K, ? extends V> e = (Object2ObjectMap.Entry)i.next();
            this.put(e.getKey(), e.getValue());
         }
      } else {
         int n = m.size();
         Iterator<? extends Map.Entry<? extends K, ? extends V>> i = m.entrySet().iterator();

         while(n-- != 0) {
            Map.Entry<? extends K, ? extends V> e = (Map.Entry)i.next();
            this.put(e.getKey(), e.getValue());
         }
      }

   }

   public int hashCode() {
      int h = 0;
      int n = this.size();

      for(ObjectIterator<Object2ObjectMap.Entry<K, V>> i = Object2ObjectMaps.fastIterator(this); n-- != 0; h += ((Object2ObjectMap.Entry)i.next()).hashCode()) {
      }

      return h;
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof Map)) {
         return false;
      } else {
         Map<?, ?> m = (Map)o;
         return m.size() != this.size() ? false : this.object2ObjectEntrySet().containsAll(m.entrySet());
      }
   }

   public String toString() {
      StringBuilder s = new StringBuilder();
      ObjectIterator<Object2ObjectMap.Entry<K, V>> i = Object2ObjectMaps.fastIterator(this);
      int n = this.size();
      boolean first = true;
      s.append("{");

      while(n-- != 0) {
         if (first) {
            first = false;
         } else {
            s.append(", ");
         }

         Object2ObjectMap.Entry<K, V> e = (Object2ObjectMap.Entry)i.next();
         if (this == e.getKey()) {
            s.append("(this map)");
         } else {
            s.append(String.valueOf(e.getKey()));
         }

         s.append("=>");
         if (this == e.getValue()) {
            s.append("(this map)");
         } else {
            s.append(String.valueOf(e.getValue()));
         }
      }

      s.append("}");
      return s.toString();
   }

   public static class BasicEntry implements Object2ObjectMap.Entry {
      protected Object key;
      protected Object value;

      public BasicEntry() {
      }

      public BasicEntry(Object key, Object value) {
         this.key = key;
         this.value = value;
      }

      public Object getKey() {
         return this.key;
      }

      public Object getValue() {
         return this.value;
      }

      public Object setValue(Object value) {
         throw new UnsupportedOperationException();
      }

      public boolean equals(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else if (o instanceof Object2ObjectMap.Entry) {
            Object2ObjectMap.Entry<K, V> e = (Object2ObjectMap.Entry)o;
            return Objects.equals(this.key, e.getKey()) && Objects.equals(this.value, e.getValue());
         } else {
            Map.Entry<?, ?> e = (Map.Entry)o;
            Object key = e.getKey();
            Object value = e.getValue();
            return Objects.equals(this.key, key) && Objects.equals(this.value, value);
         }
      }

      public int hashCode() {
         return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
      }

      public String toString() {
         return this.key + "->" + this.value;
      }
   }

   public abstract static class BasicEntrySet extends AbstractObjectSet {
      protected final Object2ObjectMap map;

      public BasicEntrySet(Object2ObjectMap map) {
         this.map = map;
      }

      public boolean contains(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else if (o instanceof Object2ObjectMap.Entry) {
            Object2ObjectMap.Entry<K, V> e = (Object2ObjectMap.Entry)o;
            K k = (K)e.getKey();
            return this.map.containsKey(k) && Objects.equals(this.map.get(k), e.getValue());
         } else {
            Map.Entry<?, ?> e = (Map.Entry)o;
            Object k = e.getKey();
            Object value = e.getValue();
            return this.map.containsKey(k) && Objects.equals(this.map.get(k), value);
         }
      }

      public boolean remove(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else if (o instanceof Object2ObjectMap.Entry) {
            Object2ObjectMap.Entry<K, V> e = (Object2ObjectMap.Entry)o;
            return this.map.remove(e.getKey(), e.getValue());
         } else {
            Map.Entry<?, ?> e = (Map.Entry)o;
            Object k = e.getKey();
            Object v = e.getValue();
            return this.map.remove(k, v);
         }
      }

      public int size() {
         return this.map.size();
      }

      public ObjectSpliterator spliterator() {
         return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf((Map)this.map), 65);
      }
   }
}
