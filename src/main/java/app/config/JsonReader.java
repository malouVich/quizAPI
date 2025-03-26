package app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import app.daos.QuestionDAO;
import app.daos.AnswerDAO;
import app.dtos.QuestionDTO;
import app.dtos.AnswerDTO;
import app.entities.Question;
import app.entities.Answer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonReader {
    private QuestionDAO questionDAO;
    private AnswerDAO answerDAO;
    private EntityManagerFactory entityManagerFactory;

    public JsonReader(EntityManagerFactory entityManagerFactory) {
        this.questionDAO = QuestionDAO.getInstance(entityManagerFactory);
        this.answerDAO = AnswerDAO.getInstance(entityManagerFactory);
        this.entityManagerFactory = entityManagerFactory;
    }

    public void populateDatabase() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Læs JSON-filen og konverter den til en liste af QuestionDTO objekter
            List<QuestionDTO> questions = objectMapper.readValue(
                    new File("src/main/resources/http/questions.json"),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, QuestionDTO.class));

            // Start en session
            try (EntityManager em = entityManagerFactory.createEntityManager()) {
                em.getTransaction().begin();  // Start transaction

                for (QuestionDTO questionDTO : questions) {
                    // Konverter QuestionDTO til Question entity
                    Question question = new Question();
                    question.setQuestionText(questionDTO.getQuestionText());
                    question.setDifficultyType(questionDTO.getDifficultyType());

                    // Gem Question i databasen
                    em.persist(question);  // Gem Question direkte i den aktive session

                    // Nu gemmer vi Answer for hver Question
                    if (questionDTO.getAnswers() != null) {
                        for (AnswerDTO answerDTO : questionDTO.getAnswers()) {
                            Answer answer = new Answer();
                            answer.setAnswerText(answerDTO.getAnswerText());
                            answer.setIsCorrect(answerDTO.getIsCorrect());
                            answer.setQuestion(question); // Sæt relationen til den allerede gemte Question

                            em.persist(answer); // Gem Answer i databasen
                        }
                    }
                }

                em.getTransaction().commit();  // Commit transaktionen
                System.out.println("Alle data er blevet gemt i databasen!");

            } catch (Exception e) {
                e.printStackTrace();
                // Hvis der er fejl, rulles transaktionen tilbage
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Skab en EntityManagerFactory instans
        EntityManagerFactory entityManagerFactory = HibernateConfig.getEntityManagerFactory();
        JsonReader jsonReader = new JsonReader(entityManagerFactory);
        jsonReader.populateDatabase();
    }
}
