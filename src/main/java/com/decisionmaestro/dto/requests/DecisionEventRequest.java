package com.decisionmaestro.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DecisionEventRequest {

    @NonNull
    private List<String> decisionCriteria;
    @NonNull
    private List<String> decidees;

}
