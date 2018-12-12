import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CoinReader {

    /**
     * Read a set of coins (number) from a file
     *
     * @param file
     * @return the set of coins
     * @throws IOException
     */
    public static Set<Integer> getCoins(String file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(file));
        List<String> coinsStr = new ArrayList<>();
        String coinStr;

        while ((coinStr = bufferedReader.readLine()) != null) {
            coinsStr.add(coinStr);
        }

        Set<Integer> coins = new HashSet<>();
        for (String coin : coinsStr) {
            coins.add(Integer.parseInt(coin));
        }

        return coins;
    }

}
