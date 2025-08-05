package com.viaversion.viaversion.libs.gson;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.viaversion.viaversion.libs.gson.internal.LinkedTreeMap;
import java.util.Map;
import java.util.Set;

public final class JsonObject extends JsonElement {
   private final LinkedTreeMap members = new LinkedTreeMap(false);

   public JsonObject deepCopy() {
      JsonObject result = new JsonObject();

      for(Map.Entry entry : this.members.entrySet()) {
         result.add((String)entry.getKey(), ((JsonElement)entry.getValue()).deepCopy());
      }

      return result;
   }

   public void add(String property, JsonElement value) {
      this.members.put(property, value == null ? JsonNull.INSTANCE : value);
   }

   @CanIgnoreReturnValue
   public JsonElement remove(String property) {
      return (JsonElement)this.members.remove(property);
   }

   public void addProperty(String property, String value) {
      this.add(property, (JsonElement)(value == null ? JsonNull.INSTANCE : new JsonPrimitive(value)));
   }

   public void addProperty(String property, Number value) {
      this.add(property, (JsonElement)(value == null ? JsonNull.INSTANCE : new JsonPrimitive(value)));
   }

   public void addProperty(String property, Boolean value) {
      this.add(property, (JsonElement)(value == null ? JsonNull.INSTANCE : new JsonPrimitive(value)));
   }

   public void addProperty(String property, Character value) {
      this.add(property, (JsonElement)(value == null ? JsonNull.INSTANCE : new JsonPrimitive(value)));
   }

   public Set entrySet() {
      return this.members.entrySet();
   }

   public Set keySet() {
      return this.members.keySet();
   }

   public int size() {
      return this.members.size();
   }

   public boolean isEmpty() {
      return this.members.size() == 0;
   }

   public boolean has(String memberName) {
      return this.members.containsKey(memberName);
   }

   public JsonElement get(String memberName) {
      return (JsonElement)this.members.get(memberName);
   }

   public JsonPrimitive getAsJsonPrimitive(String memberName) {
      return (JsonPrimitive)this.members.get(memberName);
   }

   public JsonArray getAsJsonArray(String memberName) {
      return (JsonArray)this.members.get(memberName);
   }

   public JsonObject getAsJsonObject(String memberName) {
      return (JsonObject)this.members.get(memberName);
   }

   public Map asMap() {
      return this.members;
   }

   public boolean equals(Object o) {
      return o == this || o instanceof JsonObject && ((JsonObject)o).members.equals(this.members);
   }

   public int hashCode() {
      return this.members.hashCode();
   }
}
