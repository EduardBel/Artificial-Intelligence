import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Dames {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";
    static Scanner teclat = new Scanner(System.in);
    public static int damesBlaves;  //blaves=negres
    public static int damesVermelles;   //vermelles=blanques
    public static int jugador;
    public static long time1_start;
    public static long time1_end;
    public static long total1_time = 0;
    public static long time2_start;
    public static long time2_end;
    public static long total2_time = 0;

    public static String demanaMoviment(Taulell t){

        char c;
        String mov="";
        boolean jugadaLegal=false;
        Fitxa f;
        String color;
        if(jugador==1){
            color=ANSI_BLUE;
        }
        else{
            color=ANSI_RED;
        }
        System.out.println("\n"+color+"Que vols jugar?: "+ANSI_RESET);
        mov = teclat.nextLine();

        while(!jugadaLegal){
            c=mov.charAt(3);
            f=t.getFitxa(Integer.parseInt(String.valueOf(mov.charAt(1))), t.lletraANum(mov.charAt(0)));
            if(c=='d' | c=='D' |c=='e'| c=='E') {
                if (f.getColor()==jugador && t.movimentLegal(f,determinaPosicio(c, f, t))){
                    jugadaLegal=true;
                }
                else{
                    t.printBoard();
                    System.out.println("\nMOVIMENT IL·LEGAL! "+color+"Que vols jugar?: "+ANSI_RESET);
                    mov = teclat.nextLine();
                    f=t.getFitxa(Integer.parseInt(String.valueOf(mov.charAt(1))), t.lletraANum(mov.charAt(0)));
                }
            }
            else {
                t.printBoard();
                System.out.println("\nERROR! "+color+"Que vols jugar?: "+ANSI_RESET);
                mov = teclat.nextLine();
                f=t.getFitxa(Integer.parseInt(String.valueOf(mov.charAt(1))), t.lletraANum(mov.charAt(0)));
            }
        }
        //si arriba aqui es tracta d'un moviment legal
        return mov;
    }

    public static Posicio determinaPosicio(char c, Fitxa f, Taulell t){   //si new pos és la posicio on hi ha una fitxa de l'altre color, saltar dues
    Posicio pos=new Posicio();
        if(jugador==1){ //juguen blaves
            if(c=='D'| c=='d'){
                 pos=new Posicio(f.getFila()+1, f.getColumna()-1);
                 if(t.getFitxa(pos.getFila(), pos.getColumna()).getColor()==2 && t.getFitxa(pos.getFila()+1, pos.getColumna()-1).getColor()==0){ //en cas que el moviment
                     // sigui sobre una fitxa enemiga, saltar-la si la casella és buida
                     pos=new Posicio(f.getFila()+2, f.getColumna()-2);
                 }
            }
            else if(c=='E'| c=='e'){
                 pos=new Posicio(f.getFila()+1, f.getColumna()+1);
                if(t.getFitxa(pos.getFila(), pos.getColumna()).getColor()==2 && t.getFitxa(pos.getFila()+1, pos.getColumna()+1).getColor()==0){
                    pos=new Posicio(f.getFila()+2, f.getColumna()+2);
                }
            }
            //else return -1;
        }
        else{
            if(c=='D'| c=='d'){
                 pos=new Posicio(f.getFila()-1, f.getColumna()+1);
                if(t.getFitxa(pos.getFila(), pos.getColumna()).getColor()==1 && t.getFitxa(pos.getFila()-1, pos.getColumna()+1).getColor()==0){
                    pos=new Posicio(f.getFila()-2, f.getColumna()+2);
                }
            }
            else if(c=='E'| c=='e'){
                 pos=new Posicio(f.getFila()-1, f.getColumna()-1);
                if(t.getFitxa(pos.getFila(), pos.getColumna()).getColor()==1 && t.getFitxa(pos.getFila()-1, pos.getColumna()-1).getColor()==0){
                    pos=new Posicio(f.getFila()-2, f.getColumna()-2);
                }
            }
            //else return -1;
        }
       return pos;
    }


    public static int jugadorVsJugador(Taulell t){
        jugador=1;
        String mov="";
        Fitxa f;
        while(t.movimentsPossibles(1) || t.movimentsPossibles(2)){    //hauria de ser un while(jug1.movPossibles!=0 || jug2.movPossibles!=0)
            mov=demanaMoviment(t);
            f=t.getFitxa(Integer.parseInt(String.valueOf(mov.charAt(1))), t.lletraANum(mov.charAt(0)));
            t.mouFitxa(f, determinaPosicio(mov.charAt(3), f, t));
            t.printBoard();
            canviJugador();
        }
        return t.guanyador();
    }

    public static int jugadorVsIA(Taulell t, int algorisme, int heur) {
        //int torns=0;
        AlgorismesJoc maquina=new AlgorismesJoc(2, heur, algorisme);
        jugador=1;  //comença humà
        String mov="";
        Fitxa f;
        Moviment moviment=new Moviment();
        while(t.movimentsPossibles(1) || t.movimentsPossibles(2)){    //hauria de ser un while(jug1.movPossibles!=0 || jug2.movPossibles!=0)
            if(jugador==1 && t.movimentsPossibles(1)){
                mov=demanaMoviment(t);
                f=t.getFitxa(Integer.parseInt(String.valueOf(mov.charAt(1))), t.lletraANum(mov.charAt(0)));
                t.mouFitxa(f, determinaPosicio(mov.charAt(3), f, t));
                t.printBoard();
                //torns++;
            }
            else if(jugador==2 && t.movimentsPossibles(2)){
              //  time1_start = System.currentTimeMillis();
                moviment=maquina.seleccioAlgorisme(t);
              //  time1_end = System.currentTimeMillis();
               // total1_time += ( time1_end - time1_start );
                f=t.taulell[moviment.getActual().getFila()][moviment.getActual().getColumna()];
                t.mouFitxa(f,moviment.getSeguent());
                t.printBoard();
                System.out.println("\n Nou moviment de la IA\n");
                //torns++;

            }
            canviJugador();
        }
        //System.out.println("\n\n Temps mig jugades");
        return t.guanyador();
    }

    public static int IAvsIA(Taulell t, int algorisme1, int algorisme2, int h1, int h2) {
        AlgorismesJoc maquina=new AlgorismesJoc(1, h1, algorisme1);
        AlgorismesJoc maquina2=new AlgorismesJoc(2, h2, algorisme2);
        int jugades1=0,jugades2=0;

        jugador=1;
        Fitxa f;
        Moviment moviment=new Moviment();
        while(t.movimentsPossibles(1) || t.movimentsPossibles(2)){    //hauria de ser un while(jug1.movPossibles!=0 || jug2.movPossibles!=0)
            if(jugador==1 && t.movimentsPossibles(1)){
                time1_start = System.currentTimeMillis();
                moviment=maquina.seleccioAlgorisme(t);
                time1_end = System.currentTimeMillis();
                total1_time += ( time1_end - time1_start );
                f=t.taulell[moviment.getActual().getFila()][moviment.getActual().getColumna()];
                t.mouFitxa(f,moviment.getSeguent());
                t.printBoard();
                jugades1++;
            }
            else if(jugador==2 && t.movimentsPossibles(2)){
                time2_start = System.currentTimeMillis();
                moviment=maquina2.seleccioAlgorisme(t);
                time2_end = System.currentTimeMillis();
                total2_time += ( time2_end - time2_start );
                f=t.taulell[moviment.getActual().getFila()][moviment.getActual().getColumna()];
                t.mouFitxa(f,moviment.getSeguent());
                t.printBoard();
                jugades2++;
            }
            canviJugador();
        }
        System.out.println("\n\n El primer algorisme ha trigat una mitja de: "+(float)((float)total1_time/(float)(1000*jugades1))+" s/jugada");
        System.out.println("\n\n El segon algorisme ha trigat una mitja de: "+(float)((float)total2_time/(float)(1000*jugades2))+" s/jugada");

        return t.guanyador();
    }

    public static void canviJugador(){
        if(jugador==1){
            jugador=2;
        }
        else{
            jugador=1;
        }
    }

    public static void main(String[] args) {
        jugador=1;
        Taulell t=new Taulell();
        t.setInici();
        t.printBoard();

        //int guanyador=jugadorVsIA(t,1, 1);
        int guanyador=IAvsIA(t,1,2,3,3);
        if(guanyador==0){
            System.out.println(ANSI_BLUE+"\nEMP"+ANSI_RED+"AT!"+ANSI_RESET);
        }
        else if(guanyador==1){
            System.out.println(ANSI_BLUE+"\nGUANYA EL JUGADOR BLAU!!"+ANSI_RESET);
        }
        else{
            System.out.println(ANSI_RED+"\nGUANYA EL JUGADOR VERMELL!!"+ANSI_RESET);
        }
        //A6-D / A6-E
    }
}



