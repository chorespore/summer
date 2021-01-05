package com.chao.summer;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class AWSTest {

    @Test
    public void getItemTest() {
        final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.AP_SOUTHEAST_1).build();

        String ISBN = "9787111641247";
        HashMap<String, AttributeValue> key_to_get = new HashMap<>();
        key_to_get.put("ISBN", new AttributeValue(ISBN));

        GetItemRequest request = new GetItemRequest().withKey(key_to_get).withTableName("book");

        try {
            Map<String, AttributeValue> returned_item = ddb.getItem(request).getItem();
            if (returned_item != null) {
                Set<String> keys = returned_item.keySet();
                for (String key : keys) {
                    System.out.format("%s: %s\n", key, returned_item.get(key).toString());
                }
            } else {
                System.out.format("No item found with the key %s!\n", ISBN);
            }
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }

    @Test
    public void listTableTest() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("ABC", "abc/456");
        AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard()
//                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(Regions.AP_SOUTHEAST_1).build();

        ListTablesRequest request;
        boolean more_tables = true;
        String last_name = null;

        while (more_tables) {
            try {
                if (last_name == null) {
                    request = new ListTablesRequest().withLimit(10);
                } else {
                    request = new ListTablesRequest().withLimit(10).withExclusiveStartTableName(last_name);
                }

                ListTablesResult table_list = ddb.listTables(request);
                List<String> table_names = table_list.getTableNames();

                if (table_names.size() > 0) {
                    for (String cur_name : table_names) {
                        System.out.format("* %s\n", cur_name);
                    }
                } else {
                    System.out.println("No tables found!");
                    System.exit(0);
                }

                last_name = table_list.getLastEvaluatedTableName();
                if (last_name == null) {
                    more_tables = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
