package service;

import components.MyClass;
import components.MyInPara;
import components.MyMethod;
import components.MyParameter;
import enums.ClassType;
import enums.Domain;
import enums.MethodType;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @Description
 * @Author WM
 * @Date 2020/4/26 11:52
 */
public class XmlScanner {

  private static String midXMLPATH = "src/main/resources/model/midXML.xml";
  private static File midXML;
  private static Map<String, MyClass> classTable=new HashMap<>();

  public static void setMidXMLPATH(String xmlpath){
    midXMLPATH=xmlpath;
  }

  public static Map<String, MyClass> returnClassMap(){
    return classTable;
  }

  public static void scanXML() throws Exception {
    midXML = new File(midXMLPATH);
    if (!midXML.exists()) {
      System.out.println("can not find XML");
      throw new Exception("can not find XML");
    }
    Document doc = Jsoup.parse(midXML, "utf-8");
    Elements classs = doc.getElementsByTag("class");
    for (Element c : classs) {
      MyClass myClass=getClass(c);

      fillClassMateInfo(c, myClass);

      fillParameter(c,myClass);

      fillMethod(c,myClass);

      fillRelationship(c,myClass);
    }
  }

  private static void fillRelationship(Element element, MyClass clazz) {
    Elements dependencies=element.select("dependency");
    clazz.getDependencies().clear();
    for(Element dependency:dependencies){
      String name = dependency.attr("name");
      if(!classTable.containsKey(name)){
        classTable.put(name,new MyClass());
      }
      clazz.getDependencies().add(classTable.get(name));
    }

    Elements associations=element.select("association");
    clazz.getAssociations().clear();
    for(Element association:associations){
      String name = association.attr("name");
      if(!classTable.containsKey(name)){
        classTable.put(name,new MyClass());
      }
      clazz.getAssociations().add(classTable.get(name));
    }

    Elements realizations=element.select("realization");
    clazz.getRealizations().clear();
    for(Element realization:realizations){
      String name = realization.attr("name");
      if(!classTable.containsKey(name)){
        classTable.put(name,new MyClass());
      }
      clazz.getAssociations().add(classTable.get(name));
    }

  }

  private static void fillMethod(Element element, MyClass clazz) {
    Elements methods = element.select("method");
    clazz.getMethods().clear();
    for (Element m : methods) {
      MyMethod myMethod = new MyMethod();
      myMethod.setName(m.attr("name"));
      myMethod.setType(MethodType.valueOf(m.attr("type").toUpperCase()));
      if(myMethod.getType()!=MethodType.NORMAL) {
        autoFillDefault(myMethod,myMethod.getType(),clazz.getName());
      }
      else {
        myMethod.setReturns(m.attr("return"));
        myMethod.setComplexity(Integer.parseInt(m.attr("complexity")));
        if(m.attr("domain").isEmpty()){
          myMethod.setDomain(Domain.DEFAULT);
        }
        else {
          myMethod.setDomain(Domain.valueOf(m.attr("domain").toUpperCase()));
        }

        if (m.attr("override").isEmpty()){
          myMethod.setOverrided(false);
        }
        else {
          myMethod.setOverrided(Boolean.getBoolean(m.attr("override")));
        }

        if (m.attr("isCohesion").isEmpty()){
          myMethod.setOverrided(false);
        }
        else {
          myMethod.setOverrided(Boolean.getBoolean(m.attr("isCohesion")));
        }
      }

      fillMethodPara(m,myMethod);

      fillMethodTransferMethods(m,myMethod);

      // FIXME: 2020/4/27 添加其他method属性时注意
      clazz.getMethods().add(myMethod);
    }
  }

  private static void fillMethodPara(Element element, MyMethod method) {
    Elements paras = element.select("inPara");
    method.getInParas().clear();
    for (Element p:paras){
      MyInPara inPara=new MyInPara();
      inPara.setName(p.attr("name"));
      inPara.setType(p.attr("type"));
      method.getInParas().add(inPara);
    }
  }

  private static void fillMethodTransferMethods(Element element, MyMethod method) {
    Elements tms=element.select("transferMethod");
    method.getTransferMethods().clear();
    for(Element tm:tms){
      MyMethod tempM=new MyMethod();
      tempM.setName(tm.attr("name"));
      method.getTransferMethods().add(tempM);
    }
  }


  private static void autoFillDefault(MyMethod method, MethodType type, String className) {
    switch (type){
      case CREATE:
        method.setReturns(className);
        method.setDomain(Domain.DEFAULT);
        method.setOverrided(false);
        method.setComplexity(1);
        method.setCohesion(false);
        break;
      case GETTER:
        method.setReturns(method.getName().substring(3));
        method.setDomain(Domain.PUBLIC);
        method.setOverrided(false);
        method.setComplexity(1);
        method.setCohesion(false);
        break;
      case SETTER:
        method.setReturns("void");
        method.setDomain(Domain.PUBLIC);
        method.setOverrided(false);
        method.setComplexity(1);
        method.setCohesion(false);
      default:

    }
  }

  private static MyClass getClass(Element clazz){
    String className = clazz.attr("name");
    MyClass myClass;
    if (classTable.containsKey(className)) {
      myClass = classTable.get(className);
    }
    else {
      myClass = new MyClass();
      classTable.put(className, myClass);
    }
    return myClass;
  }

  private static void fillClassMateInfo(Element element,MyClass clazz){
    clazz.setName(element.attr("name"));
    clazz.setType(ClassType.valueOf(element.attr("type").toUpperCase()));
    if(element.attr("domain").isEmpty()){
      clazz.setDomain(Domain.DEFAULT);
    }
    else {
      clazz.setDomain(Domain.valueOf(element.attr("domain").toUpperCase()));
    }
    String extend=element.attr("extends");
    if (extend.isEmpty()){
      clazz.setExtend(null);
    }
    else if(!classTable.containsKey(extend)){
      classTable.put(extend,new MyClass());
      clazz.setExtend(classTable.get(element.attr("extends")));
      clazz.getExtend().getSubClass().add(clazz);
    }
    else {
      clazz.setExtend(classTable.get(element.attr("extends")));
      clazz.getExtend().getSubClass().add(clazz);
    }

    String[] implementes=element.attr("implements").split(",");
    clazz.getImplement().clear();
    for(String s:implementes){
      if (s.isEmpty()) {
      }
      else if(classTable.containsKey(s)){
        clazz.getImplement().add(classTable.get(s));
      }
      else {
        MyClass impl=new MyClass();
        clazz.getImplement().add(impl);
        classTable.put(s,impl);
      }
    }
  }

  private static void fillParameter(Element element, MyClass clazz) {
    Elements parameters=element.select("parameter");
    clazz.getParameters().clear();
    for(Element para:parameters){
      MyParameter parameter=new MyParameter();
      parameter.setName(para.attr("name"));
      parameter.setType(para.attr("type"));
      if (para.attr("domain").isEmpty()){
        parameter.setDomain(Domain.DEFAULT);
      }
      else {
        parameter.setDomain(Domain.valueOf(para.attr("domain").toUpperCase()));
      }
      clazz.getParameters().add(parameter);
    }
  }


}


