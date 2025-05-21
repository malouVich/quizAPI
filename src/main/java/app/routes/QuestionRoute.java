package app.routes;

import app.controllers.QuestionController;
import app.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.delete;

public class QuestionRoute {

    private final QuestionController questionController = new QuestionController();

    protected EndpointGroup getRoutes() {

        return () -> {
            post("/questions/{id}", questionController::create, Role.ANYONE);
            get("/", questionController::readAll, Role.ANYONE);
            get("/{id}", questionController::read, Role.ANYONE);
            put("/{id}", questionController::update, Role.ANYONE);
            delete("/{id}", questionController::delete, Role.ANYONE);
        };
    }
}