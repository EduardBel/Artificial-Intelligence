public class Estat {
    private int fila;
    private int columna;

    public Estat(int fil, int col){
        fila=fil;
        columna=col;
    }
    public int getFila(){return fila;}

    public int getColumna(){return columna;}

    public boolean equals(Estat est){   //comprova si dos estats són iguals
        return(this.fila==est.fila && this.columna==est.columna);
    }

    public int getAlcada(Mapa mapa){
        return mapa.getValor(fila, columna);
    }

    //en cas que l'estat actual se surti del mapa o sigui un precipici, serà un estat on no mourem.
    public boolean esCorrecte(Mapa mapa){
        if(fila<0 || columna<0 || fila>=mapa.getFiles() || columna>= mapa.getColumnes() || mapa.getValor(fila, columna)==-1){
            return false;
        }
        else return true;
    }
}
