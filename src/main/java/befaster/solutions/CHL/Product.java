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
        boolean multiBuyMatch;
        do {
            multiBuyMatch = false;
            for (MultibuyOffer multibuyOffer : multibuyOffers) {
                if (multibuyOffer.matchesMultiBuy(quantity)) {
                    multiBuyMatch = true;
                    quantity -= multibuyOffer.multiBuyQuantity;
                    total += multibuyOffer.multiBuyPrice;
                }
            }
        } while (multiBuyMatch);

        if (quantity > 0) {
            total += quantity * price;
        }

        return total;

    }

    public Optional<MatchingCrossProductOffer> findMatchingCrossProductOffer(Integer quantityInBasket) {
        if (crossProductOffer == null) {
            return Optional.empty();
        }

        if (quantityInBasket >= crossProductOffer.buyingQuantity) {
            return Optional.of(new MatchingCrossProductOffer(
                    crossProductOffer.freeCrossProduct, quantityInBasket / crossProductOffer.buyingQuantity)
            );
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

        boolean matchesMultiBuy(Integer quantity) {
            return quantity >= multiBuyQuantity;
//            if (quantity > multiBuyQuantity) {
//                int multiBuys = quantity / multiBuyQuantity;
//            }
////            int remainderNonMultiBuys = quantity % multiBuyQuantity;
//
//            return new MultiBuyMatch(multiBuys * multiBuyQuantity, multiBuys * multiBuyPrice);
        }

        static class MultiBuyMatch {
            int quantityInMultibuy;
            int totalMultiBuyPrice;

            public MultiBuyMatch(int quantityInMultibuy, int totalMultiBuyPrice) {
                this.quantityInMultibuy = quantityInMultibuy;
                this.totalMultiBuyPrice = totalMultiBuyPrice;
            }
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



