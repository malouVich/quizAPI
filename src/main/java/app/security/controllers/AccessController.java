package app.security.controllers;

import app.security.enums.Role;
import dk.bugelhartmann.UserDTO;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.security.RouteRole;

import java.util.Set;

/**
 * Purpose: To handle security in the API at the route level
 *  Author: Jon Bertelsen
 */

public class AccessController implements IAccessController {

    SecurityController securityController = SecurityController.getInstance();

    /**
     * This method checks if the user has the necessary roles to access the route.
     *
     * @param ctx
     */
    public void accessHandler(Context ctx) {

        Set<RouteRole> allowedRoles = ctx.routeRoles();

        // Hvis ingen roller er krævet, tillad adgang
        if (allowedRoles.isEmpty() || allowedRoles.contains(Role.ANYONE)) {
            return;
        }

        // Forsøg at autentificere brugeren
        try {
            securityController.authenticate().handle(ctx);
        } catch (UnauthorizedResponse e) {
            throw new UnauthorizedResponse(e.getMessage());
        } catch (Exception e) {
            throw new UnauthorizedResponse("You need to log in, dude! Or your token is invalid.");
        }

        // Hent brugerinfo sat i authenticate()
        UserDTO user = ctx.attribute("user");
        if (user == null) {
            throw new UnauthorizedResponse("No user found in context after authentication");
        }

        // Tjek roller
        if (!securityController.authorize(user, allowedRoles)) {
            throw new UnauthorizedResponse("You are not authorized. Your roles: "
                    + user.getRoles() + ", required: " + allowedRoles);
        }
    }
}