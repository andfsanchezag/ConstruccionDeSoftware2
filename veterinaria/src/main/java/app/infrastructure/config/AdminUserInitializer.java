package app.infrastructure.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import app.infrastructure.persistence.entities.UserEntity;
import app.infrastructure.persistence.repository.UserRepository;

/**
 * Inicializa el usuario administrador por defecto al arrancar la aplicación.
 * Este usuario se crea solo si no existe en la base de datos.
 */
@Component
public class AdminUserInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AdminUserInitializer.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existe el usuario admin
        UserEntity existingAdmin = userRepository.findByUserName("admin");
        
        if (existingAdmin == null) {
            // Crear el usuario admin por defecto
            UserEntity adminUser = new UserEntity();
            adminUser.setName("Administrador");
            adminUser.setDocument(1234567890L);
            adminUser.setAge(30);
            adminUser.setUserName("admin");
            adminUser.setPassword(passwordEncoder.encode("admin"));
            adminUser.setRole("ADMIN");

            userRepository.save(adminUser);
            
            logger.info("✅ Usuario administrador creado exitosamente");
            logger.info("   Username: admin");
            logger.info("   Password: admin");
            logger.info("   Role: ADMIN");
        } else {
            logger.info("ℹ️  Usuario administrador ya existe en la base de datos");
        }
    }
}
