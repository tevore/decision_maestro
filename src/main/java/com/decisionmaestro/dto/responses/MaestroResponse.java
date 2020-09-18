package com.decisionmaestro.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MaestroResponse {

    @NonNull
    private String message;
    private int statusCode;

    private Map<String, String> messages;
}
