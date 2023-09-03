package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.*;

public class PAGALetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PAGALetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN and the GUESSES that have been made. */
    public char getGuess(String pattern, List<Character> guesses) {
        List<String> updatedWords = removeIncorrectGuesses(guesses, pattern);
        Map<Character, Integer> freqMap = getFreqMapThatMatchesPattern(updatedWords);

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


    public List<String> keepOnlyWordsThatMatchPattern(List<String> updatedWords, String pattern) {
        Map<Integer, Character> justLetters = new TreeMap<>();
        List<String> matchedPatternWords = new ArrayList<>();
        //Create a map that uses the index of the letter as key and letter as value
        for (int i = 0; i < pattern.length(); i++) {
            if (Character.isLetter(pattern.charAt(i))) {
                justLetters.put(i, (pattern.charAt(i)));
            }
        }
        //if no letters in the pattern, return the current list of update words
        if (justLetters.isEmpty()) {
            return updatedWords;

        }
        //Test if word matches correct pattern length and letters in pattern match the index in each word
        for (String word : updatedWords) {
            boolean match = false;
            if (word.length() != pattern.length()) {
                continue;
            }
            for (Map.Entry<Integer, Character> entry : justLetters.entrySet()) {
                match = word.charAt(entry.getKey()) == entry.getValue();
                if (!match) {
                    break;
                }
            }
            if (match) {
                matchedPatternWords.add(word);
            }
        }
        return matchedPatternWords;
    }

    public List<String> removeIncorrectGuesses(List<Character> guesses, String pattern) {
        StringBuilder wrongGuesses = new StringBuilder();
        List<String> updateWords = new ArrayList<>();
        List<String> matchedWords;
        //Create a String of guesses that did not match pattern
        for (Character guess : guesses) {
            if (!pattern.contains(String.valueOf(guess))) {
                wrongGuesses.append(guess);
            }
        }
        //Remove words that contain any of the wrong guessed letters
        for (int i = 0; i < wrongGuesses.length(); i++) {
            for (String word : words) {
                if (!word.contains(String.valueOf(wrongGuesses.charAt(i)))) {
                    updateWords.add(word);

                }
            }
        }
        //Remove words that don't match pattern
        if (!updateWords.isEmpty()) {
            matchedWords = keepOnlyWordsThatMatchPattern(updateWords, pattern);
        }
        else {
            matchedWords = keepOnlyWordsThatMatchPattern(words, pattern);
        }
        //Remove words that have duplicate letters that don't match pattern and return final list of words
        return removeWrongMultipleLetters(guesses, pattern, matchedWords);
    }

    public List<String> removeWrongMultipleLetters(List<Character> guesses, String pattern, List<String> matchedWords) {
        List<String> onlyCorrectWords = new ArrayList<>();
        for (String doubles : matchedWords) {
            boolean match = true;
            for (int i = 0; i < doubles.length(); i++) {
                if (doubles.charAt(i) == pattern.charAt(i)) {
                    continue;
                }
                if (guesses.contains(doubles.charAt(i))) {
                    match = false;
                    break;
                }
            }
            if (match) {
                onlyCorrectWords.add(doubles);
            }
        }
        return onlyCorrectWords;
    }
    public static void main(String[] args) {
        PAGALetterFreqGuesser pagalfg = new PAGALetterFreqGuesser("data/example.txt");
        System.out.println(pagalfg.getGuess("----", List.of('e')));
    }
}
