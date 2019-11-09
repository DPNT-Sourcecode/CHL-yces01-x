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
        int total = 0;
        Map<Character, Integer> productCounter = new HashMap<>();

        for (char sku : basket.toCharArray()) {
            productCounter.merge(sku, 1, Integer::sum);
        }

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
            return price * quantity;
        }
    }
}


