package com.hanif.peminjaman.cqrs.handlers;

import com.hanif.peminjaman.cqrs.dispatcher.QueryDispatcher;
import com.hanif.peminjaman.cqrs.dispatcher.QueryHandler;
import com.hanif.peminjaman.cqrs.queries.GetPeminjamanByIdQuery;
import com.hanif.peminjaman.model.Peminjaman;
import com.hanif.peminjaman.repository.PeminjamanRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetPeminjamanByIdHandler implements QueryHandler<GetPeminjamanByIdQuery, Peminjaman> {

    @Autowired
    private PeminjamanRepository peminjamanRepository;

    @Autowired
    private QueryDispatcher queryDispatcher;

    @PostConstruct
    public void register() {
        queryDispatcher.registerHandler(GetPeminjamanByIdQuery.class, this);
    }

    @Override
    public Peminjaman handle(GetPeminjamanByIdQuery query) {
        return peminjamanRepository.findById(query.getId()).orElse(null);
    }
}
