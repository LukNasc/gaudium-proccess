package br.com.gaudium.entrega.model;

public class EntregadorObj {
	private static EntregadorObj instance = null;

	private StatusEntregadorEnum status;
	private PedidoJsonObj.PedidoObj pedido;

	public EntregadorObj(){
		status = StatusEntregadorEnum.DISPONIVEL;
	}

	public static EntregadorObj getInstance(){
		if(instance == null){
			instance = new EntregadorObj();
		}

		return instance;
	}

	public StatusEntregadorEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEntregadorEnum status) {
		this.status = status;
	}

	public PedidoJsonObj.PedidoObj getPedido() {
		return pedido;
	}

	public void setPedido(PedidoJsonObj.PedidoObj pedido) {
		this.pedido = pedido;
	}
}
