package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Size64;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class AbstractReference2ObjectMap extends AbstractReference2ObjectFunction implements Reference2ObjectMap, Serializable {
   private static final long serialVersionUID = -4940583368468432370L;

   protected AbstractReference2ObjectMap() {
   }

   public boolean containsKey(Object k) {
      ObjectIterator<Reference2ObjectMap.Entry<K, V>> i = this.reference2ObjectEntrySet().iterator();

      while(i.hasNext()) {
         if (((Reference2ObjectMap.Entry)i.next()).getKey() == k) {
            return true;
         }
      }

      return false;
   }

   public boolean containsValue(Object v) {
      ObjectIterator<Reference2ObjectMap.Entry<K, V>> i = this.reference2ObjectEntrySet().iterator();

      while(i.hasNext()) {
         if (((Reference2ObjectMap.Entry)i.next()).getValue() == v) {
            return true;
         }
      }

      return false;
   }

   public boolean isEmpty() {
      return this.size() == 0;
   }

   public ReferenceSet keySet() {
      return new AbstractReferenceSet() {
         public boolean contains(Object k) {
            return AbstractReference2ObjectMap.this.containsKey(k);
         }

         public int size() {
            return AbstractReference2ObjectMap.this.size();
         }

         public void clear() {
            AbstractReference2ObjectMap.this.clear();
         }

         public ObjectIterator iterator() {
            return new ObjectIterator() {
               private final ObjectIterator i = Reference2ObjectMaps.fastIterator(AbstractReference2ObjectMap.this);

               public Object next() {
                  return ((Reference2ObjectMap.Entry)this.i.next()).getKey();
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
            return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf((Map)AbstractReference2ObjectMap.this), 65);
         }
      };
   }

   public ObjectCollection values() {
      return new AbstractObjectCollection() {
         public boolean contains(Object k) {
            return AbstractReference2ObjectMap.this.containsValue(k);
         }

         public int size() {
            return AbstractReference2ObjectMap.this.size();
         }

         public void clear() {
            AbstractReference2ObjectMap.this.clear();
         }

         public ObjectIterator iterator() {
            return new ObjectIterator() {
               private final ObjectIterator i = Reference2ObjectMaps.fastIterator(AbstractReference2ObjectMap.this);

               public Object next() {
                  return ((Reference2ObjectMap.Entry)this.i.next()).getValue();
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
            return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf((Map)AbstractReference2ObjectMap.this), 64);
         }
      };
   }

   public void putAll(Map m) {
      if (m instanceof Reference2ObjectMap) {
         ObjectIterator<Reference2ObjectMap.Entry<K, V>> i = Reference2ObjectMaps.fastIterator((Reference2ObjectMap)m);

         while(i.hasNext()) {
            Reference2ObjectMap.Entry<? extends K, ? extends V> e = (Reference2ObjectMap.Entry)i.next();
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

      for(ObjectIterator<Reference2ObjectMap.Entry<K, V>> i = Reference2ObjectMaps.fastIterator(this); n-- != 0; h += ((Reference2ObjectMap.Entry)i.next()).hashCode()) {
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
         return m.size() != this.size() ? false : this.reference2ObjectEntrySet().containsAll(m.entrySet());
      }
   }

   public String toString() {
      StringBuilder s = new StringBuilder();
      ObjectIterator<Reference2ObjectMap.Entry<K, V>> i = Reference2ObjectMaps.fastIterator(this);
      int n = this.size();
      boolean first = true;
      s.append("{");

      while(n-- != 0) {
         if (first) {
            first = false;
         } else {
            s.append(", ");
         }

         Reference2ObjectMap.Entry<K, V> e = (Reference2ObjectMap.Entry)i.next();
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

   public static class BasicEntry implements Reference2ObjectMap.Entry {
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
         } else if (o instanceof Reference2ObjectMap.Entry) {
            Reference2ObjectMap.Entry<K, V> e = (Reference2ObjectMap.Entry)o;
            return this.key == e.getKey() && Objects.equals(this.value, e.getValue());
         } else {
            Map.Entry<?, ?> e = (Map.Entry)o;
            Object key = e.getKey();
            Object value = e.getValue();
            return this.key == key && Objects.equals(this.value, value);
         }
      }

      public int hashCode() {
         return System.identityHashCode(this.key) ^ (this.value == null ? 0 : this.value.hashCode());
      }

      public String toString() {
         return this.key + "->" + this.value;
      }
   }

   public abstract static class BasicEntrySet extends AbstractObjectSet {
      protected final Reference2ObjectMap map;

      public BasicEntrySet(Reference2ObjectMap map) {
         this.map = map;
      }

      public boolean contains(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else if (o instanceof Reference2ObjectMap.Entry) {
            Reference2ObjectMap.Entry<K, V> e = (Reference2ObjectMap.Entry)o;
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
         } else if (o instanceof Reference2ObjectMap.Entry) {
            Reference2ObjectMap.Entry<K, V> e = (Reference2ObjectMap.Entry)o;
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
