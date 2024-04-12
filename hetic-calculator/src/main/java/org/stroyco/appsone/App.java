package org.stroyco.appsone;

import java.io.*;
import java.nio.file.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.stroyco.appsone.Factory.OperationFactory;
import org.stroyco.appsone.Strategy.Operation;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Reader reader = (Reader) context.getBean("reader");

        String method = ConfigReader.getProperties("implementation");

        if (method.equals("MANUAL")) {
            int number1 = Integer.parseInt(ConfigReader.getProperties("number1"));
            String operator = ConfigReader.getProperties("operator");
            int number2 = Integer.parseInt(ConfigReader.getProperties("number2"));

            if (operator == null || operator.isEmpty()) {
                System.err.println("Operator not found");
                System.exit(1);
            }

            performOperation(number1, operator, number2);
        } else if (method.equals("FOLDER")) {
            reader.readFolder();
        } else if (method.equals("JDBC")) {
            reader.readDb();
        } else {
            printUsage();
        }
    }

    private static void performOperation(int number1, String operator, int number2) {
        Operation operation = OperationFactory.getOperation(operator);
        if (operation == null) {
            System.err.println("Invalid operator");
            System.exit(1);
        }
        float result = operation.perform(number1, number2);
        System.out.println("Result: " + result);
    }

    public static void writeResultsToFile(int fileId, float result) {
        String folderName = "assets/JDBC";
        String fileName = folderName + "/" + fileId + ".res";
        try {
            // Vérifier si le dossier existe, sinon le créer
            Path folderPath = Paths.get(folderName);
            if (!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
            }

            // Ouvrir le fichier en mode append
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName), StandardOpenOption.APPEND,
                    StandardOpenOption.CREATE)) {
                // Écrire le résultat dans une nouvelle ligne
                writer.write(Float.toString(result));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printUsage() {
        System.out.println("Please use a valid method to use the calculator");
        System.out.println("Usage: java Calculator <number1> <operator> <number2>");
        System.out.println("or");
        System.out.println("Usage: java Calculator <pathToDirectory>");
        System.out.println("Supported operators: +, -, * or x, /");
        System.out.println("If using * or / in cli, please add quotes around the operator");
    }
}
