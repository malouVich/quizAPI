package app;

import app.config.ApplicationConfig;
import app.security.controllers.SecurityController;
import io.javalin.http.UnauthorizedResponse;

public class Main {
    public static void main(String[] args) {
        ApplicationConfig.startServer(7777);
    }
}