package com.viaversion.viaversion.protocols.v1_20_3to1_20_5.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.ProfileKey;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ServerboundPackets1_20_3;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.Protocol1_20_3To1_20_5;
import java.util.BitSet;
import java.util.Objects;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class AcknowledgedMessagesStorage implements StorableObject {
   static final int MAX_HISTORY = 20;
   static final int MINIMUM_DELAYED_ACK_COUNT = 20;
   static final BitSet DUMMY_LAST_SEEN_MESSAGES = new BitSet();
   Boolean secureChatEnforced;
   ChatSession chatSession;
   BitSet lastSeenMessages = new BitSet();
   int delayedAckCount;

   public int updateFromMessage(int ackCount, BitSet lastSeenMessages) {
      int delayedAckCount = this.delayedAckCount;
      this.delayedAckCount = 0;
      this.lastSeenMessages = lastSeenMessages;
      return ackCount + delayedAckCount;
   }

   public int accumulateAckCount(int ackCount) {
      this.delayedAckCount += ackCount;
      int ackCountToForward = this.delayedAckCount - 20;
      if (ackCountToForward >= 20) {
         this.lastSeenMessages = DUMMY_LAST_SEEN_MESSAGES;
         this.delayedAckCount = 20;
         return ackCountToForward;
      } else {
         return 0;
      }
   }

   public BitSet createSpoofedAck() {
      return this.lastSeenMessages;
   }

   public void setSecureChatEnforced(boolean secureChatEnforced) {
      this.secureChatEnforced = secureChatEnforced;
   }

   public @Nullable Boolean secureChatEnforced() {
      return this.secureChatEnforced;
   }

   public boolean isSecureChatEnforced() {
      return this.secureChatEnforced == null || this.secureChatEnforced;
   }

   public void queueChatSession(UUID sessionId, ProfileKey profileKey) {
      this.chatSession = new ChatSession(sessionId, profileKey);
   }

   public void sendQueuedChatSession(PacketWrapper wrapper) {
      if (this.chatSession != null) {
         PacketWrapper chatSessionUpdate = wrapper.create(ServerboundPackets1_20_3.CHAT_SESSION_UPDATE);
         chatSessionUpdate.write(Types.UUID, this.chatSession.sessionId());
         chatSessionUpdate.write(Types.PROFILE_KEY, this.chatSession.profileKey());
         chatSessionUpdate.sendToServer(Protocol1_20_3To1_20_5.class);
         this.chatSession = null;
      }
   }

   public void clear() {
      this.lastSeenMessages = new BitSet();
      this.delayedAckCount = 0;
   }

   public boolean clearOnServerSwitch() {
      return false;
   }

   public static final class ChatSession {
      final UUID sessionId;
      final ProfileKey profileKey;

      public ChatSession(UUID sessionId, ProfileKey profileKey) {
         this.sessionId = sessionId;
         this.profileKey = profileKey;
      }

      public UUID sessionId() {
         return this.sessionId;
      }

      public ProfileKey profileKey() {
         return this.profileKey;
      }

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof ChatSession)) {
            return false;
         } else {
            ChatSession var2 = (ChatSession)var1;
            return Objects.equals(this.sessionId, var2.sessionId) && Objects.equals(this.profileKey, var2.profileKey);
         }
      }

      public int hashCode() {
         return (0 * 31 + Objects.hashCode(this.sessionId)) * 31 + Objects.hashCode(this.profileKey);
      }

      public String toString() {
         return String.format("%s[sessionId=%s, profileKey=%s]", this.getClass().getSimpleName(), Objects.toString(this.sessionId), Objects.toString(this.profileKey));
      }
   }
}
