import java.util.ArrayList;

public class SymbolTable{

    private ArrayList<Symbol> table = new ArrayList();
    
    Symbol lookUpTable(String symbolName){
        for(Symbol item: table){
            if(item.getName().equals(symbolName)){
                return item;
            }
        }
        return null;
    }

    void install(String name, Complejo data, short type) {
        Symbol s = new Symbol(name, type, data);
        table.add(s);
    }

    void update(Symbol s, Complejo data) {
        Symbol newSymbol = s;  
        table.remove(s);
        newSymbol.setData(data);
        table.add(newSymbol);    
    }

    void print() {
        for (Symbol s: table) {
          System.out.println("Name: " + s.getName());
        }
    }

}