package com.asyrafil.peminjaman.cqrs.dispatcher;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class QueryDispatcher {
    private final Map<Class<?>, QueryHandler<?, ?>> handlers = new HashMap<>();

    public <Q, R> void registerHandler(Class<Q> queryType, QueryHandler<Q, R> handler) {
        handlers.put(queryType, handler);
    }

    @SuppressWarnings("unchecked")
    public <Q, R> R send(Q query) {
        QueryHandler<Q, R> handler = (QueryHandler<Q, R>) handlers.get(query.getClass());
        if (handler == null) {
            throw new RuntimeException("No handler registered for " + query.getClass().getName());
        }
        return handler.handle(query);
    }
}
