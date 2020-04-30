import java.util.List;
import java.util.Map;
import java.util.Scanner;
import service.CMDMeasurerService;
import service.ClassCKMeasurer;
import service.XmlScanner;

/**
 * @Description
 * @Author WM
 * @Date 2020/4/28 14:22
 */
public class CMDAppMain {

  public static void main(String[] args) {

    CMDMeasurerService service=new CMDMeasurerService();
    System.out.println("welcome to use auto measure tool!");
    System.out.println("please input middle xml file path(end by enter):");

    Scanner input=new Scanner(System.in);

    String path=input.nextLine();

    System.out.println("your input is ["+path+"]");

    service.setMidXMLPATH(path);

    try {
      service.loadXML();
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
      return;
    }

    List<String> measurableClassName = service.getMeasurableClassName();

    for (String classname:measurableClassName){
      Map<String, Integer> result = service.measureClassByName(classname);

      StringBuilder sb=new StringBuilder();
      System.out.println("-------------------------------------------------");
      sb.append("|");
      for(int i=0;i<(25-classname.length()-10);i++) sb.append("\t");
      sb.append("class:").append(classname);
      for(int i=0;i<(25-classname.length()-10);i++) sb.append("\t");
      sb.append("|");
      System.out.println(sb.toString());
      System.out.println("-------------------------------------------------");

      sb=new StringBuilder();
      sb.append("|\t\tWMC\t\t\t|\t\t\t").append(result.get("WMC"));
      for (int i=0;i<3-(result.get("WMC").toString().length()/2);i++)
        sb.append("\t");
      sb.append("|\t\tDIT\t\t\t|\t\t\t").append(result.get("DIT"));
      for (int i=0;i<3-(result.get("DIT").toString().length()/2);i++)
        sb.append("\t");
      sb.append("|\n");
      sb.append("|\t\tRFC\t\t\t|\t\t\t").append(result.get("RFC"));
      for (int i=0;i<3-(result.get("RFC").toString().length()/2);i++)
        sb.append("\t");
      sb.append("|\t\tNOC\t\t\t|\t\t\t").append(result.get("NOC"));
      for (int i=0;i<3-(result.get("NOC").toString().length()/2);i++)
        sb.append("\t");
      sb.append("|\n");
      sb.append("|\t\tCBO\t\t\t|\t\t\t").append(result.get("CBO"));
      for (int i=0;i<3-(result.get("CBO").toString().length()/2);i++)
        sb.append("\t");
      sb.append("|\t\tLCOM\t\t|\t\t\t").append(result.get("LCOM"));
      for (int i=0;i<3-(result.get("LCOM").toString().length()/2);i++)
        sb.append("\t");
      sb.append("|");
      System.out.println(sb.toString());
      System.out.println("-------------------------------------------------");



      System.out.println();
    }


    String classname="class13";

//    System.out.println("-------------------------------------------------");
//    sb.append("|");
//    for(int i=0;i<(25-classname.length()-9);i++) sb.append("\t");
//    sb.append("class:").append(classname);
//    for(int i=0;i<(25-classname.length()-9);i++) sb.append("\t");
//    sb.append("|");
//    System.out.println(sb.toString());
//    System.out.println("-------------------------------------------------");
//

  }
}
