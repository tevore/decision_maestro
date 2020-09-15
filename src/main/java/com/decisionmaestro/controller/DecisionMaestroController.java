package com.decisionmaestro.controller;

import com.decisionmaestro.dto.requests.DecisionEventRequest;
import com.decisionmaestro.dto.responses.MaestroResponse;
import com.decisionmaestro.service.DecisionEventGenerationService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;

import javax.inject.Inject;

@Controller
public class DecisionMaestroController {

    private final DecisionEventGenerationService decisionEventGenerationService;

    @Inject
    public DecisionMaestroController(DecisionEventGenerationService decisionEventGenerationService) {
        this.decisionEventGenerationService = decisionEventGenerationService;
    }

    @Post(uri = "/generate")
    @Produces(MediaType.APPLICATION_JSON)
    public MaestroResponse generateDecisionEvent(DecisionEventRequest request) {
        return decisionEventGenerationService.generate(request);
    }

}
