package app.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {

    private final QuestionRoute questionRoute = new QuestionRoute();
    private final AnswerRoute answerRoute = new AnswerRoute();

    public EndpointGroup getRoutes() {
        return () -> {
                path("/hotels", questionRoute.getRoutes());
                path("/rooms", answerRoute.getRoutes());
        };
    }
}
