package com.spring.futebol.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimePostRequestBody {
    @NotNull
    @NotEmpty(message = "O nome do time nao pode esta vazio")
    private String name;
}
