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
import javafx.scene.control.MenuItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class KuvaajaController implements Nakyma_IF {

  @FXML
  private LineChart<String, Number> linechart;
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
    // TODO Auto-generated method stub
  }

  @Override
  public void esiValmistelut() {
  }

  public void load_line() {

   lineChart.setOnAction(new EventHandler<ActionEvent>(){

    @Override
    public void handle(ActionEvent arg0) {
      // TODO Auto-generated method stub
      pieChart.setVisible(false);
      linechart.setVisible(true);
      xAxis.setLabel("Month");

      linechart.toFront();
      linechart.getData().clear();
      // TESTI LISÄYTS KUVAAJAAN
      XYChart.Series series = new XYChart.Series();
      // series.getData().add(new XYChart.Data<String,Number>("Tammikuu",1));
      // series.getData().add(new XYChart.Data<String,Number>("Helmikuu",2));
      // series.getData().add(new XYChart.Data<String,Number>("Maaliskuu",3));

      series.setName("Gross Profit");


      series.getData().add(new XYChart.Data("Jan", 23));
      series.getData().add(new XYChart.Data("Feb", 14));
      series.getData().add(new XYChart.Data("Mar", 15));
      series.getData().add(new XYChart.Data("Apr", 24));
      series.getData().add(new XYChart.Data("May", 34));
      series.getData().add(new XYChart.Data("Jun", 36));
      series.getData().add(new XYChart.Data("Jul", 22));
      series.getData().add(new XYChart.Data("Aug", 45));
      series.getData().add(new XYChart.Data("Sep", 43));
      series.getData().add(new XYChart.Data("Oct", 17));
      series.getData().add(new XYChart.Data("Nov", 29));
      series.getData().add(new XYChart.Data("Dec", 25));

      linechart.getData().add(series);
    }

   });
  }

  public void load_pie(ActionEvent event){


    pie.setOnAction(new EventHandler<ActionEvent>(){

     @Override
     public void handle(ActionEvent arg0) {
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

    });

  }

  public void back() {// Button Callback funktio
    vaihtaja.asetaUudeksiNaytoksi("ManagementMainMenu", "ManagementMainMenu", null);
  }

  @Override
  public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {
    this.vaihtaja = vaihtaja;
    vaihtaja.rekisteröiNakymaKontrolleri(this, "Kuvaaja_Controller");
  }

}
