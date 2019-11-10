package befaster.solutions.CHL;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class Product {
    private Integer price;
    private List<MultibuyOffer> multibuyOffers = new ArrayList<>();
    private CrossProductOffer crossProductOffer = null;

    public Product(int price) {
        this.price = price;
    }

    public Product(int price, List<MultibuyOffer> multibuyOffers) {
        this.price = price;
        this.multibuyOffers = multibuyOffers;
    }

    public Product(Integer price, CrossProductOffer crossProductOffer) {
        this.price = price;
        this.crossProductOffer = crossProductOffer;
    }

    public Integer calculatePriceFor(Integer quantity) {
        if (multibuyOffers.isEmpty()) {
            return price * quantity;
        }

        int total = 0;
        for (MultibuyOffer multibuyOffer : multibuyOffers) {
            total += multibuyOffer.calculateMultiBuyPrice(quantity);
            quantity -= multibuyOffer.calculateQuantityCoveredInMultiBuy(quantity);
        }

        if (quantity > 0) {
            total += quantity * price;
        }

        return total;

    }

    public Optional<MatchingCrossProductOffer> findMatchingCrossProductOffer(char buyingSku, Integer quantityInBasket) {
        if (crossProductOffer == null) {
            return Optional.empty();
        }

        if (buyingSku == crossProductOffer.freeCrossProduct) {
            int buyingPlusFreeTotal = crossProductOffer.buyingQuantity + 1;
            return Optional.of(new MatchingCrossProductOffer(
                    crossProductOffer.freeCrossProduct, quantityInBasket / buyingPlusFreeTotal));

        } else if (quantityInBasket >= crossProductOffer.buyingQuantity) {
            return Optional.of(new MatchingCrossProductOffer(
                    crossProductOffer.freeCrossProduct, quantityInBasket / crossProductOffer.buyingQuantity));
        }

        return Optional.empty();
    }


    public static class MultibuyOffer {
        int multiBuyQuantity;
        int multiBuyPrice;

        public MultibuyOffer(int multiBuyQuantity, int multiBuyPrice) {
            this.multiBuyQuantity = multiBuyQuantity;
            this.multiBuyPrice = multiBuyPrice;
        }

        Integer calculateQuantityCoveredInMultiBuy(Integer quantity) {
            return (quantity / multiBuyQuantity) * multiBuyQuantity;
        }

        Integer calculateMultiBuyPrice(Integer quantity) {
            int multiBuys = quantity / multiBuyQuantity;
            return (multiBuys * multiBuyPrice);
        }
    }

    public static class CrossProductOffer {
        int buyingQuantity;
        char freeCrossProduct;

        public CrossProductOffer(int buyingQuantity, char freeCrossProduct) {
            this.buyingQuantity = buyingQuantity;
            this.freeCrossProduct = freeCrossProduct;
        }
    }

    class MatchingCrossProductOffer {
        char sku;
        int freeQuantity;

        public MatchingCrossProductOffer(char sku, int freeQuantity) {
            this.sku = sku;
            this.freeQuantity = freeQuantity;
        }

        public char getSku() {
            return sku;
        }

        public int getFreeQuantity() {
            return freeQuantity;
        }
    }
}
