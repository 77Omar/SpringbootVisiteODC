package org.sid.secservice;

import org.sid.secservice.sec.entities.AppRole;
import org.sid.secservice.sec.entities.AppUser;
import org.sid.secservice.sec.entities.Visiteur;
import org.sid.secservice.sec.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class SecServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecServiceApplication.class, args);
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner start(AccountService accountService){
        return args -> {
            accountService.AddNewRole(new AppRole(null, "USER"));
            accountService.AddNewRole(new AppRole(null, "ADMIN"));

            accountService.AddNewUser(new AppUser(null, "user1", "1234", new ArrayList<>()));
            accountService.AddNewUser(new AppUser(null, "admin", "1234", new ArrayList<>()));
            accountService.AddNewUser(new AppUser(null, "user2", "1234", new ArrayList<>()));
            accountService.AddNewUser(new AppUser(null, "user3", "1234", new ArrayList<>()));

            // affecter des roles o utilisateur
            accountService.addRoleToUser("user1", "USER");
            accountService.addRoleToUser("admin", "USER");
            accountService.addRoleToUser("admin", "ADMIN");
            accountService.addRoleToUser("user2", "USER");

        };
    }


}
