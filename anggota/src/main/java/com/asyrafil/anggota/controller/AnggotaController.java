package com.asyrafil.anggota.controller;

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
import com.asyrafil.anggota.model.Anggota;
import com.asyrafil.anggota.service.AnggotaService;

@RestController
@RequestMapping("/api/anggota")
public class AnggotaController {
    @Autowired
    private AnggotaService anggotaService;

    @GetMapping
    public List<Anggota> getAllAnggotas() {
        return anggotaService.getAllAnggotas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anggota> getAnggotaById(@PathVariable Long id) {
        Anggota anggota = anggotaService.getAnggotaById(id);
        return anggota != null ? ResponseEntity.ok(anggota) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Anggota createAnggota(@RequestBody Anggota anggota) {
        return anggotaService.createAnggota(anggota);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnggota(@PathVariable Long id) {
        anggotaService.deleteAnggota(id);
        return ResponseEntity.ok().build();
    }

}
