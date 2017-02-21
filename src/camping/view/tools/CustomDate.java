package camping.view.tools;

import java.util.Calendar;

public class CustomDate implements Comparable<CustomDate>{

	private int year,month,day;

	//GETTERS SETTERS
	public int getYear(){return year;}
	public int getMonth(){return month;}
	public int getDay(){return day;}
	public void setYear(int y){year=y;}
	public void setMonth(int m){month=m;}
	public void setDay(int d){day=d;}

	public CustomDate(){
		Calendar cal = Calendar.getInstance();
		year=cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH)+1;
		day = cal.get(Calendar.DAY_OF_MONTH);
	}

	public CustomDate(int y,int m,int d){
		year=y;
		month=m;
		day=d;
	}

	public CustomDate(String s){
		String[] split = s.split("-");
		year=Integer.parseInt(split[0]);
		month=Integer.parseInt(split[1]);
		day=Integer.parseInt(split[2]);
	}

	public String toString(){
		String res=year+"-";
		if(month<10){
			res+="0";
		}
		res+=month+"-";
		if(day<10){
			res+="0";
		}
		res+=day;
		return res;
	}

	public static void main(String[] args){
		CustomDate d1 = new CustomDate();
		System.out.println(d1);
		CustomDate d2 = new CustomDate(1994,12,03);
		System.out.println(d2);
		System.out.println(d1.compareTo(d2));
		System.out.println(d2.compareTo(d1));
		System.out.println(d2.compareTo(d2));
	}



	@Override
	public int compareTo(CustomDate a) {
		int res=0;
		boolean test = this.getYear()<a.getYear();
	//COMPARAISON ANNEE
		if(test){
			res=-1;
		}else if(this.getYear()>a.getYear()){
			res=1;
		}else{
	//COMPARAISON MOIS
			test = this.getMonth()<a.getMonth();
			if(test){
				res=-1;
			}else if(this.getMonth()>a.getMonth()){
				res=1;
			}else{
	//COMPARAISON JOURS
				test = this.getDay()<a.getDay();
				if(test){
					res=-1;
				}else if(this.getDay()>a.getDay()){
					res=1;
				}else{
					res=0;
				}
			}
		}
		return res;
	}
}
