package com.asyrafil.anggota.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.asyrafil.anggota.model.Anggota;

@Repository
public interface AnggotaRepository extends JpaRepository<Anggota, Long>{

}
