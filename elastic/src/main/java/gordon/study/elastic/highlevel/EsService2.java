package gordon.study.elastic.highlevel;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class EsService2 {

    public static void main(String[] args) {
        try {
            RestHighLevelClient client = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost("localhost", 9200, "http")));

            SearchRequest searchRequest = new SearchRequest("gitlab");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.termQuery("committer_email", "zhangsan@163.com"));
            searchRequest.source(searchSourceBuilder);


            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            System.out.println(hits.totalHits);
            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit : searchHits) {
                System.out.println(hit);
            }

            client.close();
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) {
                System.out.println("CONFLICT");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
