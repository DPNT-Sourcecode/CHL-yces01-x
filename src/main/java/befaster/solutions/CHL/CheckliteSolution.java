package befaster.solutions.CHL;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CheckliteSolution {

    private Map<Character, Product> products = new HashMap<>();

    public CheckliteSolution() {
        products.put('A', new Product(50, Arrays.asList(
                new Product.Multibuy(3, 130),
                new Product.Multibuy(5, 200)))
        );

        products.put('B', new Product(30, 2, 45));
        products.put('C', new Product(20));
        products.put('D', new Product(15));
    }

    public Integer checklite(String basket) {
        try {
            Map<Character, Integer> productCounter = countEachProductInBasket(basket);
            return calculateTotalPriceForProductCounts(productCounter);
        } catch (InvalidSkuException e) {
            return -1;
        }
    }

    private Map<Character, Integer> countEachProductInBasket(String basket) {
        Map<Character, Integer> productCounter = new HashMap<>();
        for (char sku : basket.toCharArray()) {
            validateSku(sku);
            productCounter.merge(sku, 1, Integer::sum);
        }

        return productCounter;
    }

    private void validateSku(char sku) {
        if (products.get(sku) == null) {
            throw new InvalidSkuException();
        }
    }

    private int calculateTotalPriceForProductCounts(Map<Character, Integer> productCounter) {
        int total = 0;
        for (char sku : productCounter.keySet()) {
            total += products.get(sku).calculatePriceFor(productCounter.get(sku));
        }
        return total;
    }


    private static class InvalidSkuException extends RuntimeException {
    }
}


