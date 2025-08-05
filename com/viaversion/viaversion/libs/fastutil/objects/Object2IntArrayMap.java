package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrays;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class Object2IntArrayMap extends AbstractObject2IntMap implements Serializable, Cloneable {
   private static final long serialVersionUID = 1L;
   protected transient Object[] key;
   protected transient int[] value;
   protected int size;
   protected transient Object2IntMap.FastEntrySet entries;
   protected transient ObjectSet keys;
   protected transient IntCollection values;

   public Object2IntArrayMap(Object[] key, int[] value) {
      this.key = key;
      this.value = value;
      this.size = key.length;
      if (key.length != value.length) {
         throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
      }
   }

   public Object2IntArrayMap() {
      this.key = ObjectArrays.EMPTY_ARRAY;
      this.value = IntArrays.EMPTY_ARRAY;
   }

   public Object2IntArrayMap(int capacity) {
      this.key = new Object[capacity];
      this.value = new int[capacity];
   }

   public Object2IntArrayMap(Object2IntMap m) {
      this(m.size());
      int i = 0;

      for(Object2IntMap.Entry e : m.object2IntEntrySet()) {
         this.key[i] = e.getKey();
         this.value[i] = e.getIntValue();
         ++i;
      }

      this.size = i;
   }

   public Object2IntArrayMap(Map m) {
      this(m.size());
      int i = 0;

      for(Map.Entry e : m.entrySet()) {
         this.key[i] = e.getKey();
         this.value[i] = (Integer)e.getValue();
         ++i;
      }

      this.size = i;
   }

   public Object2IntArrayMap(Object[] key, int[] value, int size) {
      this.key = key;
      this.value = value;
      this.size = size;
      if (key.length != value.length) {
         throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
      } else if (size > key.length) {
         throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
      }
   }

   public Object2IntMap.FastEntrySet object2IntEntrySet() {
      if (this.entries == null) {
         this.entries = new EntrySet();
      }

      return this.entries;
   }

   private int findKey(Object k) {
      Object[] key = this.key;
      int i = this.size;

      while(i-- != 0) {
         if (Objects.equals(key[i], k)) {
            return i;
         }
      }

      return -1;
   }

   public int getInt(Object k) {
      Object[] key = this.key;
      int i = this.size;

      while(i-- != 0) {
         if (Objects.equals(key[i], k)) {
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

      for(int i = this.size; i-- != 0; key[i] = null) {
      }

      this.size = 0;
   }

   public boolean containsKey(Object k) {
      return this.findKey(k) != -1;
   }

   public boolean containsValue(int v) {
      int[] value = this.value;
      int i = this.size;

      while(i-- != 0) {
         if (value[i] == v) {
            return true;
         }
      }

      return false;
   }

   public boolean isEmpty() {
      return this.size == 0;
   }

   public int put(Object k, int v) {
      int oldKey = this.findKey(k);
      if (oldKey != -1) {
         int oldValue = this.value[oldKey];
         this.value[oldKey] = v;
         return oldValue;
      } else {
         if (this.size == this.key.length) {
            Object[] newKey = new Object[this.size == 0 ? 2 : this.size * 2];
            int[] newValue = new int[this.size == 0 ? 2 : this.size * 2];

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

   public int removeInt(Object k) {
      int oldPos = this.findKey(k);
      if (oldPos == -1) {
         return this.defRetValue;
      } else {
         int oldValue = this.value[oldPos];
         int tail = this.size - oldPos - 1;
         System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
         System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
         --this.size;
         this.key[this.size] = null;
         return oldValue;
      }
   }

   public ObjectSet keySet() {
      if (this.keys == null) {
         this.keys = new KeySet();
      }

      return this.keys;
   }

   public IntCollection values() {
      if (this.values == null) {
         this.values = new ValuesCollection();
      }

      return this.values;
   }

   public Object2IntArrayMap clone() {
      Object2IntArrayMap<K> c;
      try {
         c = (Object2IntArrayMap)super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }

      c.key = this.key.clone();
      c.value = (int[])this.value.clone();
      c.entries = null;
      c.keys = null;
      c.values = null;
      return c;
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      s.defaultWriteObject();
      Object[] key = this.key;
      int[] value = this.value;
      int i = 0;

      for(int max = this.size; i < max; ++i) {
         s.writeObject(key[i]);
         s.writeInt(value[i]);
      }

   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      Object[] key = this.key = new Object[this.size];
      int[] value = this.value = new int[this.size];

      for(int i = 0; i < this.size; ++i) {
         key[i] = s.readObject();
         value[i] = s.readInt();
      }

   }

   private final class MapEntry implements Object2IntMap.Entry, Map.Entry, ObjectIntPair {
      int index;

      MapEntry() {
      }

      MapEntry(final int index) {
         this.index = index;
      }

      public Object getKey() {
         return Object2IntArrayMap.this.key[this.index];
      }

      public Object left() {
         return Object2IntArrayMap.this.key[this.index];
      }

      public int getIntValue() {
         return Object2IntArrayMap.this.value[this.index];
      }

      public int rightInt() {
         return Object2IntArrayMap.this.value[this.index];
      }

      public int setValue(int v) {
         int oldValue = Object2IntArrayMap.this.value[this.index];
         Object2IntArrayMap.this.value[this.index] = v;
         return oldValue;
      }

      public ObjectIntPair right(int v) {
         Object2IntArrayMap.this.value[this.index] = v;
         return this;
      }

      /** @deprecated */
      @Deprecated
      public Integer getValue() {
         return Object2IntArrayMap.this.value[this.index];
      }

      /** @deprecated */
      @Deprecated
      public Integer setValue(Integer v) {
         return this.setValue(v);
      }

      public boolean equals(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry<K, Integer> e = (Map.Entry)o;
            return Objects.equals(Object2IntArrayMap.this.key[this.index], e.getKey()) && Object2IntArrayMap.this.value[this.index] == (Integer)e.getValue();
         }
      }

      public int hashCode() {
         return (Object2IntArrayMap.this.key[this.index] == null ? 0 : Object2IntArrayMap.this.key[this.index].hashCode()) ^ Object2IntArrayMap.this.value[this.index];
      }

      public String toString() {
         return Object2IntArrayMap.this.key[this.index] + "=>" + Object2IntArrayMap.this.value[this.index];
      }
   }

   private final class EntrySet extends AbstractObjectSet implements Object2IntMap.FastEntrySet {
      private EntrySet() {
      }

      public ObjectIterator iterator() {
         return new ObjectIterator() {
            private MapEntry entry;
            int curr = -1;
            int next = 0;

            public boolean hasNext() {
               return this.next < Object2IntArrayMap.this.size;
            }

            public Object2IntMap.Entry next() {
               if (!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  return this.entry = Object2IntArrayMap.this.new MapEntry(this.curr = this.next++);
               }
            }

            public void remove() {
               if (this.curr == -1) {
                  throw new IllegalStateException();
               } else {
                  this.curr = -1;
                  int tail = Object2IntArrayMap.this.size-- - this.next--;
                  System.arraycopy(Object2IntArrayMap.this.key, this.next + 1, Object2IntArrayMap.this.key, this.next, tail);
                  System.arraycopy(Object2IntArrayMap.this.value, this.next + 1, Object2IntArrayMap.this.value, this.next, tail);
                  this.entry.index = -1;
                  Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
               }
            }

            public int skip(int n) {
               if (n < 0) {
                  throw new IllegalArgumentException("Argument must be nonnegative: " + n);
               } else {
                  n = Math.min(n, Object2IntArrayMap.this.size - this.next);
                  this.next += n;
                  if (n != 0) {
                     this.curr = this.next - 1;
                  }

                  return n;
               }
            }

            public void forEachRemaining(Consumer action) {
               int max = Object2IntArrayMap.this.size;

               while(this.next < max) {
                  this.entry = Object2IntArrayMap.this.new MapEntry(this.curr = this.next++);
                  action.accept(this.entry);
               }

            }
         };
      }

      public ObjectIterator fastIterator() {
         return new ObjectIterator() {
            private MapEntry entry = Object2IntArrayMap.this.new MapEntry();
            int next = 0;
            int curr = -1;

            public boolean hasNext() {
               return this.next < Object2IntArrayMap.this.size;
            }

            public Object2IntMap.Entry next() {
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
                  int tail = Object2IntArrayMap.this.size-- - this.next--;
                  System.arraycopy(Object2IntArrayMap.this.key, this.next + 1, Object2IntArrayMap.this.key, this.next, tail);
                  System.arraycopy(Object2IntArrayMap.this.value, this.next + 1, Object2IntArrayMap.this.value, this.next, tail);
                  this.entry.index = -1;
                  Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
               }
            }

            public int skip(int n) {
               if (n < 0) {
                  throw new IllegalArgumentException("Argument must be nonnegative: " + n);
               } else {
                  n = Math.min(n, Object2IntArrayMap.this.size - this.next);
                  this.next += n;
                  if (n != 0) {
                     this.curr = this.next - 1;
                  }

                  return n;
               }
            }

            public void forEachRemaining(Consumer action) {
               int max = Object2IntArrayMap.this.size;

               while(this.next < max) {
                  this.entry.index = this.curr = this.next++;
                  action.accept(this.entry);
               }

            }
         };
      }

      public ObjectSpliterator spliterator() {
         return new EntrySetSpliterator(0, Object2IntArrayMap.this.size);
      }

      public void forEach(Consumer action) {
         int i = 0;

         for(int max = Object2IntArrayMap.this.size; i < max; ++i) {
            action.accept(Object2IntArrayMap.this.new MapEntry(i));
         }

      }

      public void fastForEach(Consumer action) {
         Object2IntArrayMap<K>.MapEntry entry = Object2IntArrayMap.this.new MapEntry();
         int i = 0;

         for(int max = Object2IntArrayMap.this.size; i < max; ++i) {
            entry.index = i;
            action.accept(entry);
         }

      }

      public int size() {
         return Object2IntArrayMap.this.size;
      }

      public boolean contains(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry<?, ?> e = (Map.Entry)o;
            if (e.getValue() != null && e.getValue() instanceof Integer) {
               K k = (K)e.getKey();
               return Object2IntArrayMap.this.containsKey(k) && Object2IntArrayMap.this.getInt(k) == (Integer)e.getValue();
            } else {
               return false;
            }
         }
      }

      public boolean remove(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry<?, ?> e = (Map.Entry)o;
            if (e.getValue() != null && e.getValue() instanceof Integer) {
               K k = (K)e.getKey();
               int v = (Integer)e.getValue();
               int oldPos = Object2IntArrayMap.this.findKey(k);
               if (oldPos != -1 && v == Object2IntArrayMap.this.value[oldPos]) {
                  int tail = Object2IntArrayMap.this.size - oldPos - 1;
                  System.arraycopy(Object2IntArrayMap.this.key, oldPos + 1, Object2IntArrayMap.this.key, oldPos, tail);
                  System.arraycopy(Object2IntArrayMap.this.value, oldPos + 1, Object2IntArrayMap.this.value, oldPos, tail);
                  --Object2IntArrayMap.this.size;
                  Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
                  return true;
               } else {
                  return false;
               }
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

         protected final Object2IntMap.Entry get(int location) {
            return Object2IntArrayMap.this.new MapEntry(location);
         }

         protected final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
            return EntrySet.this.new EntrySetSpliterator(pos, maxPos);
         }
      }
   }

   private final class KeySet extends AbstractObjectSet {
      private KeySet() {
      }

      public boolean contains(Object k) {
         return Object2IntArrayMap.this.findKey(k) != -1;
      }

      public boolean remove(Object k) {
         int oldPos = Object2IntArrayMap.this.findKey(k);
         if (oldPos == -1) {
            return false;
         } else {
            int tail = Object2IntArrayMap.this.size - oldPos - 1;
            System.arraycopy(Object2IntArrayMap.this.key, oldPos + 1, Object2IntArrayMap.this.key, oldPos, tail);
            System.arraycopy(Object2IntArrayMap.this.value, oldPos + 1, Object2IntArrayMap.this.value, oldPos, tail);
            --Object2IntArrayMap.this.size;
            Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
            return true;
         }
      }

      public ObjectIterator iterator() {
         return new ObjectIterator() {
            int pos = 0;

            public boolean hasNext() {
               return this.pos < Object2IntArrayMap.this.size;
            }

            public Object next() {
               if (!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  return Object2IntArrayMap.this.key[this.pos++];
               }
            }

            public void remove() {
               if (this.pos == 0) {
                  throw new IllegalStateException();
               } else {
                  int tail = Object2IntArrayMap.this.size - this.pos;
                  System.arraycopy(Object2IntArrayMap.this.key, this.pos, Object2IntArrayMap.this.key, this.pos - 1, tail);
                  System.arraycopy(Object2IntArrayMap.this.value, this.pos, Object2IntArrayMap.this.value, this.pos - 1, tail);
                  --Object2IntArrayMap.this.size;
                  --this.pos;
                  Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
               }
            }

            public void forEachRemaining(Consumer action) {
               Object[] key = Object2IntArrayMap.this.key;
               int max = Object2IntArrayMap.this.size;

               while(this.pos < max) {
                  action.accept(key[this.pos++]);
               }

            }
         };
      }

      public ObjectSpliterator spliterator() {
         return new KeySetSpliterator(0, Object2IntArrayMap.this.size);
      }

      public void forEach(Consumer action) {
         Object[] key = Object2IntArrayMap.this.key;
         int i = 0;

         for(int max = Object2IntArrayMap.this.size; i < max; ++i) {
            action.accept(key[i]);
         }

      }

      public int size() {
         return Object2IntArrayMap.this.size;
      }

      public void clear() {
         Object2IntArrayMap.this.clear();
      }

      final class KeySetSpliterator extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator implements ObjectSpliterator {
         KeySetSpliterator(int pos, int maxPos) {
            super(pos, maxPos);
         }

         public int characteristics() {
            return 16465;
         }

         protected final Object get(int location) {
            return Object2IntArrayMap.this.key[location];
         }

         protected final KeySetSpliterator makeForSplit(int pos, int maxPos) {
            return KeySet.this.new KeySetSpliterator(pos, maxPos);
         }

         public void forEachRemaining(Consumer action) {
            Object[] key = Object2IntArrayMap.this.key;
            int max = Object2IntArrayMap.this.size;

            while(this.pos < max) {
               action.accept(key[this.pos++]);
            }

         }
      }
   }

   private final class ValuesCollection extends AbstractIntCollection {
      private ValuesCollection() {
      }

      public boolean contains(int v) {
         return Object2IntArrayMap.this.containsValue(v);
      }

      public IntIterator iterator() {
         return new IntIterator() {
            int pos = 0;

            public boolean hasNext() {
               return this.pos < Object2IntArrayMap.this.size;
            }

            public int nextInt() {
               if (!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  return Object2IntArrayMap.this.value[this.pos++];
               }
            }

            public void remove() {
               if (this.pos == 0) {
                  throw new IllegalStateException();
               } else {
                  int tail = Object2IntArrayMap.this.size - this.pos;
                  System.arraycopy(Object2IntArrayMap.this.key, this.pos, Object2IntArrayMap.this.key, this.pos - 1, tail);
                  System.arraycopy(Object2IntArrayMap.this.value, this.pos, Object2IntArrayMap.this.value, this.pos - 1, tail);
                  --Object2IntArrayMap.this.size;
                  --this.pos;
                  Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
               }
            }

            public void forEachRemaining(IntConsumer action) {
               int[] value = Object2IntArrayMap.this.value;
               int max = Object2IntArrayMap.this.size;

               while(this.pos < max) {
                  action.accept(value[this.pos++]);
               }

            }
         };
      }

      public IntSpliterator spliterator() {
         return new ValuesSpliterator(0, Object2IntArrayMap.this.size);
      }

      public void forEach(IntConsumer action) {
         int[] value = Object2IntArrayMap.this.value;
         int i = 0;

         for(int max = Object2IntArrayMap.this.size; i < max; ++i) {
            action.accept(value[i]);
         }

      }

      public int size() {
         return Object2IntArrayMap.this.size;
      }

      public void clear() {
         Object2IntArrayMap.this.clear();
      }

      final class ValuesSpliterator extends IntSpliterators.EarlyBindingSizeIndexBasedSpliterator implements IntSpliterator {
         ValuesSpliterator(int pos, int maxPos) {
            super(pos, maxPos);
         }

         public int characteristics() {
            return 16720;
         }

         protected final int get(int location) {
            return Object2IntArrayMap.this.value[location];
         }

         protected final ValuesSpliterator makeForSplit(int pos, int maxPos) {
            return ValuesCollection.this.new ValuesSpliterator(pos, maxPos);
         }

         public void forEachRemaining(IntConsumer action) {
            int[] value = Object2IntArrayMap.this.value;
            int max = Object2IntArrayMap.this.size;

            while(this.pos < max) {
               action.accept(value[this.pos++]);
            }

         }
      }
   }
}
