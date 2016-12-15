package vPakkaus.Controllers;

/**
 *Erittäin yksinkertainen Ikkunan-kontrolleri. Halitsee ikkunaa jossa vain kaksi nappia
 */
public class shipmentController implements Nakyma_IF{

  private NayttojenVaihtaja_IF vaihtaja;

  @Override
  public void setMainController(MainController_IF m) {
    // TODO Auto-generated method stub

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

  @Override
  public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {
    this.vaihtaja = vaihtaja;
    vaihtaja.rekisteröiNakymaKontrolleri(this, "ShipmentTab");

  }


  public void shipmentCreation(){
    vaihtaja.asetaUudeksiNaytoksi("Test1", "NEW SHIPMENT", null);
  }

  public void shipmentModification(){
    vaihtaja.asetaUudeksiNaytoksi("ShipmentModification", "SHIPMENT Modification", null);
  }




}
