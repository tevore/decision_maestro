package com.decisionmaestro.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class VoteRequest {

    @NonNull
    private String userId;
    @NonNull
    private String item;
    @NonNull
    private String call;
}
