package app.routes;

import app.controllers.AnswerController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.delete;

public class AnswerRoute {

    private final AnswerController answerController = new AnswerController();

    protected EndpointGroup getRoutes() {

        return () -> {
            post("/hotel/{id}", answerController::create);
            get("/", answerController::readAll);
            get("/{id}", answerController::read);
            put("/{id}", answerController::update);
            delete("/{id}", answerController::delete);
        };
    }
}
