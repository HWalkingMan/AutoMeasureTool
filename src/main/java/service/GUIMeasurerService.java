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

  ClassCKMeasurer ckMeasurer =new ClassCKMeasurer();

  ClassLKMeasurer lkMeasurer =new ClassLKMeasurer();

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

        ckMeasurer.setClassMap(classTable);
        lkMeasurer.setClassMap(classTable);

        controller.setSl_classesContent(measurableClassnames);

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

  public Map<String,Integer> measureClassCKByName(String className){
    Map<String,Integer> result=new HashMap<>();
    result.put("WMC", ckMeasurer.measurerWMC(className));
    result.put("DIT", ckMeasurer.measurerDIT(className));
    result.put("CBO", ckMeasurer.measurerCBO(className));
    result.put("LCOM", ckMeasurer.measurerLCOM(className));
    result.put("NOC", ckMeasurer.measurerNOC(className));
    result.put("RFC", ckMeasurer.measurerRFC(className));

    return result;
  }

  public Map<String,Integer> measureClassLKByName(String className){
    Map<String,Integer> result=new HashMap<>();
    result.put("CS", lkMeasurer.measurerCS(className));
    result.put("NOA", lkMeasurer.measurerNOA(className));
    result.put("NOO", lkMeasurer.measurerNOO(className));
    result.put("SI", lkMeasurer.measurerSI(className));

    return result;
  }



}
