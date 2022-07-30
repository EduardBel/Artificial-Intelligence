import java.util.ArrayList;
import static java.lang.Math.*;
public class Node {
    private Estat estat;
    private ArrayList<Operador> cami;
    private float cost;
    private float valH;


    public Node(Estat est, ArrayList<Operador> cami) {
        this.estat = est;
        this.cami = cami;
    }

    public Node(Estat est, ArrayList<Operador> cami,  float cost, float val) {
        this.estat = est;
        this.cami = cami;
        this.cost = cost;
        this.valH=val;

    }


    public boolean dinsLlista(ArrayList<Node> llista){  //per comprovar si el node actual es troba a la llista
        boolean dins=false;
        for (Node n : llista){
            if(n.getEstat().equals(this.getEstat())) dins=true;
        }
        return dins;
    }

    public ArrayList<Operador> getCami() {
        return cami;
    }

    public ArrayList<Operador> copiaCami(){
        ArrayList<Operador> nouCami= new ArrayList<>();
        for(Operador op: cami){
            nouCami.add(op);
        }
        return nouCami;
    }

    public Estat getEstat(){
        return this.estat;
    }

    public float getCost(){
        return this.cost;
    }

    public void setCost(float cost){
        this.cost=cost;
    }

    public void setValH(float valH) {
        this.valH = valH;
    }

    public float getValH(){
        return valH;
    }

    public void nouCost(Node anterior, Mapa mapa){
        this.cost=anterior.getCost()+costMoviment(anterior.getEstat().getAlcada(mapa), mapa);
    }
    public float costMoviment(int alçadaAnt, Mapa mapa){
        if(this.getEstat().getAlcada(mapa)<alçadaAnt)return(float)0.5;
        else return(1+(this.getEstat().getAlcada(mapa))-alçadaAnt);
    }

    @Override
    public String toString() {
        return "Node{" +
                ", estat=" + estat +
                ", cami=" + cami +
                ", cost=" + cost +
                '}';
    }

    public float seleccioHeuristica(int i,Estat estatFinal, Mapa mapa){
        float val;
        switch (i){
            case 1: val=heuristica1(estatFinal);
                break;
            case 2:val=heuristica2(estatFinal);
                break;
            case 3:val=heuristica3(estatFinal, mapa);
                break;
            default:val=heuristica3(estatFinal, mapa);
        }
        return(val);
    }

    //Admissible: el valor heurístic del node serà el cost mitjà dels nodes del camí inclòs aquest més la distància entre els dos nodes
    public float heuristica2(Estat estatFinal){
        return (float) this.cost/(float)cami.size()+ (float)sqrt(pow(estatFinal.getColumna()-this.estat.getColumna(), 2) + pow(estatFinal.getFila()-this.estat.getFila(), 2));
    }

    //Admissible: el valor heurístic del node serà la distància en línia recta d'aquest node fins el node final
    public float heuristica1(Estat estatFinal){
        return (float)sqrt(pow(estatFinal.getColumna()-this.estat.getColumna(), 2) + pow(estatFinal.getFila()-this.estat.getFila(), 2));
    }

    //el valor heurístic del node serà la diferència d'alçades entre el node actual i el final
    public float heuristica3(Estat estatFinal, Mapa mapa){
        return this.estat.getAlcada(mapa)-estatFinal.getAlcada(mapa);
    }

}
