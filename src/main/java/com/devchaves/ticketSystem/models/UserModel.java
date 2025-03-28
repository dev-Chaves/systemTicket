package com.devchaves.ticketSystem.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "tb_users")
public class UserModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID users_id;

    @Column(nullable = false, unique = true)
    private String users_name;

    @Column(nullable = false)
    private String users_pass;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleEnum users_role;



}
