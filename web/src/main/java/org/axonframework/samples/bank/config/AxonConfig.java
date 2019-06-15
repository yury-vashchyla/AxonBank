package org.axonframework.samples.bank.config;

import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.config.SagaConfiguration;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.samples.bank.command.BankAccount;
import org.axonframework.samples.bank.command.BankAccountCommandHandler;
import org.axonframework.samples.bank.command.BankTransferManagementSaga;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

  @Autowired
  private AxonConfiguration axonConfiguration;
  @Autowired
  private EventBus eventBus;

  @Bean
  public BankAccountCommandHandler bankAccountCommandHandler() {
    return new BankAccountCommandHandler(axonConfiguration.repository(BankAccount.class), eventBus);
  }

  @Bean
  public SagaConfiguration bankTransferManagementSagaConfiguration() {
    return SagaConfiguration.trackingSagaManager(BankTransferManagementSaga.class);
  }

  @Autowired
  public void configure(@Qualifier("localSegment") SimpleCommandBus simpleCommandBus) {
    simpleCommandBus.registerDispatchInterceptor(new BeanValidationInterceptor<>());
  }
}
