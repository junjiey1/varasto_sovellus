package vPakkaus.Controllers;

import javafx.scene.control.TableView;
import vPakkaus.DAO_Objekti;

public class AsiakasTaulukko implements Taulukko_IF{

	private TableView<DAO_Objekti> table;

	public AsiakasTaulukko(TableView<DAO_Objekti> taulukko){
		table = taulukko;
	}

	@Override
	public TableView<DAO_Objekti> getTaulukko() {
		return table;
	}

	@Override
	public boolean paivitaTietokantaan(MainController_IF mc, Nakyma_IF nakyma) {
		return false;
	}

}
