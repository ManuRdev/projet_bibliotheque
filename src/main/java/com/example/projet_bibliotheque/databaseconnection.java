package com.example.projet_bibliotheque;

import java.sql.Connection;
import java.sql.DriverManager;

public class databaseconnection {
    public Connection databaselink;

    public Connection getConnection(){
        String databaseName ="aichaexemple";
        String databaseUser ="root";
        String databasePassword ="tonedorette";
        String url = "jdbc:mysql://localhost:3306/" + databaseName;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaselink = DriverManager.getConnection(url, databaseUser, databasePassword);
        }catch ( Exception e){
            e.printStackTrace();
            e.getCause();
        }

        return databaselink;
    }
}
