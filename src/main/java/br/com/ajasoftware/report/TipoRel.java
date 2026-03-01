package br.com.ajasoftware.report;

public enum TipoRel {
	PDF("PDF",".pdf"), XLS("XLS", ".xls");
	
	private String descricao;
	private String extensao;
	private TipoRel(String descricao, String extensao) {
		this.descricao = descricao;
		this.extensao = extensao;
	}
	public String getDescricao() {
		return descricao;
	}
	public String getExtensao() {
		return extensao;
	}
	
}