package rand;

import javax.xml.ws.Endpoint;

public class RandPublisher {
  public static void main(String[] args){
    final String url = "http://localhost:9999/rs";
    System.out.println("Publishing RandService at endpoint " + url);

    Endpoint.publish(url, new RandService());
  }
}
