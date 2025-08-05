package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

public class Reference2ObjectArrayMap extends AbstractReference2ObjectMap implements Serializable, Cloneable {
   private static final long serialVersionUID = 1L;
   protected transient Object[] key;
   protected transient Object[] value;
   protected int size;
   protected transient Reference2ObjectMap.FastEntrySet entries;
   protected transient ReferenceSet keys;
   protected transient ObjectCollection values;

   public Reference2ObjectArrayMap(Object[] key, Object[] value) {
      this.key = key;
      this.value = value;
      this.size = key.length;
      if (key.length != value.length) {
         throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
      }
   }

   public Reference2ObjectArrayMap() {
      this.key = ObjectArrays.EMPTY_ARRAY;
      this.value = ObjectArrays.EMPTY_ARRAY;
   }

   public Reference2ObjectArrayMap(int capacity) {
      this.key = new Object[capacity];
      this.value = new Object[capacity];
   }

   public Reference2ObjectArrayMap(Reference2ObjectMap m) {
      this(m.size());
      int i = 0;

      for(Reference2ObjectMap.Entry e : m.reference2ObjectEntrySet()) {
         this.key[i] = e.getKey();
         this.value[i] = e.getValue();
         ++i;
      }

      this.size = i;
   }

   public Reference2ObjectArrayMap(Map m) {
      this(m.size());
      int i = 0;

      for(Map.Entry e : m.entrySet()) {
         this.key[i] = e.getKey();
         this.value[i] = e.getValue();
         ++i;
      }

      this.size = i;
   }

   public Reference2ObjectArrayMap(Object[] key, Object[] value, int size) {
      this.key = key;
      this.value = value;
      this.size = size;
      if (key.length != value.length) {
         throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
      } else if (size > key.length) {
         throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
      }
   }

   public Reference2ObjectMap.FastEntrySet reference2ObjectEntrySet() {
      if (this.entries == null) {
         this.entries = new EntrySet();
      }

      return this.entries;
   }

   private int findKey(Object k) {
      Object[] key = this.key;
      int i = this.size;

      while(i-- != 0) {
         if (key[i] == k) {
            return i;
         }
      }

      return -1;
   }

   public Object get(Object k) {
      Object[] key = this.key;
      int i = this.size;

      while(i-- != 0) {
         if (key[i] == k) {
            return this.value[i];
         }
      }

      return this.defRetValue;
   }

   public int size() {
      return this.size;
   }

   public void clear() {
      Object[] key = this.key;
      Object[] value = this.value;

      for(int i = this.size; i-- != 0; value[i] = null) {
         key[i] = null;
      }

      this.size = 0;
   }

   public boolean containsKey(Object k) {
      return this.findKey(k) != -1;
   }

   public boolean containsValue(Object v) {
      Object[] value = this.value;
      int i = this.size;

      while(i-- != 0) {
         if (Objects.equals(value[i], v)) {
            return true;
         }
      }

      return false;
   }

   public boolean isEmpty() {
      return this.size == 0;
   }

   public Object put(Object k, Object v) {
      int oldKey = this.findKey(k);
      if (oldKey != -1) {
         V oldValue = (V)this.value[oldKey];
         this.value[oldKey] = v;
         return oldValue;
      } else {
         if (this.size == this.key.length) {
            Object[] newKey = new Object[this.size == 0 ? 2 : this.size * 2];
            Object[] newValue = new Object[this.size == 0 ? 2 : this.size * 2];

            for(int i = this.size; i-- != 0; newValue[i] = this.value[i]) {
               newKey[i] = this.key[i];
            }

            this.key = newKey;
            this.value = newValue;
         }

         this.key[this.size] = k;
         this.value[this.size] = v;
         ++this.size;
         return this.defRetValue;
      }
   }

   public Object remove(Object k) {
      int oldPos = this.findKey(k);
      if (oldPos == -1) {
         return this.defRetValue;
      } else {
         V oldValue = (V)this.value[oldPos];
         int tail = this.size - oldPos - 1;
         System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
         System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
         --this.size;
         this.key[this.size] = null;
         this.value[this.size] = null;
         return oldValue;
      }
   }

   public ReferenceSet keySet() {
      if (this.keys == null) {
         this.keys = new KeySet();
      }

      return this.keys;
   }

   public ObjectCollection values() {
      if (this.values == null) {
         this.values = new ValuesCollection();
      }

      return this.values;
   }

   public Reference2ObjectArrayMap clone() {
      Reference2ObjectArrayMap<K, V> c;
      try {
         c = (Reference2ObjectArrayMap)super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }

      c.key = this.key.clone();
      c.value = this.value.clone();
      c.entries = null;
      c.keys = null;
      c.values = null;
      return c;
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      s.defaultWriteObject();
      Object[] key = this.key;
      Object[] value = this.value;
      int i = 0;

      for(int max = this.size; i < max; ++i) {
         s.writeObject(key[i]);
         s.writeObject(value[i]);
      }

   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      Object[] key = this.key = new Object[this.size];
      Object[] value = this.value = new Object[this.size];

      for(int i = 0; i < this.size; ++i) {
         key[i] = s.readObject();
         value[i] = s.readObject();
      }

   }

   private final class MapEntry implements Reference2ObjectMap.Entry, Map.Entry, ReferenceObjectPair {
      int index;

      MapEntry() {
      }

      MapEntry(final int index) {
         this.index = index;
      }

      public Object getKey() {
         return Reference2ObjectArrayMap.this.key[this.index];
      }

      public Object left() {
         return Reference2ObjectArrayMap.this.key[this.index];
      }

      public Object getValue() {
         return Reference2ObjectArrayMap.this.value[this.index];
      }

      public Object right() {
         return Reference2ObjectArrayMap.this.value[this.index];
      }

      public Object setValue(Object v) {
         V oldValue = (V)Reference2ObjectArrayMap.this.value[this.index];
         Reference2ObjectArrayMap.this.value[this.index] = v;
         return oldValue;
      }

      public ReferenceObjectPair right(Object v) {
         Reference2ObjectArrayMap.this.value[this.index] = v;
         return this;
      }

      public boolean equals(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry<K, V> e = (Map.Entry)o;
            return Reference2ObjectArrayMap.this.key[this.index] == e.getKey() && Objects.equals(Reference2ObjectArrayMap.this.value[this.index], e.getValue());
         }
      }

      public int hashCode() {
         return System.identityHashCode(Reference2ObjectArrayMap.this.key[this.index]) ^ (Reference2ObjectArrayMap.this.value[this.index] == null ? 0 : Reference2ObjectArrayMap.this.value[this.index].hashCode());
      }

      public String toString() {
         return Reference2ObjectArrayMap.this.key[this.index] + "=>" + Reference2ObjectArrayMap.this.value[this.index];
      }
   }

   private final class EntrySet extends AbstractObjectSet implements Reference2ObjectMap.FastEntrySet {
      private EntrySet() {
      }

      public ObjectIterator iterator() {
         return new ObjectIterator() {
            private MapEntry entry;
            int curr = -1;
            int next = 0;

            public boolean hasNext() {
               return this.next < Reference2ObjectArrayMap.this.size;
            }

            public Reference2ObjectMap.Entry next() {
               if (!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  return this.entry = Reference2ObjectArrayMap.this.new MapEntry(this.curr = this.next++);
               }
            }

            public void remove() {
               if (this.curr == -1) {
                  throw new IllegalStateException();
               } else {
                  this.curr = -1;
                  int tail = Reference2ObjectArrayMap.this.size-- - this.next--;
                  System.arraycopy(Reference2ObjectArrayMap.this.key, this.next + 1, Reference2ObjectArrayMap.this.key, this.next, tail);
                  System.arraycopy(Reference2ObjectArrayMap.this.value, this.next + 1, Reference2ObjectArrayMap.this.value, this.next, tail);
                  this.entry.index = -1;
                  Reference2ObjectArrayMap.this.key[Reference2ObjectArrayMap.this.size] = null;
                  Reference2ObjectArrayMap.this.value[Reference2ObjectArrayMap.this.size] = null;
               }
            }

            public int skip(int n) {
               if (n < 0) {
                  throw new IllegalArgumentException("Argument must be nonnegative: " + n);
               } else {
                  n = Math.min(n, Reference2ObjectArrayMap.this.size - this.next);
                  this.next += n;
                  if (n != 0) {
                     this.curr = this.next - 1;
                  }

                  return n;
               }
            }

            public void forEachRemaining(Consumer action) {
               int max = Reference2ObjectArrayMap.this.size;

               while(this.next < max) {
                  this.entry = Reference2ObjectArrayMap.this.new MapEntry(this.curr = this.next++);
                  action.accept(this.entry);
               }

            }
         };
      }

      public ObjectIterator fastIterator() {
         return new ObjectIterator() {
            private MapEntry entry = Reference2ObjectArrayMap.this.new MapEntry();
            int next = 0;
            int curr = -1;

            public boolean hasNext() {
               return this.next < Reference2ObjectArrayMap.this.size;
            }

            public Reference2ObjectMap.Entry next() {
               if (!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  this.entry.index = this.curr = this.next++;
                  return this.entry;
               }
            }

            public void remove() {
               if (this.curr == -1) {
                  throw new IllegalStateException();
               } else {
                  this.curr = -1;
                  int tail = Reference2ObjectArrayMap.this.size-- - this.next--;
                  System.arraycopy(Reference2ObjectArrayMap.this.key, this.next + 1, Reference2ObjectArrayMap.this.key, this.next, tail);
                  System.arraycopy(Reference2ObjectArrayMap.this.value, this.next + 1, Reference2ObjectArrayMap.this.value, this.next, tail);
                  this.entry.index = -1;
                  Reference2ObjectArrayMap.this.key[Reference2ObjectArrayMap.this.size] = null;
                  Reference2ObjectArrayMap.this.value[Reference2ObjectArrayMap.this.size] = null;
               }
            }

            public int skip(int n) {
               if (n < 0) {
                  throw new IllegalArgumentException("Argument must be nonnegative: " + n);
               } else {
                  n = Math.min(n, Reference2ObjectArrayMap.this.size - this.next);
                  this.next += n;
                  if (n != 0) {
                     this.curr = this.next - 1;
                  }

                  return n;
               }
            }

            public void forEachRemaining(Consumer action) {
               int max = Reference2ObjectArrayMap.this.size;

               while(this.next < max) {
                  this.entry.index = this.curr = this.next++;
                  action.accept(this.entry);
               }

            }
         };
      }

      public ObjectSpliterator spliterator() {
         return new EntrySetSpliterator(0, Reference2ObjectArrayMap.this.size);
      }

      public void forEach(Consumer action) {
         int i = 0;

         for(int max = Reference2ObjectArrayMap.this.size; i < max; ++i) {
            action.accept(Reference2ObjectArrayMap.this.new MapEntry(i));
         }

      }

      public void fastForEach(Consumer action) {
         Reference2ObjectArrayMap<K, V>.MapEntry entry = Reference2ObjectArrayMap.this.new MapEntry();
         int i = 0;

         for(int max = Reference2ObjectArrayMap.this.size; i < max; ++i) {
            entry.index = i;
            action.accept(entry);
         }

      }

      public int size() {
         return Reference2ObjectArrayMap.this.size;
      }

      public boolean contains(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry<?, ?> e = (Map.Entry)o;
            K k = (K)e.getKey();
            return Reference2ObjectArrayMap.this.containsKey(k) && Objects.equals(Reference2ObjectArrayMap.this.get(k), e.getValue());
         }
      }

      public boolean remove(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry<?, ?> e = (Map.Entry)o;
            K k = (K)e.getKey();
            V v = (V)e.getValue();
            int oldPos = Reference2ObjectArrayMap.this.findKey(k);
            if (oldPos != -1 && Objects.equals(v, Reference2ObjectArrayMap.this.value[oldPos])) {
               int tail = Reference2ObjectArrayMap.this.size - oldPos - 1;
               System.arraycopy(Reference2ObjectArrayMap.this.key, oldPos + 1, Reference2ObjectArrayMap.this.key, oldPos, tail);
               System.arraycopy(Reference2ObjectArrayMap.this.value, oldPos + 1, Reference2ObjectArrayMap.this.value, oldPos, tail);
               --Reference2ObjectArrayMap.this.size;
               Reference2ObjectArrayMap.this.key[Reference2ObjectArrayMap.this.size] = null;
               Reference2ObjectArrayMap.this.value[Reference2ObjectArrayMap.this.size] = null;
               return true;
            } else {
               return false;
            }
         }
      }

      final class EntrySetSpliterator extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator implements ObjectSpliterator {
         EntrySetSpliterator(int pos, int maxPos) {
            super(pos, maxPos);
         }

         public int characteristics() {
            return 16465;
         }

         protected final Reference2ObjectMap.Entry get(int location) {
            return Reference2ObjectArrayMap.this.new MapEntry(location);
         }

         protected final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
            return EntrySet.this.new EntrySetSpliterator(pos, maxPos);
         }
      }
   }

   private final class KeySet extends AbstractReferenceSet {
      private KeySet() {
      }

      public boolean contains(Object k) {
         return Reference2ObjectArrayMap.this.findKey(k) != -1;
      }

      public boolean remove(Object k) {
         int oldPos = Reference2ObjectArrayMap.this.findKey(k);
         if (oldPos == -1) {
            return false;
         } else {
            int tail = Reference2ObjectArrayMap.this.size - oldPos - 1;
            System.arraycopy(Reference2ObjectArrayMap.this.key, oldPos + 1, Reference2ObjectArrayMap.this.key, oldPos, tail);
            System.arraycopy(Reference2ObjectArrayMap.this.value, oldPos + 1, Reference2ObjectArrayMap.this.value, oldPos, tail);
            --Reference2ObjectArrayMap.this.size;
            Reference2ObjectArrayMap.this.key[Reference2ObjectArrayMap.this.size] = null;
            Reference2ObjectArrayMap.this.value[Reference2ObjectArrayMap.this.size] = null;
            return true;
         }
      }

      public ObjectIterator iterator() {
         return new ObjectIterator() {
            int pos = 0;

            public boolean hasNext() {
               return this.pos < Reference2ObjectArrayMap.this.size;
            }

            public Object next() {
               if (!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  return Reference2ObjectArrayMap.this.key[this.pos++];
               }
            }

            public void remove() {
               if (this.pos == 0) {
                  throw new IllegalStateException();
               } else {
                  int tail = Reference2ObjectArrayMap.this.size - this.pos;
                  System.arraycopy(Reference2ObjectArrayMap.this.key, this.pos, Reference2ObjectArrayMap.this.key, this.pos - 1, tail);
                  System.arraycopy(Reference2ObjectArrayMap.this.value, this.pos, Reference2ObjectArrayMap.this.value, this.pos - 1, tail);
                  --Reference2ObjectArrayMap.this.size;
                  --this.pos;
                  Reference2ObjectArrayMap.this.key[Reference2ObjectArrayMap.this.size] = null;
                  Reference2ObjectArrayMap.this.value[Reference2ObjectArrayMap.this.size] = null;
               }
            }

            public void forEachRemaining(Consumer action) {
               Object[] key = Reference2ObjectArrayMap.this.key;
               int max = Reference2ObjectArrayMap.this.size;

               while(this.pos < max) {
                  action.accept(key[this.pos++]);
               }

            }
         };
      }

      public ObjectSpliterator spliterator() {
         return new KeySetSpliterator(0, Reference2ObjectArrayMap.this.size);
      }

      public void forEach(Consumer action) {
         Object[] key = Reference2ObjectArrayMap.this.key;
         int i = 0;

         for(int max = Reference2ObjectArrayMap.this.size; i < max; ++i) {
            action.accept(key[i]);
         }

      }

      public int size() {
         return Reference2ObjectArrayMap.this.size;
      }

      public void clear() {
         Reference2ObjectArrayMap.this.clear();
      }

      final class KeySetSpliterator extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator implements ObjectSpliterator {
         KeySetSpliterator(int pos, int maxPos) {
            super(pos, maxPos);
         }

         public int characteristics() {
            return 16465;
         }

         protected final Object get(int location) {
            return Reference2ObjectArrayMap.this.key[location];
         }

         protected final KeySetSpliterator makeForSplit(int pos, int maxPos) {
            return KeySet.this.new KeySetSpliterator(pos, maxPos);
         }

         public void forEachRemaining(Consumer action) {
            Object[] key = Reference2ObjectArrayMap.this.key;
            int max = Reference2ObjectArrayMap.this.size;

            while(this.pos < max) {
               action.accept(key[this.pos++]);
            }

         }
      }
   }

   private final class ValuesCollection extends AbstractObjectCollection {
      private ValuesCollection() {
      }

      public boolean contains(Object v) {
         return Reference2ObjectArrayMap.this.containsValue(v);
      }

      public ObjectIterator iterator() {
         return new ObjectIterator() {
            int pos = 0;

            public boolean hasNext() {
               return this.pos < Reference2ObjectArrayMap.this.size;
            }

            public Object next() {
               if (!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  return Reference2ObjectArrayMap.this.value[this.pos++];
               }
            }

            public void remove() {
               if (this.pos == 0) {
                  throw new IllegalStateException();
               } else {
                  int tail = Reference2ObjectArrayMap.this.size - this.pos;
                  System.arraycopy(Reference2ObjectArrayMap.this.key, this.pos, Reference2ObjectArrayMap.this.key, this.pos - 1, tail);
                  System.arraycopy(Reference2ObjectArrayMap.this.value, this.pos, Reference2ObjectArrayMap.this.value, this.pos - 1, tail);
                  --Reference2ObjectArrayMap.this.size;
                  --this.pos;
                  Reference2ObjectArrayMap.this.key[Reference2ObjectArrayMap.this.size] = null;
                  Reference2ObjectArrayMap.this.value[Reference2ObjectArrayMap.this.size] = null;
               }
            }

            public void forEachRemaining(Consumer action) {
               Object[] value = Reference2ObjectArrayMap.this.value;
               int max = Reference2ObjectArrayMap.this.size;

               while(this.pos < max) {
                  action.accept(value[this.pos++]);
               }

            }
         };
      }

      public ObjectSpliterator spliterator() {
         return new ValuesSpliterator(0, Reference2ObjectArrayMap.this.size);
      }

      public void forEach(Consumer action) {
         Object[] value = Reference2ObjectArrayMap.this.value;
         int i = 0;

         for(int max = Reference2ObjectArrayMap.this.size; i < max; ++i) {
            action.accept(value[i]);
         }

      }

      public int size() {
         return Reference2ObjectArrayMap.this.size;
      }

      public void clear() {
         Reference2ObjectArrayMap.this.clear();
      }

      final class ValuesSpliterator extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator implements ObjectSpliterator {
         ValuesSpliterator(int pos, int maxPos) {
            super(pos, maxPos);
         }

         public int characteristics() {
            return 16464;
         }

         protected final Object get(int location) {
            return Reference2ObjectArrayMap.this.value[location];
         }

         protected final ValuesSpliterator makeForSplit(int pos, int maxPos) {
            return ValuesCollection.this.new ValuesSpliterator(pos, maxPos);
         }

         public void forEachRemaining(Consumer action) {
            Object[] value = Reference2ObjectArrayMap.this.value;
            int max = Reference2ObjectArrayMap.this.size;

            while(this.pos < max) {
               action.accept(value[this.pos++]);
            }

         }
      }
   }
}
