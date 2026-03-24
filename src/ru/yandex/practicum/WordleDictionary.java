package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.List;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {
    private final List<String> words;
    private PrintWriter log;

    public WordleDictionary(List<String> words, PrintWriter log) {
        this.words = words;
        this.log = log;
        changeWordsList();
    }

    public void changeWordsList() {
        int initialSize = words.size();

        words.removeIf(word -> word.length() != 5);
        words.replaceAll(word -> word.toLowerCase().replace("ё", "е"));

        int finalSize = words.size();
        int removedCount = initialSize - finalSize;

        log.println("Очистка словаря завершена:");
        log.println("- Исходное количество слов: " + initialSize);
        log.println("- Удалено (не подходят по длине): " + removedCount);
        log.println("- Итого доступно для игры: " + finalSize);
        log.flush();
    }

    public List<String> getWords() {
        return words;
    }
}
