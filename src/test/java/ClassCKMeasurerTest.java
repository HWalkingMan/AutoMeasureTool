import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.Test;

/**
 * @Description
 * @Author WM
 * @Date 2020/4/28 10:12
 */
public class ClassCKMeasurerTest {

  @Test
  public void CKMeasurera() throws IOException {
    XmlScanner.scanXML();
    ClassCKMeasurer measurer=new ClassCKMeasurer();
    ClassCKMeasurer.setClassMap(XmlScanner.returnClassMap());
    System.out.println("CBO> "+measurer.measurerCBO("class6"));
    System.out.println("WMC> "+measurer.measurerWMC("class6"));
    System.out.println("DIT> "+measurer.measurerDIT("class6"));
    System.out.println("LCOM> "+measurer.measurerLCOM("class6"));
    System.out.println("NOC> "+measurer.measurerNOC("class6"));
    System.out.println("RFC> "+measurer.measurerRFC("class6"));

    measurer.measurerDIT("class6");
  }

}