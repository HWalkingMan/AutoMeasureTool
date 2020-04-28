package enums;

/**
 * @Description
 * @Author WM
 * @Date 2020/4/26 17:17
 */
public enum MethodType {
  NORMAL("normal"),GETTER("getter"),SETTER("setter"),CREATE("create");

  String name;

  MethodType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

}
