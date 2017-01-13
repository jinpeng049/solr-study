package utils;

import org.apache.solr.client.solrj.impl.HttpSolrClient;

/**
 * Created by Administrator on 2017/1/13.
 */
public class ConnectUtils {

    private static final String URL = "http://localhost:8983/solr/gettingstarted";

    private HttpSolrClient solrClient;

    private static ConnectUtils connectUtils;

    private ConnectUtils() {
    }

    public static ConnectUtils instance() {
        if (connectUtils == null) {
            connectUtils = new ConnectUtils();
            connectUtils.init();
            return connectUtils;
        }
        return connectUtils;
    }

    private void init() {
        solrClient = new HttpSolrClient.Builder(URL).build();
        solrClient.setConnectionTimeout(3000);
    }

    public HttpSolrClient getSolrClient() {
        return solrClient;
    }

    public void setSolrClient(HttpSolrClient solrClient) {
        this.solrClient = solrClient;
    }
}
