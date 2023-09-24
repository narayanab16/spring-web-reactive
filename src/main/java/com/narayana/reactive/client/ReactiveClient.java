package com.narayana.reactive.client;

import com.narayana.reactive.server.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactiveClient {
    public WebClient webClient;
    public ReactiveClient(WebClient webClient) {
        this.webClient = webClient;
    }
    public Mono<User> getUser(Integer id) {
        System.out.println("User ReactiveClient ");
        return this.webClient.get().uri("/getUser/" + id).accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(x ->
                        x.statusCode().equals(HttpStatus.OK)
                                ? x.bodyToMono(User.class)
                                : Mono.just(new User(-1, "dummy")));
    }

    public Mono<User> createUser(User user) {
        return this.webClient.post().uri("/createUser").accept(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .exchangeToMono(x ->
                        x.statusCode().equals(HttpStatus.CREATED) ?
                                x.bodyToMono(User.class) :
                                Mono.justOrEmpty(null)
                );
    }

    public Flux<User> getUsers() {
        System.out.println("Users ReactiveClient ");
        return this.webClient.get().uri("/getUsers").accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(x -> x.statusCode().equals(HttpStatus.OK) ?  x.bodyToFlux(User.class) : Flux.just(new User(-1, "dummy")));
    }

    public static void main(String[] args) {
        WebClient webClient =
                WebClient.builder().baseUrl("http://localhost:10000").build();
        ReactiveClient client = new ReactiveClient(webClient);
        Mono<User> narayana = client.createUser(new User(1000, "Narayana"));
        Mono<User> basetty = client.createUser(new User(2000, "Basetty"));
        System.out.println(" User  Created : " + narayana.block());
        System.out.println(" User  Created : " + basetty.block());
        Mono<User> user = client.getUser(1);// not exists but lets check
        System.out.println("Got user 1 : " + user.block());
        Flux<User> users = client.getUsers();
        System.out.println(" Got users : " + users);
        System.out.println(" Got users : " + users.collectList().block());
    }

}
