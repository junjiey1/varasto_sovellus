package vPakkaus.Controllers;

import javafx.scene.layout.AnchorPane;

public interface NayttojenVaihtaja_IF{
	public void asetaUudeksiNaytoksi(String nimi, String otsikko, Object preData);
	public AnchorPane getAnchorPane(String nimi);
	public void rekisteröiNakymaKontrolleri(Nakyma_IF viewController, String avain);
	public Nakyma_IF haeKontrolleri(String nimi);
}
