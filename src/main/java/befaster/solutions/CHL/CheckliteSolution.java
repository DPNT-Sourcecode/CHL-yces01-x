package befaster.solutions.CHL;

import java.util.HashMap;

public class CheckliteSolution {

    private HashMap<Character, Integer> skus = new HashMap<>();

    public CheckliteSolution() {
        skus.put('A', 50);
        skus.put('B', 30);
    }

    public Integer checklite(String basket) {
        int total = 0;

        for (char sku : basket.toCharArray()) {
            total += skus.get(sku);
        }

        return total;
    }
}

