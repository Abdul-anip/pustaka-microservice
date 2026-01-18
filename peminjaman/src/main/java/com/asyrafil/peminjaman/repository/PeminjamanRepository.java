package com.asyrafil.peminjaman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.asyrafil.peminjaman.model.Peminjaman;

@Repository
public interface PeminjamanRepository extends JpaRepository<Peminjaman, Long>{

}
