package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.*;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {

    private String answer;

    private int steps = 6;

    private final WordleDictionary dictionary;

    private final Map<String, String> wrongWords = new LinkedHashMap<>();

    private final PrintWriter log;

    public WordleGame(WordleDictionary dictionary, PrintWriter log) {
        this.dictionary = dictionary;
        this.log = log;
    }

    public String checkWord(String input) {
        if (dictionary.getWords().isEmpty()) {
            throw new IllegalStateException("Словарь пуст, играть невозможно!");
        }

        if (input.isEmpty()) {
            printWrongWords(wrongWords);
        }

        steps--;

        log.println(input + " " + answer);
        log.flush();

        return suggestHint(input, answer);
    }

    public void getRandomWord() {
        Random random = new Random();
        int randomIndex = random.nextInt(dictionary.getWords().size());
        answer = dictionary.getWords().get(randomIndex);
    }

    private String suggestHint(String inputWord, String targetWord) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < targetWord.length(); i++) {
            if (targetWord.charAt(i) == inputWord.charAt(i)) {
                sb.append("+");
            } else if (targetWord.indexOf(inputWord.charAt(i)) != -1) {
                sb.append("^");
            } else {
                sb.append("-");
            }
        }

        wrongWords.put(inputWord, sb.toString());
        return sb.toString();
    }

    public void printWrongWords(Map<String, String> wrongWords) {
        for (String word : wrongWords.keySet()) {
            String hint = wrongWords.get(word);
            System.out.println(word + " " + hint);
        }
    }

    public boolean hasSteps() {
        return steps > 0;
    }
}
