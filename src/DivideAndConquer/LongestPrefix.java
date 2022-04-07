package DivideAndConquer;

public class LongestPrefix {
    /**
     * You should implement this method.
     *
     * @param n the number of encodings
     * @param encodings the encodings to analyse. Note that you should use entries encodings[1] to
     *     encodings[n]!
     * @return the longest common prefix amongst all encodings.
     */
    public static String longestPrefix(int n, String[] encodings) {
        if (encodings.length == 0) return "";
        return longestPrefix(n, encodings, 1, encodings.length);
    }

    public static String longestPrefix(int n, String[] encodings, int low, int high) {
        if (high - low == 1) return encodings[low];

        int m = (low + high) / 2;
        String longestLeft = longestPrefix(n, encodings, low, m);
        String longestRight = longestPrefix(n, encodings, m, high);

        StringBuilder sb = new StringBuilder();
        int k = Math.min(longestLeft.length(), longestRight.length());
        for (int i = 0; i < k; ++i) {
            if (longestLeft.charAt(i) == longestRight.charAt(i)) {
                sb.append(longestLeft.charAt(i));
            } else {
                break;
            }
        }
        return sb.toString();
    }

}
