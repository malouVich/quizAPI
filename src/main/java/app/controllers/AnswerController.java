package app.controllers;

import app.config.HibernateConfig;
import app.daos.AnswerDAO;
import app.dtos.AnswerDTO;
import app.dtos.QuestionDTO;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class AnswerController {

    private AnswerDAO answerDAO;

    public AnswerController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.answerDAO = AnswerDAO.getInstance(emf);
    }

    public void create(Context ctx) {
        // request
        AnswerDTO jsonRequest = validateEntity(ctx);

        int questionId = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();

        QuestionDTO questionDTO = answerDAO.addAnswerToQuestion(questionId, jsonRequest);
        // response
        ctx.res().setStatus(201);
        ctx.json(questionDTO, QuestionDTO.class);
    }

    public void read(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // entity
        AnswerDTO answerDTO = answerDAO.read(id);
        // response
        ctx.res().setStatus(200);
        ctx.json(answerDTO, AnswerDTO.class);
    }

    public void readAll(Context ctx) {
        // entity
        List<AnswerDTO> answerDTOS = answerDAO.readAll();
        // response
        ctx.res().setStatus(200);
        ctx.json(answerDTOS, AnswerDTO.class);
    }

    public void update(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // entity
        AnswerDTO answerDTO = answerDAO.update(id, validateEntity(ctx));
        // response
        ctx.res().setStatus(200);
        ctx.json(answerDTO, AnswerDTO.class);
    }

    public void delete(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // entity
        answerDAO.delete(id);
        // response
        ctx.res().setStatus(204);
    }


    public boolean validatePrimaryKey(Integer integer) {return answerDAO.validatePrimaryKey(integer);}

    public AnswerDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(AnswerDTO.class)
                .check(a -> a.getAnswerText() != null && !a.getAnswerText().isEmpty() , "No answer text")
                .check(a -> a.getIsCorrect() != null, "Not a valid correct answer")
                .get();
    }
}
