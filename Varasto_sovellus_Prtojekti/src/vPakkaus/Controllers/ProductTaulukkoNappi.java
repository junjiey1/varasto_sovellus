package vPakkaus.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableSelectionModel;
import vPakkaus.DAO_Objekti;
import vPakkaus.Product;

public class ProductTaulukkoNappi extends TableCell<DAO_Objekti, Object>{
	private Button cellButton;
	private Product[] tuotteetpaivitettavana;

    public ProductTaulukkoNappi(String name, Product[] list){
          cellButton = new Button();
          cellButton.setText(name);
          tuotteetpaivitettavana = list;
          cellButton.setOnAction(new EventHandler<ActionEvent>() {
              @Override public void handle(ActionEvent e) {
            	  System.out.println("Indeksi " + getIndex());
                  Product p = (Product)getTableView().getItems().get(getIndex());
                  p.setMax_temperature(null);
                  p.setMin_temperature(null);
                  p.setTemp(false);
                  System.out.println("Lämpötila nollattu");
                  tuotteetpaivitettavana[getIndex()] = p;
                  getTableView().getSelectionModel().selectLeftCell();
                  //getTableRow().se
                  //System.out.println(getTableView().getc
                  getTableRow().setStyle("-fx-background-color:lightcoral");
                  setGraphic(null);
                  //updateTableRow(getTableRow());
                  //getTableRow().setStyle("-fx-background-color:lightcoral");
              }
          });
    }

    //Näyttää lämpötila nollaus napin jos on asetettu lämpötilat
    @Override
	public void updateItem(Object item, boolean empty) {
    	super.updateItem(item, empty);
    	Product p = (Product)item;
    	if(p == null)
    		return;
        if(p.getTemp()){
            //cellButton.setText("Nollaa lämpötila");
            setGraphic(cellButton);
        } else {
            setGraphic(null);
        }
    }
}
