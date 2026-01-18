package com.asyrafil.pengembalian.controller;

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
import com.asyrafil.pengembalian.model.Pengembalian;
import com.asyrafil.pengembalian.service.PengembalianService;
import com.asyrafil.pengembalian.vo.ResponseTemplate;
import com.asyrafil.pengembalian.dto.PengembalianRequest;
import java.sql.Date;

@RestController
@RequestMapping("/api/pengembalian")
public class PengembalianController {
    @Autowired
    private PengembalianService pengembalianService;

    @GetMapping
    public List<Pengembalian> getAllPengembalians() {
        return pengembalianService.getAllPengembalians();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pengembalian> getPengembalianById(@PathVariable Long id) {
        Pengembalian pengembalian = pengembalianService.getPengembalianById(id);
        return pengembalian != null ? ResponseEntity.ok(pengembalian) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createPengembalian(@RequestBody PengembalianRequest request) {
        try {
            // Validate required fields
            if (request.getTanggal_pengembalian() == null || request.getTanggal_pengembalian().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("tanggal_pengembalian is required and cannot be empty");
            }

            // Create Pengembalian entity from request
            Pengembalian pengembalian = new Pengembalian();
            pengembalian.setPeminjaman_id(request.getPeminjaman_id());

            // Parse date with proper error handling
            try {
                pengembalian.setTanggal_dikembalikan(Date.valueOf(request.getTanggal_pengembalian()));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(
                        "Invalid date format for tanggal_pengembalian. Expected format: YYYY-MM-DD (e.g., 2025-12-26)");
            }

            pengembalian.setDenda((long) request.getDenda());

            // Save pengembalian
            Pengembalian savedPengembalian = pengembalianService.createPengembalian(pengembalian);

            // Send email notification
            if (request.getEmail() != null && request.getNamaBuku() != null) {
                pengembalianService.prosesPengembalian(request.getEmail(), request.getNamaBuku());
            }

            return ResponseEntity.ok(savedPengembalian);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error creating pengembalian: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePengembalian(@PathVariable Long id) {
        pengembalianService.deletePengembalian(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/peminjaman/{id}")
    public List<ResponseTemplate> getPengembalianWithPeminjamanId(@PathVariable("id") Long id) {
        return pengembalianService.getPengembalianWithPeminjamanById(id);
    }
}
