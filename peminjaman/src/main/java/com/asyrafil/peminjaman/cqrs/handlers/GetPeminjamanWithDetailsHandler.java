package com.asyrafil.peminjaman.cqrs.handlers;

import com.asyrafil.peminjaman.cqrs.dispatcher.QueryDispatcher;
import com.asyrafil.peminjaman.cqrs.dispatcher.QueryHandler;
import com.asyrafil.peminjaman.cqrs.queries.GetPeminjamanWithDetailsQuery;
import com.asyrafil.peminjaman.model.Peminjaman;
import com.asyrafil.peminjaman.repository.PeminjamanRepository;
import com.asyrafil.peminjaman.vo.Anggota;
import com.asyrafil.peminjaman.vo.Buku;
import com.asyrafil.peminjaman.vo.ResponseTemplate;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetPeminjamanWithDetailsHandler
        implements QueryHandler<GetPeminjamanWithDetailsQuery, List<ResponseTemplate>> {

    @Autowired
    private PeminjamanRepository peminjamanRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private QueryDispatcher queryDispatcher;

    @PostConstruct
    public void register() {
        queryDispatcher.registerHandler(GetPeminjamanWithDetailsQuery.class, this);
    }

    @Override
    public List<ResponseTemplate> handle(GetPeminjamanWithDetailsQuery query) {
        List<ResponseTemplate> responseList = new ArrayList<>();
        Peminjaman peminjaman = peminjamanRepository.findById(query.getId()).orElse(null);

        if (peminjaman != null) {
            Buku buku = restTemplate.getForObject("http://BUKU/api/buku/"
                    + peminjaman.getBuku_id(), Buku.class);
            Anggota anggota = restTemplate.getForObject("http://ANGGOTA/api/anggota/"
                    + peminjaman.getAnggota_id(), Anggota.class);
            ResponseTemplate vo = new ResponseTemplate();
            vo.setPeminjaman(peminjaman);
            vo.setBuku(buku);
            vo.setAnggota(anggota);
            responseList.add(vo);
        }

        return responseList;
    }
}
