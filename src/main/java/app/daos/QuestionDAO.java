package app.daos;

import app.dtos.QuestionDTO;
import app.entities.Question;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
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

    public QuestionDTO create(QuestionDTO questionDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Question question = new Question(questionDTO);
            em.persist(question);
            em.getTransaction().commit();
            return new QuestionDTO(question);
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
            TypedQuery<QuestionDTO> query = em.createQuery("SELECT new app.dtos.QuestionDTO(q) FROM Question q", QuestionDTO.class);
            return query.getResultList();
        }
    }


    public QuestionDTO update(Integer integer, QuestionDTO questionDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Question q = em.find(Question.class, integer);
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
