package befaster.solutions.CHL;

import java.util.HashMap;
import java.util.Map;

public class CheckliteSolution {

    private Map<Character, Product> products = new HashMap<>();

    public CheckliteSolution() {
        products.put('A', new Product(50, 3, 130));
        products.put('B', new Product(30, 2, 45));
        products.put('C', new Product(20));
        products.put('D', new Product(15));
    }

    public Integer checklite(String basket) {
        Map<Character, Integer> productCounter = countEachProductInBasket(basket);
        return calculateTotalPriceForProductCounts(productCounter);
    }

    private Map<Character, Integer> countEachProductInBasket(String basket) {
        Map<Character, Integer> productCounter = new HashMap<>();
        for (char sku : basket.toCharArray()) {
            productCounter.merge(sku, 1, Integer::sum);
        }
        return productCounter;
    }

    private int calculateTotalPriceForProductCounts(Map<Character, Integer> productCounter) {
        int total = 0;
        for (char sku : productCounter.keySet()) {
            total += products.get(sku).calculatePriceFor(productCounter.get(sku));
        }
        return total;
    }


    private static class Product {
        private Integer price;
        private Integer multiBuyQuantity;
        private Integer multiBuyPrice;

        public Product(int price) {
            this.price = price;
            this.multiBuyQuantity = null;
            this.multiBuyPrice = null;
        }

        public Product(int price, int multiBuyQuantity, int multiBuyPrice) {
            this.price = price;
            this.multiBuyQuantity = multiBuyQuantity;
            this.multiBuyPrice = multiBuyPrice;
        }

        public Integer calculatePriceFor(Integer quantity) {
            if (multiBuyAvailable()) {
                return calculateMultiBuyPrice(quantity);
            }

            return price * quantity;

        }

        private boolean multiBuyAvailable() {
            return multiBuyQuantity != null;
        }

        private Integer calculateMultiBuyPrice(Integer quantity) {
            int multiBuys = quantity / multiBuyQuantity;
            int remainderNonMultiBuys = quantity % multiBuyQuantity;

            return (multiBuys * multiBuyPrice) + (remainderNonMultiBuys * price);
        }
    }
}


