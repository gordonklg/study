package gordon.study.elastic.restclient;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.RequestLine;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.io.IOException;

public class Basic {

    public static void main(String[] args) {
        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200, "http")).build();

        try {
            Request request = new Request("GET", "/");
            Response response = restClient.performRequest(request);
            RequestLine requestLine = response.getRequestLine();
            HttpHost host = response.getHost();
            int statusCode = response.getStatusLine().getStatusCode();
            Header[] headers = response.getHeaders();
            String responseBody = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
