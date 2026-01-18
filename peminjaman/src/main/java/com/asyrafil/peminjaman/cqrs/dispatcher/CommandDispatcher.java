package com.asyrafil.peminjaman.cqrs.dispatcher;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class CommandDispatcher {
    private final Map<Class<?>, CommandHandler<?, ?>> handlers = new HashMap<>();

    public <T, R> void registerHandler(Class<T> commandType, CommandHandler<T, R> handler) {
        handlers.put(commandType, handler);
    }

    @SuppressWarnings("unchecked")
    public <T, R> R send(T command) {
        CommandHandler<T, R> handler = (CommandHandler<T, R>) handlers.get(command.getClass());
        if (handler == null) {
            throw new RuntimeException("No handler registered for " + command.getClass().getName());
        }
        return handler.handle(command);
    }
}
