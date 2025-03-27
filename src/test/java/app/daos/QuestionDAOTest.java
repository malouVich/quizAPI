package app.daos;

import app.config.HibernateConfig;
import app.dtos.QuestionDTO;
import app.entities.Question;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class QuestionDAOTest {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static QuestionDAO questionDAO = QuestionDAO.getInstance(emf);


    @BeforeEach
    void setUp() {
        // Ryd databasen før hver test
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Question").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @AfterAll
    static void tearDownClass() {
        emf.close();
    }

    @Test
    @Order(1)
    void testCreate() {
        Question question = new Question();
        question.setQuestionText("Hvad er 2+2?");
        question.setDifficultyType(Question.DifficultyType.EASY);

        Question savedQuestion = questionDAO.create(question);
        assertNotNull(savedQuestion);
        assertNotNull(savedQuestion.getId());
        assertEquals("Hvad er 2+2?", savedQuestion.getQuestionText());
    }

    @Test
    @Order(2)
    void testRead() {
        Question question = new Question();
        question.setQuestionText("Hvad er 3+3?");
        question.setDifficultyType(Question.DifficultyType.MEDIUM);

        Question savedQuestion = questionDAO.create(question);
        QuestionDTO fetchedQuestion = questionDAO.read(savedQuestion.getId());

        assertNotNull(fetchedQuestion);
        assertEquals("Hvad er 3+3?", fetchedQuestion.getQuestionText());
    }

    @Test
    @Order(3)
    void testReadAll() {
        Question q1 = new Question();
        q1.setQuestionText("Spørgsmål 1");
        q1.setDifficultyType(Question.DifficultyType.EASY);

        Question q2 = new Question();
        q2.setQuestionText("Spørgsmål 2");
        q2.setDifficultyType(Question.DifficultyType.HARD);

        questionDAO.create(q1);
        questionDAO.create(q2);

        List<QuestionDTO> questions = questionDAO.readAll();
        assertEquals(2, questions.size());
    }

    @Test
    @Order(4)
    void testUpdate() {
        Question question = new Question();
        question.setQuestionText("Gammel tekst");
        question.setDifficultyType(Question.DifficultyType.MEDIUM);

        Question savedQuestion = questionDAO.create(question);

        QuestionDTO updatedDTO = new QuestionDTO();
        updatedDTO.setQuestionText("Ny tekst");
        updatedDTO.setDifficultyType(Question.DifficultyType.HARD);

        QuestionDTO updatedQuestion = questionDAO.update(savedQuestion.getId(), updatedDTO);

        assertNotNull(updatedQuestion);
        assertEquals("Ny tekst", updatedQuestion.getQuestionText());
        assertEquals(Question.DifficultyType.HARD, updatedQuestion.getDifficultyType());
    }

    @Test
    @Order(5)
    void testDelete() {
        Question question = new Question();
        question.setQuestionText("Slet mig");
        question.setDifficultyType(Question.DifficultyType.MEDIUM);

        Question savedQuestion = questionDAO.create(question);
        questionDAO.delete(savedQuestion.getId());

        QuestionDTO deletedQuestion = questionDAO.read(savedQuestion.getId());
        assertNull(deletedQuestion);
    }

    @Test
    @Order(6)
    void testValidatePrimaryKey() {
        Question question = new Question();
        question.setQuestionText("Er jeg her?");
        question.setDifficultyType(Question.DifficultyType.EASY);

        Question savedQuestion = questionDAO.create(question);
        assertTrue(questionDAO.validatePrimaryKey(savedQuestion.getId()));

        questionDAO.delete(savedQuestion.getId());
        assertFalse(questionDAO.validatePrimaryKey(savedQuestion.getId()));
    }
}
