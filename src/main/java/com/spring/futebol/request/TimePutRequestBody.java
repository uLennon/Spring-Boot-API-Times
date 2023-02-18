package com.spring.futebol.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimePutRequestBody {
    private Long id;
    private String name;
}
