package app.controllers;

import app.config.HibernateConfig;
import app.daos.QuestionDAO;
import app.dtos.QuestionDTO;
import app.entities.Question;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class QuestionController {

    private final QuestionDAO questionDAO;

    public QuestionController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.questionDAO = QuestionDAO.getInstance(emf);
    }



    public void create(Context ctx) {
        // request
        Question jsonRequest = ctx.bodyAsClass(Question.class);
        // DTO
        Question question = questionDAO.create(jsonRequest);
        // response
        ctx.res().setStatus(201);
        ctx.json(question, QuestionDTO.class);
    }

    public void read(Context ctx)  {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // DTO
        QuestionDTO questionDTO = questionDAO.read(id);
        // response
        ctx.res().setStatus(200);
        ctx.json(questionDTO, QuestionDTO.class);
    }


    public void readAll(Context ctx) {
        // List of DTOS
        List<QuestionDTO> questionDTOS = questionDAO.readAll();
        // response
        ctx.res().setStatus(200);
        ctx.json(questionDTOS, QuestionDTO.class);
    }

    public void update(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // dto
        QuestionDTO questionDTO = questionDAO.update(id, validateEntity(ctx));
        // response
        ctx.res().setStatus(200);
        ctx.json(questionDTO, QuestionDTO.class);
    }


    public void delete(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        questionDAO.delete(id);
        // response
        ctx.res().setStatus(204);
    }


    public boolean validatePrimaryKey(Integer integer) {
        return questionDAO.validatePrimaryKey(integer);
    }


    public QuestionDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(QuestionDTO.class)
                .check( q -> q.getQuestionText() != null && !q.getQuestionText().isEmpty(), "The question text must be filled out")
                .check( q -> q.getDifficultyType() != null, "Question type must be set")
                .get();
    }
}