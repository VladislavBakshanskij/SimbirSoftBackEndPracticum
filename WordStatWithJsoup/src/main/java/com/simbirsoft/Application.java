package com.simbirsoft;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Application {
    private static final Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите URL(https://www.simbirsoft.com/): ");
        String url = scanner.next();

        try (final FileWriter fw = new FileWriter("result_jsoup.txt")) {
            HtmlPageLoader pageLoader = new HtmlPageLoader(url);
            Document page = pageLoader.load();
            Map<String, Integer> wordMap = new HtmlPageWordUniqueParser().parse(page);

            for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
                String word = entry.getKey();
                Integer countRepeatWord = entry.getValue();
                try {
                    String message = word + " - " + countRepeatWord + "\n";
                    System.out.print(message);
                    fw.write(message);
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }

            System.out.println("\n\n\n\tИдет сохранение в базу данных.\n\tПодождите.....");
            DataBase.writeWordMap(wordMap);
            System.out.println("\n\n\n\tСохранение завершено!");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
