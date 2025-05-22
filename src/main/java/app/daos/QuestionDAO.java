package app.daos;

import app.dtos.QuestionDTO;
import app.entities.Answer;
import app.entities.Question;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class QuestionDAO {

    private static QuestionDAO instance;
    private static EntityManagerFactory emf;

    private QuestionDAO(){}

    public static QuestionDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new QuestionDAO();
        }
        return instance;
    }

    public Question create(Question question) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            if (question.getAnswers() != null) {
                for (Answer answer : question.getAnswers()) {
                    answer.setQuestion(question);
                }
            }

            em.persist(question);
            em.flush();
            em.getTransaction().commit();
            return question;
        }
    }


    public QuestionDTO read(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Question question = em.find(Question.class, integer);
            return new QuestionDTO(question);
        }
    }

    public List<QuestionDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Question> query = em.createQuery(
                    "SELECT DISTINCT q FROM Question q LEFT JOIN FETCH q.answers ORDER BY q.id",
                    Question.class
            );
            List<Question> questions = query.getResultList();
            return questions.stream().map(QuestionDTO::new).toList();
        }
    }



    public QuestionDTO update(Integer integer, QuestionDTO questionDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Question q = em.find(Question.class, integer);
            if(q == null){
                em.getTransaction().rollback();;
                return null;
            }
            q.setQuestionText(questionDTO.getQuestionText());
            q.setDifficultyType(questionDTO.getDifficultyType());
            Question mergedQuestion = em.merge(q);
            em.getTransaction().commit();
            return mergedQuestion != null ? new QuestionDTO(mergedQuestion) : null;
        }
    }


    public void delete(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Question question = em.find(Question.class, integer);
            if (question != null) {
                Query query = em.createQuery("DELETE FROM Answer a WHERE a.question.id = :questionId");
                query.setParameter("questionId",integer);
                query.executeUpdate();
                em.remove(question);
            }
            em.getTransaction().commit();
        }
    }

    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Question question = em.find(Question.class, integer);
            return question != null;
        }
    }
}