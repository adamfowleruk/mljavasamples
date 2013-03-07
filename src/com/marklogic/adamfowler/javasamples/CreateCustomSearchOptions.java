package com.marklogic.adamfowler.javasamples;

import javax.xml.namespace.QName;

import com.marklogic.client.admin.QueryOptionsManager;
import com.marklogic.client.io.QueryOptionsHandle;
import com.marklogic.client.admin.config.QueryOptionsBuilder;

public class CreateCustomSearchOptions extends BaseExample {
  public static void main(String[] args) {
    
    // See MarkLogic Java API doc here: https://docs.marklogic.com/guide/java/query-options#id_84576

    // create a search options object on the server (for future requests)
    QueryOptionsManager qoManager = client.newServerConfigManager().newQueryOptionsManager();
    
    QueryOptionsHandle handle = new QueryOptionsHandle();
    QueryOptionsBuilder builder = new QueryOptionsBuilder();
    // construct the options for the example
    handle.withValues(
      builder.values("kingdom",
        builder.range(builder.elementRangeIndex(
          new QName("animal-kingdome"), // NB range index must already exist
          builder.rangeType("xs:string"))
        )
      )
    );
    
    // write to server (user MUST HAVE rest-admin role)
    qoManager.writeOptions("adamsoptions", handle);
    
    client.release();
  }
}
