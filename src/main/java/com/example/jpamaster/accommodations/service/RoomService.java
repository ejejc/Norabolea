package com.example.jpamaster.accommodations.service;

import com.example.jpamaster.accommodations.domain.entity.BorrowRoom;
import com.example.jpamaster.accommodations.domain.entity.Media;
import com.example.jpamaster.accommodations.domain.entity.Room;
import com.example.jpamaster.accommodations.dto.ReviewDto;
import com.example.jpamaster.accommodations.dto.RoomDto;
import com.example.jpamaster.accommodations.repository.room.RoomReposittory;
import com.example.jpamaster.common.exception.InvalidParameterException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class RoomService {
    private final RoomReposittory roomReposittory;

    public List<Room> searchRoomListOfAccommodationSeq(Long accommodationSeq) {
        if (Objects.isNull(accommodationSeq) || accommodationSeq == 0L) {
            throw new InvalidParameterException("유효하지 않은 숙소입니다.");
        }
        return roomReposittory.findByAccommodationSeq(accommodationSeq);
    }

    public Room searchRoomEntity(RoomDto dto) {
        Room room = Room.builder()
                .roomPrice(dto.getRoomPrice())
                .roomName(dto.getRoomName())
                .standardPerson(dto.getStandardPerson())
                .maxPerson(dto.getMaxPerson())
                .checkInTime(dto.getCheckInTime())
                .checkOutTime(dto.getCheckOutTime())
                .useYn(dto.isUseYn())
                .borrowRoom(BorrowRoom.builder()
                        .borrowTime(dto.getBorrow().getBorrowTime())
                        .borrowPrice(dto.getBorrow().getBorrowPrice())
                        .operateTime(dto.getBorrow().getOperateTime()).build())
                .build();
        for (RoomDto.RoomMedia roomMedia : dto.getMediaList()) {
            room.addMedia(Media.builder()
                    .mediaUrl(roomMedia.getMediaUrl())
                    .mainFlag(roomMedia.isMainFlag())
                    .useYn(roomMedia.isUseYn()).build());
        }
        return room;
    }
}
