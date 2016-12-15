package testfx;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

import javafx.scene.Parent;
import vPakkaus.MainLaunch;
/**
 * Testi luokka, missa testataan asiakkaiden etsiminen
 * @author benyi
 *
 */
public class AsiakasViewPage extends GuiTest{
/**
 * Asiakkaiden etsiminen
 */
  @Test
  public void search(){
    click("#searchCustomer");
    click("#searchCustomer1");
    click("#searchCustomer11");
    click("#searchCustomer").type("t");
    click("#searchBtn");
    doubleClick("test2");
  }
/**
 * Asiakkaiden valitseminen listalta.
 */
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

 /**
  * getRootNode, joka  palauttaa testauttavan luokan 
  */
  @Override
  protected Parent getRootNode() {
    MainLaunch m = new MainLaunch();
    m.testFX_Esivalmistelut();
    Parent parent = m.getAnchorPane("customerview");
    return parent;
  }

}
