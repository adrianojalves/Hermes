package br.com.ajasoftware.enums;

import br.com.ajasoftware.util.Trata;

/**
 * Enum que contém todos os estados brasileiros.
 * @author Jerônimo Nunes Rocha
 *
 */
public enum Estados {

	AC(12,"Acre","AC"),
	AL(27,"Alagoas","AL"),
	AP(16,"Amapá","AP"),
	AM(13,"Amazonas","AM"),
	BA(29,"Bahia","BA"),
	CE(23,"Ceará","CE"),
	DF(53,"Distrito Federal","DF"),
	ES(32,"Espírito Santo","ES"),
	GO(52,"Goiás","GO"),
	MA(21,"Maranhão","MA"),
	MT(51,"Mato Grosso","MT"),
	MS(50,"Mato Grosso do Sul","MS"),
	MG(31,"Minas Gerais","MG"),
	PA(15,"Pará","PA"),
	PB(25,"Paraíba","PB"),
	PR(41,"Paraná","PR"),
	PE(26,"Pernambuco","PE"),
	PI(22,"Piauí","PI"),
	RJ(33,"Rio de Janeiro","RJ"),
	RN(24,"Rio Grande do Norte","RN"),
	RS(43,"Rio Grande do Sul","RS"),
	RO(11,"Rondônia","RO"),
	RR(14,"Roraima","RR"),
	SC(42,"Santa Catarina","SC"),
	SE(28,"Sergipe","SE"),
	SP(35,"São Paulo","SP"),
	TO(17,"Tocantins","TO"),
	EX(99,"Exterior","EX");

	private final int codigoIbge;
	private final String nome;
	private final String sigla;

	private Estados(int codigoIbge,String nome, String sigla) {
		this.codigoIbge = codigoIbge;
		this.nome = nome;
		this.sigla=sigla;
	}
	
	public String getCodigo() {
		return Integer.toString(codigoIbge);
	}
	
	public String getNome(){
		return nome;
	}
	
	public int getCodigoIbge(){
		return codigoIbge;
	}
	
	public String getSigla() {
		return sigla;
	}
	
	public static Estados getEstado(String cUf) {
		for(Estados e : Estados.values()) {
			if(cUf.equals("EX")) {
				return EX;
			}
			if(e.codigoIbge==Trata.Int(cUf)) {
				return e;
			}
		}
		return null;
	}

	public static Estados getEstadoUf(String value) {
		// TODO Auto-generated method stub
		if(value==null) {
			value="";
		}
		for(Estados e : values()) {
			if(e.name().equals(value)) {
				return e;
			}
		}
		return null;
	}
}