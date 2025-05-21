package app;

import app.config.ApplicationConfig;
import app.security.controllers.SecurityController;
import io.javalin.http.UnauthorizedResponse;

public class Main {
    public static void main(String[] args) {
        ApplicationConfig.startServer(7777);
        System.out.println("🔧 CONNECTION_STR: " + System.getenv("CONNECTION_STR"));
        System.out.println("🔧 QUIZ_DB_NAME: " + System.getenv("QUIZ_DB_NAME"));

    }
}