package adages;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Random;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/")
public class Adages {
  // Add aphorisms to taste...
  private String[] alphorisms = {
    "What can be shown cannot be said.",
    "If a lion could talk, we could not understand him.",
    "Philosophy is a battle against the bewitchment of our intelligence by means of language",
    "Ambition is the death of thought.",
    "The limits of my language mean the limits of my world."
  };

  public Adages() {}

  @GET
  @Produces({MediaType.APPLICATION_XML}) // could use "application/xml"
  public JAXBElement<Adage> getXml () {
    return toXml(createAdage());
  }

  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Path("/json")
  public String getJson() {
    return toJson(createAdage());
  }

  @GET
  @Produces({MediaType.TEXT_PLAIN})
  @Path("/plain")
  public String getPlain() {
    return createAdage().toString() + "\n";
  }

  private Adage createAdage() {
    Adage adage = new Adage();
    adage.setWords(alphorisms[new Random().nextInt(alphorisms.length)]);
    return adage;
  }

  // Java Adage --> XML Document
  @XmlElementDecl(namespace = "http://alphorism.adage", name = "adage")
  private JAXBElement<Adage> toXml (Adage adage) {
    return new JAXBElement<Adage>(new QName("adage"), Adage.class, adage);
  }

  // Java Adage --> JSON Document
  private String toJson (Adage adage) {
    String json = "If you see this, there's a problem.";
    try {
      json = new ObjectMapper().writeValueAsString(adage);
    }
    catch (Exception e) { }
    return json;
  }
}
