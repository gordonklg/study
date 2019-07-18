package gordon.study.elastic.offical.gettingstarted;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ESOGS03 {

    public static void main(String[] args) {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));

        try {
            // Update Document
            // https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.7/java-rest-high-document-update.html
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("name", "Joe");
            jsonMap.put("salary", 12345);
            UpdateRequest updateRequest = new UpdateRequest("customer", "_doc", "1").doc(jsonMap);
            UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
            System.out.println(updateResponse);

            // Update Document by script
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("count", 11111);
            Script inline = new Script(ScriptType.INLINE, "painless",
                    "ctx._source.salary += params.count", parameters);
            updateRequest = new UpdateRequest("customer", "_doc", "1").script(inline);
            updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
            System.out.println(updateResponse);

            // Delete Document
            // https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.7/java-rest-high-document-delete.html
            DeleteRequest deleteRequest = new DeleteRequest("customer", "_doc", "1");
            DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
            System.out.println(deleteResponse);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
            }
        }
    }
}