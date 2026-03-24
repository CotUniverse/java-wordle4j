package ru.yandex.practicum;

import java.util.Random;
import java.util.Scanner;

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

    Scanner scanner = new Scanner(System.in);

    public WordleGame(WordleDictionary dictionary) {
        this.dictionary = dictionary;
    }

    public void wordleGame() {

        Random random = new Random();
        int randomIndex = random.nextInt(dictionary.getWords().size());
        answer = dictionary.getWords().get(randomIndex);
        System.out.println(answer);
        while (steps >= 0) {
            System.out.println("Введите слово");
            String inputWord = scanner.nextLine();
            if (inputWord.equals(answer)) {
                System.out.println("Урааа! Вы угадали слово!!!!");
                break;
            } else {
                System.out.println(suggestHint(inputWord, answer));
                steps--;
            }
        }
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

        return sb.toString();
    }
}
