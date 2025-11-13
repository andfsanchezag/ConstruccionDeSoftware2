-- Script de inicialización para la base de datos veterinary
-- Este script se ejecuta automáticamente cuando se crea el contenedor de MySQL por primera vez

USE veterinary;

-- Aquí puedes agregar datos iniciales si lo necesitas
-- Por ejemplo:

-- INSERT INTO users (name, document, age, user_name, password, role) VALUES
-- ('Admin', '1234567890', 30, 'admin', 'password_hash', 'ADMIN');

-- Mensaje de confirmación
SELECT 'Base de datos veterinary inicializada correctamente' AS mensaje;
