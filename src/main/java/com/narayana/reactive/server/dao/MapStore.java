package com.narayana.reactive.server.dao;


import com.narayana.reactive.server.model.User;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class MapStore {
    private Map<Integer, User> map = new LinkedHashMap<>();
    public Mono<User> addUser(User user) {
        map.put(user.id(), user);
        return Mono.just(user);
    }
    public Mono<User> getUser(Integer id) {
        System.out.println("User MapStore ");
        return Mono.just(map.get(id));
    }

    public Flux<User> getUsers() {
        System.out.println("Users MapStore ");
        List<User> users = map.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());
        return Flux.fromIterable(users);
    }

}
