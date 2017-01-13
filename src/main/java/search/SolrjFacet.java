package search;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.RangeFacet;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ConnectUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/1/13.
 */
public class SolrjFacet {

    private static final Logger log = LoggerFactory.getLogger(SolrjFacet.class);

    @Test
    public void tFacet() {
        /**
         *http://localhost:8983/solr/gettingstarted/select?q=*:*&wt=json&indent=on&rows=0&
         *facet=true&facet.range=price&f.price.facet.range.start=0&f.price.facet.range.end=600&f.price.facet.range.gap=50&facet.range.other=after
         */
        HttpSolrClient solrClient = ConnectUtils.instance().getSolrClient();
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("*:*");
        solrQuery.setFacet(true);
        solrQuery.addFacetField("manu_id_s");
        solrQuery.addNumericRangeFacet("price", 0, 600, 50);
        solrQuery.addFacetPivotField("cat", "inStock");
//        solrQuery.

        log.debug("solrQuery:{}", solrQuery);
        QueryResponse queryResponse = null;
        try {
            queryResponse = solrClient.query(solrQuery);
        } catch (Exception e) {
            log.error("queryFact exception", e);
        }

        List<FacetField> facetFields = queryResponse.getFacetFields();
        facetFields.forEach(e -> {
            log.debug("e:{};" + e);
        });
        List<RangeFacet> rangeFacets = queryResponse.getFacetRanges();
        rangeFacets.forEach(rangeFacet -> {
            log.debug("range After:{};", rangeFacet.getAfter());
            log.debug("range Between:{};", rangeFacet.getBetween());

        });

        log.debug("queryResponse:{}", queryResponse);

        SolrDocumentList documentList = queryResponse.getResults();

        for (SolrDocument solrDocument : documentList) {
            log.debug("solrDocument:{};", solrDocument);
        }
    }

}
