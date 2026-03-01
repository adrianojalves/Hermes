package br.com.ajasoftware.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Trata {
	private static String mascaraValor = "###,###,###,###,###,###,##0";
	private static final SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy"),
			dataAmericano=new SimpleDateFormat("MM/dd/yy"),
			mesAno=new SimpleDateFormat("MM/yyyy");
	
	public static String FormValor(BigDecimal val, int dec){		
		String masc = mascaraValor;
		if(dec>0){ 
			masc+= ".";
			for(int i=0; i<dec; i++){ masc+="0"; }
		}
		DecimalFormat decimal = new DecimalFormat(masc);
		String saida = "";
		try{ saida = decimal.format(val); }
		catch (Exception e) { saida = decimal.format(0.0); }
		return saida;
	}
	
	public static Integer Int(String value){
		try{
			return new Integer(value);
		}
		catch(Exception ex){
			return null;
		}
	}

	public static boolean Boolean(Boolean bool) {
		if(bool!=null) {
			return bool;
		}
		return false;
	}

	public static BigDecimal BigDecimal(String valor) {
		try {
			return new BigDecimal(valor.replace(",", "."));
		}
		catch(Exception ex) {
			return BigDecimal.ZERO;
		}
	}

	public static String FormData(Date data) {
		try {
			return format.format(data);
		}
		catch(Exception ex) {
			return null;
		}
	}
	
	public static String FormMesAno(Date data) {
		try {
			return mesAno.format(data);
		}
		catch(Exception ex) {
			return null;
		}
	}

	public static Date FormData(String data) {
		try {
			return format.parse(data);
		}
		catch(Exception ex) {
			return null;
		}
	}

	public static String String(String string) {
		return string==null?"":string;
	}
	
	public static String removerAcentos(String str) {
	    return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	public static Date Data(String data) {
		try {
			return format.parse(data);
		}
		catch(Exception ex) {
			return null;
		}
	}
	public static Date DataAmericano(String data) {
		try {
			return dataAmericano.parse(data);
		}
		catch(Exception ex) {
			return null;
		}
	}
}