package DynamicProgramming;

/**
 * At t=0 you have received 0.1 bitcoins. You may trade your bitcoins until time T, when you aim to use the value to buy a house (in euros). A friend provided you with a predictor of the euro–bitcoin exchange rate, which for every day t gives the rate rt of 1 bitcoin in euro with trusted buyers/sellers. Because of transaction costs, if you sell x bitcoins you receive only 0.95⋅x⋅rt, and when you buy bitcoins, you pay a fixed amount of €5 for the transaction.
 *
 * Give (a) recursive function(s) describing the amount in euro your 0.1 bitcoin is worth by time T, based on optimal trading decisions and assuming perfect exchange rate predictions rt.
 */
public class BitcoinsAndEuros {
    /**
     * Implement this method
     *
     * @param t - the number of days you have
     * @param r - exchange rates of each day. Ignore index 0. I.e. the exchange rate of the first day can be found using r[1].
     * @return the maximum amount of euros after T days
     */
    public static double optimalTrade(int t, double[] r) {
        double[] e = new double[t+1];
        double[] b = new double[t+1];

        // Base case (aka given)
        b[0] = 0.1;

        // For each day...
        for (int i = 1; i <= t; ++i) {
            // The number of bitcoins is the maximum of trading and not trading
            b[i] = Math.max(b[i-1], (e[i-1] - 5) / r[i]);
            // The number of euros is the maximum of exchanging and not exchanging
            e[i] = Math.max(e[i-1], 0.95 * b[i-1] * r[i]);
        }
        return e[t];
    }
}
