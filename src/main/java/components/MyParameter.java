package components;

import enums.Domain;

/**
 * @Description
 * @Author WM
 * @Date 2020/4/26 16:13
 */
public class MyParameter {
  private String name;
  private String type;
  private Domain domain=Domain.DEFAULT;

  public MyParameter() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Domain getDomain() {
    return domain;
  }

  public void setDomain(Domain domain) {
    this.domain = domain;
  }
}
