package vPakkaus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Luokka vastaa tietokantayhteydesta ja kyselyista.
 *
 */
public class DB_AccessObject {
	// ACCESS SQL_DB_OBJ.
	private static Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

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
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC-ajurin lataus epäonnistui");
		}
	}

	// -----METODIT-----//

	/**
	 * Metodi, joka vastaa käyttäjätunnusten hakemisesta ja tarkistamisesta.
	 *
	 * @param uname
	 *            kayttajatunnus
	 * @param pword
	 *            salasana
	 * @return palautta listana.
	 */

	public int[] LogIn(String uname, String pword) {
		int res = 0; // Oletetaan, että login epäonnistuu

		PreparedStatement haetiedot = null;
		ResultSet rs = null;
		String pass = "";
		int id = 0;

		try {
			// Parametrisoitu sql-kysely
			haetiedot = conn.prepareStatement("SELECT * FROM users WHERE user = ?");
			try {
				// Asetetaan argumentit sql-kyselyyn
				haetiedot.setString(1, uname);
				rs = haetiedot.executeQuery();// Hae annetulla käyttäjänimellä

				// tietokanta rivi
				try {
					while (rs.next()) {
						pass = rs.getString("pass"); // hae password column ja
						// tallenna muuttujaan
						id = rs.getInt("id");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					rs.close();
					System.out.println("Tulosjuokko suljettu");
				}
			} catch (SQLException e) {
				System.out.println("Haku " + uname + " epäonnistui!");
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.out.println("Tietojen haku epäonnistui!");
		} finally {
			try {
				haetiedot.close();
				System.out.println("Haku kysely suljettu");
			} catch (SQLException e) {
				System.out.println("Yhteyden sulkemisessa vikaa");
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("Vikaa LogInin lopussa");
			}
		}

		if (pass.equals(pword)) { // tarkistetaan salasanat
			// True vain jos funktioon tullut salasana
			// on sama mikä löytyy tietokannasta
			res = 1;
		}
		int[] list = { res, id };
		return list;
	}

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
		// Listaan kerätään virheet
		ArrayList<Boolean> onkoVirheitä = new ArrayList();

		// Haetaan hyllypaikka
		Hyllypaikka hyllypaikka = HaeHylly(joukko.getHylly().getNimi());

		// Haetaan hyllypaikan tuotteet
		ArrayList<String> Hyllyn_tuotteet = HaeHyllypaikanTuotteet(joukko.getHylly().getNimi());

		// Jos hyllypaikkaa ei ole olemassa
		if (hyllypaikka == null) {
			System.out.println("hyllypaikkaa ei ole olemassa");
			onkoVirheitä.add(false);
			// Jos tuotetta on jo hyllypaikassa
		} else if (Hyllyn_tuotteet.contains(joukko.getProduct().getProduct_name())) {
			System.out.println("Tuotetta on jo hyllyssä");

			// Tarkistetaan onko hyllyssä tilaa tuotteille
			boolean mahtuuko = MahtuukoTuotteetHyllyyn(joukko);
			// jos mahtuu
			// alla keskeneräinen tarkistus voiko tuotetta lisätä hyllyyn lämpötilan perusteella
//			if (joukko.getProduct().getMax_temperature() != null & joukko.getProduct().getMin_temperature() != null) {
//				if (hyllypaikka.getLämpötila() < joukko.getProduct().getMax_temperature() && hyllypaikka.getLämpötila() > joukko.getProduct().getMin_temperature()) {
//
//				} else {
//					return false;
//				}
//			}

			if (mahtuuko) {

				// haetaan jo hyllyssä olevien tuotteiden määrä
				Tuotejoukko joukko_hyllyssä = GetTuotejoukko(joukko.getProduct().getProduct_name(),
						joukko.getHylly().getNimi());
				// lisätään uusi määrä vanhaan määrään
				int uusimaara = joukko.getMaara() + joukko_hyllyssä.getMaara();
				joukko.setMaara(uusimaara);
				MuokkaaTuoteriviä(joukko);

				System.out.println("Tuotteet lisätty hyllyyn");
				return true;
			} else {
				System.out.println("Tuotteet eivät mahdu hyllyyn");
				return false;
			}

			// Muuten
		} else {
			// tarkistetaan mahtuuko tuotteet hyllyyn
			boolean mahtuuko = MahtuukoTuotteetHyllyyn(joukko);
			System.out.println("Mahtuuko " + mahtuuko);
			if (mahtuuko) {
				Product product = findProduct(joukko.getProduct().getProduct_name());
				// Jos kyseistä tuotetta ei ole vielä olemassa ollenkaan
				if (product == null) {
					addProductToTuoteTable(joukko.getProduct());
					joukko.getProduct().setID(findProduct(joukko.getProduct().getProduct_name()).getID());
					if (joukko.getProduct().getMax_temperature() != null && joukko.getProduct().getMin_temperature() != null)
						addTemperatures(joukko.getProduct());
				} else {
					product = findProduct(joukko.getProduct().getProduct_name());
					// joukolle täytyy asettaa tuote ID, joka haetaan
					// productille
					// yläpuolella.
					joukko.getProduct().setID(product.getID());
				}
				addProductToTuoteriviTable(joukko);
			}
		}
		if (onkoVirheitä.contains(false))
			return false;
		return true;
	}

	public boolean addTemperatures(Product product) {
		// Tarvitaan tuotteen ID jotta voidaan lisätä arvoja Lämpötila
		// taulukkoon
		System.out.println("ID " + product.getID());
		try {
			ps = conn.prepareStatement("INSERT INTO lampotila(tuoteID, lampotila_max, lampotila_min) VALUES (?,?,?);");
			// haetaan tuotteen id tietokannasta

			ps.setInt(1, product.getID());
			ps.setInt(2, product.getMax_temperature());
			ps.setInt(3, product.getMin_temperature());

			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			System.out.println("Lisäys epäonnistui lampotilataulukkoon!");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean CreateHyllypaikka(Hyllypaikka hyllypaikka) {
		int varastoID = 513;

		try {
			ps = conn.prepareStatement(
					"INSERT INTO hyllypaikka(tunnus, pituus, leveys, korkeus, maksimi_paino, lampotila, varastoID) VALUES (?,?,?,?,?,?,?);");
			// haetaan tuotteen id tietokannasta

			ps.setString(1, hyllypaikka.getNimi());
			ps.setDouble(2, hyllypaikka.getPituus());
			ps.setDouble(3, hyllypaikka.getLeveys());
			ps.setDouble(4, hyllypaikka.getKorkeus());
			ps.setDouble(5, hyllypaikka.getMax_paino());
			ps.setDouble(6, hyllypaikka.getLämpötila());
			ps.setInt(7, varastoID);

			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			System.out.println("Lisäys epäonnistui hyllypaikkataulukkoon!");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean MahtuukoTuotteetHyllyyn(Tuotejoukko joukko) {
		boolean mahtuuko = true;


		Hyllypaikka hylly = HaeHylly(joukko.getHylly().getNimi());
		//Kesken eräinen tsekkaus ettei yhden tuotteen mitat ole hyllyn mittoja suurempi
//		ArrayList<Double> hyllynmitat = new ArrayList();
//		hyllynmitat.add(hylly.getKorkeus());
//		hyllynmitat.add(hylly.getLeveys());
//		hyllynmitat.add(hylly.getPituus());
		Double hyllyn_tilavuus = hylly.getKorkeus()*hylly.getLeveys()*hylly.getPituus();
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
		int lampotila_boolean = 0;
		if (product.getMax_temperature() != null && product.getMin_temperature() != null)
			lampotila_boolean = 1;
		try {
			ps = conn.prepareStatement(
					"INSERT INTO tuote(nimi, hinta, paino, pituus, leveys, korkeus, lampotila_boolean)"
							+ "VALUES (?,?,?,?,?,?,?);");
			// haetaan tuotteen id tietokannasta

			ps.setString(1, product.getProduct_name());
			ps.setFloat(2, product.getProduct_price());
			ps.setDouble(3, product.getProduct_weight());
			ps.setDouble(4, product.getProduct_length());
			ps.setDouble(5, product.getProduct_width());
			ps.setDouble(6, product.getProduct_height());
			ps.setDouble(7, lampotila_boolean);

			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			System.out.println("Lisäys epäonnistui tuotetaulukkoon!");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public ArrayList<Tuotejoukko> haeHyllynTuotejoukot(String hyllynimi) {

		ArrayList<Tuotejoukko> tj = new ArrayList();
		ArrayList<String> nimet = new ArrayList();
		ArrayList<Integer> määrät = new ArrayList();

		try {
			ps = conn.prepareStatement(
					"SELECT tuote.nimi, tuoterivi.maara FROM tuote, tuoterivi WHERE tuote.tuoteID = tuoterivi.tuoteID AND tuoterivi.hyllypaikka = ?;");

			// Asetetaan argumentit sql-kyselyyn
			ps.setString(1, hyllynimi);
			rs = ps.executeQuery();// Hae annetulla käyttäjänimellä
			// tietokanta rivi

			while (rs.next()) {
				int maara = rs.getInt("maara");
				String nimi = rs.getString("nimi");
				nimet.add(nimi);
				määrät.add(maara);
			}

			for (int i = 0; i < nimet.size(); i++) {
				Tuotejoukko tuotejoukko = new Tuotejoukko(findProduct(nimet.get(i)), HaeHylly(hyllynimi),
						määrät.get(i));
				tj.add(tuotejoukko);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tj;
	}

	public boolean addProductToTuoteriviTable(Tuotejoukko joukko) {

		try {
			ps = conn.prepareStatement("INSERT INTO tuoterivi(tuoteID, hyllypaikka, maara)" + "VALUES (?,?,?);");

			ps.setInt(1, joukko.getProduct().getID());
			ps.setString(2, joukko.getHylly().getNimi());
			ps.setInt(3, joukko.getMaara());

			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			System.out.println("Lisäys epäonnistui tuoterivitaulukkoon!");
			e.printStackTrace();
			return false;
		}
		return true;
	}

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
	public Hyllypaikka HaeHylly(String tunnus) {
		Hyllypaikka hyl = null;
		try {
			ps = conn.prepareStatement(
					"select hyllypaikka.tunnus , hyllypaikka.pituus, hyllypaikka.leveys, hyllypaikka.korkeus, hyllypaikka.lampotila, hyllypaikka.maksimi_paino from hyllypaikka where hyllypaikka.tunnus=?;");
			ps.setString(1, tunnus);
			rs = ps.executeQuery();
			while (rs.next()) {
				String nimi = rs.getString("tunnus");
				double leveys = rs.getDouble("leveys");
				double pituus = rs.getDouble("pituus");
				double syvyys = rs.getDouble("korkeus");
				double lampotila = rs.getDouble("lampotila");
				double max_paino = rs.getDouble("maksimi_paino");
				hyl = new Hyllypaikka(nimi, pituus, leveys, syvyys, lampotila, max_paino);
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return hyl;
	}

	public ArrayList<String> HaeHyllypaikanTuotteet(String hyllypaikka) {
		ArrayList<String> HP_Tuotteet = new ArrayList();
		try {
			ps = conn.prepareStatement(
					"Select tuote.nimi from tuote, tuoterivi WHERE tuoterivi.tuoteID = tuote.tuoteID AND tuoterivi.hyllypaikka = ?;");
			ps.setString(1, hyllypaikka);
			rs = ps.executeQuery();
			while (rs.next()) {
				String nimi = rs.getString("nimi");

				HP_Tuotteet.add(nimi);
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return HP_Tuotteet;
	}

	public Product findTemperatures(Product pro) {
		if (pro.getTemp()) {
			try {
				ps = conn.prepareStatement("SELECT lampotila_max, lampotila_min FROM lampotila WHERE tuoteID = ?");
				// Asetetaan argumentit sql-kyselyyn
				ps.setInt(1, pro.getID());
				rs = ps.executeQuery();
				while (rs.next()) {
					int lampotila_max = rs.getInt("lampotila_max");
					int lampotila_min = rs.getInt("lampotila_min");
					pro.setMax_temperature(lampotila_max);
					pro.setMin_temperature(lampotila_min);
				}
				ps.close();
			} catch (SQLException e) {
				System.out.println("Ei voitu hakea lämpötilaa!");
				e.printStackTrace();
			}
		}
		return pro;
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
		Product product = null;
		try {
			ps = conn.prepareStatement(
					"SELECT tuote.tuoteID, tuote.nimi, tuote.hinta, tuote.paino, tuote.pituus, tuote.leveys, tuote.korkeus, tuote.lampotila_boolean FROM tuote WHERE tuote.nimi = ?");

			// Asetetaan argumentit sql-kyselyyn
			ps.setString(1, nimi);
			rs = ps.executeQuery();// Hae annetulla käyttäjänimellä
			// tietokanta rivi

			while (rs.next()) {
				int id = rs.getInt("tuoteID");
				String name = rs.getString("nimi");
				double paino = rs.getDouble("paino");
				double pituus = rs.getDouble("pituus");
				double leveys = rs.getDouble("leveys");
				double korkeus = rs.getDouble("korkeus");
				float hinta = rs.getFloat("hinta");
				float lampotila_boolean = rs.getFloat("lampotila_boolean");
				product = new Product(name, paino, leveys, korkeus, pituus, hinta);
				product.setID(id);
				if (lampotila_boolean == 1)
					product.setTemp(true);
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}

	public Tuotejoukko GetTuotejoukko(String nimi, String hyllypaikka) {

		Tuotejoukko tuotejoukko = null;
		try {
			ps = conn.prepareStatement(
					"Select tuoterivi.maara FROM tuoterivi, tuote WHERE tuoterivi.tuoteID = tuote.tuoteID AND tuote.nimi = ? and  tuoterivi.hyllypaikka = ?;");
			ps.setString(1, nimi);
			ps.setString(2, hyllypaikka);
			rs = ps.executeQuery();
			while (rs.next()) {
				int maara = rs.getInt("maara");
				Product product = findProduct(nimi);
				Hyllypaikka hp = HaeHylly(hyllypaikka);
				tuotejoukko = new Tuotejoukko(product, hp, maara);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tuotejoukko;

	}

	public boolean MuokkaaTuoteriviä(Tuotejoukko tuotejoukko) {
		boolean error = false;

		try {
			ps = conn.prepareStatement(
					"UPDATE tuote, tuoterivi SET tuoterivi.maara = ?  WHERE tuote.tuoteID = tuoterivi.tuoteID AND tuote.nimi = ? AND tuoterivi.hyllypaikka = ?;");

			ps.setInt(1, tuotejoukko.getMaara());
			ps.setString(2, tuotejoukko.getProduct().getProduct_name());
			ps.setString(3, tuotejoukko.getHylly().getNimi());

			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
			error = true;
		}

		return error;
	}

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
		 /**
		 *Hakee useamman tavaran tietueet. Hakuehto on osa tavaran nimestä.
		 *
		 * @param nimi Tavaran nimi.
		 * @return Palauttaa ArrayList, mika sisaltaa product olioita.
		 */
		 public ArrayList<Product> findProducts(String nimi) {
		 ArrayList<Product> products = new ArrayList();
		 Product product;

		 try {
		 nimi = "%"+nimi+"%";
		 ps = conn.prepareStatement("SELECT tuote.tuoteID, tuote.nimi,"
		 		+ "tuote.hinta, tuote.paino, tuote.pituus, tuote.leveys, tuote.korkeus, tuote.lampotila_boolean "
		 		+ "FROM tuote WHERE tuote.nimi LIKE ?");
		 		//+ "AND tuote.tuoteID = hyllypaikka.tuoteID AND tuote.tuoteID ="
		 		//+ "varasto.tuoteID;");

		 // Asetetaan argumentit sql-kyselyyn
		 ps.setString(1, nimi);
		 rs = ps.executeQuery();
		 // tietokanta rivi

		 while (rs.next()) {
			 int id = rs.getInt("tuoteID");
			 String name = rs.getString("nimi");
			 double paino = rs.getDouble("paino");
			 double pituus = rs.getDouble("pituus");
			 double leveys = rs.getDouble("leveys");
			 double korkeus = rs.getDouble("korkeus");
			 float hinta = rs.getFloat("hinta");
			 product = new Product(name, paino, leveys, korkeus, pituus, hinta);
			 product.setID(id);
			 if(rs.getInt("lampotila_boolean")==1){
				 product.setTemp(true);
			 }
	 		products.add(product);
		 }
		 rs.close();
		 ps.close();
		 for(Product p : products)
			 if(p.getTemp())
				 product = findTemperatures(p);
		 } catch (SQLException e) {
		 e.printStackTrace();
		 }

		 return products;
	}
	//
	// /**
	// * Paivittaa tavaran tiedot tietokantaan.
	// *
	// * @param products tavaran tietue
	// * @return Return error, jos tavaran lisaaminen epäonnistuu.
	// */
	// public boolean updateProducts(ArrayList<Product> products) {
	// boolean error = true;
	//
	// for(Product p : products){
	//
	// try {
	// ps= conn.prepareStatement("UPDATE tuote, varasto, hyllypaikka SET
	// tuote.nimi = ?,tuote.hinta = ?, tuote.paino = ?, tuote.tilavuus = ?,
	// hyllypaikka.tunnus = ?, varasto.maara = ? WHERE tuote.tuoteID =
	// hyllypaikka.tuoteID AND tuote.tuoteID = varasto.tuoteID AND tuote.tuoteID
	// = ?;");
	//
	// ps.setString(1, p.getProduct_name());
	// ps.setFloat(2, p.getProduct_price());
	// ps.setDouble(3, p.getProduct_weight());
	// ps.setDouble(4, p.getProduct_volume());
	// ps.setString(5, p.getProduct_location());
	// ps.setInt(6, p.getMaara());
	// ps.setInt(7, p.getID());
	//
	// ps.executeUpdate();
	// ps.close();
	//
	// } catch (SQLException e) {
	// e.printStackTrace();
	// error = false;
	// }
	//
	// }
	//
	// return error;
	// }
	//
	//
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
}
