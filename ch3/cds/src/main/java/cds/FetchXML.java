package cds;

import org.json.JSONObject;
import org.json.XML;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class FetchXML {
  public void setJson(String json) {}

  public String getJson() {
    JSONObject json = null;

    try {
      String xml = "";
      URL url = new URL("http://www.w3schools.com/xml/cd_catalog.xml");
      URLConnection conn = url.openConnection();
      BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

      String line = null;
      while ((line = in.readLine()) != null) {
        xml += line;
      }
      in.close()

      xml = xml.replace("'", "");
      json = XML.toJSONObject(xml.toLowerCase());
    }
    catch(Exception e) {

    }

    return json.toString();
  }
}
