package com.example.jpamaster.accommodations.domain.entity;

import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "room_media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment("객실 미디어 Seq")
    @Column(name = "room_media_seq")
    private Long seq;

    @Comment("미디어 url")
    @Column(name = "room_media_url")
    private String mediaUrl;

    @Comment("대표 이미지 여부")
    @Column(name = "main_flag")
    private boolean mainFlag;

    @Comment("미디어 사용 여부")
    @Column(name = "use_yn")
    private boolean useYn;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @Setter
    private Room room;
}
