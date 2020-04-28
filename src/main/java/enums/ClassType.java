package enums;

/**
 * @Description
 * @Author WM
 * @Date 2020/4/26 16:18
 */
public enum ClassType {
  NORMAL("normal"),ABSTRACT("abstract"),INTERFACE("interface"),ENUM("enum");

  String name;

  ClassType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
