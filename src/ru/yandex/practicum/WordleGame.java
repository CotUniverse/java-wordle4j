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
    Random random = new Random();

    private String answer;

    private int steps = 6;

    private final WordleDictionary dictionary;

    private final PrintWriter log;

    private final Set<Character> absentLetters = new HashSet<>();

    private final Map<Integer, Character> confirmedLetters = new LinkedHashMap<>();

    private final Set<Character> presentLetters = new HashSet<>();

    public WordleGame(WordleDictionary dictionary, PrintWriter log) {
        this.dictionary = dictionary;
        this.log = log;
    }

    public String checkWord(String input) throws DictionaryException, WordNotFoundInDictionary, WrongWordLengthException {
        if (dictionary.getWords().isEmpty()) {
            throw new DictionaryException("Словарь пуст, играть невозможно!");
        }

        if (input.isEmpty()) {
            return autoSuggest();
        }

        if (input.length() != 5) {
            throw new WrongWordLengthException("Слово должно быть из 5 букв");
        }

        String hint = suggestHint(input, answer);

        steps--;

        log.println(input + " " + answer);
        log.flush();

        return hint;
    }

    public void getRandomWord() {
        int randomIndex = random.nextInt(dictionary.getWords().size());
        answer = dictionary.getWords().get(randomIndex);
    }

    private String suggestHint(String inputWord, String targetWord)
            throws WordNotFoundInDictionary {

        if (!dictionary.getWords().contains(inputWord)) {
            throw new WordNotFoundInDictionary("Введеное вами слово отсутсвует в словаре");
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < targetWord.length(); i++) {
            char current = inputWord.charAt(i);
            if (targetWord.charAt(i) == current) {
                confirmedLetters.put(i, current);
                sb.append("+");
            } else if (targetWord.indexOf(current) != -1) {
                presentLetters.add(current);
                sb.append("^");
            } else {
                absentLetters.add(current);
                sb.append("-");
            }
        }

        return sb.toString();
    }

    public String autoSuggest() {
        List<String> allWords = dictionary.getWords();
        List<String> possibleWords = new ArrayList<>();

        for (String word : allWords) {
            if (isWordMatching(word)) {
                possibleWords.add(word);
            }
        }

        return possibleWords.get(random.nextInt(possibleWords.size()));
    }

    public boolean isWordMatching(String word) {
        for (char c : word.toCharArray()) {
            if (absentLetters.contains(c))
                return false;
        }

        for (Map.Entry<Integer, Character> entry : confirmedLetters.entrySet()) {
            if (word.charAt(entry.getKey()) != entry.getValue())
                return false;
        }

        for (char c : presentLetters) {
            if (word.indexOf(c) == -1) return false;
        }

        return true;
    }

    public boolean hasSteps() {
        return steps > 0;
    }
}
