package com.asyrafil.peminjaman.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asyrafil.peminjaman.cqrs.commands.CreatePeminjamanCommand;
import com.asyrafil.peminjaman.cqrs.commands.DeletePeminjamanCommand;
import com.asyrafil.peminjaman.cqrs.dispatcher.CommandDispatcher;
import com.asyrafil.peminjaman.cqrs.dispatcher.QueryDispatcher;
import com.asyrafil.peminjaman.cqrs.queries.GetAllPeminjamanQuery;
import com.asyrafil.peminjaman.cqrs.queries.GetPeminjamanByIdQuery;
import com.asyrafil.peminjaman.cqrs.queries.GetPeminjamanWithDetailsQuery;
import com.asyrafil.peminjaman.dto.PeminjamanRequest;
import com.asyrafil.peminjaman.model.Peminjaman;
import com.asyrafil.peminjaman.vo.ResponseTemplate;

@RestController
@RequestMapping("/api/peminjaman")
public class PeminjamanController {

    @Autowired
    private CommandDispatcher commandDispatcher;

    @Autowired
    private QueryDispatcher queryDispatcher;

    @GetMapping
    public List<Peminjaman> getAllPeminjamans() {
        return queryDispatcher.send(new GetAllPeminjamanQuery());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Peminjaman> getPeminjamanById(@PathVariable Long id) {
        Peminjaman peminjaman = queryDispatcher.send(new GetPeminjamanByIdQuery(id));
        return peminjaman != null ? ResponseEntity.ok(peminjaman) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Peminjaman createPeminjaman(@RequestBody PeminjamanRequest request) {
        CreatePeminjamanCommand command = new CreatePeminjamanCommand();
        command.setAnggota_id(request.getAnggota_id());
        command.setBuku_id(request.getBuku_id());
        command.setTanggal_pinjam(request.getTanggal_pinjam());
        command.setTanggal_kembali(request.getTanggal_kembali());
        command.setEmail(request.getEmail());
        command.setNamaBuku(request.getNamaBuku());

        return commandDispatcher.send(command);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePeminjaman(@PathVariable Long id) {
        commandDispatcher.send(new DeletePeminjamanCommand(id));
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/buku/{id}")
    public List<ResponseTemplate> getPeminjamanWithBukuId(@PathVariable("id") Long id) {
        return queryDispatcher.send(new GetPeminjamanWithDetailsQuery(id));
    }

}