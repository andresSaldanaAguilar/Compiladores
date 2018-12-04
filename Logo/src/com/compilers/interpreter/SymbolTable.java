package com.compilers.interpreter;

import java.util.ArrayList;

public class SymbolTable {

    ArrayList<Parameter> symbols;
    
    public SymbolTable(){
        symbols = new ArrayList<>();
    }
    
    public Object find(String name){
        for(int i = 0; i < symbols.size(); i++)
            if(name.equals(symbols.get(i).getName()))
                return symbols.get(i).getObject();
        return null;
    }
    
    public boolean insert(String name, Object objeto){
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
