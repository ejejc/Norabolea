package com.example.jpamaster.users;

import javax.persistence.*;
import java.util.Date;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userSeq;

    @Column(name = "name")
    private String name;

    @Column(name = "birth")
    private String birth;

    @Column(name = "id")
    private String id;

    @Column(name = "phone_number")
    private String phoneNo;

    @Column(name = "gender")
    private int gender; // TODO: enum으로 뺄까?

    @Column(name = "user_status")
    private int status; // TODO: enum으로 뺄까?

    @Column(name = "reg_date")
    private Date regDate;

    @Column(name = "update_date")
    private Date updateDate;
}
z