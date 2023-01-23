package com.example.jpamaster.accommodations.domain.entity;

import com.example.jpamaster.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
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

    @OneToOne
    @JoinColumn(name = "review_seq")
    private Review review;
}
