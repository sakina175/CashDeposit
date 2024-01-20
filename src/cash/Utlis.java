package cash;

public class Utlis {
	public String genrateCode(int number){
		java.util.Date date= new java.util.Date();
		int dtoDateYr= 1900+date.getYear();
		String result=String.valueOf(number)+"/"+String.valueOf(dtoDateYr);
		System.out.print("result in utlis is "+result);
		
		return result; 
	}
}
