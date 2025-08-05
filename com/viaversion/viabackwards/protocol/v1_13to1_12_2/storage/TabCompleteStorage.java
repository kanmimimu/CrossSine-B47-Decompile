package com.viaversion.viabackwards.protocol.v1_13to1_12_2.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TabCompleteStorage implements StorableObject {
   private final Map usernames = new HashMap();
   private final Set commands = new HashSet();
   private int lastId;
   private String lastRequest;
   private boolean lastAssumeCommand;

   public Map usernames() {
      return this.usernames;
   }

   public Set commands() {
      return this.commands;
   }

   public int lastId() {
      return this.lastId;
   }

   public void setLastId(int lastId) {
      this.lastId = lastId;
   }

   public String lastRequest() {
      return this.lastRequest;
   }

   public void setLastRequest(String lastRequest) {
      this.lastRequest = lastRequest;
   }

   public boolean isLastAssumeCommand() {
      return this.lastAssumeCommand;
   }

   public void setLastAssumeCommand(boolean lastAssumeCommand) {
      this.lastAssumeCommand = lastAssumeCommand;
   }
}
