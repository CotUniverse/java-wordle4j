package ru.yandex.practicum;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {
    public static final String FILE_NAME = "words_ru.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (PrintWriter logWriter = new PrintWriter(new FileWriter("game.log", true));) {

            WordleDictionaryLoader loader = new WordleDictionaryLoader(logWriter);

            WordleDictionary myDictionary = loader.openDictionary(FILE_NAME);

            WordleGame wordleGame = new WordleGame(myDictionary, logWriter);

            wordleGame.getRandomWord();

            while (wordleGame.hasSteps()) {
                System.out.println("Введите слово");
                try {
                    String input = scanner.nextLine().toLowerCase();
                    String hint = wordleGame.checkWord(input);
                    if (hint.equals("+++++")) {
                        System.out.println("Вы победили!");
                        break;
                    } else {
                        System.out.println(hint);
                    }

                } catch (WordNotFoundInDictionary | WrongWordLengthException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (DictionaryException | IOException e) {
            System.out.println("Прозошла ошибка");
        }

    }

}
