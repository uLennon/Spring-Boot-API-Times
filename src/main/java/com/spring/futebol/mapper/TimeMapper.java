package com.spring.futebol.mapper;

import com.spring.futebol.domain.Time;
import com.spring.futebol.request.TimePostRequestBody;
import com.spring.futebol.request.TimePutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class TimeMapper {
    public static final  TimeMapper INSTANCE = Mappers.getMapper(TimeMapper.class);
    public abstract Time toTime(TimePostRequestBody timePostRequestBody);
    public abstract Time toTime(TimePutRequestBody timePutRequestBody);
}
