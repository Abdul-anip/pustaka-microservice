package com.asyrafil.peminjaman.cqrs.handlers;

import com.asyrafil.peminjaman.cqrs.dispatcher.QueryDispatcher;
import com.asyrafil.peminjaman.cqrs.dispatcher.QueryHandler;
import com.asyrafil.peminjaman.cqrs.queries.GetPeminjamanByIdQuery;
import com.asyrafil.peminjaman.model.Peminjaman;
import com.asyrafil.peminjaman.repository.PeminjamanRepository;
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
