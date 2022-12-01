package com.forum.ticketgenerator.repository;

import com.forum.ticketgenerator.model.database.TicketUser;
import org.springframework.data.repository.CrudRepository;

public interface TicketUserRepository extends CrudRepository<TicketUser, Long> {
    TicketUser findByUsername(String username);
}
