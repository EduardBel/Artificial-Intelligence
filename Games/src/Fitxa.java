public class Fitxa {
    private int fila;
    private int columna;
    private int color;

    public Fitxa(int f, int c, int color){
        fila=f;
        columna=c;
        this.color=color;
    }
    public Fitxa(){
        this.color=0;
    }

    public Fitxa(int color){
        this.color=color;
    }

    public int getColor(){
        return this.color;
    }

    public int getColorContrari(){
        if(color==1)return 2;
        else return 1;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }
}
