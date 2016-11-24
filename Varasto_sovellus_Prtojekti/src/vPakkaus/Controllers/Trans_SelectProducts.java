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
  private ArrayList<Tuotejoukko> paivitettavatJoukot;

  public Trans_SelectProducts(){
    tehdas = new TaulukkoFactory();
    paivitettavatJoukot = new ArrayList<Tuotejoukko>();
  }

  public void initialize(){
    lahetysTuotteet.getColumns().addAll(tehdas.buildHelperTable(null).getColumns());
  }


  private void resetTables(TableView<DAO_Objekti> taulukko) { //
    int length = taulukko.getItems().size(); // Hae taulun rivien määrä
    if (length > 0) {// Jos on rivejä
      for (; 0 < length;) {// Poistetaan yksi kerrallaan
        System.out.println("Deleting");
        taulukko.getItems().remove(0);
        length--;
      }
    }
    taulukko.refresh(); // Varmuuden vuoksi päivitetään TableView
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
    resetTables(tuoteTaulukko);
    ArrayList<Tuotejoukko> p = new ArrayList<Tuotejoukko>();
    p.add(new Tuotejoukko(new Product("lol", 1.2,1.2,1.2,3.3,1f), new Hyllypaikka("A-4", 9.9, 9.9, 9.9, 10, 2000), 10));
    p.add(new Tuotejoukko(new Product("lol", 1.2,1.2,1.2,3.3,1f), new Hyllypaikka("A-3", 9.9, 9.9, 9.9, 10, 2000), 10));
    p.add(new Tuotejoukko(new Product("liha", 1.2,1.2,1.2,3.3,1f), new Hyllypaikka("A-3", 9.9, 9.9, 9.9, 10, 2000), 10));
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
    if(arvo<1){ //Haluttu tuotteiden lisäys määrä <=0. Ei voida hyväksyä

      return;
    }
    dao.setMaara(dao.getMaara()-arvo);
    tuoteTaulukko.refresh();
    int i=0;
    boolean found = false;
    for(DAO_Objekti item : lahetysTuotteet.getItems()){//Yritetään löytää lähetystaulukosta tuote jolla sama nimi
      Tuotejoukko t = (Tuotejoukko)item;
      if(t.getTuotteenNimi().equals(dao.getTuotteenNimi())){ //tuotteilla sama nimi
        i = lahetysTuotteet.getItems().indexOf(t);//lähetystuote taulukon indeksi talteen
        found = true;//merkataan löydetyksi
      }
    }
    if(found){
      Tuotejoukko dao2 = (Tuotejoukko)lahetysTuotteet.getItems().get(i);//Haetaan saadulla indeksillä
      dao2.setMaara(dao2.getMaara() + arvo); //asetetaan uusi määrä
    }
    else{//Tuotetta ei löydy lähetys taulukosta
      Tuotejoukko newLahetysProduct = new Tuotejoukko(dao.getProduct(), dao.getHylly(), arvo); //Uusi tuotejoukko olio
      lahetysTuotteet.getItems().add(newLahetysProduct);//lisätään tauluun
    }
    lahetysTuotteet.refresh();//päivitetään
    addToPaivitettaviin(dao);
  }

  private void addToPaivitettaviin(Tuotejoukko dao){
    if(!paivitettavatJoukot.contains(dao))
      paivitettavatJoukot.add(dao);
    for(Tuotejoukko ttt : paivitettavatJoukot){
      System.out.println(ttt.getTuotteenNimi() + " " + ttt.getHyllynNimi() + ttt.getMaara());
    }
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
    paivitettavatJoukot = new ArrayList<Tuotejoukko>(); //Luodaan uusi päivitettavat lista
    resetTables(tuoteTaulukko);
    resetTables(lahetysTuotteet);
    maara.setText("");
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
