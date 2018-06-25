package predictions2;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Collections;
import java.beans.XMLEncoder;
import javax.servlet.ServletContext;

public class Predictions {
    private ConcurrentMap<Integer, Prediction> predictions;
    private ServletContext sctx;
    private AtomicInteger mapKey;

    public Predictions () {
      predictions = new ConcurrentHashMap<Integer, Prediction> ();
      mapKey = new AtomicInteger();
    }

    // The ServletContext is required to read the data from
    // a text file packaged inside the WAR file
    public void setServletContext (ServletContext sctx) {
      this.sctx = sctx;
    }

    public ServletContext getServletContext () {
      return this.sctx;
    }

    public void setMap (ConcurrentMap<Integer, Prediction> predictions) {
      // no-op for now
    }

    public ConcurrentMap<Integer, Prediction> getMap () {
      if (getServletContext() == null) {
        return null;
      }

      if (predictions.size() < 1) {
        populate();
      }

      return this.predictions;
    }

    public String toXML(Object obj) {
      String xml = null;

      try {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLEncoder encoder = new XMLEncoder(out);
        encoder.writeObject(obj);
        encoder.close();
        xml = out.toString();
      }
      catch (Exception e) {
        xml = e.getMessage();
      }

      return xml;
    }

    public int addPrediction (Prediction p) {
      int id = mapKey.incrementAndGet();
      p.setId(id);
      predictions.put(id, p);
      return id;
    }

    //** utility
    private void populate () {
      String filename = "/WEB-INF/data/predictions.db";
      InputStream in = sctx.getResourceAsStream(filename);

      if (in != null) {
        try {
          InputStreamReader isr = new InputStreamReader(in);
          BufferedReader reader = new BufferedReader(isr);
          int i = 0;
          String record = null;

          while ((record = reader.readLine()) != null) {
            String[] parts = record.split("!");
            Prediction p = new Prediction ();
            p.setWho(parts[0]);
            p.setWhat(parts[1]);
            addPrediction(p);
          }
        }
        catch (IOException e) {}
      }
    }
}
