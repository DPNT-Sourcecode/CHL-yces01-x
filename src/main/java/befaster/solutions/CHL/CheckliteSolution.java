package befaster.solutions.CHL;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class CheckliteSolution {

    private Map<Character, Product> products = new HashMap<>();

    public CheckliteSolution() {
        products.put('A', new Product(50, asList(
                new Product.MultibuyOffer(3, 130),
                new Product.MultibuyOffer(5, 200))));
        products.put('B', new Product(30, singletonList(
                new Product.MultibuyOffer(2, 45))));
        products.put('C', new Product(20));
        products.put('D', new Product(15));
        products.put('E', new Product(40,
                new Product.CrossProductOffer(2, 'B')));
    }

    public Integer checklite(String basket) {
        try {
            Map<Character, Integer> productCounter = countEachProductInBasket(basket);

            for (char sku : productCounter.keySet()) {
                products.get(sku)
                        .findMatchingCrossProductOffer(productCounter.get(sku))
                        .ifPresent(freeSku -> removeFromCounter(productCounter, freeSku));
            }

            return calculateTotalPriceForProductCounts(productCounter);
        } catch (InvalidSkuException e) {
            return -1;
        }
    }

    private void removeFromCounter(Map<Character, Integer> productCounter, Product.MatchingCrossProductOffer matchingCrossProductOffer) {
        Integer countForFreeProduct = productCounter.get(matchingCrossProductOffer.getSku());

        if (countForFreeProduct != null && countForFreeProduct > 0) {
            countForFreeProduct = calculateNewQuantityOfFreeProduct(matchingCrossProductOffer, countForFreeProduct);
            productCounter.put(matchingCrossProductOffer.getSku(), countForFreeProduct);
        }
    }

    private Integer calculateNewQuantityOfFreeProduct(Product.MatchingCrossProductOffer matchingCrossProductOffer, Integer countForFreeProduct) {
        countForFreeProduct = countForFreeProduct - matchingCrossProductOffer.getFreeQuantity();
        if (countForFreeProduct < 0) {
            countForFreeProduct = 0;
        }
        return countForFreeProduct;
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
