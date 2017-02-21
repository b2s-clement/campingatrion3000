package camping.model;

public class Emplacement {
	private int numeroEmplacement;
	private String type;
	private boolean occupe;
	private int nbPlaces;
	private double prixEmplacement;

	//BUILDERS
	public Emplacement(int num,String typ,boolean occ,int nbp,double prix){
		numeroEmplacement=num;
		type=typ;
		occupe=occ;
		nbPlaces=nbp;
		prixEmplacement=prix;
	}

	//METHODES
	public String toString(){
		if(occupe){
			return "- OCCUPÉ - Emplacement "+numeroEmplacement+", "+type+", "+nbPlaces+" places, "+prixEmplacement+"€";
		}else{
			return "- DISPONIBLE - Emplacement "+numeroEmplacement+", "+type+", "+nbPlaces+" places, "+prixEmplacement+"€";
		}
	};
	public void reserver(){ //lorsque l'emplacement est réservé
        occupe=true;
    }
    public void liberer(){ // lorsqu'il est libéré
        occupe=false;
    }
    public void afficher(){
        System.out.println(this);
    }


	//GETTERS et SETTERS
	public int getNumeroEmplacement() {return numeroEmplacement;}
	public void setNumeroEmplacement(int numeroEmplacement) {this.numeroEmplacement = numeroEmplacement;}
	public String getType() {return type;}
	public void setType(String type) {this.type = type;}
	public boolean isOccupe() {return occupe;}
	public void setOccupe(boolean occupe) {this.occupe = occupe;}
	public int getNbPlaces() {return nbPlaces;}
	public void setNbPlaces(int nbPlaces) {this.nbPlaces = nbPlaces;}
	public double getPrixEmplacement() {return prixEmplacement;}
	public void setPrixEmplacement(double prixEmplacement) {this.prixEmplacement = prixEmplacement;}
}
