// MyReader.java
package org.stroyco.appsone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.stroyco.appsone.Factory.OperationFactory;
import org.stroyco.appsone.Strategy.Operation;

public class MyReader implements Reader {

    @Override
    public void readDb() {
        if (BddMethod.db_url == null || BddMethod.db_url.isEmpty() || BddMethod.db_user == null
                || BddMethod.db_user.isEmpty() || BddMethod.db_password == null
                || BddMethod.db_password.isEmpty()) {
            System.err.println("Database connection information not found");
            System.exit(1);
        }

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            Connection connection = DriverManager.getConnection(BddMethod.db_url, BddMethod.db_user,
                    BddMethod.db_password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ID, NOM FROM FICHIER WHERE TYPE = 'OP'");
            List<Integer> lines = new ArrayList<>();
            List<String> fileNamesList = new ArrayList<>();
            while (resultSet.next()) {
                lines.add(resultSet.getInt("ID"));
                fileNamesList.add(resultSet.getString("NOM"));
            }

            for (Integer id : lines) {
                ResultSet resultSet2 = statement.executeQuery("SELECT * FROM LIGNE WHERE FICHIER_ID = " + id);
                while (resultSet2.next()) {
                    System.out.println("PARAM1: " + resultSet2.getString("PARAM1"));
                    System.out.println("OPERATOR: " + resultSet2.getString("OPERATEUR"));
                    System.out.println("PARAM2: " + resultSet2.getString("PARAM2"));

                    int number1 = resultSet2.getInt("PARAM1");
                    String operator = resultSet2.getString("OPERATEUR");
                    int number2 = resultSet2.getInt("PARAM2");

                    Operation operation = OperationFactory.getOperation(operator);
                    if (operation != null) {
                        float result = operation.perform(number1, number2);
                        App.writeResultsToFile(id, result); // Appel de la méthode depuis la classe App
                    } else {
                        System.err.println("Invalid operator: " + operator);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readFolder() {
        String filePath = ConfigReader.getProperties("filePath");

        if (filePath == null || filePath.isEmpty()) {
            System.err.println("File path not found");
            System.exit(1);
        }

        Path directory = Paths.get(filePath);

        if (!Files.isDirectory(directory)) {
            System.err.println("Directory not found");
            System.exit(1);
        }

        try {
            Files.walk(directory)
                    .filter(Files::isRegularFile)
                    .filter(file -> file.getFileName().toString().endsWith(".op"))
                    .forEach(Folder::processFile);
            System.out.println("Operation(s) completed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
