package com.decisionmaestro.service;

import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Singleton
public class DynamoClientService {

    private final DynamoDbAsyncClient dynamoDbAsyncClient;

    @Inject
    public DynamoClientService() {
        this.dynamoDbAsyncClient = DynamoDbAsyncClient.create();
    }


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

    //TODO move uid to FilterExpression
    public String performQuery() throws ExecutionException, InterruptedException {
        String uid = "{zz3}";
        Map<String, AttributeValue> map = new HashMap<>();
        map.put(":uid", AttributeValue.builder().s("zz3").build());
        map.put(":dci", AttributeValue.builder().s("308cdc08-b35d-4fdb-b194-5cfa4aa63993").build());
        QueryRequest queryRequest = QueryRequest.builder().tableName("decision_session").keyConditionExpression("dec_ses_id = :dci and user_id = :uid")
                .expressionAttributeValues(map).limit(3).build();
        CompletableFuture<QueryResponse> queryReq = dynamoDbAsyncClient.query(queryRequest);
        QueryResponse response = queryReq.get();

        return response.toString();
    }
}
