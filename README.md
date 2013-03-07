# Java Samples

These samples were created for partners and customers that wanted specific use cases covered in a simple sample.

If you need any more please email adam.fowler@marklogic.com

NB to run the samples you'll need to download the Java API libraries and add them to this eclipse project. You can get these here:-

https://developer.marklogic.com/products/java

- Get example with pre-persisted query options (from the server)
 - example for custom constraint (and default search grammar)
- Put example on used.demo.marklogic.com on a REST Server port - plus URIs for custom search options
- Find example of using snippets, and fetching full document - in Top Songs sample
 - can it be done in one call? - No, Either Snippets or Document
 - how is best to do it? -> fetch the 10 on current page as required
- If customer specifies extra information for each search result (E.g. xhtml keyword element), then how to pull those back?
 - E.g. title = /my/xpath
- How to parse custom/advanced search details in a generic manner?
 - e.g. 'title' etc on the query options (advanced search page) -> use a custom saved options node and pass this as a constraint value (See Top Songs advanced search)
 