package vPakkaus.Controllers;

public class MuokkaaProductController implements SetMainController{
	private MainController mc;

	public MuokkaaProductController(){

	}

	@Override
	public void setMainController(MainController m) {
		mc = m;
	}

	public void HaeTuote(){
		String nimi=null;
		//Hae tekstikentän arvo
		mc.haeTuote(nimi);
	}

}
