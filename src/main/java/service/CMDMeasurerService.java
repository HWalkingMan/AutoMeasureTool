package service;

import components.MyClass;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @Description
 * @Author WM
 * @Date 2020/4/30 11:21
 */
public class CMDMeasurerService {

  private Map<String, MyClass> classTable;

  List<String> measurableClassnames=new ArrayList<>();

  public void setMidXMLPATH(String xmlpath){
    XmlScanner.setMidXMLPATH(xmlpath);
  }

  ClassCKMeasurer measurer=new ClassCKMeasurer();

  public void loadXML() throws Exception {

      XmlScanner.scanXML();

      classTable=XmlScanner.returnClassMap();

      measurer.setClassMap(classTable);


  }
  public List<String> getMeasurableClassName(){
    for(Entry<String, MyClass> entry:classTable.entrySet()){
      if(((MyClass)entry.getValue()).getName()!=null && !((MyClass)entry.getValue()).getName().isEmpty()){
        measurableClassnames.add(entry.getKey());
      }
    }
    return measurableClassnames;
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
