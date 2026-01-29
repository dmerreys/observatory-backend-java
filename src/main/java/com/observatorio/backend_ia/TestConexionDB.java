package com.observatorio.backend_ia; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class TestConexionDB implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== PRUEBA DE CONEXIÓN A POSTGRESQL ===");
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("¡CONEXIÓN EXITOSA!");
            System.out.println("Base de datos: " + connection.getCatalog());
            System.out.println("Usuario: " + connection.getMetaData().getUserName());
            System.out.println("Versión PostgreSQL: " + connection.getMetaData().getDatabaseProductVersion());
        } catch (SQLException e) {
            System.err.println("ERROR AL CONECTAR A LA BASE DE DATOS:");
            System.err.println(e.getMessage());
            throw e;  // Para que veas el stack trace completo
        }
        System.out.println("=======================================");
    }
}