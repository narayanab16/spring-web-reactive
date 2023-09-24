package com.narayana.reactive.server.routes;

import com.narayana.reactive.server.handlers.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RequestPredicates.*;

@Configuration(proxyBeanMethods = false)
public class UserRoute {
    @Bean
    public RouterFunction<ServerResponse> getUserById(UserHandler userHandler) {
        System.out.println("User Route ");
        return RouterFunctions.route(
                GET("/getUser/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::getUser
        );
    }
    @Bean
    public RouterFunction<ServerResponse> createUser(UserHandler userHandler) {
        return RouterFunctions.route(
                POST("/createUser").and(accept(MediaType.APPLICATION_JSON)), userHandler::createUser
        );
    }

    @Bean
    public RouterFunction<ServerResponse> getUsers(UserHandler userHandler) {
        return RouterFunctions.route(
                GET("/getUsers").and(accept(MediaType.APPLICATION_JSON)), userHandler::getUsers
        );
    }
}
