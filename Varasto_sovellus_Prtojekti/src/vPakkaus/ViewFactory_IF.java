package vPakkaus;
import javafx.scene.layout.AnchorPane;
import vPakkaus.Controllers.NayttojenVaihtaja_IF;

/**
 * @author Julius
 *Määrittelee Tehtaalle metodin
 */
public interface ViewFactory_IF {
	/**
	 *
	 * @param fxmlTiedostoNimi
	 * @param vaihtaja
	 * Luo annetulla tiedostonnimellä AnchorPane olioita. Asettaa ohjelman pääkontrollerin ja näkymien vaihtajan Anchorpane-kontrollerille
	 * @return AnchorPane
	 */
	public AnchorPane annaNakyma(String fxmlTiedostoNimi, NayttojenVaihtaja_IF vaihtaja);
}
