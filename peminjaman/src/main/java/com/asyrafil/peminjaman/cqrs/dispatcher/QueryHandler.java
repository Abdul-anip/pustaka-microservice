package com.asyrafil.peminjaman.cqrs.dispatcher;

public interface QueryHandler<Q, R> {
    R handle(Q query);
}
