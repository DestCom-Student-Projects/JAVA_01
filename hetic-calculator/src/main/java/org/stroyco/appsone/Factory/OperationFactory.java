package org.stroyco.appsone.Factory;

import java.util.HashMap;
import java.util.Map;

import org.stroyco.appsone.Strategy.Operation;
import org.stroyco.appsone.Strategy.Implementation.AddOperation;
import org.stroyco.appsone.Strategy.Implementation.DivideOperation;
import org.stroyco.appsone.Strategy.Implementation.MultiplyOperation;
import org.stroyco.appsone.Strategy.Implementation.SubtractOperation;

public class OperationFactory {
    private static final Map<String, Operation> operations = new HashMap<>();

    static {
        operations.put("+", (Operation) new AddOperation());
        operations.put("-", (Operation) new SubtractOperation());
        operations.put("*", (Operation) new MultiplyOperation());
        operations.put("x", (Operation) new MultiplyOperation());
        operations.put("/", (Operation) new DivideOperation());
    }

    public static Operation getOperation(String operator) {
        return operations.get(operator);
    }
}