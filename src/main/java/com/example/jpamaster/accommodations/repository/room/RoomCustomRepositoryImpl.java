package com.example.jpamaster.accommodations.repository.room;

import com.example.jpamaster.accommodations.domain.entity.QRoom;
import com.example.jpamaster.accommodations.domain.entity.Room;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class RoomCustomRepositoryImpl implements RoomCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    public RoomCustomRepositoryImpl(EntityManager em) {
        jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Room> findByAccommodationSeq(Long seq) {
        return jpaQueryFactory.selectFrom(QRoom.room)
                .where(QRoom.room.accommodations.accommodationSeq.eq(seq))
                .fetch();
    }
}
