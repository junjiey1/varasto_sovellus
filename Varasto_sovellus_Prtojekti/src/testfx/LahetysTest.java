package testfx;

import org.loadui.testfx.GuiTest;

import javafx.scene.Parent;
import vPakkaus.MainLaunch;

public class LahetysTest extends GuiTest{




  @Override
  protected Parent getRootNode() {
    MainLaunch m = new MainLaunch();
    m.testFX_Esivalmistelut();
    Parent parent = m.getAnchorPane("Test1");
    return parent;
  }

}
