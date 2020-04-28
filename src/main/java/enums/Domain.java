package enums;

/**
 * @Description
 * @Author WM
 * @Date 2020/4/26 17:19
 */
public enum Domain {
  PUBLIC("public"),PRIVATE("private"),PROTECTED("protected"),DEFAULT("default");
  String name;

  Domain(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }


}
