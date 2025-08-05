package com.viaversion.viabackwards.protocol.v1_19_3to1_19_1;

import com.google.common.base.Preconditions;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.v1_19_1to1_19.Protocol1_19_1To1_19;
import com.viaversion.viabackwards.protocol.v1_19_1to1_19.storage.ChatRegistryStorage;
import com.viaversion.viabackwards.protocol.v1_19_3to1_19_1.data.BackwardsMappingData1_19_3;
import com.viaversion.viabackwards.protocol.v1_19_3to1_19_1.rewriter.BlockItemPacketRewriter1_19_3;
import com.viaversion.viabackwards.protocol.v1_19_3to1_19_1.rewriter.EntityPacketRewriter1_19_3;
import com.viaversion.viabackwards.protocol.v1_19_3to1_19_1.storage.ChatSessionStorage;
import com.viaversion.viabackwards.protocol.v1_19_3to1_19_1.storage.ChatTypeStorage1_19_3;
import com.viaversion.viabackwards.protocol.v1_19_3to1_19_1.storage.NonceStorage;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Holder;
import com.viaversion.viaversion.api.minecraft.PlayerMessageSignature;
import com.viaversion.viaversion.api.minecraft.ProfileKey;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.SoundEvent;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19_3;
import com.viaversion.viaversion.api.minecraft.signature.SignableCommandArgumentsProvider;
import com.viaversion.viaversion.api.minecraft.signature.model.MessageMetadata;
import com.viaversion.viaversion.api.minecraft.signature.storage.ChatSession1_19_3;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.BitSetType;
import com.viaversion.viaversion.api.type.types.ByteArrayType;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
import com.viaversion.viaversion.protocols.v1_19_1to1_19_3.packet.ClientboundPackets1_19_3;
import com.viaversion.viaversion.protocols.v1_19_1to1_19_3.packet.ServerboundPackets1_19_3;
import com.viaversion.viaversion.protocols.v1_19to1_19_1.packet.ClientboundPackets1_19_1;
import com.viaversion.viaversion.protocols.v1_19to1_19_1.packet.ServerboundPackets1_19_1;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.CipherUtil;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.Pair;
import java.security.SignatureException;
import java.util.BitSet;
import java.util.List;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class Protocol1_19_3To1_19_1 extends BackwardsProtocol {
   public static final BackwardsMappingData1_19_3 MAPPINGS = new BackwardsMappingData1_19_3();
   public static final ByteArrayType.OptionalByteArrayType OPTIONAL_SIGNATURE_BYTES_TYPE = new ByteArrayType.OptionalByteArrayType(256);
   public static final ByteArrayType SIGNATURE_BYTES_TYPE = new ByteArrayType(256);
   final EntityPacketRewriter1_19_3 entityRewriter = new EntityPacketRewriter1_19_3(this);
   final BlockItemPacketRewriter1_19_3 itemRewriter = new BlockItemPacketRewriter1_19_3(this);
   final TranslatableRewriter translatableRewriter;
   final TagRewriter tagRewriter;

   public Protocol1_19_3To1_19_1() {
      super(ClientboundPackets1_19_3.class, ClientboundPackets1_19_1.class, ServerboundPackets1_19_3.class, ServerboundPackets1_19_1.class);
      this.translatableRewriter = new TranslatableRewriter(this, ComponentRewriter.ReadType.JSON);
      this.tagRewriter = new TagRewriter(this);
   }

   protected void registerPackets() {
      super.registerPackets();
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_3.SYSTEM_CHAT);
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_3.SET_ACTION_BAR_TEXT);
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_3.SET_TITLE_TEXT);
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_3.SET_SUBTITLE_TEXT);
      this.translatableRewriter.registerBossEvent(ClientboundPackets1_19_3.BOSS_EVENT);
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_3.DISCONNECT);
      this.translatableRewriter.registerTabList(ClientboundPackets1_19_3.TAB_LIST);
      this.translatableRewriter.registerOpenScreen(ClientboundPackets1_19_3.OPEN_SCREEN);
      this.translatableRewriter.registerPlayerCombatKill(ClientboundPackets1_19_3.PLAYER_COMBAT_KILL);
      this.translatableRewriter.registerPing();
      SoundRewriter<ClientboundPackets1_19_3> soundRewriter = new SoundRewriter(this);
      soundRewriter.registerStopSound(ClientboundPackets1_19_3.STOP_SOUND);
      this.registerClientbound(ClientboundPackets1_19_3.SOUND, (wrapper) -> {
         String mappedIdentifier = this.rewriteSound(wrapper);
         if (mappedIdentifier != null) {
            wrapper.write(Types.STRING, mappedIdentifier);
            wrapper.setPacketType(ClientboundPackets1_19_1.CUSTOM_SOUND);
         }

      });
      this.registerClientbound(ClientboundPackets1_19_3.SOUND_ENTITY, (wrapper) -> {
         String mappedIdentifier = this.rewriteSound(wrapper);
         if (mappedIdentifier != null) {
            int mappedId = MAPPINGS.mappedSound(mappedIdentifier);
            if (mappedId == -1) {
               wrapper.cancel();
            } else {
               wrapper.write(Types.VAR_INT, mappedId);
            }
         }
      });
      this.tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:non_flammable_wood");
      this.tagRewriter.addEmptyTag(RegistryType.ITEM, "minecraft:overworld_natural_logs");
      this.tagRewriter.registerGeneric(ClientboundPackets1_19_3.UPDATE_TAGS);
      (new StatisticsRewriter(this)).register(ClientboundPackets1_19_3.AWARD_STATS);
      CommandRewriter<ClientboundPackets1_19_3> commandRewriter = new CommandRewriter(this);
      this.registerClientbound(ClientboundPackets1_19_3.COMMANDS, (wrapper) -> {
         int size = (Integer)wrapper.passthrough(Types.VAR_INT);

         for(int i = 0; i < size; ++i) {
            byte flags = (Byte)wrapper.passthrough(Types.BYTE);
            wrapper.passthrough(Types.VAR_INT_ARRAY_PRIMITIVE);
            if ((flags & 8) != 0) {
               wrapper.passthrough(Types.VAR_INT);
            }

            int nodeType = flags & 3;
            if (nodeType == 1 || nodeType == 2) {
               wrapper.passthrough(Types.STRING);
            }

            if (nodeType == 2) {
               int argumentTypeId = (Integer)wrapper.read(Types.VAR_INT);
               int mappedArgumentTypeId = MAPPINGS.getArgumentTypeMappings().getNewId(argumentTypeId);
               Preconditions.checkArgument(mappedArgumentTypeId != -1, "Unknown command argument type id: " + argumentTypeId);
               wrapper.write(Types.VAR_INT, mappedArgumentTypeId);
               String identifier = MAPPINGS.getArgumentTypeMappings().identifier(argumentTypeId);
               commandRewriter.handleArgument(wrapper, identifier);
               if (identifier.equals("minecraft:gamemode")) {
                  wrapper.write(Types.VAR_INT, 0);
               }

               if ((flags & 16) != 0) {
                  wrapper.passthrough(Types.STRING);
               }
            }
         }

         wrapper.passthrough(Types.VAR_INT);
      });
      this.registerClientbound(ClientboundPackets1_19_3.SERVER_DATA, new PacketHandlers() {
         public void register() {
            this.map(Types.OPTIONAL_COMPONENT);
            this.map(Types.OPTIONAL_STRING);
            this.create(Types.BOOLEAN, false);
         }
      });
      this.registerServerbound(State.LOGIN, ServerboundLoginPackets.HELLO, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.handler((wrapper) -> {
               ProfileKey profileKey = (ProfileKey)wrapper.read(Types.OPTIONAL_PROFILE_KEY);
               if (profileKey == null) {
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
               boolean isNonce = (Boolean)wrapper.read(Types.BOOLEAN);
               if (!isNonce) {
                  wrapper.read(Types.LONG);
                  wrapper.read(Types.BYTE_ARRAY_PRIMITIVE);
                  wrapper.write(Types.BYTE_ARRAY_PRIMITIVE, nonceStorage.nonce() != null ? nonceStorage.nonce() : new byte[0]);
               }

            });
         }
      });
      this.registerServerbound(ServerboundPackets1_19_1.CHAT, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.map(Types.LONG);
            this.map(Types.LONG);
            this.read(Types.BYTE_ARRAY_PRIMITIVE);
            this.read(Types.BOOLEAN);
            this.read(Types.PLAYER_MESSAGE_SIGNATURE_ARRAY);
            this.read(Types.OPTIONAL_PLAYER_MESSAGE_SIGNATURE);
            this.handler((wrapper) -> {
               ChatSession1_19_3 chatSession = (ChatSession1_19_3)wrapper.user().get(ChatSession1_19_3.class);
               if (chatSession != null) {
                  String message = (String)wrapper.get(Types.STRING, 0);
                  long timestamp = (Long)wrapper.get(Types.LONG, 0);
                  long salt = (Long)wrapper.get(Types.LONG, 1);
                  MessageMetadata metadata = new MessageMetadata((UUID)null, timestamp, salt);

                  byte[] signature;
                  try {
                     signature = chatSession.signChatMessage(metadata, message, new PlayerMessageSignature[0]);
                  } catch (SignatureException e) {
                     throw new RuntimeException(e);
                  }

                  wrapper.write(Protocol1_19_3To1_19_1.OPTIONAL_SIGNATURE_BYTES_TYPE, signature);
               } else {
                  wrapper.write(Protocol1_19_3To1_19_1.OPTIONAL_SIGNATURE_BYTES_TYPE, (Object)null);
               }

               wrapper.write(Types.VAR_INT, 0);
               wrapper.write(new BitSetType(20), new BitSet(20));
            });
         }
      });
      this.registerServerbound(ServerboundPackets1_19_1.CHAT_COMMAND, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.map(Types.LONG);
            this.map(Types.LONG);
            this.handler((wrapper) -> {
               ChatSession1_19_3 chatSession = (ChatSession1_19_3)wrapper.user().get(ChatSession1_19_3.class);
               SignableCommandArgumentsProvider argumentsProvider = (SignableCommandArgumentsProvider)Via.getManager().getProviders().get(SignableCommandArgumentsProvider.class);
               String command = (String)wrapper.get(Types.STRING, 0);
               long timestamp = (Long)wrapper.get(Types.LONG, 0);
               long salt = (Long)wrapper.get(Types.LONG, 1);
               int signatures = (Integer)wrapper.read(Types.VAR_INT);

               for(int i = 0; i < signatures; ++i) {
                  wrapper.read(Types.STRING);
                  wrapper.read(Types.BYTE_ARRAY_PRIMITIVE);
               }

               wrapper.read(Types.BOOLEAN);
               if (chatSession != null && argumentsProvider != null) {
                  MessageMetadata metadata = new MessageMetadata((UUID)null, timestamp, salt);
                  List<Pair<String, String>> arguments = argumentsProvider.getSignableArguments(command);
                  wrapper.write(Types.VAR_INT, arguments.size());

                  for(Pair argument : arguments) {
                     byte[] signature;
                     try {
                        signature = chatSession.signChatMessage(metadata, (String)argument.value(), new PlayerMessageSignature[0]);
                     } catch (SignatureException e) {
                        throw new RuntimeException(e);
                     }

                     wrapper.write(Types.STRING, (String)argument.key());
                     wrapper.write(Protocol1_19_3To1_19_1.SIGNATURE_BYTES_TYPE, signature);
                  }
               } else {
                  wrapper.write(Types.VAR_INT, 0);
               }

               int offset = 0;
               BitSet acknowledged = new BitSet(20);
               wrapper.write(Types.VAR_INT, 0);
               wrapper.write(new BitSetType(20), acknowledged);
            });
            this.read(Types.PLAYER_MESSAGE_SIGNATURE_ARRAY);
            this.read(Types.OPTIONAL_PLAYER_MESSAGE_SIGNATURE);
         }
      });
      this.registerClientbound(ClientboundPackets1_19_3.PLAYER_CHAT, ClientboundPackets1_19_1.SYSTEM_CHAT, new PacketHandlers() {
         public void register() {
            this.read(Types.UUID);
            this.read(Types.VAR_INT);
            this.read(Protocol1_19_3To1_19_1.OPTIONAL_SIGNATURE_BYTES_TYPE);
            this.handler((wrapper) -> {
               String plainContent = (String)wrapper.read(Types.STRING);
               wrapper.read(Types.LONG);
               wrapper.read(Types.LONG);
               int lastSeen = (Integer)wrapper.read(Types.VAR_INT);

               for(int i = 0; i < lastSeen; ++i) {
                  int index = (Integer)wrapper.read(Types.VAR_INT);
                  if (index == 0) {
                     wrapper.read(Protocol1_19_3To1_19_1.SIGNATURE_BYTES_TYPE);
                  }
               }

               JsonElement unsignedContent = (JsonElement)wrapper.read(Types.OPTIONAL_COMPONENT);
               JsonElement content = (JsonElement)(unsignedContent != null ? unsignedContent : ComponentUtil.plainToJson(plainContent));
               Protocol1_19_3To1_19_1.this.translatableRewriter.processText(wrapper.user(), content);
               int filterMaskType = (Integer)wrapper.read(Types.VAR_INT);
               if (filterMaskType == 2) {
                  wrapper.read(Types.LONG_ARRAY_PRIMITIVE);
               }

               int chatTypeId = (Integer)wrapper.read(Types.VAR_INT);
               JsonElement senderName = (JsonElement)wrapper.read(Types.COMPONENT);
               JsonElement targetName = (JsonElement)wrapper.read(Types.OPTIONAL_COMPONENT);
               JsonElement result = Protocol1_19_1To1_19.decorateChatMessage(Protocol1_19_3To1_19_1.this, (ChatRegistryStorage)wrapper.user().get(ChatTypeStorage1_19_3.class), chatTypeId, senderName, targetName, content);
               if (result == null) {
                  wrapper.cancel();
               } else {
                  wrapper.write(Types.COMPONENT, result);
                  wrapper.write(Types.BOOLEAN, false);
               }
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_19_3.DISGUISED_CHAT, ClientboundPackets1_19_1.SYSTEM_CHAT, (wrapper) -> {
         JsonElement content = (JsonElement)wrapper.read(Types.COMPONENT);
         this.translatableRewriter.processText(wrapper.user(), content);
         int chatTypeId = (Integer)wrapper.read(Types.VAR_INT);
         JsonElement senderName = (JsonElement)wrapper.read(Types.COMPONENT);
         JsonElement targetName = (JsonElement)wrapper.read(Types.OPTIONAL_COMPONENT);
         JsonElement result = Protocol1_19_1To1_19.decorateChatMessage(this, (ChatRegistryStorage)wrapper.user().get(ChatTypeStorage1_19_3.class), chatTypeId, senderName, targetName, content);
         if (result == null) {
            wrapper.cancel();
         } else {
            wrapper.write(Types.COMPONENT, result);
            wrapper.write(Types.BOOLEAN, false);
         }
      });
      this.cancelClientbound(ClientboundPackets1_19_3.UPDATE_ENABLED_FEATURES);
      this.cancelServerbound(ServerboundPackets1_19_1.CHAT_PREVIEW);
      this.cancelServerbound(ServerboundPackets1_19_1.CHAT_ACK);
   }

   @Nullable String rewriteSound(PacketWrapper wrapper) {
      Holder<SoundEvent> holder = (Holder)wrapper.read(Types.SOUND_EVENT);
      if (holder.hasId()) {
         int mappedId = MAPPINGS.getSoundMappings().getNewId(holder.id());
         if (mappedId == -1) {
            wrapper.cancel();
            return null;
         } else {
            wrapper.write(Types.VAR_INT, mappedId);
            return null;
         }
      } else {
         String soundIdentifier = ((SoundEvent)holder.value()).identifier();
         String mappedIdentifier = MAPPINGS.getMappedNamedSound(soundIdentifier);
         if (mappedIdentifier == null) {
            return soundIdentifier;
         } else if (mappedIdentifier.isEmpty()) {
            wrapper.cancel();
            return null;
         } else {
            return mappedIdentifier;
         }
      }
   }

   public void init(UserConnection user) {
      user.put(new ChatSessionStorage());
      user.put(new ChatTypeStorage1_19_3());
      this.addEntityTracker(user, new EntityTrackerBase(user, EntityTypes1_19_3.PLAYER));
   }

   public BackwardsMappingData1_19_3 getMappingData() {
      return MAPPINGS;
   }

   public TranslatableRewriter getComponentRewriter() {
      return this.translatableRewriter;
   }

   public BlockItemPacketRewriter1_19_3 getItemRewriter() {
      return this.itemRewriter;
   }

   public EntityPacketRewriter1_19_3 getEntityRewriter() {
      return this.entityRewriter;
   }

   public TagRewriter getTagRewriter() {
      return this.tagRewriter;
   }
}
