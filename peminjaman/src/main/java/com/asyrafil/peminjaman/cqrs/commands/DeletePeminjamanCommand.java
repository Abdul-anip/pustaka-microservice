package com.asyrafil.peminjaman.cqrs.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeletePeminjamanCommand {
    private Long id;
}
