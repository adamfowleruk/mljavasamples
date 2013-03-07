package com.marklogic.adamfowler.javasamples;

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
    if (properties.containsKey("name")) {
      // print out name
      System.out.println("Animal name: " + (String)properties.get("name"));
    } else {
      System.out.println("This animal has no name!");
    }
    
  }
}
