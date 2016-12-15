package vPakkaus.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.sql.Date;
import java.util.Map;
import java.util.TreeMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Kuvaaja näkymän kontrolleri. Vastaa kuvaajien piirtämisestä ja kuvaajan piirtämiseen vaadittavan datan hakemisesta.
 */
public class KuvaajaController implements Nakyma_IF {

  @FXML
  private LineChart<String, Integer> linechart;
  @FXML
  private CategoryAxis xAxis;
  @FXML
  private NumberAxis yAxis;
  @FXML
  private MenuItem lineChart;
  @FXML
  private MenuItem pie;
  @FXML
  private PieChart pieChart;
  @FXML
  private DatePicker startDate;
  @FXML
  private DatePicker endDate;
  @FXML
  private TextField textField;


  private MainController_IF mc;
  private NayttojenVaihtaja_IF vaihtaja;

  @Override
  public void setMainController(MainController_IF m) {
    mc = m;
  }

  @Override
  public void paivita(Object data) {
    // TODO Auto-generated method stub
  }

  @Override
  public void resetoi() {
    // TODO Auto-generated method stub
  }

  @Override
  public void virheIlmoitus(Object viesti) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error");
    alert.setContentText(viesti.toString());
    alert.showAndWait();
  }

  @Override
  public void esiValmistelut() {
  }

  public void show_line(ActionEvent event){

  lineChart.setOnAction(new EventHandler<ActionEvent>(){

    @Override
    public void handle(ActionEvent arg0) {
      // TODO Auto-generated method stub
      pieChart.setVisible(false);
      linechart.setVisible(true);
    }

   });
  }
  public void load_line() {
      pieChart.setVisible(false);
      linechart.setVisible(true);
      xAxis.setLabel("Month");
      linechart.toFront();
      linechart.getData().clear();
      XYChart.Series<String, Integer> series = new XYChart.Series<String, Integer>();
      if(startDate.getValue()==null || endDate.getValue()==null || textField.getText().equals("")){
        virheIlmoitus("Vaadittavia kenttiä ei olla täytetty");
        return;
      }
      series.setName("Menneet tuotteet");
      TreeMap<Date, Integer> res = mc.haeTietoja(Date.valueOf(startDate.getValue()), Date.valueOf(endDate.getValue()), textField.getText());
      if(res==null)
        return;
      for(Map.Entry<Date,Integer> entry : res.entrySet()) {
        Date key = entry.getKey();
        Integer value = entry.getValue();
        System.out.println(key.toString() + " => " + value);
        series.getData().add(new XYChart.Data<String, Integer>(key.toString(), value));
      }
      linechart.getData().add(series);
  }

  public void show_pie(ActionEvent event){
    pie.setOnAction(new EventHandler<ActionEvent>(){

      @Override
      public void handle(ActionEvent arg0) {
        linechart.setVisible(false);
        pieChart.setVisible(true);
      }

     });
  }
  public void load_pie(){
       linechart.setVisible(false);
       pieChart.setVisible(true);

    ObservableList<Data> list = FXCollections.observableArrayList(
        new PieChart.Data("TestiTuote1", 30),
        new PieChart.Data("TestiTuote2", 14),
        new PieChart.Data("TestiTuote3", 6),
        new PieChart.Data("TestiTuote4", 60)
        );
    pieChart.setData(list);
  }

  public void back() {// Button Callback funktio
    vaihtaja.asetaUudeksiNaytoksi("ManagementMainMenu", "ManagementMainMenu", null);
  }

  @Override
  public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {
    this.vaihtaja = vaihtaja;
    vaihtaja.rekisteröiNakymaKontrolleri(this, "Graphs");
  }

}
