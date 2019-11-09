package befaster.solutions.SUM;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SumSolutionTest {
    private SumSolution sum;

    @Before
    public void setUp() {
        sum = new SumSolution();
    }

    @Test
    public void compute_sum() {
        assertThat(sum.compute(1, 1), equalTo(2));
    }

    @Test(expected = SumSolution.OutOfBoundsException.class)
    public void givenAnIntegerIsLessThan0_thenThrowException() {
        sum.compute(-1, 1);
    }

    @Test(expected = SumSolution.OutOfBoundsException.class)
    public void givenAnIntegerIsGreaterThan100_thenThrowException() {
        sum.compute(101, 1);
    }
}
