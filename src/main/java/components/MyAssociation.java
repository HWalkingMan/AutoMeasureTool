package components;

import enums.AssociationType;

/**
 * @Description
 * @Author WM
 * @Date 2020/4/26 16:15
 */
public class MyAssociation  {
  private AssociationType type;

  public MyAssociation() {
    super();
  }

  public AssociationType getType() {
    return type;
  }

  public void setType(AssociationType type) {
    this.type = type;
  }
}
