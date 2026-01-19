package com.hanif.peminjaman.cqrs.dispatcher;

public interface CommandHandler<T, R> {
    R handle(T command);
}
