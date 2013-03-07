package com.marklogic.adamfowler.javasamples;

import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;

import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.DocumentMetadataHandle.DocumentProperties;

public class GetDocumentProperties extends BaseExample {
  public static void main(String args[]) {
    // fetch a document's properties directly from it's URI
    
    String docuri = "/animals/penguin.xml";
    
    // I want an XML representation (default) of the doc
    XMLDocumentManager docMgr = client.newXMLDocumentManager();
    
    // get a metadata handle
    DocumentMetadataHandle metadataHandle = new DocumentMetadataHandle();
    docMgr.readMetadata(docuri, metadataHandle); 
    
    DocumentProperties properties = metadataHandle.getProperties();
    
    // check if property exists (best practice)
    QName qname = new QName("http://marklogic.com/xdmp/property","last-modified");
    if (properties.containsKey(qname)) {
      // print out name
      System.out.println("Animal last modified: " + properties.get(qname));
    } else {
      System.out.println("This animal has no last modified date!");
    }
    
    System.out.println("Looping through all properties");
    // show all properties
    Set<QName> set = properties.keySet();
    Iterator<QName> setIter = set.iterator();
    while (setIter.hasNext()) {
      QName key = setIter.next();
      Object value = properties.get(key);
      System.out.println("  " + key.toString() + " = " + value.toString());
    }
    System.out.println("Done");
    
    client.release();
  }
}
