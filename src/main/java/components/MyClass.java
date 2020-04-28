package components;

import enums.ClassType;
import enums.Domain;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description
 * @Author WM
 * @Date 2020/4/26 16:12
 */
public class MyClass {
  protected String name;
  protected ClassType type;
  protected MyClass extend;
  private Domain domain;
  protected Set<MyClass> implement=new HashSet<>();

  protected Set<MyMethod> methods=new HashSet<>();
  protected Set<MyParameter> parameters =new HashSet<>();
  protected Set<MyClass> associations=new HashSet<>();
  protected Set<MyClass> dependencies=new HashSet<>();
  protected Set<MyClass> realizations=new HashSet<>();

  protected Set<MyClass> subClass=new HashSet<>();


  public MyClass() {
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setType(ClassType type) {
    this.type = type;
  }

  public void setExtend(MyClass extend) {
    this.extend = extend;
  }

  public Domain getDomain() {
    return domain;
  }

  public void setDomain(Domain domain) {
    this.domain = domain;
  }

  public String getName() {
    return name;
  }

  public ClassType getType() {
    return type;
  }

  public MyClass getExtend() {
    return extend;
  }

  public Set<MyClass> getImplement() {
    return implement;
  }

  public Set<MyMethod> getMethods() {
    return methods;
  }

  public Set<MyParameter> getParameters() {
    return parameters;
  }

  public Set<MyClass> getAssociations() {
    return associations;
  }

  public Set<MyClass> getDependencies() {
    return dependencies;
  }

  public Set<MyClass> getRealizations() {
    return realizations;
  }

  public Set<MyClass> getSubClass() {
    return subClass;
  }
}
