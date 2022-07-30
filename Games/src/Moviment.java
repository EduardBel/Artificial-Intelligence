public class Moviment {
    private Posicio actual;
    private Posicio seguent;

    public Moviment(Posicio a, Posicio s){
        actual=a;
        seguent=s;
    }

    public Moviment(){}

    public Posicio getActual() {
        return actual;
    }

    public Posicio getSeguent() {
        return seguent;
    }
}
