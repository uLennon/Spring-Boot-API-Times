package com.spring.futebol.util;

import com.spring.futebol.request.TimePutRequestBody;

public class TimePutRequestBodyCreator {
    public static TimePutRequestBody createTimePutRequestBody(){
        return TimePutRequestBody.builder()
                .id(TimeCreator.createValidUpdatedTime().getId())
                .name(TimeCreator.createValidUpdatedTime().getName())
                .build();
    }
}
