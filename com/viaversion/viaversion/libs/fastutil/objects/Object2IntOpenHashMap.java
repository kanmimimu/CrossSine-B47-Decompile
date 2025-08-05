package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.ToIntFunction;

public class Object2IntOpenHashMap extends AbstractObject2IntMap implements Serializable, Cloneable, Hash {
   private static final long serialVersionUID = 0L;
   private static final boolean ASSERTS = false;
   protected transient Object[] key;
   protected transient int[] value;
   protected transient int mask;
   protected transient boolean containsNullKey;
   protected transient int n;
   protected transient int maxFill;
   protected final transient int minN;
   protected int size;
   protected final float f;
   protected transient Object2IntMap.FastEntrySet entries;
   protected transient ObjectSet keys;
   protected transient IntCollection values;

   public Object2IntOpenHashMap(int expected, float f) {
      if (!(f <= 0.0F) && !(f >= 1.0F)) {
         if (expected < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
         } else {
            this.f = f;
            this.minN = this.n = HashCommon.arraySize(expected, f);
            this.mask = this.n - 1;
            this.maxFill = HashCommon.maxFill(this.n, f);
            this.key = new Object[this.n + 1];
            this.value = new int[this.n + 1];
         }
      } else {
         throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1");
      }
   }

   public Object2IntOpenHashMap(int expected) {
      this(expected, 0.75F);
   }

   public Object2IntOpenHashMap() {
      this(16, 0.75F);
   }

   public Object2IntOpenHashMap(Map m, float f) {
      this(m.size(), f);
      this.putAll(m);
   }

   public Object2IntOpenHashMap(Map m) {
      this(m, 0.75F);
   }

   public Object2IntOpenHashMap(Object2IntMap m, float f) {
      this(m.size(), f);
      this.putAll(m);
   }

   public Object2IntOpenHashMap(Object2IntMap m) {
      this(m, 0.75F);
   }

   public Object2IntOpenHashMap(Object[] k, int[] v, float f) {
      this(k.length, f);
      if (k.length != v.length) {
         throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
      } else {
         for(int i = 0; i < k.length; ++i) {
            this.put(k[i], v[i]);
         }

      }
   }

   public Object2IntOpenHashMap(Object[] k, int[] v) {
      this(k, v, 0.75F);
   }

   private int realSize() {
      return this.containsNullKey ? this.size - 1 : this.size;
   }

   public void ensureCapacity(int capacity) {
      int needed = HashCommon.arraySize(capacity, this.f);
      if (needed > this.n) {
         this.rehash(needed);
      }

   }

   private void tryCapacity(long capacity) {
      int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil((double)((float)capacity / this.f)))));
      if (needed > this.n) {
         this.rehash(needed);
      }

   }

   private int removeEntry(int pos) {
      int oldValue = this.value[pos];
      --this.size;
      this.shiftKeys(pos);
      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
         this.rehash(this.n / 2);
      }

      return oldValue;
   }

   private int removeNullEntry() {
      this.containsNullKey = false;
      this.key[this.n] = null;
      int oldValue = this.value[this.n];
      --this.size;
      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
         this.rehash(this.n / 2);
      }

      return oldValue;
   }

   public void putAll(Map m) {
      if ((double)this.f <= (double)0.5F) {
         this.ensureCapacity(m.size());
      } else {
         this.tryCapacity((long)(this.size() + m.size()));
      }

      super.putAll(m);
   }

   private int find(Object k) {
      if (k == null) {
         return this.containsNullKey ? this.n : -(this.n + 1);
      } else {
         K[] key = (K[])this.key;
         K curr;
         int pos;
         if ((curr = (K)key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
            return -(pos + 1);
         } else if (k.equals(curr)) {
            return pos;
         } else {
            while((curr = (K)key[pos = pos + 1 & this.mask]) != null) {
               if (k.equals(curr)) {
                  return pos;
               }
            }

            return -(pos + 1);
         }
      }
   }

   private void insert(int pos, Object k, int v) {
      if (pos == this.n) {
         this.containsNullKey = true;
      }

      this.key[pos] = k;
      this.value[pos] = v;
      if (this.size++ >= this.maxFill) {
         this.rehash(HashCommon.arraySize(this.size + 1, this.f));
      }

   }

   public int put(Object k, int v) {
      int pos = this.find(k);
      if (pos < 0) {
         this.insert(-pos - 1, k, v);
         return this.defRetValue;
      } else {
         int oldValue = this.value[pos];
         this.value[pos] = v;
         return oldValue;
      }
   }

   private int addToValue(int pos, int incr) {
      int oldValue = this.value[pos];
      this.value[pos] = oldValue + incr;
      return oldValue;
   }

   public int addTo(Object k, int incr) {
      int pos;
      if (k == null) {
         if (this.containsNullKey) {
            return this.addToValue(this.n, incr);
         }

         pos = this.n;
         this.containsNullKey = true;
      } else {
         K[] key = (K[])this.key;
         K curr;
         if ((curr = (K)key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
            if (curr.equals(k)) {
               return this.addToValue(pos, incr);
            }

            while((curr = (K)key[pos = pos + 1 & this.mask]) != null) {
               if (curr.equals(k)) {
                  return this.addToValue(pos, incr);
               }
            }
         }
      }

      this.key[pos] = k;
      this.value[pos] = this.defRetValue + incr;
      if (this.size++ >= this.maxFill) {
         this.rehash(HashCommon.arraySize(this.size + 1, this.f));
      }

      return this.defRetValue;
   }

   protected final void shiftKeys(int pos) {
      K[] key = (K[])this.key;
      int[] value = this.value;

      while(true) {
         int last = pos;
         pos = pos + 1 & this.mask;

         K curr;
         while(true) {
            if ((curr = (K)key[pos]) == null) {
               key[last] = null;
               return;
            }

            int slot = HashCommon.mix(curr.hashCode()) & this.mask;
            if (last <= pos) {
               if (last >= slot || slot > pos) {
                  break;
               }
            } else if (last >= slot && slot > pos) {
               break;
            }

            pos = pos + 1 & this.mask;
         }

         key[last] = curr;
         value[last] = value[pos];
      }
   }

   public int removeInt(Object k) {
      if (k == null) {
         return this.containsNullKey ? this.removeNullEntry() : this.defRetValue;
      } else {
         K[] key = (K[])this.key;
         K curr;
         int pos;
         if ((curr = (K)key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
            return this.defRetValue;
         } else if (k.equals(curr)) {
            return this.removeEntry(pos);
         } else {
            while((curr = (K)key[pos = pos + 1 & this.mask]) != null) {
               if (k.equals(curr)) {
                  return this.removeEntry(pos);
               }
            }

            return this.defRetValue;
         }
      }
   }

   public int getInt(Object k) {
      if (k == null) {
         return this.containsNullKey ? this.value[this.n] : this.defRetValue;
      } else {
         K[] key = (K[])this.key;
         K curr;
         int pos;
         if ((curr = (K)key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
            return this.defRetValue;
         } else if (k.equals(curr)) {
            return this.value[pos];
         } else {
            while((curr = (K)key[pos = pos + 1 & this.mask]) != null) {
               if (k.equals(curr)) {
                  return this.value[pos];
               }
            }

            return this.defRetValue;
         }
      }
   }

   public boolean containsKey(Object k) {
      if (k == null) {
         return this.containsNullKey;
      } else {
         K[] key = (K[])this.key;
         K curr;
         int pos;
         if ((curr = (K)key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
            return false;
         } else if (k.equals(curr)) {
            return true;
         } else {
            while((curr = (K)key[pos = pos + 1 & this.mask]) != null) {
               if (k.equals(curr)) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public boolean containsValue(int v) {
      K[] key = (K[])this.key;
      int[] value = this.value;
      if (this.containsNullKey && value[this.n] == v) {
         return true;
      } else {
         int i = this.n;

         while(i-- != 0) {
            if (key[i] != null && value[i] == v) {
               return true;
            }
         }

         return false;
      }
   }

   public int getOrDefault(Object k, int defaultValue) {
      if (k == null) {
         return this.containsNullKey ? this.value[this.n] : defaultValue;
      } else {
         K[] key = (K[])this.key;
         K curr;
         int pos;
         if ((curr = (K)key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
            return defaultValue;
         } else if (k.equals(curr)) {
            return this.value[pos];
         } else {
            while((curr = (K)key[pos = pos + 1 & this.mask]) != null) {
               if (k.equals(curr)) {
                  return this.value[pos];
               }
            }

            return defaultValue;
         }
      }
   }

   public int putIfAbsent(Object k, int v) {
      int pos = this.find(k);
      if (pos >= 0) {
         return this.value[pos];
      } else {
         this.insert(-pos - 1, k, v);
         return this.defRetValue;
      }
   }

   public boolean remove(Object k, int v) {
      if (k == null) {
         if (this.containsNullKey && v == this.value[this.n]) {
            this.removeNullEntry();
            return true;
         } else {
            return false;
         }
      } else {
         K[] key = (K[])this.key;
         K curr;
         int pos;
         if ((curr = (K)key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
            return false;
         } else if (k.equals(curr) && v == this.value[pos]) {
            this.removeEntry(pos);
            return true;
         } else {
            while((curr = (K)key[pos = pos + 1 & this.mask]) != null) {
               if (k.equals(curr) && v == this.value[pos]) {
                  this.removeEntry(pos);
                  return true;
               }
            }

            return false;
         }
      }
   }

   public boolean replace(Object k, int oldValue, int v) {
      int pos = this.find(k);
      if (pos >= 0 && oldValue == this.value[pos]) {
         this.value[pos] = v;
         return true;
      } else {
         return false;
      }
   }

   public int replace(Object k, int v) {
      int pos = this.find(k);
      if (pos < 0) {
         return this.defRetValue;
      } else {
         int oldValue = this.value[pos];
         this.value[pos] = v;
         return oldValue;
      }
   }

   public int computeIfAbsent(Object k, ToIntFunction mappingFunction) {
      Objects.requireNonNull(mappingFunction);
      int pos = this.find(k);
      if (pos >= 0) {
         return this.value[pos];
      } else {
         int newValue = mappingFunction.applyAsInt(k);
         this.insert(-pos - 1, k, newValue);
         return newValue;
      }
   }

   public int computeIfAbsent(Object key, Object2IntFunction mappingFunction) {
      Objects.requireNonNull(mappingFunction);
      int pos = this.find(key);
      if (pos >= 0) {
         return this.value[pos];
      } else if (!mappingFunction.containsKey(key)) {
         return this.defRetValue;
      } else {
         int newValue = mappingFunction.getInt(key);
         this.insert(-pos - 1, key, newValue);
         return newValue;
      }
   }

   public int computeIntIfPresent(Object k, BiFunction remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      int pos = this.find(k);
      if (pos < 0) {
         return this.defRetValue;
      } else {
         Integer newValue = (Integer)remappingFunction.apply(k, this.value[pos]);
         if (newValue == null) {
            if (k == null) {
               this.removeNullEntry();
            } else {
               this.removeEntry(pos);
            }

            return this.defRetValue;
         } else {
            return this.value[pos] = newValue;
         }
      }
   }

   public int computeInt(Object k, BiFunction remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      int pos = this.find(k);
      Integer newValue = (Integer)remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
      if (newValue == null) {
         if (pos >= 0) {
            if (k == null) {
               this.removeNullEntry();
            } else {
               this.removeEntry(pos);
            }
         }

         return this.defRetValue;
      } else {
         int newVal = newValue;
         if (pos < 0) {
            this.insert(-pos - 1, k, newVal);
            return newVal;
         } else {
            return this.value[pos] = newVal;
         }
      }
   }

   public int merge(Object k, int v, BiFunction remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      int pos = this.find(k);
      if (pos < 0) {
         if (pos < 0) {
            this.insert(-pos - 1, k, v);
         } else {
            this.value[pos] = v;
         }

         return v;
      } else {
         Integer newValue = (Integer)remappingFunction.apply(this.value[pos], v);
         if (newValue == null) {
            if (k == null) {
               this.removeNullEntry();
            } else {
               this.removeEntry(pos);
            }

            return this.defRetValue;
         } else {
            return this.value[pos] = newValue;
         }
      }
   }

   public void clear() {
      if (this.size != 0) {
         this.size = 0;
         this.containsNullKey = false;
         Arrays.fill(this.key, (Object)null);
      }
   }

   public int size() {
      return this.size;
   }

   public boolean isEmpty() {
      return this.size == 0;
   }

   public Object2IntMap.FastEntrySet object2IntEntrySet() {
      if (this.entries == null) {
         this.entries = new MapEntrySet();
      }

      return this.entries;
   }

   public ObjectSet keySet() {
      if (this.keys == null) {
         this.keys = new KeySet();
      }

      return this.keys;
   }

   public IntCollection values() {
      if (this.values == null) {
         this.values = new AbstractIntCollection() {
            public IntIterator iterator() {
               return Object2IntOpenHashMap.this.new ValueIterator();
            }

            public IntSpliterator spliterator() {
               return Object2IntOpenHashMap.this.new ValueSpliterator();
            }

            public void forEach(IntConsumer consumer) {
               K[] key = (K[])Object2IntOpenHashMap.this.key;
               int[] value = Object2IntOpenHashMap.this.value;
               if (Object2IntOpenHashMap.this.containsNullKey) {
                  consumer.accept(value[Object2IntOpenHashMap.this.n]);
               }

               int pos = Object2IntOpenHashMap.this.n;

               while(pos-- != 0) {
                  if (key[pos] != null) {
                     consumer.accept(value[pos]);
                  }
               }

            }

            public int size() {
               return Object2IntOpenHashMap.this.size;
            }

            public boolean contains(int v) {
               return Object2IntOpenHashMap.this.containsValue(v);
            }

            public void clear() {
               Object2IntOpenHashMap.this.clear();
            }
         };
      }

      return this.values;
   }

   public boolean trim() {
      return this.trim(this.size);
   }

   public boolean trim(int n) {
      int l = HashCommon.nextPowerOfTwo((int)Math.ceil((double)((float)n / this.f)));
      if (l < this.n && this.size <= HashCommon.maxFill(l, this.f)) {
         try {
            this.rehash(l);
            return true;
         } catch (OutOfMemoryError var4) {
            return false;
         }
      } else {
         return true;
      }
   }

   protected void rehash(int newN) {
      K[] key = (K[])this.key;
      int[] value = this.value;
      int mask = newN - 1;
      K[] newKey = (K[])(new Object[newN + 1]);
      int[] newValue = new int[newN + 1];
      int i = this.n;

      int pos;
      for(int j = this.realSize(); j-- != 0; newValue[pos] = value[i]) {
         --i;
         if (key[i] != null) {
            if (newKey[pos = HashCommon.mix(key[i].hashCode()) & mask] != null) {
               while(newKey[pos = pos + 1 & mask] != null) {
               }
            }

            newKey[pos] = key[i];
         }
      }

      newValue[newN] = value[this.n];
      this.n = newN;
      this.mask = mask;
      this.maxFill = HashCommon.maxFill(this.n, this.f);
      this.key = newKey;
      this.value = newValue;
   }

   public Object2IntOpenHashMap clone() {
      Object2IntOpenHashMap<K> c;
      try {
         c = (Object2IntOpenHashMap)super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }

      c.keys = null;
      c.values = null;
      c.entries = null;
      c.containsNullKey = this.containsNullKey;
      c.key = this.key.clone();
      c.value = (int[])this.value.clone();
      return c;
   }

   public int hashCode() {
      int h = 0;
      K[] key = (K[])this.key;
      int[] value = this.value;
      int j = this.realSize();
      int i = 0;

      for(int t = 0; j-- != 0; ++i) {
         while(key[i] == null) {
            ++i;
         }

         if (this != key[i]) {
            t = key[i].hashCode();
         }

         t ^= value[i];
         h += t;
      }

      if (this.containsNullKey) {
         h += value[this.n];
      }

      return h;
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      K[] key = (K[])this.key;
      int[] value = this.value;
      Object2IntOpenHashMap<K>.EntryIterator i = new EntryIterator();
      s.defaultWriteObject();
      int j = this.size;

      while(j-- != 0) {
         int e = i.nextEntry();
         s.writeObject(key[e]);
         s.writeInt(value[e]);
      }

   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      this.n = HashCommon.arraySize(this.size, this.f);
      this.maxFill = HashCommon.maxFill(this.n, this.f);
      this.mask = this.n - 1;
      K[] key = (K[])(this.key = new Object[this.n + 1]);
      int[] value = this.value = new int[this.n + 1];

      int v;
      int pos;
      for(int i = this.size; i-- != 0; value[pos] = v) {
         K k = (K)s.readObject();
         v = s.readInt();
         if (k == null) {
            pos = this.n;
            this.containsNullKey = true;
         } else {
            for(pos = HashCommon.mix(k.hashCode()) & this.mask; key[pos] != null; pos = pos + 1 & this.mask) {
            }
         }

         key[pos] = k;
      }

   }

   private void checkTable() {
   }

   final class MapEntry implements Object2IntMap.Entry, Map.Entry, ObjectIntPair {
      int index;

      MapEntry(final int index) {
         this.index = index;
      }

      MapEntry() {
      }

      public Object getKey() {
         return Object2IntOpenHashMap.this.key[this.index];
      }

      public Object left() {
         return Object2IntOpenHashMap.this.key[this.index];
      }

      public int getIntValue() {
         return Object2IntOpenHashMap.this.value[this.index];
      }

      public int rightInt() {
         return Object2IntOpenHashMap.this.value[this.index];
      }

      public int setValue(int v) {
         int oldValue = Object2IntOpenHashMap.this.value[this.index];
         Object2IntOpenHashMap.this.value[this.index] = v;
         return oldValue;
      }

      public ObjectIntPair right(int v) {
         Object2IntOpenHashMap.this.value[this.index] = v;
         return this;
      }

      /** @deprecated */
      @Deprecated
      public Integer getValue() {
         return Object2IntOpenHashMap.this.value[this.index];
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
            return Objects.equals(Object2IntOpenHashMap.this.key[this.index], e.getKey()) && Object2IntOpenHashMap.this.value[this.index] == (Integer)e.getValue();
         }
      }

      public int hashCode() {
         return (Object2IntOpenHashMap.this.key[this.index] == null ? 0 : Object2IntOpenHashMap.this.key[this.index].hashCode()) ^ Object2IntOpenHashMap.this.value[this.index];
      }

      public String toString() {
         return Object2IntOpenHashMap.this.key[this.index] + "=>" + Object2IntOpenHashMap.this.value[this.index];
      }
   }

   private abstract class MapIterator {
      int pos;
      int last;
      int c;
      boolean mustReturnNullKey;
      ObjectArrayList wrapped;

      private MapIterator() {
         this.pos = Object2IntOpenHashMap.this.n;
         this.last = -1;
         this.c = Object2IntOpenHashMap.this.size;
         this.mustReturnNullKey = Object2IntOpenHashMap.this.containsNullKey;
      }

      abstract void acceptOnIndex(Object var1, int var2);

      public boolean hasNext() {
         return this.c != 0;
      }

      public int nextEntry() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            --this.c;
            if (this.mustReturnNullKey) {
               this.mustReturnNullKey = false;
               return this.last = Object2IntOpenHashMap.this.n;
            } else {
               K[] key = (K[])Object2IntOpenHashMap.this.key;

               while(--this.pos >= 0) {
                  if (key[this.pos] != null) {
                     return this.last = this.pos;
                  }
               }

               this.last = Integer.MIN_VALUE;
               K k = (K)this.wrapped.get(-this.pos - 1);

               int p;
               for(p = HashCommon.mix(k.hashCode()) & Object2IntOpenHashMap.this.mask; !k.equals(key[p]); p = p + 1 & Object2IntOpenHashMap.this.mask) {
               }

               return p;
            }
         }
      }

      public void forEachRemaining(Object action) {
         if (this.mustReturnNullKey) {
            this.mustReturnNullKey = false;
            this.acceptOnIndex(action, this.last = Object2IntOpenHashMap.this.n);
            --this.c;
         }

         K[] key = (K[])Object2IntOpenHashMap.this.key;

         while(this.c != 0) {
            if (--this.pos < 0) {
               this.last = Integer.MIN_VALUE;
               K k = (K)this.wrapped.get(-this.pos - 1);

               int p;
               for(p = HashCommon.mix(k.hashCode()) & Object2IntOpenHashMap.this.mask; !k.equals(key[p]); p = p + 1 & Object2IntOpenHashMap.this.mask) {
               }

               this.acceptOnIndex(action, p);
               --this.c;
            } else if (key[this.pos] != null) {
               this.acceptOnIndex(action, this.last = this.pos);
               --this.c;
            }
         }

      }

      private void shiftKeys(int pos) {
         K[] key = (K[])Object2IntOpenHashMap.this.key;
         int[] value = Object2IntOpenHashMap.this.value;

         while(true) {
            int last = pos;
            pos = pos + 1 & Object2IntOpenHashMap.this.mask;

            K curr;
            while(true) {
               if ((curr = (K)key[pos]) == null) {
                  key[last] = null;
                  return;
               }

               int slot = HashCommon.mix(curr.hashCode()) & Object2IntOpenHashMap.this.mask;
               if (last <= pos) {
                  if (last >= slot || slot > pos) {
                     break;
                  }
               } else if (last >= slot && slot > pos) {
                  break;
               }

               pos = pos + 1 & Object2IntOpenHashMap.this.mask;
            }

            if (pos < last) {
               if (this.wrapped == null) {
                  this.wrapped = new ObjectArrayList(2);
               }

               this.wrapped.add(key[pos]);
            }

            key[last] = curr;
            value[last] = value[pos];
         }
      }

      public void remove() {
         if (this.last == -1) {
            throw new IllegalStateException();
         } else {
            if (this.last == Object2IntOpenHashMap.this.n) {
               Object2IntOpenHashMap.this.containsNullKey = false;
               Object2IntOpenHashMap.this.key[Object2IntOpenHashMap.this.n] = null;
            } else {
               if (this.pos < 0) {
                  Object2IntOpenHashMap.this.removeInt(this.wrapped.set(-this.pos - 1, (Object)null));
                  this.last = -1;
                  return;
               }

               this.shiftKeys(this.last);
            }

            --Object2IntOpenHashMap.this.size;
            this.last = -1;
         }
      }

      public int skip(int n) {
         int i = n;

         while(i-- != 0 && this.hasNext()) {
            this.nextEntry();
         }

         return n - i - 1;
      }
   }

   private final class EntryIterator extends MapIterator implements ObjectIterator {
      private MapEntry entry;

      private EntryIterator() {
      }

      public MapEntry next() {
         return this.entry = Object2IntOpenHashMap.this.new MapEntry(this.nextEntry());
      }

      final void acceptOnIndex(Consumer action, int index) {
         action.accept(this.entry = Object2IntOpenHashMap.this.new MapEntry(index));
      }

      public void remove() {
         super.remove();
         this.entry.index = -1;
      }
   }

   private final class FastEntryIterator extends MapIterator implements ObjectIterator {
      private final MapEntry entry;

      private FastEntryIterator() {
         this.entry = Object2IntOpenHashMap.this.new MapEntry();
      }

      public MapEntry next() {
         this.entry.index = this.nextEntry();
         return this.entry;
      }

      final void acceptOnIndex(Consumer action, int index) {
         this.entry.index = index;
         action.accept(this.entry);
      }
   }

   private abstract class MapSpliterator {
      int pos = 0;
      int max;
      int c;
      boolean mustReturnNull;
      boolean hasSplit;

      MapSpliterator() {
         this.max = Object2IntOpenHashMap.this.n;
         this.c = 0;
         this.mustReturnNull = Object2IntOpenHashMap.this.containsNullKey;
         this.hasSplit = false;
      }

      MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
         this.max = Object2IntOpenHashMap.this.n;
         this.c = 0;
         this.mustReturnNull = Object2IntOpenHashMap.this.containsNullKey;
         this.hasSplit = false;
         this.pos = pos;
         this.max = max;
         this.mustReturnNull = mustReturnNull;
         this.hasSplit = hasSplit;
      }

      abstract void acceptOnIndex(Object var1, int var2);

      abstract MapSpliterator makeForSplit(int var1, int var2, boolean var3);

      public boolean tryAdvance(Object action) {
         if (this.mustReturnNull) {
            this.mustReturnNull = false;
            ++this.c;
            this.acceptOnIndex(action, Object2IntOpenHashMap.this.n);
            return true;
         } else {
            for(K[] key = (K[])Object2IntOpenHashMap.this.key; this.pos < this.max; ++this.pos) {
               if (key[this.pos] != null) {
                  ++this.c;
                  this.acceptOnIndex(action, this.pos++);
                  return true;
               }
            }

            return false;
         }
      }

      public void forEachRemaining(Object action) {
         if (this.mustReturnNull) {
            this.mustReturnNull = false;
            ++this.c;
            this.acceptOnIndex(action, Object2IntOpenHashMap.this.n);
         }

         for(K[] key = (K[])Object2IntOpenHashMap.this.key; this.pos < this.max; ++this.pos) {
            if (key[this.pos] != null) {
               this.acceptOnIndex(action, this.pos);
               ++this.c;
            }
         }

      }

      public long estimateSize() {
         return !this.hasSplit ? (long)(Object2IntOpenHashMap.this.size - this.c) : Math.min((long)(Object2IntOpenHashMap.this.size - this.c), (long)((double)Object2IntOpenHashMap.this.realSize() / (double)Object2IntOpenHashMap.this.n * (double)(this.max - this.pos)) + (long)(this.mustReturnNull ? 1 : 0));
      }

      public MapSpliterator trySplit() {
         if (this.pos >= this.max - 1) {
            return null;
         } else {
            int retLen = this.max - this.pos >> 1;
            if (retLen <= 1) {
               return null;
            } else {
               int myNewPos = this.pos + retLen;
               int retPos = this.pos;
               SplitType split = (SplitType)this.makeForSplit(retPos, myNewPos, this.mustReturnNull);
               this.pos = myNewPos;
               this.mustReturnNull = false;
               this.hasSplit = true;
               return split;
            }
         }
      }

      public long skip(long n) {
         if (n < 0L) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else if (n == 0L) {
            return 0L;
         } else {
            long skipped = 0L;
            if (this.mustReturnNull) {
               this.mustReturnNull = false;
               ++skipped;
               --n;
            }

            K[] key = (K[])Object2IntOpenHashMap.this.key;

            while(this.pos < this.max && n > 0L) {
               if (key[this.pos++] != null) {
                  ++skipped;
                  --n;
               }
            }

            return skipped;
         }
      }
   }

   private final class EntrySpliterator extends MapSpliterator implements ObjectSpliterator {
      private static final int POST_SPLIT_CHARACTERISTICS = 1;

      EntrySpliterator() {
      }

      EntrySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
         super(pos, max, mustReturnNull, hasSplit);
      }

      public int characteristics() {
         return this.hasSplit ? 1 : 65;
      }

      final void acceptOnIndex(Consumer action, int index) {
         action.accept(Object2IntOpenHashMap.this.new MapEntry(index));
      }

      final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
         return Object2IntOpenHashMap.this.new EntrySpliterator(pos, max, mustReturnNull, true);
      }
   }

   private final class MapEntrySet extends AbstractObjectSet implements Object2IntMap.FastEntrySet {
      private MapEntrySet() {
      }

      public ObjectIterator iterator() {
         return Object2IntOpenHashMap.this.new EntryIterator();
      }

      public ObjectIterator fastIterator() {
         return Object2IntOpenHashMap.this.new FastEntryIterator();
      }

      public ObjectSpliterator spliterator() {
         return Object2IntOpenHashMap.this.new EntrySpliterator();
      }

      public boolean contains(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry<?, ?> e = (Map.Entry)o;
            if (e.getValue() != null && e.getValue() instanceof Integer) {
               K k = (K)e.getKey();
               int v = (Integer)e.getValue();
               if (k == null) {
                  return Object2IntOpenHashMap.this.containsNullKey && Object2IntOpenHashMap.this.value[Object2IntOpenHashMap.this.n] == v;
               } else {
                  K[] key = (K[])Object2IntOpenHashMap.this.key;
                  K curr;
                  int pos;
                  if ((curr = (K)key[pos = HashCommon.mix(k.hashCode()) & Object2IntOpenHashMap.this.mask]) == null) {
                     return false;
                  } else if (k.equals(curr)) {
                     return Object2IntOpenHashMap.this.value[pos] == v;
                  } else {
                     while((curr = (K)key[pos = pos + 1 & Object2IntOpenHashMap.this.mask]) != null) {
                        if (k.equals(curr)) {
                           return Object2IntOpenHashMap.this.value[pos] == v;
                        }
                     }

                     return false;
                  }
               }
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
               if (k == null) {
                  if (Object2IntOpenHashMap.this.containsNullKey && Object2IntOpenHashMap.this.value[Object2IntOpenHashMap.this.n] == v) {
                     Object2IntOpenHashMap.this.removeNullEntry();
                     return true;
                  } else {
                     return false;
                  }
               } else {
                  K[] key = (K[])Object2IntOpenHashMap.this.key;
                  K curr;
                  int pos;
                  if ((curr = (K)key[pos = HashCommon.mix(k.hashCode()) & Object2IntOpenHashMap.this.mask]) == null) {
                     return false;
                  } else if (curr.equals(k)) {
                     if (Object2IntOpenHashMap.this.value[pos] == v) {
                        Object2IntOpenHashMap.this.removeEntry(pos);
                        return true;
                     } else {
                        return false;
                     }
                  } else {
                     while((curr = (K)key[pos = pos + 1 & Object2IntOpenHashMap.this.mask]) != null) {
                        if (curr.equals(k) && Object2IntOpenHashMap.this.value[pos] == v) {
                           Object2IntOpenHashMap.this.removeEntry(pos);
                           return true;
                        }
                     }

                     return false;
                  }
               }
            } else {
               return false;
            }
         }
      }

      public int size() {
         return Object2IntOpenHashMap.this.size;
      }

      public void clear() {
         Object2IntOpenHashMap.this.clear();
      }

      public void forEach(Consumer consumer) {
         if (Object2IntOpenHashMap.this.containsNullKey) {
            consumer.accept(Object2IntOpenHashMap.this.new MapEntry(Object2IntOpenHashMap.this.n));
         }

         K[] key = (K[])Object2IntOpenHashMap.this.key;
         int pos = Object2IntOpenHashMap.this.n;

         while(pos-- != 0) {
            if (key[pos] != null) {
               consumer.accept(Object2IntOpenHashMap.this.new MapEntry(pos));
            }
         }

      }

      public void fastForEach(Consumer consumer) {
         Object2IntOpenHashMap<K>.MapEntry entry = Object2IntOpenHashMap.this.new MapEntry();
         if (Object2IntOpenHashMap.this.containsNullKey) {
            entry.index = Object2IntOpenHashMap.this.n;
            consumer.accept(entry);
         }

         K[] key = (K[])Object2IntOpenHashMap.this.key;
         int pos = Object2IntOpenHashMap.this.n;

         while(pos-- != 0) {
            if (key[pos] != null) {
               entry.index = pos;
               consumer.accept(entry);
            }
         }

      }
   }

   private final class KeyIterator extends MapIterator implements ObjectIterator {
      public KeyIterator() {
      }

      final void acceptOnIndex(Consumer action, int index) {
         action.accept(Object2IntOpenHashMap.this.key[index]);
      }

      public Object next() {
         return Object2IntOpenHashMap.this.key[this.nextEntry()];
      }
   }

   private final class KeySpliterator extends MapSpliterator implements ObjectSpliterator {
      private static final int POST_SPLIT_CHARACTERISTICS = 1;

      KeySpliterator() {
      }

      KeySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
         super(pos, max, mustReturnNull, hasSplit);
      }

      public int characteristics() {
         return this.hasSplit ? 1 : 65;
      }

      final void acceptOnIndex(Consumer action, int index) {
         action.accept(Object2IntOpenHashMap.this.key[index]);
      }

      final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
         return Object2IntOpenHashMap.this.new KeySpliterator(pos, max, mustReturnNull, true);
      }
   }

   private final class KeySet extends AbstractObjectSet {
      private KeySet() {
      }

      public ObjectIterator iterator() {
         return Object2IntOpenHashMap.this.new KeyIterator();
      }

      public ObjectSpliterator spliterator() {
         return Object2IntOpenHashMap.this.new KeySpliterator();
      }

      public void forEach(Consumer consumer) {
         K[] key = (K[])Object2IntOpenHashMap.this.key;
         if (Object2IntOpenHashMap.this.containsNullKey) {
            consumer.accept(key[Object2IntOpenHashMap.this.n]);
         }

         int pos = Object2IntOpenHashMap.this.n;

         while(pos-- != 0) {
            K k = (K)key[pos];
            if (k != null) {
               consumer.accept(k);
            }
         }

      }

      public int size() {
         return Object2IntOpenHashMap.this.size;
      }

      public boolean contains(Object k) {
         return Object2IntOpenHashMap.this.containsKey(k);
      }

      public boolean remove(Object k) {
         int oldSize = Object2IntOpenHashMap.this.size;
         Object2IntOpenHashMap.this.removeInt(k);
         return Object2IntOpenHashMap.this.size != oldSize;
      }

      public void clear() {
         Object2IntOpenHashMap.this.clear();
      }
   }

   private final class ValueIterator extends MapIterator implements IntIterator {
      public ValueIterator() {
      }

      final void acceptOnIndex(IntConsumer action, int index) {
         action.accept(Object2IntOpenHashMap.this.value[index]);
      }

      public int nextInt() {
         return Object2IntOpenHashMap.this.value[this.nextEntry()];
      }
   }

   private final class ValueSpliterator extends MapSpliterator implements IntSpliterator {
      private static final int POST_SPLIT_CHARACTERISTICS = 256;

      ValueSpliterator() {
      }

      ValueSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
         super(pos, max, mustReturnNull, hasSplit);
      }

      public int characteristics() {
         return this.hasSplit ? 256 : 320;
      }

      final void acceptOnIndex(IntConsumer action, int index) {
         action.accept(Object2IntOpenHashMap.this.value[index]);
      }

      final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
         return Object2IntOpenHashMap.this.new ValueSpliterator(pos, max, mustReturnNull, true);
      }
   }
}
