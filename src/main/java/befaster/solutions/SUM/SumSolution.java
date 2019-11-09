package befaster.solutions.SUM;

@SuppressWarnings("unused")
public class SumSolution {

    public static final int UPPER_BOUND = 100;
    public static final int LOWER_BOUND = 0;

    public int compute(int x, int y) {
        if (argsLessThanLowerBound(x, y) || argsGreaterThanUpperBound(x, y)) {
            throw new OutOfBoundsException();
        }
        
        return x + y;
    }

    private boolean argsGreaterThanUpperBound(int x, int y) {
        return x > UPPER_BOUND || y > UPPER_BOUND;
    }

    private boolean argsLessThanLowerBound(int x, int y) {
        return x < LOWER_BOUND || y < LOWER_BOUND;
    }

    public static class OutOfBoundsException extends RuntimeException {

    }
}



