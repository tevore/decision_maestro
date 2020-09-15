package com.decisionmaestro.service;

import com.decisionmaestro.dto.requests.DecisionEventRequest;
import com.decisionmaestro.dto.responses.MaestroResponse;
import io.micronaut.context.annotation.Prototype;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.HashMap;
import java.util.Map;

//TODO -- abstract away dynamoDB interaction class ( or make it a microservice )
//TODO should we limit the # of microservices for now and expand out later or prototype? I think so....
//TODO properly configure AWS settings to submit item to DynamoDB
@Prototype
public class DecisionEventGenerationService {

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
        Map<String, AttributeValue> itemRequestMap = new HashMap<>();
        AttributeValue val = AttributeValue.builder().s("1").build();
        itemRequestMap.put("dec_ses_id", val);
        DynamoDbAsyncClient dynamoDbAsyncClient = DynamoDbAsyncClient.create();
        PutItemRequest putItemRequest = PutItemRequest.builder().item(itemRequestMap).tableName("decision_session").build();
        dynamoDbAsyncClient.putItem(putItemRequest);
        //messengerApiClient.sendLink(request.getDecidees());
        return new MaestroResponse("Decision event generated", 200);
    }
}
