package ru.yandex.practicum;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;

class WordleTest {

    @Test
    void testOpenDictionary() throws IOException {
        try (PrintWriter logWriter = new PrintWriter(new FileWriter("test_game.log", true))) {
            WordleDictionaryLoader loader = new WordleDictionaryLoader(logWriter);

            Assertions.assertNotNull(loader.openDictionary("words_ru.txt"));
            Assertions.assertTrue(loader.openDictionary("words_en.txt").getWords().isEmpty(),
                    "Ожидается пустой словарь для несуществующего файла");
        } catch (IOException e) {
            System.out.println("Прозошла ошибка");;
        }
    }
}
