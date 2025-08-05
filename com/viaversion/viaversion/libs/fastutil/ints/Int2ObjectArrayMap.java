package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

public class Int2ObjectArrayMap extends AbstractInt2ObjectMap implements Serializable, Cloneable {
   private static final long serialVersionUID = 1L;
   protected transient int[] key;
   protected transient Object[] value;
   protected int size;
   protected transient Int2ObjectMap.FastEntrySet entries;
   protected transient IntSet keys;
   protected transient ObjectCollection values;

   public Int2ObjectArrayMap(int[] key, Object[] value) {
      this.key = key;
      this.value = value;
      this.size = key.length;
      if (key.length != value.length) {
         throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
      }
   }

   public Int2ObjectArrayMap() {
      this.key = IntArrays.EMPTY_ARRAY;
      this.value = ObjectArrays.EMPTY_ARRAY;
   }

   public Int2ObjectArrayMap(int capacity) {
      this.key = new int[capacity];
      this.value = new Object[capacity];
   }

   public Int2ObjectArrayMap(Int2ObjectMap m) {
      this(m.size());
      int i = 0;

      for(Int2ObjectMap.Entry e : m.int2ObjectEntrySet()) {
         this.key[i] = e.getIntKey();
         this.value[i] = e.getValue();
         ++i;
      }

      this.size = i;
   }

   public Int2ObjectArrayMap(Map m) {
      this(m.size());
      int i = 0;

      for(Map.Entry e : m.entrySet()) {
         this.key[i] = (Integer)e.getKey();
         this.value[i] = e.getValue();
         ++i;
      }

      this.size = i;
   }

   public Int2ObjectArrayMap(int[] key, Object[] value, int size) {
      this.key = key;
      this.value = value;
      this.size = size;
      if (key.length != value.length) {
         throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
      } else if (size > key.length) {
         throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
      }
   }

   public Int2ObjectMap.FastEntrySet int2ObjectEntrySet() {
      if (this.entries == null) {
         this.entries = new EntrySet();
      }

      return this.entries;
   }

   private int findKey(int k) {
      int[] key = this.key;
      int i = this.size;

      while(i-- != 0) {
         if (key[i] == k) {
            return i;
         }
      }

      return -1;
   }

   public Object get(int k) {
      int[] key = this.key;
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
      Object[] value = this.value;

      for(int i = this.size; i-- != 0; value[i] = null) {
      }

      this.size = 0;
   }

   public boolean containsKey(int k) {
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

   public Object put(int k, Object v) {
      int oldKey = this.findKey(k);
      if (oldKey != -1) {
         V oldValue = (V)this.value[oldKey];
         this.value[oldKey] = v;
         return oldValue;
      } else {
         if (this.size == this.key.length) {
            int[] newKey = new int[this.size == 0 ? 2 : this.size * 2];
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

   public Object remove(int k) {
      int oldPos = this.findKey(k);
      if (oldPos == -1) {
         return this.defRetValue;
      } else {
         V oldValue = (V)this.value[oldPos];
         int tail = this.size - oldPos - 1;
         System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
         System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
         --this.size;
         this.value[this.size] = null;
         return oldValue;
      }
   }

   public IntSet keySet() {
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

   public Int2ObjectArrayMap clone() {
      Int2ObjectArrayMap<V> c;
      try {
         c = (Int2ObjectArrayMap)super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }

      c.key = (int[])this.key.clone();
      c.value = this.value.clone();
      c.entries = null;
      c.keys = null;
      c.values = null;
      return c;
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      s.defaultWriteObject();
      int[] key = this.key;
      Object[] value = this.value;
      int i = 0;

      for(int max = this.size; i < max; ++i) {
         s.writeInt(key[i]);
         s.writeObject(value[i]);
      }

   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      int[] key = this.key = new int[this.size];
      Object[] value = this.value = new Object[this.size];

      for(int i = 0; i < this.size; ++i) {
         key[i] = s.readInt();
         value[i] = s.readObject();
      }

   }

   private final class MapEntry implements Int2ObjectMap.Entry, Map.Entry, IntObjectPair {
      int index;

      MapEntry() {
      }

      MapEntry(final int index) {
         this.index = index;
      }

      public int getIntKey() {
         return Int2ObjectArrayMap.this.key[this.index];
      }

      public int leftInt() {
         return Int2ObjectArrayMap.this.key[this.index];
      }

      public Object getValue() {
         return Int2ObjectArrayMap.this.value[this.index];
      }

      public Object right() {
         return Int2ObjectArrayMap.this.value[this.index];
      }

      public Object setValue(Object v) {
         V oldValue = (V)Int2ObjectArrayMap.this.value[this.index];
         Int2ObjectArrayMap.this.value[this.index] = v;
         return oldValue;
      }

      public IntObjectPair right(Object v) {
         Int2ObjectArrayMap.this.value[this.index] = v;
         return this;
      }

      /** @deprecated */
      @Deprecated
      public Integer getKey() {
         return Int2ObjectArrayMap.this.key[this.index];
      }

      public boolean equals(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry<Integer, V> e = (Map.Entry)o;
            return Int2ObjectArrayMap.this.key[this.index] == (Integer)e.getKey() && Objects.equals(Int2ObjectArrayMap.this.value[this.index], e.getValue());
         }
      }

      public int hashCode() {
         return Int2ObjectArrayMap.this.key[this.index] ^ (Int2ObjectArrayMap.this.value[this.index] == null ? 0 : Int2ObjectArrayMap.this.value[this.index].hashCode());
      }

      public String toString() {
         return Int2ObjectArrayMap.this.key[this.index] + "=>" + Int2ObjectArrayMap.this.value[this.index];
      }
   }

   private final class EntrySet extends AbstractObjectSet implements Int2ObjectMap.FastEntrySet {
      private EntrySet() {
      }

      public ObjectIterator iterator() {
         return new ObjectIterator() {
            private MapEntry entry;
            int curr = -1;
            int next = 0;

            public boolean hasNext() {
               return this.next < Int2ObjectArrayMap.this.size;
            }

            public Int2ObjectMap.Entry next() {
               if (!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  return this.entry = Int2ObjectArrayMap.this.new MapEntry(this.curr = this.next++);
               }
            }

            public void remove() {
               if (this.curr == -1) {
                  throw new IllegalStateException();
               } else {
                  this.curr = -1;
                  int tail = Int2ObjectArrayMap.this.size-- - this.next--;
                  System.arraycopy(Int2ObjectArrayMap.this.key, this.next + 1, Int2ObjectArrayMap.this.key, this.next, tail);
                  System.arraycopy(Int2ObjectArrayMap.this.value, this.next + 1, Int2ObjectArrayMap.this.value, this.next, tail);
                  this.entry.index = -1;
                  Int2ObjectArrayMap.this.value[Int2ObjectArrayMap.this.size] = null;
               }
            }

            public int skip(int n) {
               if (n < 0) {
                  throw new IllegalArgumentException("Argument must be nonnegative: " + n);
               } else {
                  n = Math.min(n, Int2ObjectArrayMap.this.size - this.next);
                  this.next += n;
                  if (n != 0) {
                     this.curr = this.next - 1;
                  }

                  return n;
               }
            }

            public void forEachRemaining(Consumer action) {
               int max = Int2ObjectArrayMap.this.size;

               while(this.next < max) {
                  this.entry = Int2ObjectArrayMap.this.new MapEntry(this.curr = this.next++);
                  action.accept(this.entry);
               }

            }
         };
      }

      public ObjectIterator fastIterator() {
         return new ObjectIterator() {
            private MapEntry entry = Int2ObjectArrayMap.this.new MapEntry();
            int next = 0;
            int curr = -1;

            public boolean hasNext() {
               return this.next < Int2ObjectArrayMap.this.size;
            }

            public Int2ObjectMap.Entry next() {
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
                  int tail = Int2ObjectArrayMap.this.size-- - this.next--;
                  System.arraycopy(Int2ObjectArrayMap.this.key, this.next + 1, Int2ObjectArrayMap.this.key, this.next, tail);
                  System.arraycopy(Int2ObjectArrayMap.this.value, this.next + 1, Int2ObjectArrayMap.this.value, this.next, tail);
                  this.entry.index = -1;
                  Int2ObjectArrayMap.this.value[Int2ObjectArrayMap.this.size] = null;
               }
            }

            public int skip(int n) {
               if (n < 0) {
                  throw new IllegalArgumentException("Argument must be nonnegative: " + n);
               } else {
                  n = Math.min(n, Int2ObjectArrayMap.this.size - this.next);
                  this.next += n;
                  if (n != 0) {
                     this.curr = this.next - 1;
                  }

                  return n;
               }
            }

            public void forEachRemaining(Consumer action) {
               int max = Int2ObjectArrayMap.this.size;

               while(this.next < max) {
                  this.entry.index = this.curr = this.next++;
                  action.accept(this.entry);
               }

            }
         };
      }

      public ObjectSpliterator spliterator() {
         return new EntrySetSpliterator(0, Int2ObjectArrayMap.this.size);
      }

      public void forEach(Consumer action) {
         int i = 0;

         for(int max = Int2ObjectArrayMap.this.size; i < max; ++i) {
            action.accept(Int2ObjectArrayMap.this.new MapEntry(i));
         }

      }

      public void fastForEach(Consumer action) {
         Int2ObjectArrayMap<V>.MapEntry entry = Int2ObjectArrayMap.this.new MapEntry();
         int i = 0;

         for(int max = Int2ObjectArrayMap.this.size; i < max; ++i) {
            entry.index = i;
            action.accept(entry);
         }

      }

      public int size() {
         return Int2ObjectArrayMap.this.size;
      }

      public boolean contains(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry<?, ?> e = (Map.Entry)o;
            if (e.getKey() != null && e.getKey() instanceof Integer) {
               int k = (Integer)e.getKey();
               return Int2ObjectArrayMap.this.containsKey(k) && Objects.equals(Int2ObjectArrayMap.this.get(k), e.getValue());
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
            if (e.getKey() != null && e.getKey() instanceof Integer) {
               int k = (Integer)e.getKey();
               V v = (V)e.getValue();
               int oldPos = Int2ObjectArrayMap.this.findKey(k);
               if (oldPos != -1 && Objects.equals(v, Int2ObjectArrayMap.this.value[oldPos])) {
                  int tail = Int2ObjectArrayMap.this.size - oldPos - 1;
                  System.arraycopy(Int2ObjectArrayMap.this.key, oldPos + 1, Int2ObjectArrayMap.this.key, oldPos, tail);
                  System.arraycopy(Int2ObjectArrayMap.this.value, oldPos + 1, Int2ObjectArrayMap.this.value, oldPos, tail);
                  --Int2ObjectArrayMap.this.size;
                  Int2ObjectArrayMap.this.value[Int2ObjectArrayMap.this.size] = null;
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

         protected final Int2ObjectMap.Entry get(int location) {
            return Int2ObjectArrayMap.this.new MapEntry(location);
         }

         protected final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
            return EntrySet.this.new EntrySetSpliterator(pos, maxPos);
         }
      }
   }

   private final class KeySet extends AbstractIntSet {
      private KeySet() {
      }

      public boolean contains(int k) {
         return Int2ObjectArrayMap.this.findKey(k) != -1;
      }

      public boolean remove(int k) {
         int oldPos = Int2ObjectArrayMap.this.findKey(k);
         if (oldPos == -1) {
            return false;
         } else {
            int tail = Int2ObjectArrayMap.this.size - oldPos - 1;
            System.arraycopy(Int2ObjectArrayMap.this.key, oldPos + 1, Int2ObjectArrayMap.this.key, oldPos, tail);
            System.arraycopy(Int2ObjectArrayMap.this.value, oldPos + 1, Int2ObjectArrayMap.this.value, oldPos, tail);
            --Int2ObjectArrayMap.this.size;
            Int2ObjectArrayMap.this.value[Int2ObjectArrayMap.this.size] = null;
            return true;
         }
      }

      public IntIterator iterator() {
         return new IntIterator() {
            int pos = 0;

            public boolean hasNext() {
               return this.pos < Int2ObjectArrayMap.this.size;
            }

            public int nextInt() {
               if (!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  return Int2ObjectArrayMap.this.key[this.pos++];
               }
            }

            public void remove() {
               if (this.pos == 0) {
                  throw new IllegalStateException();
               } else {
                  int tail = Int2ObjectArrayMap.this.size - this.pos;
                  System.arraycopy(Int2ObjectArrayMap.this.key, this.pos, Int2ObjectArrayMap.this.key, this.pos - 1, tail);
                  System.arraycopy(Int2ObjectArrayMap.this.value, this.pos, Int2ObjectArrayMap.this.value, this.pos - 1, tail);
                  --Int2ObjectArrayMap.this.size;
                  --this.pos;
                  Int2ObjectArrayMap.this.value[Int2ObjectArrayMap.this.size] = null;
               }
            }

            public void forEachRemaining(java.util.function.IntConsumer action) {
               int[] key = Int2ObjectArrayMap.this.key;
               int max = Int2ObjectArrayMap.this.size;

               while(this.pos < max) {
                  action.accept(key[this.pos++]);
               }

            }
         };
      }

      public IntSpliterator spliterator() {
         return new KeySetSpliterator(0, Int2ObjectArrayMap.this.size);
      }

      public void forEach(java.util.function.IntConsumer action) {
         int[] key = Int2ObjectArrayMap.this.key;
         int i = 0;

         for(int max = Int2ObjectArrayMap.this.size; i < max; ++i) {
            action.accept(key[i]);
         }

      }

      public int size() {
         return Int2ObjectArrayMap.this.size;
      }

      public void clear() {
         Int2ObjectArrayMap.this.clear();
      }

      final class KeySetSpliterator extends IntSpliterators.EarlyBindingSizeIndexBasedSpliterator implements IntSpliterator {
         KeySetSpliterator(int pos, int maxPos) {
            super(pos, maxPos);
         }

         public int characteristics() {
            return 16721;
         }

         protected final int get(int location) {
            return Int2ObjectArrayMap.this.key[location];
         }

         protected final KeySetSpliterator makeForSplit(int pos, int maxPos) {
            return KeySet.this.new KeySetSpliterator(pos, maxPos);
         }

         public void forEachRemaining(java.util.function.IntConsumer action) {
            int[] key = Int2ObjectArrayMap.this.key;
            int max = Int2ObjectArrayMap.this.size;

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
         return Int2ObjectArrayMap.this.containsValue(v);
      }

      public ObjectIterator iterator() {
         return new ObjectIterator() {
            int pos = 0;

            public boolean hasNext() {
               return this.pos < Int2ObjectArrayMap.this.size;
            }

            public Object next() {
               if (!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  return Int2ObjectArrayMap.this.value[this.pos++];
               }
            }

            public void remove() {
               if (this.pos == 0) {
                  throw new IllegalStateException();
               } else {
                  int tail = Int2ObjectArrayMap.this.size - this.pos;
                  System.arraycopy(Int2ObjectArrayMap.this.key, this.pos, Int2ObjectArrayMap.this.key, this.pos - 1, tail);
                  System.arraycopy(Int2ObjectArrayMap.this.value, this.pos, Int2ObjectArrayMap.this.value, this.pos - 1, tail);
                  --Int2ObjectArrayMap.this.size;
                  --this.pos;
                  Int2ObjectArrayMap.this.value[Int2ObjectArrayMap.this.size] = null;
               }
            }

            public void forEachRemaining(Consumer action) {
               Object[] value = Int2ObjectArrayMap.this.value;
               int max = Int2ObjectArrayMap.this.size;

               while(this.pos < max) {
                  action.accept(value[this.pos++]);
               }

            }
         };
      }

      public ObjectSpliterator spliterator() {
         return new ValuesSpliterator(0, Int2ObjectArrayMap.this.size);
      }

      public void forEach(Consumer action) {
         Object[] value = Int2ObjectArrayMap.this.value;
         int i = 0;

         for(int max = Int2ObjectArrayMap.this.size; i < max; ++i) {
            action.accept(value[i]);
         }

      }

      public int size() {
         return Int2ObjectArrayMap.this.size;
      }

      public void clear() {
         Int2ObjectArrayMap.this.clear();
      }

      final class ValuesSpliterator extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator implements ObjectSpliterator {
         ValuesSpliterator(int pos, int maxPos) {
            super(pos, maxPos);
         }

         public int characteristics() {
            return 16464;
         }

         protected final Object get(int location) {
            return Int2ObjectArrayMap.this.value[location];
         }

         protected final ValuesSpliterator makeForSplit(int pos, int maxPos) {
            return ValuesCollection.this.new ValuesSpliterator(pos, maxPos);
         }

         public void forEachRemaining(Consumer action) {
            Object[] value = Int2ObjectArrayMap.this.value;
            int max = Int2ObjectArrayMap.this.size;

            while(this.pos < max) {
               action.accept(value[this.pos++]);
            }

         }
      }
   }
}
