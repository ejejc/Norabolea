package com.example.jpamaster.flight.service;

import lombok.Getter;

import java.util.List;

@Getter
public class AirlineInfoVo {

    private Response response;

    @Getter
    public static class Response {
        private Object header;
        private Body body;

        @Getter
        public static class Body {
            private List<Item> items;

            @Getter
            public static class Item {
                private String airlineImage;
                private String airlineName;
                private String airlineTel;
                private String airlineIcTel;
                private String airlineIata;
                private String airlineIcao;
            }
        }
    }
}
