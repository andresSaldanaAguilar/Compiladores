public class Symbol {
  String name;
  short type;
  Complejo data;
  //
  public String metodo;
  int defn;
  Symbol next;

  public Symbol(String name, short type, Complejo data) {
    this.name = name;
    this.type = type;
    this.data = data;
  }

  public String getName() {return name;}
  public short getType() {return type;}
  public Complejo getData() {return data;}

  public void setData(Complejo data) {this.data = data;}
}
