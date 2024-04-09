package fr.hetic;

import java.io.*;

import fr.hetic.Factory.OperationFactory;
import fr.hetic.Strategy.Operation;

public class Calculator {
    public static void main(String[] args) {
        if (args.length == 3) {
            int number1 = Integer.parseInt(args[0]);
            String operator = args[1];
            int number2 = Integer.parseInt(args[2]);

            Operation operation = OperationFactory.getOperation(operator);
            if (operation == null) {
                System.err.println("Invalid operator");
                System.exit(1);
            }

            float result = operation.perform(number1, number2);
            System.out.println("Result: " + result);
        } else if (args.length == 1) {
            File ops = new File(args[0]);

            if (!ops.isDirectory()) {
                System.err.println("File not found");
                System.exit(1);
            }

            fileOperation(ops);

            System.out.println("Operation(s) completed");
        } else {
            System.out.println("Please use a valid method to use the calculator");
            System.out.println("Usage: java Calculator <number1> <operator> <number2>");
            System.out.println("or");
            System.out.println("Usage: java Calculator <pathToDirectory>");
            System.out.println("Supported operators: +, -, * or x, /");
            System.out.println("If using * or / in cli, please add quotes around the operator");
        }
    }

    public static void fileOperation(File folder) {
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                String fileName = file.getName();
                String[] fileParts = fileName.split("\\.");

                if (fileParts.length == 2 && fileParts[1].equals("op")) {
                    System.out.println("Found file: " + fileName);
                    processFile(file);
                }
            }
        }
    }

    public static void processFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath().replace(".op", ".res")))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(" ");
                    if (parts.length != 3) {
                        writer.write("ERROR");
                        writer.newLine();
                        continue;
                    }

                    int number1 = Integer.parseInt(parts[0]);
                    String operator = parts[1];
                    int number2 = Integer.parseInt(parts[2]);

                    Operation operation = OperationFactory.getOperation(operator);
                    if (operation == null) {
                        writer.write("ERROR");
                        writer.newLine();
                        continue;
                    }

                    float result = operation.perform(number1, number2);
                    if (Float.isNaN(result)) {
                        writer.write("ERROR");
                    } else if (result == (int) result) {
                        writer.write(String.valueOf((int) result));
                    } else {
                        writer.write(String.format("%.2f", result));
                    }
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}