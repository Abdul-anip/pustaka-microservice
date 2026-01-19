package com.hanif.peminjaman.cqrs.handlers;

import com.hanif.peminjaman.cqrs.dispatcher.QueryDispatcher;
import com.hanif.peminjaman.cqrs.dispatcher.QueryHandler;
import com.hanif.peminjaman.cqrs.queries.GetAllPeminjamanQuery;
import com.hanif.peminjaman.model.Peminjaman;
import com.hanif.peminjaman.repository.PeminjamanRepository;
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
