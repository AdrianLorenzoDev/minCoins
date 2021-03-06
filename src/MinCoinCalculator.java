import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MinCoinCalculator {

    private Map<Integer, Integer> minValuesCache; // Memoization cache
    private Set<Integer> coins; // Set of coins

    /**
     * @param coins Set of coins
     */
    public MinCoinCalculator(Set<Integer> coins) {
        this.coins = coins;
        minValuesCache = new HashMap<>();
        minValuesCache.put(0, 0);
    }


    /**
     * Calculates the minimum number of coins of a infinite set of them to get at determinated value by memoization,
     * based on geeksforgeeks.org tabulation solution
     *
     * @param value The value which the sum of coins must be
     * @return The minimum number of coins
     */
    private int minCoinsMem(int value) {

        // Value is in cache
        if (minValuesCache.getOrDefault(value, Integer.MAX_VALUE) != Integer.MAX_VALUE) {
            return minValuesCache.get(value);
        }

        minValuesCache.put(value, Integer.MAX_VALUE);

        // Go through all coins smaller than value
        for (Integer coin : coins) {
            if (coin <= value) {
                int subResult = minCoinsMem(value - coin);
                if (subResult != Integer.MAX_VALUE && subResult + 1 < minValuesCache.get(value)) {
                    minValuesCache.put(value, subResult + 1);
                }
            }
        }

        return minValuesCache.get(value);
    }

    /**
     * Calls minCoinsMem method and handles StackOverflowError due to Dynamic Programming limitations.
     *
     * @param value The value which the sum of coins must be
     * @return The minimum number of coins
     */
    public int minCoinsMemoization(int value) {
        try {
            return minCoinsMem(value);
        } catch (StackOverflowError e) {
            return -1;
        }
    }


    /**
     * Calculates the minimum number of coins of a infinite set of them to get at determinated value by tabulation
     * Source: geekforgeeks.org (Goku)
     *
     * @param value The value which the sum of coins must be
     * @return The minimum number of coins
     */
    public int minCoinsTab(int value) {

        int[] table = new int[value + 1];

        // Base case (If given value is 0)
        table[0] = 0;

        for (int i = 1; i <= value; i++) {
            table[i] = Integer.MAX_VALUE;
        }

        // Compute minimum coins required for all
        // values from 1 to value
        for (int i = 1; i <= value; i++) {
            // Go through all coins smaller than value
            for (Integer coin : coins) {
                if (coin <= i) {
                    int subResult = table[i - coin];
                    if (subResult != Integer.MAX_VALUE && subResult + 1 < table[i])
                        table[i] = subResult + 1;
                }
            }
        }

        return table[value];
    }

    /**
     * Calls minCoinsTab method and handles StackOverflowError due to Dynamic Programming limitations.
     *
     * @param value The value which the sum of coins must be
     * @return The minimum number of coins
     */
    public int minCoinsTabulation(int value) {
        try {
            return minCoinsTab(value);
        } catch (StackOverflowError e) {
            return -1;
        }
    }


}