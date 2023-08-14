package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.*;

public class NaiveLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public NaiveLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Makes a guess which ignores the given pattern. */
    public char getGuess(String pattern, List<Character> guesses) {
        return getGuess(guesses);
    }

    /** Returns a map from a given letter to its frequency across all words.
     *  This task is similar to something you did in hw0b! */
    public Map<Character, Integer> getFrequencyMap() {
        List<Character> chars = new ArrayList<>();
        Map<Character, Integer> frequency = new TreeMap<>();
        int count;
        for (String word : words) {
            word.chars().forEach(y -> chars.add((char) y));
        }

        for (char alphabet = 'a'; alphabet <= 'z'; ++alphabet){
            if (!chars.contains(alphabet)) {
                continue;
            }
            count = Collections.frequency(chars, alphabet);
            frequency.put(alphabet, count);
        }

        return frequency;
    }

    /** Returns the most common letter in WORDS that has not yet been guessed
     *  (and therefore isn't present in GUESSES). */
    public char getGuess(List<Character> guesses) {
        Map<Character, Integer> freqMap = getFrequencyMap();

        if (freqMap.isEmpty()) {
            return '?';
        }

        for (Character guess : guesses) {
            freqMap.remove(guess);
        }

        return Collections.max(freqMap.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public static void main(String[] args) {
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser("data/example.txt");
        System.out.println("list of words: " + nlfg.words);
        System.out.println("frequency map: " + nlfg.getFrequencyMap());

        List<Character> guesses = List.of('e', 'l');
        System.out.println("guess: " + nlfg.getGuess(guesses));
    }
}
