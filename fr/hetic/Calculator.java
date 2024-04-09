package fr.hetic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class Calculator {
    public static void main(String[] args) {

        if(args.length == 3) {
            int number1 = Integer.parseInt(args[0]);
            String operator = args[1];
            int number2 = Integer.parseInt(args[2]);

            operation(number1, operator, number2, "cli");
        } 
        else if (args.length == 1) {
            File ops = new File(args[0]);

            if(!ops.isDirectory()) {
                System.err.println("File not found");
                System.exit(1);
            }

            fileOperation(ops);

            System.out.println("Operation(s) completed");
        }   
        else {
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

                if(fileParts.length == 2) {
                    String extension = fileParts[1];
                    if(extension.equals("op")) {
                        System.out.println("Found file: " + fileName);
                        processFile(file);
                    }
                }
            }
        }
    }

    public static void processFile(File fichier) {
        float[] resList = new float[0];
        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                int number1 = Integer.parseInt(parts[0]);
                String operator = parts[1];
                int number2 = Integer.parseInt(parts[2]);

                float res = operation(number1, operator, number2, "file");

                float[] temp = new float[resList.length + 1];
                System.arraycopy(resList, 0, temp, 0, resList.length);
                temp[resList.length] = res;
                resList = temp;
            }

            writeToFile(
            fichier.getName().replace(".op", ".res"), fichier.getParent(), resList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(String fileName, String directory, float[] resList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(directory + "/" + fileName))) {
            for (float res : resList) {
                if (Float.isNaN(res)) {
                    writer.write("ERROR");
                } else if(res == (int) res) {
                    writer.write(String.valueOf((int) res));
                } else {
                    writer.write(String.format("%.2f", res));
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static float operation(int a, String operator, int b, String method) {
        float result = 0;
        switch (operator) {
            case "+":
                result = add(a, b);
                break;
            case "-":
                result = substract(a, b);
                break;
            case "*" :
            case "x":  
                result = multiply(a, b);
                break;
            case "/":
                if (b == 0) {
                    return Float.NaN;
                }
                result = divide(a, b);
                break;
            default:
                System.err.println("Invalid operator");
                System.out.println("Usage: java Calculator <number1> <operator> <number2>");
                System.out.println("Supported operators: +, -, * or x, /");
                System.out.println("If using * or /, please add quotes around the operator");
                System.exit(1);
        }

        if (method.equals("cli")) {
            System.out.println("Result: " + result);
            return result;
        } else {
            return result;
        }
    }

    public static int add(int a, int b) {
        return a + b;
    }

    public static int substract(int a, int b) {
        return a - b;
    }

    public static int multiply(int a, int b) {
        return a * b;
    }

    public static float divide(int a, int b) {
        return (float) a / b;
    }
}