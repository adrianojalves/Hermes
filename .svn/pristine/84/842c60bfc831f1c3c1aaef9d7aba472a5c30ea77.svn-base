package br.com.ajasoftware.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import br.com.ajasoftware.qualifiers.GeraParametroReport;

@Named
@RequestScoped
public class Funcoes implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ExternalContext context;
	
	public String md5(String valor){
		String sen = "";
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			System.out.println("md = "+md);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		BigInteger hash=null;
		try {
			hash = new BigInteger(1, md.digest(valor.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sen = hash.toString(16);
		if(sen.length()<32){
			sen = adicionaZeros(32-sen.length())+sen;
		}
		return sen;
	}
	private String adicionaZeros(int valor){
		String zeros = "";

		for(int i=0; i<valor; i++){
			zeros+="0";
		}

		return zeros;
	}

	public boolean Boolean(Boolean value){
		try{
			return value.booleanValue();
		}
		catch(Exception ex){
			return false;
		}
	}
	public String removeFormatacao(String campo){
		if(campo!=null){
			campo = campo.replace(".", "").replace("-", "").replace("/", "").replace(" ", "");
		}
		return campo;
	}
	public BigDecimal zeraNulo(BigDecimal valor){
		if(valor==null){
			return BigDecimal.ZERO;
		}
		return valor;
	}

	public List<BigDecimal> getParcelas(BigDecimal valor, Integer qparcelas){
		List<BigDecimal> retorno = new ArrayList<>();
		if(qparcelas==null || qparcelas<=1){
			retorno.add(valor);
		}
		else{
			BigDecimal parcela = valor.divide(new BigDecimal(qparcelas), 2, RoundingMode.DOWN);

			for(int i = 0; i<qparcelas.intValue(); i++){
				retorno.add(parcela);
			}

			BigDecimal diferenca = calculaDiferenca(valor, retorno);

			retorno.set(retorno.size()-1, retorno.get(retorno.size()-1).subtract(diferenca));
		}
		return retorno;
	}
	public BigDecimal calculaDiferenca(BigDecimal valor, List<BigDecimal> valores) {
		// TODO Auto-generated method stub
		BigDecimal diferenca = BigDecimal.ZERO;
		BigDecimal soma = BigDecimal.ZERO;

		for(BigDecimal v : valores){
			soma = soma.add(v);
		}
		if(!(soma.compareTo(valor)==0)){
			diferenca = soma.subtract(valor);
		}

		return diferenca;
	}

	public List<Date> gerarDatas(Date dataInicial, Integer dias, Integer qparcelas){		
		List<Date> retorno = new ArrayList<>();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(dataInicial);

		if(qparcelas==null){
			calendar.add(Calendar.DATE,dias);
			retorno.add(calendar.getTime());
		}
		else{
			for(int i=0; i<qparcelas; i++){
				calendar.add(Calendar.DATE,dias);
				retorno.add(calendar.getTime());
			}
		}

		return retorno;
	}
	public String acertaNumero(String texto,int tamanho){
		if(texto == null)texto = "";
		//System.out.println("Texto: " + texto);

		String zero ="";
		for(int i=0;i<tamanho - texto.length();i++)
			zero += "0";

		texto = zero + texto;
		return (texto.substring(0,tamanho));
		//System.out.println("Texto sem simbolos: " + texto);      
	}
	public String acertaNumero(Integer tx,int tamanho){		
		String texto = String.valueOf(tx);
		if(texto == null)texto = "";
		//System.out.println("Texto: " + texto);

		String zero ="";
		for(int i=0;i<tamanho - texto.length();i++)
			zero += "0";

		texto = zero + texto;
		return (texto.substring(0,tamanho));
		//System.out.println("Texto sem simbolos: " + texto);      
	}
	public String acertaNumero(int tx,int tamanho){
		String texto = String.valueOf(tx);
		if(texto == null)texto = "";
		//System.out.println("Texto: " + texto);

		String zero ="";
		for(int i=0;i<tamanho - texto.length();i++)
			zero += "0";

		texto = zero + texto;
		return (texto.substring(0,tamanho));
		//System.out.println("Texto sem simbolos: " + texto);      
	}
	@Produces
	@GeraParametroReport
	public Map<String, Object> gerarParametros(){
		Map<String, Object> parametros = new HashMap<>();

		ServletContext servlet = (ServletContext)context.getContext();
		parametros.put("SUBREPORT_DIR", servlet.getRealPath("/relatorios/"));		

		return parametros;
	}
	
	public static int getMes(Date data) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		return calendar.get(Calendar.MONTH)+1;
	}
	public static int getAno(Date data) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		return calendar.get(Calendar.YEAR);
	}
}