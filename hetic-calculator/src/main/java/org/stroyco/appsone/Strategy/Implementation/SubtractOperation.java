package org.stroyco.appsone.Strategy.Implementation;

import org.stroyco.appsone.Strategy.Operation;

public class SubtractOperation implements Operation {
    @Override
    public float perform(int a, int b) {
        return a - b;
    }
}