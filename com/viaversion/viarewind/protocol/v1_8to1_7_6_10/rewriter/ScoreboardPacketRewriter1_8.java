package com.viaversion.viarewind.protocol.v1_8to1_7_6_10.rewriter;

import com.viaversion.viarewind.protocol.v1_7_6_10to1_7_2_5.packet.ClientboundPackets1_7_2_5;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.Protocol1_8To1_7_6_10;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.storage.ScoreboardTracker;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import com.viaversion.viaversion.util.ChatColorUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ScoreboardPacketRewriter1_8 extends RewriterBase {
   public ScoreboardPacketRewriter1_8(Protocol1_8To1_7_6_10 protocol) {
      super(protocol);
   }

   protected void registerPackets() {
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.SET_OBJECTIVE, (wrapper) -> {
         String name = (String)wrapper.passthrough(Types.STRING);
         if (name.length() > 16) {
            wrapper.set(Types.STRING, 0, name = name.substring(0, 16));
         }

         byte mode = (Byte)wrapper.read(Types.BYTE);
         ScoreboardTracker scoreboard = (ScoreboardTracker)wrapper.user().get(ScoreboardTracker.class);
         if (mode == 0) {
            if (scoreboard.objectiveExists(name)) {
               wrapper.cancel();
               return;
            }

            scoreboard.addObjective(name);
         } else if (mode == 1) {
            if (!scoreboard.objectiveExists(name)) {
               wrapper.cancel();
               return;
            }

            if (scoreboard.getColorIndependentSidebar() != null) {
               String username = wrapper.user().getProtocolInfo().getUsername();
               Optional<Byte> color = scoreboard.getPlayerTeamColor(username);
               if (color.isPresent()) {
                  String sidebar = (String)scoreboard.getColorDependentSidebar().get(color.get());
                  if (name.equals(sidebar)) {
                     PacketWrapper sidebarPacket = PacketWrapper.create(ClientboundPackets1_7_2_5.SET_DISPLAY_OBJECTIVE, (UserConnection)wrapper.user());
                     sidebarPacket.write(Types.BYTE, (byte)1);
                     sidebarPacket.write(Types.STRING, scoreboard.getColorIndependentSidebar());
                     sidebarPacket.scheduleSend(Protocol1_8To1_7_6_10.class);
                  }
               }
            }

            scoreboard.removeObjective(name);
         } else if (mode == 2 && !scoreboard.objectiveExists(name)) {
            wrapper.cancel();
            return;
         }

         if (mode != 0 && mode != 2) {
            wrapper.write(Types.STRING, "");
         } else {
            String displayName = (String)wrapper.passthrough(Types.STRING);
            if (displayName.length() > 32) {
               wrapper.set(Types.STRING, 1, displayName.substring(0, 32));
            }

            wrapper.read(Types.STRING);
         }

         wrapper.write(Types.BYTE, mode);
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.SET_SCORE, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.map(Types.VAR_INT, Types.BYTE);
            this.handler((wrapper) -> {
               ScoreboardTracker scoreboard = (ScoreboardTracker)wrapper.user().get(ScoreboardTracker.class);
               String name = (String)wrapper.get(Types.STRING, 0);
               byte mode = (Byte)wrapper.get(Types.BYTE, 0);
               if (mode == 1) {
                  name = scoreboard.removeTeamForScore(name);
               } else {
                  name = scoreboard.sendTeamForScore(name);
               }

               if (name.length() > 16) {
                  name = ChatColorUtil.stripColor(name);
                  if (name.length() > 16) {
                     name = name.substring(0, 16);
                  }
               }

               wrapper.set(Types.STRING, 0, name);
               String objective = (String)wrapper.read(Types.STRING);
               if (objective.length() > 16) {
                  objective = objective.substring(0, 16);
               }

               if (mode != 1) {
                  int score = (Integer)wrapper.read(Types.VAR_INT);
                  wrapper.write(Types.STRING, objective);
                  wrapper.write(Types.INT, score);
               }

            });
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.SET_DISPLAY_OBJECTIVE, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE);
            this.map(Types.STRING);
            this.handler((wrapper) -> {
               byte position = (Byte)wrapper.get(Types.BYTE, 0);
               String name = (String)wrapper.get(Types.STRING, 0);
               ScoreboardTracker scoreboard = (ScoreboardTracker)wrapper.user().get(ScoreboardTracker.class);
               if (position > 2) {
                  byte receiverTeamColor = (byte)(position - 3);
                  scoreboard.getColorDependentSidebar().put(receiverTeamColor, name);
                  String username = wrapper.user().getProtocolInfo().getUsername();
                  Optional<Byte> color = scoreboard.getPlayerTeamColor(username);
                  if (color.isPresent() && (Byte)color.get() == receiverTeamColor) {
                     position = 1;
                  } else {
                     position = -1;
                  }
               } else if (position == 1) {
                  scoreboard.setColorIndependentSidebar(name);
                  String username = wrapper.user().getProtocolInfo().getUsername();
                  Optional<Byte> color = scoreboard.getPlayerTeamColor(username);
                  if (color.isPresent() && scoreboard.getColorDependentSidebar().containsKey(color.get())) {
                     position = -1;
                  }
               }

               if (position == -1) {
                  wrapper.cancel();
               } else {
                  wrapper.set(Types.BYTE, 0, position);
               }
            });
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.SET_PLAYER_TEAM, (wrapper) -> {
         String team = (String)wrapper.passthrough(Types.STRING);
         if (team == null) {
            wrapper.cancel();
         } else {
            ScoreboardTracker scoreboard = (ScoreboardTracker)wrapper.user().get(ScoreboardTracker.class);
            byte mode = (Byte)wrapper.passthrough(Types.BYTE);
            if (mode != 0 && !scoreboard.teamExists(team)) {
               wrapper.cancel();
            } else {
               if (mode == 0 && scoreboard.teamExists(team)) {
                  scoreboard.removeTeam(team);
                  PacketWrapper removeTeam = PacketWrapper.create(ClientboundPackets1_7_2_5.SET_PLAYER_TEAM, (UserConnection)wrapper.user());
                  removeTeam.write(Types.STRING, team);
                  removeTeam.write(Types.BYTE, (byte)1);
                  removeTeam.send(Protocol1_8To1_7_6_10.class);
               }

               if (mode == 0) {
                  scoreboard.addTeam(team);
               } else if (mode == 1) {
                  scoreboard.removeTeam(team);
               }

               if (mode == 0 || mode == 2) {
                  wrapper.passthrough(Types.STRING);
                  wrapper.passthrough(Types.STRING);
                  wrapper.passthrough(Types.STRING);
                  wrapper.passthrough(Types.BYTE);
                  wrapper.read(Types.STRING);
                  byte color = (Byte)wrapper.read(Types.BYTE);
                  if (mode == 2 && (Byte)scoreboard.getTeamColor(team).get() != color) {
                     String sidebar = (String)scoreboard.getColorDependentSidebar().get(color);
                     PacketWrapper sidebarPacket = PacketWrapper.create(ClientboundPackets1_7_2_5.SET_DISPLAY_OBJECTIVE, (UserConnection)wrapper.user());
                     sidebarPacket.write(Types.BYTE, (byte)1);
                     sidebarPacket.write(Types.STRING, sidebar == null ? "" : sidebar);
                     sidebarPacket.scheduleSend(Protocol1_8To1_7_6_10.class);
                  }

                  scoreboard.setTeamColor(team, color);
               }

               if (mode == 0 || mode == 3 || mode == 4) {
                  byte color = (Byte)scoreboard.getTeamColor(team).get();
                  String[] entries = (String[])wrapper.read(Types.STRING_ARRAY);
                  List<String> entryList = new ArrayList();

                  for(String entry : entries) {
                     String username = wrapper.user().getProtocolInfo().getUsername();
                     if (mode == 4) {
                        if (!scoreboard.isPlayerInTeam(entry, team)) {
                           continue;
                        }

                        scoreboard.removePlayerFromTeam(entry, team);
                        if (entry.equals(username)) {
                           PacketWrapper sidebarPacket = PacketWrapper.create(ClientboundPackets1_7_2_5.SET_DISPLAY_OBJECTIVE, (UserConnection)wrapper.user());
                           sidebarPacket.write(Types.BYTE, (byte)1);
                           sidebarPacket.write(Types.STRING, scoreboard.getColorIndependentSidebar() == null ? "" : scoreboard.getColorIndependentSidebar());
                           sidebarPacket.scheduleSend(Protocol1_8To1_7_6_10.class);
                        }
                     } else {
                        scoreboard.addPlayerToTeam(entry, team);
                        if (entry.equals(username) && scoreboard.getColorDependentSidebar().containsKey(color)) {
                           PacketWrapper displayObjective = PacketWrapper.create(ClientboundPackets1_7_2_5.SET_DISPLAY_OBJECTIVE, (UserConnection)wrapper.user());
                           displayObjective.write(Types.BYTE, (byte)1);
                           displayObjective.write(Types.STRING, (String)scoreboard.getColorDependentSidebar().get(color));
                           displayObjective.scheduleSend(Protocol1_8To1_7_6_10.class);
                        }
                     }

                     entryList.add(entry);
                  }

                  wrapper.write(Types.SHORT, (short)entryList.size());

                  for(String entry : entryList) {
                     wrapper.write(Types.STRING, entry);
                  }
               }

            }
         }
      });
   }
}
