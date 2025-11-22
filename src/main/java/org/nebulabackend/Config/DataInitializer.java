package org.nebulabackend.Config;

import org.nebulabackend.Model.Role;
import org.nebulabackend.Model.User;
import org.nebulabackend.Repository.RoleRepository;
import org.nebulabackend.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;
import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder encoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {

        /* 🚨 CÓDIGO TEMPORAL DE DEPURACIÓN COMENTADO 🚨
           Ya obtuviste el hash. Comentamos esta línea para limpiar la consola.
        String cleanHash = encoder.encode("admin123");
        System.out.println("------------------------------------");
        System.out.println("👉 HASH GENERADO PARA ADMIN123: " + cleanHash);
        System.out.println("------------------------------------");
        */

        System.out.println("--- Inicializando Roles y Usuario Admin ---");

        // 1. CREACIÓN DE ROLES BASE
        insertRoleIfNotFound("ROLE_ADMIN");
        insertRoleIfNotFound("ROLE_USER");
        insertRoleIfNotFound("ROLE_MODERATOR");

        // 2. CREACIÓN DEL USUARIO ADMINISTRADOR POR DEFECTO
        /* 🚫 BLOQUE DE CREACIÓN DE USUARIO COMENTADO 🚫
           Desactivamos la creación para que no interfiera con el hash que insertaste manualmente en la DB.
        if (userRepository.findByUsername("admin").isEmpty()) {

            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Error: ROLE_ADMIN no encontrado."));

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);

            User admin = new User(
                    "admin",
                    "admin@nebula.com",
                    this.encoder.encode("admin123"),
                    roles
            );
            userRepository.save(admin);
            System.out.println(">>> Usuario 'admin' inicial creado con éxito!");
        } else {
            System.out.println(">>> Usuario 'admin' ya existe. Inicialización omitida.");
        }
        */
        System.out.println("------------------------------------------");
    }

    private void insertRoleIfNotFound(String roleName) {
        Optional<Role> existingRole = roleRepository.findByName(roleName);
        if (existingRole.isEmpty()) {
            roleRepository.save(new Role(null, roleName));
            System.out.println(">>> Rol '" + roleName + "' creado.");
        }
    }
}