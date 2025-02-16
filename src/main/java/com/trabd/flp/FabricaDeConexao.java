package com.trabd.flp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class FabricaDeConexao {
    public static Connection getConexao() {
        try {
            final String url = "jdbc:mysql://localhost:3306/mercado";
            final String usuario = "root";
            final String senha = "filipe99";

            return DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
