package com.asyrafil.peminjaman.cqrs.commands;

import lombok.Data;

@Data
public class CreatePeminjamanCommand {
    private Long anggota_id;
    private Long buku_id;
    private String tanggal_pinjam;
    private String tanggal_kembali;
    private String email;
    private String namaBuku;
}
