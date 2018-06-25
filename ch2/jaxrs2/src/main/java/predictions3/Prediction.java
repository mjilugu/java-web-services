
package predictions3;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "prediction")
public class Prediction implements Comparable<Prediction> {
  private String who;
  private String what;
  private int    id;

  public Prediction () {}

  @Override
  public String toString () {
    return String.format("%2d: ", id) + who + " ==> " + what + "\n";
  }

  public void setWho(String who) {
    this.who = who;
  }

  @XmlElement
  public String getWho() {
    return this.who;
  }

  public void setWhat(String what) {
    this.what = what;
  }

  @XmlElement
  public String getWhat() {
    return this.what;
  }

  public void setId(int id) {
    this.id = id;
  }

  @XmlElement
  public int getId() {
    return this.id;
  }

  public int compareTo(Prediction other) {
    return this.id - other.id;
  }
}
