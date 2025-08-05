package com.viaversion.viabackwards.protocol.v1_13to1_12_2.rewriter;

import com.google.common.base.Joiner;
import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.data.ParticleIdMappings1_12_2;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.storage.TabCompleteStorage;
import com.viaversion.viabackwards.utils.ChatUtil;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.rewriter.ItemPacketRewriter1_13;
import com.viaversion.viaversion.protocols.v1_12to1_12_1.packet.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.v1_12to1_12_1.packet.ServerboundPackets1_12_1;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaversion.util.ProtocolLogger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerPacketRewriter1_13 extends RewriterBase {
   final CommandRewriter commandRewriter;

   public PlayerPacketRewriter1_13(Protocol1_13To1_12_2 protocol) {
      super(protocol);
      this.commandRewriter = new CommandRewriter(this.protocol);
   }

   protected void registerPackets() {
      ((Protocol1_13To1_12_2)this.protocol).registerClientbound(State.LOGIN, ClientboundLoginPackets.CUSTOM_QUERY.getId(), -1, new PacketHandlers() {
         public void register() {
            this.handler((packetWrapper) -> {
               packetWrapper.cancel();
               packetWrapper.create(ServerboundLoginPackets.CUSTOM_QUERY_ANSWER.getId(), (wrapper) -> {
                  wrapper.write(Types.VAR_INT, (Integer)packetWrapper.read(Types.VAR_INT));
                  wrapper.write(Types.BOOLEAN, false);
               }).sendToServer(Protocol1_13To1_12_2.class);
            });
         }
      });
      ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.CUSTOM_PAYLOAD, (wrapper) -> {
         String channel = (String)wrapper.read(Types.STRING);
         if (channel.equals("minecraft:trader_list")) {
            wrapper.write(Types.STRING, "MC|TrList");
            ((Protocol1_13To1_12_2)this.protocol).getItemRewriter().handleTradeList(wrapper);
         } else {
            String oldChannel = ItemPacketRewriter1_13.getOldPluginChannelId(channel);
            if (oldChannel == null) {
               if (!Via.getConfig().isSuppressConversionWarnings()) {
                  ((Protocol1_13To1_12_2)this.protocol).getLogger().warning("Ignoring clientbound plugin message with channel: " + channel);
               }

               wrapper.cancel();
               return;
            }

            wrapper.write(Types.STRING, oldChannel);
            if (oldChannel.equals("REGISTER") || oldChannel.equals("UNREGISTER")) {
               String[] channels = (new String((byte[])wrapper.read(Types.REMAINING_BYTES), StandardCharsets.UTF_8)).split("\u0000");
               List<String> rewrittenChannels = new ArrayList();

               for(String s : channels) {
                  String rewritten = ItemPacketRewriter1_13.getOldPluginChannelId(s);
                  if (rewritten != null) {
                     rewrittenChannels.add(rewritten);
                  } else if (!Via.getConfig().isSuppressConversionWarnings()) {
                     ((Protocol1_13To1_12_2)this.protocol).getLogger().warning("Ignoring plugin channel in clientbound " + oldChannel + ": " + s);
                  }
               }

               wrapper.write(Types.REMAINING_BYTES, Joiner.on('\u0000').join(rewrittenChannels).getBytes(StandardCharsets.UTF_8));
            }
         }

      });
      ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.LEVEL_PARTICLES, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.BOOLEAN);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.INT);
            this.handler((wrapper) -> {
               ParticleIdMappings1_12_2.ParticleData old = ParticleIdMappings1_12_2.getMapping((Integer)wrapper.get(Types.INT, 0));
               wrapper.set(Types.INT, 0, old.getHistoryId());
               int[] data = old.rewriteData((Protocol1_13To1_12_2)PlayerPacketRewriter1_13.this.protocol, wrapper);
               if (data != null) {
                  if (old.getHandler().isBlockHandler() && data[0] == 0) {
                     wrapper.cancel();
                     return;
                  }

                  for(int i : data) {
                     wrapper.write(Types.VAR_INT, i);
                  }
               }

            });
         }
      });
      ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.PLAYER_INFO, new PacketHandlers() {
         public void register() {
            this.handler((packetWrapper) -> {
               TabCompleteStorage storage = (TabCompleteStorage)packetWrapper.user().get(TabCompleteStorage.class);
               int action = (Integer)packetWrapper.passthrough(Types.VAR_INT);
               int nPlayers = (Integer)packetWrapper.passthrough(Types.VAR_INT);

               for(int i = 0; i < nPlayers; ++i) {
                  UUID uuid = (UUID)packetWrapper.passthrough(Types.UUID);
                  if (action != 0) {
                     if (action == 1) {
                        packetWrapper.passthrough(Types.VAR_INT);
                     } else if (action == 2) {
                        packetWrapper.passthrough(Types.VAR_INT);
                     } else if (action == 3) {
                        packetWrapper.passthrough(Types.OPTIONAL_COMPONENT);
                     } else if (action == 4) {
                        storage.usernames().remove(uuid);
                     }
                  } else {
                     String name = (String)packetWrapper.passthrough(Types.STRING);
                     storage.usernames().put(uuid, name);
                     int nProperties = (Integer)packetWrapper.passthrough(Types.VAR_INT);

                     for(int j = 0; j < nProperties; ++j) {
                        packetWrapper.passthrough(Types.STRING);
                        packetWrapper.passthrough(Types.STRING);
                        packetWrapper.passthrough(Types.OPTIONAL_STRING);
                     }

                     packetWrapper.passthrough(Types.VAR_INT);
                     packetWrapper.passthrough(Types.VAR_INT);
                     packetWrapper.passthrough(Types.OPTIONAL_COMPONENT);
                  }
               }

            });
         }
      });
      ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.SET_OBJECTIVE, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.map(Types.BYTE);
            this.handler((wrapper) -> {
               byte mode = (Byte)wrapper.get(Types.BYTE, 0);
               if (mode == 0 || mode == 2) {
                  JsonElement value = (JsonElement)wrapper.read(Types.COMPONENT);
                  String legacyValue = ((Protocol1_13To1_12_2)PlayerPacketRewriter1_13.this.protocol).jsonToLegacy(wrapper.user(), value);
                  wrapper.write(Types.STRING, ChatUtil.fromLegacy(legacyValue, 'f', 32));
                  int type = (Integer)wrapper.read(Types.VAR_INT);
                  wrapper.write(Types.STRING, type == 1 ? "hearts" : "integer");
               }

            });
         }
      });
      ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.SET_PLAYER_TEAM, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.map(Types.BYTE);
            this.handler((wrapper) -> {
               byte action = (Byte)wrapper.get(Types.BYTE, 0);
               if (action == 0 || action == 2) {
                  JsonElement displayName = (JsonElement)wrapper.read(Types.COMPONENT);
                  String legacyTextDisplayName = ((Protocol1_13To1_12_2)PlayerPacketRewriter1_13.this.protocol).jsonToLegacy(wrapper.user(), displayName);
                  wrapper.write(Types.STRING, ChatUtil.fromLegacy(legacyTextDisplayName, 'f', 32));
                  byte flags = (Byte)wrapper.read(Types.BYTE);
                  String nameTagVisibility = (String)wrapper.read(Types.STRING);
                  String collisionRule = (String)wrapper.read(Types.STRING);
                  int colour = (Integer)wrapper.read(Types.VAR_INT);
                  if (colour == 21) {
                     colour = -1;
                  }

                  JsonElement prefixComponent = (JsonElement)wrapper.read(Types.COMPONENT);
                  JsonElement suffixComponent = (JsonElement)wrapper.read(Types.COMPONENT);
                  String prefix = ((Protocol1_13To1_12_2)PlayerPacketRewriter1_13.this.protocol).jsonToLegacy(wrapper.user(), prefixComponent);
                  if (ViaBackwards.getConfig().addTeamColorTo1_13Prefix()) {
                     String var15 = colour > -1 && colour <= 15 ? Integer.toHexString(colour) : "r";
                     prefix = prefix + "§" + var15;
                  }

                  String suffix = ((Protocol1_13To1_12_2)PlayerPacketRewriter1_13.this.protocol).jsonToLegacy(wrapper.user(), suffixComponent);
                  wrapper.write(Types.STRING, ChatUtil.fromLegacyPrefix(prefix, 'f', 16));
                  wrapper.write(Types.STRING, ChatUtil.fromLegacy(suffix, '\u0000', 16));
                  wrapper.write(Types.BYTE, flags);
                  wrapper.write(Types.STRING, nameTagVisibility);
                  wrapper.write(Types.STRING, collisionRule);
                  wrapper.write(Types.BYTE, (byte)colour);
               }

               if (action == 0 || action == 3 || action == 4) {
                  wrapper.passthrough(Types.STRING_ARRAY);
               }

            });
         }
      });
      ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.COMMANDS, (ClientboundPacketType)null, (wrapper) -> {
         wrapper.cancel();
         TabCompleteStorage storage = (TabCompleteStorage)wrapper.user().get(TabCompleteStorage.class);
         if (!storage.commands().isEmpty()) {
            storage.commands().clear();
         }

         int size = (Integer)wrapper.read(Types.VAR_INT);
         boolean initialNodes = true;

         for(int i = 0; i < size; ++i) {
            byte flags = (Byte)wrapper.read(Types.BYTE);
            wrapper.read(Types.VAR_INT_ARRAY_PRIMITIVE);
            if ((flags & 8) != 0) {
               wrapper.read(Types.VAR_INT);
            }

            byte nodeType = (byte)(flags & 3);
            if (initialNodes && nodeType == 2) {
               initialNodes = false;
            }

            if (nodeType == 1 || nodeType == 2) {
               String name = (String)wrapper.read(Types.STRING);
               if (nodeType == 1 && initialNodes) {
                  storage.commands().add("/" + name);
               }
            }

            if (nodeType == 2) {
               this.commandRewriter.handleArgument(wrapper, (String)wrapper.read(Types.STRING));
            }

            if ((flags & 16) != 0) {
               wrapper.read(Types.STRING);
            }
         }

      });
      ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.COMMAND_SUGGESTIONS, (wrapper) -> {
         TabCompleteStorage storage = (TabCompleteStorage)wrapper.user().get(TabCompleteStorage.class);
         if (storage.lastRequest() == null) {
            wrapper.cancel();
         } else {
            if (storage.lastId() != (Integer)wrapper.read(Types.VAR_INT)) {
               wrapper.cancel();
            }

            int start = (Integer)wrapper.read(Types.VAR_INT);
            int length = (Integer)wrapper.read(Types.VAR_INT);
            int lastRequestPartIndex = storage.lastRequest().lastIndexOf(32) + 1;
            if (lastRequestPartIndex != start) {
               wrapper.cancel();
            }

            if (length != storage.lastRequest().length() - lastRequestPartIndex) {
               wrapper.cancel();
            }

            int count = (Integer)wrapper.passthrough(Types.VAR_INT);

            for(int i = 0; i < count; ++i) {
               String match = (String)wrapper.read(Types.STRING);
               String var9 = start == 0 && !storage.isLastAssumeCommand() ? "/" : "";
               wrapper.write(Types.STRING, var9 + match);
               wrapper.read(Types.OPTIONAL_COMPONENT);
            }

         }
      });
      ((Protocol1_13To1_12_2)this.protocol).registerServerbound(ServerboundPackets1_12_1.COMMAND_SUGGESTION, (wrapper) -> {
         TabCompleteStorage storage = (TabCompleteStorage)wrapper.user().get(TabCompleteStorage.class);
         List<String> suggestions = new ArrayList();
         String command = (String)wrapper.read(Types.STRING);
         boolean assumeCommand = (Boolean)wrapper.read(Types.BOOLEAN);
         wrapper.read(Types.OPTIONAL_POSITION1_8);
         if (!assumeCommand && !command.startsWith("/")) {
            String buffer = command.substring(command.lastIndexOf(32) + 1);

            for(String value : storage.usernames().values()) {
               if (startsWithIgnoreCase(value, buffer)) {
                  suggestions.add(value);
               }
            }
         } else if (!storage.commands().isEmpty() && !command.contains(" ")) {
            for(String value : storage.commands()) {
               if (startsWithIgnoreCase(value, command)) {
                  suggestions.add(value);
               }
            }
         }

         if (suggestions.isEmpty()) {
            if (!assumeCommand && command.startsWith("/")) {
               command = command.substring(1);
            }

            int id = ThreadLocalRandom.current().nextInt();
            wrapper.write(Types.VAR_INT, id);
            wrapper.write(Types.STRING, command);
            storage.setLastId(id);
            storage.setLastAssumeCommand(assumeCommand);
            storage.setLastRequest(command);
         } else {
            wrapper.cancel();
            PacketWrapper response = wrapper.create(ClientboundPackets1_12_1.COMMAND_SUGGESTIONS);
            response.write(Types.VAR_INT, suggestions.size());

            for(String value : suggestions) {
               response.write(Types.STRING, value);
            }

            response.scheduleSend(Protocol1_13To1_12_2.class);
            storage.setLastRequest((String)null);
         }
      });
      ((Protocol1_13To1_12_2)this.protocol).registerServerbound(ServerboundPackets1_12_1.CUSTOM_PAYLOAD, (wrapper) -> {
         switch ((String)wrapper.read(Types.STRING)) {
            case "MC|BSign":
            case "MC|BEdit":
               wrapper.setPacketType(ServerboundPackets1_13.EDIT_BOOK);
               Item book = (Item)wrapper.read(Types.ITEM1_8);
               wrapper.write(Types.ITEM1_13, ((Protocol1_13To1_12_2)this.protocol).getItemRewriter().handleItemToServer(wrapper.user(), book));
               boolean signing = channel.equals("MC|BSign");
               wrapper.write(Types.BOOLEAN, signing);
               break;
            case "MC|ItemName":
               wrapper.setPacketType(ServerboundPackets1_13.RENAME_ITEM);
               break;
            case "MC|AdvCmd":
               byte type = (Byte)wrapper.read(Types.BYTE);
               if (type == 0) {
                  wrapper.setPacketType(ServerboundPackets1_13.SET_COMMAND_BLOCK);
                  wrapper.cancel();
                  ((Protocol1_13To1_12_2)this.protocol).getLogger().warning("Client send MC|AdvCmd custom payload to update command block, weird!");
               } else if (type == 1) {
                  wrapper.setPacketType(ServerboundPackets1_13.SET_COMMAND_MINECART);
                  wrapper.write(Types.VAR_INT, (Integer)wrapper.read(Types.INT));
                  wrapper.passthrough(Types.STRING);
                  wrapper.passthrough(Types.BOOLEAN);
               } else {
                  wrapper.cancel();
               }
               break;
            case "MC|AutoCmd":
               wrapper.setPacketType(ServerboundPackets1_13.SET_COMMAND_BLOCK);
               int x = (Integer)wrapper.read(Types.INT);
               int y = (Integer)wrapper.read(Types.INT);
               int z = (Integer)wrapper.read(Types.INT);
               wrapper.write(Types.BLOCK_POSITION1_8, new BlockPosition(x, (short)y, z));
               wrapper.passthrough(Types.STRING);
               byte flags = 0;
               if ((Boolean)wrapper.read(Types.BOOLEAN)) {
                  flags = (byte)(flags | 1);
               }

               String mode = (String)wrapper.read(Types.STRING);
               int modeId = mode.equals("SEQUENCE") ? 0 : (mode.equals("AUTO") ? 1 : 2);
               wrapper.write(Types.VAR_INT, modeId);
               if ((Boolean)wrapper.read(Types.BOOLEAN)) {
                  flags = (byte)(flags | 2);
               }

               if ((Boolean)wrapper.read(Types.BOOLEAN)) {
                  flags = (byte)(flags | 4);
               }

               wrapper.write(Types.BYTE, flags);
               break;
            case "MC|Struct":
               wrapper.setPacketType(ServerboundPackets1_13.SET_STRUCTURE_BLOCK);
               int x = (Integer)wrapper.read(Types.INT);
               int y = (Integer)wrapper.read(Types.INT);
               int z = (Integer)wrapper.read(Types.INT);
               wrapper.write(Types.BLOCK_POSITION1_8, new BlockPosition(x, (short)y, z));
               wrapper.write(Types.VAR_INT, (Byte)wrapper.read(Types.BYTE) - 1);
               String mode = (String)wrapper.read(Types.STRING);
               int modeId = mode.equals("SAVE") ? 0 : (mode.equals("LOAD") ? 1 : (mode.equals("CORNER") ? 2 : 3));
               wrapper.write(Types.VAR_INT, modeId);
               wrapper.passthrough(Types.STRING);
               wrapper.write(Types.BYTE, ((Integer)wrapper.read(Types.INT)).byteValue());
               wrapper.write(Types.BYTE, ((Integer)wrapper.read(Types.INT)).byteValue());
               wrapper.write(Types.BYTE, ((Integer)wrapper.read(Types.INT)).byteValue());
               wrapper.write(Types.BYTE, ((Integer)wrapper.read(Types.INT)).byteValue());
               wrapper.write(Types.BYTE, ((Integer)wrapper.read(Types.INT)).byteValue());
               wrapper.write(Types.BYTE, ((Integer)wrapper.read(Types.INT)).byteValue());
               String mirror = (String)wrapper.read(Types.STRING);
               if (mode.equals("NONE")) {
                  boolean var10000 = false;
               } else {
                  boolean var36 = mode.equals("LEFT_RIGHT") ? true : true;
               }

               String rotation = (String)wrapper.read(Types.STRING);
               if (mode.equals("NONE")) {
                  boolean var37 = false;
               } else if (mode.equals("CLOCKWISE_90")) {
                  boolean var38 = true;
               } else {
                  boolean var39 = mode.equals("CLOCKWISE_180") ? true : true;
               }

               wrapper.passthrough(Types.STRING);
               byte flags = 0;
               if ((Boolean)wrapper.read(Types.BOOLEAN)) {
                  flags = (byte)(flags | 1);
               }

               if ((Boolean)wrapper.read(Types.BOOLEAN)) {
                  flags = (byte)(flags | 2);
               }

               if ((Boolean)wrapper.read(Types.BOOLEAN)) {
                  flags = (byte)(flags | 4);
               }

               wrapper.passthrough(Types.FLOAT);
               wrapper.passthrough(Types.VAR_LONG);
               wrapper.write(Types.BYTE, flags);
               break;
            case "MC|Beacon":
               wrapper.setPacketType(ServerboundPackets1_13.SET_BEACON);
               wrapper.write(Types.VAR_INT, (Integer)wrapper.read(Types.INT));
               wrapper.write(Types.VAR_INT, (Integer)wrapper.read(Types.INT));
               break;
            case "MC|TrSel":
               wrapper.setPacketType(ServerboundPackets1_13.SELECT_TRADE);
               wrapper.write(Types.VAR_INT, (Integer)wrapper.read(Types.INT));
               break;
            case "MC|PickItem":
               wrapper.setPacketType(ServerboundPackets1_13.PICK_ITEM);
               break;
            default:
               String newChannel = ItemPacketRewriter1_13.getNewPluginChannelId(channel);
               if (newChannel == null) {
                  if (!Via.getConfig().isSuppressConversionWarnings()) {
                     ((Protocol1_13To1_12_2)this.protocol).getLogger().warning("Ignoring serverbound plugin message with channel: " + channel);
                  }

                  wrapper.cancel();
                  return;
               }

               wrapper.write(Types.STRING, newChannel);
               if (newChannel.equals("minecraft:register") || newChannel.equals("minecraft:unregister")) {
                  String[] channels = (new String((byte[])wrapper.read(Types.REMAINING_BYTES), StandardCharsets.UTF_8)).split("\u0000");
                  List<String> rewrittenChannels = new ArrayList();

                  for(String s : channels) {
                     String rewritten = ItemPacketRewriter1_13.getNewPluginChannelId(s);
                     if (rewritten != null) {
                        rewrittenChannels.add(rewritten);
                     } else if (!Via.getConfig().isSuppressConversionWarnings()) {
                        ProtocolLogger var40 = ((Protocol1_13To1_12_2)this.protocol).getLogger();
                        String var18 = Key.stripMinecraftNamespace(newChannel).toUpperCase(Locale.ROOT);
                        var40.warning("Ignoring plugin channel in serverbound " + var18 + ": " + s);
                     }
                  }

                  if (!rewrittenChannels.isEmpty()) {
                     wrapper.write(Types.REMAINING_BYTES, Joiner.on('\u0000').join(rewrittenChannels).getBytes(StandardCharsets.UTF_8));
                  } else {
                     wrapper.cancel();
                  }
               }
         }

      });
      ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.AWARD_STATS, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> {
               int size = (Integer)wrapper.get(Types.VAR_INT, 0);
               int newSize = size;

               for(int i = 0; i < size; ++i) {
                  int categoryId = (Integer)wrapper.read(Types.VAR_INT);
                  int statisticId = (Integer)wrapper.read(Types.VAR_INT);
                  String name = "";
                  switch (categoryId) {
                     case 0:
                     case 1:
                     case 2:
                     case 3:
                     case 4:
                     case 5:
                     case 6:
                     case 7:
                        wrapper.read(Types.VAR_INT);
                        --newSize;
                        break;
                     case 8:
                        name = (String)((Protocol1_13To1_12_2)PlayerPacketRewriter1_13.this.protocol).getMappingData().getStatisticMappings().get(statisticId);
                        if (name == null) {
                           wrapper.read(Types.VAR_INT);
                           --newSize;
                           break;
                        }
                     default:
                        wrapper.write(Types.STRING, name);
                        wrapper.passthrough(Types.VAR_INT);
                  }
               }

               if (newSize != size) {
                  wrapper.set(Types.VAR_INT, 0, newSize);
               }

            });
         }
      });
   }

   static boolean startsWithIgnoreCase(String string, String prefix) {
      return string.length() < prefix.length() ? false : string.regionMatches(true, 0, prefix, 0, prefix.length());
   }
}
