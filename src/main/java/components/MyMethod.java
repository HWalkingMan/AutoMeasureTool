package components;

import enums.Domain;
import enums.MethodType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description
 * @Author WM
 * @Date 2020/4/26 16:12
 */
public class MyMethod {
  private String name;
  private MethodType type;
  private String returns;
  private Domain domain;
  private boolean overrided;
  private List<MyInPara> inParas=new ArrayList<MyInPara>();

  private int complexity;
  private boolean isCohesion;
  private Set<MyMethod> transferMethods=new HashSet<>();


  public MyMethod() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public MethodType getType() {
    return type;
  }

  public void setType(MethodType type) {
    this.type = type;
  }

  public String getReturns() {
    return returns;
  }

  public void setReturns(String returns) {
    this.returns = returns;
  }

  public Domain getDomain() {
    return domain;
  }

  public void setDomain(Domain domain) {
    this.domain = domain;
  }

  public boolean isOverrided() {
    return overrided;
  }

  public void setOverrided(boolean overrided) {
    this.overrided = overrided;
  }

  public List<MyInPara> getInParas() {
    return inParas;
  }

  public void setInParas(List<MyInPara> inParas) {
    this.inParas = inParas;
  }

  public int getComplexity() {
    return complexity;
  }

  public void setComplexity(int complexity) {
    this.complexity = complexity;
  }

  public boolean isCohesion() {
    return isCohesion;
  }

  public void setCohesion(boolean cohesion) {
    isCohesion = cohesion;
  }

  public Set<MyMethod> getTransferMethods() {
    return transferMethods;
  }
}
