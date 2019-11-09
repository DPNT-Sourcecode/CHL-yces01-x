package befaster.solutions.CHL;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CheckliteSolutionTest {
    private CheckliteSolution checkliteSolution;

    @Before
    public void setUp() throws Exception {
        checkliteSolution = new CheckliteSolution();
    }

    @Test
    public void givenOneAProductInBasket_thenPriceIs50() {
        assertThat(checkliteSolution.checklite("A"), Matchers.is(50));
    }

    @Test
    public void givenOneAandOneBInBasket_thenTotalIs80() {
        assertThat(checkliteSolution.checklite("AB"), Matchers.is(80));
    }

    @Test
    public void givenThreeAProductsInBasket_thenTotalIsSpecialOfferOf130() {
        assertThat(checkliteSolution.checklite("AAA"), Matchers.is(130));
    }

    @Test
    public void givenFourAProducts_thenTotalIsSpecialOfferPlusOneTotalling180() {
        assertThat(checkliteSolution.checklite("AAAA"), Matchers.is(180));
    }

    @Test
    public void givenFourAProductsAndOneB_thenTotalIs210() {
        assertThat(checkliteSolution.checklite("AAAAB"), Matchers.is(210));
    }

    @Test
    public void givenFourAProductsAndFourB_thenTotalIs255() {
        assertThat(checkliteSolution.checklite("AAAABBBB"), Matchers.is(270));
    }
}