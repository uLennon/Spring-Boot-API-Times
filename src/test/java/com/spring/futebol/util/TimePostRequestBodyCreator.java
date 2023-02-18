package com.spring.futebol.util;

import com.spring.futebol.request.TimePostRequestBody;

public class TimePostRequestBodyCreator {
    public static TimePostRequestBody createTimePostRequestBody(){
        return TimePostRequestBody.builder()
                .name(TimeCreator.createValidTime().getName())
                .build();
    }
}
