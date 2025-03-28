package app.daos;

import app.dtos.AnswerDTO;
import app.dtos.QuestionDTO;
import app.entities.Answer;
import app.entities.Question;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class AnswerDAO {
    private static AnswerDAO instance;
    private static EntityManagerFactory emf;

    public static AnswerDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new AnswerDAO();
        }
        return instance;
    }

    public QuestionDTO addAnswerToQuestion(Integer hotelId, AnswerDTO answerDTO ) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Answer answer = new Answer(answerDTO);
            Question question = em.find(Question.class, hotelId);
            question.addAnswer(answer);
            em.persist(answer);
            Question mergedQuestion = em.merge(question);
            em.getTransaction().commit();
            return new QuestionDTO(mergedQuestion);
        }
    }

    public Answer create(Answer answer) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(answer);
            em.flush();
            em.getTransaction().commit();
            return answer;
        }finally {
            emf.close();
        }
    }

    public AnswerDTO read(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Answer answer = em.find(Answer.class, integer);
            return answer != null ? new AnswerDTO(answer) : null;
        }
    }

    public List<AnswerDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<AnswerDTO> query = em.createQuery("SELECT new app.dtos.AnswerDTO(a) FROM Answer a", AnswerDTO.class);
            return query.getResultList();
        }
    }


    public AnswerDTO update(Integer integer, AnswerDTO answerDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Answer a = em.find(Answer.class, integer);
            if(a == null){
                em.getTransaction().rollback();
                return null;
            }
            a.setAnswerText(answerDTO.getAnswerText());
            a.setIsCorrect(answerDTO.getIsCorrect());
            Answer mergedAnswer = em.merge(a);
            em.getTransaction().commit();
            return new AnswerDTO(mergedAnswer);
        }
    }

    public void delete(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Answer answer = em.find(Answer.class, integer);
            if (answer != null){
                em.remove(answer);
            }
            em.getTransaction().commit();
        }
    }

    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Answer answer = em.find(Answer.class, integer);
            return answer != null;
        }
    }
}