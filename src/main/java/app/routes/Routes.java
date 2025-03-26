package app.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {

    private final QuestionRoute questionRoute = new QuestionRoute();
    private final AnswerRoute answerRoute = new AnswerRoute();

    public EndpointGroup getRoutes() {
        return () -> {
                path("/quiz", questionRoute.getRoutes());
                path("/answers", answerRoute.getRoutes());
        };
    }
}
