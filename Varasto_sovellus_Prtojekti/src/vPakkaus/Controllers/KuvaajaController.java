package vPakkaus.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class KuvaajaController implements Nakyma_IF{

  @FXML
  private LineChart<String,Number> linechart;
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
    // TODO Auto-generated method stub

  }

  public void kuvaaja(ActionEvent event){
    linechart.getData().clear();
    //TESTI LISÄYTS KUVAAJAAN
    XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
    series.getData().add(new XYChart.Data<String,Number>("Tammikuu",1));
    series.getData().add(new XYChart.Data<String,Number>("Helmikuu",2));
    series.getData().add(new XYChart.Data<String,Number>("Maaliskuu",3));
    linechart.getData().add(series);

  }
  public void back(){//Button Callback funktio
    vaihtaja.asetaUudeksiNaytoksi("ManagementMainMenu", "ManagementMainMenu",null);

  }

  @Override
  public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {
    this.vaihtaja = vaihtaja;
    vaihtaja.rekisteröiNakymaKontrolleri(this, "Trans_SelectProduct");

  }

}
