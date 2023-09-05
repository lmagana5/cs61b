package aoa.choosers;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import aoa.utils.FileUtils;

import java.util.*;

public class RandomChooser implements Chooser {
    private final String chosenWord;
    private String pattern;

    public RandomChooser(int wordLength, String dictionaryFile) {
        if (wordLength < 1) {
            throw new IllegalArgumentException();
        }
        List<String> words = FileUtils.readWordsOfLength(dictionaryFile, wordLength);
        if (words.isEmpty()) {
            throw new IllegalStateException();
        }
        int numWords = words.size();
        int randomlyChosenWordNumber = StdRandom.uniform(numWords);
        chosenWord = words.get(randomlyChosenWordNumber);
        pattern = "-".repeat(wordLength);
    }

    @Override
    public int makeGuess(char letter) {
        int occurances = 0;
        StringBuilder updatedPattern = new StringBuilder(pattern);
        List<Integer> patternIndexes = new ArrayList<>();
        for (int i = 0; i < chosenWord.length(); i++) {
            if (chosenWord.charAt(i) == letter) {
                occurances++;
                patternIndexes.add(i);
            }
        }
        for (int indexes : patternIndexes) {
            updatedPattern.setCharAt(indexes, letter);
        }
        pattern = updatedPattern.toString();

        return occurances;
    }

    @Override
    public String getPattern() {
        return pattern;
    }

    @Override
    public String getWord() {
        return chosenWord;
    }
}
