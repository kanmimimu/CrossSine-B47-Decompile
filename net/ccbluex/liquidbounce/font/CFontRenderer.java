package net.ccbluex.liquidbounce.font;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.lwjgl.opengl.GL11;

public class CFontRenderer extends CFont {
   protected final CFont.CharData[] boldChars = new CFont.CharData[256];
   protected final CFont.CharData[] italicChars = new CFont.CharData[256];
   protected final CFont.CharData[] boldItalicChars = new CFont.CharData[256];
   private final int[] colorCode = new int[32];
   protected DynamicTexture texBold;
   protected DynamicTexture texItalic;
   protected DynamicTexture texItalicBold;

   public CFontRenderer(Font font, boolean antiAlias, boolean fractionalMetrics) {
      super(font, antiAlias, fractionalMetrics);
      this.setupMinecraftColorcodes();
      this.setupBoldItalicIDs();
   }

   public float drawStringWithShadow(String text, double x, double y, int color) {
      return Math.max(this.drawString(text, x + (double)0.5F, y + (double)0.5F, color, true), this.drawString(text, x, y, color, false));
   }

   public float drawString(String text, float x, float y, int color) {
      GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
      RenderUtils.glColor(color);
      return this.drawString(text, (double)x, (double)y, color, false);
   }

   public float drawCenteredString(String text, double x, double y, int color) {
      GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
      return this.drawString(text, (float)(x - (double)((float)(this.getStringWidth(text) / 2))), (float)y, color);
   }

   public float drawCenteredStringWithShadow(String text, float x, float y, int color) {
      return this.drawStringWithShadow(text, (double)(x - (float)(this.getStringWidth(text) / 2)), (double)y, color);
   }

   public float drawCenteredStringWithShadow(String text, double x, double y, int color) {
      return this.drawStringWithShadow(text, x - (double)(this.getStringWidth(text) / 2), y, color);
   }

   public static boolean isChinese(char c) {
      String s = String.valueOf(c);
      return "1234567890abcdefghijklmnopqrstuvwxyz!<>@#$%^&*()-_=+[]{}|\\/'\",.~`".contains(s.toLowerCase());
   }

   public static boolean isContainChinese(String str) {
      Pattern p = Pattern.compile("[一-龥]");
      Matcher m = p.matcher(str);
      return m.find();
   }

   public static char validateLegalString(String content) {
      String illegal = "`~!#%^&*=+\\|{};:'\",<>/?○●★☆☉♀♂※¤╬の〆";
      char isLegalChar = 't';

      for(int i = 0; i < content.length(); ++i) {
         for(int j = 0; j < illegal.length(); ++j) {
            if (content.charAt(i) == illegal.charAt(j)) {
               isLegalChar = content.charAt(i);
               return isLegalChar;
            }
         }
      }

      return isLegalChar;
   }

   public static int DisplayFontWidth(String str, CFontRenderer font) {
      int x = 0;

      for(int iF = 0; iF < str.length(); ++iF) {
         String s = String.valueOf(str.toCharArray()[iF]);
         if (s.contains("§") && iF + 1 <= str.length()) {
            ++iF;
         } else if (isChinese(s.charAt(0))) {
            x = (int)((float)x + (float)font.getStringWidth(s));
         } else {
            x = (int)((float)x + (float)Fonts.font35.func_78256_a(s));
         }
      }

      return x + 5;
   }

   public int DisplayFontWidths(CFontRenderer font, String str) {
      return this.DisplayFontWidths(str, font);
   }

   public int DisplayFontWidths(String str, CFontRenderer font) {
      int x = 0;

      for(int iF = 0; iF < str.length(); ++iF) {
         String s = String.valueOf(str.toCharArray()[iF]);
         if (s.contains("§") && iF + 1 <= str.length()) {
            ++iF;
         } else if (isChinese(s.charAt(0))) {
            x = (int)((float)x + (float)font.getStringWidth(s));
         } else {
            x = (int)((float)x + (float)Fonts.font35.func_78256_a(s));
         }
      }

      return x + 5;
   }

   public static void DisplayFont(CFontRenderer font, String str, float x, float y, int color) {
      DisplayFont(str, x, y, color, font);
   }

   public static void DisplayFonts(CFontRenderer font, String str, float x, float y, int color) {
      DisplayFont(str, x, y, color, font);
   }

   public float DisplayFont2(CFontRenderer font, String str, float x, float y, int color, boolean shadow) {
      return shadow ? DisplayFont(str, x, y, color, true, font) : DisplayFont(str, x, y, color, font);
   }

   public static float DisplayFont(String str, float x, float y, int color, boolean shadow, CFontRenderer font) {
      str = " " + str;

      for(int iF = 0; iF < str.length(); ++iF) {
         String s = String.valueOf(str.toCharArray()[iF]);
         if (s.contains("§") && iF + 1 <= str.length()) {
            color = getColor(String.valueOf(str.toCharArray()[iF + 1]));
            ++iF;
         } else if (isChinese(s.charAt(0))) {
            font.drawString(s, x + 0.5F, y + 1.5F, (new Color(0, 0, 0, 100)).getRGB());
            font.drawString(s, x - 0.5F, y + 0.5F, color);
            x += (float)font.getStringWidth(s);
         } else {
            Fonts.font35.drawString(s, x + 1.5F, y + 2.0F, (new Color(0, 0, 0, 50)).getRGB());
            Fonts.font35.drawString(s, x + 0.5F, y + 1.0F, color);
            x += (float)Fonts.font35.func_78256_a(s);
         }
      }

      return x;
   }

   public static float DisplayFont(String str, float x, float y, int color, CFontRenderer font) {
      str = " " + str;

      for(int iF = 0; iF < str.length(); ++iF) {
         String s = String.valueOf(str.toCharArray()[iF]);
         if (s.contains("§") && iF + 1 <= str.length()) {
            color = getColor(String.valueOf(str.toCharArray()[iF + 1]));
            ++iF;
         } else if (isChinese(s.charAt(0))) {
            font.drawString(s, x - 0.5F, y + 1.0F, color);
            x += (float)font.getStringWidth(s);
         } else {
            Fonts.font35.drawString(s, x + 0.5F, y + 1.0F, color);
            x += (float)Fonts.font35.func_78256_a(s);
         }
      }

      return x;
   }

   public float DisplayFonts(String str, float x, float y, int color, CFontRenderer font) {
      str = " " + str;

      for(int iF = 0; iF < str.length(); ++iF) {
         String s = String.valueOf(str.toCharArray()[iF]);
         if (s.contains("§") && iF + 1 <= str.length()) {
            color = getColor(String.valueOf(str.toCharArray()[iF + 1]));
            ++iF;
         } else if (isChinese(s.charAt(0))) {
            font.drawString(s, x - 0.5F, y + 1.0F, color);
            x += (float)font.getStringWidth(s);
         } else {
            Fonts.font35.drawString(s, x + 0.5F, y + 1.0F, color);
            x += (float)Fonts.font35.func_78256_a(s);
         }
      }

      return x;
   }

   public static int getColor(String var0) {
      // $FF: Couldn't be decompiled
   }

   public float drawString(String text, float x, float y, int color, boolean shadow) {
      return this.drawString(text, (double)x, (double)y, color, shadow);
   }

   public float drawString(String text, double x, double y, int color, boolean shadow) {
      GlStateManager.func_179147_l();
      GlStateManager.func_179084_k();
      double x2 = x - (double)1.0F;
      if (text == null) {
         return 0.0F;
      } else {
         if (color == 553648127) {
            color = 16777215;
         }

         if ((color & -67108864) == 0) {
            color |= -16777216;
         }

         if (shadow) {
            color = (new Color(0, 0, 0)).getRGB();
         }

         CFont.CharData[] currentData = this.charData;
         float alpha = (float)(color >> 24 & 255) / 255.0F;
         boolean bold = false;
         boolean italic = false;
         boolean strikethrough = false;
         boolean underline = false;
         char c = (char)((int)(x2 * (double)2.0F));
         double y2 = (y - (double)3.0F) * (double)2.0F;
         GL11.glPushMatrix();
         GlStateManager.func_179139_a((double)0.5F, (double)0.5F, (double)0.5F);
         GlStateManager.func_179147_l();
         GlStateManager.func_179112_b(770, 771);
         GlStateManager.func_179131_c((float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, alpha);
         int size = text.length();
         GlStateManager.func_179098_w();
         GlStateManager.func_179144_i(this.tex.func_110552_b());
         GL11.glBindTexture(3553, this.tex.func_110552_b());

         for(int i = 0; i < size; ++i) {
            char character = text.charAt(i);
            if (character == 167) {
               int colorIndex = 21;

               try {
                  colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
               } catch (Exception e) {
                  e.printStackTrace();
               }

               if (colorIndex < 16) {
                  bold = false;
                  italic = false;
                  underline = false;
                  strikethrough = false;
                  GlStateManager.func_179144_i(this.tex.func_110552_b());
                  currentData = this.charData;
                  if (colorIndex < 0) {
                     colorIndex = 15;
                  }

                  if (shadow) {
                     colorIndex += 16;
                  }

                  int colorcode = this.colorCode[colorIndex];
                  GlStateManager.func_179131_c((float)(colorcode >> 16 & 255) / 255.0F, (float)(colorcode >> 8 & 255) / 255.0F, (float)(colorcode & 255) / 255.0F, alpha);
               } else if (colorIndex != 16) {
                  if (colorIndex == 17) {
                     bold = true;
                     if (italic) {
                        GlStateManager.func_179144_i(this.texItalicBold.func_110552_b());
                        currentData = this.boldItalicChars;
                     } else {
                        GlStateManager.func_179144_i(this.texBold.func_110552_b());
                        currentData = this.boldChars;
                     }
                  } else if (colorIndex == 18) {
                     strikethrough = true;
                  } else if (colorIndex == 19) {
                     underline = true;
                  } else if (colorIndex == 20) {
                     italic = true;
                     if (bold) {
                        GlStateManager.func_179144_i(this.texItalicBold.func_110552_b());
                        currentData = this.boldItalicChars;
                     } else {
                        GlStateManager.func_179144_i(this.texItalic.func_110552_b());
                        currentData = this.italicChars;
                     }
                  } else {
                     bold = false;
                     italic = false;
                     underline = false;
                     strikethrough = false;
                     GlStateManager.func_179131_c((float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, alpha);
                     GlStateManager.func_179144_i(this.tex.func_110552_b());
                     currentData = this.charData;
                  }
               }

               ++i;
            } else if (character < currentData.length) {
               GL11.glBegin(4);
               this.drawChar(currentData, character, (float)c, (float)y2);
               GL11.glEnd();
               if (strikethrough) {
                  this.drawLine((double)c, y2 + (double)(currentData[character].height / 2), (double)c + (double)currentData[character].width - (double)8.0F, y2 + (double)(currentData[character].height / 2));
               }

               if (underline) {
                  this.drawLine((double)c, y2 + (double)currentData[character].height - (double)2.0F, (double)c + (double)currentData[character].width - (double)8.0F, y2 + (double)currentData[character].height - (double)2.0F);
               }

               double var10000 = (double)c;
               int var10001 = currentData[character].width - 8;
               this.getClass();
               c = (char)((int)(var10000 + (double)(var10001 + 0)));
            }
         }

         GL11.glHint(3155, 4352);
         GL11.glPopMatrix();
         return (float)c / 2.0F;
      }
   }

   public int drawStringi(String text, double x, double y, int color, boolean shadow) {
      GlStateManager.func_179147_l();
      GlStateManager.func_179084_k();
      double x2 = x - (double)1.0F;
      if (text == null) {
         return 0;
      } else {
         if (color == 553648127) {
            color = 16777215;
         }

         if ((color & -67108864) == 0) {
            color |= -16777216;
         }

         if (shadow) {
            color = (new Color(0, 0, 0)).getRGB();
         }

         CFont.CharData[] currentData = this.charData;
         float alpha = (float)(color >> 24 & 255) / 255.0F;
         boolean bold = false;
         boolean italic = false;
         boolean strikethrough = false;
         boolean underline = false;
         char c = (char)((int)(x2 * (double)2.0F));
         double y2 = (y - (double)3.0F) * (double)2.0F;
         GL11.glPushMatrix();
         GlStateManager.func_179139_a((double)0.5F, (double)0.5F, (double)0.5F);
         GlStateManager.func_179147_l();
         GlStateManager.func_179112_b(770, 771);
         GlStateManager.func_179131_c((float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, alpha);
         int size = text.length();
         GlStateManager.func_179098_w();
         GlStateManager.func_179144_i(this.tex.func_110552_b());
         GL11.glBindTexture(3553, this.tex.func_110552_b());

         for(int i = 0; i < size; ++i) {
            char character = text.charAt(i);
            if (character == 167) {
               int colorIndex = 21;

               try {
                  colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
               } catch (Exception e) {
                  e.printStackTrace();
               }

               if (colorIndex < 16) {
                  bold = false;
                  italic = false;
                  underline = false;
                  strikethrough = false;
                  GlStateManager.func_179144_i(this.tex.func_110552_b());
                  currentData = this.charData;
                  if (colorIndex < 0) {
                     colorIndex = 15;
                  }

                  if (shadow) {
                     colorIndex += 16;
                  }

                  int colorcode = this.colorCode[colorIndex];
                  GlStateManager.func_179131_c((float)(colorcode >> 16 & 255) / 255.0F, (float)(colorcode >> 8 & 255) / 255.0F, (float)(colorcode & 255) / 255.0F, alpha);
               } else if (colorIndex != 16) {
                  if (colorIndex == 17) {
                     bold = true;
                     if (italic) {
                        GlStateManager.func_179144_i(this.texItalicBold.func_110552_b());
                        currentData = this.boldItalicChars;
                     } else {
                        GlStateManager.func_179144_i(this.texBold.func_110552_b());
                        currentData = this.boldChars;
                     }
                  } else if (colorIndex == 18) {
                     strikethrough = true;
                  } else if (colorIndex == 19) {
                     underline = true;
                  } else if (colorIndex == 20) {
                     italic = true;
                     if (bold) {
                        GlStateManager.func_179144_i(this.texItalicBold.func_110552_b());
                        currentData = this.boldItalicChars;
                     } else {
                        GlStateManager.func_179144_i(this.texItalic.func_110552_b());
                        currentData = this.italicChars;
                     }
                  } else {
                     bold = false;
                     italic = false;
                     underline = false;
                     strikethrough = false;
                     GlStateManager.func_179131_c((float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, alpha);
                     GlStateManager.func_179144_i(this.tex.func_110552_b());
                     currentData = this.charData;
                  }
               }

               ++i;
            } else if (character < currentData.length) {
               GL11.glBegin(4);
               this.drawChar(currentData, character, (float)c, (float)y2);
               GL11.glEnd();
               if (strikethrough) {
                  this.drawLine((double)c, y2 + (double)(currentData[character].height / 2), (double)c + (double)currentData[character].width - (double)8.0F, y2 + (double)(currentData[character].height / 2));
               }

               if (underline) {
                  this.drawLine((double)c, y2 + (double)currentData[character].height - (double)2.0F, (double)c + (double)currentData[character].width - (double)8.0F, y2 + (double)currentData[character].height - (double)2.0F);
               }

               double var10000 = (double)c;
               int var10001 = currentData[character].width - 8;
               this.getClass();
               c = (char)((int)(var10000 + (double)(var10001 + 0)));
            }
         }

         GL11.glHint(3155, 4352);
         GL11.glPopMatrix();
         return c / 2;
      }
   }

   public int getStringWidth(String text) {
      if (text == null) {
         return 0;
      } else {
         int width = 0;
         CFont.CharData[] currentData = this.charData;
         boolean bold = false;
         boolean italic = false;
         int size = text.length();

         for(int i = 0; i < size; ++i) {
            char character = text.charAt(i);
            if (character == 167) {
               int colorIndex = "0123456789abcdefklmnor".indexOf(character);
               bold = false;
               italic = false;
               ++i;
            } else if (character < currentData.length) {
               int var10001 = currentData[character].width - 8;
               this.getClass();
               width += var10001 + 0;
            }
         }

         return width / 2;
      }
   }

   public void setFont(Font font) {
      this.setFont(font);
      this.setupBoldItalicIDs();
   }

   public void setAntiAlias(boolean antiAlias) {
      this.setAntiAlias(antiAlias);
      this.setupBoldItalicIDs();
   }

   public void setFractionalMetrics(boolean fractionalMetrics) {
      this.setFractionalMetrics(fractionalMetrics);
      this.setupBoldItalicIDs();
   }

   private void setupBoldItalicIDs() {
      this.texBold = this.setupTexture(this.font.deriveFont(1), this.antiAlias, this.fractionalMetrics, this.boldChars);
      this.texItalic = this.setupTexture(this.font.deriveFont(2), this.antiAlias, this.fractionalMetrics, this.italicChars);
   }

   private void drawLine(double x, double y, double x1, double y1) {
      GL11.glDisable(3553);
      GL11.glLineWidth(1.0F);
      GL11.glBegin(1);
      GL11.glVertex2d(x, y);
      GL11.glVertex2d(x1, y1);
      GL11.glEnd();
      GL11.glEnable(3553);
   }

   public List wrapWords(String text, double width) {
      ArrayList<String> finalWords = new ArrayList();
      if ((double)this.getStringWidth(text) > width) {
         String[] words = text.split(" ");
         String currentWord = "";
         char c = '\uffff';

         for(String word : words) {
            for(int i = 0; i < word.toCharArray().length; ++i) {
               if (word.toCharArray()[i] == 167 && i < word.toCharArray().length - 1) {
                  c = word.toCharArray()[i + 1];
               }
            }

            if ((double)this.getStringWidth(currentWord + word + " ") < width) {
               currentWord = currentWord + word + " ";
            } else {
               finalWords.add(currentWord);
               currentWord = 167 + c + word + " ";
            }
         }

         if (currentWord.length() > 0) {
            if ((double)this.getStringWidth(currentWord) < width) {
               finalWords.add(167 + c + currentWord + " ");
            } else {
               finalWords.addAll(this.formatString(currentWord, width));
            }
         }
      } else {
         finalWords.add(text);
      }

      return finalWords;
   }

   public List formatString(String string, double width) {
      ArrayList<String> finalWords = new ArrayList();
      String currentWord = "";
      int lastColorCode = 65535;
      char[] chars = string.toCharArray();

      for(int i = 0; i < chars.length; ++i) {
         char c = chars[i];
         if (c == 167 && i < chars.length - 1) {
            lastColorCode = chars[i + 1];
         }

         if ((double)this.getStringWidth(currentWord + c) < width) {
            currentWord = currentWord + c;
         } else {
            finalWords.add(currentWord);
            currentWord = String.valueOf(167 + lastColorCode) + c;
         }
      }

      if (currentWord.length() > 0) {
         finalWords.add(currentWord);
      }

      return finalWords;
   }

   private void setupMinecraftColorcodes() {
      for(int index = 0; index < 32; ++index) {
         int noClue = (index >> 3 & 1) * 85;
         int red = (index >> 2 & 1) * 170 + noClue;
         int green = (index >> 1 & 1) * 170 + noClue;
         int blue = (index & 1) * 170 + noClue;
         if (index == 6) {
            red += 85;
         }

         if (index >= 16) {
            red /= 4;
            green /= 4;
            blue /= 4;
         }

         this.colorCode[index] = (red & 255) << 16 | (green & 255) << 8 | blue & 255;
      }

   }
}
