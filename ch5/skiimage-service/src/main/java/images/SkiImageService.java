package images;

@WebService
public class SkiImageService {
  private Map<String, String> photos;

  @WebMethod
  public Image getImage(String name) {
    return createImage(name);
  }

  @WebMethod
  public List<Image> getImages() {
    return createImageList();
  }

  public SkiImageService () {
    photos = new HashMap<String,String>();
    photos.put("nordic", "nordic.jpg");
    photos.put("alpine", "alpine.jpg");
    photos.put("telemk", "telemk.jpg");
  }

  private Image createImage(String name) {
    String fileName = photos.get(name);
    byte[] bytes = getRawBytes(fileName);
    ByteArrayInputStream in = new ByteArrayInputStream(bytes);
    Iterator iterators = ImageIO.getImageReadersByFormatName("jpeg");
    ImageReader iterator = (ImageReader) iterators.next();
    Image image = null;

    try {
      ImageInputStream iis = ImageIO.createImageInputStream(in);
      iterator.setInput(iis, true);
      image = iterator.read(0);
    }
    catch(Exception e) { throw new RuntimeException(e);}
    return image;
  }

  private List<Image> createImageList() {
    List<Image> list = new ArrayList<Image>();
    for (String key : photos.keySet()) {
      Image image = createImage(key);
      if (image  != null) list.add(image);
    }
    return list;
  }

  private byte[] getRawBytes(String filename) {
    if (filename == null) {
      filename = "nordic.jpg";
    }
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      FileInputStream in = new FileInputStream(filename);
      if (in == null) {
        in = new FileInputStream("nordi.jpg")
      }
      byte[] buffer = new byte[2048];
      int n = 0;
      while ((n = in.read(buffer)) != -1) {
        out.write(buffer, 0, n);
      }
    }
    catch(Exception e) {
      throw new RuntimeException(e);
    }
    return out.toByteArray();
  }
}
