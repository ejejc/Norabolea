package com.example.jpamaster.accommodations.domain.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accommodation_category")
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment("카테고리 Seq")
    @Column(name = "category_seq")
    private Long seq;

    @Comment("카테고리 이름")
    @Column(name = "category_name")
    private String categoryName;

    @Comment("카테고리 부모 seq")
    @Column(name = "category_parent_seq")
    private Long parentSeq;
}
