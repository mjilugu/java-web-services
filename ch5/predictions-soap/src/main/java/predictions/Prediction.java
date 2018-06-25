package predictions;

import java.io.Serializable;

public class Prediction implements Serializable, Comparable<Prediction> {
  private String who;
  private String what;
  private int    id;

  public Prediction() {
    // noop
  }

  public void setWho (String who) {
    this.who = who;
  }

  public String getWho () {
    return this.who;
  }

  public void setWhat (String what) {
    this.what = what;
  }

  public String getWhat () {
    return this.what;
  }

  public void setId (int id) {
    this.id = id;
  }

  public int getId () {
    return this.id;
  }

  public int compareTo(Prediction other) {
    return this.id - other.id;
  }
}
