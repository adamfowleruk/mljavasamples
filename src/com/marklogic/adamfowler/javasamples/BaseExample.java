package com.marklogic.adamfowler.javasamples;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.DatabaseClientFactory.Authentication;

public class BaseExample {

  // get server connection
  static DatabaseClient client = DatabaseClientFactory.newClient("used.demo.marklogic.com", 8060, "rest-sample", "obvious", Authentication.DIGEST);
  

}
