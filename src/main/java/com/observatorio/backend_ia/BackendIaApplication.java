package com.observatorio.backend_ia;

import com.observatorio.backend_ia.model.User;
import com.observatorio.backend_ia.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients  // Para Feign
public class BackendIaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendIaApplication.class, args);
    }

    // Seed admin inicial
    @Bean
    CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin2026"));

                // Inicializa explícitamente si es necesario (aunque ya lo hace el = new HashSet<>())
                if (admin.getRoles() == null) {
                    admin.setRoles(new HashSet<>());
                }
                admin.getRoles().add("ADMIN");  // Ahora sí agrega

                userRepository.save(admin);
                System.out.println("Admin creado con rol ADMIN");
            }
        };
    }
}