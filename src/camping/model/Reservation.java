	package camping.model;

import camping.model.database.ReservDB;
import camping.view.tools.CustomDate;

public class Reservation {

	//ATTRIBUTES
	private int ID;
	private Client cli;
	private Emplacement emp;
	private CustomDate dateArr,dateDep;
	private boolean reglement,current;

	//BUILDER
	public Reservation(Client c,Emplacement e, CustomDate d1,CustomDate d2){
		cli=c;
		emp=e;
		dateArr=d1;
		dateDep=d2;
		reglement=false;
		current=false;
		int i=0;
		while(ReservDB.isNumResTaken(i)){
			i++;
		}
		ID=i;
	}
	public Reservation(int i,Client c,Emplacement e, CustomDate d1,CustomDate d2,boolean r,boolean cu){
		cli=c;
		emp=e;
		dateArr=d1;
		dateDep=d2;
		ID=i;
	}
	//GETTERS AND SETTERS

	public int getID(){return ID;}
	public void setID(int iD){ID = iD;}
	public Client getCli(){return cli;}
	public void setCli(Client cli){this.cli = cli;}
	public Emplacement getEmp(){return emp;}
	public void setEmp(Emplacement emp){this.emp = emp;}
	public CustomDate getDateArr(){return dateArr;}
	public void setDateArr(CustomDate dateArr){this.dateArr = dateArr;}
	public CustomDate getDateDep(){return dateDep;}
	public void setDateDep(CustomDate dateDep){this.dateDep = dateDep;}
	public boolean getReglement(){return reglement;}
	public void setRegelement(boolean r){reglement=r;}
	public boolean getCurrent(){return current;}
	public void setCurrent(boolean r){current=r;}

	//METHODS

}
