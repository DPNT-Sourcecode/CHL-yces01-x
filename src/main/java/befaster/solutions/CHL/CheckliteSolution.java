package befaster.solutions.CHL;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class CheckliteSolution {

    private Map<Character, Product> products = new HashMap<>();

    public CheckliteSolution() {
        products.put('A', new Product(50, asList(
                new Product.MultibuyOffer(5, 200),
                new Product.MultibuyOffer(3, 130))));
        products.put('B', new Product(30, singletonList(
                new Product.MultibuyOffer(2, 45))));
        products.put('C', new Product(20));
        products.put('D', new Product(15));
        products.put('E', new Product(40,
                new Product.CrossProductOffer(2, 'B')));
        products.put('F', new Product(10,
                new Product.CrossProductOffer(2, 'F')));
        products.put('G', new Product(20));
        products.put('H', new Product(10, asList(
                new Product.MultibuyOffer(10, 80),
                new Product.MultibuyOffer(5, 45))));
        products.put('I', new Product(35));
        products.put('J', new Product(60));
        products.put('K', new Product(80, singletonList(
                new Product.MultibuyOffer(2, 150))));
        products.put('L', new Product(90));
        products.put('M', new Product(15));
        products.put('N', new Product(40,
                new Product.CrossProductOffer(3, 'M')));
        products.put('O', new Product(10));
        products.put('P', new Product(50, singletonList(
                new Product.MultibuyOffer(5, 200))));
        products.put('Q', new Product(30, singletonList(
                new Product.MultibuyOffer(3, 80))));
        products.put('R', new Product(50,
                new Product.CrossProductOffer(3, 'Q')));
        products.put('S', new Product(30));
        products.put('T', new Product(20));
        products.put('U', new Product(40,
                new Product.CrossProductOffer(3, 'U')));
        products.put('V', new Product(50, asList(
                new Product.MultibuyOffer(3, 130),
                new Product.MultibuyOffer(2, 90))));
        products.put('W', new Product(20));
        products.put('X', new Product(90));
        products.put('Y', new Product(10));
        products.put('Z', new Product(50));
    }

    public Integer checklite(String basket) {
        try {
            Map<Character, Integer> productCounter = countEachProductInBasket(basket);

            for (char sku : productCounter.keySet()) {
                products.get(sku)
                        .findMatchingCrossProductOffer(sku, productCounter.get(sku))
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
        countForFreeProduct -= matchingCrossProductOffer.getFreeQuantity();
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


