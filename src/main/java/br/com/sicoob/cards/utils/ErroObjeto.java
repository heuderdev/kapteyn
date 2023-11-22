package br.com.sicoob.cards.utils;

public class ErroObjeto {
    private String mensagem;

    public ErroObjeto(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}

