package com.viaversion.viaversion.util;

import com.google.common.collect.ForwardingSet;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
import org.checkerframework.checker.nullness.qual.NonNull;

public class SetWrapper extends ForwardingSet {
   private final Set set;
   private final Consumer addListener;

   public SetWrapper(Set set, Consumer addListener) {
      this.set = set;
      this.addListener = addListener;
   }

   public boolean add(@NonNull Object element) {
      this.addListener.accept(element);
      return super.add(element);
   }

   public boolean addAll(Collection collection) {
      for(Object element : collection) {
         this.addListener.accept(element);
      }

      return super.addAll(collection);
   }

   protected Set delegate() {
      return this.originalSet();
   }

   public Set originalSet() {
      return this.set;
   }
}
