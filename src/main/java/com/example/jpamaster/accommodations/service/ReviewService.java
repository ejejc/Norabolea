package com.example.jpamaster.accommodations.service;

import com.example.jpamaster.accommodations.domain.entity.Review;
import com.example.jpamaster.accommodations.domain.entity.Room;
import com.example.jpamaster.accommodations.dto.ReviewDto.Req;
import com.example.jpamaster.accommodations.repository.ReviewRepository;
import com.example.jpamaster.accommodations.repository.RoomReposittory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final RoomReposittory roomReposittory;
    public void addReview(Req reviewDto) {
        Room room = roomReposittory.findById(reviewDto.getRoomSeq()).orElse(null);
        Review review = reviewDto.changeToEntity(room);
        reviewRepository.save(reviewDto.changeToEntity(room));
    }
}
