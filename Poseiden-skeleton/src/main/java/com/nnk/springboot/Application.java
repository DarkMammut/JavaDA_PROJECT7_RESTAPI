package com.nnk.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    CommandLineRunner commandLineRunner(UserRepository userRepository) {
//        return args -> {
//            User admin = User.builder()
//                    .fullname("Administrator")
//                    .username("admin")
//                    .password(bCryptPasswordEncoder().encode("admin"))
//                    .roles(Set.of("ADMIN"))
//                    .build();
//
//            User user = User.builder()
//                    .fullname("User")
//                    .username("user")
//                    .password(bCryptPasswordEncoder().encode("user"))
//                    .roles(Set.of("USER"))
//                    .build();
//
//            userRepository.save(admin);
//            userRepository.save(user);
//        };
//    }
}
