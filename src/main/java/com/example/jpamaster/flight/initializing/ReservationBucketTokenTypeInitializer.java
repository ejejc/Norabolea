package com.example.jpamaster.flight.initializing;

import com.example.jpamaster.flight.domain.entity.ReservationBucketTokenType;
import com.example.jpamaster.flight.domain.repository.ReservationBucketTokenTypeRepository;
import com.example.jpamaster.flight.enums.FlightEnums.BucketTokenType;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class ReservationBucketTokenTypeInitializer implements ApplicationRunner {

    private final ReservationBucketTokenTypeRepository reservationBucketTokenTypeRepository;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        BucketTokenType[] tokenTypes = BucketTokenType.values();

        List<ReservationBucketTokenType> saveList = new ArrayList<>();

        for (BucketTokenType token : tokenTypes) {
            List<ReservationBucketTokenType> reservationBucketTokenTypes = initializeReservationToken(token);
            if (!reservationBucketTokenTypes.isEmpty()) {
                saveList.addAll(reservationBucketTokenTypes);
            }
        }

        reservationBucketTokenTypeRepository.saveAll(saveList);
    }

    private List<ReservationBucketTokenType> initializeReservationToken(final BucketTokenType tokenType) {
        List<ReservationBucketTokenType> reservationBucketTokenTypes = new ArrayList<>(1);

        reservationBucketTokenTypeRepository.findById(tokenType)
            .ifPresentOrElse(
                (token) -> {
                    log.info("token type : {}", token);
                },
                () -> {
                    ReservationBucketTokenType reservationBucketTokenType = ReservationBucketTokenType.builder()
                        .bucketTokenType(tokenType)
                        .reservationBucketCostMultipleRate(tokenType.getDefaultCostMultiple())
                        .build();


                    reservationBucketTokenTypes.add(reservationBucketTokenType);
                    log.info("token type created : {}", reservationBucketTokenType);
                }
            );

        return reservationBucketTokenTypes;
    }
}
