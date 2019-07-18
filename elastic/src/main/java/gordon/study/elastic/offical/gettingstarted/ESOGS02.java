package gordon.study.elastic.offical.gettingstarted;

import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;
import java.util.Arrays;

public class ESOGS02 {

    public static void main(String[] args) {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));

        try {
            // Delete Index
            // https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.7/java-rest-high-delete-index.html
            try {
                DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("customer");
                AcknowledgedResponse deleteIndexResponse = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
                System.out.println(deleteIndexResponse.isAcknowledged());
            } catch (ElasticsearchException exception) {
                if (exception.status() == RestStatus.NOT_FOUND) {
                    System.out.println("Index not found.");
                }
            }

            // Create Index
            // https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.7/java-rest-high-create-index.html
            CreateIndexRequest createIndexRequest = new CreateIndexRequest("customer");
            CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            System.out.println(createIndexResponse.isAcknowledged());

            // Create Document
            // https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.7/java-rest-high-document-index.html
            IndexRequest indexRequest = new IndexRequest("customer", "_doc", "1");
            indexRequest.source("name", "John Doe");
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            System.out.println(indexResponse);

            // Get Document
            // https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.7/java-rest-high-document-get.html
            GetRequest getRequest = new GetRequest("customer", "_doc", "1");
            GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
            System.out.println(getResponse);

            // Create Document Auto ID
            indexRequest = new IndexRequest("customer", "_doc");
            indexRequest.source("name", "John Doe");
            indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            System.out.println(indexResponse.getId());

            client.close();
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
