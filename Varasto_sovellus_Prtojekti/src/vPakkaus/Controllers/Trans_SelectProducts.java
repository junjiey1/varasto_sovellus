package vPakkaus.Controllers;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import vPakkaus.DAO_Objekti;
import vPakkaus.Hyllypaikka;
import vPakkaus.Product;
import vPakkaus.Tuotejoukko;

public class Trans_SelectProducts implements Nakyma_IF{

  @FXML
  private TableView<DAO_Objekti> tuoteTaulukko;
  @FXML
  private TableView<DAO_Objekti> lahetysTuotteet;
  @FXML
  private Button hae, addButton;
  @FXML
  private TextField tuoteNimi;
  @FXML
  private Tab selectProduct;
  @FXML
  private Tab confirm;
  @FXML
  private Tab page_1;
  @FXML
  private TabPane trans_tabPane;
  @FXML
  private TextField maara;

  private Taulukko_IF taulukko;
  private MainController_IF mc;
  private TaulukkoFactory tehdas;
  private NayttojenVaihtaja_IF vaihtaja;
  private Tab activeTab;

  public Trans_SelectProducts(){
    tehdas = new TaulukkoFactory();
  }


  public void reset() {
    int length = tuoteTaulukko.getItems().size(); // Hae taulun rivien määrä
    if (length > 0) {// Jos on rivejä
      for (; 0 < length;) {// Poistetaan yksi kerrallaan
        System.out.println("Deleting");
        tuoteTaulukko.getItems().remove(0);
        length--;
      }
    }
    tuoteTaulukko.refresh(); // Varmuuden vuoksi päivitetään TableView
  }

  private void täytäTaulukko() {
    tuoteTaulukko.getItems().addAll(taulukko.getTaulukko().getItems());
    tuoteTaulukko.refresh();
  }

  private boolean luoUusiTaulukko(ArrayList<DAO_Objekti> p){
    if(p == null || p.size()<=0)
      return false;
    tuoteTaulukko.getColumns().clear();
    taulukko = tehdas.annaTaulukko(p.get(0), p);
    tuoteTaulukko.getColumns().addAll(taulukko.getTaulukko().getColumns());
    return true;
  }

  public void haeTuotteRyhmia(){
    lahetysTuotteet.getColumns().addAll(tehdas.buildHelperTable(null).getColumns());
    ArrayList<Tuotejoukko> p = new ArrayList<Tuotejoukko>();
    p.add(new Tuotejoukko(new Product("lol", 1.2,1.2,1.2,3.3,1f), new Hyllypaikka("A-4", 9.9, 9.9, 9.9, 10, 2000), 10));
    p.add(new Tuotejoukko(new Product("lol", 1.2,1.2,1.2,3.3,1f), new Hyllypaikka("A-3", 9.9, 9.9, 9.9, 10, 2000), 10));
    Object o = (Object)p;
    if(luoUusiTaulukko((ArrayList<DAO_Objekti>)o)){
      täytäTaulukko();
    }
  }

  public void lisaaTuotteitaLahetykseen(){
    Tuotejoukko dao = (Tuotejoukko)tuoteTaulukko.getSelectionModel().getSelectedItem();
    if(dao==null || maara.getText().equals("")){
      System.out.println("ei määritelty");
      return;
    }
    int arvo;
    try{
      arvo = Integer.parseInt(maara.getText());
    }catch(NumberFormatException e){
      //Ei ole int
      System.out.println("ei int");
      return;
    }
    if(arvo>dao.getMaara()){ //Haluttu määrä on suurempi kuin hyllyssä olevan tuotteen määrä
      System.out.println("liian suuri arvo");
      return;
    }
    Tuotejoukko t = new Tuotejoukko(dao.getProduct(), dao.getHylly(), dao.getMaara()-arvo);

    //dao.setMaara(dao.getMaara()-arvo);
    DAO_Objekti dao1 = (DAO_Objekti)t;
    tuoteTaulukko.refresh();
    if(lahetysTuotteet.getItems().contains(dao1)){
      int i = lahetysTuotteet.getItems().indexOf(dao1);
      Tuotejoukko dao2 = (Tuotejoukko)lahetysTuotteet.getItems().get(i);
      dao2.setMaara(dao2.getMaara()+arvo);
      dao1 = (DAO_Objekti)dao2;
      lahetysTuotteet.getItems().add(i, dao1);
      //lahetysTuotteet.getItems().get(lahetysTuotteet.getItems().indexOf(dao));
    }
    else{
      lahetysTuotteet.getItems().add(dao1);
    }
    lahetysTuotteet.refresh();
  }

  @Override
  public void setMainController(MainController_IF m) {
    mc=m;
  }

  @Override
  public void paivita(Object data) {

  }

  @Override
  public void resetoi() {

  }

  @Override
  public void virheIlmoitus(Object viesti) {

  }

  @Override
  public void esiValmistelut() {

  }

  public void back(){
    page_1.setDisable(false);

    activeTab = page_1;
    activeTab.setContent(vaihtaja.getAnchorPane("Transmission"));
    trans_tabPane.getSelectionModel().select(0);
    selectProduct.setDisable(true);
  }
  public void next(){

  }


  @Override
  public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {
    vaihtaja.rekisteröiNakymaKontrolleri(this, "Trans_SelectProduct");
  }

}
