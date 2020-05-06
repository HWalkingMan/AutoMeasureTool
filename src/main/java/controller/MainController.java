package controller;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
  public BarChart bc_CK;
  public TextField te_CS;
  public TextField te_NOO;
  public TextField te_NOA;
  public TextField te_SI;
  public BarChart bc_LK;
  public CategoryAxis bc_ck_x;
  public NumberAxis bc_ck_y;
  public CategoryAxis bc_lk_x;
  public NumberAxis bc_lk_y;

  private GUIMeasurerService service;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    service=new GUIMeasurerService(this);
    bc_CK.setTitle("CK 度量结果");
    bc_ck_x.setCategories(FXCollections.<String>observableArrayList(Arrays.asList("WMC","RFC","DIT","NOC","CBO","LCOM")));

    bc_LK.setTitle("LK 度量结果");
    bc_lk_x.setCategories(FXCollections.<String>observableArrayList(Arrays.asList("CS","NOO","NOA","SI")));
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
    //bc_ck_x.setCategories(FXCollections.<String>observableArrayList(Arrays.asList("1","2","3","4")));

    XYChart.Series<String, Number> series1 = new XYChart.Series<>(),series2 = new XYChart.Series<>();;
    series1.setName("class1");
    series1.getData().add(new XYChart.Data<>("WMC", 1.0));
    series1.getData().add(new XYChart.Data<>("RFC", 3.0));
    series1.getData().add(new XYChart.Data<>("DIT", 5.0));
    series1.getData().add(new XYChart.Data<>("NOC", 3.0));
    series1.getData().add(new XYChart.Data<>("CBO", 5.0));
    series1.getData().add(new XYChart.Data<>("LCOM", 5.0));

    series2.setName("class2");
    series2.getData().add(new XYChart.Data<>("WMC", 2.0));
    series2.getData().add(new XYChart.Data<>("RFC", 1.0));
    series2.getData().add(new XYChart.Data<>("DIT", 2.0));
    series2.getData().add(new XYChart.Data<>("NOC", 7.0));
    series2.getData().add(new XYChart.Data<>("CBO", 2.0));
    series2.getData().add(new XYChart.Data<>("LCOM", 5.0));
    bc_CK.getData().addAll(series1,series2);
  }

  @FXML
  public void btn_start_click(ActionEvent event) {
    //fillValue(service.measureClassByName((String)sl_classes.getValue()));
  }

  public void setWarnMsg(String msg){
    la_warnMsg.setText(msg);
  }


  public void setSl_classesContent(List<String> classes){
    sl_classes.setDisable(false);
    sl_classes.getItems().addAll(classes);


    sl_classes.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)-> {
      fillCKValue(service.measureClassCKByName(classes.get(newv.intValue())));
      fillLKValue(service.measureClassLKByName(classes.get(newv.intValue())));
      btn_start.setDisable(false);

    });

    Map<String,Map<String,Integer>> results=new HashMap<>();
    for(String clazz:classes){
      results.put(clazz,service.measureClassCKByName(clazz));
    }
    printCKBarChart(results);

    results.clear();
    for(String clazz:classes){
      results.put(clazz,service.measureClassLKByName(clazz));
    }
    printLKBarChart(results);
  }

  private void printCKBarChart( Map<String,Map<String,Integer>> results){
    System.out.println(results);
    for (Entry<String,Map<String,Integer>> entry:results.entrySet()){
      XYChart.Series<String, Number> series = new XYChart.Series<>();
      series.setName(entry.getKey());
      series.getData().add(new XYChart.Data<>("WMC",0.1+entry.getValue().get("WMC")));
      series.getData().add(new XYChart.Data<>("RFC",0.1+entry.getValue().get("RFC")));
      series.getData().add(new XYChart.Data<>("DIT",0.1+entry.getValue().get("DIT")));
      series.getData().add(new XYChart.Data<>("NOC",0.1+entry.getValue().get("NOC")));
      series.getData().add(new XYChart.Data<>("CBO",0.1+entry.getValue().get("CBO")));
      series.getData().add(new XYChart.Data<>("LCOM",0.1+entry.getValue().get("LCOM")));
      bc_CK.getData().add(series);
    }
  }
  private void printLKBarChart( Map<String,Map<String,Integer>> results){
    for (Entry<String,Map<String,Integer>> entry:results.entrySet()){
      XYChart.Series<String, Number> series = new XYChart.Series<>();
      series.setName(entry.getKey());
      series.getData().add(new XYChart.Data<>("CS",0.1+entry.getValue().get("CS")));
      series.getData().add(new XYChart.Data<>("NOO",0.1+entry.getValue().get("NOO")));
      series.getData().add(new XYChart.Data<>("NOA",0.1+entry.getValue().get("NOA")));
      series.getData().add(new XYChart.Data<>("SI",0.1+entry.getValue().get("SI")));
      System.out.println(series);
      bc_LK.getData().add(series);
    }
  }

  private void fillCKValue(Map<String,Integer> value){
    te_WMC.setText(value.get("WMC").toString());
    te_RFC.setText(value.get("RFC").toString());
    te_DIT.setText(value.get("DIT").toString());
    te_CBO.setText(value.get("CBO").toString());
    te_NOC.setText(value.get("NOC").toString());
    te_LCOM.setText(value.get("LCOM").toString());
  }

  private void fillLKValue(Map<String,Integer> value){
    te_CS.setText(value.get("CS").toString());
    te_NOO.setText(value.get("NOO").toString());
    te_NOA.setText(value.get("NOA").toString());
    te_SI.setText(value.get("SI").toString());
  }


}
