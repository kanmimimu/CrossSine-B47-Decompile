package com.viaversion.viaversion.platform;

import io.netty.channel.ChannelInitializer;

public interface WrappedChannelInitializer {
   ChannelInitializer original();
}
