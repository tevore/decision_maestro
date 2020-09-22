package com.decisionmaestro.dto.requests;

import io.micronaut.core.annotation.Introspected;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class VoteRequest {

    @NonNull
    private String userId;
    @NonNull
    private String item;
    @NonNull
    private String call;
}
