package com.decisionmaestro.controller;

import com.decisionmaestro.dto.requests.DecisionEventRequest;
import com.decisionmaestro.dto.requests.VoteRequest;
import com.decisionmaestro.dto.responses.MaestroResponse;
import com.decisionmaestro.service.DecisionEvaluationService;
import com.decisionmaestro.service.DecisionEventGenerationService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

import javax.inject.Inject;
import java.util.concurrent.ExecutionException;

@Controller
public class DecisionMaestroController {

    private final DecisionEventGenerationService decisionEventGenerationService;
    private final DecisionEvaluationService decisionEvaluationService;

    @Inject
    public DecisionMaestroController(DecisionEventGenerationService decisionEventGenerationService,
                                     DecisionEvaluationService decisionEvaluationService) {
        this.decisionEventGenerationService = decisionEventGenerationService;
        this.decisionEvaluationService = decisionEvaluationService;
    }

    @Post(uri = "/generate")
    @Produces(MediaType.APPLICATION_JSON)
    public MaestroResponse generateDecisionEvent(DecisionEventRequest request) throws ExecutionException, InterruptedException {
        return decisionEventGenerationService.generate(request);
    }

    @Get(uri = "/{decisionSessionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDecision(@PathVariable(name = "decisionSessionId") String decisionSessionId) throws ExecutionException, InterruptedException {
        return decisionEvaluationService.getDecision(decisionSessionId);
    }

    @Get(uri = "/query")
    @Produces(MediaType.APPLICATION_JSON)
    public String getQueryResponse() throws ExecutionException, InterruptedException {
        return decisionEvaluationService.getQuery();
    }

    @Post(uri = "/vote/{decisionSessionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String postAVote(@PathVariable(name = "decisionSessionId") String decisionSessionId,
                            VoteRequest voteRequest) {

        return decisionEventGenerationService.postVote(decisionSessionId, voteRequest);

    }

}
