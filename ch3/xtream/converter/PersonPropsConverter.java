

public class PersonPropsConverter implements Converter {
  public boolean canConvert(Class c) {
    return c.equals(PersonProps.class);
  }

  public void marshal(Object object, HierarchicalStreamWriter writer,
                      MarshallingContext context) {
    PersonProps person = (PersonProps) object;
    writer.startNode("name");
    writer.setValue(person.getName());
    writer.endNode();
  }

  public Object unmarshal(HierarchicalStreamReader reader,
                          UnmarshallingContext context) {
    PersonProps person = new PersonProps();
    reader.moveDown();
    person.setName(reader.getValue());
    reader.moveUp();
    return person;
  }
}
