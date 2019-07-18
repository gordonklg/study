package gordon.study.elastic.offical.gettingstarted;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.RequestLine;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.util.ArrayUtils;

import java.io.IOException;
import java.util.Arrays;

public class ESOGS01 {

    public static void main(String[] args) {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));

        try {
            // Health Check
            // https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.7/java-rest-high-cluster-health.html
            ClusterHealthRequest request = new ClusterHealthRequest();
            ClusterHealthResponse response = client.cluster().health(request, RequestOptions.DEFAULT);
            System.out.println(response);
            System.out.println();

            // List Nodes
            // CAN NOT FIND!
            // use Low Level REST Client
            // https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.7/java-rest-low-usage-requests.html
            Request request2 = new Request("GET", "/_cat/nodes?v");
            // https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.7/java-rest-low-usage-responses.html
            Response response2 = client.getLowLevelClient().performRequest(request2);
            System.out.println(response2);
            System.out.println("requestLine: " + response2.getRequestLine());
            System.out.println("host: " + response2.getHost());
            System.out.println("statusCode: " + response2.getStatusLine().getStatusCode());
            System.out.println("headers: ");
            Arrays.stream(response2.getHeaders()).forEach(x -> System.out.println("  " + x));
            System.out.println("responseBody: \n" + EntityUtils.toString(response2.getEntity()));
            System.out.println();


            // List Indices
            Request request3 = new Request("GET", "/_cat/indices?v");
            // https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.7/java-rest-low-usage-responses.html
            Response response3 = client.getLowLevelClient().performRequest(request3);
            System.out.println("responseBody: \n" + EntityUtils.toString(response3.getEntity()));

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
