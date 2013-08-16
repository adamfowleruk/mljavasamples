package com.marklogic.adamfowler.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.extensions.ResourceManager;
import com.marklogic.client.extensions.ResourceServices;
import com.marklogic.client.extensions.ResourceServices.ServiceResultIterator;
import com.marklogic.client.io.DOMHandle;
import com.marklogic.client.io.Format;
import com.marklogic.client.util.RequestParameters;

public class RestApisManager extends ResourceManager {
  static final public String NAME = "rest-apis";
  
  public RestApisManager(DatabaseClient client) {
    super();
    
    client.init(NAME,this);
  }
  
  public List<RestApiDescriptor> getRestApis() {
    RequestParameters params = new RequestParameters();
    params.add("service", "/v1/rest-apis");

    // get the initialized service object from the base class
    ResourceServices services = getServices();
    // call the service implementation on the REST Server, 
    // returning a ResourceServices object
    DOMHandle dh = new DOMHandle();
    services.get(params, dh);
    //iterate over results, get content
    
    //DOMHandle responseManifest = resultItr.next().getContent(new DOMHandle());

    List<RestApiDescriptor> items = new ArrayList<RestApiDescriptor>();

    NodeList responseItems = dh.get().getDocumentElement().getChildNodes();
    int      responseCount = responseItems.getLength();
    for (int i=0; i < responseCount; i++) {
      Node responseNode = responseItems.item(i);
      if (responseNode.getNodeType() != Node.ELEMENT_NODE)
        continue;
      Element responseItem = (Element) responseNode;

      String  name       = null;
      String  group   = null;
      String  database  = null;
      String  modulesDatabase   = null;
      String  port = null;

      NodeList fieldItems = responseItem.getChildNodes();
      int      fieldCount = fieldItems.getLength();
      for (int j=0; j < fieldCount; j++) {
        Node fieldNode = fieldItems.item(j);
        if (fieldNode.getNodeType() != Node.ELEMENT_NODE)
          continue;
        Element fieldItem = (Element) fieldNode;
        String  fieldName = fieldItem.getLocalName();

        if ("name".equals(fieldName)) {
          name       = fieldItem.getTextContent();
        } else if ("group".equals(fieldName)) {
          group   = fieldItem.getTextContent();
        } else if ("database".equals(fieldName)) {
          database  = fieldItem.getTextContent();
        } else if ("modulesDatabase".equals(fieldName)) {
          modulesDatabase   = fieldItem.getTextContent();
        } else if ("port".equals(fieldName)) {
          port = fieldItem.getTextContent();
        } else {
          // TODO: warn
        }
      }
      
      RestApiDescriptor desc = new RestApiDescriptor(name,group,database,modulesDatabase,port);
      
      items.add(desc);
    }
    
    return items;
  }
    
  public class RestApiDescriptor {
    String name = null;String group = null;String database = null;String modulesDatabase = null;String port = null;
    
    public RestApiDescriptor(String name,String group,String database,String modulesDatabase,String port) {
      this.name = name;
      this.group = group;
      this.database = database;
      this.modulesDatabase = modulesDatabase;
      this.port = port;
    }

    public String getName() {
      return name;
    }

    public String getGroup() {
      return group;
    }

    public String getDatabase() {
      return database;
    }

    public String getModulesDatabase() {
      return modulesDatabase;
    }

    public String getPort() {
      return port;
    }
    
    
  }
  
  public static void main(String args[]) {
    DatabaseClient client = DatabaseClientFactory.newClient("localhost", 8002, "admin", "admin", DatabaseClientFactory.Authentication.DIGEST);
    RestApisManager mgr = new RestApisManager(client);
    
    List<RestApiDescriptor> results = mgr.getRestApis();
    
    Iterator<RestApiDescriptor> iter = results.iterator();
    
    while (iter.hasNext()) {
      RestApiDescriptor ins = iter.next();
      System.out.println("Rest Instance: name: " + ins.getName() + ", group: " + ins.getGroup() + ", database: " + ins.getDatabase() + 
          ", modulesDatabase: " + ins.getModulesDatabase() + ", port: " + ins.getPort());
    }
    
    System.out.println("Done");
    
  }
}
