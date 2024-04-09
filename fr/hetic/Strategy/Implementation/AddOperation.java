package fr.hetic.Strategy.Implementation;

import fr.hetic.Strategy.Operation;

public class AddOperation implements Operation {
    @Override
    public float perform(int a, int b) {
        return a + b;
    }
}