package gordon.study.elastic.offical.gettingstarted;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

public class ESOGS05 {

    public static void main(String[] args) {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));

        try {
            // Sample Aggregations
            SearchRequest searchRequest = new SearchRequest("bank");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.aggregation(AggregationBuilders.terms("group_by_state").field("state.keyword"))
                    .size(0);
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(searchResponse);

            // Sample Aggregations 2
            searchRequest = new SearchRequest("bank");
            searchSourceBuilder = new SearchSourceBuilder();
            TermsAggregationBuilder stateTermsAggs = AggregationBuilders.terms("group_by_state").field("state.keyword").order(BucketOrder.aggregation("average_balance", false));
            AvgAggregationBuilder balanceAvgAggs = AggregationBuilders.avg("average_balance").field("balance");
            stateTermsAggs.subAggregation(balanceAvgAggs);
            searchSourceBuilder.aggregation(stateTermsAggs).size(0);
            searchRequest.source(searchSourceBuilder);
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(searchResponse);

            // Sample Aggregations 3
            searchRequest = new SearchRequest("bank");
            searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.aggregation(AggregationBuilders.range("group_by_age").field("age").addRange(20, 30).addRange(30, 40).addRange(40, 50)
                    .subAggregation(AggregationBuilders.terms("group_by_gender").field("gender.keyword")
                            .subAggregation(AggregationBuilders.avg("average_balance").field("balance"))
                    )
            ).size(0);
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