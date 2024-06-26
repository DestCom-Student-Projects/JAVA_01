package org.stroyco.appsone.Strategy.Implementation;

import org.stroyco.appsone.Strategy.Operation;


public class DivideOperation implements Operation {
    @Override
    public float perform(int a, int b) {
        if (b == 0) {
            return Float.NaN;
        }
        return (float) a / b;
    }
}