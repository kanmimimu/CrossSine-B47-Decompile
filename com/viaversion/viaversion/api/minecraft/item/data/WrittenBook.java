package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class WrittenBook {
   final FilterableString title;
   final String author;
   final int generation;
   final FilterableComponent[] pages;
   final boolean resolved;
   public static final Type TYPE = new Type(WrittenBook.class) {
      public WrittenBook read(ByteBuf buffer) {
         FilterableString title = (FilterableString)FilterableString.TYPE.read(buffer);
         String author = (String)Types.STRING.read(buffer);
         int generation = Types.VAR_INT.readPrimitive(buffer);
         FilterableComponent[] pages = (FilterableComponent[])FilterableComponent.ARRAY_TYPE.read(buffer);
         boolean resolved = buffer.readBoolean();
         return new WrittenBook(title, author, generation, pages, resolved);
      }

      public void write(ByteBuf buffer, WrittenBook value) {
         FilterableString.TYPE.write(buffer, value.title);
         Types.STRING.write(buffer, value.author);
         Types.VAR_INT.writePrimitive(buffer, value.generation);
         FilterableComponent.ARRAY_TYPE.write(buffer, value.pages);
         buffer.writeBoolean(value.resolved);
      }
   };

   public WrittenBook(FilterableString title, String author, int generation, FilterableComponent[] pages, boolean resolved) {
      this.title = title;
      this.author = author;
      this.generation = generation;
      this.pages = pages;
      this.resolved = resolved;
   }

   public FilterableString title() {
      return this.title;
   }

   public String author() {
      return this.author;
   }

   public int generation() {
      return this.generation;
   }

   public FilterableComponent[] pages() {
      return this.pages;
   }

   public boolean resolved() {
      return this.resolved;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof WrittenBook)) {
         return false;
      } else {
         WrittenBook var2 = (WrittenBook)var1;
         return Objects.equals(this.title, var2.title) && Objects.equals(this.author, var2.author) && this.generation == var2.generation && Objects.equals(this.pages, var2.pages) && this.resolved == var2.resolved;
      }
   }

   public int hashCode() {
      return ((((0 * 31 + Objects.hashCode(this.title)) * 31 + Objects.hashCode(this.author)) * 31 + Integer.hashCode(this.generation)) * 31 + Objects.hashCode(this.pages)) * 31 + Boolean.hashCode(this.resolved);
   }

   public String toString() {
      return String.format("%s[title=%s, author=%s, generation=%s, pages=%s, resolved=%s]", this.getClass().getSimpleName(), Objects.toString(this.title), Objects.toString(this.author), Integer.toString(this.generation), Objects.toString(this.pages), Boolean.toString(this.resolved));
   }
}
