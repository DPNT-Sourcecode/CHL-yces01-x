package befaster.solutions.HLO;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class HelloSolutionTest {
    private HelloSolution helloSolution;

    @Before
    public void setUp() {
        helloSolution = new HelloSolution();
    }

    @Test
    public void canSayHelloWorldForGivenName() {
        MatcherAssert.assertThat(helloSolution.hello("friend"), Matchers.is("Hello, friend!"));
    }
}