package vPakkaus.Controllers;

import javafx.scene.layout.AnchorPane;

public interface NayttojenVaihtaja_IF{
	public void asetaUudeksiNaytoksi(String nimi, String otsikko);
	public AnchorPane getAnchorPane(String nimi);
	public void rekisteröiNakymaKontrolleri(Nakyma_IF viewController, String avain);
}
