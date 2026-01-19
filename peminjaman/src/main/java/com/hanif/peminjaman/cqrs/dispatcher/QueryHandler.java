package com.hanif.peminjaman.cqrs.dispatcher;

public interface QueryHandler<Q, R> {
    R handle(Q query);
}
