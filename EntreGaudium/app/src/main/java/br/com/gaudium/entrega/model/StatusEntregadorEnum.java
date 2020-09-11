package br.com.gaudium.entrega.model;

public enum StatusEntregadorEnum {
	DISPONIVEL("D"),
	DECIDINDO("I"),
	COLETANDO("L"),
	ENTREGANDO("E");

	private String sigla;

	StatusEntregadorEnum(String sigla){
		this.sigla = sigla;
	}

	public boolean equalsEnum(StatusEntregadorEnum e){
		if(e == this) return true;
		if(e == null) return false;
		return this.sigla.equals(e.sigla);
	}
}
