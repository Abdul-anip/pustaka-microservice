package com.hanif.anggota.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hanif.anggota.model.Anggota;

@Repository
public interface AnggotaRepository extends JpaRepository<Anggota, Long>{

}
