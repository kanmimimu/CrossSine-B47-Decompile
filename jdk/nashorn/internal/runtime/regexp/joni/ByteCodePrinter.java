package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.ast.CClassNode;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;

class ByteCodePrinter {
   final int[] code;
   final int codeLength;
   final char[][] templates;
   Object[] operands;
   private static final String[] OpCodeNames = new String[]{"finish", "end", "exact1", "exact2", "exact3", "exact4", "exact5", "exactn", "exactmb2-n1", "exactmb2-n2", "exactmb2-n3", "exactmb2-n", "exactmb3n", "exactmbn", "exact1-ic", "exactn-ic", "cclass", "cclass-mb", "cclass-mix", "cclass-not", "cclass-mb-not", "cclass-mix-not", "cclass-node", "anychar", "anychar-ml", "anychar*", "anychar-ml*", "anychar*-peek-next", "anychar-ml*-peek-next", "word", "not-word", "word-bound", "not-word-bound", "word-begin", "word-end", "begin-buf", "end-buf", "begin-line", "end-line", "semi-end-buf", "begin-position", "backref1", "backref2", "backrefn", "backrefn-ic", "backref_multi", "backref_multi-ic", "backref_at_level", "mem-start", "mem-start-push", "mem-end-push", "mem-end-push-rec", "mem-end", "mem-end-rec", "fail", "jump", "push", "pop", "push-or-jump-e1", "push-if-peek-next", "repeat", "repeat-ng", "repeat-inc", "repeat-inc-ng", "repeat-inc-sg", "repeat-inc-ng-sg", "null-check-start", "null-check-end", "null-check-end-memst", "null-check-end-memst-push", "push-pos", "pop-pos", "push-pos-not", "fail-pos", "push-stop-bt", "pop-stop-bt", "look-behind", "push-look-behind-not", "fail-look-behind-not", "call", "return", "state-check-push", "state-check-push-or-jump", "state-check", "state-check-anychar*", "state-check-anychar-ml*", "set-option-push", "set-option"};
   private static final int[] OpCodeArgTypes = new int[]{0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, -1, -1, -1, -1, 4, 4, 4, 4, 4, 4, 0, 1, 1, 0, -1, -1, -1, -1, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 1, 0, 0, 0, -1, -1, 0, 2, 0, -1, -1, 6, 6, 6, 5, 5};

   public ByteCodePrinter(Regex regex) {
      this.code = regex.code;
      this.codeLength = regex.codeLength;
      this.operands = regex.operands;
      this.templates = regex.templates;
   }

   public String byteCodeListToString() {
      return this.compiledByteCodeListToString();
   }

   private void pString(StringBuilder sb, int len, int s) {
      sb.append(":");
      sb.append(new String(this.code, s, len));
   }

   private void pLenString(StringBuilder sb, int len, int s) {
      sb.append(":").append(len).append(":");
      sb.append(new String(this.code, s, len));
   }

   private static void pLenStringFromTemplate(StringBuilder sb, int len, char[] tm, int idx) {
      sb.append(":T:").append(len).append(":");
      sb.append(tm, idx, len);
   }

   public int compiledByteCodeToString(StringBuilder sb, int bptr) {
      sb.append("[").append(OpCodeNames[this.code[bptr]]);
      int argType = OpCodeArgTypes[this.code[bptr]];
      int var40;
      if (argType != -1) {
         var40 = bptr + 1;
         switch (argType) {
            case 0:
            default:
               break;
            case 1:
               sb.append(":(").append(this.code[var40]).append(")");
               ++var40;
               break;
            case 2:
               sb.append(":(").append(this.code[var40]).append(")");
               ++var40;
               break;
            case 3:
               sb.append(":").append(this.code[var40]);
               ++var40;
               break;
            case 4:
               sb.append(":").append(this.code[var40]);
               ++var40;
               break;
            case 5:
               sb.append(":").append(this.code[var40]);
               ++var40;
               break;
            case 6:
               sb.append(":").append(this.code[var40]);
               var40 += 2;
         }
      } else {
         var40 = bptr + 1;
         switch (this.code[bptr]) {
            case 2:
            case 27:
            case 28:
               this.pString(sb, 1, var40++);
               break;
            case 3:
               this.pString(sb, 2, var40);
               var40 += 2;
               break;
            case 4:
               this.pString(sb, 3, var40);
               var40 += 3;
               break;
            case 5:
               this.pString(sb, 4, var40);
               var40 += 4;
               break;
            case 6:
               this.pString(sb, 5, var40);
               var40 += 5;
               break;
            case 7:
               int len = this.code[var40];
               ++var40;
               int tm = this.code[var40];
               ++var40;
               int idx = this.code[var40];
               ++var40;
               pLenStringFromTemplate(sb, len, this.templates[tm], idx);
               break;
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 23:
            case 24:
            case 25:
            case 26:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 78:
            case 79:
            case 80:
            default:
               StringBuilder var10002 = (new StringBuilder()).append("undefined code: ");
               --var40;
               throw new InternalException(var10002.append(this.code[var40]).toString());
            case 14:
               this.pString(sb, 1, var40);
               ++var40;
               break;
            case 15:
               int len = this.code[var40];
               ++var40;
               int tm = this.code[var40];
               ++var40;
               int idx = this.code[var40];
               ++var40;
               pLenStringFromTemplate(sb, len, this.templates[tm], idx);
               break;
            case 16:
               BitSet bs = new BitSet();
               System.arraycopy(this.code, var40, bs.bits, 0, 8);
               int n = bs.numOn();
               var40 += 8;
               sb.append(":").append(n);
               break;
            case 17:
            case 20:
               int len = this.code[var40];
               ++var40;
               int cod = this.code[var40];
               var40 += len;
               sb.append(":").append(cod).append(":").append(len);
               break;
            case 18:
            case 21:
               BitSet bs = new BitSet();
               System.arraycopy(this.code, var40, bs.bits, 0, 8);
               int n = bs.numOn();
               var40 += 8;
               int len = this.code[var40];
               ++var40;
               int cod = this.code[var40];
               var40 += len;
               sb.append(":").append(n).append(":").append(cod).append(":").append(len);
               break;
            case 19:
               BitSet bs = new BitSet();
               System.arraycopy(this.code, var40, bs.bits, 0, 8);
               int n = bs.numOn();
               var40 += 8;
               sb.append(":").append(n);
               break;
            case 22:
               CClassNode cc = (CClassNode)this.operands[this.code[var40]];
               ++var40;
               int n = cc.bs.numOn();
               sb.append(":").append(cc).append(":").append(n);
               break;
            case 44:
               int mem = this.code[var40];
               ++var40;
               sb.append(":").append(mem);
               break;
            case 45:
            case 46:
               sb.append(" ");
               int len = this.code[var40];
               ++var40;

               for(int i = 0; i < len; ++i) {
                  int mem = this.code[var40];
                  ++var40;
                  if (i > 0) {
                     sb.append(", ");
                  }

                  sb.append(mem);
               }
               break;
            case 47:
               int option = this.code[var40];
               ++var40;
               sb.append(":").append(option);
               int level = this.code[var40];
               ++var40;
               sb.append(":").append(level);
               sb.append(" ");
               int len = this.code[var40];
               ++var40;

               for(int i = 0; i < len; ++i) {
                  int mem = this.code[var40];
                  ++var40;
                  if (i > 0) {
                     sb.append(", ");
                  }

                  sb.append(mem);
               }
               break;
            case 58:
            case 59:
               int addr = this.code[var40];
               ++var40;
               sb.append(":(").append(addr).append(")");
               this.pString(sb, 1, var40);
               ++var40;
               break;
            case 60:
            case 61:
               int mem = this.code[var40];
               ++var40;
               int addr = this.code[var40];
               ++var40;
               sb.append(":").append(mem).append(":").append(addr);
               break;
            case 76:
               int len = this.code[var40];
               ++var40;
               sb.append(":").append(len);
               break;
            case 77:
               int addr = this.code[var40];
               ++var40;
               int len = this.code[var40];
               ++var40;
               sb.append(":").append(len).append(":(").append(addr).append(")");
               break;
            case 81:
            case 82:
               int scn = this.code[var40];
               ++var40;
               int addr = this.code[var40];
               ++var40;
               sb.append(":").append(scn).append(":(").append(addr).append(")");
         }
      }

      sb.append("]");
      return var40;
   }

   private String compiledByteCodeListToString() {
      StringBuilder sb = new StringBuilder();
      sb.append("code length: ").append(this.codeLength).append("\n");
      int ncode = 0;
      int bp = 0;

      for(int end = this.codeLength; bp < end; bp = this.compiledByteCodeToString(sb, bp)) {
         ++ncode;
         if (bp > 0) {
            sb.append(ncode % 5 == 0 ? "\n" : " ");
         }
      }

      sb.append("\n");
      return sb.toString();
   }
}
