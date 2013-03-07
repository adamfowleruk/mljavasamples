package com.marklogic.adamfowler.javasamples;

import java.io.InputStream;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.InputStreamHandle;

public class GetDocumentElements extends BaseExample {

  /**
   * @param args
   */
  public static void main(String[] args) {
    
    // See Java Developer's Guide at https://docs.marklogic.com/guide/java/document-operations#id_59305
    // Also see XPath tutorial at http://onjava.com/pub/a/onjava/2005/01/12/xpath.html

    String docuri = "/animals/penguin.xml";
    
    // I want an XML representation (default) of the doc
    XMLDocumentManager docMgr = client.newXMLDocumentManager();
    
    // fetch document's content DOM
    InputStream byteStream = docMgr.read(docuri, new InputStreamHandle()).get();
    InputSource inputSource = new InputSource(byteStream);
    
    // extract elements we want with XPath (less code than DOM)
    XPathFactory factory = XPathFactory.newInstance();
    XPath xPath = factory.newXPath();
    try {
      Element kingdom = (Element)(xPath.evaluate("/animal/animal-kingdom", inputSource,  XPathConstants.NODE));
      if (null != kingdom) {
        System.out.println("Animal's kingdom is: " + kingdom.toString());
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
  }

}
