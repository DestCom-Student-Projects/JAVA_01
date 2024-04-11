package org.stroyco.appsone;

import java.io.*;
import java.nio.file.*;
import java.util.stream.*;
import org.apache.commons.lang3.StringUtils;

import org.stroyco.appsone.Factory.OperationFactory;
import org.stroyco.appsone.Strategy.Operation;

public class App 
{
     public static void main(String[] args) {
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
                     .forEach(App::processFile);
                System.out.println("Operation(s) completed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } 
        else if (method.equals("JDBC")) {
            BddMethod.main(args);
        }
        else {
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

    private static void processFile(Path filePath) {
        try (Stream<String> lines = Files.lines(filePath);
             BufferedWriter writer = Files.newBufferedWriter(filePath.resolveSibling(filePath.getFileName().toString().replace(".op", ".res")))) {

            lines.map(line -> line.split(StringUtils.SPACE))
                 .filter(parts -> parts.length == 3)
                 .filter(parts -> isValidNumber(parts[0]) && isValidNumber(parts[2]))
                 .forEach(parts -> {
                     int number1 = Integer.parseInt(parts[0]);
                     String operator = parts[1];
                     int number2 = Integer.parseInt(parts[2]);
                     performOperationToFile(number1, operator, number2, writer);
                 });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValidNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static void performOperationToFile(int number1, String operator, int number2, BufferedWriter writer) {
        Operation operation = OperationFactory.getOperation(operator);
        try {
            if (operation == null) {
                writer.write("ERROR");
            } else {
                float result = operation.perform(number1, number2);
                if (Float.isNaN(result)) {
                    writer.write("ERROR");
                } else if (result == (int) result) {
                    writer.write(String.valueOf((int) result));
                } else {
                    writer.write(String.format("%.2f", result));
                }
            }
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName), StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
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
