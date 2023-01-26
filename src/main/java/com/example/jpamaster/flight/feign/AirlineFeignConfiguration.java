package com.example.jpamaster.flight.feign;

import com.example.jpamaster.common.exception.JpaMasterBadRequest;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AirlineFeignConfiguration implements ErrorDecoder {


    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                throw new JpaMasterBadRequest("항공사 정보를 찾아올 수 없습니다.");
        }

        return null;
    }
}
