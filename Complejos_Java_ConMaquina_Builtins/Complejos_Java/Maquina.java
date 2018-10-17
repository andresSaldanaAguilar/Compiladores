import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Stack;

import sun.reflect.generics.scope.MethodScope;
class Maquina{

    public final static short NUMBER=257;
    public final static short PRINT=258;
    public final static short VAR=259;
    public final static short BLTIN=260;
    public final static short INDEF=261;

    Stack pila;
    ArrayList prog; //RAM
    static int pc = 0; //contador del programa
    int progbase = 0;
    boolean returning = false;
    Method metodo;
    Method metodos[];
    Class c;
    Class parames[];

    void initcode(){
        pila = new Stack();
        prog = new ArrayList();
    }
    int code(Object f){
        prog.add(f);
        return prog.size()-1;
    }

    void execute(int p){
        String inst;
        for(pc = p; ! (inst=(String)prog.get(pc)).equals("STOP"); ){
            try{
                inst = (String)prog.get(pc);
                pc = pc+1;
                c = this.getClass();
                metodo = c.getDeclaredMethod(inst, null);
                metodo.invoke(this, null);
            }
            catch(NoSuchMethodException e){}
            catch(InvocationTargetException e ){}
            catch(IllegalAccessException e){}          
        }
    }

    void constpush(){
        Symbol s;
        s = (Symbol)prog.get(pc);
        pc = pc+1;
        pila.push(new Complejo(s.data.getReal(),s.data.getImg()));
    }

    void varpush(){
        Symbol s;
        s = (Symbol)prog.get(pc);
        pc = pc+1;
        pila.push(s);
    }

    public void sumaComplejos() {
        Complejo c2 = ((Complejo)pila.pop());
        Complejo c1 = ((Complejo)pila.pop());     
        pila.push(new Complejo(c1.getReal() + c2.getReal(),c1.getImg() + c2.getImg()));
    }

    void eval(){
        Symbol s;
        s = (Symbol)pila.pop();
        if(s.type == INDEF)
            System.out.println("var not defined");
        pila.push(new Complejo(s.data.getReal(),s.data.getImg()));
    }

    void bltin(){
        Complejo d;
        String inst;
        Class c;
        Complejo ret;
    
        Class paramC[]={Complejo.class}; 
        Object param[]=new Object[1];
    
        d=(Complejo)pila.pop();
        param[0]=new Complejo(d.getReal(),d.getImg());
        inst=((Symbol)prog.get(pc)).name;
        c=java.lang.Math.class; //wut
        try {
            metodo=c.getDeclaredMethod(inst, paramC);
            ret=(Complejo)metodo.invoke(this, param);
            pila.push(ret);
        }
        catch(NoSuchMethodException e){
            System.out.println("Non existant "+e+ ",inst "+inst);
        }
        catch(InvocationTargetException e){
            System.out.println(e);
        }
        catch(IllegalAccessException e){
            System.out.println(e);
        }
        pc=pc+1;
    }
    
}