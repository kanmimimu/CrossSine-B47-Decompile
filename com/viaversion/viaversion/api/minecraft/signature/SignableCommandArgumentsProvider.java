package com.viaversion.viaversion.api.minecraft.signature;

import com.viaversion.viaversion.api.platform.providers.Provider;
import java.util.List;

public abstract class SignableCommandArgumentsProvider implements Provider {
   public abstract List getSignableArguments(String var1);
}
