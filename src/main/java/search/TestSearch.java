package search;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;
import utils.ConnectUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestSearch {


    public static void main(String[] args) {
        TestSearch search = new TestSearch();
//        search.addIndex();
        search.testQuery();
//        search.del();
    }


    public void testQuery() {
        HttpSolrClient solrClient = ConnectUtils.instance().getSolrClient();




        //分页
//		solrQuery.setStart(0);
//        solrQuery.setRows(0);

        //开启高亮...
//        solrQuery.setHighlight(true);

        //我需要那几个字段进行高亮...


    }

    public void del() {
        HttpSolrClient solrClient = ConnectUtils.instance().getSolrClient();
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
        HttpSolrClient solrClient = ConnectUtils.instance().getSolrClient();
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
        HttpSolrClient solrClient = ConnectUtils.instance().getSolrClient();
        Map<String, SolrInputField> filedMap = new HashMap();
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