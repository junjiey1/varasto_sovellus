package vPakkaus;
import javafx.scene.layout.AnchorPane;
import vPakkaus.Controllers.NayttojenVaihtaja_IF;

public interface ViewFactory_IF {
	public AnchorPane annaNakyma(String fxmlTiedostoNimi, NayttojenVaihtaja_IF vaihtaja);
}
