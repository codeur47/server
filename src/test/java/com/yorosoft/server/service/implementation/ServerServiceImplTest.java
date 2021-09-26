package com.yorosoft.server.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yorosoft.server.enumeration.Status;
import com.yorosoft.server.model.Server;
import com.yorosoft.server.repository.ServerRepo;

import java.io.IOException;

import java.util.ArrayList;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ServerServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ServerServiceImplTest {
    @MockBean
    private ServerRepo serverRepo;

    @Autowired
    private ServerServiceImpl serverServiceImpl;

    @Test
    void testList() {
        when(this.serverRepo.findAll((org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<Server>(new ArrayList<Server>()));
        assertTrue(this.serverServiceImpl.list(1).isEmpty());
        verify(this.serverRepo).findAll((org.springframework.data.domain.Pageable) any());
    }

    @Test
    void testGet() {
        Server server = new Server();
        server.setStatus(Status.SERVER_UP);
        server.setId(123L);
        server.setName("Name");
        server.setMemory("Memory");
        server.setIpAddress("42 Main St");
        server.setType("Type");
        Optional<Server> ofResult = Optional.<Server>of(server);
        when(this.serverRepo.findById((Long) any())).thenReturn(ofResult);
        assertSame(server, this.serverServiceImpl.get(123L));
        verify(this.serverRepo).findById((Long) any());
    }

    @Test
    void testGet2() {
        Server server = new Server();
        server.setStatus(Status.SERVER_UP);
        server.setId(123L);
        server.setName("Fetching server by id: {}");
        server.setMemory("Fetching server by id: {}");
        server.setIpAddress("42 Main St");
        server.setType("Fetching server by id: {}");
        Optional<Server> ofResult = Optional.<Server>of(server);
        when(this.serverRepo.findById((Long) any())).thenReturn(ofResult);
        assertSame(server, this.serverServiceImpl.get(0L));
        verify(this.serverRepo).findById((Long) any());
    }

    @Test
    void testUpdate() {
        Server server = new Server();
        server.setStatus(Status.SERVER_UP);
        server.setId(123L);
        server.setName("Name");
        server.setMemory("Memory");
        server.setIpAddress("42 Main St");
        server.setType("Type");
        when(this.serverRepo.save((Server) any())).thenReturn(server);

        Server server1 = new Server();
        server1.setStatus(Status.SERVER_UP);
        server1.setId(123L);
        server1.setName("Name");
        server1.setMemory("Memory");
        server1.setIpAddress("42 Main St");
        server1.setType("Type");
        assertSame(server, this.serverServiceImpl.update(server1));
        verify(this.serverRepo).save((Server) any());
    }

    @Test
    void testDelete() {
        doNothing().when(this.serverRepo).deleteById((Long) any());
        assertTrue(this.serverServiceImpl.delete(123L));
        verify(this.serverRepo).deleteById((Long) any());
    }
}

