package com.vti.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "`group`")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", length = 255, unique = true)
    private String name;

    @Column(name = "create_date")
    @Temporal(value = TemporalType.DATE)
    private Date createDate;

    public Group(String name) {
        Date currentDateAndTime = new Date();

        // If you need a Calendar object for more manipulation
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDateAndTime);

        this.name = name;
        this.createDate = currentDateAndTime;
    }

    public Group(int id, String name)
    {
        this.id = id;
        this.name = name;
    }
}
