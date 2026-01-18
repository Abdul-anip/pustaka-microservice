package com.asyrafil.peminjaman.cqrs.handlers;

import com.asyrafil.peminjaman.cqrs.commands.DeletePeminjamanCommand;
import com.asyrafil.peminjaman.cqrs.dispatcher.CommandDispatcher;
import com.asyrafil.peminjaman.cqrs.dispatcher.CommandHandler;
import com.asyrafil.peminjaman.repository.PeminjamanRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeletePeminjamanHandler implements CommandHandler<DeletePeminjamanCommand, Void> {

    @Autowired
    private PeminjamanRepository peminjamanRepository;

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PostConstruct
    public void register() {
        commandDispatcher.registerHandler(DeletePeminjamanCommand.class, this);
    }

    @Override
    public Void handle(DeletePeminjamanCommand command) {
        peminjamanRepository.deleteById(command.getId());
        return null;
    }
}
