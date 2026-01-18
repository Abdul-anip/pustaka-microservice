package com.asyrafil.buku.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Buku {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String judul;
    private String pengarang;
    private String penerbit;
    private Integer tahun_terbit;
}
