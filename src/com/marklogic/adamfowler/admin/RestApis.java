package com.marklogic.adamfowler.admin;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "rest-apis", namespace = "http://marklogic.com/rest-api")
public class RestApis {
  
  public RestApis() {
    
  }
  
    @XmlElement(name = "rest-api", namespace = "http://marklogic.com/rest-api")
    RestApi[] restApi;
 
    public RestApi[] getRestApis() {
      return restApi;
    }
    
    public void setRestApis(RestApi[] restApis) {
      this.restApi = restApis;
    }
 
}