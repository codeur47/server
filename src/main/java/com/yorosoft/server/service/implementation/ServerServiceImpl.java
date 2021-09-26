package com.yorosoft.server.service.implementation;

import com.yorosoft.server.model.Server;
import com.yorosoft.server.repository.ServerRepo;
import com.yorosoft.server.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Collection;
import java.util.Optional;

import static com.yorosoft.server.enumeration.Status.SERVER_DOWN;
import static com.yorosoft.server.enumeration.Status.SERVER_UP;
import static java.lang.Boolean.TRUE;
import static org.springframework.data.domain.PageRequest.of;

/**
 * @author Get Arrays (https://www.getarrays.io/)
 * @version 1.0
 * @since 9/4/2021
 */

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ServerServiceImpl implements ServerService {
    private final ServerRepo serverRepo;

    @Override
    public Server create(Server server) {
        log.info("Saving new server: {}", server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers");
        return serverRepo.findAll(of(0, limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server by id: {}", id);
        Optional<Server> optionalServer = serverRepo.findById(id);
        return optionalServer.orElse(null);
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server: {}", server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server by ID: {}", id);
        serverRepo.deleteById(id);
        return TRUE;
    }
}
