package testfx;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

import javafx.scene.Parent;
import vPakkaus.MainLaunch;

public class AsiakasPage extends GuiTest{

  @Test
  public void search(){
    click("#searchCustomer");
    click("#searchCustomer1");
    click("#searchCustomer11");
    click("#searchCustomer").type("t");
    click("#searchBtn");
    doubleClick("test2");
  }

  @Test
  public void view(){
    click("#searchCustomer");
    click("#searchCustomer").type("t");
    click("#searchBtn");
    click("#viewBtn");
    click("OK");
    doubleClick("test2");
    click("#viewBtn");
  }

  @Override
  protected Parent getRootNode() {
    MainLaunch m = new MainLaunch();
    m.testFX_Esivalmistelut();
    Parent parent = m.getAnchorPane("customerview");
    return parent;
  }

}
