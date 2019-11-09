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
            offerCalculations.add(calculateMultiBuyPrice(quantity, multibuyOffer));
        }

        Collections.sort(offerCalculations);

        return offerCalculations.get(0);


    }

    private Integer calculateMultiBuyPrice(Integer quantity, Multibuy multibuyOffer) {

        int multiBuys = quantity / multibuyOffer.multiBuyQuantity;
        int remainderNonMultiBuys = quantity % multibuyOffer.multiBuyQuantity;

        return (multiBuys * multibuyOffer.multiBuyPrice) + (remainderNonMultiBuys * price);
    }

    public static class Multibuy {
        int multiBuyQuantity;
        int multiBuyPrice;

        public Multibuy(int multiBuyQuantity, int multiBuyPrice) {
            this.multiBuyQuantity = multiBuyQuantity;
            this.multiBuyPrice = multiBuyPrice;
        }
    }
}

