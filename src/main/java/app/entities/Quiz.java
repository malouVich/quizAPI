package app.entities;

import java.util.HashMap;
import java.util.Map;

public class Quiz {

    private Map<Question, Question.DifficultyType> difficulty;

    public Quiz() {
        difficulty = new HashMap<>();
    }

    // Tilføj spørgsmål med en sværhedsgrad
    public void addQuestion(Question question, Question.DifficultyType difficultyLevel) {
        difficulty.put(question, difficultyLevel);
    }

    // Hent sværhedsgraden for et bestemt spørgsmål
    public Question.DifficultyType getDifficulty(Question question) {
        return difficulty.get(question);
    }

    // Udskriv alle spørgsmål og deres sværhedsgrader
    public void printQuestions() {
        for (Map.Entry<Question, Question.DifficultyType> entry : difficulty.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
