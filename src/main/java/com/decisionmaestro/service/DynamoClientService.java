package com.decisionmaestro.service;

import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class DynamoClientService {


    public void generateSession() {
        Map<String, AttributeValue> itemRequestMap = new HashMap<>();
        AttributeValue val = AttributeValue.builder().s("1").build();
        itemRequestMap.put("dec_ses_id", val);
        DynamoDbAsyncClient dynamoDbAsyncClient = DynamoDbAsyncClient.create();
        PutItemRequest putItemRequest = PutItemRequest.builder().item(itemRequestMap).tableName("decision_session").build();
        dynamoDbAsyncClient.putItem(putItemRequest);
    }
}
