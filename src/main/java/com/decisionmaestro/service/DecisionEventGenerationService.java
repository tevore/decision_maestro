package com.decisionmaestro.service;

import com.decisionmaestro.dto.requests.DecisionEventRequest;
import com.decisionmaestro.dto.responses.MaestroResponse;
import io.micronaut.context.annotation.Prototype;

import javax.inject.Inject;

@Prototype
public class DecisionEventGenerationService {

    private final DynamoClientService dynamoClientService;

    @Inject
    public DecisionEventGenerationService(DynamoClientService dynamoClientService) {
        this.dynamoClientService = dynamoClientService;
    }

    /**
     * This service method creates an entry into the db with the
     * original set of data and the modifiable set
     * It also communicates with the MessengerAPI to send a message
     * and link to all decidees inviting them to modify the list
     *
     * @param request
     * @return a message/status of how the operation went
     */
    public MaestroResponse generate(DecisionEventRequest request) {
        //dbConnection.save(request.getDecisionCriteria());
        dynamoClientService.generateSession();
        //messengerApiClient.sendLink(request.getDecidees());
        return new MaestroResponse("Decision event generated", 200);
    }
}
