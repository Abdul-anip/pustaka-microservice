package com.asyrafil.peminjaman.cqrs.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPeminjamanWithDetailsQuery {
    private Long id;
}
