package com.parking.buddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

@SpringBootApplication
//@EnableSwagger2
//@EnableWebMvc
//@EnableScheduling

public class MyParkingBuddyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyParkingBuddyApplication.class, args);

//        String dbUrl = "jdbc:sqlite:database.db";
//
//        // Replace with the path
//        where you want to save the .sql file
//        String sqlFilePath = "database.sql";
//
//        try (
//                // Connect to the database
//                Connection connection = DriverManager.getConnection(dbUrl);
//                Statement statement = connection.createStatement();
//
//                // Open the .sql file for writing
//                BufferedWriter writer = new BufferedWriter(new FileWriter(sqlFilePath))
//        ) {
//            // Get a list of all tables in the database
//            ResultSet tablesResultSet = statement.executeQuery("SELECT name FROM sqlite_master WHERE type='table'");
//
//            while (tablesResultSet.next()) {
//                String tableName = tablesResultSet.getString(1);
//
//                // Write the CREATE TABLE statement to the .sql file
//                writer.write("CREATE TABLE " + tableName + " (");
//
//                // Get the column names for the current table
//                ResultSet columnsResultSet = statement.executeQuery("PRAGMA table_info(" + tableName + ")");
//                String columnNames = "";
//                while (columnsResultSet.next()) {
//                    if (!columnNames.isEmpty()) {
//                        columnNames += ", ";
//                    }
//                    columnNames += columnsResultSet.getString("name");
//                }
//                writer.write(columnNames + ");");
//                writer.newLine();
//
//                // Get the data for the current table
//                ResultSet dataResultSet = statement.executeQuery("SELECT * FROM " + tableName);
//                while (dataResultSet.next()) {
//                    // Build the INSERT statement for the current row
//                    String insertSql = "INSERT INTO " + tableName + " (" + columnNames + ") VALUES (";
//                    for (int i = 1; i <= dataResultSet.getMetaData().getColumnCount(); i++) {
//                        String columnValue = dataResultSet.getString(i);
//                        insertSql += "'" + columnValue + "'";
//                        if (i < dataResultSet.getMetaData().getColumnCount()) {
//                            insertSql += ", ";
//                        }
//                    }
//                    insertSql += ");";
//
//                    // Write the INSERT statement to the .sql file
//                    writer.write(insertSql);
//                    writer.newLine();
//                }
//            }
//        } catch (SQLException | IOException e) {
//            e.printStackTrace();
//        }




    }

}
