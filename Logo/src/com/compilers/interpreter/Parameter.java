package com.compilers.interpreter;

public class Parameter {

    private String name;
    private Object obj;

    public Parameter(String name, Object obj){
        this.name = name;
        this.obj = obj;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String nombre) {
        this.name = nombre;
    }

    public Object getObject() {
        return obj;
    }

    public void setObject(Object objeto) {
        this.obj = objeto;
    }
        
}
