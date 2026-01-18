package com.asyrafil.buku.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.asyrafil.buku.model.Buku;
import com.asyrafil.buku.repository.BukuRepository;

@Service
public class BukuService {
    @Autowired
    private BukuRepository bukuRepository;

    public List<Buku> getAllBukus() {
        return bukuRepository.findAll();
    }

    public Buku getBukuById(Long id) {
        return bukuRepository.findById(id).orElse(null);
    }

    public Buku createBuku(Buku buku) {
        return bukuRepository.save(buku);
    }

    public void deleteBuku(Long id) {
        bukuRepository.deleteById(id);
    }

}
