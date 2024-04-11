package without_maven.fr.hetic.Strategy.Implementation;

import without_maven.fr.hetic.Strategy.Operation;

public class SubtractOperation implements Operation {
    @Override
    public float perform(int a, int b) {
        return a - b;
    }
}