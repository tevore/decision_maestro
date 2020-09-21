package com.decisionmaestro.service;

import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Singleton
public class DynamoClientService {

    private final DynamoDbAsyncClient dynamoDbAsyncClient;

    @Inject
    public DynamoClientService() {
        this.dynamoDbAsyncClient = DynamoDbAsyncClient.create();
    }


    public CompletableFuture<PutItemResponse> generateSession(String id, List userIds, String initiatedTime, List places) {
        Map<String, AttributeValue> itemRequestMap = new HashMap<>();

        AttributeValue val1 = AttributeValue.builder().s(id).build();
        itemRequestMap.put("dec_ses_id", val1);
        AttributeValue val2 = AttributeValue.builder().ss(userIds).build();
        itemRequestMap.put("user_ids", val2);
        AttributeValue val3 = AttributeValue.builder().s(initiatedTime).build();
        itemRequestMap.put("init_time", val3);
        AttributeValue val4 = AttributeValue.builder().ss(places).build();
        itemRequestMap.put("places", val4);
//        AttributeValue val5 = AttributeValue.builder().ss(Collections.emptyList()).build();
//        itemRequestMap.put("votes", val5);

        PutItemRequest putItemRequest = PutItemRequest.builder().item(itemRequestMap).tableName("decision_session").build();
        CompletableFuture<PutItemResponse> future = dynamoDbAsyncClient.putItem(putItemRequest);
        return future;
    }

    public String retrieveSessionIdList(String decisionSessionId) throws ExecutionException, InterruptedException {
        Map<String, KeysAndAttributes> map = new HashMap<>();
        Map<String, AttributeValue> keys = new HashMap<>();
        keys.put("dec_ses_id", AttributeValue.builder().s(decisionSessionId).build());
        KeysAndAttributes keysAndAttributes = KeysAndAttributes.builder().keys(keys).build();
        map.put("decision_session", keysAndAttributes);
        BatchGetItemRequest batchGetItemRequest = BatchGetItemRequest.builder().requestItems(map).build();
        CompletableFuture<BatchGetItemResponse> retrievedItem = dynamoDbAsyncClient.batchGetItem(batchGetItemRequest);
        BatchGetItemResponse batch = retrievedItem.get();
        String sdf = batch.toString();
        System.out.println(">>>>>> " + sdf);

        return sdf;
    }


    public String performQuery() throws ExecutionException, InterruptedException {
//        String uid = "{zz3}";
        Map<String, AttributeValue> keyMap = new HashMap<>();
        Map<String, Condition> conditionMap = new HashMap<>();
//        keyMap.put(":sui", AttributeValue.builder().ss("6d235791-723b-4ad1-96ad-1ca8ce0acfd0").build());
        keyMap.put(":dci", AttributeValue.builder().s("13bb75f8-bd7f-4dbb-af80-3e63aa99ec5c").build());
        QueryRequest queryRequest = QueryRequest.builder().tableName("decision_session")
                .keyConditionExpression("dec_ses_id = :dci")
                .expressionAttributeValues(keyMap)
//                .filterExpression("session_use_id = :sui")
//                .queryFilter(conditionMap)
                .limit(25).build();
        CompletableFuture<QueryResponse> queryReq = dynamoDbAsyncClient.query(queryRequest);
        QueryResponse response = queryReq.get();

        return response.toString();
    }
}
