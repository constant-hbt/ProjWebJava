package controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
    private Connection con;
    public Conexao() throws Exception{
        String url = "jdbc:postgresql://localhost:5432/proj_web";
        Class.forName("org.postgresql.Driver");
        con = DriverManager.getConnection(url, "postgres", "root");
    }
    
    public Connection getConexao(){
        return con;
    }
}
