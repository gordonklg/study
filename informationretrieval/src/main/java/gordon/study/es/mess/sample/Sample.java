package gordon.study.es.mess.sample;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class Sample {

    public static void main(String[] args) {
        try {
            RestHighLevelClient client = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost("localhost", 9200, "http")));

            String[] mats = new String[]{"MAT1", "MAT2", "MAT3"};
            int skuPerMat = 3;
            Random r = new Random();

            BulkRequest bulkRequest = new BulkRequest();

            for (int i = 0; i < 100; i++) {


                IndexRequest request = new IndexRequest("test", "_doc");
                Map<String, Object> jsonMap = new HashMap<>();
                int offset = r.nextInt(mats.length);
                jsonMap.put("invCode", UUID.randomUUID().toString());
                jsonMap.put("materialCode", mats[offset]);
                jsonMap.put("sku", "SKU" + (skuPerMat * offset + r.nextInt(skuPerMat) + 1));
                jsonMap.put("created", LocalDateTime.now());
                jsonMap.put("price", 5000);
                request.source(jsonMap);

                bulkRequest.add(request);
            }

            BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            System.out.println(bulkResponse.hasFailures());
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
