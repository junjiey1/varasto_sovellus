package vPakkaus.Controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import vPakkaus.DAO_Objekti;
import vPakkaus.Tuotejoukko;

public class Trans_SelectProducts implements LahetysInformationProvider_IF{

  @FXML
  private TableView<DAO_Objekti> tuoteTaulukko, lahetysTuotteet;
  @FXML
  private Button hae, addButton, resetButton;
  @FXML
  private TextField tuoteNimi, maara;
  @FXML
  private CheckBox addFromTextFile;
  @FXML
  private ListView<String> TextFilesTable;
  @FXML
  private Label pNameLabel;

  private File file;
  private Scanner input;
  private Taulukko_IF taulukko;
  private MainController_IF mc;
  private TaulukkoFactory_IF tehdas;
  private NayttojenVaihtaja_IF vaihtaja;
  private ArrayList<Tuotejoukko> paivitettavatJoukot;
  private LahetysRakentaja_IF rakentaja;
  private TreeMap<String, String> tmap;

  private ObservableList<String> productTextFiles = FXCollections.observableArrayList();

  boolean noErrorsEncountered;
  int pQuantity, fileRow;
  String path, fileName, pName, pShelf;
  String[] oneRowOfData;

  public Trans_SelectProducts(){
    tmap = new TreeMap<String, String>();
    mc = null;
    vaihtaja = null;
    tehdas = TaulukkoFactory.getInstance();
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
    mc.haeTuotejoukot(tuoteNimi.getText());
  }

  public void lisaaTuotteitaLahetykseen() throws FileNotFoundException{
    if (addFromTextFile.isSelected()){
      parseTextFiles();
    }else{
      Tuotejoukko dao = (Tuotejoukko)tuoteTaulukko.getSelectionModel().getSelectedItem();
      if(dao==null || maara.getText().equals("")){
        virheIlmoitus("Tuotetta ei ole valittu tai määrä ei ole annettu");
        return;
      }
      int arvo;
      try{
        arvo = Integer.parseInt(maara.getText());
      }catch(NumberFormatException e){
        //Ei ole int
        virheIlmoitus("Anna kokonaisluku siirrettävien tuotteiden määräksi");
        return;
      }
      if(arvo>dao.getMaara()){ //Haluttu määrä on suurempi kuin hyllyssä olevan tuotteen määrä
        virheIlmoitus("Annoit siirrettävien määräksi " + arvo + ", mutta tämä ylittää hyllypaikassa olevien tuotteiden arvon " + dao.getMaara());
        return;
      }
      if(arvo<1){ //Haluttu tuotteiden lisäys määrä <=0. Ei voida hyväksyä
        virheIlmoitus("Antamasi määrä on <=0.");
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
    resetTables(tuoteTaulukko);
    ArrayList<DAO_Objekti> list = new ArrayList<DAO_Objekti>();
    list.addAll((ArrayList<DAO_Objekti>)data);
    if(paivitettavatJoukot.size()>0){
      for(int i = 0; i<paivitettavatJoukot.size();i++){
        for(int j = 0; j<list.size();j++){
          Tuotejoukko ttj = (Tuotejoukko)list.get(j);
          if(paivitettavatJoukot.get(i).getTuotteenNimi().equals(ttj.getTuotteenNimi())){
            if(paivitettavatJoukot.get(i).getHyllynNimi().equals(ttj.getHyllynNimi()))
              list.set(j, paivitettavatJoukot.get(i));
          }
        }
      }
    }
    if(luoUusiTaulukko(list)){
      täytäTaulukko();
    }
  }

  @Override
  public void resetoi() {
    paivitettavatJoukot = new ArrayList<Tuotejoukko>(); //Luodaan uusi päivitettavat lista
    resetTables(tuoteTaulukko);
    resetTables(lahetysTuotteet);
    maara.setText("");
    tuoteNimi.setText("");
    lahetysTuotteet.getItems().clear();
    tuoteTaulukko.getItems().clear();
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
    if(rakentaja.getTuotteet()!=null && !rakentaja.getTuotteet().isEmpty()){
      lahetysTuotteet.getItems().clear();
      lahetysTuotteet.getItems().addAll(rakentaja.getTuotteet());
    }
  }

  public void back_to(){
    vaihtaja.asetaUudeksiNaytoksi("Transmission", null, null);
  }

  public void next_confirm(){
    rakentaja.setMuutetutTuoterivit(paivitettavatJoukot);
    rakentaja.setTuotteet(lahetysTuotteet.getItems());
    vaihtaja.asetaUudeksiNaytoksi("confirm_tab", null, lahetysTuotteet);
  }

  @Override
  public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {
    this.vaihtaja = vaihtaja;
    vaihtaja.rekisteröiNakymaKontrolleri(this, "Trans_SelectProduct");
  }

  @Override
  public void setLahetyksenRakentaja(LahetysRakentaja_IF rakentaja) {
    this.rakentaja = rakentaja;
  }

  public void parseTextFiles() throws FileNotFoundException{
    noErrorsEncountered = true;
    for (String s : productTextFiles) {
      file = new File(tmap.get(s));
      input = new Scanner(file);
      fileRow=1;
      while (input.hasNext()) {
        oneRowOfData = input.nextLine().split(",");
        if(!validoiListanMuuttujat(oneRowOfData))
          virheIlmoitus("Rivillä : " + fileRow + " Tuotteen " + pName + " muuttujissa havaittiin virhe!");
        else
          //noErrorsEncountered = mc.addProduct(rakennaTuotejoukko());
        fileRow++;
      }




    }




    tmap.clear();
  }

  private boolean validoiListanMuuttujat(String[] rowOfData){
    try{
      pName = oneRowOfData[0];
      pShelf = oneRowOfData[1];
      pQuantity = Integer.parseInt(oneRowOfData[2]);
      return true;
    }catch (NumberFormatException ex) {
      return false;
    }
  }





  //ON DRAG DETECTED
  @FXML
  void dropTextFiles(DragEvent event) {
    Dragboard db = event.getDragboard();

    int index = 0;
    if (db.hasFiles()) {
      path = db.getFiles().toString();

      // MULTIPLE FILES DRAGGED
      if (db.getFiles().size() > 1) {
        oneRowOfData = path.split(",");
        for (int i = 0; i < oneRowOfData.length; i++) {
          if (i == oneRowOfData.length - 1) {
            oneRowOfData[i] = oneRowOfData[i].substring(1, oneRowOfData[i].length() - 1);
          } else {
            oneRowOfData[i] = oneRowOfData[i].substring(1, oneRowOfData[i].length());
          }
          path = oneRowOfData[i];
          index = path.lastIndexOf("\\");
          fileName = path.substring(index + 1, path.length());
          tmap.put(new String(fileName), new String(path));
          productTextFiles.add(fileName);
          TextFilesTable.setItems(productTextFiles);
        }

        // ONLY 1 FILE DRAGGED
      } else {
        path = path.substring(1, path.length() - 1);
        index = path.lastIndexOf("\\");
        fileName = path.substring(index + 1, path.length());
        tmap.put(new String(fileName), new String(path));
        productTextFiles.add(fileName);
        TextFilesTable.setItems(productTextFiles);
      }
      db.clear();
    }
    event.consume();
    db = null;
  }



  //TOGGLE SWITCH
  public void addTextFiles(){
    if (addFromTextFile.isSelected()){
      TextFilesTable.toFront();
      pNameLabel.setVisible(false);
      tuoteNimi.setVisible(false);
      hae.toBack();
      resetButton.toBack();
      maara.setVisible(false);

    }else{

      TextFilesTable.toBack();
      pNameLabel.setVisible(true);
      tuoteNimi.setVisible(true);
      hae.toFront();
      resetButton.toFront();
      maara.setVisible(true);
    }
  }



  public void removeTextFile() {
    String Fname = null;
    Fname = productTextFiles.get(productTextFiles.indexOf(TextFilesTable.getSelectionModel().getSelectedItem()));
    productTextFiles.remove(productTextFiles.indexOf(TextFilesTable.getSelectionModel().getSelectedItem()));
    tmap.remove(Fname);
  }

  public void removeAllTextFiles() {
    productTextFiles.clear();
    tmap.clear();
  }


}
