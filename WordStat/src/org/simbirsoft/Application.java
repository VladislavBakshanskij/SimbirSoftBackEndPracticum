package org.simbirsoft;


import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        System.out.print("Введите URL(https://www.simbirsoft.com/): ");
        Scanner scanner = new Scanner(System.in);
        String url = scanner.next();

        try (FileWriter fileWriter = new FileWriter("result.txt")) {
            HtmlPageLoader htmlPageLoader = new HtmlPageLoader(url);
            HtmlPageWordUniqueParser parser = new HtmlPageWordUniqueParser();
            Map<String, Integer> wordMap = parser.parse(htmlPageLoader.load());

            for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
                String word = entry.getKey();
                Integer countRepeatWord = entry.getValue();

                try {
                    String message = word + " - " + countRepeatWord + "\n";
                    System.out.print(message);
                    fileWriter.write(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
