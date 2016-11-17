package vPakkaus.Controllers;

import java.util.ArrayList;

import javafx.scene.control.TableView;
import vPakkaus.DAO_Objekti;
import vPakkaus.Product;

public interface Taulukko_IF {
	public void addTableView(TableView table);
	public TableView<DAO_Objekti> getTaulukko();
	public boolean paivitaTietokantaan(MainController_IF mc, Nakyma_IF nakyma);
	public DAO_Objekti getObject(int index);
}
