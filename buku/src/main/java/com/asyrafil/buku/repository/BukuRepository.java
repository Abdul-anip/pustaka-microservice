package com.asyrafil.buku.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.asyrafil.buku.model.Buku;

@Repository
public interface BukuRepository extends JpaRepository<Buku, Long>{

}
