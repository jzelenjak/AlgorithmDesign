package DivideAndConquer;

/**
 * Description
 * On her holiday at a tourist attraction, Alex climbed a ladder to take a picture and climbed back down. However, the picture file got corrupted. To fix that, she needs to know exactly when the picture was taken. Luckily, Alex was wearing an altimeter that saved her exact altitude at each point in time and stored it in the array altitude. Your job is to design an algorithm that figures out at what point in time Alex took the picture accessing the array altitude as few times as possible.
 *
 * We know that:
 * - Alex definitely took the picture between t=0 and t=altitude.length
 * - Alex never descended before taking the picture, so until the maximum the function only increases
 * - Similarly, Alex never ascended after taking the picture, so after the maximum, the function only decreases
 * - Note that these two things together are exactly how we define a “unimodal function”.
 *
 * Note that we specifically do not know:
 * - Alex’s altitude at the moment she took the picture
 * - Alex’s climbing speed; it could have varied a lot during time
 *
 * Hints:
 * - If we ask the altitude at t=altitude.length/2, can we deduce anything? Does it help to also ask t=0 and t=altitude.length?
 * - If we ask the altitude at t=altitude.length/3 and t=altitude.length*2/3, can we deduce anything?
 * - If we know Alex took the picture between t=0 and t=altitude.length*2/3, can we solve the problem then? And what if we know she took the picture between t=altitude.length/3 and t=altitude.length?
 */
public class TernarySearch {
    /**
     * Finds the x coordinate with the highest value of an array with a unimodal function, by recursively evaluating the values at one-third and two-thirds of the range.
     * Depending on which is higher, a new evaluation is made with a smaller range to find the x coordinate with the highest value.
     *
     * @param altitude the array with the unimodal function
     * @return the x coordinate with the highest value
     */
    public static int findPictureTime(double[] altitude) {
        return findPictureTime(altitude, 0, altitude.length - 1);
    }

    /**
     * Finds the x coordinate with the highest value of an array with a unimodal function, by recursively evaluating the values at one-third and two-thirds of the range.
     * Depending on which is higher, a new evaluation is made with a smaller range to find the x coordinate with the highest value.
     * @param altitude the array with the unimodal function
     * @param low lower bound on the x coordinate
     * @param high upper bound on the x coordinate
     * @return the x coordinate with the highest value
     */
    public static int findPictureTime(double[] altitude, int low, int high) {
        // Base case 1: length == 1 => return the x coordinate
        if (high == low) return high;
        // Base case 2: length == 2 => return the x coordinate with the higher altitude
        if (high == low + 1) return (altitude[low] > altitude[high]) ? low : high;
        // Base case 3: length == 3 => solve recursively, return x the highest altitude
        if (high == low + 2) {
            int sub = findPictureTime(altitude, low, high-1);
            return sub > altitude[high] ? sub : high;
        }

        // Recursive case

        // Range length
        int range = (high - low + 1);
        // 1/3 of the range
        int t1 = low + range / 3;
        // 2/3 of the range
        int t2 = low + 2 * range / 3;

        // altitude[t_{1/3}] < altitude[t_{2/3}] => peak to the right
        if (altitude[t1] < altitude[t2]) return findPictureTime(altitude, t1+1, high);
        // altitude[t_{1/3}] > altitude[t_{2/3}] => peak to the left
        if (altitude[t1] > altitude[t2]) return findPictureTime(altitude, low, t2-1);
        // altitude[t_{1/3}] == altitude[t_{2/3}] => peak in the middle
        return findPictureTime(altitude, t1+1, t2-1);
    }
}
