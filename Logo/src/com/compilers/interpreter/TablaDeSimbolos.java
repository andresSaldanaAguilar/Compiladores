package com.compilers.interpreter;

import java.util.ArrayList;

public class TablaDeSimbolos {

    ArrayList<Parameter> symbols;
    
    public TablaDeSimbolos(){
        symbols = new ArrayList<>();
    }
    
    public Object encontrar(String name){
        for(int i = 0; i < symbols.size(); i++)
            if(name.equals(symbols.get(i).getName()))
                return symbols.get(i).getObject();
        return null;
    }
    
    public boolean insertar(String name, Object objeto){
        Parameter par = new Parameter(name, objeto);
        for(int i = 0; i < symbols.size(); i++)
            if(name.equals(symbols.get(i).getName())){
                symbols.get(i).setObject(objeto);
                return true;
            }
        symbols.add(par);
        return false;
    }
    
    public void consolePrint(){
        for(int i = 0; i < symbols.size(); i++){
            System.out.println(symbols.get(i).getName() + symbols.get(i).getObject().toString());
        }
    }

}