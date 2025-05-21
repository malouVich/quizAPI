package app.routes;

import app.security.controllers.AccessController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {

    private final QuestionRoute questionRoute = new QuestionRoute();
    private final AnswerRoute answerRoute = new AnswerRoute();
    private static AccessController accessController = new AccessController();

    public EndpointGroup getRoutes() {
        return () -> {
                path("/game", questionRoute.getRoutes());
                path("/answers", answerRoute.getRoutes());
        };
    }
}