package com.asyrafil.peminjaman.cqrs.handlers;

import com.asyrafil.peminjaman.cqrs.dispatcher.QueryDispatcher;
import com.asyrafil.peminjaman.cqrs.dispatcher.QueryHandler;
import com.asyrafil.peminjaman.cqrs.queries.GetAllPeminjamanQuery;
import com.asyrafil.peminjaman.model.Peminjaman;
import com.asyrafil.peminjaman.repository.PeminjamanRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllPeminjamanHandler implements QueryHandler<GetAllPeminjamanQuery, List<Peminjaman>> {

    @Autowired
    private PeminjamanRepository peminjamanRepository;

    @Autowired
    private QueryDispatcher queryDispatcher;

    @PostConstruct
    public void register() {
        queryDispatcher.registerHandler(GetAllPeminjamanQuery.class, this);
    }

    @Override
    public List<Peminjaman> handle(GetAllPeminjamanQuery query) {
        System.out.println("CQRS Handler Executed: GetAllPeminjamanHandler. Fetching all records...");
        return peminjamanRepository.findAll();
    }
}
