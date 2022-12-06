package com.example.jpamaster.flight.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@EqualsAndHashCode(of = "airlineSeq")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "airline")
@Entity
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long airlineSeq;

    @Comment("항공사 명 한글")
    @Column(name = "kr_name")
    private String krName;

    @Comment("항공사 명 영어")
    @Column(name = "en_name")
    private String enName;

}
