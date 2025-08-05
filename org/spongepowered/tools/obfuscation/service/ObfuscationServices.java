package org.spongepowered.tools.obfuscation.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.tools.obfuscation.ObfuscationType;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;

public final class ObfuscationServices {
   private static ObfuscationServices instance;
   private final ServiceLoader serviceLoader = ServiceLoader.load(IObfuscationService.class, this.getClass().getClassLoader());
   private final Set services = new HashSet();

   private ObfuscationServices() {
   }

   public static ObfuscationServices getInstance() {
      if (instance == null) {
         instance = new ObfuscationServices();
      }

      return instance;
   }

   public void initProviders(IMixinAnnotationProcessor ap) {
      try {
         for(IObfuscationService service : this.serviceLoader) {
            if (!this.services.contains(service)) {
               this.services.add(service);
               String serviceName = service.getClass().getSimpleName();
               Collection<ObfuscationTypeDescriptor> obfTypes = service.getObfuscationTypes();
               if (obfTypes != null) {
                  for(ObfuscationTypeDescriptor obfType : obfTypes) {
                     try {
                        ObfuscationType type = ObfuscationType.create(obfType, ap);
                        ap.printMessage(Kind.NOTE, serviceName + " supports type: \"" + type + "\"");
                     } catch (Exception ex) {
                        ex.printStackTrace();
                     }
                  }
               }
            }
         }
      } catch (ServiceConfigurationError serviceError) {
         ap.printMessage(Kind.ERROR, serviceError.getClass().getSimpleName() + ": " + serviceError.getMessage());
         serviceError.printStackTrace();
      }

   }

   public Set getSupportedOptions() {
      Set<String> supportedOptions = new HashSet();

      for(IObfuscationService provider : this.serviceLoader) {
         Set<String> options = provider.getSupportedOptions();
         if (options != null) {
            supportedOptions.addAll(options);
         }
      }

      return supportedOptions;
   }

   public IObfuscationService getService(Class serviceClass) {
      for(IObfuscationService service : this.serviceLoader) {
         if (serviceClass.getName().equals(service.getClass().getName())) {
            return service;
         }
      }

      return null;
   }
}
