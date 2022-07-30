public class Mapa {
    private int[][] mapa;
    private int files;
    private int columnes;

    public Mapa(int fil, int col) {
        files = fil;
        columnes = col;
        mapa = new int[fil][col];
    }


    public void donarFormat(String[] linies) {  //crea el mapa des de la llista d'strings que teniem inicialment
        String[] dades;
        int compt = 0;
        while (compt < linies.length) {
            dades = linies[compt].split(" ");
            for (int i = 0; i < (dades.length); i++) {
                mapa[compt][i] = Integer.parseInt(dades[i]);
            }
            compt++;
        }
    }

    public void mostraMapa(){   //imprimeix el mapa per pantalla
        for(int i=0;i<files;i++){
            for(int j=0;j<columnes;j++){
                if(mapa[i][j]==-1)System.out.print("  ");  //simulem els precipicis amb espais en blanc
                else System.out.print(mapa[i][j]+" ");
            }
            System.out.print("\n");
        }
    }

    public int getValor(int fila, int col){
        return mapa[fila][col];
    }

    public int getFiles(){return files;}

    public int getColumnes(){return columnes;}

}
