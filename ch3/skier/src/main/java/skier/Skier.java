package skier;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootelement
public class Skier {
  private Person person;
  private String nationalTeam;
  private Collection majorAchievements;

  public Skier () {

  }

  public Skier (Person person, String nationalTeam,
      Collection<String> majorAchievements) {
    setPerson(person);
    setNationalTeam(nationalTeam);
    setMajorAchievements(majorAchievements);
  }

  public Person getPerson () {
    return this.person;
  }

  public void setPerson (Person person) {
    this.person = person;
  }

  public String getNationalTeam () {
    return this.nationalTeam;
  }

  public void setNationalTeam (String nationalTeam) {
    this.nationalTeam = nationalTeam;
  }

  public String getNationalTeam () {
    return this.nationalTeam;
  }

  public Collection getMajorAchievements () {
    return this.majorAchievements;
  }

  public void setMajorAchievements(Collection majorAchievements) {
    this.majorAchievements = majorAchievements;
  }
}
