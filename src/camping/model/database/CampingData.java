package camping.model.database;

import java.awt.Polygon;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class CampingData {

	/*
	 * 	Cette classe gère le stockage les coordonnées du camping et de ses emplacements
	 *
	 */

	// GET FROM FILES

	public static Polygon getCamping(String nf)throws IOException,ClassNotFoundException{
		ObjectInputStream entree = new ObjectInputStream(new FileInputStream(nf));
		Polygon res = (Polygon)entree.readObject();
		entree.close();
		return res;
	}

	public static HashMap<Integer,Polygon> getEmplacements(String nf)throws IOException,ClassNotFoundException{
		ObjectInputStream entree = new ObjectInputStream(new FileInputStream(nf));
		@SuppressWarnings("unchecked")
		HashMap<Integer,Polygon> res = (HashMap<Integer,Polygon>)entree.readObject();
		entree.close();
		return res;
	}

	// SAVE INTO FILES

	public static void saveCamping(Polygon p,String nf)throws IOException{
		ObjectOutputStream sortie = new ObjectOutputStream(new FileOutputStream(nf,false));
		sortie.writeObject(p);
		sortie.close();
	}

	public static void saveEmplacements(HashMap<Integer,Polygon> h,String nf)throws IOException{
		ObjectOutputStream sortie = new ObjectOutputStream(new FileOutputStream(nf,false));
		sortie.writeObject(h);
		sortie.close();
	}

}
