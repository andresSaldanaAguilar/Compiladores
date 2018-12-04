package com.compilers.interpreter;


import com.compilers.configuration.Configuracion;
import java.awt.Color;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Stack;
import com.compilers.configuration.Configuration;
import com.compilers.configuration.Line;

public class MaquinaDePila {
    
    private int programCounter;
    private final ArrayList  memory;
    private final Stack stack;
    private final TablaDeSimbolos table;
    private boolean stop = false;
    private boolean returning = false;
    private final Stack<Frame> frameStack;
    private final Configuracion actualConfiguration;
    
    public MaquinaDePila(TablaDeSimbolos table){
        actualConfiguration = new Configuracion();
        programCounter = 0;
        memory = new ArrayList<>();
        stack = new Stack();
        this.table = table;
        frameStack = new Stack();
    }
    
    public int numeroDeElementos(){
        return memory.size() + 1;
    }
    
    // Add new operation in memory
    public int agregarOperacion(String nombre){
        int posicion = memory.size();
        try{
            memory.add(this.getClass().getDeclaredMethod(nombre, null));
            return posicion;
        }
        catch(Exception e){
            System.out.println("Error al agregar operación " + nombre + ". ");
        }
        return -1;
    }
    
    public int agregar(Object objeto){
        int posicion = memory.size();
        memory.add(objeto);
        return posicion;
    }
    
    public void agregar(Object objeto, int posicion){
        memory.remove(posicion);
        memory.add(posicion, objeto);
    }
    
    public int agregarOperacionEn(String nombre, int posicion){
        try{
            memory.add(posicion, this.getClass().getDeclaredMethod(nombre, null));
        }
        catch(Exception e ){
            System.out.println("Error al agregar operación " + nombre + ". ");
        }
        return posicion;
    }
    
    // Basic stack machine functions
    private void SUM(){
        Object matriz2 = stack.pop();
        Object matriz1 = stack.pop();
		stack.push((double)matriz1 + (double)matriz2);
    }
    
    private void RES(){
        Object matriz2 = stack.pop();
        Object matriz1 = stack.pop();
		stack.push((double)matriz1 - (double)matriz2);
    }

    private void MUL(){
        Object matriz2 = stack.pop();
        Object matriz1 = stack.pop();
		stack.push((double)matriz1 * (double)matriz2);
    }
    
    private void negativo(){
        Object matriz1 = stack.pop();
        System.out.println(matriz1);
        stack.push(-(double)matriz1);
    }
    
    private void constPush(){
        stack.push(memory.get(++programCounter));
    }
    
    private void varPush(){
        stack.push(memory.get(++programCounter));
    }
    
    private void varPush_Eval(){
        stack.push(table.encontrar((String)memory.get(++programCounter)));
    }

    private void asignar(){
        String variable = (String)stack.pop();
        Object objeto = stack.pop();
        table.insertar(variable, objeto);
    }
    
    private void EQ(){
        Object A = stack.pop();
        Object B = stack.pop();
		stack.push((boolean)((double)A==(double)B));
    }

    private void NE(){
        Object A = stack.pop();
        Object B = stack.pop();
		stack.push((double)A!=(double)B);
    }

    private void LE(){
        double a;
        double b;
        Object B = stack.pop();
        Object A = stack.pop(); //Se sacan en orden inverso por la forma de la pila
		a = (double)A;
		b = (double)B;
        stack.push(a < b);
    }

    private void GR(){
        double a;
        double b;
        Object B = stack.pop();
        Object A = stack.pop(); //Se sacan en orden inverso por la forma de la pila
		a = (double)A;
		b = (double)B;
        stack.push(a > b);
    }

    private void LQ(){
        double a;
        double b;
        Object B = stack.pop();
        Object A = stack.pop(); //Se sacan en orden inverso por la forma de la pila
		a = (double)A;
		b = (double)B;
        stack.push(a <= b);
    }

    private void GE(){
        double a;
        double b;
        Object B = stack.pop();
        Object A = stack.pop(); //Se sacan en orden inverso por la forma de la pila
		a = (double)A;
		b = (double)B;
        stack.push(a >= b);
    }

    private void NOT(){
        stack.push(! (boolean)stack.pop());
    }

    private void AND(){
        stack.push((boolean)stack.pop() && (boolean)stack.pop());
    }

    private void OR(){
        stack.push((boolean)stack.pop() || (boolean)stack.pop());
    }
    
    private void stop(){
        stop = true;
    }
    
    private void _return(){
        returning = true;
    }

    private void nop(){
    }
    
    private void WHILE(){
        int condicion = programCounter;
        boolean continua = true;
        while(continua && !returning){
            ejecutar(condicion + 3);           
            if((boolean)stack.pop()){ //lee el resultado de la condición de la pila
                ejecutar((int)memory.get(condicion+1));//Ejecuta el cuerpo
            }
            else{
                programCounter = (int)memory.get(condicion+2); 
                continua = false;
            }
        }     
    }
    
    private void IF_ELSE(){
        int condicion = programCounter;
        ejecutar(condicion + 4); //Evalúa la condicion
        boolean resultado = true;
        try{
            resultado = (boolean)stack.pop();
        }
        catch(Exception e ){
        }
        if(resultado){ //lee el resultado de la condición de la pila
            ejecutar((int)memory.get(condicion+1));//Ejecuta el cuerpo
        }
        else{
            ejecutar((int)memory.get(condicion+2));
        }
        programCounter = (int)memory.get(condicion+3) - 1; //El -1 es para corregir el aumento del cp al final de cada instrucción
    }

    private void FOR(){
        int condicion = programCounter;
        ejecutar(condicion + 5);  // Ejecutamos la primera parte
        boolean continua = true;
        while(continua && !returning){
            ejecutar((int)memory.get(condicion+1)); //evaluamos la condición        
            if((boolean)stack.pop()){ //lee el resultado de la condición de la pila
                ejecutar((int)memory.get(condicion+3));//Ejecuta el cuerpo
                ejecutar((int)memory.get(condicion+2));//Ejecuta la última parte del for
            }
            else{
                programCounter = (int)memory.get(condicion+4); 
                continua = false;
            }
        } 
    }
    
    private void declaracion(){
        table.insertar((String)memory.get(++programCounter), ++programCounter); //Apuntamos a la primera instrucción de la función
        int invocados = 0;
        while(memory.get(programCounter) != null || invocados != 0){ //Llevamos cp hasta la siguiente instrucción después de la declaración
            if( memory.get(programCounter) instanceof Method)
                if(((Method)memory.get(programCounter)).getName().equals("invocar"))
                    invocados++;
            if( memory.get(programCounter) instanceof Function)
                invocados++;
            if(memory.get(programCounter) == null)
                invocados--;
            programCounter++;
        }
    }
    
    private void invocar(){   
        Frame marco = new Frame();
        String nombre = (String)memory.get(++programCounter);
        marco.setNombre(nombre);
        programCounter++;
        while(memory.get(programCounter) != null){ //Aquí también usamos null como delimitador. Aquí se agregan los parámetros al marco
            if(memory.get(programCounter) instanceof String){
                if(((String)(memory.get(programCounter))).equals("Limite")){
                    Object parametro = stack.pop();
                    marco.agregarParametro(parametro);
                    programCounter++;
                }
            }
            else{ 
                ejecutarInstruccion(programCounter);
            }

        }
        marco.setRetorno(programCounter);
        frameStack.add(marco);
        ejecutarFuncion((int)table.encontrar(nombre)); //VAMOS AQUI***************************
    }
    
    private void push_parametro(){
        stack.push(frameStack.lastElement().getParametro((int)memory.get(++programCounter)-1));
    }
    
    
    //Métodos para la ejecución
    public void imprimirMemoria(){
        for(int i = 0; i < memory.size(); i++)
            System.out.println("" + i + ": " +memory.get(i));
    }
    
    public void ejecutar(){
        //imprimirMemoria();
        stop = false;
        while(programCounter < memory.size())
            ejecutarInstruccion(programCounter);
    }
    
    public boolean ejecutarSiguiente(){
        //imprimirMemoria();
        if(programCounter < memory.size()){
            ejecutarInstruccion(programCounter);
            return true;
        }
        return false;
    }
    
    public void ejecutar(int indice){//ejecuta hasta que se encuentra Stop     
        programCounter = indice;
        while(!stop && !returning){
            ejecutarInstruccion(programCounter);
        }
        stop = false;
    }
    
    public void ejecutarFuncion(int indice){
        programCounter = indice;
        while(!returning && memory.get(programCounter) != null){
            ejecutarInstruccion(programCounter);
        }
        returning = false;
        programCounter = frameStack.lastElement().getRetorno();
        frameStack.removeElement(frameStack.lastElement());
    }
    
    public void ejecutarInstruccion(int indice){
        //System.out.println("Ejecutando: " + indice);
        try{         
            Object objetoLeido = memory.get(indice);
            if(objetoLeido instanceof Method){
                Method metodo = (Method)objetoLeido;
                metodo.invoke(this, null);
            }
            if(objetoLeido instanceof Function){
                ArrayList parametros = new ArrayList();
                Function funcion = (Function)objetoLeido;
                programCounter++;
                while(memory.get(programCounter) != null){ //Aquí también usamos null como delimitador. Aquí se agregan los parámetros al marco
                    if(memory.get(programCounter) instanceof String){
                        if(((String)(memory.get(programCounter))).equals("Limite")){
                            Object parametro = stack.pop();
                            parametros.add(parametro);
                            programCounter++;
                        }
                    }
                    else{ 
                        ejecutarInstruccion(programCounter);
                    }

                }
                funcion.ejecutar(actualConfiguration, parametros);
            }
            programCounter++;
        }
        catch(Exception e){}
    }
    
    public Configuracion getConfiguracion(){
        return actualConfiguration;
    }
    
    public static class Girar implements Function{
        @Override
        public void ejecutar(Object A, ArrayList parametros) {
            Configuration configuracion = (Configuration)A;
            int angulo = (configuracion.getAngle() + (int)(double)parametros.get(0))%360;
            configuracion.setAngle(angulo);
        }
    }
    
    public static class Avanzar implements Function{
        @Override
        public void ejecutar(Object A, ArrayList parametros) {
            Configuration configuracion = (Configuration)A;
            int angulo = configuracion.getAngle();
            double x0 = configuracion.getX();
            double y0 = configuracion.getY();
            double x1 = x0 + Math.cos(Math.toRadians(angulo))*(double)parametros.get(0);
            double y1 = y0 + Math.sin(Math.toRadians(angulo))*(double)parametros.get(0);
            configuracion.setPosition(x1, y1);
            configuracion.addLine(new Line((int)x0,(int)y0,(int)x1,(int)y1, configuracion.getColor()));
        }
    }
    
    public static class CambiarColor implements Function{
        @Override
        public void ejecutar(Object A, ArrayList parametros) {
            Configuration configuracion = (Configuration)A;
            configuracion.setColor(new Color((int)(double)parametros.get(0)%256, (int)(double)parametros.get(1)%256, (int)(double)parametros.get(2)%256));
        }
    }
    
}
