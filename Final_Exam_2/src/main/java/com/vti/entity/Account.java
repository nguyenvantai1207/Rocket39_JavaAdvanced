package com.vti.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "`account`")
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "role")
    private String Role;

    @ManyToOne()
    @JoinColumn(name = "group_id")
    private Group group;
}
