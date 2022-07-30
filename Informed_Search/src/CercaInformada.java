import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

public class CercaInformada {
    //llegeix les línies del fitxer i les introdueix a una llista d'strings
    private static String[] llegirLiniesFitxer(String nomFitxer, int numLinies) {
        String[] resultat;
        resultat=new String[numLinies];
        try {
            BufferedReader fitxer=new BufferedReader(new FileReader(nomFitxer));
            for (int i=0; i<numLinies; i++) {
                resultat[i]=fitxer.readLine();
            }
            fitxer.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("No s'ha trobat el fitxer amb les dades de les mesures de Cobertura");
        }
        catch (IOException e) {
            System.out.println("S'ha produit un error al llegir el fitxer amb les dades de les mesures de Cobertura");
        }
        return resultat;
    }

    public static void main(String[] args){
        //donem format al mapa
        String[] linies=llegirLiniesFitxer("mapa2.txt", 10);
        Mapa mapa= new Mapa(10, 10);
        mapa.donarFormat(linies);

        //definim els estats inicial i final del mapa
        Estat estatInicial= new Estat(0,0);
        Estat estatFinal= new Estat(9,9);


        System.out.println("Algorisme Best first:");
        mapa.mostraMapa();
        ArrayList<Operador>cami = bestFirst( new Estat(0,0), estatFinal , mapa, 3);
        System.out.println( "Passos: " + cami.size() );
        for ( Operador o:  cami) {
            System.out.print(o + "-");
        }

        System.out.println("\n\nAlgorisme A*:");
        mapa.mostraMapa();
        cami = aEstrella( new Estat(0,0), estatFinal , mapa, 3);
        System.out.println( "Passos: " + cami.size() );
        for ( Operador o:  cami) {
            System.out.print(o + "-");
        }
    }
    public static ArrayList<Operador> bestFirst(Estat estatInicial, Estat estatFinal, Mapa mapa, int heuristica){
        ArrayList<Node> pends= new ArrayList<>();
        ArrayList<Node> tracts= new ArrayList<>();
        boolean trobat=false;
        ArrayList<Operador> cami= new ArrayList<>();
        Node actual, seguent;
        pends.add(new Node(estatInicial, cami, mapa.getValor(0,0), 0));  //afegim el node inicial a l'estructura de nodes pendents

        while(!trobat && !pends.isEmpty()){

            actual=pends.get(0);
            pends.remove(0);

            if(actual.getEstat().equals(estatFinal)){   //hem acabat
                trobat=true;
                cami=actual.getCami();
                System.out.println("Cost: " + actual.getCost() );
            }
            else{
                for(Operador op: Operador.values()){       //sino comprovem els nodes adjacents
                    ArrayList<Operador> nouCami=actual.copiaCami();
                    nouCami.add(op);
                    seguent=new Node(op.mou(actual.getEstat()), nouCami);
                    if(seguent.getEstat().esCorrecte(mapa) && !seguent.dinsLlista(pends) && !seguent.dinsLlista(tracts)){   //en cas que no s'hagi visitat aquest node
                        seguent.nouCost(actual, mapa);
                        seguent.setValH(seguent.seleccioHeuristica(heuristica,estatFinal, mapa));
                        pends.add(seguent); //l'afegim a pendents
                    }
                }
                pends.sort(Comparator.comparing(Node::getValH));    //reordenem la llista
                tracts.add(actual); //marquem com a tractat el node actual
            }
        }
        return cami;
    }
    public static ArrayList<Operador> aEstrella(Estat estatInicial, Estat estatFinal, Mapa mapa, int heuristica) {
        ArrayList<Node> pends= new ArrayList<>();
        ArrayList<Node> tracts= new ArrayList<>();
        boolean trobat=false;
        ArrayList<Operador> cami= new ArrayList<>();
        Node actual, seguent;
        pends.add(new Node(estatInicial, cami, mapa.getValor(0,0), 0));  //afegim el node inicial a l'estructura de nodes pendents

        while(!trobat && !pends.isEmpty()){

            actual=pends.get(0);
            pends.remove(0);

            if(actual.getEstat().equals(estatFinal)){
                trobat=true;
                cami=actual.getCami();
                System.out.println("Cost: " + actual.getCost() );
            }
            else{
                for(Operador op: Operador.values()){
                    ArrayList<Operador> nouCami=actual.copiaCami();
                    nouCami.add(op);
                    seguent=new Node(op.mou(actual.getEstat()), nouCami);
                    if(seguent.getEstat().esCorrecte(mapa) && !seguent.dinsLlista(pends) && !seguent.dinsLlista(tracts)){
                        seguent.nouCost(actual, mapa);
                        seguent.setValH(seguent.seleccioHeuristica(heuristica,estatFinal, mapa)+seguent.getCost());
                        pends.add(seguent);
                    }
                }
                pends.sort(Comparator.comparing(Node::getValH));
                tracts.add(actual);
            }
        }
        return cami;
    }
}
