package com.decisionmaestro.service;

import com.decisionmaestro.dto.requests.DecisionEventRequest;
import com.decisionmaestro.dto.requests.VoteRequest;
import com.decisionmaestro.dto.responses.MaestroResponse;
import io.micronaut.context.annotation.Prototype;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
    public MaestroResponse generate(DecisionEventRequest request) throws ExecutionException, InterruptedException {


//        String decisionSessionId = UUID.randomUUID().toString();
        LocalDateTime localDateTime = LocalDateTime.now(ZoneOffset.UTC);
        List<CompletableFuture> deciderMessages = new ArrayList<>();
//        for(String decider : request.getDecidees()) {
            deciderMessages.add(dynamoClientService.generateSession(
                    UUID.randomUUID().toString(),
                    request.getDecidees(),
                    localDateTime.toString(),
                    Arrays.asList("place 1", "place2", "place3")));
//        }

        for(CompletableFuture<PutItemResponse> x : deciderMessages) {
            PutItemResponse resp = x.get();
            System.out.println("C-F: " + resp.attributes());
        }

        return new MaestroResponse("Decision event generated", 200, null);
    }

    public String postVote(String decisionSessionId, VoteRequest voteRequest) {
        return dynamoClientService.postVoteForSession(decisionSessionId, voteRequest);
//        return "";
    }
}
