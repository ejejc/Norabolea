package com.example.jpamaster.accommodation;

import com.example.jpamaster.accommodations.domain.Address;
import com.example.jpamaster.accommodations.domain.entity.*;
import com.example.jpamaster.accommodations.enums.AccomodationsEnum;
import com.example.jpamaster.accommodations.repository.AccommodationsRepository;
import com.example.jpamaster.accommodations.repository.RoomReposittory;
import com.example.jpamaster.accommodations.repository.SellerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccommodationServiceTests {
    @Autowired
    AccommodationsRepository accommodationsRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    RoomReposittory roomReposittory;

    @Test
    @Transactional
    @Commit
    public void insertAccommodation() {
        Seller seller = Seller.builder()
                .sellerName("최은지")
                .sellerId("ejejc")
                .sellerPwd("ejejc8901")
                .sellerBirth("970127")
                .phoneNo("01022465216")
                .businessNo("8907263")
                .address("서울 강남구 언주로87길 11 (역삼동)")
                .email("ejejc@naver.com")
                .status(AccomodationsEnum.SellerStatus.NORMAL)
                .build();
        Address address = Address.builder()
                .deliveryAddress("서울 강남구")
                .deliveyAddressDetail("연주로 87길 11 (역삼동)")
                .deliveryPostNo("123-456").build();
        Accommodations accommodations
                = Accommodations.builder()
                .accommodationTitle("역삼 컬리넌")
                .contact("050350524475")
                .accommodationsType(AccomodationsEnum.Type.MOTEL)
                /*.seller(seller)*/
                .address(address).build();
        accommodationsRepository.save(accommodations);

        BorrowRoom borrowRoom = BorrowRoom.builder()
                .borrowTime(4)
                .borrowPrice(32000L)
                .operateTime("17:00 ~ 22:00")
                .build();
        Room room = Room.builder()
                .roomPrice(132000L)
                .standardPerson(2)
                .maxPerson(2)
                .checkInTime("15:00")
                .checkOutTime("11:00")
                .borrowRoom(borrowRoom)
                .useYn(true)
                /*.accommodations(accommodations)*/
                .build();
        roomReposittory.save(room);


        Accommodations accom = accommodationsRepository.findById(accommodations.getAccommodationSeq()).orElse(null);
        Assertions.assertThat(accom).isNotNull();
        Assertions.assertThat(accommodations.getAccommodationSeq()).isEqualTo(accom.getAccommodationSeq());
    }
}
