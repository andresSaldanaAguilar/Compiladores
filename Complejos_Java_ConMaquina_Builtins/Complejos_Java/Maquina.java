import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Stack;

class Maquina{

    public final static short CNUMBER=257;
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
    SymbolTable symbolTable;

    void initcode(){
        pila = new Stack();
        prog = new ArrayList();
        symbolTable = new SymbolTable();
        init();
    }

    void init() { 
        /* Function init */
        String[] funcNames = {"exp", "sin", "cos"};
        for (int i = 0; i < funcNames.length; i++) {
            symbolTable.install(funcNames[i], null, (short) 2);  
        }
    }

    int code(Object f){
        prog.add(f);
        return prog.size()-1;
    }

    Object pop(){
        return pila.pop();
    }

    void cnumber() {
        Complejo c = (Complejo) prog.get(pc);
        pc = pc + 1;                                 // Extra increment in pc.
        pila.push(c);                                // A Complejo is pushed in the stack.
    }

    void getvar() {
        Cadena varname  = (Cadena) pila.pop();          // Conseguimos nombre de variable
        Symbol s    = symbolTable.lookUpTable(varname.getCadena()); //necesario por ser un objeto
        if (s == null) 
          System.err.println("Undefined variable");
        else 
          pila.push(s.getData());
    }

    void setvar() {
        Cadena varname   = (Cadena)    pila.pop(); //conseguimos nombre de variable      // Get data from stack (symbol).         
        Complejo c  = (Complejo)  pila.pop(); //conseguimos su valor
        Symbol s = symbolTable.lookUpTable(varname.getCadena());
        if (s == null){
            symbolTable.install(varname.getCadena(),c,(short) 1);
        }   
        else 
            System.err.println("Variable name already defined");
    }

    void varpush(){
        Cadena c = (Cadena) prog.get(pc); //conseguimos nombre de var
        pc = pc + 1; //aumentamos pc                              
        pila.push(c); //la metemos en pila                        
    }

    void eval(){
        Symbol s;
        s = (Symbol)pila.pop();
        if(s.type == INDEF)
        System.out.println("var not defined");
        pila.push(new Complejo(s.data.getReal(),s.data.getImg()));
    }

    void stringpush (){
        Cadena c = (Cadena) prog.get(pc);
        pc = pc + 1;
        pila.push(c);      
    }

    void bltin(){
        Cadena sym  = (Cadena) pila.pop();
        Symbol s    = symbolTable.lookUpTable(sym.getCadena());

        Complejo c  = (Complejo)  pila.pop();
        /*Invocacion*/
        String fName    = s.getName();                                    
        Complejo res    = new Complejo(0, 0);
        Functions f     = new Functions(); 
        Class cl        = f.getClass();
        Class[] cArg    = new Class[1];
        cArg[0]         = res.getClass();    
        Object param[]  = new Object[1];

        /* Obteniendo el metodo */
        try {
            Method method   = cl.getMethod(fName, cArg);      
            param[0]        = c;
            res             = (Complejo) method.invoke(f, param);
        } catch (NoSuchMethodException ex) {
        } catch (IllegalAccessException ex) {   
        } catch (InvocationTargetException ex) {
        }
        
        /* resultado a la pila */
        pila.push(res);
    }

    void assign(){
        Symbol s;
        Complejo c2;
        s=(Symbol)pila.pop();
        c2=(Complejo)pila.pop();
          
        if(s.type != VAR && s.type != INDEF)
            System.out.println("asignacion a una no variable "+s.name);
        s.data = c2;
        s.type= VAR;
        pila.push(new Complejo(s.data.getReal(),s.data.getImg()));
    }

    void add() {
        Complejo c2 = ((Complejo)pila.pop());
        Complejo c1 = ((Complejo)pila.pop());     
        pila.push(new Complejo(c1.getReal() + c2.getReal(),c1.getImg() + c2.getImg()));
    }

    void sub() {
        Complejo c2 = ((Complejo)pila.pop());
        Complejo c1 = ((Complejo)pila.pop());     
        pila.push(new Complejo(c1.getReal() - c2.getReal(),c1.getImg() - c2.getImg()));
    }

    void div() {
        Complejo c2 = ((Complejo)pila.pop());
        Complejo c1 = ((Complejo)pila.pop()); 
        float d = (float) ( (c2.getReal() * c2.getReal()) + (c2.getImg() * c2.getImg()) );
        Complejo res = new Complejo((float) (c1.getReal() * c2.getReal() + c1.getImg() * c2.getImg()) / d,
        (float) (c1.getImg() * c2.getReal() - c1.getReal() * c2.getImg()) / d );   
        pila.push(res);
    }  
    
    void mul(){
        Complejo c2 = ((Complejo)pila.pop());
        Complejo c1 = ((Complejo)pila.pop()); 
        Complejo res = new Complejo((c1.getReal() * c2.getReal()) - (c1.getImg() * c2.getImg()),
        (c1.getImg() * c2.getReal()) + (c1.getReal() * c2.getImg()) );
        pila.push(res);
    }

    void pow(){
        Functions f = new Functions();
        Complejo base = ((Complejo)pila.pop());
        Complejo c1 = ((Complejo)pila.pop()); 
        Complejo res = f.pow(c1, base);
        pila.push(res);
    }

    void imprimeComplejo() {
        Complejo c = (Complejo) pila.pop();
        System.out.println("Result: ");
        c.imprimeComplejo();
    }

    void execute(boolean delete) {
        /* First delete previous code */    
        if (delete) { 
          int limit = prog.indexOf("STOP");        
          for (int i = 0; i <= limit; i++)    // Remove until limit is reached.
            prog.remove(0);
        }
      
        String inst;
        System.out.println(".:: Generated Program ::. ");
        System.out.println("Program Size: = " + prog.size());
        for (pc = 0; pc < prog.size(); pc = pc + 1) {
            System.out.println("PC = " + pc + ". Instruction: " + prog.get(pc));
        }
      
            pc = 0;      
        while (true) {         
                try {          
            inst = (String) prog.get(pc);         // We get the instruction at pc.
            if (inst.equals("STOP"))                    // The whole program has been executed.
              break;
            
            pc = pc + 1;                                // Normal Increment.         
            c = this.getClass();
            metodo = c.getDeclaredMethod(inst, null);   // Get method of this class.
            metodo.invoke(this, null);                  
                } catch(NoSuchMethodException e){
                    System.out.println("No method " + e);
          } catch(InvocationTargetException e){
                    e.printStackTrace();
          } catch(IllegalAccessException e){
                    System.out.println(e);
          }
          }
    } 
}