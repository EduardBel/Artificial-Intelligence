import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Taulell {
    public Fitxa [][] taulell;
    private final int files;
    private final int columnes;
    private int quiJuga;
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";

    public Taulell(){
        files=8;
        columnes=8;
        taulell= new Fitxa[8][8];
        quiJuga=1;
    }
    public int lletraANum(char c){
        int ret;
        switch (c){
            case 'a'|'A' : ret= 0;
                break;
            case 'b'|'B' : ret= 1;
                break;
            case 'c'|'C' : ret= 2;
                break;
            case 'd'|'D' : ret= 3;
                break;
            case 'e'|'E' : ret= 4;
                break;
            case 'f'|'F' : ret= 5;
                break;
            case 'g'|'G' : ret= 6;
                break;
            case 'h'|'H' : ret= 7;
                break;
            default : ret=-1;
        }
        return ret;
    }

    public void setQuiJuga(int quiJuga) {
        this.quiJuga = quiJuga;
    }

    public Fitxa getFitxa(int f, int c){
        if(f>7 | f<0 | c<0 | c>7){
            return new Fitxa(-1);
        }
        else{
            return taulell[f][c];
        }
    }

    public int getQuiJuga(){
        return this.quiJuga;
    }

    public void canviTorn(){
        if(quiJuga==1) quiJuga=2;
        else quiJuga=1;
    }

    public boolean movimentsPossibles(int jugador){
        boolean possible=false;
        Fitxa f=new Fitxa();    //fitxa buida
        if(jugador==1) { //blaves
            for (int i = 0; i < 7 && !possible; i++) {  //7 PERQUE NO SURTI PER LA PART DE BAIX
                for (int j = 0; j < 8; j++) {
                    if (taulell[i][j].getColor() == 1) {    //si trobem una sola fitxa amb moviment possible hem acabat
                        f = taulell[i][j];
                        try {
                            if (taulell[f.getFila() + 1][f.getColumna() + 1].getColor() == 0) { //esquerra
                                possible = true;
                            }
                        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                        }
                        try {
                            if (taulell[f.getFila() + 1][f.getColumna() - 1].getColor() == 0) {    //dreta
                                possible = true;
                            }
                        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                        }
                        try {
                            if (taulell[f.getFila() + 1][f.getColumna() + 1].getColor() == 2 && taulell[f.getFila() + 2][f.getColumna() + 2].getColor() == 0) {  //mata esquerra
                                possible = true;
                            }
                        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                        }
                        try {
                            if (taulell[f.getFila() + 1][f.getColumna() - 1].getColor() == 2 && taulell[f.getFila() + 2][f.getColumna() - 2].getColor() == 0) {   //mata dreta
                                possible = true;
                            }
                        } catch (java.lang.ArrayIndexOutOfBoundsException e) {}
                    }
                }
            }
        }
        else{   //vermelles
            for(int i=1;i<8 && !possible;i++){  //i=1 PERQUE NO SURTI PER LA PART DE DALT
                for(int j=0;j<8;j++){
                    if(taulell[i][j].getColor()==2){    //si trobem una sola fitxa amb moviment possible hem acabat
                        f=taulell[i][j];
                        try{
                            if(taulell[f.getFila()-1][f.getColumna()-1].getColor()==0){ //esquerra
                                possible=true;
                            }
                        }
                        catch (java.lang.ArrayIndexOutOfBoundsException e) {}
                        try{
                            if(taulell[f.getFila()-1][f.getColumna()+1].getColor()==0){    //dreta
                                possible=true;
                            }
                        }
                        catch (java.lang.ArrayIndexOutOfBoundsException e) {}
                        try{
                            if(taulell[f.getFila()-1][f.getColumna()-1].getColor()==1 && taulell[f.getFila()-2][f.getColumna()-2].getColor()==0){  //mata esquerra
                                possible=true;
                            }
                        }
                        catch (java.lang.ArrayIndexOutOfBoundsException e) {}
                        try{
                            if(taulell[f.getFila()-1][f.getColumna()+1].getColor()==1 && taulell[f.getFila()-2][f.getColumna()+2].getColor()==0) {   //mata dreta
                                possible=true;
                            }
                        }
                        catch (java.lang.ArrayIndexOutOfBoundsException e) {}
                    }
                }
            }
        }
        return possible;
    }

    public Taulell clonar(){
        Taulell t=new Taulell();
        for(int i=0;i<files;i++){
            for(int j=0;j<columnes;j++){
                t.taulell[i][j]=taulell[i][j];
            }
        }
        return t;
    }

    public List<Moviment> movimentsPossiblesALlista(int jugador){
        List<Moviment> mov=new ArrayList<>();
        Fitxa f=new Fitxa();    //fitxa buida
        if(jugador==1) { //blaves
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (taulell[i][j].getColor() == 1) {
                        f = taulell[i][j];
                        try {
                            if (taulell[f.getFila() + 1][f.getColumna() + 1].getColor() == 0) { //esquerra
                                mov.add(new Moviment(new Posicio(f.getFila(),f.getColumna()),new Posicio(f.getFila()+1,f.getColumna()+1)));
                            }
                        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                        }
                        try {
                            if (taulell[f.getFila() + 1][f.getColumna() - 1].getColor() == 0) {    //dreta
                                mov.add(new Moviment(new Posicio(f.getFila(),f.getColumna()),new Posicio(f.getFila()+1,f.getColumna()-1)));
                            }
                        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                        }
                        try {
                            if (taulell[f.getFila() + 1][f.getColumna() + 1].getColor() == 2 && taulell[f.getFila() + 2][f.getColumna() + 2].getColor() == 0) {  //mata esquerra
                                mov.add(new Moviment(new Posicio(f.getFila(),f.getColumna()),new Posicio(f.getFila()+2,f.getColumna()+2)));
                            }
                        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                        }
                        try {
                            if (taulell[f.getFila() + 1][f.getColumna() - 1].getColor() == 2 && taulell[f.getFila() + 2][f.getColumna() - 2].getColor() == 0) {   //mata dreta
                                mov.add(new Moviment(new Posicio(f.getFila(),f.getColumna()),new Posicio(f.getFila()+2,f.getColumna()-2)));
                            }
                        } catch (java.lang.ArrayIndexOutOfBoundsException e) {}
                    }
                }
            }
        }
        else{   //vermelles
            for(int i=0;i<8;i++){  //i=1 PERQUE NO SURTI PER LA PART DE DALT
                for(int j=0;j<8;j++){
                    if(taulell[i][j].getColor()==2){    //si trobem una sola fitxa amb moviment possible hem acabat
                        f=taulell[i][j];
                        try{
                            if(taulell[f.getFila()-1][f.getColumna()-1].getColor()==0){ //esquerra
                                mov.add(new Moviment(new Posicio(f.getFila(),f.getColumna()),new Posicio(f.getFila()-1,f.getColumna()-1)));
                            }
                        }
                        catch (java.lang.ArrayIndexOutOfBoundsException e) {}
                        try{
                            if(taulell[f.getFila()-1][f.getColumna()+1].getColor()==0){    //dreta
                                mov.add(new Moviment(new Posicio(f.getFila(),f.getColumna()),new Posicio(f.getFila()-1,f.getColumna()+1)));
                            }
                        }
                        catch (java.lang.ArrayIndexOutOfBoundsException e) {}
                        try{
                            if(taulell[f.getFila()-1][f.getColumna()-1].getColor()==1 && taulell[f.getFila()-2][f.getColumna()-2].getColor()==0){  //mata esquerra
                                mov.add(new Moviment(new Posicio(f.getFila(),f.getColumna()),new Posicio(f.getFila()-2,f.getColumna()-2)));
                            }
                        }
                        catch (java.lang.ArrayIndexOutOfBoundsException e) {}
                        try{
                            if(taulell[f.getFila()-1][f.getColumna()+1].getColor()==1 && taulell[f.getFila()-2][f.getColumna()+2].getColor()==0) {   //mata dreta
                                mov.add(new Moviment(new Posicio(f.getFila(),f.getColumna()),new Posicio(f.getFila()-2,f.getColumna()+2)));
                            }
                        }
                        catch (java.lang.ArrayIndexOutOfBoundsException e) {}
                    }
                }
            }
        }
        return mov;
    }

    public List<Posicio> movimentsPossiblesFitxa(int fil, int c){
        List<Posicio> llista=new ArrayList<>();
        Fitxa f = taulell[fil][c];    //fitxa buida

        if(taulell[fil][c].getColor()==1){    //blau
            try {
                if (taulell[f.getFila() + 1][f.getColumna() + 1].getColor() == 0) { //esquerra
                    llista.add(new Posicio(f.getFila()+1,f.getColumna()+1));
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            }
            try {
                if (taulell[f.getFila() + 1][f.getColumna() - 1].getColor() == 0) {    //dreta
                    llista.add(new Posicio(f.getFila()+1,f.getColumna()-1));
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            }
            try {
                if (taulell[f.getFila() + 1][f.getColumna() + 1].getColor() == 2 && taulell[f.getFila() + 2][f.getColumna() + 2].getColor() == 0) {  //mata esquerra
                    llista.add(new Posicio(f.getFila()+2,f.getColumna()+2));
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            }
            try {
                if (taulell[f.getFila() + 1][f.getColumna() - 1].getColor() == 2 && taulell[f.getFila() + 2][f.getColumna() - 2].getColor() == 0) {   //mata dreta
                    llista.add(new Posicio(f.getFila()+2,f.getColumna()-2));
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException e) {}
        }
        else{   //vermell
            try{
                if(taulell[f.getFila()-1][f.getColumna()-1].getColor()==0){ //esquerra
                    llista.add(new Posicio(f.getFila()-1,f.getColumna()-1));
                }
            }
            catch (java.lang.ArrayIndexOutOfBoundsException e) {}
            try{
                if(taulell[f.getFila()-1][f.getColumna()+1].getColor()==0){    //dreta
                    llista.add(new Posicio(f.getFila()-1,f.getColumna()+1));
                }
            }
            catch (java.lang.ArrayIndexOutOfBoundsException e) {}
            try{
                if(taulell[f.getFila()-1][f.getColumna()-1].getColor()==1 && taulell[f.getFila()-2][f.getColumna()-2].getColor()==0){  //mata esquerra
                    llista.add(new Posicio(f.getFila()-2,f.getColumna()-2));
                }
            }
            catch (java.lang.ArrayIndexOutOfBoundsException e) {}
            try{
                if(taulell[f.getFila()-1][f.getColumna()+1].getColor()==1 && taulell[f.getFila()-2][f.getColumna()+2].getColor()==0) {   //mata dreta
                    llista.add(new Posicio(f.getFila()-2,f.getColumna()+2));
                }
            }
            catch (java.lang.ArrayIndexOutOfBoundsException e) {}
        }
        return llista;
    }

    public void mouFitxa(Fitxa f, Posicio p){   //quan entra aqui és un moviment legal
        if(Math.abs(p.getFila()-f.getFila())==2) {                            //si dif entre files (valor absolut) de fitxa i posicio és 2, s'ha de matar a la fitxa adversaria(posar fitxa buida a aquella posició)
            taulell[f.getFila()][f.getColumna()]=new Fitxa();
            taulell[p.getFila()][p.getColumna()]=new Fitxa(p.getFila(), p.getColumna(), f.getColor());
            if(f.getColor()==1){
                if(p.getColumna()<f.getColumna()){  //ha saltat a la dreta
                    taulell[f.getFila()+1][f.getColumna()-1]=new Fitxa();   //casella buida
                }
                else{   //ha saltat a l'esquerra
                    taulell[f.getFila()+1][f.getColumna()+1]=new Fitxa();
                }
            }
            else{   //juguen les vermelles
                if(p.getColumna()>f.getColumna()){  //ha saltat a la dreta
                    taulell[f.getFila()-1][f.getColumna()+1]=new Fitxa();   //casella buida
                }
                else{   //ha saltat a l'esquerra
                    taulell[f.getFila()-1][f.getColumna()-1]=new Fitxa();
                }
            }
        }
        else{
            taulell[f.getFila()][f.getColumna()]=new Fitxa();
            taulell[p.getFila()][p.getColumna()]=new Fitxa(p.getFila(), p.getColumna(), f.getColor());
        }
       // if(quiJuga==1) quiJuga=2;   //a qui li toca jugar
       // else{quiJuga=1;}
    }

    public boolean movimentLegal(Fitxa f, Posicio p){   //falta comprovar si hi ha altres fitxes enemigues pel cami
        if(p.getFila()>7|p.getFila()<0|p.getColumna()>7|p.getColumna()<0){
            return false;
        }
        else if(getFitxa(p.getFila(), p.getColumna()).getColor()!=0) {    //comprovem que no salti a una casella ocupada per una fitxa del mateix color o del contrari
            return false;
        }
        else{
            return true;
        }
    }
    public void setInici(){
        int i, j;
        for(i=0;i<3;i=i+2){
            for(j=0;j<8;j++){
                if(j%2==1){
                    taulell[i][j]=new Fitxa(i,j,1);
                }
                else{
                    taulell[i][j]=new Fitxa();  //espai buit
                }
            }
        }
        i=1;
        for(j=0;j<8;j++){
            if(j%2==0){
                taulell[i][j]=new Fitxa(i,j,1);
            }
            else{
                taulell[i][j]=new Fitxa();  //espai buit
            }
        }
        for(i=3;i<5;i++){
            for(j=0;j<8;j++){
                taulell[i][j]=new Fitxa();  //espai buit
            }
        }
        for(i=5;i<8;i=i+2){
            for(j=0;j<8;j++){
                if(j%2==0){
                    taulell[i][j]=new Fitxa(i,j,2);
                }
                else{
                    taulell[i][j]=new Fitxa();  //espai buit
                }
            }
        }
        i=6;
        for(j=0;j<8;j++){
            if(j%2==1){
                taulell[i][j]=new Fitxa(i,j,2);
            }
            else{
                taulell[i][j]=new Fitxa();  //espai buit
            }
        }


    }

    public void printBoard()  {

        String back, espaiBuit;
        int cont=0;

        //comptem el total de dames blaves
        int damesB=0;
        for(int c=0;c<8;c++){
            if(getFitxa(7, c).getColor()==1) damesB++;
        }
        //comptem el total de dames vermelles
        int damesV=0;
        for(int c=0;c<8;c++){
            if(getFitxa(0, c).getColor()==2) damesV++;
        }

        System.out.println("\nDames"+ANSI_BLUE+" BLAVES: "+ANSI_RESET+damesB);
        System.out.println("Dames"+ANSI_RED+" VERMELLES: "+ANSI_RESET+damesV);


        System.out.println("\t A\t B\t C\t D\t E\t F\t G\t H");
        System.out.println("    --------------------------------");
        for( int row = 0; row < columnes; row++ ){
            System.out.print(  "  "+(row)+ "|");

            for( int col = 0; col < files; col++ ){
                if((cont/8)%2==0) {
                    if (cont % 2 == 1) {
                        back = ANSI_WHITE_BACKGROUND;
                        espaiBuit = ANSI_WHITE;
                    } else {
                        back = ANSI_BLACK_BACKGROUND;
                        espaiBuit = ANSI_BLACK;
                    }
                }
                else{

                    if (cont % 2 == 0) {
                        back = ANSI_WHITE_BACKGROUND;
                        espaiBuit = ANSI_WHITE;
                    } else {
                        back = ANSI_BLACK_BACKGROUND;
                        espaiBuit = ANSI_BLACK;
                    }

                }
                if(taulell[row][col].getColor()==1){
                    System.out.print(""+ANSI_BLUE+back+ " ■\t" + ""+ANSI_RESET );
                }
                else if(taulell[row][col].getColor()==2){
                    System.out.print(""+ANSI_RED+back+ " ■\t" + ""+ANSI_RESET );
                }
                else{
                    System.out.print( ""+back+espaiBuit+ "■\t" + ""+ANSI_RESET );
                }
                cont++;
            }
            System.out.println( "|"+ (row) );
        }
         System.out.println("    --------------------------------");
        System.out.print("\t A\t B\t C\t D\t E\t F\t G\t H");
    }


    public int guanyador(){
        int damesB=0;
        for(int c=0;c<8;c++){
            if(getFitxa(7, c).getColor()==1) damesB++;
        }
        //comptem el total de dames vermelles
        int damesV=0;
        for(int c=0;c<8;c++){
            if(getFitxa(0, c).getColor()==2) damesV++;
        }
        if(damesB==damesV) {
            for(int c=0;c<8;c++){
                if(getFitxa(6, c).getColor()==1) damesB++;
            }
            for(int c=0;c<8;c++){
                if(getFitxa(1, c).getColor()==2) damesV++;
            }
        }
        if(damesB==damesV) {
            for(int c=0;c<8;c++){
                if(getFitxa(5, c).getColor()==1) damesB++;
            }
            for(int c=0;c<8;c++){
                if(getFitxa(2, c).getColor()==2) damesV++;
            }
        }
        if(damesB==damesV) {
            for(int c=0;c<8;c++){
                if(getFitxa(4, c).getColor()==1) damesB++;
            }
            for(int c=0;c<8;c++){
                if(getFitxa(3, c).getColor()==2) damesV++;
            }
        }
        if(damesB==damesV) {
            for(int c=0;c<8;c++){
                if(getFitxa(3, c).getColor()==1) damesB++;
            }
            for(int c=0;c<8;c++){
                if(getFitxa(4, c).getColor()==2) damesV++;
            }
        }
        if(damesB==damesV) {
            for(int c=0;c<8;c++){
                if(getFitxa(2, c).getColor()==1) damesB++;
            }
            for(int c=0;c<8;c++){
                if(getFitxa(5, c).getColor()==2) damesV++;
            }
        }
        if(damesB==damesV) {
            for(int c=0;c<8;c++){
                if(getFitxa(1, c).getColor()==1) damesB++;
            }
            for(int c=0;c<8;c++){
                if(getFitxa(6, c).getColor()==2) damesV++;
            }
        }
        if(damesB==damesV) {
            for(int c=0;c<8;c++){
                if(getFitxa(0, c).getColor()==1) damesB++;
            }
            for(int c=0;c<8;c++){
                if(getFitxa(7, c).getColor()==2) damesV++;
            }
        }
        if(damesB==damesV) return 0;
        else if(damesB<damesV)return 2;
        else return 1;
    }

    public int totalDames(int color){
        int cont=0;
        if(color==1){
            for(int j=0;j<8;j++){
                if(taulell[7][j].getColor()==1){
                    cont++;
                }
            }
        }
        else{
            for(int j=0;j<8;j++){
                if(taulell[0][j].getColor()==2){
                    cont++;
                }
            }
        }

        return cont;
    }

    public int totalFitxes(int color){
        int cont=0;
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(taulell[i][j].getColor()==color){
                    cont++;
                }
            }
        }
        return cont;
    }

    public int fitxesAvencades(int color){
        int valor=1;
        int cont=0;
        if(color==1){   //el valor va augmentant ja que les fitxes avançen cap a "baix"
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    if(taulell[i][j].getColor()==1){
                        cont+=valor;
                    }
                }
                valor++;
            }
        }
        else{   //el valor va disminuint per cada fila que baixem(tenen menys "valor" per les vermelles
            valor=8;
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    if(taulell[i][j].getColor()==2){
                        cont+=valor;
                    }
                }
                valor--;
            }
        }
        return cont;
    }

    public List<Posicio> getFitxes(int jugador){
        List<Posicio> llista=new ArrayList<>();
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(taulell[i][j].getColor()==jugador){
                    llista.add(new Posicio(i,j));
                }
            }
        }
        return llista;
    }

    @Override
    public String toString() {
        return "Taulell{" +
                "taulell=" + Arrays.toString(taulell) +
                '}';
    }
}
