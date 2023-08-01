import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapExercises {
    /**
     * Returns a map from every lower case letter to the number corresponding to that letter, where 'a' is
     * 1, 'b' is 2, 'c' is 3, ..., 'z' is 26.
     */


    public static Map<Character, Integer> letterToNum() {
        Map<Character, Integer> map = new HashMap<>();
        int value = 1;
        for (char key = 'a'; key <= 'z'; ++key) {
            map.put(key, value);
            value++;

        }
        return map;
    }

    /** Returns a map from the integers in the list to their squares. For example, if the input list
     *  is [1, 3, 6, 7], the returned map goes from 1 to 1, 3 to 9, 6 to 36, and 7 to 49.
     */
    public static Map<Integer, Integer> squares(List<Integer> nums) {
        Map<Integer, Integer> squared = new HashMap<>();
        for (int values : nums) {
            squared.put(values, values * values);
        }
        return squared;
    }

    /** Returns a map of the counts of all words that appear in a list of words. */
    public static Map<String, Integer> countWords(List<String> words) {
        Map<String, Integer> wordMap = new HashMap<>();
        for (String elem : words) {
            int count = 0;
            for (String compare : words) {
                if (elem.equals(compare)) {
                    count++;
                }
            }
            wordMap.put(elem, count);
        }
        return wordMap;
    }
}
