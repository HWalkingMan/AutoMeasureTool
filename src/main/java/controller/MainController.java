package controller;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import service.GUIMeasurerService;

/**
 * @Description
 * @Author WM
 * @Date 2020/4/28 10:32
 */
public class MainController implements Initializable {

  public Button btn_openXML;
  public ComboBox sl_classes;
  public TextField te_WMC;
  public TextField te_RFC;
  public TextField te_DIT;
  public TextField te_NOC;
  public TextField te_CBO;
  public TextField te_LCOM;
  public AnchorPane rootLayout;
  public Label la_warnMsg;
  public Button btn_start;
  public Button btn_test;

  private GUIMeasurerService service;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    service=new GUIMeasurerService(this);
  }

  @FXML
  public void btn_openXML_click(ActionEvent event) {
    System.out.println("btn_openFile clicked");

    FileChooser chooser = new FileChooser(); // 创建一个文件对话框
    chooser.setTitle("打开文件"); // 设置文件对话框的标题
    chooser.setInitialDirectory(new File("I:\\")); // 设置文件对话框的初始目录

    // 给文件对话框添加多个文件类型的过滤器
    chooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("所有模型", "*.xml", "*.oom", "*.oob"),
        new FileChooser.ExtensionFilter("所有文件", "*.*"));
    // 显示文件打开对话框，且该对话框支持同时选择多个文件
    Window stage = rootLayout.getScene().getWindow();
    File file = chooser.showOpenDialog(stage); // 显示文件打开对话框
    if (file == null) {
      la_warnMsg.setText("未选择任何文件");
    }
    else {
      System.out.println("准备打开的文件路径是：" + file.getAbsolutePath());
    }

    service.setMidXMLPATH(file.getAbsolutePath());

    service.loadXML();

  }

  @FXML
  public void btn_test_click(ActionEvent event) {

  }

  @FXML
  public void btn_start_click(ActionEvent event) {
    //System.out.println(sl_classes.getValue());
    fillValue(service.measureClassByName((String)sl_classes.getValue()));
  }

  public void setWarnMsg(String msg){
    la_warnMsg.setText(msg);
  }


  public void setSl_classesContent(List<String> classes){
    sl_classes.setDisable(false);
    sl_classes.getItems().addAll(classes);

    sl_classes.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)-> {
      //fillValue(service.measureClassByName(classes.get(newv.intValue())));
      //System.out.println(classes.get(newv.intValue()));
      btn_start.setDisable(false);
    });
  }

  private void fillValue(Map<String,Integer> value){
    te_WMC.setText(value.get("WMC").toString());
    te_RFC.setText(value.get("RFC").toString());
    te_DIT.setText(value.get("DIT").toString());
    te_CBO.setText(value.get("CBO").toString());
    te_NOC.setText(value.get("NOC").toString());
    te_LCOM.setText(value.get("LCOM").toString());
  }


}
