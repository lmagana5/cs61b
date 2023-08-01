import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
    public static int sum(List<Integer> L) {
        if (L.isEmpty()) {
            return 0;
        }
        int sum = 0;
        for (int i : L) {
            sum += i;
        }
        return sum;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        List<Integer> evenNums = new ArrayList<>();
        for (int i : L) {
            if (i % 2 == 0) {
                evenNums.add(i);
            }
        }
        return evenNums;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {

        List<Integer> commonNums = new ArrayList<>();

        for (int i : L1) {
            if (L2.contains(i)) {
                commonNums.add(i);
            }
        }
        return commonNums;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        int total = 0;
        for (String elem : words) {
            for (int i = 0; i < elem.length(); i++) {
                if (elem.charAt(i) == c) {
                    total += 1;
                }
            }
        }

        return total;
    }
}
