package jdk.internal.dynalink;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public interface CallSiteDescriptor {
   int SCHEME = 0;
   int OPERATOR = 1;
   int NAME_OPERAND = 2;
   String TOKEN_DELIMITER = ":";
   String OPERATOR_DELIMITER = "|";

   int getNameTokenCount();

   String getNameToken(int var1);

   String getName();

   MethodType getMethodType();

   MethodHandles.Lookup getLookup();

   CallSiteDescriptor changeMethodType(MethodType var1);
}
