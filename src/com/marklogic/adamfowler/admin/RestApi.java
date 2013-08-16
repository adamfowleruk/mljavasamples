package com.marklogic.adamfowler.admin;

import javax.xml.bind.annotation.XmlElement;

public class RestApi {
  @XmlElement(name="name", namespace = "http://marklogic.com/rest-api")
  protected String m_name = null;

  @XmlElement(name="group", namespace = "http://marklogic.com/rest-api")
  protected String m_group = null;
  
  @XmlElement(name="database", namespace = "http://marklogic.com/rest-api")
  protected String m_database = null;
  
  @XmlElement(name="modules", namespace = "http://marklogic.com/rest-api")
  protected String m_modulesDatabase = null;

  @XmlElement(name="port", namespace = "http://marklogic.com/rest-api")
  protected String m_port = null;
  
  public RestApi() {
    
  }
  
  public RestApi(String name,String group,String database,String modulesDatabase,String port) {
    this.m_name = name;
    this.m_group = group;
    this.m_database = database;
    this.m_modulesDatabase = modulesDatabase;
    this.m_port = port;
  }

  public String getName() {
    return m_name;
  }

  public String getGroup() {
    return m_group;
  }

  public String getDatabase() {
    return m_database;
  }

  public String getModulesDatabase() {
    return m_modulesDatabase;
  }

  public String getPort() {
    return m_port;
  }

  public void setName(String name) {
    this.m_name = name;
  }

  public void setGroup(String group) {
    this.m_group = group;
  }

  public void setDatabase(String database) {
    this.m_database = database;
  }

  public void setModulesDatabase(String modulesDatabase) {
    this.m_modulesDatabase = modulesDatabase;
  }

  public void setPort(String port) {
    this.m_port = port;
  }
  
  
}

