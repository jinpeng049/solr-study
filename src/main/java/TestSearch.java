import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestSearch {


    private static final String URL = "http://localhost:8983/solr/gettingstarted";
    private static HttpSolrClient solrClient = null;

    public static void main(String[] args) {
        TestSearch search = new TestSearch();
        search.init();
//        search.addIndex();
        search.testQuery();
//        search.del();
    }

    private void init() {
        solrClient = new HttpSolrClient.Builder(URL).build();
        solrClient.setConnectionTimeout(3000);
    }

    public void testQuery() {

        //http://localhost:8983/solr/gettingstarted/select?wt=json&indent=true&q=*:*&rows=0&facet=true&facet.field=manu_id_s
        SolrQuery solrQuery = new SolrQuery();

//        solrQuery.setQuery("description:小键");
        solrQuery.setQuery("*:*");
        solrQuery.addFacetField("manu_id_s");
//        solrQuery.set("wt", "xml");
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("wt", "json");
//        SolrParams params = new MapSolrParams(map);
//        solrQuery.add(params);
        //分页
//		solrQuery.setStart(0);
        solrQuery.setRows(0);

        //开启高亮...
        solrQuery.setHighlight(true);

        //高亮显示的格式...
//        solrQuery.setHighlightSimplePre("<font color='red'>");
//        solrQuery.setHighlightSimplePost("</font>");


        //我需要那几个字段进行高亮...

//        solrQuery.setParam("hl.fl", "description");
        QueryResponse queryResponse = null;
        try {
            queryResponse = solrClient.query(solrQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        System.err.println("respHeader:" + JSON.toJSONString(queryResponse.getFacetFields()));
        List<FacetField> facetFields = queryResponse.getFacetFields();
        facetFields.forEach(e -> {
            System.err.println("e:" + e);
        });
        System.err.println("queryResponse:" + queryResponse);

        //返回所有的结果...
        SolrDocumentList documentList = queryResponse.getResults();

//        Map<String, Map<String, List<String>>> maplist = queryResponse.getHighlighting();

        //返回高亮之后的结果..

        for (SolrDocument solrDocument : documentList) {
            System.err.println("solrDocument:" + solrDocument);
//			Object name=solrDocument.get("name");
//			Object content=solrDocument.get("description");
//			System.out.println(id);
//			System.out.println(name);
//			System.out.println(content);
        }
    }

    public void del() {
        try {
            solrClient.deleteById("9527");
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addIndex() {
        SolrInputDocument document = new SolrInputDocument();

        document.addField("id", "9527");

        document.addField("name", "武书静");

        document.addField("xxxxx_ss", "很有文艺范的一个名字");

        try {
            solrClient.add(document);

            solrClient.commit();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void addData() {
        Map<String, SolrInputField> filedMap = new HashMap<String, SolrInputField>();
        SolrInputField fa = new SolrInputField("id");
        fa.addValue("id_JJJJJ", 1);

        SolrInputField fb = new SolrInputField("aa");
        fb.addValue("aaa", 1);
        filedMap.put("id", fa);
        //filedMap.put("a", fb);
        SolrInputDocument doc = new SolrInputDocument(filedMap);
        try {
            //solrClient.deleteById("id");
            solrClient.add(doc);
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}