package com.vti.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "group_management")
@Data
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "group_name", unique = true, nullable = false)
    private String groupName;

    @Column(name = "quantity")
    private Integer quantity = 0;

    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    private Date createDate;

    @ManyToOne()
    @JoinColumn(name = "creator_id")
    private Account creator;

//    Cái này có hay không vẫn được
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Account> accounts;
}
