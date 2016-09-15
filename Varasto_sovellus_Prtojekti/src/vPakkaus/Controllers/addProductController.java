package vPakkaus.Controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import vPakkaus.DB_AccessObject;

public class addProductController {

    @FXML
    private TextField productName;
    @FXML
    private TextField quantity;
    @FXML
    private TextField price;
    @FXML
    private TextField weight;
    @FXML
    private TextField volume;
    @FXML
    private TextField whLocation;


    boolean allGood;


//    String nimi, double paino, double tilavuus,
//	String hyllypaikka, Date saapumispaiva, Date lahtopaiva, float hinta,
//	int lisaaja_id, int poistaja_id, int maara)

    public void addNewProduct(){

    	//DB_AccessObject.Lisaa(productName.getText(), paino, tilavuus, hyllypaikka, saapumispaiva, lahtopaiva, hinta, lisaaja_id, poistaja_id, maara)

    	readFromFile();



    }

    public static void readFromFile()
    {
    	try (BufferedReader br = new BufferedReader(new FileReader("C:\\testing.txt")))
		{
			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

    }
}
