package com.decisionmaestro.service;

import com.decisionmaestro.dto.responses.MaestroResponse;

import javax.inject.Inject;
import java.util.concurrent.ExecutionException;

public class DecisionEvaluationService {

    private final DynamoClientService dynamoClientService;

    @Inject
    public DecisionEvaluationService(DynamoClientService dynamoClientService) {
        this.dynamoClientService = dynamoClientService;
    }


    /**
     * Collects the decisionSession item for a given id and returns back a list of mutually
     * agreed upon items
     * @param decisionSessionId
     * @return
     */
    public String getDecision(String decisionSessionId) throws ExecutionException, InterruptedException {
        return dynamoClientService.retrieveSessionIdList(decisionSessionId);
//        return null;
    }

    public String getQuery() throws ExecutionException, InterruptedException {
        return dynamoClientService.performQuery();
    }
}
