package com.marklogic.adamfowler.javasamples;

import java.util.ArrayList;

import javax.xml.namespace.QName;

import com.marklogic.client.admin.QueryOptionsManager;
import com.marklogic.client.io.QueryOptionsHandle;
import com.marklogic.client.admin.config.QueryOptions.QueryConstraint;
import com.marklogic.client.admin.config.QueryOptionsBuilder;

public class CreateCustomSearchOptions extends BaseExample {
  public static void main(String[] args) {
    
    // See MarkLogic Java API doc here: https://docs.marklogic.com/guide/java/query-options#id_84576

    // create a search options object on the server (for future requests)
    QueryOptionsManager qoManager = client.newServerConfigManager().newQueryOptionsManager();
    
    QueryOptionsHandle handle = new QueryOptionsHandle();
    QueryOptionsBuilder builder = new QueryOptionsBuilder();
    // construct the options for the example
    ArrayList<QueryConstraint> cs = new ArrayList<QueryConstraint>();
    cs.add(
      builder.constraint("kingdom",
        builder.range(builder.elementRangeIndex(
          new QName("animal-kingdom"), // NB range index must already exist
          builder.rangeType("xs:string"))
        )
      )
    );
    handle.setConstraints(cs);
    
    // write to server (user MUST HAVE rest-admin role)
    System.out.println("Creating options on server");
    qoManager.writeOptions("adamsoptions", handle);
    System.out.println("Search options adding complete");
    
    client.release();
  }
}
