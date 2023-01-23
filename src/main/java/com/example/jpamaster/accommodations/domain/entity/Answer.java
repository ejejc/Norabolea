package com.example.jpamaster.accommodations.domain.entity;

import com.example.jpamaster.common.domain.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "answer")
public class Answer extends BaseEntity {

    @Column(name = "answer_seq")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long answerSeq;

    @Column(name = "content")
    private String content;

    @Column(name = "delete_yn")
    private Boolean deleteYn;

    @Column(name = "reg_admin_seq")
    private Long regAdminSeq;
}
