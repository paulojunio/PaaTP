import java.lang.Math;
import IO.*;
class Trapezoidal {
  private double x1;
  private double x2;
  private double x3;
  
  public Trapezoidal () {}
  public Trapezoidal (double x1, double x2, double x3) {
    this.x1 = x1;
    this.x2 = x2;
    this.x3 = x3;
  }
  
  public void setX1(double x1) {
    this.x1 = x1;
  }
  public void setX2(double x2) {
    this.x2 = x2;
  }
  public void setX3(double x3) {
    this.x3 = x3;
  }
  
  public double getX1() {
    return x1;
  }
  public double getX2() {
    return x2;
  }
  public double getX3() {
    return x3;
  }
}
class PaaTP {
  static Trapezoidal [] trapezoidais;
  static double solution;
  static int [] solutionVector;
  
  
  public static double pickSide(double x) {
    double side;
    //System.out.println("Lado = " + x);
    double sideD = Math.pow(x,2) + Math.pow(100,2);
    side = Math.sqrt(sideD);
    return side;
  }
  public static double calculateArea (double x,double y , double z) {
    double area;
    double semiMeter = (x + y + z)/2 ;
    //System.out.println("X =" + x +  " Y = " + y + " Z = " + z);
    area = Math.sqrt(semiMeter*(semiMeter - x)*(semiMeter - y)*(semiMeter - z));
    return area;
  }
  public static double doWaste(int [] x) {
    
    double tmp1 = 0.0,tmp2 = 0.0;
    double waste = 0.0;
    double x1,x2,x3;
    double side1,side2;
    
    for(int i = 0; i <= x.length; i++) {
      
      if(i != x.length ) {
        x3 = trapezoidais[x[i]].getX3(); 
        if(tmp1 >= tmp2) {
          
          x3 = x3 * -1;
          side1 = pickSide(tmp1);
          side2 = pickSide(x3);
          if(x3 <= tmp1) { 
            waste += calculateArea(side1,side2,tmp1 - x3);
          }else{
            waste += calculateArea(side1,side2,x3 - tmp1);
          }
          
        }else{
          //System.out.println("entro");
          side1 = pickSide(tmp2);
          side2 = pickSide(x3);
          if(x3 <= tmp2) { 
            waste += calculateArea(side1,side2,tmp2 - x3);
          }else{
            waste += calculateArea(side1,side2,x3 - tmp2);
          }
        }
        
        x1 = trapezoidais[x[i]].getX1();
        x2 = trapezoidais[x[i]].getX2();
        x3 = trapezoidais[x[i]].getX3();
        //System.out.println(x1 + " " + x2 + " " + x3);
        if( x2 > 0 ) {
          tmp1 = 0.0;
          tmp2 = x2;
        }else if(x2 < 0){
         tmp1 = x2 * (-1);  
         tmp2 = 0.0;
        }else{
         tmp1 = tmp2 = 0.0;
        }
      }else{
        x3 = 0;
        if(tmp1 >= tmp2) {

          side1 = pickSide(tmp1);
          side2 = pickSide(x3);
          if(x3 <= tmp1) { 
            waste += calculateArea(side1,side2,tmp1 - x3);
          }else{
            waste += calculateArea(side1,side2,x3 - tmp1);
          }
          
        }else{
          
          side1 = pickSide(tmp2);
          side2 = pickSide(x3);
          if(x3 <= tmp2) { 
            waste += calculateArea(side1,side2,tmp2 - x3);
          }else{
            waste += calculateArea(side1,side2,x3 - tmp2);
          }
        }

      }
      //System.out.println(waste);
    }
    return waste;
  }
  public static void permutation(int n , int [] x , boolean [] pass , int cont){
   
   if(n == cont) {
     double waste = doWaste(x);
     //System.out.println("Solution " + waste);
     if(waste < solution) {
      solution = waste;
      for(int i = 0; i < solutionVector.length;i++) 
        solutionVector[i] = x[i];
     }     
     }else{
       for(int i = 0; i < n; i++) {
         if(!pass[i]) {
           pass[i] = true;
           x[cont] = i;
           permutation(n,x,pass,cont+1);
           pass[i] = false;
         }
       }
     }
  }
  public static void createGraph() {
    
    IOGraphics g = new IOGraphics("PAA", 1000, 500);
    double tmp1 = 0.0, tmp2 = 0.0;
    double lastPos1 = -500.0, lastPos2 = -500.0;

    double x1,x2,x3;

    for(int i = 0; i < solutionVector.length; i++) {

      x1 = trapezoidais[solutionVector[i]].getX1() * 5;
      x2 = trapezoidais[solutionVector[i]].getX2() * 5;
      x3 = trapezoidais[solutionVector[i]].getX3() * 5; 

      if(tmp1 >= tmp2) {  
          x3 = x3 * - 1;      
          if(x3 <= tmp1) { 
            //System.out.println("Passo if 1");
            //botar Trapezoidal ligado emCima.
            g.drawLine(lastPos1, 100, x1 + lastPos1, 100, "BLACK", 0.003); // x3 + lastPos1 ate x1 + x3 + lastPos1
            g.drawLine(lastPos1 - x3, 0, lastPos1 + x1 + x2, 0, "BLACK", 0.003);    //x3+x2
            g.drawLine(lastPos1 - x3, 0, lastPos1, 100, "BLACK", 0.003); //x3 <--> x1
            g.drawLine(lastPos1 + x1 + x2 , 0, x1 + lastPos1 , 100, "BLACK", 0.003);  //x1 <--> x2
            lastPos1 = lastPos1 + x1;
            lastPos2 = lastPos1 + x2;
          }else{
            //System.out.println("Passo if 2");
            //botar Trapezoidal Ligado emBaixo.
            g.drawLine(lastPos2 + x3, 100, x1 + x3 + lastPos2, 100, "BLACK", 0.003); // x3 + lastPos1 ate x1 + x3 + lastPos1
            g.drawLine(lastPos2, 0, lastPos2 + x1 + x2 + x3, 0, "BLACK", 0.003);    //x3+x2
            g.drawLine(lastPos2, 0,lastPos2 + x3, 100, "BLACK", 0.003); //x3 <--> x1
            g.drawLine(lastPos2 + x1 + x2 + x3 , 0, x1 + x3 + lastPos2 , 100, "BLACK", 0.003);  //x1 <--> x2
            lastPos1 = lastPos2 + x1 + x3;
            lastPos2 = lastPos1 + x2;
          }       
        }else{ 
          if(x3 <= tmp2) { 
            //System.out.println("Passo if 3");
            //botar Trapezoidal Ligado emBaixo.
            g.drawLine(lastPos2 - x3, 100, x1 - x3 + lastPos2, 100, "BLACK", 0.003); // x3 + lastPos1 ate x1 + x3 + lastPos1
            g.drawLine(lastPos2, 0, lastPos2 + x1 + x2 - x3, 0, "BLACK", 0.003);    //x3+x2
            g.drawLine(lastPos2, 0, lastPos2 - x3, 100, "BLACK", 0.003); //x3 <--> x1
            g.drawLine(lastPos2 + x1 + x2 - x3 , 0, x1 - x3 + lastPos2, 100, "BLACK", 0.003);  //x1 <--> x2
            lastPos1 = lastPos2 + x1 - x3;
            lastPos2 = lastPos1 + x2;
          }else{
            //System.out.println("Passo if 4");
            //botar Trapezoidal ligado emCima.
            g.drawLine(lastPos1, 100, x1 + lastPos1, 100, "BLACK", 0.003); // x3 + lastPos1 ate x1 + x3 + lastPos1
            g.drawLine(lastPos1 + x3, 0, lastPos1 + x1 + x2, 0, "BLACK", 0.003);    //x3+x2
            g.drawLine(lastPos1 + x3, 0, lastPos1, 100, "BLACK", 0.003); //x3 <--> x1
            g.drawLine(lastPos1 + x1 + x2, 0, x1 + lastPos1 , 100, "BLACK", 0.003);  //x1 <--> x2
            lastPos1 = lastPos1 + x1;
            lastPos2 = lastPos1 + x2;
          }
        }
        
        x1 = trapezoidais[solutionVector[i]].getX1() * 5;
        x2 = trapezoidais[solutionVector[i]].getX2() * 5;
        x3 = trapezoidais[solutionVector[i]].getX3() * 5;
        
        if( x2 > 0 ) {
          tmp1 = 0.0;
          tmp2 = x2;
        }else if(x2 < 0){
         tmp1 = x2 * (-1);        
         tmp2 = 0.0;
        }else{
         tmp1 = tmp2 = 0.0;
        }
        
      }
  }
  public static void main(String[] args) {
    solution = Double.MAX_VALUE;
    try {

      File file = new File("Test.txt");
      BufferedReader br = new BufferedReader(new FileReader(file));
      String st;
      st = br.readLine();
      int n = Integer.parseInt(st);
      //System.out.println(solution);
      trapezoidais = new Trapezoidal [n];
      for(int i = 0;(st = br.readLine()) != null; i++) {
        System.out.println(st);
        String [] tmp = st.split(" ");
        trapezoidais[i] = new Trapezoidal(Double.parseDouble(tmp[0]),Double.parseDouble(tmp[0]) + Double.parseDouble(tmp[1]) ,Double.parseDouble(tmp[2]));     
        //System.out.println(trapezoidais[i].getX2());
      }

      int vector [] = new int [n];
      boolean pass [] = new boolean[n];
      for(int i = 0; i < n; i++) {
        vector[i] = 0;
        pass[i] = false;
      }
      long begin = System.currentTimeMillis();
      permutation(n,vector,pass,0);
      long end = System.currentTimeMillis();
      System.out.println("Tempo de execução: " + (end - begin));
      System.out.println("Melhor solução do problema: " + solution);
      //digite enter para blotar
      createGraph();

    }catch(IOException ex) {
      System.out.println(ex.getMessage());
    }   
    
  }
}