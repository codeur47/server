package com.yorosoft.server.repository;


import com.yorosoft.server.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Get Arrays (https://www.getarrays.io/)
 * @version 1.0
 * @since 9/4/2021
 */
public interface ServerRepo extends JpaRepository<Server, Long> {
    Server findByIpAddress(String ipAddress);
}
