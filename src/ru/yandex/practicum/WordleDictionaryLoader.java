package ru.yandex.practicum;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {
    private final Charset standardCharsets = StandardCharsets.UTF_8;

    public WordleDictionary openDictionary(String fileName) {
        List<String> wordleDictionary = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName), standardCharsets)) {
            while (br.ready()) {
                wordleDictionary.add(br.readLine());
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении." + fileName);
        }

        return new WordleDictionary(wordleDictionary);
    }

}
