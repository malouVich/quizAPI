package app.routes;

import app.controllers.AnswerController;
import app.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.delete;

public class AnswerRoute {

    private final AnswerController answerController = new AnswerController();

    protected EndpointGroup getRoutes() {

        return () -> {
            post("/answer/{id}", answerController::create, Role.ANYONE);
            get("/", answerController::readAll, Role.ANYONE);
            get("/{id}", answerController::read, Role.ANYONE);
            put("/{id}", answerController::update, Role.ANYONE);
            delete("/{id}", answerController::delete, Role.ANYONE);
        };
    }
}