public class Complejo {
  private float real;
  private float img;

  public Complejo(float real, float img) {
    this.real = real;
    this.img  = img;
  }

  public float getReal() {return real; }
  
  public float getImg() {return img; }

  public void imprimeComplejo() {
    if (img > 0)
      System.out.println("" + this.real + " + " + this.img + " i");
    else
      System.out.println("" + this.real + " " + this.img + " i");
  }

  public void sumaComplejos(Complejo c1, Complejo c2) {
    this.real = c1.getReal() + c2.getReal();
    this.img  = c1.getImg() + c2.getImg();
  }

  public void restaComplejos(Complejo c1, Complejo c2) {
    this.real = c1.getReal() - c2.getReal();
    this.img  = c1.getImg() - c2.getImg();
  }

  public void multiplicaComplejos(Complejo c1, Complejo c2) {
    this.real = (c1.getReal() * c2.getReal()) - (c1.getImg() * c2.getImg());
    this.img  = (c1.getImg() * c2.getReal()) + (c1.getReal() * c2.getImg());
  }

  public void divideComplejos(Complejo c1, Complejo c2) {
    float d = (float) ( (c2.getReal() * c2.getReal()) + (c2.getImg() * c2.getImg()) );
    this.real = (float) (c1.getReal() * c2.getReal() + c1.getImg() * c2.getImg()) / d;
    this.img  = (float) (c1.getImg() * c2.getReal() - c1.getReal() * c2.getImg()) / d;
  }
}
