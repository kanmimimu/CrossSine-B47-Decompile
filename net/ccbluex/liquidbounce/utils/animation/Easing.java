package net.ccbluex.liquidbounce.utils.animation;

import java.util.function.Function;
import org.apache.commons.lang3.StringUtils;

public enum Easing {
   LINEAR((x) -> x),
   EASE_IN_QUAD((x) -> x * x),
   EASE_OUT_QUAD((x) -> x * ((double)2.0F - x)),
   EASE_IN_OUT_QUAD((x) -> x < (double)0.5F ? (double)2.0F * x * x : (double)-1.0F + ((double)4.0F - (double)2.0F * x) * x),
   EASE_IN_CUBIC((x) -> x * x * x),
   EASE_OUT_CUBIC((x) -> {
      Double var1;
      return var1 = x - (double)1.0F * var1 * var1 + (double)1.0F;
   }),
   EASE_IN_OUT_CUBIC((x) -> x < (double)0.5F ? (double)4.0F * x * x * x : (x - (double)1.0F) * ((double)2.0F * x - (double)2.0F) * ((double)2.0F * x - (double)2.0F) + (double)1.0F),
   EASE_IN_QUART((x) -> x * x * x * x),
   EASE_OUT_QUART((x) -> {
      Double var1;
      return (double)1.0F - var1 = x - (double)1.0F * var1 * var1 * var1;
   }),
   EASE_IN_OUT_QUART((x) -> {
      Double var1;
      return x < (double)0.5F ? (double)8.0F * x * x * x * x : (double)1.0F - (double)8.0F * var1 = x - (double)1.0F * var1 * var1 * var1;
   }),
   EASE_IN_QUINT((x) -> x * x * x * x * x),
   EASE_OUT_QUINT((x) -> {
      Double var1;
      return (double)1.0F + var1 = x - (double)1.0F * var1 * var1 * var1 * var1;
   }),
   EASE_IN_OUT_QUINT((x) -> {
      Double var1;
      return x < (double)0.5F ? (double)16.0F * x * x * x * x * x : (double)1.0F + (double)16.0F * var1 = x - (double)1.0F * var1 * var1 * var1 * var1;
   }),
   EASE_IN_SINE((x) -> (double)1.0F - Math.cos(x * Math.PI / (double)2.0F)),
   EASE_OUT_SINE((x) -> Math.sin(x * Math.PI / (double)2.0F)),
   EASE_IN_OUT_SINE((x) -> (double)1.0F - Math.cos(Math.PI * x / (double)2.0F)),
   EASE_IN_EXPO((x) -> x == (double)0.0F ? (double)0.0F : Math.pow((double)2.0F, (double)10.0F * x - (double)10.0F)),
   EASE_OUT_EXPO((x) -> x == (double)1.0F ? (double)1.0F : (double)1.0F - Math.pow((double)2.0F, (double)-10.0F * x)),
   EASE_IN_OUT_EXPO((x) -> x == (double)0.0F ? (double)0.0F : (x == (double)1.0F ? (double)1.0F : (x < (double)0.5F ? Math.pow((double)2.0F, (double)20.0F * x - (double)10.0F) / (double)2.0F : ((double)2.0F - Math.pow((double)2.0F, (double)-20.0F * x + (double)10.0F)) / (double)2.0F))),
   EASE_IN_CIRC((x) -> (double)1.0F - Math.sqrt((double)1.0F - x * x)),
   EASE_OUT_CIRC((x) -> {
      Double var1;
      return Math.sqrt((double)1.0F - var1 = x - (double)1.0F * var1);
   }),
   EASE_IN_OUT_CIRC((x) -> x < (double)0.5F ? ((double)1.0F - Math.sqrt((double)1.0F - (double)4.0F * x * x)) / (double)2.0F : (Math.sqrt((double)1.0F - (double)4.0F * (x - (double)1.0F) * x) + (double)1.0F) / (double)2.0F),
   SIGMOID((x) -> (double)1.0F / ((double)1.0F + Math.exp(-x))),
   EASE_OUT_ELASTIC((x) -> x == (double)0.0F ? (double)0.0F : (x == (double)1.0F ? (double)1.0F : Math.pow((double)2.0F, (double)-10.0F * x) * Math.sin((x * (double)10.0F - (double)0.75F) * 2.0943951023931953) * (double)0.5F + (double)1.0F)),
   EASE_IN_BACK((x) -> 2.70158 * x * x * x - 1.70158 * x * x),
   DECELERATE((x) -> (double)1.0F - (x - (double)1.0F) * (x - (double)1.0F));

   private final Function function;

   private Easing(Function function) {
      this.function = function;
   }

   public Function getFunction() {
      return this.function;
   }

   public String toString() {
      return StringUtils.capitalize(super.toString().toLowerCase().replace("_", " "));
   }
}
