public class ValorRetorn {
    private int valor;
    private Taulell t;

    public ValorRetorn(int v, Taulell t){
        valor=v;
        this.t=t;
    }

    public ValorRetorn(){}

    public int getValor() {
        return valor;
    }

    public Taulell getT() {
        return t;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
    public void setT(Taulell ta){
        t=ta;
    }
}
