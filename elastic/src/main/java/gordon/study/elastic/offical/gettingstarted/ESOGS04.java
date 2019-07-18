package gordon.study.elastic.offical.gettingstarted;

import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ESOGS04 {

    public static void main(String[] args) {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));

        try {
            // Sample Query
            // https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.7/java-rest-high-search.html
            SearchRequest searchRequest = new SearchRequest("bank");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchAllQuery()).sort("account_number", SortOrder.ASC).from(10).size(10);
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(searchResponse);

            // Sample Query Match
            searchRequest = new SearchRequest("bank");
            searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchQuery("address", "mill lane"));
            String[] includeFields = new String[]{"account_number", "balance"};
            searchSourceBuilder.fetchSource(includeFields, null);
            searchRequest.source(searchSourceBuilder);
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(searchResponse);

            // Sample Query Match Phrase
            searchRequest = new SearchRequest("bank");
            searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchPhraseQuery("address", "mill lane"));
            searchRequest.source(searchSourceBuilder);
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(searchResponse);

            // Sample Query 2
            // https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.7/java-rest-high-query-builders.html
            searchRequest = new SearchRequest("bank");
            searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.boolQuery()
                    .must(QueryBuilders.matchQuery("address", "mill lane"))
                    .must(QueryBuilders.matchQuery("gender", "M"))
                    .mustNot(QueryBuilders.matchQuery("state", "MD")));
            searchRequest.source(searchSourceBuilder);
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(searchResponse);

            // Sample Query 4
            searchRequest = new SearchRequest("bank");
            searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("address", "mill lane"))
                    .must(QueryBuilders.matchQuery("gender", "M"))
                    .must(QueryBuilders.boolQuery()
                            .should(QueryBuilders.matchQuery("age", 38))
                            .should(QueryBuilders.matchQuery("age", 28))
                    )
                    .mustNot(QueryBuilders.matchQuery("state", "MD")));
            searchRequest.source(searchSourceBuilder);
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(searchResponse);

            // Sample Query Range
            searchRequest = new SearchRequest("bank");
            searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.rangeQuery("balance").gte(20000).lte(30000));
            searchRequest.source(searchSourceBuilder);
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(searchResponse);

            // Sample Query Filter
            searchRequest = new SearchRequest("bank");
            searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.boolQuery()
                    .must(QueryBuilders.matchAllQuery())
                    .filter(QueryBuilders.rangeQuery("balance").gte(20000).lte(30000)));
            searchRequest.source(searchSourceBuilder);
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(searchResponse);

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