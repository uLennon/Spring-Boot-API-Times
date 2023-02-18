package com.spring.futebol.util;

import com.spring.futebol.domain.Time;

public class TimeCreator {
    public static Time createTimeTobeSaved(){
        return Time.builder()
                .name("Botafogo")
                .build();
    }
    public static Time createValidTime(){
        return Time.builder()
                .name("Botafogo")
                .id(1L)
                .build();
    }
    public static Time createValidUpdatedTime(){
            return Time.builder()
                    .name("Botafogo")
                    .id(1L)
                    .build();
        }

}
