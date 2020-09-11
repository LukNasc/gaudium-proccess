package br.com.gaudium.entrega.model;

import java.util.List;

public class DadosEntregador {
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public class Response{
        private String nome;
        private String foto;
        private List<Historico> historico;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public List<Historico> getHistorico() {
            return historico;
        }

        public void setHistorico(List<Historico> historico) {
            this.historico = historico;
        }

        public String getFoto() {
            return foto;
        }

        public void setFoto(String foto) {
            this.foto = foto;
        }
    }
}
