package befaster.solutions.CHL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Product {
    private Integer price;
    private List<Multibuy> multibuyOffers = new ArrayList<>();
    private CrossProductOffer crossProductOffer;

    public Product(int price) {
        this.price = price;
    }

    public Product(int price, List<Multibuy> multibuyOffers) {
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

        List<Integer> offerCalculations = new ArrayList<>();
        for (Multibuy multibuyOffer : multibuyOffers) {
            offerCalculations.add(multibuyOffer.calculateMultiBuyPrice(quantity, price));
        }

        Collections.sort(offerCalculations);

        return offerCalculations.get(0);

    }



    public static class Multibuy {
        int multiBuyQuantity;
        int multiBuyPrice;

        public Multibuy(int multiBuyQuantity, int multiBuyPrice) {
            this.multiBuyQuantity = multiBuyQuantity;
            this.multiBuyPrice = multiBuyPrice;
        }

        Integer calculateMultiBuyPrice(Integer quantity, Integer individualPrice) {

            int multiBuys = quantity / multiBuyQuantity;
            int remainderNonMultiBuys = quantity % multiBuyQuantity;

            return (multiBuys * multiBuyPrice) + (remainderNonMultiBuys * individualPrice);
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
}
