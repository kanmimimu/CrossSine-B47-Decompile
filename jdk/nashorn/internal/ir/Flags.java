package jdk.nashorn.internal.ir;

public interface Flags {
   int getFlags();

   boolean getFlag(int var1);

   LexicalContextNode clearFlag(LexicalContext var1, int var2);

   LexicalContextNode setFlag(LexicalContext var1, int var2);

   LexicalContextNode setFlags(LexicalContext var1, int var2);
}
