package br.com.gaudium.entrega.model;

import com.google.android.gms.maps.model.LatLng;

public class PedidoJsonObj {
	private String success;
	private PedidoObj response;

	public boolean isSuccess() {
		return "true".equalsIgnoreCase(success);
	}

	public PedidoObj getResponse() {
		return response;
	}

	public static class PedidoObj {
		private String endereco_coleta;
		private double lat_coleta, lng_coleta;
		private EntregaObj[] entrega;

		public String getEndereco_coleta() {
			return endereco_coleta;
		}

		public double getLat_coleta() {
			return lat_coleta;
		}

		public double getLng_coleta() {
			return lng_coleta;
		}

		public void setEndereco_coleta(String endereco_coleta) {
			this.endereco_coleta = endereco_coleta;
		}

		public void setLat_coleta(double lat_coleta) {
			this.lat_coleta = lat_coleta;
		}

		public void setLng_coleta(double lng_coleta) {
			this.lng_coleta = lng_coleta;
		}

		public EntregaObj[] getEntregas() {
			return entrega;
		}

		public void setEntrega(EntregaObj[] entrega) {
			this.entrega = entrega;
		}

		public EntregaObj getEntregaAtual(){
			if(entrega == null) return null;
			for (int i = 0; i < entrega.length; i++) {
				if(!entrega[i].isEntregue())
					return entrega[i];
			}

			return null;
		}

		public LatLng getLatLng() {
			return new LatLng(lat_coleta, lng_coleta);
		}
	}

	public static class EntregaObj {
		private boolean entregue;
		private double lat, lng;
		private String id;

		public EntregaObj(String id, double lat, double lng){
			this.id = id;
			this.lat = lat;
			this.lng = lng;
		}

		public double getLat() {
			return lat;
		}

		public void setLat(double lat) {
			this.lat = lat;
		}

		public double getLng() {
			return lng;
		}

		public void setLng(double lng) {
			this.lng = lng;
		}

		public boolean isEntregue() {
			return entregue;
		}

		public void setEntregue(boolean entregue) {
			this.entregue = entregue;
		}

		public LatLng getLatLng() {
			return new LatLng(lat, lng);
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
	}
}
