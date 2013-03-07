package com.marklogic.adamfowler.javasamples;

import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.MatchLocation;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StructuredQueryBuilder;
import com.marklogic.client.query.StructuredQueryBuilder.Operator;
import com.marklogic.client.query.StructuredQueryDefinition;

public class CustomSearchOptions extends BaseExample {

  /**
   * @param args
   */
  public static void main(String[] args) {
    
    // See MarkLogic Java API doc here: https://docs.marklogic.com/guide/java/query-options#id_84576
    
    // execute search

    QueryManager queryMgr = client.newQueryManager();

    // create a search definition
    //QueryOptionsManager qoManager = client.newServerConfigManager().newQueryOptionsManager();
    //QueryOptionsHandle handle = qoManager.readOptions("adamsoptions", new QueryOptionsHandle()); // how to load if you wanted to see the options
    
    StructuredQueryBuilder qb = new StructuredQueryBuilder("adamsoptions");
    
    // set the search argument
    StructuredQueryDefinition querydef = qb.rangeConstraint("kingdom", Operator.EQ,"bird"); // uses custom search option constraint 'kingdom' - see CreateCustomSearchOptions for details
    

    // create a handle for the search results
    SearchHandle resultsHandle = new SearchHandle();

    // set the page length
    queryMgr.setPageLength(10);
    // run the search
    System.out.println("Performing search");
    queryMgr.search(querydef, resultsHandle, 1);
    
    // display result document URIs
    MatchDocumentSummary[] summaries = resultsHandle.getMatchResults();
    System.out.println("Results summaries count: " + summaries.length);
    for (MatchDocumentSummary summary : summaries ) {
      System.out.println("Matching doc URI: " + summary.getUri());
      
      // now do something with the result
      MatchLocation[] locations = summary.getMatchLocations();
      for (MatchLocation location : locations) {
        // do something with the snippet text
        System.out.println("  Snippet: " + location.getAllSnippetText());      
      }
    }
    System.out.println("Results listing completed");
    
    client.release();
  }

}
