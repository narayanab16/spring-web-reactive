package com.narayana.reactive.server.handlers;

import com.narayana.reactive.server.dao.MapStore;
import com.narayana.reactive.server.model.User;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.ServletException;
import java.io.IOException;
import java.net.URI;

@Component
public class UserHandler {

        private MapStore mapStore;
        public UserHandler(MapStore mapStore) {
            this.mapStore = mapStore;
        }
        public ServerResponse getUser(ServerRequest req) {
            System.out.println("User UserHandler ");
            String id = req.pathVariable("id");
            Mono<User> user = mapStore.getUser(Integer.valueOf(id));
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                    .body(user);
        }
        public ServerResponse createUser(ServerRequest req) throws ServletException, IOException {
            User user = req.body(User.class);
            Mono<User> userAdded = mapStore.addUser(user);
            return ServerResponse.created(URI.create("/createUser")).contentType(MediaType.APPLICATION_JSON)
                    .body(userAdded);
        }

        public ServerResponse getUsers(ServerRequest req) {
            System.out.println("Users UserHandler ");
            Flux<User> users = mapStore.getUsers();
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                    //.body(BodyInserters.fromValue(users));
                    .body(users);
        }
}
