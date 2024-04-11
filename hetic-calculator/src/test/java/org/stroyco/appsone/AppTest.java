package org.stroyco.appsone;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.stroyco.appsone.Factory.OperationFactory;
import org.stroyco.appsone.Strategy.Operation;
import org.stroyco.appsone.Strategy.Implementation.AddOperation;

/**
 * Unit test for simple App.
 */
public class AppTest {

    private final Operation addition = OperationFactory.getOperation("+");
    private final Operation subtraction = OperationFactory.getOperation("-");
    private final Operation multiplication = OperationFactory.getOperation("*");
    private final Operation division = OperationFactory.getOperation("/");

    @Test
    public void getOperation() {
        Operation operation = OperationFactory.getOperation("+");
        assertNotNull(operation);
        assertTrue(operation instanceof AddOperation);
    }

    @Test
    public void testAddition() {
        assertEquals(5, addition.perform(2, 3));
    }

    @Test
    public void testSubtraction() {
        assertEquals(2, subtraction.perform(5, 3));
    }

    @Test
    public void testMultiplication() {
        assertEquals(6, multiplication.perform(2, 3));
    }

    @Test
    public void testDivision() {
        assertEquals(2, division.perform(6, 3));
    }
}
