package builder;

import java.util.Scanner;

/**
 * @Description
 * @Author WM
 * @Date 2020/5/9 14:43
 */
public class XMLBuilder {

  public static void main(String[] args) {
    Scanner input=new Scanner(System.in);
    System.out.println("welcome to use middle XMl builder!");
    System.out.println("please input class diagram path:");
    String s = input.nextLine();
    System.out.println("your input is ["+s+"]");
    System.out.println("please input sequence diagram path(quit by q)");
    String s1 = input.nextLine();
    String s2 = input.nextLine();
    input.nextLine();
    System.out.println("your input is ["+s1+"],["+s2+"]");
    System.out.println("\nbuilding ----------\n");
    System.out.println("build successfully! midXML's path is [src/main/resources/model/midXML.xml]");
  }

}
