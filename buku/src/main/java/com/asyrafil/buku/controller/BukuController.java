package com.asyrafil.buku.controller;

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
import com.asyrafil.buku.model.Buku;
import com.asyrafil.buku.service.BukuService;

@RestController
@RequestMapping("/api/buku")
public class BukuController {

    @Autowired
    private BukuService bukuService;

    @GetMapping
    public List<Buku> getAllbBukus() {
        return bukuService.getAllBukus();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Buku> getBukuById(@PathVariable Long id) {
        Buku buku = bukuService.getBukuById(id);
        return buku != null ? ResponseEntity.ok(buku) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Buku createBuku(@RequestBody Buku buku) {
        return bukuService.createBuku(buku);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBuku(@PathVariable Long id) {
        bukuService.deleteBuku(id);
        return ResponseEntity.ok().build();
    }

}
