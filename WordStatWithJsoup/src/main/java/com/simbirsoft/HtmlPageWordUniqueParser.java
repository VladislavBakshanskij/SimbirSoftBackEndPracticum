package com.simbirsoft;

import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

public class HtmlPageWordUniqueParser {
    private Map<String, Integer> wordMap;

    public Map<String, Integer> parse(@NotNull Document document) {
        return parse(document.body());
    }

    public Map<String, Integer> getWordMap() {
        return wordMap;
    }

    public Map<String, Integer> parse(@NotNull Element body) {
        wordMap = new TreeMap<>();
        List<String> words = new ArrayList<>();
        words.addAll(getWordsFromElement(body));
        words.addAll(getWordsFromImageAltAttribute(body));

        for (String word : words) {
            if (word.isEmpty()) {
                continue;
            }

            if (!wordMap.containsKey(word)) {
                wordMap.put(word, 0);
            }

            wordMap.put(word, wordMap.get(word) + 1);
        }

        return wordMap;
    }

    private List<String> getWordsFromImageAltAttribute(Element e) {
        Elements images = e.getElementsByTag("img");
        List<String> words = new ArrayList<>();

        for (Element image : images) {
            final String imageText = image.attr("alt");

            if (!imageText.isEmpty()) {
                words.addAll(getWords(imageText));
            }
        }

        return words;
    }

    private List<String> getWordsFromElement(Element e) {
        return getWords(e.text());
    }

    private List<String> getWords(String str) {
        StringBuilder sb = new StringBuilder(str);
        char symbol;

        for (int i = 0; i < str.length(); i++) {
            symbol = str.charAt(i);

            if (!Character.isLetter(symbol)) {
                sb.replace(i, i + 1, " ");
            }
        }

        return Arrays.asList(sb.toString().toUpperCase().replaceAll("\\s+", " ").split(" "));
    }
}
