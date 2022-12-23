package com.example.jpamaster.accommodations.controller;

import com.example.jpamaster.accommodations.dto.AccommodationDto;
import com.example.jpamaster.accommodations.service.AccommodationService;
import com.example.jpamaster.common.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/accommodation")
@Api(tags = {"숙박 Controller"})
public class AccommodationController {

    private final AccommodationService accommodationService;

    @PostMapping // TODO: 비즈니스 로직이 점점 늘어나겠지?, 추후 로그인 기능 넣어지면 토근 파싱해서 seller 객체 넣쟈 ㅎㅎ
    @ApiOperation(value = "숙박 정보 저장 API")
    public ApiResponse<Void> add(@RequestBody AccommodationDto param) {
        accommodationService.addAccommodation(param);
        // 이렇게 생성할 경우, controller 단에서 객체 생성 코드가 반복적으로 일어남.. > 개선해야 할 것 같다.
        //return new ApiResponse<AccommodationDto>(param);
        /**
         * MEMO: 객체 생성 없이 return 하는 방향을 생각 > ApiResponse 클래스 자체가 제네릭 클래스이다.
         * 객체 생성 없이 하려면 > 정적 메소드를 활용해야 될 것 같은데 제네릭 클래스에 정적 메소드를 사용하면 컴파일 오류 발생
         * 이는, 제네렉 정적 메소드에서 제네릭 타입 파라미터를 받는데 객체 생성 전까지는 타입을 알 수 없기 때문이다.
         * 그래서 static 키워드 뒤에 제네릭을 붙임으로써 제네릭 메소드를 만들어준다.
         */
        return ApiResponse.createOk(null);
    }

    @GetMapping()
    @ApiOperation(value = "숙박 정보 조회 API")
    public ApiResponse<AccommodationDto> find (@RequestParam Long accommodationSeq) {
        return ApiResponse.createOk(accommodationService.findAccommodation(accommodationSeq));
    }

    @GetMapping("/location")
    @ApiOperation(value = "숙박 위치 정보 조회 API")
    public ApiResponse<Void> findLocation() {
        accommodationService.findLocationToAccommodation();
        return ApiResponse.createOk(null);
    }


}
