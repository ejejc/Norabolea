package com.example.jpamaster.accommodations.controller;

import com.example.jpamaster.accommodations.dto.CouponDto;
import com.example.jpamaster.accommodations.service.CouponService;
import com.example.jpamaster.common.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
@Api(tags = {"쿠폰 Controller"})
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    @ApiOperation(value = "어드민 - 쿠폰 저장 API")
    public ApiResponse<Long> addCoupon(@RequestBody CouponDto couponDto) {
        return ApiResponse.createOk(couponService.addCoupon(couponDto));
    }

}
