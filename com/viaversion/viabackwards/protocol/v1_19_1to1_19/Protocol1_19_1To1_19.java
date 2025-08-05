package com.viaversion.viabackwards.protocol.v1_19_1to1_19;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.v1_19_1to1_19.rewriter.EntityPacketRewriter1_19_1;
import com.viaversion.viabackwards.protocol.v1_19_1to1_19.storage.ChatRegistryStorage;
import com.viaversion.viabackwards.protocol.v1_19_1to1_19.storage.ChatRegistryStorage1_19_1;
import com.viaversion.viabackwards.protocol.v1_19_1to1_19.storage.NonceStorage;
import com.viaversion.viabackwards.protocol.v1_19_1to1_19.storage.ReceivedMessagesStorage;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.PlayerMessageSignature;
import com.viaversion.viaversion.api.minecraft.ProfileKey;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19;
import com.viaversion.viaversion.api.minecraft.signature.SignableCommandArgumentsProvider;
import com.viaversion.viaversion.api.minecraft.signature.model.DecoratableMessage;
import com.viaversion.viaversion.api.minecraft.signature.model.MessageMetadata;
import com.viaversion.viaversion.api.minecraft.signature.storage.ChatSession1_19_1;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.Protocol1_18_2To1_19;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.packet.ClientboundPackets1_19;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.packet.ServerboundPackets1_19;
import com.viaversion.viaversion.protocols.v1_19to1_19_1.Protocol1_19To1_19_1;
import com.viaversion.viaversion.protocols.v1_19to1_19_1.packet.ClientboundPackets1_19_1;
import com.viaversion.viaversion.protocols.v1_19to1_19_1.packet.ServerboundPackets1_19_1;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.util.CipherUtil;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.Pair;
import com.viaversion.viaversion.util.ProtocolLogger;
import com.viaversion.viaversion.util.TagUtil;
import java.security.SignatureException;
import java.util.List;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class Protocol1_19_1To1_19 extends BackwardsProtocol {
   public static final int SYSTEM_CHAT_ID = 1;
   public static final int GAME_INFO_ID = 2;
   static final UUID ZERO_UUID = new UUID(0L, 0L);
   static final byte[] EMPTY_BYTES = new byte[0];
   final EntityPacketRewriter1_19_1 entityRewriter = new EntityPacketRewriter1_19_1(this);
   final TranslatableRewriter translatableRewriter;

   public Protocol1_19_1To1_19() {
      super(ClientboundPackets1_19_1.class, ClientboundPackets1_19.class, ServerboundPackets1_19_1.class, ServerboundPackets1_19.class);
      this.translatableRewriter = new TranslatableRewriter(this, ComponentRewriter.ReadType.JSON);
   }

   protected void registerPackets() {
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_1.SET_ACTION_BAR_TEXT);
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_1.SET_TITLE_TEXT);
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_1.SET_SUBTITLE_TEXT);
      this.translatableRewriter.registerBossEvent(ClientboundPackets1_19_1.BOSS_EVENT);
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_1.DISCONNECT);
      this.translatableRewriter.registerTabList(ClientboundPackets1_19_1.TAB_LIST);
      this.translatableRewriter.registerOpenScreen(ClientboundPackets1_19_1.OPEN_SCREEN);
      this.translatableRewriter.registerPlayerCombatKill(ClientboundPackets1_19_1.PLAYER_COMBAT_KILL);
      this.translatableRewriter.registerPing();
      this.entityRewriter.register();
      this.registerClientbound(ClientboundPackets1_19_1.LOGIN, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.BOOLEAN);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.STRING_ARRAY);
            this.map(Types.NAMED_COMPOUND_TAG);
            this.map(Types.STRING);
            this.map(Types.STRING);
            this.handler((wrapper) -> {
               ChatRegistryStorage chatTypeStorage = (ChatRegistryStorage)wrapper.user().get(ChatRegistryStorage1_19_1.class);
               chatTypeStorage.clear();
               CompoundTag registry = (CompoundTag)wrapper.get(Types.NAMED_COMPOUND_TAG, 0);

               for(CompoundTag chatType : TagUtil.removeRegistryEntries(registry, "chat_type", new ListTag(CompoundTag.class))) {
                  NumberTag idTag = chatType.getNumberTag("id");
                  chatTypeStorage.addChatType(idTag.asInt(), chatType);
               }

               registry.put("minecraft:chat_type", Protocol1_18_2To1_19.MAPPINGS.chatRegistry());
            });
            this.handler(Protocol1_19_1To1_19.this.entityRewriter.worldTrackerHandlerByKey());
         }
      });
      this.registerClientbound(ClientboundPackets1_19_1.PLAYER_CHAT, ClientboundPackets1_19.SYSTEM_CHAT, (wrapper) -> {
         wrapper.read(Types.OPTIONAL_BYTE_ARRAY_PRIMITIVE);
         PlayerMessageSignature signature = (PlayerMessageSignature)wrapper.read(Types.PLAYER_MESSAGE_SIGNATURE);
         if (!signature.uuid().equals(ZERO_UUID) && signature.signatureBytes().length != 0) {
            ReceivedMessagesStorage messagesStorage = (ReceivedMessagesStorage)wrapper.user().get(ReceivedMessagesStorage.class);
            messagesStorage.add(signature);
            if (messagesStorage.tickUnacknowledged() > 64) {
               messagesStorage.resetUnacknowledgedCount();
               PacketWrapper chatAckPacket = wrapper.create(ServerboundPackets1_19_1.CHAT_ACK);
               chatAckPacket.write(Types.PLAYER_MESSAGE_SIGNATURE_ARRAY, messagesStorage.lastSignatures());
               chatAckPacket.write(Types.OPTIONAL_PLAYER_MESSAGE_SIGNATURE, (Object)null);
               chatAckPacket.sendToServer(Protocol1_19_1To1_19.class);
            }
         }

         String plainMessage = (String)wrapper.read(Types.STRING);
         JsonElement message = null;
         JsonElement decoratedMessage = (JsonElement)wrapper.read(Types.OPTIONAL_COMPONENT);
         if (decoratedMessage != null) {
            message = decoratedMessage;
         }

         wrapper.read(Types.LONG);
         wrapper.read(Types.LONG);
         wrapper.read(Types.PLAYER_MESSAGE_SIGNATURE_ARRAY);
         JsonElement unsignedMessage = (JsonElement)wrapper.read(Types.OPTIONAL_COMPONENT);
         if (unsignedMessage != null) {
            message = unsignedMessage;
         }

         if (message == null) {
            message = ComponentUtil.plainToJson(plainMessage);
         }

         int filterMaskType = (Integer)wrapper.read(Types.VAR_INT);
         if (filterMaskType == 2) {
            wrapper.read(Types.LONG_ARRAY_PRIMITIVE);
         }

         int chatTypeId = (Integer)wrapper.read(Types.VAR_INT);
         JsonElement senderName = (JsonElement)wrapper.read(Types.COMPONENT);
         JsonElement targetName = (JsonElement)wrapper.read(Types.OPTIONAL_COMPONENT);
         decoratedMessage = decorateChatMessage(this, (ChatRegistryStorage)wrapper.user().get(ChatRegistryStorage1_19_1.class), chatTypeId, senderName, targetName, message);
         if (decoratedMessage == null) {
            wrapper.cancel();
         } else {
            this.translatableRewriter.processText(wrapper.user(), decoratedMessage);
            wrapper.write(Types.COMPONENT, decoratedMessage);
            wrapper.write(Types.VAR_INT, 1);
         }
      });
      this.registerClientbound(ClientboundPackets1_19_1.SYSTEM_CHAT, (wrapper) -> {
         JsonElement content = (JsonElement)wrapper.passthrough(Types.COMPONENT);
         this.translatableRewriter.processText(wrapper.user(), content);
         boolean overlay = (Boolean)wrapper.read(Types.BOOLEAN);
         wrapper.write(Types.VAR_INT, overlay ? 2 : 1);
      });
      this.registerServerbound(ServerboundPackets1_19.CHAT, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.map(Types.LONG);
            this.map(Types.LONG);
            this.read(Types.BYTE_ARRAY_PRIMITIVE);
            this.read(Types.BOOLEAN);
            this.handler((wrapper) -> {
               ChatSession1_19_1 chatSession = (ChatSession1_19_1)wrapper.user().get(ChatSession1_19_1.class);
               ReceivedMessagesStorage messagesStorage = (ReceivedMessagesStorage)wrapper.user().get(ReceivedMessagesStorage.class);
               if (chatSession != null) {
                  UUID sender = wrapper.user().getProtocolInfo().getUuid();
                  String message = (String)wrapper.get(Types.STRING, 0);
                  long timestamp = (Long)wrapper.get(Types.LONG, 0);
                  long salt = (Long)wrapper.get(Types.LONG, 1);
                  MessageMetadata metadata = new MessageMetadata(sender, timestamp, salt);
                  DecoratableMessage decoratableMessage = new DecoratableMessage(message);

                  byte[] signature;
                  try {
                     signature = chatSession.signChatMessage(metadata, decoratableMessage, messagesStorage.lastSignatures());
                  } catch (SignatureException e) {
                     throw new RuntimeException(e);
                  }

                  wrapper.write(Types.BYTE_ARRAY_PRIMITIVE, signature);
                  wrapper.write(Types.BOOLEAN, decoratableMessage.isDecorated());
               } else {
                  wrapper.write(Types.BYTE_ARRAY_PRIMITIVE, Protocol1_19_1To1_19.EMPTY_BYTES);
                  wrapper.write(Types.BOOLEAN, false);
               }

               messagesStorage.resetUnacknowledgedCount();
               wrapper.write(Types.PLAYER_MESSAGE_SIGNATURE_ARRAY, messagesStorage.lastSignatures());
               wrapper.write(Types.OPTIONAL_PLAYER_MESSAGE_SIGNATURE, (Object)null);
            });
         }
      });
      this.registerServerbound(ServerboundPackets1_19.CHAT_COMMAND, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.map(Types.LONG);
            this.map(Types.LONG);
            this.handler((wrapper) -> {
               ReceivedMessagesStorage messagesStorage = (ReceivedMessagesStorage)wrapper.user().get(ReceivedMessagesStorage.class);
               ChatSession1_19_1 chatSession = (ChatSession1_19_1)wrapper.user().get(ChatSession1_19_1.class);
               SignableCommandArgumentsProvider argumentsProvider = (SignableCommandArgumentsProvider)Via.getManager().getProviders().get(SignableCommandArgumentsProvider.class);
               if (chatSession != null && argumentsProvider != null) {
                  int signatures = (Integer)wrapper.read(Types.VAR_INT);

                  for(int i = 0; i < signatures; ++i) {
                     wrapper.read(Types.STRING);
                     wrapper.read(Types.BYTE_ARRAY_PRIMITIVE);
                  }

                  UUID sender = wrapper.user().getProtocolInfo().getUuid();
                  String command = (String)wrapper.get(Types.STRING, 0);
                  long timestamp = (Long)wrapper.get(Types.LONG, 0);
                  long salt = (Long)wrapper.get(Types.LONG, 1);
                  MessageMetadata metadata = new MessageMetadata(sender, timestamp, salt);
                  List<Pair<String, String>> arguments = argumentsProvider.getSignableArguments(command);
                  wrapper.write(Types.VAR_INT, arguments.size());

                  for(Pair argument : arguments) {
                     byte[] signature;
                     try {
                        signature = chatSession.signChatMessage(metadata, new DecoratableMessage((String)argument.value()), messagesStorage.lastSignatures());
                     } catch (SignatureException e) {
                        throw new RuntimeException(e);
                     }

                     wrapper.write(Types.STRING, (String)argument.key());
                     wrapper.write(Types.BYTE_ARRAY_PRIMITIVE, signature);
                  }
               } else {
                  int signatures = (Integer)wrapper.passthrough(Types.VAR_INT);

                  for(int i = 0; i < signatures; ++i) {
                     wrapper.passthrough(Types.STRING);
                     wrapper.read(Types.BYTE_ARRAY_PRIMITIVE);
                     wrapper.write(Types.BYTE_ARRAY_PRIMITIVE, Protocol1_19_1To1_19.EMPTY_BYTES);
                  }
               }

               wrapper.passthrough(Types.BOOLEAN);
               messagesStorage.resetUnacknowledgedCount();
               wrapper.write(Types.PLAYER_MESSAGE_SIGNATURE_ARRAY, messagesStorage.lastSignatures());
               wrapper.write(Types.OPTIONAL_PLAYER_MESSAGE_SIGNATURE, (Object)null);
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_19_1.SERVER_DATA, new PacketHandlers() {
         public void register() {
            this.map(Types.OPTIONAL_COMPONENT);
            this.map(Types.OPTIONAL_STRING);
            this.map(Types.BOOLEAN);
            this.read(Types.BOOLEAN);
         }
      });
      this.registerServerbound(State.LOGIN, ServerboundLoginPackets.HELLO, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.handler((wrapper) -> {
               ProfileKey profileKey = (ProfileKey)wrapper.read(Types.OPTIONAL_PROFILE_KEY);
               ChatSession1_19_1 chatSession = (ChatSession1_19_1)wrapper.user().get(ChatSession1_19_1.class);
               wrapper.write(Types.OPTIONAL_PROFILE_KEY, chatSession == null ? null : chatSession.getProfileKey());
               wrapper.write(Types.OPTIONAL_UUID, chatSession == null ? null : chatSession.getUuid());
               if (profileKey == null || chatSession != null) {
                  wrapper.user().put(new NonceStorage((byte[])null));
               }

            });
         }
      });
      this.registerClientbound(State.LOGIN, ClientboundLoginPackets.HELLO, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.handler((wrapper) -> {
               if (!wrapper.user().has(NonceStorage.class)) {
                  byte[] publicKey = (byte[])wrapper.passthrough(Types.BYTE_ARRAY_PRIMITIVE);
                  byte[] nonce = (byte[])wrapper.passthrough(Types.BYTE_ARRAY_PRIMITIVE);
                  wrapper.user().put(new NonceStorage(CipherUtil.encryptNonce(publicKey, nonce)));
               }
            });
         }
      });
      this.registerServerbound(State.LOGIN, ServerboundLoginPackets.ENCRYPTION_KEY, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE_ARRAY_PRIMITIVE);
            this.handler((wrapper) -> {
               NonceStorage nonceStorage = (NonceStorage)wrapper.user().remove(NonceStorage.class);
               if (nonceStorage.nonce() != null) {
                  boolean isNonce = (Boolean)wrapper.read(Types.BOOLEAN);
                  wrapper.write(Types.BOOLEAN, true);
                  if (!isNonce) {
                     wrapper.read(Types.LONG);
                     wrapper.read(Types.BYTE_ARRAY_PRIMITIVE);
                     wrapper.write(Types.BYTE_ARRAY_PRIMITIVE, nonceStorage.nonce());
                  }

               }
            });
         }
      });
      this.registerClientbound(State.LOGIN, ClientboundLoginPackets.CUSTOM_QUERY, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.STRING);
            this.handler((wrapper) -> {
               String identifier = (String)wrapper.get(Types.STRING, 0);
               if (identifier.equals("velocity:player_info")) {
                  byte[] data = (byte[])wrapper.passthrough(Types.REMAINING_BYTES);
                  if (data.length == 1 && data[0] > 1) {
                     data[0] = 1;
                  } else if (data.length == 0) {
                     data = new byte[]{1};
                     wrapper.set(Types.REMAINING_BYTES, 0, data);
                  } else {
                     ProtocolLogger var10000 = Protocol1_19_1To1_19.this.getLogger();
                     int var5 = data.length;
                     var10000.warning("Received unexpected data in velocity:player_info (length=" + var5 + ")");
                  }
               }

            });
         }
      });
      this.cancelClientbound(ClientboundPackets1_19_1.CUSTOM_CHAT_COMPLETIONS);
      this.cancelClientbound(ClientboundPackets1_19_1.DELETE_CHAT);
      this.cancelClientbound(ClientboundPackets1_19_1.PLAYER_CHAT_HEADER);
   }

   public void init(UserConnection user) {
      user.put(new ChatRegistryStorage1_19_1());
      user.put(new ReceivedMessagesStorage());
      this.addEntityTracker(user, new EntityTrackerBase(user, EntityTypes1_19.PLAYER));
   }

   public TranslatableRewriter getComponentRewriter() {
      return this.translatableRewriter;
   }

   public EntityPacketRewriter1_19_1 getEntityRewriter() {
      return this.entityRewriter;
   }

   public static @Nullable JsonElement decorateChatMessage(Protocol protocol, ChatRegistryStorage chatRegistryStorage, int chatTypeId, JsonElement senderName, @Nullable JsonElement targetName, JsonElement message) {
      CompoundTag chatType = chatRegistryStorage.chatType(chatTypeId);
      if (chatType == null) {
         protocol.getLogger().warning("Chat message has unknown chat type id " + chatTypeId + ". Message: " + message);
         return null;
      } else {
         chatType = chatType.getCompoundTag("element").getCompoundTag("chat");
         return chatType == null ? null : Protocol1_19To1_19_1.translatabaleComponentFromTag(chatType, senderName, targetName, message);
      }
   }
}
