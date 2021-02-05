package org.simbirsoft;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class HtmlPageWordUniqueParser {
    private final Map<String, Integer> wordMap;
    private final List<Character> rusAlphabet;

    public HtmlPageWordUniqueParser() {
        wordMap = new TreeMap<>();
        rusAlphabet = new ArrayList<>();

        for (char i = 'а'; i < 'я'; i++) {
            rusAlphabet.add(i);
        }
    }

    public Map<String, Integer> getWordMap() {
        return wordMap;
    }

    public Map<String, Integer> parse(String html) {
        final int startBodyIndex = html.indexOf("<body");
        final int endBodyIndex = html.lastIndexOf("</body");

        String bodyText = html.substring(startBodyIndex, endBodyIndex);

        for (int i = 0; i < bodyText.length(); i++) {
            char c = bodyText.charAt(i);

            if (isRussianLetter(c)) {
                int start = i;

                while (isRussianLetter(c)) {
                    i++;
                    c = bodyText.charAt(i);
                }

                final String word = clean(bodyText.substring(start, i).toUpperCase());

                if (wordMap.containsKey(word)) {
                    final Integer countRepeatWord = wordMap.get(word);
                    wordMap.put(word, countRepeatWord + 1);
                } else {
                    wordMap.put(word, 1);
                }
            }
        }

        return wordMap;
    }

    private String clean(String word) {
        word = word.replaceAll("\\s+", "");
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            if (!isRussianLetter(c)) {
                return word.substring(0, i);
            }
        }

        return word;
    }

    private boolean isRussianLetter(char symbol) {
        return rusAlphabet.contains(Character.toLowerCase(symbol));
    }
}
