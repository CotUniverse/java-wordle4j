package ru.yandex.practicum;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleTest {

    @Test
    void testCheckWord() throws IOException {
        try (PrintWriter logWriter = new PrintWriter(new FileWriter("test_game.log", true))) {
            List<String> words = new ArrayList<>(List.of("гамаг"));
            WordleDictionary myDictionary = new WordleDictionary(words, logWriter);
            WordleGame wordleGame = new WordleGame(myDictionary, logWriter);
            wordleGame.getRandomWord();
            Assertions.assertEquals("+++++", wordleGame.checkWord("гамаг"));
            Assertions.assertEquals("^+^--", wordleGame.checkWord("маарт"));
        }
    }

    @Test
    void testOpenDictionary() throws IOException {
        try (PrintWriter logWriter = new PrintWriter(new FileWriter("test_loader.log", true))) {
            WordleDictionaryLoader loader = new WordleDictionaryLoader(logWriter);

            Assertions.assertNotNull(loader.openDictionary("words_ru.txt"));
            Assertions.assertTrue(loader.openDictionary("words_en.txt").getWords().isEmpty(),
                    "Ожидается пустой словарь для несуществующего файла");
        }
    }
}
