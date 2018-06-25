import clientSOAP.PredictionsSOAP;
import clientSOAP.PredictionsSOAPService;
import clientSOAP.Prediction;
import clientSOAP.ClientHandlerResolver;
import java.util.List;

public class PredictionsClient {
  public static void main (String[] args) {
    if (args.length < 2) {
      System.err.println("Usage: PredictionsClient <name> <key>");
      return;
    }

    new PredictionsClient().runTests(args[0], args[1]);
  }

  private void runTests(String name, String key) {
    PredictionsSOAPService service = new PredictionsSOAPService();
    service.setHandlerResolver(new ClientHandlerResolver(name, key));
    PredictionSOAP port = service.getPredictionsSOAPPort();

    getTests(port);
    postTest(port);
    getAllTest(port);     // confirm the POST
    deleteTest(port, 33); // delete the just POSTed prediction
    getAllTest(port);     // confirm the POST
    putTest(port);
  }

  private void getTests(PredictionsSOAP port) {
    getAllTest(port);
    getOneTest(port);
  }

  private void getAllTest(PredictionsSOAP port) {
    msg("getAll");
    List<Prediction> preds = port.getAll();
    for (Prediction pred : preds) {
      System.out.println(String.format("%2d: ", pred.getId()) + pred.getWho() +
                          " predicts: " + pred.getWhat());
    }
  }

  private void getOneTest(PredictionsSOAP port) {
    msg("getOne (31)");
    System.out.println(port.getOne(31).getWhat());
  }

  private void postTest(PredictionSOAP port) {
    msg("postTest");
    String who = "Freddy";
    String what = "Something bad may happen.";
    String res = port.create(who, what);
    System.out.println(res);
  }

  private void putTest(PredictionsSOAP port) {
    msg("putTest -- here's the record to be edited");
    getOneTest(port);
    String who = "FooBar";
    String what = null;   // shouldn't change
    int id = 31;
    String res = port.edited(id, who, what);
    System.out.println("Confirming");
    Prediction p = port.getOne(31);
    System.out.println(p.getWho());
    System.out.println(p.getWhat());
  }

  private void deleteTest(PredictionsSOAP port, int id) {
    msg("deleteTest");
    String res = port.delete(id);
    System.out.println(res);
  }

  private void msg(String s) {
    System.out.println("\n" + s + "\n");
  }
}
