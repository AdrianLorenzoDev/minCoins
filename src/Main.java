import org.apache.commons.cli.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {


    /**
     * Arguments parser with default options
     *
     * @param args Arguments
     * @return Parsed arguments
     * @throws ParseException
     */
    public static CommandLine parseArguments(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption(new Option("time", false, "Time measurement"));
        options.addOption(new Option("tabulation", false,
                "Print tabulation implementation result"));
        options.addOption(new Option("max", true,
                "Sets max value for array size due to heap JVM size. Default: 100000"));
        options.addOption(new Option("help", false, "Prints help about program"));

        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine arguments = commandLineParser.parse(options, args);

        if(arguments.hasOption("help")){
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "minCoins [VALUE] [FILE]...", options );
            System.exit(1);
        }

        return arguments;
    }


    /**
     * Read a set of coins (number) from a file
     *
     * @param file
     * @return the set of coins
     * @throws IOException
     */
    public static Set<Integer> getCoins(String file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
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


    public static void main(String[] args) throws IOException, ParseException {
        CommandLine arguments = parseArguments(args);
        String[] nonOptionArguments = arguments.getArgs();

        if (nonOptionArguments.length < 2) {
            System.out.println("ERROR: More arguments neeeded");
            System.exit(-1);
        }

        int value = Integer.parseInt(nonOptionArguments[0]);

        int maxArraySize;
        if (arguments.hasOption("max")) {
            maxArraySize = Integer.parseInt(arguments.getOptionValue("max"));
        } else {
            maxArraySize = 100000;
        }

        if (maxArraySize <= value) {
            System.out.println("ERROR: Value must be higher than the arraySize: " + maxArraySize);
            System.exit(-2);
        }

        minCoinCalculator test = new minCoinCalculator(getCoins(nonOptionArguments[1]), maxArraySize);

        long startTimeStamp, time;
        int result;

        if (arguments.hasOption("tabulation")) {
            // Time argument
            if (arguments.hasOption("time")) {
                // Memoization
                startTimeStamp = System.currentTimeMillis();
                result = test.minCoinsMemoization(value);
                time = System.currentTimeMillis() - startTimeStamp;
                System.out.println("MEMOIZATION:    Min number of coins needed: " + result + " - TIME: " + time + " ms");

                // Tabulation
                startTimeStamp = System.currentTimeMillis();
                result = test.minCoinsMemoization(value);
                time = System.currentTimeMillis() - startTimeStamp;
                System.out.println("TABULATION:     Min number of coins needed: " + result + " - TIME: " + time + " ms");
            } else {
                // Memoization
                result = test.minCoinsMemoization(value);
                System.out.println("MEMOIZATION:    Min number of coins needed: " + result);

                // Tabulation
                result = test.minCoinsTabulation(value);
                System.out.println("TABULATION:     Min number of coins needed: " + result);
            }
        } else {
            //Time argument
            if (arguments.hasOption("time")) {
                startTimeStamp = System.currentTimeMillis();
                result = test.minCoinsMemoization(value);
                time = System.currentTimeMillis() - startTimeStamp;
                System.out.println("Min number of coins needed: " + result + " - TIME: " + time + " ms");
            } else {
                result = test.minCoinsMemoization(value);
                System.out.println("Min number of coins needed: " + result);
            }
        }
    }
}