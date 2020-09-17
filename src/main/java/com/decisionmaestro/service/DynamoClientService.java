package com.decisionmaestro.service;

import io.micronaut.context.annotation.Prototype;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Singleton
public class DynamoClientService {


    public CompletableFuture<PutItemResponse> generateSession(String id, String decisionSessionId, String userId, String initiatedTime) {
        Map<String, AttributeValue> itemRequestMap = new HashMap<>();

        AttributeValue val1 = AttributeValue.builder().s(id).build();
        itemRequestMap.put("dec_ses_id", val1);
        AttributeValue val2 = AttributeValue.builder().s(decisionSessionId).build();
        itemRequestMap.put("session_use_id", val2);
        AttributeValue val3 = AttributeValue.builder().s(userId).build();
        itemRequestMap.put("user_id", val3);
        AttributeValue val4 = AttributeValue.builder().s(initiatedTime).build();
        itemRequestMap.put("init_time", val4);

        DynamoDbAsyncClient dynamoDbAsyncClient = DynamoDbAsyncClient.create();
        PutItemRequest putItemRequest = PutItemRequest.builder().item(itemRequestMap).tableName("decision_session").build();
        CompletableFuture<PutItemResponse> future = dynamoDbAsyncClient.putItem(putItemRequest);
//        dynamoDbAsyncClient.close();
        return future;
    }
}
