package jdk.nashorn.internal.runtime.options;

public class Option {
   protected Object value;

   Option(Object value) {
      this.value = value;
   }

   public Object getValue() {
      return this.value;
   }

   public String toString() {
      return this.getValue() + " [" + this.getValue().getClass() + "]";
   }
}
