package vPakkaus.Controllers;

public interface Nakyma_IF extends SetMainController{
	public void paivita (Object data);
	public void resetoi();
	public void virheIlmoitus(Object viesti);
	public void esiValmistelut();
	public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja);
}
