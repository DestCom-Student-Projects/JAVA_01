package org.stroyco.appsone;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.stroyco.appsone.Factory.OperationFactory;
import org.stroyco.appsone.Strategy.Operation;

public class Folder {
    public static void processFile(Path filePath) {
        try (Stream<String> lines = Files.lines(filePath);
                BufferedWriter writer = Files.newBufferedWriter(
                        filePath.resolveSibling(filePath.getFileName().toString().replace(".op", ".res")))) {

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
}
