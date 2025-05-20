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
            post("/", questionController::create);
            get("/re", questionController::readAll);
            get("/{id}", questionController::read);
            put("/{id}", questionController::update);
            delete("/{id}", questionController::delete);
        };
    }
}