package befaster.solutions.SUM;

@SuppressWarnings("unused")
public class SumSolution {

    public int compute(int x, int y) {
        if (argsLessThanZero(x, y) || x > 100 || y > 100 ) {
            throw new OutOfBoundsException();
        }
        
        return x + y;
    }

    private boolean argsLessThanZero(int x, int y) {
        return x < 0 || y < 0;
    }

    public static class OutOfBoundsException extends RuntimeException {

    }
}


