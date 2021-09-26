package com.yorosoft.server.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yorosoft.server.enumeration.Status;
import com.yorosoft.server.model.Response;
import com.yorosoft.server.model.Server;
import com.yorosoft.server.repository.ServerRepo;
import com.yorosoft.server.service.implementation.ServerServiceImpl;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ServerController.class})
@ExtendWith(SpringExtension.class)
public class ServerControllerTest {
    @Autowired
    private ServerController serverController;

    @MockBean
    private ServerServiceImpl serverServiceImpl;

    @Test
    public void testGetServers() throws InterruptedException {
        ServerRepo serverRepo = mock(ServerRepo.class);
        when(serverRepo.findAll((org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<Server>(new ArrayList<Server>()));
        ResponseEntity<Response> actualServers = (new ServerController(new ServerServiceImpl(serverRepo))).getServers();
        assertTrue(actualServers.getHeaders().isEmpty());
        assertTrue(actualServers.hasBody());
        assertEquals(HttpStatus.OK, actualServers.getStatusCode());
        Response body = actualServers.getBody();
        assertNull(body.getReason());
        assertEquals("Servers retrieved", body.getMessage());
        assertNull(body.getDeveloperMessage());
        assertEquals(1, body.getData().size());
        assertEquals(HttpStatus.OK, body.getStatus());
        assertEquals(200, body.getStatusCode());
        verify(serverRepo).findAll((org.springframework.data.domain.Pageable) any());
    }

    @Test
    public void testSaveServer() {
        Server server = new Server();
        server.setStatus(Status.SERVER_UP);
        server.setId(123L);
        server.setImageUrl("https://example.org/example");
        server.setName("Name");
        server.setMemory("Memory");
        server.setIpAddress("42 Main St");
        server.setType("Type");
        ServerServiceImpl serverServiceImpl = mock(ServerServiceImpl.class);
        when(serverServiceImpl.create((Server) any())).thenReturn(server);
        ServerController serverController = new ServerController(serverServiceImpl);

        Server server1 = new Server();
        server1.setStatus(Status.SERVER_UP);
        server1.setId(123L);
        server1.setImageUrl("https://example.org/example");
        server1.setName("Name");
        server1.setMemory("Memory");
        server1.setIpAddress("42 Main St");
        server1.setType("Type");
        ResponseEntity<Response> actualSaveServerResult = serverController.saveServer(server1);
        assertTrue(actualSaveServerResult.getHeaders().isEmpty());
        assertTrue(actualSaveServerResult.hasBody());
        assertEquals(HttpStatus.OK, actualSaveServerResult.getStatusCode());
        Response body = actualSaveServerResult.getBody();
        assertNull(body.getReason());
        assertEquals("Server created", body.getMessage());
        assertNull(body.getDeveloperMessage());
        assertEquals(1, body.getData().size());
        assertEquals(HttpStatus.CREATED, body.getStatus());
        assertEquals(201, body.getStatusCode());
        verify(serverServiceImpl).create((Server) any());
    }

    @Test
    public void testDeleteServer() {
        ServerRepo serverRepo = mock(ServerRepo.class);
        doNothing().when(serverRepo).deleteById((Long) any());
        ResponseEntity<Response> actualDeleteServerResult = (new ServerController(new ServerServiceImpl(serverRepo)))
                .deleteServer(123L);
        assertTrue(actualDeleteServerResult.getHeaders().isEmpty());
        assertTrue(actualDeleteServerResult.hasBody());
        assertEquals(HttpStatus.OK, actualDeleteServerResult.getStatusCode());
        Response body = actualDeleteServerResult.getBody();
        assertNull(body.getReason());
        assertEquals("Server deleted", body.getMessage());
        assertNull(body.getDeveloperMessage());
        assertEquals(1, body.getData().size());
        assertEquals(HttpStatus.OK, body.getStatus());
        assertEquals(200, body.getStatusCode());
        verify(serverRepo).deleteById((Long) any());
    }

    @Test
    public void testGetServer() throws Exception {
        Server server = new Server();
        server.setStatus(Status.SERVER_UP);
        server.setId(123L);
        server.setImageUrl("https://example.org/example");
        server.setName("Name");
        server.setMemory("Memory");
        server.setIpAddress("42 Main St");
        server.setType("Type");
        when(this.serverServiceImpl.get((Long) any())).thenReturn(server);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/server/get/{id}", 123L);
        getResult.accept("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.serverController)
                .build()
                .perform(getResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406));
    }

    @Test
    public void testPingServer() throws Exception {
        Server server = new Server();
        server.setStatus(Status.SERVER_UP);
        server.setId(123L);
        server.setImageUrl("https://example.org/example");
        server.setName("Name");
        server.setMemory("Memory");
        server.setIpAddress("42 Main St");
        server.setType("Type");
        when(this.serverServiceImpl.ping((String) any())).thenReturn(server);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/server/ping/{ipAddress}", "42 Main St");
        getResult.accept("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.serverController)
                .build()
                .perform(getResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406));
    }
}

