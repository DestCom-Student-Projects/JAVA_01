package without_maven.fr.hetic.Factory;

import java.util.HashMap;
import java.util.Map;

import without_maven.fr.hetic.Strategy.Operation;
import without_maven.fr.hetic.Strategy.Implementation.AddOperation;
import without_maven.fr.hetic.Strategy.Implementation.DivideOperation;
import without_maven.fr.hetic.Strategy.Implementation.MultiplyOperation;
import without_maven.fr.hetic.Strategy.Implementation.SubtractOperation;

public class OperationFactory {
    private static final Map<String, Operation> operations = new HashMap<>();

    static {
        operations.put("+", new AddOperation());
        operations.put("-", new SubtractOperation());
        operations.put("*", new MultiplyOperation());
        operations.put("x", new MultiplyOperation());
        operations.put("/", new DivideOperation());
    }

    public static Operation getOperation(String operator) {
        return operations.get(operator);
    }
}