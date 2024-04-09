package fr.hetic.Strategy.Implementation;

import fr.hetic.Strategy.Operation;

public class SubtractOperation implements Operation {
    @Override
    public float perform(int a, int b) {
        return a - b;
    }
}