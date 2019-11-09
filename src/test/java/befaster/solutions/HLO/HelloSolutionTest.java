package befaster.solutions.HLO;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HelloSolutionTest {
    private HelloSolution helloSolution;

    @Before
    public void setUp() throws Exception {
        helloSolution = new HelloSolution();
    }

    @Test
    public void name() {
        MatcherAssert.assertThat(helloSolution.hello("friend"), Matchers.is("Hello friend"));
    }
}