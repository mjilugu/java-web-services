


public class TempConverterClient {
  private static final String endpoint = "https://localhost:8443/tc";
  // Make the client "trusting" and handle the hostname verification.
  static {
    HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
      public boolean verify(String name, SSLSession session) {
        return true;  // allow everything
      }
    });

    try {
      TrustManager[] trustMgr = new TrustManager[] {
        new X509TrustManager () {
          public X509Certificate[] getAcceptedIssuers() {
            return null;
          }
          public void checkClientTrusted(X509Certificate[] cs, String t) {
            // no-op
          }
          public void checkServerTrusted(X509Certificate[] cs, String t) {
            // no-op
          }
        }
      };
      SSLContext sslCtx = SSLContext.getInstance("TLS");
      sslCtx.init(null, trustMgr, null);
      HttpsURLConnection.setDefaultSSLSocketFactory(sslCtx.getSocketFactory());
    }
    catch(Exception e) { throw new RuntimeException(e); }
  }

  public static void main (String[] args) {
    if (args.length < 2) {
      System.err.println("Usage: TempConvertClient <uname> <passwd>");
      return;
    }
    String uname = args[0];
    String passwd = args[1];
    String passwdHash = RealmBase.Digest(passwd,  // password
                                  "SHA",          // algorithm
                                  null);          // default encoding: utf-8
    TempConvertService service = new TempConvertService();
    TempConvert port = service.getTempConvertPort();
    BindingProvider prov = (BindingProvider) port;
    prov.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                                endpoint);
    prov.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, uname);
    prov.getRequestContext().put(BidningProvider.PASSWORD_PROPERTY, passwdHash);
    System.out.println("f2c(-40.1) = " + port.f2C(-40.1f));
    System.out.println("c2F(-40.1) = " + port.c2F(-40.1f));
    System.out.println("f2c(+98.7) = " + port.f2c(+98.7f));
  }
}
