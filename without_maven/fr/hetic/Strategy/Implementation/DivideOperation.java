package without_maven.fr.hetic.Strategy.Implementation;

import without_maven.fr.hetic.Strategy.Operation;


public class DivideOperation implements Operation {
    @Override
    public float perform(int a, int b) {
        if (b == 0) {
            return Float.NaN;
        }
        return (float) a / b;
    }
}