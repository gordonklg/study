package gordon.study.elastic.highlevel;

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

public class EsService {

    public static void main(String[] args) {
        try {
            RestHighLevelClient client = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost("localhost", 9200, "http")));

            String[] projects= new String[]{"AM", "NDQ", "APIG"};
            String[] emails = new String[]{"zhangsan@163.com","lisi@163.com","wangwu@163.com"};
            Random r = new Random();

            BulkRequest bulkRequest = new BulkRequest();

            for (int i = 0; i < 100; i++) {


                IndexRequest request = new IndexRequest("gitlab", "_doc", UUID.randomUUID().toString());
                Map<String, Object> jsonMap = new HashMap<>();
                jsonMap.put("group_name", "SGM_INT_B2C");
                jsonMap.put("project_name", projects[r.nextInt(projects.length)]);
                jsonMap.put("committer_email", emails[r.nextInt(emails.length)]);
                jsonMap.put("committed_date", LocalDateTime.now().minusMinutes(r.nextInt(300000) + 500));
                int addLine = r.nextInt(500) + 20;
                int delLine = r.nextInt(200) + 10;
                jsonMap.put("addition_line", addLine);
                jsonMap.put("deletion_line", delLine);
                jsonMap.put("total_line", addLine + delLine);
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
