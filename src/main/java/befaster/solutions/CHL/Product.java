package befaster.solutions.CHL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Product {
    private Integer price;
    private List<Multibuy> multibuyOffers = new ArrayList<>();

    public Product(int price) {
        this.price = price;
    }

    public Product(int price, int multiBuyQuantity, int multiBuyAmount) {
        this.price = price;
        multibuyOffers.add(new Multibuy(multiBuyQuantity, multiBuyAmount));
    }

    public Product(int price, List<Multibuy> multibuyOffers) {
        this.price = price;
        this.multibuyOffers = multibuyOffers;
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

        private Integer calculateMultiBuyPrice(Integer quantity, Integer individualPrice) {

            int multiBuys = quantity / multiBuyQuantity;
            int remainderNonMultiBuys = quantity % multiBuyQuantity;

            return (multiBuys * multiBuyPrice) + (remainderNonMultiBuys * individualPrice);
        }
    }
}


