package vPakkaus.Controllers;

import java.util.ArrayList;

import javafx.scene.control.TableView;
import vPakkaus.DAO_Objekti;
import vPakkaus.Tuotejoukko;

public interface TaulukkoFactory_IF {
	public Taulukko_IF annaTaulukko(DAO_Objekti obj, ArrayList<DAO_Objekti> lista);
	public TableView<DAO_Objekti> buildHelperTable(ArrayList<DAO_Objekti> lista);
}
