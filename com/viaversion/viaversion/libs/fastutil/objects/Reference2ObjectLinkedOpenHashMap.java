package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import com.viaversion.viaversion.libs.fastutil.Size64;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class Reference2ObjectLinkedOpenHashMap extends AbstractReference2ObjectSortedMap implements Serializable, Cloneable, Hash {
   private static final long serialVersionUID = 0L;
   private static final boolean ASSERTS = false;
   protected transient Object[] key;
   protected transient Object[] value;
   protected transient int mask;
   protected transient boolean containsNullKey;
   protected transient int first;
   protected transient int last;
   protected transient long[] link;
   protected transient int n;
   protected transient int maxFill;
   protected final transient int minN;
   protected int size;
   protected final float f;
   protected transient Reference2ObjectSortedMap.FastSortedEntrySet entries;
   protected transient ReferenceSortedSet keys;
   protected transient ObjectCollection values;

   public Reference2ObjectLinkedOpenHashMap(int expected, float f) {
      this.first = -1;
      this.last = -1;
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
            this.link = new long[this.n + 1];
         }
      } else {
         throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1");
      }
   }

   public Reference2ObjectLinkedOpenHashMap(int expected) {
      this(expected, 0.75F);
   }

   public Reference2ObjectLinkedOpenHashMap() {
      this(16, 0.75F);
   }

   public Reference2ObjectLinkedOpenHashMap(Map m, float f) {
      this(m.size(), f);
      this.putAll(m);
   }

   public Reference2ObjectLinkedOpenHashMap(Map m) {
      this(m, 0.75F);
   }

   public Reference2ObjectLinkedOpenHashMap(Reference2ObjectMap m, float f) {
      this(m.size(), f);
      this.putAll(m);
   }

   public Reference2ObjectLinkedOpenHashMap(Reference2ObjectMap m) {
      this(m, 0.75F);
   }

   public Reference2ObjectLinkedOpenHashMap(Object[] k, Object[] v, float f) {
      this(k.length, f);
      if (k.length != v.length) {
         throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
      } else {
         for(int i = 0; i < k.length; ++i) {
            this.put(k[i], v[i]);
         }

      }
   }

   public Reference2ObjectLinkedOpenHashMap(Object[] k, Object[] v) {
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
      this.fixPointers(pos);
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
      this.fixPointers(this.n);
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
      if (this.size == 0) {
         this.first = this.last = pos;
         this.link[pos] = -1L;
      } else {
         long[] var10000 = this.link;
         int var10001 = this.last;
         var10000[var10001] ^= (this.link[this.last] ^ (long)pos & 4294967295L) & 4294967295L;
         this.link[pos] = ((long)this.last & 4294967295L) << 32 | 4294967295L;
         this.last = pos;
      }

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
         this.fixPointers(pos, last);
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

   private Object setValue(int pos, Object v) {
      V oldValue = (V)this.value[pos];
      this.value[pos] = v;
      return oldValue;
   }

   public Object removeFirst() {
      if (this.size == 0) {
         throw new NoSuchElementException();
      } else {
         int pos = this.first;
         if (this.size == 1) {
            this.first = this.last = -1;
         } else {
            this.first = (int)this.link[pos];
            if (0 <= this.first) {
               long[] var10000 = this.link;
               int var10001 = this.first;
               var10000[var10001] |= -4294967296L;
            }
         }

         --this.size;
         V v = (V)this.value[pos];
         if (pos == this.n) {
            this.containsNullKey = false;
            this.key[this.n] = null;
            this.value[this.n] = null;
         } else {
            this.shiftKeys(pos);
         }

         if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
         }

         return v;
      }
   }

   public Object removeLast() {
      if (this.size == 0) {
         throw new NoSuchElementException();
      } else {
         int pos = this.last;
         if (this.size == 1) {
            this.first = this.last = -1;
         } else {
            this.last = (int)(this.link[pos] >>> 32);
            if (0 <= this.last) {
               long[] var10000 = this.link;
               int var10001 = this.last;
               var10000[var10001] |= 4294967295L;
            }
         }

         --this.size;
         V v = (V)this.value[pos];
         if (pos == this.n) {
            this.containsNullKey = false;
            this.key[this.n] = null;
            this.value[this.n] = null;
         } else {
            this.shiftKeys(pos);
         }

         if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
         }

         return v;
      }
   }

   private void moveIndexToFirst(int i) {
      if (this.size != 1 && this.first != i) {
         if (this.last == i) {
            this.last = (int)(this.link[i] >>> 32);
            long[] var10000 = this.link;
            int var10001 = this.last;
            var10000[var10001] |= 4294967295L;
         } else {
            long linki = this.link[i];
            int prev = (int)(linki >>> 32);
            int next = (int)linki;
            long[] var6 = this.link;
            var6[prev] ^= (this.link[prev] ^ linki & 4294967295L) & 4294967295L;
            var6 = this.link;
            var6[next] ^= (this.link[next] ^ linki & -4294967296L) & -4294967296L;
         }

         long[] var8 = this.link;
         int var9 = this.first;
         var8[var9] ^= (this.link[this.first] ^ ((long)i & 4294967295L) << 32) & -4294967296L;
         this.link[i] = -4294967296L | (long)this.first & 4294967295L;
         this.first = i;
      }
   }

   private void moveIndexToLast(int i) {
      if (this.size != 1 && this.last != i) {
         if (this.first == i) {
            this.first = (int)this.link[i];
            long[] var10000 = this.link;
            int var10001 = this.first;
            var10000[var10001] |= -4294967296L;
         } else {
            long linki = this.link[i];
            int prev = (int)(linki >>> 32);
            int next = (int)linki;
            long[] var6 = this.link;
            var6[prev] ^= (this.link[prev] ^ linki & 4294967295L) & 4294967295L;
            var6 = this.link;
            var6[next] ^= (this.link[next] ^ linki & -4294967296L) & -4294967296L;
         }

         long[] var8 = this.link;
         int var9 = this.last;
         var8[var9] ^= (this.link[this.last] ^ (long)i & 4294967295L) & 4294967295L;
         this.link[i] = ((long)this.last & 4294967295L) << 32 | 4294967295L;
         this.last = i;
      }
   }

   public Object getAndMoveToFirst(Object k) {
      if (k == null) {
         if (this.containsNullKey) {
            this.moveIndexToFirst(this.n);
            return this.value[this.n];
         } else {
            return this.defRetValue;
         }
      } else {
         K[] key = (K[])this.key;
         K curr;
         int pos;
         if ((curr = (K)key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) {
            return this.defRetValue;
         } else if (k == curr) {
            this.moveIndexToFirst(pos);
            return this.value[pos];
         } else {
            while((curr = (K)key[pos = pos + 1 & this.mask]) != null) {
               if (k == curr) {
                  this.moveIndexToFirst(pos);
                  return this.value[pos];
               }
            }

            return this.defRetValue;
         }
      }
   }

   public Object getAndMoveToLast(Object k) {
      if (k == null) {
         if (this.containsNullKey) {
            this.moveIndexToLast(this.n);
            return this.value[this.n];
         } else {
            return this.defRetValue;
         }
      } else {
         K[] key = (K[])this.key;
         K curr;
         int pos;
         if ((curr = (K)key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) {
            return this.defRetValue;
         } else if (k == curr) {
            this.moveIndexToLast(pos);
            return this.value[pos];
         } else {
            while((curr = (K)key[pos = pos + 1 & this.mask]) != null) {
               if (k == curr) {
                  this.moveIndexToLast(pos);
                  return this.value[pos];
               }
            }

            return this.defRetValue;
         }
      }
   }

   public Object putAndMoveToFirst(Object k, Object v) {
      int pos;
      if (k == null) {
         if (this.containsNullKey) {
            this.moveIndexToFirst(this.n);
            return this.setValue(this.n, v);
         }

         this.containsNullKey = true;
         pos = this.n;
      } else {
         K[] key = (K[])this.key;
         K curr;
         if ((curr = (K)key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
            if (curr == k) {
               this.moveIndexToFirst(pos);
               return this.setValue(pos, v);
            }

            while((curr = (K)key[pos = pos + 1 & this.mask]) != null) {
               if (curr == k) {
                  this.moveIndexToFirst(pos);
                  return this.setValue(pos, v);
               }
            }
         }
      }

      this.key[pos] = k;
      this.value[pos] = v;
      if (this.size == 0) {
         this.first = this.last = pos;
         this.link[pos] = -1L;
      } else {
         long[] var10000 = this.link;
         int var10001 = this.first;
         var10000[var10001] ^= (this.link[this.first] ^ ((long)pos & 4294967295L) << 32) & -4294967296L;
         this.link[pos] = -4294967296L | (long)this.first & 4294967295L;
         this.first = pos;
      }

      if (this.size++ >= this.maxFill) {
         this.rehash(HashCommon.arraySize(this.size, this.f));
      }

      return this.defRetValue;
   }

   public Object putAndMoveToLast(Object k, Object v) {
      int pos;
      if (k == null) {
         if (this.containsNullKey) {
            this.moveIndexToLast(this.n);
            return this.setValue(this.n, v);
         }

         this.containsNullKey = true;
         pos = this.n;
      } else {
         K[] key = (K[])this.key;
         K curr;
         if ((curr = (K)key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
            if (curr == k) {
               this.moveIndexToLast(pos);
               return this.setValue(pos, v);
            }

            while((curr = (K)key[pos = pos + 1 & this.mask]) != null) {
               if (curr == k) {
                  this.moveIndexToLast(pos);
                  return this.setValue(pos, v);
               }
            }
         }
      }

      this.key[pos] = k;
      this.value[pos] = v;
      if (this.size == 0) {
         this.first = this.last = pos;
         this.link[pos] = -1L;
      } else {
         long[] var10000 = this.link;
         int var10001 = this.last;
         var10000[var10001] ^= (this.link[this.last] ^ (long)pos & 4294967295L) & 4294967295L;
         this.link[pos] = ((long)this.last & 4294967295L) << 32 | 4294967295L;
         this.last = pos;
      }

      if (this.size++ >= this.maxFill) {
         this.rehash(HashCommon.arraySize(this.size, this.f));
      }

      return this.defRetValue;
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
         this.first = this.last = -1;
      }
   }

   public int size() {
      return this.size;
   }

   public boolean isEmpty() {
      return this.size == 0;
   }

   protected void fixPointers(int i) {
      if (this.size == 0) {
         this.first = this.last = -1;
      } else if (this.first == i) {
         this.first = (int)this.link[i];
         if (0 <= this.first) {
            long[] var8 = this.link;
            int var9 = this.first;
            var8[var9] |= -4294967296L;
         }

      } else if (this.last == i) {
         this.last = (int)(this.link[i] >>> 32);
         if (0 <= this.last) {
            long[] var7 = this.link;
            int var10001 = this.last;
            var7[var10001] |= 4294967295L;
         }

      } else {
         long linki = this.link[i];
         int prev = (int)(linki >>> 32);
         int next = (int)linki;
         long[] var10000 = this.link;
         var10000[prev] ^= (this.link[prev] ^ linki & 4294967295L) & 4294967295L;
         var10000 = this.link;
         var10000[next] ^= (this.link[next] ^ linki & -4294967296L) & -4294967296L;
      }
   }

   protected void fixPointers(int s, int d) {
      if (this.size == 1) {
         this.first = this.last = d;
         this.link[d] = -1L;
      } else if (this.first == s) {
         this.first = d;
         long[] var9 = this.link;
         int var10 = (int)this.link[s];
         var9[var10] ^= (this.link[(int)this.link[s]] ^ ((long)d & 4294967295L) << 32) & -4294967296L;
         this.link[d] = this.link[s];
      } else if (this.last == s) {
         this.last = d;
         long[] var8 = this.link;
         int var10001 = (int)(this.link[s] >>> 32);
         var8[var10001] ^= (this.link[(int)(this.link[s] >>> 32)] ^ (long)d & 4294967295L) & 4294967295L;
         this.link[d] = this.link[s];
      } else {
         long links = this.link[s];
         int prev = (int)(links >>> 32);
         int next = (int)links;
         long[] var10000 = this.link;
         var10000[prev] ^= (this.link[prev] ^ (long)d & 4294967295L) & 4294967295L;
         var10000 = this.link;
         var10000[next] ^= (this.link[next] ^ ((long)d & 4294967295L) << 32) & -4294967296L;
         this.link[d] = links;
      }
   }

   public Object firstKey() {
      if (this.size == 0) {
         throw new NoSuchElementException();
      } else {
         return this.key[this.first];
      }
   }

   public Object lastKey() {
      if (this.size == 0) {
         throw new NoSuchElementException();
      } else {
         return this.key[this.last];
      }
   }

   public Reference2ObjectSortedMap tailMap(Object from) {
      throw new UnsupportedOperationException();
   }

   public Reference2ObjectSortedMap headMap(Object to) {
      throw new UnsupportedOperationException();
   }

   public Reference2ObjectSortedMap subMap(Object from, Object to) {
      throw new UnsupportedOperationException();
   }

   public Comparator comparator() {
      return null;
   }

   public Reference2ObjectSortedMap.FastSortedEntrySet reference2ObjectEntrySet() {
      if (this.entries == null) {
         this.entries = new MapEntrySet();
      }

      return this.entries;
   }

   public ReferenceSortedSet keySet() {
      if (this.keys == null) {
         this.keys = new KeySet();
      }

      return this.keys;
   }

   public ObjectCollection values() {
      if (this.values == null) {
         this.values = new AbstractObjectCollection() {
            private static final int SPLITERATOR_CHARACTERISTICS = 80;

            public ObjectIterator iterator() {
               return Reference2ObjectLinkedOpenHashMap.this.new ValueIterator();
            }

            public ObjectSpliterator spliterator() {
               return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf((Map)Reference2ObjectLinkedOpenHashMap.this), 80);
            }

            public void forEach(Consumer consumer) {
               long[] link = Reference2ObjectLinkedOpenHashMap.this.link;
               V[] value = (V[])Reference2ObjectLinkedOpenHashMap.this.value;
               int i = Reference2ObjectLinkedOpenHashMap.this.size;
               int next = Reference2ObjectLinkedOpenHashMap.this.first;

               while(i-- != 0) {
                  int curr = next;
                  next = (int)link[next];
                  consumer.accept(value[curr]);
               }

            }

            public int size() {
               return Reference2ObjectLinkedOpenHashMap.this.size;
            }

            public boolean contains(Object v) {
               return Reference2ObjectLinkedOpenHashMap.this.containsValue(v);
            }

            public void clear() {
               Reference2ObjectLinkedOpenHashMap.this.clear();
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
      int i = this.first;
      int prev = -1;
      int newPrev = -1;
      long[] link = this.link;
      long[] newLink = new long[newN + 1];
      this.first = -1;

      int t;
      for(int j = this.size; j-- != 0; prev = t) {
         int pos;
         if (key[i] == null) {
            pos = newN;
         } else {
            for(pos = HashCommon.mix(System.identityHashCode(key[i])) & mask; newKey[pos] != null; pos = pos + 1 & mask) {
            }
         }

         newKey[pos] = key[i];
         newValue[pos] = value[i];
         if (prev != -1) {
            newLink[newPrev] ^= (newLink[newPrev] ^ (long)pos & 4294967295L) & 4294967295L;
            newLink[pos] ^= (newLink[pos] ^ ((long)newPrev & 4294967295L) << 32) & -4294967296L;
            newPrev = pos;
         } else {
            newPrev = this.first = pos;
            newLink[pos] = -1L;
         }

         t = i;
         i = (int)link[i];
      }

      this.link = newLink;
      this.last = newPrev;
      if (newPrev != -1) {
         newLink[newPrev] |= 4294967295L;
      }

      this.n = newN;
      this.mask = mask;
      this.maxFill = HashCommon.maxFill(this.n, this.f);
      this.key = newKey;
      this.value = newValue;
   }

   public Reference2ObjectLinkedOpenHashMap clone() {
      Reference2ObjectLinkedOpenHashMap<K, V> c;
      try {
         c = (Reference2ObjectLinkedOpenHashMap)super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }

      c.keys = null;
      c.values = null;
      c.entries = null;
      c.containsNullKey = this.containsNullKey;
      c.key = this.key.clone();
      c.value = this.value.clone();
      c.link = (long[])this.link.clone();
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
      Reference2ObjectLinkedOpenHashMap<K, V>.EntryIterator i = new EntryIterator();
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
      long[] link = this.link = new long[this.n + 1];
      int prev = -1;
      this.first = this.last = -1;
      int i = this.size;

      while(i-- != 0) {
         K k = (K)s.readObject();
         V v = (V)s.readObject();
         int pos;
         if (k == null) {
            pos = this.n;
            this.containsNullKey = true;
         } else {
            for(pos = HashCommon.mix(System.identityHashCode(k)) & this.mask; key[pos] != null; pos = pos + 1 & this.mask) {
            }
         }

         key[pos] = k;
         value[pos] = v;
         if (this.first != -1) {
            link[prev] ^= (link[prev] ^ (long)pos & 4294967295L) & 4294967295L;
            link[pos] ^= (link[pos] ^ ((long)prev & 4294967295L) << 32) & -4294967296L;
            prev = pos;
         } else {
            prev = this.first = pos;
            link[pos] |= -4294967296L;
         }
      }

      this.last = prev;
      if (prev != -1) {
         link[prev] |= 4294967295L;
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
         return Reference2ObjectLinkedOpenHashMap.this.key[this.index];
      }

      public Object left() {
         return Reference2ObjectLinkedOpenHashMap.this.key[this.index];
      }

      public Object getValue() {
         return Reference2ObjectLinkedOpenHashMap.this.value[this.index];
      }

      public Object right() {
         return Reference2ObjectLinkedOpenHashMap.this.value[this.index];
      }

      public Object setValue(Object v) {
         V oldValue = (V)Reference2ObjectLinkedOpenHashMap.this.value[this.index];
         Reference2ObjectLinkedOpenHashMap.this.value[this.index] = v;
         return oldValue;
      }

      public ReferenceObjectPair right(Object v) {
         Reference2ObjectLinkedOpenHashMap.this.value[this.index] = v;
         return this;
      }

      public boolean equals(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry<K, V> e = (Map.Entry)o;
            return Reference2ObjectLinkedOpenHashMap.this.key[this.index] == e.getKey() && Objects.equals(Reference2ObjectLinkedOpenHashMap.this.value[this.index], e.getValue());
         }
      }

      public int hashCode() {
         return System.identityHashCode(Reference2ObjectLinkedOpenHashMap.this.key[this.index]) ^ (Reference2ObjectLinkedOpenHashMap.this.value[this.index] == null ? 0 : Reference2ObjectLinkedOpenHashMap.this.value[this.index].hashCode());
      }

      public String toString() {
         return Reference2ObjectLinkedOpenHashMap.this.key[this.index] + "=>" + Reference2ObjectLinkedOpenHashMap.this.value[this.index];
      }
   }

   private abstract class MapIterator {
      int prev;
      int next;
      int curr;
      int index;

      abstract void acceptOnIndex(Object var1, int var2);

      protected MapIterator() {
         this.prev = -1;
         this.next = -1;
         this.curr = -1;
         this.index = -1;
         this.next = Reference2ObjectLinkedOpenHashMap.this.first;
         this.index = 0;
      }

      private MapIterator(final Object from) {
         this.prev = -1;
         this.next = -1;
         this.curr = -1;
         this.index = -1;
         if (from == null) {
            if (Reference2ObjectLinkedOpenHashMap.this.containsNullKey) {
               this.next = (int)Reference2ObjectLinkedOpenHashMap.this.link[Reference2ObjectLinkedOpenHashMap.this.n];
               this.prev = Reference2ObjectLinkedOpenHashMap.this.n;
            } else {
               throw new NoSuchElementException("The key " + from + " does not belong to this map.");
            }
         } else if (Reference2ObjectLinkedOpenHashMap.this.key[Reference2ObjectLinkedOpenHashMap.this.last] == from) {
            this.prev = Reference2ObjectLinkedOpenHashMap.this.last;
            this.index = Reference2ObjectLinkedOpenHashMap.this.size;
         } else {
            for(int pos = HashCommon.mix(System.identityHashCode(from)) & Reference2ObjectLinkedOpenHashMap.this.mask; Reference2ObjectLinkedOpenHashMap.this.key[pos] != null; pos = pos + 1 & Reference2ObjectLinkedOpenHashMap.this.mask) {
               if (Reference2ObjectLinkedOpenHashMap.this.key[pos] == from) {
                  this.next = (int)Reference2ObjectLinkedOpenHashMap.this.link[pos];
                  this.prev = pos;
                  return;
               }
            }

            throw new NoSuchElementException("The key " + from + " does not belong to this map.");
         }
      }

      public boolean hasNext() {
         return this.next != -1;
      }

      public boolean hasPrevious() {
         return this.prev != -1;
      }

      private final void ensureIndexKnown() {
         if (this.index < 0) {
            if (this.prev == -1) {
               this.index = 0;
            } else if (this.next == -1) {
               this.index = Reference2ObjectLinkedOpenHashMap.this.size;
            } else {
               int pos = Reference2ObjectLinkedOpenHashMap.this.first;

               for(this.index = 1; pos != this.prev; ++this.index) {
                  pos = (int)Reference2ObjectLinkedOpenHashMap.this.link[pos];
               }

            }
         }
      }

      public int nextIndex() {
         this.ensureIndexKnown();
         return this.index;
      }

      public int previousIndex() {
         this.ensureIndexKnown();
         return this.index - 1;
      }

      public int nextEntry() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            this.curr = this.next;
            this.next = (int)Reference2ObjectLinkedOpenHashMap.this.link[this.curr];
            this.prev = this.curr;
            if (this.index >= 0) {
               ++this.index;
            }

            return this.curr;
         }
      }

      public int previousEntry() {
         if (!this.hasPrevious()) {
            throw new NoSuchElementException();
         } else {
            this.curr = this.prev;
            this.prev = (int)(Reference2ObjectLinkedOpenHashMap.this.link[this.curr] >>> 32);
            this.next = this.curr;
            if (this.index >= 0) {
               --this.index;
            }

            return this.curr;
         }
      }

      public void forEachRemaining(Object action) {
         for(; this.hasNext(); this.acceptOnIndex(action, this.curr)) {
            this.curr = this.next;
            this.next = (int)Reference2ObjectLinkedOpenHashMap.this.link[this.curr];
            this.prev = this.curr;
            if (this.index >= 0) {
               ++this.index;
            }
         }

      }

      public void remove() {
         this.ensureIndexKnown();
         if (this.curr == -1) {
            throw new IllegalStateException();
         } else {
            if (this.curr == this.prev) {
               --this.index;
               this.prev = (int)(Reference2ObjectLinkedOpenHashMap.this.link[this.curr] >>> 32);
            } else {
               this.next = (int)Reference2ObjectLinkedOpenHashMap.this.link[this.curr];
            }

            --Reference2ObjectLinkedOpenHashMap.this.size;
            if (this.prev == -1) {
               Reference2ObjectLinkedOpenHashMap.this.first = this.next;
            } else {
               long[] var7 = Reference2ObjectLinkedOpenHashMap.this.link;
               int var10001 = this.prev;
               var7[var10001] ^= (Reference2ObjectLinkedOpenHashMap.this.link[this.prev] ^ (long)this.next & 4294967295L) & 4294967295L;
            }

            if (this.next == -1) {
               Reference2ObjectLinkedOpenHashMap.this.last = this.prev;
            } else {
               long[] var8 = Reference2ObjectLinkedOpenHashMap.this.link;
               int var9 = this.next;
               var8[var9] ^= (Reference2ObjectLinkedOpenHashMap.this.link[this.next] ^ ((long)this.prev & 4294967295L) << 32) & -4294967296L;
            }

            int pos = this.curr;
            this.curr = -1;
            if (pos == Reference2ObjectLinkedOpenHashMap.this.n) {
               Reference2ObjectLinkedOpenHashMap.this.containsNullKey = false;
               Reference2ObjectLinkedOpenHashMap.this.key[Reference2ObjectLinkedOpenHashMap.this.n] = null;
               Reference2ObjectLinkedOpenHashMap.this.value[Reference2ObjectLinkedOpenHashMap.this.n] = null;
            } else {
               K[] key = (K[])Reference2ObjectLinkedOpenHashMap.this.key;
               V[] value = (V[])Reference2ObjectLinkedOpenHashMap.this.value;

               while(true) {
                  int last = pos;
                  pos = pos + 1 & Reference2ObjectLinkedOpenHashMap.this.mask;

                  K curr;
                  while(true) {
                     if ((curr = (K)key[pos]) == null) {
                        key[last] = null;
                        value[last] = null;
                        return;
                     }

                     int slot = HashCommon.mix(System.identityHashCode(curr)) & Reference2ObjectLinkedOpenHashMap.this.mask;
                     if (last <= pos) {
                        if (last >= slot || slot > pos) {
                           break;
                        }
                     } else if (last >= slot && slot > pos) {
                        break;
                     }

                     pos = pos + 1 & Reference2ObjectLinkedOpenHashMap.this.mask;
                  }

                  key[last] = curr;
                  value[last] = value[pos];
                  if (this.next == pos) {
                     this.next = last;
                  }

                  if (this.prev == pos) {
                     this.prev = last;
                  }

                  Reference2ObjectLinkedOpenHashMap.this.fixPointers(pos, last);
               }
            }
         }
      }

      public int skip(int n) {
         int i = n;

         while(i-- != 0 && this.hasNext()) {
            this.nextEntry();
         }

         return n - i - 1;
      }

      public int back(int n) {
         int i = n;

         while(i-- != 0 && this.hasPrevious()) {
            this.previousEntry();
         }

         return n - i - 1;
      }

      public void set(Reference2ObjectMap.Entry ok) {
         throw new UnsupportedOperationException();
      }

      public void add(Reference2ObjectMap.Entry ok) {
         throw new UnsupportedOperationException();
      }
   }

   private final class EntryIterator extends MapIterator implements ObjectListIterator {
      private MapEntry entry;

      public EntryIterator() {
      }

      public EntryIterator(Object from) {
         super(from, null);
      }

      final void acceptOnIndex(Consumer action, int index) {
         action.accept(Reference2ObjectLinkedOpenHashMap.this.new MapEntry(index));
      }

      public MapEntry next() {
         return this.entry = Reference2ObjectLinkedOpenHashMap.this.new MapEntry(this.nextEntry());
      }

      public MapEntry previous() {
         return this.entry = Reference2ObjectLinkedOpenHashMap.this.new MapEntry(this.previousEntry());
      }

      public void remove() {
         super.remove();
         this.entry.index = -1;
      }
   }

   private final class FastEntryIterator extends MapIterator implements ObjectListIterator {
      final MapEntry entry;

      public FastEntryIterator() {
         this.entry = Reference2ObjectLinkedOpenHashMap.this.new MapEntry();
      }

      public FastEntryIterator(Object from) {
         super(from, null);
         this.entry = Reference2ObjectLinkedOpenHashMap.this.new MapEntry();
      }

      final void acceptOnIndex(Consumer action, int index) {
         this.entry.index = index;
         action.accept(this.entry);
      }

      public MapEntry next() {
         this.entry.index = this.nextEntry();
         return this.entry;
      }

      public MapEntry previous() {
         this.entry.index = this.previousEntry();
         return this.entry;
      }
   }

   private final class MapEntrySet extends AbstractObjectSortedSet implements Reference2ObjectSortedMap.FastSortedEntrySet {
      private static final int SPLITERATOR_CHARACTERISTICS = 81;

      private MapEntrySet() {
      }

      public ObjectBidirectionalIterator iterator() {
         return Reference2ObjectLinkedOpenHashMap.this.new EntryIterator();
      }

      public ObjectSpliterator spliterator() {
         return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf((Map)Reference2ObjectLinkedOpenHashMap.this), 81);
      }

      public Comparator comparator() {
         return null;
      }

      public ObjectSortedSet subSet(Reference2ObjectMap.Entry fromElement, Reference2ObjectMap.Entry toElement) {
         throw new UnsupportedOperationException();
      }

      public ObjectSortedSet headSet(Reference2ObjectMap.Entry toElement) {
         throw new UnsupportedOperationException();
      }

      public ObjectSortedSet tailSet(Reference2ObjectMap.Entry fromElement) {
         throw new UnsupportedOperationException();
      }

      public Reference2ObjectMap.Entry first() {
         if (Reference2ObjectLinkedOpenHashMap.this.size == 0) {
            throw new NoSuchElementException();
         } else {
            return Reference2ObjectLinkedOpenHashMap.this.new MapEntry(Reference2ObjectLinkedOpenHashMap.this.first);
         }
      }

      public Reference2ObjectMap.Entry last() {
         if (Reference2ObjectLinkedOpenHashMap.this.size == 0) {
            throw new NoSuchElementException();
         } else {
            return Reference2ObjectLinkedOpenHashMap.this.new MapEntry(Reference2ObjectLinkedOpenHashMap.this.last);
         }
      }

      public boolean contains(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry<?, ?> e = (Map.Entry)o;
            K k = (K)e.getKey();
            V v = (V)e.getValue();
            if (k == null) {
               return Reference2ObjectLinkedOpenHashMap.this.containsNullKey && Objects.equals(Reference2ObjectLinkedOpenHashMap.this.value[Reference2ObjectLinkedOpenHashMap.this.n], v);
            } else {
               K[] key = (K[])Reference2ObjectLinkedOpenHashMap.this.key;
               K curr;
               int pos;
               if ((curr = (K)key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2ObjectLinkedOpenHashMap.this.mask]) == null) {
                  return false;
               } else if (k == curr) {
                  return Objects.equals(Reference2ObjectLinkedOpenHashMap.this.value[pos], v);
               } else {
                  while((curr = (K)key[pos = pos + 1 & Reference2ObjectLinkedOpenHashMap.this.mask]) != null) {
                     if (k == curr) {
                        return Objects.equals(Reference2ObjectLinkedOpenHashMap.this.value[pos], v);
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
               if (Reference2ObjectLinkedOpenHashMap.this.containsNullKey && Objects.equals(Reference2ObjectLinkedOpenHashMap.this.value[Reference2ObjectLinkedOpenHashMap.this.n], v)) {
                  Reference2ObjectLinkedOpenHashMap.this.removeNullEntry();
                  return true;
               } else {
                  return false;
               }
            } else {
               K[] key = (K[])Reference2ObjectLinkedOpenHashMap.this.key;
               K curr;
               int pos;
               if ((curr = (K)key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2ObjectLinkedOpenHashMap.this.mask]) == null) {
                  return false;
               } else if (curr == k) {
                  if (Objects.equals(Reference2ObjectLinkedOpenHashMap.this.value[pos], v)) {
                     Reference2ObjectLinkedOpenHashMap.this.removeEntry(pos);
                     return true;
                  } else {
                     return false;
                  }
               } else {
                  while((curr = (K)key[pos = pos + 1 & Reference2ObjectLinkedOpenHashMap.this.mask]) != null) {
                     if (curr == k && Objects.equals(Reference2ObjectLinkedOpenHashMap.this.value[pos], v)) {
                        Reference2ObjectLinkedOpenHashMap.this.removeEntry(pos);
                        return true;
                     }
                  }

                  return false;
               }
            }
         }
      }

      public int size() {
         return Reference2ObjectLinkedOpenHashMap.this.size;
      }

      public void clear() {
         Reference2ObjectLinkedOpenHashMap.this.clear();
      }

      public ObjectListIterator iterator(Reference2ObjectMap.Entry from) {
         return Reference2ObjectLinkedOpenHashMap.this.new EntryIterator(from.getKey());
      }

      public ObjectListIterator fastIterator() {
         return Reference2ObjectLinkedOpenHashMap.this.new FastEntryIterator();
      }

      public ObjectListIterator fastIterator(Reference2ObjectMap.Entry from) {
         return Reference2ObjectLinkedOpenHashMap.this.new FastEntryIterator(from.getKey());
      }

      public void forEach(Consumer consumer) {
         long[] link = Reference2ObjectLinkedOpenHashMap.this.link;
         int i = Reference2ObjectLinkedOpenHashMap.this.size;
         int next = Reference2ObjectLinkedOpenHashMap.this.first;

         while(i-- != 0) {
            int curr = next;
            next = (int)link[next];
            consumer.accept(Reference2ObjectLinkedOpenHashMap.this.new MapEntry(curr));
         }

      }

      public void fastForEach(Consumer consumer) {
         Reference2ObjectLinkedOpenHashMap<K, V>.MapEntry entry = Reference2ObjectLinkedOpenHashMap.this.new MapEntry();
         long[] link = Reference2ObjectLinkedOpenHashMap.this.link;
         int i = Reference2ObjectLinkedOpenHashMap.this.size;
         int next = Reference2ObjectLinkedOpenHashMap.this.first;

         while(i-- != 0) {
            entry.index = next;
            next = (int)link[next];
            consumer.accept(entry);
         }

      }
   }

   private final class KeyIterator extends MapIterator implements ObjectListIterator {
      public KeyIterator(final Object k) {
         super(k, null);
      }

      public Object previous() {
         return Reference2ObjectLinkedOpenHashMap.this.key[this.previousEntry()];
      }

      public KeyIterator() {
      }

      final void acceptOnIndex(Consumer action, int index) {
         action.accept(Reference2ObjectLinkedOpenHashMap.this.key[index]);
      }

      public Object next() {
         return Reference2ObjectLinkedOpenHashMap.this.key[this.nextEntry()];
      }
   }

   private final class KeySet extends AbstractReferenceSortedSet {
      private static final int SPLITERATOR_CHARACTERISTICS = 81;

      private KeySet() {
      }

      public ObjectListIterator iterator(Object from) {
         return Reference2ObjectLinkedOpenHashMap.this.new KeyIterator(from);
      }

      public ObjectListIterator iterator() {
         return Reference2ObjectLinkedOpenHashMap.this.new KeyIterator();
      }

      public ObjectSpliterator spliterator() {
         return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf((Map)Reference2ObjectLinkedOpenHashMap.this), 81);
      }

      public void forEach(Consumer consumer) {
         long[] link = Reference2ObjectLinkedOpenHashMap.this.link;
         K[] key = (K[])Reference2ObjectLinkedOpenHashMap.this.key;
         int i = Reference2ObjectLinkedOpenHashMap.this.size;
         int next = Reference2ObjectLinkedOpenHashMap.this.first;

         while(i-- != 0) {
            int curr = next;
            next = (int)link[next];
            consumer.accept(key[curr]);
         }

      }

      public int size() {
         return Reference2ObjectLinkedOpenHashMap.this.size;
      }

      public boolean contains(Object k) {
         return Reference2ObjectLinkedOpenHashMap.this.containsKey(k);
      }

      public boolean remove(Object k) {
         int oldSize = Reference2ObjectLinkedOpenHashMap.this.size;
         Reference2ObjectLinkedOpenHashMap.this.remove(k);
         return Reference2ObjectLinkedOpenHashMap.this.size != oldSize;
      }

      public void clear() {
         Reference2ObjectLinkedOpenHashMap.this.clear();
      }

      public Object first() {
         if (Reference2ObjectLinkedOpenHashMap.this.size == 0) {
            throw new NoSuchElementException();
         } else {
            return Reference2ObjectLinkedOpenHashMap.this.key[Reference2ObjectLinkedOpenHashMap.this.first];
         }
      }

      public Object last() {
         if (Reference2ObjectLinkedOpenHashMap.this.size == 0) {
            throw new NoSuchElementException();
         } else {
            return Reference2ObjectLinkedOpenHashMap.this.key[Reference2ObjectLinkedOpenHashMap.this.last];
         }
      }

      public Comparator comparator() {
         return null;
      }

      public ReferenceSortedSet tailSet(Object from) {
         throw new UnsupportedOperationException();
      }

      public ReferenceSortedSet headSet(Object to) {
         throw new UnsupportedOperationException();
      }

      public ReferenceSortedSet subSet(Object from, Object to) {
         throw new UnsupportedOperationException();
      }
   }

   private final class ValueIterator extends MapIterator implements ObjectListIterator {
      public Object previous() {
         return Reference2ObjectLinkedOpenHashMap.this.value[this.previousEntry()];
      }

      public ValueIterator() {
      }

      final void acceptOnIndex(Consumer action, int index) {
         action.accept(Reference2ObjectLinkedOpenHashMap.this.value[index]);
      }

      public Object next() {
         return Reference2ObjectLinkedOpenHashMap.this.value[this.nextEntry()];
      }
   }
}
