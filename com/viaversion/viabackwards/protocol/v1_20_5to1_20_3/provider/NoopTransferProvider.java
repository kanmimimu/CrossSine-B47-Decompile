package com.viaversion.viabackwards.protocol.v1_20_5to1_20_3.provider;

import com.viaversion.viaversion.api.connection.UserConnection;

final class NoopTransferProvider implements TransferProvider {
   public void connectToServer(UserConnection connection, String host, int port) {
   }
}
