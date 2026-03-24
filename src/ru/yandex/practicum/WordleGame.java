package ru.yandex.practicum;

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

    Scanner scanner = new Scanner(System.in);

    public WordleGame(WordleDictionary dictionary) {
        this.dictionary = dictionary;
    }

    public void wordleGame() {
        if (dictionary.getWords().isEmpty()) {
            throw new IllegalStateException("Словарь пуст, играть невозможно!");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(dictionary.getWords().size());
        answer = dictionary.getWords().get(randomIndex);

        while (steps > 0) {
            System.out.println("Введите слово");
            String inputWord = scanner.nextLine();
            if (inputWord.length() != answer.length()) {
                System.out.println("Ошибка! Слово должно содержать " + answer.length() + " букв.");
            } else if (inputWord.equals(answer)) {
                System.out.println("Урааа! Вы угадали слово!!!!");
                break;
            } else if (inputWord.isEmpty()) {
                printWrongWords(wrongWords);
            } else {
                System.out.println(suggestHint(inputWord, answer));
                steps--;
            }
        }

        if (steps == 0)
            System.out.println("Вы проиграли. Правильное слово - " + answer);
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

    private void printWrongWords(Map<String, String> wrongWords) {
        for (String word : wrongWords.keySet()) {
            String hint = wrongWords.get(word);
            System.out.println(word + " " + hint);
        }
    }
}
