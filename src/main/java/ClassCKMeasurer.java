import components.MyClass;
import components.MyMethod;
import java.util.Map;

/**
 * @Description
 * @Author WM
 * @Date 2020/4/27 22:36
 */
public class ClassCKMeasurer {
  private static Map<String, MyClass> classMap;

  private boolean isMeasurable(String className){
    if (!classMap.containsKey(className)){
      return false;
    }
    else {
      MyClass myClass=classMap.get(className);
      return myClass.getName() != null && !myClass.getName().isEmpty();
    }
  }

  public static void setClassMap(Map<String, MyClass> classMap) {
    ClassCKMeasurer.classMap = classMap;
  }

  //类的加权方法数
  public Integer measurerWMC(String className){
    if (!isMeasurable(className)){
      return null;
    }
    int sum=0;
    MyClass myClass=classMap.get(className);
    for(MyMethod method:myClass.getMethods()){
      sum+=method.getComplexity();
    }

    return sum;
  }

  //类的响应数量
  public Integer measurerRFC(String className){
    if (!isMeasurable(className)){
      return null;
    }
    int sum=0;
    MyClass myClass=classMap.get(className);
    sum+=myClass.getMethods().size();
    for(MyMethod method:myClass.getMethods()){
      sum+=method.getTransferMethods().size();// FIXME: 2020/4/28 需要去重
    }
    return sum;
  }

  //继承树深度
  public Integer measurerDIT(String className){
    if (!isMeasurable(className)){
      return null;
    }
    int sum=0;
    MyClass myClass=classMap.get(className),p=myClass.getExtend();
    while(p!=null){
      sum++;
      p=p.getExtend();
    }
    return sum;
  }

  //子类数量
  public Integer measurerNOC(String className){
    if (!isMeasurable(className)){
      return null;
    }
    return classMap.get(className).getSubClass().size();
  }

  //对象间耦合度
  public Integer measurerCBO(String className){
    if (!isMeasurable(className)){
      return null;
    }
    int sum=0;
    MyClass myClass=classMap.get(className);
    sum+=myClass.getAssociations().size();
    sum+=myClass.getDependencies().size();
    sum+=myClass.getRealizations().size();
    return sum;
  }

  //类缺乏内聚性
  public Integer measurerLCOM(String className){
    if (!isMeasurable(className)){
      return null;
    }
    int cohesionMethod=0,totalMethod=0;
    MyClass myClass=classMap.get(className);
    totalMethod=myClass.getMethods().size();
    for(MyMethod method:myClass.getMethods()){
      if(method.isCohesion()){
        cohesionMethod++;
      }
    }
    return comb(totalMethod,2)-2*comb(cohesionMethod,2);
  }

  private static int comb(int m,int n){
    if(n==0)    return 1;
    if (n==1)   return m;
    if (n>m/2)  return comb(m,m-n);
    if (n>1)    return comb(m-1,n-1)+comb(m-1,n);
    return -1; //通过编译需要，数字无实际意义
  }

}
