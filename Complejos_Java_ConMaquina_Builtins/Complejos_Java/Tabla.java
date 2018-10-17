public class Tabla {
   Symbol listaSimbolo;
   
   Tabla(){
      listaSimbolo=null;
   }

    Symbol install(String name, Complejo data, short type) {
        Symbol simb = new Symbol(name, type, data);
        simb.ponSig(listaSymbol);
        listaSymbol=simb;
        return simb;
    }

    Symbol lookup(String s){
        for(Symbol sp=listaSymbol; sp!=null; sp=sp.obtenSig())
            if((sp.getName()).equals(s))
	            return sp;
        return null;
   }
}
