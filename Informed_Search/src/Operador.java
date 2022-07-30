public enum Operador{
    UP{
        @Override
        public Estat mou(Estat est){
            return(new Estat(est.getFila()-1,est.getColumna()));
        }
    },
    DOWN{
        @Override
        public Estat mou(Estat est){
            return(new Estat(est.getFila()+1,est.getColumna()));
        }
    },
    LEFT{
        @Override
        public Estat mou(Estat est){
            return(new Estat(est.getFila(),est.getColumna()-1));
        }
    },
    RIGHT{
        @Override
        public Estat mou(Estat est){
            return(new Estat(est.getFila(),est.getColumna()+1));
        }
    };

    public abstract Estat mou(Estat est);
}