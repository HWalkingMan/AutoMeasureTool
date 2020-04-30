package service;

import components.MyClass;
import controller.MainController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @Description
 * @Author WM
 * @Date 2020/4/28 11:16
 */
public class GUIMeasurerService {

  private MainController controller;

  private Map<String, MyClass> classTable;

  List<String> measurableClassnames=new ArrayList<>();

  ClassCKMeasurer measurer=new ClassCKMeasurer();

  public GUIMeasurerService(MainController controller) {
    this.controller = controller;
  }

  public void setMidXMLPATH(String xmlpath){
    XmlScanner.setMidXMLPATH(xmlpath);
  }

  public void loadXML()  {
    new Runnable() {
      @Override
      public void run() {
        try {
          XmlScanner.scanXML();
        }
        catch (Exception e) {
          e.printStackTrace();
          controller.setWarnMsg(e.getMessage());
        }
        classTable=XmlScanner.returnClassMap();

        getMeasurableClassName();

        controller.setSl_classesContent(measurableClassnames);

        measurer.setClassMap(classTable);
      }
    }.run();

  }

  private void getMeasurableClassName(){
    for(Entry<String,MyClass> entry:classTable.entrySet()){
      if(((MyClass)entry.getValue()).getName()!=null && !((MyClass)entry.getValue()).getName().isEmpty()){
        measurableClassnames.add(entry.getKey());
      }
    }
  }

  public Map<String,Integer> measureClassByName(String className){
    Map<String,Integer> result=new HashMap<>();
    result.put("WMC",measurer.measurerWMC(className));
    result.put("DIT",measurer.measurerDIT(className));
    result.put("CBO",measurer.measurerCBO(className));
    result.put("LCOM",measurer.measurerLCOM(className));
    result.put("NOC",measurer.measurerNOC(className));
    result.put("RFC",measurer.measurerRFC(className));

    return result;
  }

}
