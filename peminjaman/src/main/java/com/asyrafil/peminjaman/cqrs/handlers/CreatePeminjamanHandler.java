package com.asyrafil.peminjaman.cqrs.handlers;

import com.asyrafil.peminjaman.cqrs.commands.CreatePeminjamanCommand;
import com.asyrafil.peminjaman.cqrs.dispatcher.CommandDispatcher;
import com.asyrafil.peminjaman.cqrs.dispatcher.CommandHandler;
import com.asyrafil.peminjaman.email.EmailProducer;
import com.asyrafil.peminjaman.model.Peminjaman;
import com.asyrafil.peminjaman.repository.PeminjamanRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static net.logstash.logback.marker.Markers.append;

@Service
public class CreatePeminjamanHandler implements CommandHandler<CreatePeminjamanCommand, Peminjaman> {

    private static final Logger logger = LoggerFactory.getLogger(CreatePeminjamanHandler.class);

    @Autowired
    private PeminjamanRepository peminjamanRepository;

    @Autowired
    private EmailProducer emailProducer;

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PostConstruct
    public void register() {
        commandDispatcher.registerHandler(CreatePeminjamanCommand.class, this);
    }

    @Override
    public Peminjaman handle(CreatePeminjamanCommand command) {

        Peminjaman peminjaman = new Peminjaman();
        peminjaman.setAnggota_id(command.getAnggota_id());
        peminjaman.setBuku_id(command.getBuku_id());
        peminjaman.setTanggal_pinjam(Date.valueOf(command.getTanggal_pinjam()));
        peminjaman.setTanggal_kembali(Date.valueOf(command.getTanggal_kembali()));

        Peminjaman savedPeminjaman = peminjamanRepository.save(peminjaman);

        // Structured Logging for ELK Data
        logger.info(append("event", "peminjaman_created")
                .and(append("anggota_id", command.getAnggota_id()))
                .and(append("buku_id", command.getBuku_id()))
                .and(append("peminjaman_id", savedPeminjaman.getId()))
                .and(append("email", command.getEmail())),
                "Peminjaman Created: Anggota {} meminjam Buku {}", command.getAnggota_id(), command.getBuku_id());

        if (command.getEmail() != null && command.getNamaBuku() != null) {
            String subject = "Peminjaman Buku Berhasil";
            String body = "Terima kasih telah meminjam buku \"" + command.getNamaBuku()
                    + "\". Harap kembalikan tepat waktu.";
            emailProducer.sendEmail(command.getEmail(), subject, body);
        }

        return savedPeminjaman;
    }
}
