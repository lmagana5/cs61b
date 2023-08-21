package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.*;

public class PatternAwareLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PatternAwareLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN. */
    public char getGuess(String pattern, List<Character> guesses) {
        List<String> matchedWords;
        matchedWords = keepOnlyWordsThatMatchPattern(pattern);
        Map<Character, Integer> freqMap = getFreqMapThatMatchesPattern(matchedWords);
        if (freqMap.isEmpty()) {
            return '?';
        }

        for (Character guess : guesses) {
            freqMap.remove(guess);
        }

        return Collections.max(freqMap.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public Map<Character, Integer> getFreqMapThatMatchesPattern(List<String> matchedWords) {
        List<Character> chars = new ArrayList<>();
        Map<Character, Integer> frequency = new TreeMap<>();
        int count;
        for (String word : matchedWords) {
            word.chars().forEach(y -> chars.add((char) y));
        }

        for (char alphabet = 'a'; alphabet <= 'z'; ++alphabet) {
            if (!chars.contains(alphabet)) {
                continue;
            }
            count = Collections.frequency(chars, alphabet);
            frequency.put(alphabet, count);
        }

        return frequency;
    }


    public List<String> keepOnlyWordsThatMatchPattern(String pattern) {
        Map<Integer, Character> justLetters = new TreeMap<>();
        List<String> matchedPatternWords = new ArrayList<>();
        for (int i = 0; i< pattern.length(); i++) {
            if (Character.isLetter(pattern.charAt(i))) {
               justLetters.put(i, (pattern.charAt(i)));
            }
        }
        if (justLetters.isEmpty()) {
            return words;
        }

        for ( String word : words) {
            boolean match = false;
            if (word.length() != pattern.length()){
                continue;
            }
            for (Map.Entry<Integer, Character> entry : justLetters.entrySet()) {
                match = word.charAt(entry.getKey()) == entry.getValue();
                if(!match) {
                    break;
                }
            }
            if (match) {
                matchedPatternWords.add(word);
            }
        }
        return matchedPatternWords;
    }

    public static void main(String[] args) {
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser("data/example.txt");
        System.out.println(palfg.getGuess("-e--", List.of('e')));
    }
}