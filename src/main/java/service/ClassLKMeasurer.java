package service;

import components.MyClass;
import components.MyMethod;
import enums.MethodType;
import java.util.Map;

/**
 * @Description
 * @Author WM
 * @Date 2020/5/6 12:21
 */
public class ClassLKMeasurer {
  private Map<String, MyClass> classMap;
  private boolean isMeasurable(String className){
    if (!classMap.containsKey(className)){
      return false;
    }
    else {
      MyClass myClass=classMap.get(className);
      return myClass.getName() != null && !myClass.getName().isEmpty();
    }
  }

  public void setClassMap(Map<String, MyClass> classMap) {
    this.classMap = classMap;
  }

  //类规模度量
  public Integer measurerCS(String className){
    if (!isMeasurable(className)){
      return null;
    }
    MyClass myClass=classMap.get(className);
    return myClass.getParameters().size()+myClass.getMethods().size();
  }

  //方法重写数量
  public Integer measurerNOO(String className){
    if (!isMeasurable(className)){
      return null;
    }
    MyClass myClass=classMap.get(className);
    int sum=0;
    for (MyMethod method:myClass.getMethods()){
      if(method.isOverrided()){
        sum++;
      }
    }
    return sum;
  }

  //增加方法数量
  public Integer measurerNOA(String className){
    if (!isMeasurable(className)){
      return null;
    }
    MyClass myClass=classMap.get(className);
    int sum=0;
    for (MyMethod method:myClass.getMethods()){
      if(!method.isOverrided()&&method.getType()== MethodType.NORMAL){
        sum++;
      }
    }
    return sum;
  }

  //特征化指数
  public Integer measurerSI(String className){
    if (!isMeasurable(className)){
      return null;
    }
    MyClass myClass=classMap.get(className);
    int sum_noo=0,sum_dit=0;
    for (MyMethod method:myClass.getMethods()){
      if(!method.isOverrided()&&method.getType()== MethodType.NORMAL){
        sum_noo++;
      }
    }

    MyClass p=myClass.getExtend();
    while(p!=null){
      sum_dit++;
      p=p.getExtend();
    }

    // FIXME: 2020/5/6


    return sum_noo;
  }

}
