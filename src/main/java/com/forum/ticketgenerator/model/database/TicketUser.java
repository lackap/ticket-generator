package com.forum.ticketgenerator.model.database;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "TICKET_USER")
public class TicketUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    private String displayName;

    private String role;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] logo;
}
