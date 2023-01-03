package com.example.jpamaster.accommodations.repository.room;

import com.example.jpamaster.accommodations.domain.entity.Room;

import java.util.List;

public interface RoomCustomRepository {
    public List<Room> findByAccommodationSeq(Long seq);
}
