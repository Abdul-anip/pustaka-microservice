package com.hanif.peminjaman.cqrs.handlers;

import com.hanif.peminjaman.cqrs.dispatcher.QueryDispatcher;
import com.hanif.peminjaman.cqrs.dispatcher.QueryHandler;
import com.hanif.peminjaman.cqrs.queries.GetPeminjamanWithDetailsQuery;
import com.hanif.peminjaman.model.Peminjaman;
import com.hanif.peminjaman.repository.PeminjamanRepository;
import com.hanif.peminjaman.vo.Anggota;
import com.hanif.peminjaman.vo.Buku;
import com.hanif.peminjaman.vo.ResponseTemplate;
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
