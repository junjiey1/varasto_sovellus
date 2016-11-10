package vPakkaus.Controllers;

import java.util.ArrayList;

import javafx.scene.control.TableView;
import vPakkaus.DAO_Objekti;
import vPakkaus.Product;

public interface Taulukko_IF {
	public void addTableView(TableView table);
	public TableView<DAO_Objekti> getTaulukko();
	public void addInstance(DAO_Objekti obj);
	public void prepareEditArray();
	public boolean paivitaTietokantaan(MainController_IF mc);
	public DAO_Objekti getObject(int index);
}
