package org.stroyco.appsone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.stroyco.appsone.Factory.OperationFactory;
import org.stroyco.appsone.Strategy.Operation;


public class BddMethod {
  private static String db_url = ConfigReader.getProperties("databaseString");
  private static String db_user = ConfigReader.getProperties("databaseUser");
  private static String db_password = ConfigReader.getProperties("databasePassword");

  public static void main(String args[])
  {
    if (db_url == null || db_url.isEmpty() || db_user == null || db_user.isEmpty() || db_password == null || db_password.isEmpty()) {
        System.err.println("Database connection information not found");
        System.exit(1);
    }
    

    try {
      Class.forName("org.postgresql.Driver");
      } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }

    try {
      Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
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

      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

}

