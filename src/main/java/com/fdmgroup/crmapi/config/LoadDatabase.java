package com.fdmgroup.crmapi.config;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fdmgroup.crmapi.models.Address;
import com.fdmgroup.crmapi.models.Agent;
import com.fdmgroup.crmapi.models.Buyer;
import com.fdmgroup.crmapi.models.Contact;
import com.fdmgroup.crmapi.models.Manager;
import com.fdmgroup.crmapi.models.Property;
import com.fdmgroup.crmapi.models.Seller;
import com.fdmgroup.crmapi.repositories.AgentRepository;
import com.fdmgroup.crmapi.repositories.ManagerRepository;

@Configuration
class LoadDatabase {

  private static final Logger log = Logger.getLogger(LoadDatabase.class.getName());

  @Bean
  CommandLineRunner initDatabase(ManagerRepository managerRepository, AgentRepository agentRepository) {

    Manager manager = new Manager("manager.example", "password1234", new Contact("Project", "Manager", "projectManager1@fdmgroup.com", "07595101202"));

    Agent agent = new Agent("philip.parkinson", "password1234", new Contact("Test5", "Agent", "testAgent@fdmgroup.com", "07595101202"), manager);

    // Buyer buyer = new Buyer(new Contact("isabel", "worley", "i.worley@fdmgroup.com", "0797101202"), BigDecimal.valueOf(250000));

    // Property property = new Property(0, new Address(0, "15 main street", "london", ""), agent, null, null, null, null, null)

    // Seller seller = new Seller(new Contact("kelston", "fu", "k.fu@fdmgroup.com", "0797101303"), [new Property("null", agent, null, null, null, null)]);

    return args -> {
      // log.info("Preloading Manager " + managerRepository.save(manager));
      log.info("Preloading Agent " + agentRepository.save(agent));
    };
  }
}