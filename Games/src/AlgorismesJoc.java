import java.util.ArrayList;
import java.util.List;

public class AlgorismesJoc {
    public static int nivellMiniMax=7;
    public static int nivellAlfaBeta=13;

    private  Moviment moviment;
    private  int color;
    private  int heuristica;
    private  int algorisme;

public AlgorismesJoc(int color, int heuristica, int algorisme){
    this.color=color;
    this.algorisme=algorisme;
    this.heuristica=heuristica;
}



    public int prenHeuristica(Taulell t) {
        int ret;
        switch (heuristica) {
            case 1: ret=heuristica1(t);
                break;
            case 2: ret=heuristica2(t);
                break;
            case 3: ret=heuristica3(t);
                break;
            default:ret=heuristica3(t);
        }
        return ret;
    }

    public Moviment seleccioAlgorisme(Taulell t){
        switch (algorisme){
            case 1: miniMax(t, 1);
            break;
            case 2: alfaBeta(t, 1, Integer.MIN_VALUE,Integer.MAX_VALUE);
        }

        return moviment;
    }

    public List<Moviment> movimentsPossibles(Taulell t){
        List<Moviment> llista=new ArrayList<>();
        for(Posicio p: t.getFitxes(color)){
            for(Posicio mov: t.movimentsPossiblesFitxa(p.getFila(),p.getColumna())){
                llista.add(new Moviment(p, mov));
            }
        }
        return llista;
    }

    public  ValorRetorn miniMax(Taulell t, int nivell){
        List<Moviment> movs=movimentsPossibles(t);
        ValorRetorn resultat;

        if(!t.movimentsPossibles(1) && !t.movimentsPossibles(2)){    //si no hi ha moviments possibles(ha acabat la partida)
            if(t.guanyador()==color){
                return (new ValorRetorn(Integer.MAX_VALUE, null));
            }
            else{
                return (new ValorRetorn(Integer.MIN_VALUE, null));
            }
        }
        else if(nivell==nivellMiniMax || movs.isEmpty()){
            return(new ValorRetorn(prenHeuristica(t), null));
        }
        else{
            ValorRetorn ret=new ValorRetorn();
            if(nivell%2==1){
                ret.setValor(Integer.MIN_VALUE);
               // t.canviTorn();
            }
            else{
                ret.setValor(Integer.MAX_VALUE);
            }
            for(Moviment m: movs){
                Taulell nouTaulell=t.clonar();
                Fitxa f=nouTaulell.taulell[m.getActual().getFila()][m.getActual().getColumna()];
                nouTaulell.mouFitxa(f, m.getSeguent());
                resultat=miniMax(nouTaulell,nivell+1);

                //node MAX(màquina)
                if(nivell%2==1){
                    if(nivell==1){
                        moviment=m;
                    }
                    if(resultat.getValor()> ret.getValor()){
                        ret.setValor(resultat.getValor());
                        ret.setT(nouTaulell);

                        if(nivell==1){
                            moviment=m;
                        }
                    }
                }
                else{   //node MIN
                    if(resultat.getValor()< ret.getValor()){
                        ret.setValor(resultat.getValor());
                        ret.setT(nouTaulell);
                    }
                }
            }
            return ret;
        }
    }

    public ValorRetorn alfaBeta(Taulell t, int nivell, int alfa, int beta){
        ValorRetorn resultat;
        List<Moviment>movs=movimentsPossibles(t);

        if(!t.movimentsPossibles(1) && !t.movimentsPossibles(2)){    //si no hi ha moviments possibles(ha acabat la partida)
            if(t.guanyador()==color){
                return (new ValorRetorn(Integer.MAX_VALUE, null));
            }
            else{
                return (new ValorRetorn(Integer.MIN_VALUE, null));
            }
        }
        else if(nivell==nivellAlfaBeta || movs.isEmpty()){
            return(new ValorRetorn(prenHeuristica(t), null));
        }
        else{
            ValorRetorn ret=new ValorRetorn();
            Moviment mov;
            while(!movs.isEmpty() && alfa<beta){
                mov=movs.get(0);
                movs.remove(0);
                Taulell nouTaulell=t.clonar();
                Fitxa f=nouTaulell.taulell[mov.getActual().getFila()][mov.getActual().getColumna()];
                nouTaulell.mouFitxa(f, mov.getSeguent());
                resultat=alfaBeta(t, nivell+1,alfa,beta);

                //node MAX
                if(nivell%2 ==1){
                    if(resultat.getValor()>alfa){
                        alfa=resultat.getValor();
                        ret.setT(nouTaulell);
                        if(nivell==1){
                            moviment=mov;
                        }
                    }
                }
                else{   //node MIN
                    if(resultat.getValor()<beta){
                        beta=resultat.getValor();
                        ret.setT(nouTaulell);
                    }
                }
            }
            if(nivell%2==1){
                ret.setValor(alfa);
            }
            else{
                ret.setValor(beta);
            }
            return ret;
        }
    }




    public int heuristica1(Taulell t){
        int val=0;
        if(color==1){
            val=3*t.totalDames(1)+(t.totalFitxes(1)-t.totalDames(1));
            val-=3*t.totalDames(2)+(t.totalFitxes(2)-t.totalDames(2));
        }
        else{
            val=3*t.totalDames(2)+(t.totalFitxes(2)-t.totalDames(2));
            val-=3*t.totalDames(1)+(t.totalFitxes(1)-t.totalDames(1));
        }
        return val;
    }

    public int heuristica2(Taulell t){
        int val=0;
        if(color==1){
            val=t.totalFitxes(1)-t.totalFitxes(2);
        }
        else{
            val=t.totalFitxes(2)-t.totalFitxes(1);
        }
        return val;
    }

    public int heuristica3(Taulell t){
        int val=0;
        if(color==1){
            val=t.fitxesAvencades(1)-t.fitxesAvencades(2);
        }
        else{
            val=t.fitxesAvencades(2)-t.fitxesAvencades(1);
        }
        return val;
    }
}
