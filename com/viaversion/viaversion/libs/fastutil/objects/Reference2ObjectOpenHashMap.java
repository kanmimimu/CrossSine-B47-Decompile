package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
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

public class Reference2ObjectOpenHashMap extends AbstractReference2ObjectMap implements Serializable, Cloneable, Hash {
   private static final long serialVersionUID = 0L;
   private static final boolean ASSERTS = false;
   protected transient Object[] key;
   protected transient Object[] value;
   protected transient int mask;
   protected transient boolean containsNullKey;
   protected transient int n;
   protected transient int maxFill;
   protected final transient int minN;
   protected int size;
   protected final float f;
   protected transient Reference2ObjectMap.FastEntrySet entries;
   protected transient ReferenceSet keys;
   protected transient ObjectCollection values;

   public Reference2ObjectOpenHashMap(int expected, float f) {
      if (!(f <= 0.0F) && !(f >= 1.0F)) {
         if (expected < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
         } else {
            this.f = f;
            this.minN = this.n = HashCommon.arraySize(expected, f);
            this.mask = this.n - 1;
            this.maxFill = HashCommon.maxFill(this.n, f);
            this.key = new Object[this.n + 1];
            this.value = new Object[this.n + 1];
         }
      } else {
         throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1");
      }
   }

   public Reference2ObjectOpenHashMap(int expected) {
      this(expected, 0.75F);
   }

   public Reference2ObjectOpenHashMap() {
      this(16, 0.75F);
   }

   public Reference2ObjectOpenHashMap(Map m, float f) {
      this(m.size(), f);
      this.putAll(m);
   }

   public Reference2ObjectOpenHashMap(Map m) {
      this(m, 0.75F);
   }

   public Reference2ObjectOpenHashMap(Reference2ObjectMap m, float f) {
      this(m.size(), f);
      this.putAll(m);
   }

   public Reference2ObjectOpenHashMap(Reference2ObjectMap m) {
      this(m, 0.75F);
   }

   public Reference2ObjectOpenHashMap(Object[] k, Object[] v, float f) {
      this(k.length, f);
      if (k.length != v.length) {
         throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
      } else {
         for(int i = 0; i < k.length; ++i) {
            this.put(k[i], v[i]);
         }

      }
   }

   public Reference2ObjectOpenHashMap(Object[] k, Object[] v) {
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

   private Object removeEntry(int pos) {
      V oldValue = (V)this.value[pos];
      this.value[pos] = null;
      --this.size;
      this.shiftKeys(pos);
      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
         this.rehash(this.n / 2);
      }

      return oldValue;
   }

   private Object removeNullEntry() {
      this.containsNullKey = false;
      this.key[this.n] = null;
      V oldValue = (V)this.value[this.n];
      this.value[this.n] = null;
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
         if ((curr = (K)key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) {
            return -(pos + 1);
         } else if (k == curr) {
            return pos;
         } else {
            while((curr = (K)key[pos = pos + 1 & this.mask]) != null) {
               if (k == curr) {
                  return pos;
               }
            }

            return -(pos + 1);
         }
      }
   }

   private void insert(int pos, Object k, Object v) {
      if (pos == this.n) {
         this.containsNullKey = true;
      }

      this.key[pos] = k;
      this.value[pos] = v;
      if (this.size++ >= this.maxFill) {
         this.rehash(HashCommon.arraySize(this.size + 1, this.f));
      }

   }

   public Object put(Object k, Object v) {
      int pos = this.find(k);
      if (pos < 0) {
         this.insert(-pos - 1, k, v);
         return this.defRetValue;
      } else {
         V oldValue = (V)this.value[pos];
         this.value[pos] = v;
         return oldValue;
      }
   }

   protected final void shiftKeys(int pos) {
      K[] key = (K[])this.key;
      V[] value = (V[])this.value;

      while(true) {
         int last = pos;
         pos = pos + 1 & this.mask;

         K curr;
         while(true) {
            if ((curr = (K)key[pos]) == null) {
               key[last] = null;
               value[last] = null;
               return;
            }

            int slot = HashCommon.mix(System.identityHashCode(curr)) & this.mask;
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

   public Object remove(Object k) {
      if (k == null) {
         return this.containsNullKey ? this.removeNullEntry() : this.defRetValue;
      } else {
         K[] key = (K[])this.key;
         K curr;
         int pos;
         if ((curr = (K)key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) {
            return this.defRetValue;
         } else if (k == curr) {
            return this.removeEntry(pos);
         } else {
            while((curr = (K)key[pos = pos + 1 & this.mask]) != null) {
               if (k == curr) {
                  return this.removeEntry(pos);
               }
            }

            return this.defRetValue;
         }
      }
   }

   public Object get(Object k) {
      if (k == null) {
         return this.containsNullKey ? this.value[this.n] : this.defRetValue;
      } else {
         K[] key = (K[])this.key;
         K curr;
         int pos;
         if ((curr = (K)key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) {
            return this.defRetValue;
         } else if (k == curr) {
            return this.value[pos];
         } else {
            while((curr = (K)key[pos = pos + 1 & this.mask]) != null) {
               if (k == curr) {
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
         if ((curr = (K)key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) {
            return false;
         } else if (k == curr) {
            return true;
         } else {
            while((curr = (K)key[pos = pos + 1 & this.mask]) != null) {
               if (k == curr) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public boolean containsValue(Object v) {
      K[] key = (K[])this.key;
      V[] value = (V[])this.value;
      if (this.containsNullKey && Objects.equals(value[this.n], v)) {
         return true;
      } else {
         int i = this.n;

         while(i-- != 0) {
            if (key[i] != null && Objects.equals(value[i], v)) {
               return true;
            }
         }

         return false;
      }
   }

   public Object getOrDefault(Object k, Object defaultValue) {
      if (k == null) {
         return this.containsNullKey ? this.value[this.n] : defaultValue;
      } else {
         K[] key = (K[])this.key;
         K curr;
         int pos;
         if ((curr = (K)key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) {
            return defaultValue;
         } else if (k == curr) {
            return this.value[pos];
         } else {
            while((curr = (K)key[pos = pos + 1 & this.mask]) != null) {
               if (k == curr) {
                  return this.value[pos];
               }
            }

            return defaultValue;
         }
      }
   }

   public Object putIfAbsent(Object k, Object v) {
      int pos = this.find(k);
      if (pos >= 0) {
         return this.value[pos];
      } else {
         this.insert(-pos - 1, k, v);
         return this.defRetValue;
      }
   }

   public boolean remove(Object k, Object v) {
      if (k == null) {
         if (this.containsNullKey && Objects.equals(v, this.value[this.n])) {
            this.removeNullEntry();
            return true;
         } else {
            return false;
         }
      } else {
         K[] key = (K[])this.key;
         K curr;
         int pos;
         if ((curr = (K)key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) {
            return false;
         } else if (k == curr && Objects.equals(v, this.value[pos])) {
            this.removeEntry(pos);
            return true;
         } else {
            while((curr = (K)key[pos = pos + 1 & this.mask]) != null) {
               if (k == curr && Objects.equals(v, this.value[pos])) {
                  this.removeEntry(pos);
                  return true;
               }
            }

            return false;
         }
      }
   }

   public boolean replace(Object k, Object oldValue, Object v) {
      int pos = this.find(k);
      if (pos >= 0 && Objects.equals(oldValue, this.value[pos])) {
         this.value[pos] = v;
         return true;
      } else {
         return false;
      }
   }

   public Object replace(Object k, Object v) {
      int pos = this.find(k);
      if (pos < 0) {
         return this.defRetValue;
      } else {
         V oldValue = (V)this.value[pos];
         this.value[pos] = v;
         return oldValue;
      }
   }

   public Object computeIfAbsent(Object key, Reference2ObjectFunction mappingFunction) {
      Objects.requireNonNull(mappingFunction);
      int pos = this.find(key);
      if (pos >= 0) {
         return this.value[pos];
      } else if (!mappingFunction.containsKey(key)) {
         return this.defRetValue;
      } else {
         V newValue = (V)mappingFunction.get(key);
         this.insert(-pos - 1, key, newValue);
         return newValue;
      }
   }

   public Object computeIfPresent(Object k, BiFunction remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      int pos = this.find(k);
      if (pos < 0) {
         return this.defRetValue;
      } else if (this.value[pos] == null) {
         return this.defRetValue;
      } else {
         V newValue = (V)remappingFunction.apply(k, this.value[pos]);
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

   public Object compute(Object k, BiFunction remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      int pos = this.find(k);
      V newValue = (V)remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
      if (newValue == null) {
         if (pos >= 0) {
            if (k == null) {
               this.removeNullEntry();
            } else {
               this.removeEntry(pos);
            }
         }

         return this.defRetValue;
      } else if (pos < 0) {
         this.insert(-pos - 1, k, newValue);
         return newValue;
      } else {
         return this.value[pos] = newValue;
      }
   }

   public Object merge(Object k, Object v, BiFunction remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      Objects.requireNonNull(v);
      int pos = this.find(k);
      if (pos >= 0 && this.value[pos] != null) {
         V newValue = (V)remappingFunction.apply(this.value[pos], v);
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
      } else {
         if (pos < 0) {
            this.insert(-pos - 1, k, v);
         } else {
            this.value[pos] = v;
         }

         return v;
      }
   }

   public void clear() {
      if (this.size != 0) {
         this.size = 0;
         this.containsNullKey = false;
         Arrays.fill(this.key, (Object)null);
         Arrays.fill(this.value, (Object)null);
      }
   }

   public int size() {
      return this.size;
   }

   public boolean isEmpty() {
      return this.size == 0;
   }

   public Reference2ObjectMap.FastEntrySet reference2ObjectEntrySet() {
      if (this.entries == null) {
         this.entries = new MapEntrySet();
      }

      return this.entries;
   }

   public ReferenceSet keySet() {
      if (this.keys == null) {
         this.keys = new KeySet();
      }

      return this.keys;
   }

   public ObjectCollection values() {
      if (this.values == null) {
         this.values = new AbstractObjectCollection() {
            public ObjectIterator iterator() {
               return Reference2ObjectOpenHashMap.this.new ValueIterator();
            }

            public ObjectSpliterator spliterator() {
               return Reference2ObjectOpenHashMap.this.new ValueSpliterator();
            }

            public void forEach(Consumer consumer) {
               K[] key = (K[])Reference2ObjectOpenHashMap.this.key;
               V[] value = (V[])Reference2ObjectOpenHashMap.this.value;
               if (Reference2ObjectOpenHashMap.this.containsNullKey) {
                  consumer.accept(value[Reference2ObjectOpenHashMap.this.n]);
               }

               int pos = Reference2ObjectOpenHashMap.this.n;

               while(pos-- != 0) {
                  if (key[pos] != null) {
                     consumer.accept(value[pos]);
                  }
               }

            }

            public int size() {
               return Reference2ObjectOpenHashMap.this.size;
            }

            public boolean contains(Object v) {
               return Reference2ObjectOpenHashMap.this.containsValue(v);
            }

            public void clear() {
               Reference2ObjectOpenHashMap.this.clear();
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
      V[] value = (V[])this.value;
      int mask = newN - 1;
      K[] newKey = (K[])(new Object[newN + 1]);
      V[] newValue = (V[])(new Object[newN + 1]);
      int i = this.n;

      int pos;
      for(int j = this.realSize(); j-- != 0; newValue[pos] = value[i]) {
         --i;
         if (key[i] != null) {
            if (newKey[pos = HashCommon.mix(System.identityHashCode(key[i])) & mask] != null) {
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

   public Reference2ObjectOpenHashMap clone() {
      Reference2ObjectOpenHashMap<K, V> c;
      try {
         c = (Reference2ObjectOpenHashMap)super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }

      c.keys = null;
      c.values = null;
      c.entries = null;
      c.containsNullKey = this.containsNullKey;
      c.key = this.key.clone();
      c.value = this.value.clone();
      return c;
   }

   public int hashCode() {
      int h = 0;
      K[] key = (K[])this.key;
      V[] value = (V[])this.value;
      int j = this.realSize();
      int i = 0;

      for(int t = 0; j-- != 0; ++i) {
         while(key[i] == null) {
            ++i;
         }

         if (this != key[i]) {
            t = System.identityHashCode(key[i]);
         }

         if (this != value[i]) {
            t ^= value[i] == null ? 0 : value[i].hashCode();
         }

         h += t;
      }

      if (this.containsNullKey) {
         h += value[this.n] == null ? 0 : value[this.n].hashCode();
      }

      return h;
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      K[] key = (K[])this.key;
      V[] value = (V[])this.value;
      Reference2ObjectOpenHashMap<K, V>.EntryIterator i = new EntryIterator();
      s.defaultWriteObject();
      int j = this.size;

      while(j-- != 0) {
         int e = i.nextEntry();
         s.writeObject(key[e]);
         s.writeObject(value[e]);
      }

   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      this.n = HashCommon.arraySize(this.size, this.f);
      this.maxFill = HashCommon.maxFill(this.n, this.f);
      this.mask = this.n - 1;
      K[] key = (K[])(this.key = new Object[this.n + 1]);
      V[] value = (V[])(this.value = new Object[this.n + 1]);

      V v;
      int pos;
      for(int i = this.size; i-- != 0; value[pos] = v) {
         K k = (K)s.readObject();
         v = (V)s.readObject();
         if (k == null) {
            pos = this.n;
            this.containsNullKey = true;
         } else {
            for(pos = HashCommon.mix(System.identityHashCode(k)) & this.mask; key[pos] != null; pos = pos + 1 & this.mask) {
            }
         }

         key[pos] = k;
      }

   }

   private void checkTable() {
   }

   final class MapEntry implements Reference2ObjectMap.Entry, Map.Entry, ReferenceObjectPair {
      int index;

      MapEntry(final int index) {
         this.index = index;
      }

      MapEntry() {
      }

      public Object getKey() {
         return Reference2ObjectOpenHashMap.this.key[this.index];
      }

      public Object left() {
         return Reference2ObjectOpenHashMap.this.key[this.index];
      }

      public Object getValue() {
         return Reference2ObjectOpenHashMap.this.value[this.index];
      }

      public Object right() {
         return Reference2ObjectOpenHashMap.this.value[this.index];
      }

      public Object setValue(Object v) {
         V oldValue = (V)Reference2ObjectOpenHashMap.this.value[this.index];
         Reference2ObjectOpenHashMap.this.value[this.index] = v;
         return oldValue;
      }

      public ReferenceObjectPair right(Object v) {
         Reference2ObjectOpenHashMap.this.value[this.index] = v;
         return this;
      }

      public boolean equals(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry<K, V> e = (Map.Entry)o;
            return Reference2ObjectOpenHashMap.this.key[this.index] == e.getKey() && Objects.equals(Reference2ObjectOpenHashMap.this.value[this.index], e.getValue());
         }
      }

      public int hashCode() {
         return System.identityHashCode(Reference2ObjectOpenHashMap.this.key[this.index]) ^ (Reference2ObjectOpenHashMap.this.value[this.index] == null ? 0 : Reference2ObjectOpenHashMap.this.value[this.index].hashCode());
      }

      public String toString() {
         return Reference2ObjectOpenHashMap.this.key[this.index] + "=>" + Reference2ObjectOpenHashMap.this.value[this.index];
      }
   }

   private abstract class MapIterator {
      int pos;
      int last;
      int c;
      boolean mustReturnNullKey;
      ReferenceArrayList wrapped;

      private MapIterator() {
         this.pos = Reference2ObjectOpenHashMap.this.n;
         this.last = -1;
         this.c = Reference2ObjectOpenHashMap.this.size;
         this.mustReturnNullKey = Reference2ObjectOpenHashMap.this.containsNullKey;
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
               return this.last = Reference2ObjectOpenHashMap.this.n;
            } else {
               K[] key = (K[])Reference2ObjectOpenHashMap.this.key;

               while(--this.pos >= 0) {
                  if (key[this.pos] != null) {
                     return this.last = this.pos;
                  }
               }

               this.last = Integer.MIN_VALUE;
               K k = (K)this.wrapped.get(-this.pos - 1);

               int p;
               for(p = HashCommon.mix(System.identityHashCode(k)) & Reference2ObjectOpenHashMap.this.mask; k != key[p]; p = p + 1 & Reference2ObjectOpenHashMap.this.mask) {
               }

               return p;
            }
         }
      }

      public void forEachRemaining(Object action) {
         if (this.mustReturnNullKey) {
            this.mustReturnNullKey = false;
            this.acceptOnIndex(action, this.last = Reference2ObjectOpenHashMap.this.n);
            --this.c;
         }

         K[] key = (K[])Reference2ObjectOpenHashMap.this.key;

         while(this.c != 0) {
            if (--this.pos < 0) {
               this.last = Integer.MIN_VALUE;
               K k = (K)this.wrapped.get(-this.pos - 1);

               int p;
               for(p = HashCommon.mix(System.identityHashCode(k)) & Reference2ObjectOpenHashMap.this.mask; k != key[p]; p = p + 1 & Reference2ObjectOpenHashMap.this.mask) {
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
         K[] key = (K[])Reference2ObjectOpenHashMap.this.key;
         V[] value = (V[])Reference2ObjectOpenHashMap.this.value;

         while(true) {
            int last = pos;
            pos = pos + 1 & Reference2ObjectOpenHashMap.this.mask;

            K curr;
            while(true) {
               if ((curr = (K)key[pos]) == null) {
                  key[last] = null;
                  value[last] = null;
                  return;
               }

               int slot = HashCommon.mix(System.identityHashCode(curr)) & Reference2ObjectOpenHashMap.this.mask;
               if (last <= pos) {
                  if (last >= slot || slot > pos) {
                     break;
                  }
               } else if (last >= slot && slot > pos) {
                  break;
               }

               pos = pos + 1 & Reference2ObjectOpenHashMap.this.mask;
            }

            if (pos < last) {
               if (this.wrapped == null) {
                  this.wrapped = new ReferenceArrayList(2);
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
            if (this.last == Reference2ObjectOpenHashMap.this.n) {
               Reference2ObjectOpenHashMap.this.containsNullKey = false;
               Reference2ObjectOpenHashMap.this.key[Reference2ObjectOpenHashMap.this.n] = null;
               Reference2ObjectOpenHashMap.this.value[Reference2ObjectOpenHashMap.this.n] = null;
            } else {
               if (this.pos < 0) {
                  Reference2ObjectOpenHashMap.this.remove(this.wrapped.set(-this.pos - 1, (Object)null));
                  this.last = -1;
                  return;
               }

               this.shiftKeys(this.last);
            }

            --Reference2ObjectOpenHashMap.this.size;
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
         return this.entry = Reference2ObjectOpenHashMap.this.new MapEntry(this.nextEntry());
      }

      final void acceptOnIndex(Consumer action, int index) {
         action.accept(this.entry = Reference2ObjectOpenHashMap.this.new MapEntry(index));
      }

      public void remove() {
         super.remove();
         this.entry.index = -1;
      }
   }

   private final class FastEntryIterator extends MapIterator implements ObjectIterator {
      private final MapEntry entry;

      private FastEntryIterator() {
         this.entry = Reference2ObjectOpenHashMap.this.new MapEntry();
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
         this.max = Reference2ObjectOpenHashMap.this.n;
         this.c = 0;
         this.mustReturnNull = Reference2ObjectOpenHashMap.this.containsNullKey;
         this.hasSplit = false;
      }

      MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
         this.max = Reference2ObjectOpenHashMap.this.n;
         this.c = 0;
         this.mustReturnNull = Reference2ObjectOpenHashMap.this.containsNullKey;
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
            this.acceptOnIndex(action, Reference2ObjectOpenHashMap.this.n);
            return true;
         } else {
            for(K[] key = (K[])Reference2ObjectOpenHashMap.this.key; this.pos < this.max; ++this.pos) {
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
            this.acceptOnIndex(action, Reference2ObjectOpenHashMap.this.n);
         }

         for(K[] key = (K[])Reference2ObjectOpenHashMap.this.key; this.pos < this.max; ++this.pos) {
            if (key[this.pos] != null) {
               this.acceptOnIndex(action, this.pos);
               ++this.c;
            }
         }

      }

      public long estimateSize() {
         return !this.hasSplit ? (long)(Reference2ObjectOpenHashMap.this.size - this.c) : Math.min((long)(Reference2ObjectOpenHashMap.this.size - this.c), (long)((double)Reference2ObjectOpenHashMap.this.realSize() / (double)Reference2ObjectOpenHashMap.this.n * (double)(this.max - this.pos)) + (long)(this.mustReturnNull ? 1 : 0));
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

            K[] key = (K[])Reference2ObjectOpenHashMap.this.key;

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
         action.accept(Reference2ObjectOpenHashMap.this.new MapEntry(index));
      }

      final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
         return Reference2ObjectOpenHashMap.this.new EntrySpliterator(pos, max, mustReturnNull, true);
      }
   }

   private final class MapEntrySet extends AbstractObjectSet implements Reference2ObjectMap.FastEntrySet {
      private MapEntrySet() {
      }

      public ObjectIterator iterator() {
         return Reference2ObjectOpenHashMap.this.new EntryIterator();
      }

      public ObjectIterator fastIterator() {
         return Reference2ObjectOpenHashMap.this.new FastEntryIterator();
      }

      public ObjectSpliterator spliterator() {
         return Reference2ObjectOpenHashMap.this.new EntrySpliterator();
      }

      public boolean contains(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry<?, ?> e = (Map.Entry)o;
            K k = (K)e.getKey();
            V v = (V)e.getValue();
            if (k == null) {
               return Reference2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Reference2ObjectOpenHashMap.this.value[Reference2ObjectOpenHashMap.this.n], v);
            } else {
               K[] key = (K[])Reference2ObjectOpenHashMap.this.key;
               K curr;
               int pos;
               if ((curr = (K)key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2ObjectOpenHashMap.this.mask]) == null) {
                  return false;
               } else if (k == curr) {
                  return Objects.equals(Reference2ObjectOpenHashMap.this.value[pos], v);
               } else {
                  while((curr = (K)key[pos = pos + 1 & Reference2ObjectOpenHashMap.this.mask]) != null) {
                     if (k == curr) {
                        return Objects.equals(Reference2ObjectOpenHashMap.this.value[pos], v);
                     }
                  }

                  return false;
               }
            }
         }
      }

      public boolean remove(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry<?, ?> e = (Map.Entry)o;
            K k = (K)e.getKey();
            V v = (V)e.getValue();
            if (k == null) {
               if (Reference2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Reference2ObjectOpenHashMap.this.value[Reference2ObjectOpenHashMap.this.n], v)) {
                  Reference2ObjectOpenHashMap.this.removeNullEntry();
                  return true;
               } else {
                  return false;
               }
            } else {
               K[] key = (K[])Reference2ObjectOpenHashMap.this.key;
               K curr;
               int pos;
               if ((curr = (K)key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2ObjectOpenHashMap.this.mask]) == null) {
                  return false;
               } else if (curr == k) {
                  if (Objects.equals(Reference2ObjectOpenHashMap.this.value[pos], v)) {
                     Reference2ObjectOpenHashMap.this.removeEntry(pos);
                     return true;
                  } else {
                     return false;
                  }
               } else {
                  while((curr = (K)key[pos = pos + 1 & Reference2ObjectOpenHashMap.this.mask]) != null) {
                     if (curr == k && Objects.equals(Reference2ObjectOpenHashMap.this.value[pos], v)) {
                        Reference2ObjectOpenHashMap.this.removeEntry(pos);
                        return true;
                     }
                  }

                  return false;
               }
            }
         }
      }

      public int size() {
         return Reference2ObjectOpenHashMap.this.size;
      }

      public void clear() {
         Reference2ObjectOpenHashMap.this.clear();
      }

      public void forEach(Consumer consumer) {
         if (Reference2ObjectOpenHashMap.this.containsNullKey) {
            consumer.accept(Reference2ObjectOpenHashMap.this.new MapEntry(Reference2ObjectOpenHashMap.this.n));
         }

         K[] key = (K[])Reference2ObjectOpenHashMap.this.key;
         int pos = Reference2ObjectOpenHashMap.this.n;

         while(pos-- != 0) {
            if (key[pos] != null) {
               consumer.accept(Reference2ObjectOpenHashMap.this.new MapEntry(pos));
            }
         }

      }

      public void fastForEach(Consumer consumer) {
         Reference2ObjectOpenHashMap<K, V>.MapEntry entry = Reference2ObjectOpenHashMap.this.new MapEntry();
         if (Reference2ObjectOpenHashMap.this.containsNullKey) {
            entry.index = Reference2ObjectOpenHashMap.this.n;
            consumer.accept(entry);
         }

         K[] key = (K[])Reference2ObjectOpenHashMap.this.key;
         int pos = Reference2ObjectOpenHashMap.this.n;

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
         action.accept(Reference2ObjectOpenHashMap.this.key[index]);
      }

      public Object next() {
         return Reference2ObjectOpenHashMap.this.key[this.nextEntry()];
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
         action.accept(Reference2ObjectOpenHashMap.this.key[index]);
      }

      final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
         return Reference2ObjectOpenHashMap.this.new KeySpliterator(pos, max, mustReturnNull, true);
      }
   }

   private final class KeySet extends AbstractReferenceSet {
      private KeySet() {
      }

      public ObjectIterator iterator() {
         return Reference2ObjectOpenHashMap.this.new KeyIterator();
      }

      public ObjectSpliterator spliterator() {
         return Reference2ObjectOpenHashMap.this.new KeySpliterator();
      }

      public void forEach(Consumer consumer) {
         K[] key = (K[])Reference2ObjectOpenHashMap.this.key;
         if (Reference2ObjectOpenHashMap.this.containsNullKey) {
            consumer.accept(key[Reference2ObjectOpenHashMap.this.n]);
         }

         int pos = Reference2ObjectOpenHashMap.this.n;

         while(pos-- != 0) {
            K k = (K)key[pos];
            if (k != null) {
               consumer.accept(k);
            }
         }

      }

      public int size() {
         return Reference2ObjectOpenHashMap.this.size;
      }

      public boolean contains(Object k) {
         return Reference2ObjectOpenHashMap.this.containsKey(k);
      }

      public boolean remove(Object k) {
         int oldSize = Reference2ObjectOpenHashMap.this.size;
         Reference2ObjectOpenHashMap.this.remove(k);
         return Reference2ObjectOpenHashMap.this.size != oldSize;
      }

      public void clear() {
         Reference2ObjectOpenHashMap.this.clear();
      }
   }

   private final class ValueIterator extends MapIterator implements ObjectIterator {
      public ValueIterator() {
      }

      final void acceptOnIndex(Consumer action, int index) {
         action.accept(Reference2ObjectOpenHashMap.this.value[index]);
      }

      public Object next() {
         return Reference2ObjectOpenHashMap.this.value[this.nextEntry()];
      }
   }

   private final class ValueSpliterator extends MapSpliterator implements ObjectSpliterator {
      private static final int POST_SPLIT_CHARACTERISTICS = 0;

      ValueSpliterator() {
      }

      ValueSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
         super(pos, max, mustReturnNull, hasSplit);
      }

      public int characteristics() {
         return this.hasSplit ? 0 : 64;
      }

      final void acceptOnIndex(Consumer action, int index) {
         action.accept(Reference2ObjectOpenHashMap.this.value[index]);
      }

      final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
         return Reference2ObjectOpenHashMap.this.new ValueSpliterator(pos, max, mustReturnNull, true);
      }
   }
}
