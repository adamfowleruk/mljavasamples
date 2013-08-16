package com.marklogic.adamfowler.admin;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPDigestAuthFilter;

public class RestApisJersey {
  
  
  public static void main(String args[]) {
    ClientConfig config = new DefaultClientConfig();
    Client client = Client.create(config);
    client.addFilter(new HTTPDigestAuthFilter("admin","admin"));
    WebResource service = client.resource(getBaseURI());
    // Fluent interfaces
    ClientResponse response = service.path("rest-apis").accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
    System.out.println(response.toString());
    
    RestApis restapis = response.getEntity(RestApis.class);
    
    RestApi[] apis = restapis.getRestApis();
    RestApi ins;
    for (int i = 0;i < apis.length;i++) {
      ins = apis[i];
      System.out.println("Rest Instance: name: " + ins.getName() + ", group: " + ins.getGroup() + ", database: " + ins.getDatabase() + 
          ", modulesDatabase: " + ins.getModulesDatabase() + ", port: " + ins.getPort());
    }
    
  }
  


  private static URI getBaseURI() {
    return UriBuilder.fromUri("http://localhost:8002/v1").build();
  }
  
}
