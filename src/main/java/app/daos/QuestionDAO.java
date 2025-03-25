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

    public static QuestionDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new QuestionDAO();
        }
        return instance;
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

    public QuestionDTO create(QuestionDTO questionDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Question question = new Question(questionDTO);
            em.persist(question);
            em.getTransaction().commit();
            return new QuestionDTO(question);
        }
    }
}
