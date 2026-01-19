package com.hanif.pengembalian.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hanif.pengembalian.model.Pengembalian;

@Repository
public interface PengembalianRepository extends JpaRepository<Pengembalian, Long>{

}

