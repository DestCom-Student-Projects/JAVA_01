package fr.hetic.Factory;

import java.util.HashMap;
import java.util.Map;

import fr.hetic.Strategy.Operation;
import fr.hetic.Strategy.Implementation.AddOperation;
import fr.hetic.Strategy.Implementation.DivideOperation;
import fr.hetic.Strategy.Implementation.MultiplyOperation;
import fr.hetic.Strategy.Implementation.SubtractOperation;

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