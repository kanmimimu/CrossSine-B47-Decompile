package net.raphimc.vialegacy.api.splitter;

import java.util.function.BiConsumer;

public interface PreNettyPacketType {
   BiConsumer getPacketReader();
}
