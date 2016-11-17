package vPakkaus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import TietokantaKyselyt.AsiakasDB;
import TietokantaKyselyt.HyllyDB;
import TietokantaKyselyt.ProductDB;
import TietokantaKyselyt.TuoteriviDB;
import TietokantaKyselyt.UsersDB;
import TietokantaKyselyt.lampotilaDB;

/**
 * Luokka vastaa tietokantayhteydesta ja kyselyista.
 *
 */
public class DB_AccessObject {
	// ACCESS SQL_DB_OBJ.
	private static Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;


	private lampotilaDB lampotiladb;
	private AsiakasDB asiakasdb;
	private UsersDB usersdb;
	private ProductDB productdb;
	private TuoteriviDB tuoterividb;
	private HyllyDB hyllydb;

	/**
	 * Luodaan yhteys virtuaalikoneeseen ja tietokantaan.
	 *
	 */

	public DB_AccessObject() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// conn =
			// DriverManager.getConnection("jdbc:mysql://10.114.32.19:3306/varasto",
			// "jenkins", "jenkins");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:9000/varasto", "toimi", "toimi");
		} catch (SQLException e) {
			System.out.println("Yhteyden muodostaminen epäonnistui");
			try {
				System.out.println("Yritetään muodostaa yhteys Jenkinsillä");
				conn = DriverManager.getConnection("jdbc:mysql://10.114.32.19:3306/varasto", "jenkins", "jenkins");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC-ajurin lataus epäonnistui");
		}
		try {
			if (conn.isValid(10)) {
				usersdb = new UsersDB(conn);
				hyllydb = new HyllyDB(conn);
				lampotiladb = new lampotilaDB(conn);
				asiakasdb = new AsiakasDB(conn);
				productdb = new ProductDB(conn, lampotiladb);
				tuoterividb = new TuoteriviDB(conn, productdb, hyllydb);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int[] LogIn(String uname, String pword) {

		return usersdb.LogIn(uname, pword);
	}
	// -----METODIT-----//

	/**
	 * Lisaa tavara tietokantaan. Kutsuu metodeita, jolla saadaan arvot
	 * syotettya oikeisiin tietokantatauluihin.
	 *
	 * @param nimi
	 *            Tavaran nimi(String)
	 * @param paino
	 *            Tavaran paino (double)
	 * @param tilavuus
	 *            Tavaran tilavuus (double)
	 * @param hyllypaikka
	 *            missä hyllypaikassa tavara sijaitsee (String)
	 * @param hinta
	 *            Tavaran hinta (float)
	 * @param maara
	 *            Tavaran maara (int)
	 * @return Onnistuuko tavaran lisaaminen (boolean)
	 */

	public boolean Lisaa(Tuotejoukko joukko) {

		ArrayList<Boolean> onkoVirheitä = new ArrayList();

		Hyllypaikka hyllypaikka = HaeHylly(joukko.getHylly().getNimi());
		joukko.setHylly(hyllypaikka);

		// Haetaan hyllypaikan tuotteet
		ArrayList<String> Hyllyn_tuotteet = HaeHyllypaikanTuotteet(joukko.getHylly().getNimi());

		boolean mahtuuko = MahtuukoTuotteetHyllyyn(joukko);
		boolean onkoLampotilaSopiva = checkLampotila(joukko);

		// Jos hyllypaikkaa ei ole olemassa
		if (hyllypaikka == null) {
			System.out.println("hyllypaikkaa ei ole olemassa");
			onkoVirheitä.add(false);
		} else if (!mahtuuko) {
			System.out.println("Tuotteet eivät mahdu hyllyyn");
			onkoVirheitä.add(false);
			// Jos tuotetta on jo hyllypaikassa
		} else if (!onkoLampotilaSopiva) {
			System.out.println("Tuotteen lämpötila ei ole sopiva kyseiseen hyllypaikkaan");
			onkoVirheitä.add(false);
		} else if (Hyllyn_tuotteet.contains(joukko.getProduct().getProduct_name())) {
			System.out.println("Tuotetta on jo hyllyssä");

			// haetaan jo hyllyssä olevien tuotteiden määrä
			int maarahyllyssa = tuotteidenMaaraHyllyssa(joukko.getProduct().getProduct_name(),
					joukko.getHylly().getNimi());
			// lisätään uusi määrä vanhaan määrään
			int uusimaara = joukko.getMaara() + maarahyllyssa;
			joukko.setMaara(uusimaara);
			MuokkaaTuoteriviä(joukko);

			System.out.println("Tuotteiden määrää kasvatettu");

		} else {

			Product product = findProduct(joukko.getProduct().getProduct_name());
			// Jos kyseistä tuotetta ei ole vielä olemassa ollenkaan
			if (product == null) {
				addProductToTuoteTable(joukko.getProduct());
				joukko.getProduct().setID(findProduct(joukko.getProduct().getProduct_name()).getID());
				if (joukko.getProduct().getMax_temperature() != null
						&& joukko.getProduct().getMin_temperature() != null)
					addTemperatures(joukko.getProduct());
			} else {
				product = findProduct(joukko.getProduct().getProduct_name());
				// joukolle täytyy asettaa tuote ID, joka haetaan
				joukko.getProduct().setID(product.getID());
			}
			addProductToTuoteriviTable(joukko);

		}
		if (onkoVirheitä.contains(false))
			return false;
		return true;

	}

	public boolean addTemperatures(Product product) {
		return lampotiladb.addTemperatures(product);
	}

	public boolean CreateHyllypaikka(Hyllypaikka hyllypaikka) {
		return hyllydb.CreateHyllypaikka(hyllypaikka);
	}

	public boolean checkLampotila(Tuotejoukko joukko) {
		return lampotiladb.checkLampotila(joukko);
	}

	public boolean MahtuukoTuotteetHyllyyn(Tuotejoukko joukko) {
		boolean mahtuuko = true;

		Hyllypaikka hylly = HaeHylly(joukko.getHylly().getNimi());
		// Kesken eräinen tsekkaus ettei yhden tuotteen mitat ole hyllyn mittoja
		// suurempi
		// ArrayList<Double> hyllynmitat = new ArrayList();
		// hyllynmitat.add(hylly.getKorkeus());
		// hyllynmitat.add(hylly.getLeveys());
		// hyllynmitat.add(hylly.getPituus());
		Double hyllyn_tilavuus = hylly.getKorkeus() * hylly.getLeveys() * hylly.getPituus();
		Double vaadittu_tilavuus = joukko.getMaara() * joukko.getProduct().getProduct_volume();
		Double käytetty_tilavuus = 0.0;

		Double hyllyn_max_paino = HaeHylly(joukko.getHylly().getNimi()).getMax_paino();
		Double vaadittu_paino = joukko.getMaara() * joukko.getProduct().getProduct_weight();
		Double käytetty_paino = 0.0;

		ArrayList<Tuotejoukko> hyllyntuotejoukot = haeHyllynTuotejoukot(joukko.getHylly().getNimi());

		for (Tuotejoukko t : hyllyntuotejoukot) {
			käytetty_tilavuus += käytetty_tilavuus + t.getMaara() * t.getProduct().getProduct_volume();
			käytetty_paino += käytetty_paino + t.getMaara() * t.getProduct().getProduct_weight();
		}
		System.out.println("hyllyn_tilavuus =" + hyllyn_tilavuus + " vaadittu_tilavuus =" + vaadittu_tilavuus
				+ " käytetty_tilavuus =" + käytetty_tilavuus);
		System.out.println("hyllyn_max_paino =" + hyllyn_max_paino + " vaadittu_paino =" + vaadittu_paino
				+ " käytetty_paino =" + käytetty_paino);

		if ((hyllyn_tilavuus - käytetty_tilavuus) > vaadittu_tilavuus
				&& (hyllyn_max_paino - käytetty_paino) > vaadittu_paino) {
			return mahtuuko;
		} else {
			mahtuuko = false;
		}
		return mahtuuko;

	}

	//
	//
	//
	// /**
	// * Lisaa tiedot tavara-tauluun tietokannassa.
	// *
	// * @param nimi Tavaran nimi
	// * @param hinta Tavaran hinta
	// * @param paino Tavaran paino
	// * @param tilavuus Tavaran tilavuus
	// * @return jos jotain error tapahtuu lisaamisessa
	// */

	public boolean addProductToTuoteTable(Product product) {
		return productdb.addProductToTuoteTable(product);
	}

	public ArrayList<Tuotejoukko> haeHyllynTuotejoukot(String hyllynimi) {
		return tuoterividb.haeHyllynTuotejoukot(hyllynimi);
	}

	public boolean addProductToTuoteriviTable(Tuotejoukko joukko) {
		return tuoterividb.addProductToTuoteriviTable(joukko);
	}


	public Hyllypaikka HaeHylly(String tunnus) {
		return hyllydb.HaeHylly(tunnus);
	}

	public ArrayList<String> HaeHyllypaikanTuotteet(String hyllypaikka) {
		return productdb.HaeHyllypaikanTuotteet(hyllypaikka);
	}

	public ArrayList<String> HaeTuotteenHyllypaikat(Product product) {
		return hyllydb.HaeTuotteenHyllypaikat(product);
	}

	public Product findTemperatures(Product pro) {
		return lampotiladb.findTemperatures(pro);
	}

	/**
	 * Etsii tavaran nimen perusteella.
	 *
	 * @param nimi
	 *            Tavaran nimi
	 * @return palauttaa tavaran kaikki tietueet nimen perusteella.(palauttaa
	 *         null, jos tavara ei löyty.)
	 */
	public Product findProduct(String nimi) {
		return productdb.findProduct(nimi);
	}

	public int tuotteidenMaaraHyllyssa(String nimi, String hyllypaikka) {
		return tuoterividb.tuotteidenMaaraHyllyssa(nimi, hyllypaikka);
	}

	public boolean MuokkaaTuoteriviä(Tuotejoukko tuotejoukko) {
		return tuoterividb.MuokkaaTuoteriviä(tuotejoukko);
	}


	/**
	 * Hakee useamman tavaran tietueet. Hakuehto on osa tavaran nimestä.
	 *
	 * @param nimi
	 *            Tavaran nimi.
	 * @return Palauttaa ArrayList, mika sisaltaa product olioita.
	 */
	public ArrayList<Product> findProducts(String nimi) {
		return productdb.findProducts(nimi);
	}

	//
	/**
	 * Paivittaa tavaran tiedot tietokantaan.
	 *
	 * @param products
	 *            tavaran tietue
	 * @return Return error, jos tavaran lisaaminen epäonnistuu.
	 */
	public boolean updateProducts(ArrayList<Product> products) {
		return productdb.updateProducts(products);
	}

	public boolean checkIfTuoteIDExcistInLampoTila(int ID) {
		return lampotiladb.checkIfTuoteIDExcistInLampoTila(ID);
	}

	public boolean deleteLampotila(Product p) {
		return lampotiladb.deleteLampotila(p);
	}

	public boolean updateLampotila(Product p) {
		return lampotiladb.updateLampotila(p);
	}

	public boolean addAsiakas(Asiakas a) {
		return asiakasdb.addAsiakas(a);
	}

	public boolean updateAsiakas(Asiakas a) {
		return asiakasdb.updateAsiakas(a);
	}

	public Asiakas haeAsiakas(String nimi) {
		return asiakasdb.haeAsiakas(nimi);
	}

	public ArrayList<Asiakas> haeAsiakkaat(String nimi) {
		return asiakasdb.haeAsiakkaat(nimi);
	}
	// /**
	// * Sulje tietokanta yhteys.
	// *
	// * @throws SQLException Heittää error, jos jostain syystä sulkeminen
	// epäonnistuu
	// */
	//

	public void close() throws SQLException {
		conn.close();
	}
	//
	//
	// /**
	// * Tavaran tiedon postaminen.
	// *
	// * @param id Tavaran ID
	// * @return Palauta booleana, onko poistaminen onnistunut.
	// */
	//
	// public boolean deleteProduct(int id){
	// boolean error = true;
	// ps = null;
	// try {
	// ps = conn.prepareStatement("DELETE FROM hyllypaikka WHERE tuoteID = ?");
	// ps.setInt(1, id);
	// ps.executeUpdate();
	// ps = conn.prepareStatement("DELETE FROM varasto WHERE tuoteID = ?");
	// ps.setInt(1, id);
	// ps.executeUpdate();
	// ps = conn.prepareStatement("DELETE FROM tuote WHERE tuoteID = ?");
	// ps.setInt(1, id);
	// ps.executeUpdate();
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// error = false;
	// e.printStackTrace();
	// }
	// return error;
	// }
	//
	// /**
	// * Poistaa kaikkien taulujen sisällöt joilla on viitteitä tuote tauluun
	// sekä poistaa tuotetaulun rivit.
	// * Käytetään vain testailussa.
	// */
	//
	// public void dropTuotteet(){
	// ps = null;
	// try {
	// ps = conn.prepareStatement("DELETE FROM hyllypaikka");
	// ps.executeUpdate();
	// ps = conn.prepareStatement("DELETE FROM varasto");
	// ps.executeUpdate();
	// ps = conn.prepareStatement("DELETE FROM tuote");
	// ps.executeUpdate();
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }

	//
	// /**
	// * Lisaa tavaralle hyllypaikka tietokantaan.
	// *
	// * @param hyllypaikka varaston hyllypaikat
	// * @param id tavaran ID
	// * @return Palauta booleana, onko lisaaminen onnistunut
	// */
	// public boolean addProductLocation(String hyllypaikka, int id){
	// boolean error = true;
	//
	// try {
	// ps = conn.prepareStatement("INSERT INTO hyllypaikka(tunnus, tuoteID)" +
	// "VALUES (?,?);");
	//
	// ps.setString(1, hyllypaikka);
	// ps.setInt(2, id);
	//
	// ps.executeUpdate();
	// ps.close();
	//
	// } catch (SQLException e) {
	// System.out.println("Lisäys epäonnistui hyllypaikkataulukkoon!");
	// e.printStackTrace();
	// error = false;
	// }
	// return error;
	// }
	//
	// /**
	// * Lisaa tietokantaan tuotteelle oikean varaston.
	// *
	// * @param maara Tavaran maara
	// * @param id Tavaran ID
	// * @return Palauta booleana, onko lisaaminen onnistunut
	// */
	// public boolean addProductToWarehouse(int maara, int id) {
	// boolean error = true;
	// try {
	// ps= conn.prepareStatement("INSERT INTO varasto(varastoID, maara,
	// tuoteID)" + "VALUES (?,?,?);");
	//
	// int varastoID = 1;
	// ps.setInt(1, varastoID);
	// ps.setInt(2, maara);
	// ps.setInt(3, id);
	//
	// ps.executeUpdate();
	// ps.close();
	//
	// } catch (SQLException e) {
	// System.out.println("Lisäys epäonnistui varastotaulukkoon!");
	// e.printStackTrace();
	// error = false;
	// }
	// return error;
	// }
	//
	//
	//
	// /**
	// *Hakee tavaran ID:n nimen perusteella.
	// *
	// * @param nimi Tavaran nimi
	// * @return Tavaran ID
	// */
	//
	// public int getProductID(String nimi) {
	// Integer id = null;
	//
	// try {
	// ps = conn.prepareStatement("SELECT tuoteID FROM tuote WHERE nimi = ?");
	//
	// // Asetetaan argumentit sql-kyselyyn
	// ps.setString(1, nimi);
	// rs = ps.executeQuery();// Hae annetulla käyttäjänimellä
	// // tietokanta rivi
	//
	// while (rs.next()) {
	// id = rs.getInt("tuoteID");
	// }
	//
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// return id;
	// }

	//
}
