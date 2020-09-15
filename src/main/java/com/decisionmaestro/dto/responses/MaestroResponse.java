package com.decisionmaestro.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MaestroResponse {

    @NonNull
    private String message;
    private int statusCode;
}
