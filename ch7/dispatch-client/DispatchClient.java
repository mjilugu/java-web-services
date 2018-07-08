


public class DispatchClient {
  private static final String baseUrl = "http://localhost:8080/aphorisms2/";

  public static void main(String[] args) {
    new DispatchClient().callRestlet();
  }

  private void callRestlet() {
    QName qname = getQName("uri", "restlet"); // service's name
    Service service = Service.create(qname);
    runTests(service);
  }

  private void runTests(Service service) {
    // get all -- plain text
    Dispatch<Source> dispatch = getDispatch(service, getQName("get","All"), baseUrl);
    setRequestMethod(dispatch, "GET");
    Source result = dispatch.invoke(null);
    stringifyAndPrintResponse("Result (plaintext):", result);

    // get all -- xml
    dispatch = getDispatch(service, getQName("get", "AllXml"), baseUrl + "xml");
    setRequestMethod(dispatch, "GET");
    result = dispatch.invoke(null);
    stringifyAndPrintResponse("Result (xml):", result);

    // get all -- json
    dispatch = getDispatch(service, getQName("get", "AllJson"), baseUrl + "json");
    setRequestMethod(dispatch, "GET");
    result = dispatch.invoke(null);
    stringifyAndPrintResponse("Result (json):", result);

    // get one -- xml
    dispatch = getDispatch(service, getQName("get", "OneXml"), baseUrl + "xml/2");
    setRequestMethod(dispatch, "GET");
    result = dispatch.invoke(null);
    stringifyAndPrintResponse("Result (one--xml):", result);

    // delete
    dispatch = getDispatch(service, getQName("delete", "One"), baseUrl + "delete/3");
    setRequestMethod(dispatch, "DELETE");
    result = dispatch.invoke(null);
    stringifyAndPrintResponse("Result (delete):", result);

    // post -- failure
    dispatch = getDispatch(service, getQName("post", "Create"), baseUrl + "create");
    setRequestMethod(dispatch, "POST");
    String cargo = "<a>words=This test will not work!</a>"; // minimal XML
    StringReader reader = new StringReader(cargo);
    result = dispatch.invoke(new StreamSource(reader));
    stringifyAndPrintResponse("Result (post):", result);
  }

  private Dispatch<Source> getDispatch(Service service, QName portName, String url) {
    service.addPort(portName, HTTPBinding.HTTP_BINDING, url);
    return service.createDispatch(portName,
                                  Source.class,
                                  javax.xml.ws.Service.Mode.MESSAGE);
  }

  private void strigifyAndPrintResponse(String msg, Source result) {
    String str = null;
    if (result instanceof StreamSource) {
      try {
        StreamSource source = (StreamSource) result;
        byte[] buff = new byte[1024]; // adages are short
        source.getInputStream().read(buff);
        str = new String(buff);
      }
      catch(Exception e) { throw new RuntimeException(e); }
    }
    System.out.println("\n" + msg + "\n" + str);
  }
}
