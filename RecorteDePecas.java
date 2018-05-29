/**
  * Pontificia Universidade Catolica de Minas Gerais
  * Projeto e Analise de Algoritmos - 1/2018
  * @author: Paulo Junio Reis Rodrigues - pjrrodrigues@sga.pucminas.br
  * @author: Guilherme Galvão Oliveira Silva - guilherme.silva.998109@sga.pucminas.br
  * @author: Joao Paulo de Castro Bento Pereira - jpcbpereira@sga.pucminas.br
  */

/* Bibliotecas Utilizadas */
import java.lang.Math; /* Calculo de Potencias e Raizes */
import java.io.*;
import IO.*; /* Biblioteca Grafica @author: Theldo Cruz Franqueira */

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

class RecorteDePecas {
  static Trapezoidal [] trapezoidais;
  static double solucao;
  static int [] vetorSolucao;
  
  /**
   * Funcao para calcular o lado de um triangulo retangulo pela formula de pitagoras
   * @param double x - Tamanho do lado
   */

  public static double calcularLado (double x) {
    double lado;
    double ladoTemporario = Math.pow(x,2) + Math.pow(100,2);
    lado = Math.sqrt(ladoTemporario);
    return lado;
  }

  /**
   * Funcao para calcular area do triangulo de desperdicio utilizando a formula de semi perimetros
   * @param int x - ponto x do triangulo
   * @param int y - ponto y do triangulo
   * @param int z - ponto z do triangulo
   */
  
  public static double calcularArea (double x, double y, double z) {
    double area;
    double semiPerimetro = (x + y + z)/2 ;
    //System.out.println("X =" + x +  " Y = " + y + " Z = " + z);
    area = Math.sqrt (semiPerimetro*(semiPerimetro - x)*(semiPerimetro - y)*(semiPerimetro - z));
    return area;
  }

  /**
   * Funcao para calcular desperdicio total presente no array de pecas
   * @param int [] x - Arranjo com pecas
   */
  
  public static double calcularDesperdicio(int [] x) {
    double parteSuperiorTemporaria = 0.0, parteInferiorTemporaria = 0.0;
    double desperdicio = 0.0;
    double x1,x2,x3;
    double ladoTriangulo1,ladoTriangulo2;
    for(int i = 0; i <= x.length; i++) {
      if(i != x.length ) {
        x3 = trapezoidais[x[i]].getX3();
        if(parteSuperiorTemporaria >= parteInferiorTemporaria) {
          x3 = x3 * -1;
          ladoTriangulo1 = calcularLado(parteSuperiorTemporaria);
          ladoTriangulo2 = calcularLado(x3);
          if(x3 <= parteSuperiorTemporaria) { 
            desperdicio += calcularArea(ladoTriangulo1, ladoTriangulo2, parteSuperiorTemporaria - x3);
          }else{
            desperdicio += calcularArea(ladoTriangulo1, ladoTriangulo2, x3 - parteSuperiorTemporaria);
          }
        }else{
          //System.out.println("entro");
          ladoTriangulo1 = calcularLado(parteInferiorTemporaria);
          ladoTriangulo2 = calcularLado(x3);
          if(x3 <= parteInferiorTemporaria) { 
            desperdicio += calcularArea(ladoTriangulo1,ladoTriangulo2,parteInferiorTemporaria - x3);
          }else{
            desperdicio += calcularArea(ladoTriangulo1,ladoTriangulo2,x3 - parteInferiorTemporaria);
          }
        }
        x1 = trapezoidais[x[i]].getX1();
        x2 = trapezoidais[x[i]].getX2();
        x3 = trapezoidais[x[i]].getX3();
        //System.out.println(x1 + " " + x2 + " " + x3);
        if( x2 > 0 ) {
          if(x2 > x1) {
            parteSuperiorTemporaria = 0.0;
            parteInferiorTemporaria = x2 - x1;
          }else if(x2 < x1){
            parteSuperiorTemporaria = x1 - x2;
            parteInferiorTemporaria = 0.0;
          }else{
            parteSuperiorTemporaria = parteInferiorTemporaria = 0.0;
          }
        }else if(x2 < 0){
         parteSuperiorTemporaria = x2 * (-1) + x1;  
         parteInferiorTemporaria = 0.0;
        }else{
         parteSuperiorTemporaria = parteInferiorTemporaria = 0.0;
        }
      }else{
        x3 = 0;
        if(parteSuperiorTemporaria >= parteInferiorTemporaria) {
          ladoTriangulo1 = calcularLado(parteSuperiorTemporaria);
          ladoTriangulo2 = calcularLado(x3);
          if(x3 <= parteSuperiorTemporaria) { 
            desperdicio += calcularArea(ladoTriangulo1,ladoTriangulo2,parteSuperiorTemporaria - x3);
          }else{
            desperdicio += calcularArea(ladoTriangulo1,ladoTriangulo2,x3 - parteSuperiorTemporaria);
          }
        }else{   
          ladoTriangulo1 = calcularLado(parteInferiorTemporaria);
          ladoTriangulo2 = calcularLado(x3);
          if(x3 <= parteInferiorTemporaria) { 
            desperdicio += calcularArea(ladoTriangulo1,ladoTriangulo2,parteInferiorTemporaria - x3);
          }else{
            desperdicio += calcularArea(ladoTriangulo1,ladoTriangulo2,x3 - parteInferiorTemporaria);
          }
        }
      }
      //System.out.println(desperdicio);
    }
    return desperdicio;
  }

  /**
   * Funcao para realizar a permutacao de todas as possibiliades de arranjo de pecas
   * @param int n - tamanho do vetor x
   * @param int [] x - Arranjo de pecas que sera permutado
   * @param boolean [] pass - Posicoes que passarm na permutacao
   * @param int cont - controle de permutacao
   */

  public static void permutacao (int n , int [] x , boolean [] pass , int cont){
   if(n == cont) {
     double desperdicio = calcularDesperdicio(x);
     //System.out.println("solucao " + desperdicio);
     if(desperdicio < solucao) {
      solucao = desperdicio;
      for(int i = 0; i < vetorSolucao.length;i++) 
        vetorSolucao[i] = x[i];
     }     
     }else{
       for(int i = 0; i < n; i++) {
         if(!pass[i]) {
           pass[i] = true;
           x[cont] = i;
           permutacao(n, x, pass, cont+1);
           pass[i] = false;
         }
       }
     }
  }

  /**
   * Funcao para plotar o grafico de pecas utilizando biblioteca IO
   * @libraryAuthor: Theldo Cruz Franqueira
   */

  public static void plotarPecas() {
    
    IOGraphics g = new IOGraphics("PAA", 2000, 1000);
    double parteSuperiorTemporaria = 0.0, parteInferiorTemporaria = 0.0;
    double ultimaPosicaoSuperior = -500.0, ultimaPosicaoInferior = -500.0;
    double x1,x2,x3;
	g.drawLine(ultimaPosicaoInferior, -50, ultimaPosicaoSuperior, 150, "BLACK", 0.003);
    for(int i = 0; i < vetorSolucao.length; i++) {
      x1 = trapezoidais[vetorSolucao[i]].getX1() * 8;
      x2 = trapezoidais[vetorSolucao[i]].getX2() * 8;
      x3 = trapezoidais[vetorSolucao[i]].getX3() * 8;
      if(parteSuperiorTemporaria >= parteInferiorTemporaria) {  
          x3 = x3 * - 1;      
          if(x3 <= parteSuperiorTemporaria) { 
            //System.out.println("Passo if 1");
            //botar Trapezoidal ligado em Cima.
            g.drawLine(ultimaPosicaoSuperior, 100, x1 + ultimaPosicaoSuperior, 100, "BLACK", 0.003);
            g.drawLine(ultimaPosicaoSuperior - x3, 0, ultimaPosicaoSuperior + x2, 0, "BLACK", 0.003);
            g.drawLine(ultimaPosicaoSuperior - x3, 0, ultimaPosicaoSuperior, 100, "BLACK", 0.003);
            g.drawLine(ultimaPosicaoSuperior + x2 , 0, x1 + ultimaPosicaoSuperior , 100, "BLACK", 0.003);
            ultimaPosicaoSuperior = ultimaPosicaoSuperior + x1;
            ultimaPosicaoInferior = ultimaPosicaoSuperior + x2 - x1;
          }else{
            //System.out.println("Passo if 2");
            //botar Trapezoidal Ligado emBaixo.
            g.drawLine(ultimaPosicaoInferior + x3, 100, x1 + x3 + ultimaPosicaoInferior, 100, "BLACK", 0.003);
            g.drawLine(ultimaPosicaoInferior, 0, ultimaPosicaoInferior + x2 + x3, 0, "BLACK", 0.003);
            g.drawLine(ultimaPosicaoInferior, 0,ultimaPosicaoInferior + x3, 100, "BLACK", 0.003);
            g.drawLine(ultimaPosicaoInferior + x2 + x3 , 0, x1 + x3 + ultimaPosicaoInferior , 100, "BLACK", 0.003);
            ultimaPosicaoSuperior = ultimaPosicaoInferior + x1 + x3;
            ultimaPosicaoInferior = ultimaPosicaoSuperior + x2 - x1;
          }       
        }else{ 
          if(x3 <= parteInferiorTemporaria) { 
            //System.out.println("Passo if 3");
            //botar Trapezoidal Ligado emBaixo.
            g.drawLine(ultimaPosicaoInferior - x3, 100, x1 - x3 + ultimaPosicaoInferior, 100, "BLACK", 0.003);
            g.drawLine(ultimaPosicaoInferior, 0, ultimaPosicaoInferior + x2 - x3, 0, "BLACK", 0.003);
            g.drawLine(ultimaPosicaoInferior, 0, ultimaPosicaoInferior - x3, 100, "BLACK", 0.003);
            g.drawLine(ultimaPosicaoInferior + x2 - x3 , 0, x1 - x3 + ultimaPosicaoInferior, 100, "BLACK", 0.003);
            ultimaPosicaoSuperior = ultimaPosicaoInferior + x1 - x3;
            ultimaPosicaoInferior = ultimaPosicaoSuperior + x2 - x1;
          }else{
            //System.out.println("Passo if 4");
            //botar Trapezoidal ligado emCima.
            g.drawLine(ultimaPosicaoSuperior, 100, x1 + ultimaPosicaoSuperior, 100, "BLACK", 0.003);
            g.drawLine(ultimaPosicaoSuperior + x3, 0, ultimaPosicaoSuperior + x2, 0, "BLACK", 0.003);
            g.drawLine(ultimaPosicaoSuperior + x3, 0, ultimaPosicaoSuperior, 100, "BLACK", 0.003);
            g.drawLine(ultimaPosicaoSuperior + x2, 0, x1 + ultimaPosicaoSuperior , 100, "BLACK", 0.003);
            ultimaPosicaoSuperior = ultimaPosicaoSuperior + x1;
            ultimaPosicaoInferior = ultimaPosicaoSuperior + x2 - x1;
          }
        }
        x1 = trapezoidais[vetorSolucao[i]].getX1() * 8;
        x2 = trapezoidais[vetorSolucao[i]].getX2() * 8;
        x3 = trapezoidais[vetorSolucao[i]].getX3() * 8;
        if( x2 > 0 ) {
          if(x2 > x1) {
            parteSuperiorTemporaria = 0.0;
            parteInferiorTemporaria = x2 - x1;
          }else if(x2 < x1){
            parteSuperiorTemporaria = x1 - x2;
            parteInferiorTemporaria = 0.0;
          }else{
            parteSuperiorTemporaria = parteInferiorTemporaria = 0.0;
          }
        }else if(x2 < 0){
         parteSuperiorTemporaria = x2 * (-1) + x1;  
         parteInferiorTemporaria = 0.0;
        }else{
         parteSuperiorTemporaria = parteInferiorTemporaria = 0.0;
        }
        
      }
      g.drawLine(ultimaPosicaoInferior + parteSuperiorTemporaria, -50, ultimaPosicaoSuperior + parteInferiorTemporaria, 150, "BLACK", 0.003);
  }
	
  public static void branchAndBound(int n, int [] x, boolean [] utilizada, int cont, double desperdicioTemporario) {
	  int aux = -1;
	  if( n == cont ) {
		solucao = calcularDesperdicio(x);
		for(int i = 0; i < vetorSolucao.length;i++) 
			vetorSolucao[i] = x[i];
	  }else{
		for(int i = 0; i < n; i++) {
			
			if(utilizada[i] != true) {
				int [] auxVector = new int [2];
				auxVector[0] = x[cont - 1]; //Ultima peça
				auxVector[1] = x[i]; // peça que a gente que colocar
				double desperdicio = calcularDesperdicio(auxVector);
				
				if(desperdicioTemporario > desperdicio) {
						desperdicioTemporario = desperdicio;
						aux = i;
				}
			}
		}	
		utilizada[aux] = true;
		x[cont] = aux;
		branchAndBound(n,x,utilizada,cont + 1,Double.MAX_VALUE);
      }
  }

  public static void main(String[] args) {
    solucao = Double.MAX_VALUE;
    try {
      /* Parse da entrada */
      File file = new File("Test.txt");
      BufferedReader br = new BufferedReader(new FileReader(file));
      String st;
      st = br.readLine();
      int n = Integer.parseInt(st);
      //System.out.println(solucao);
      trapezoidais = new Trapezoidal [n];
      vetorSolucao = new int [n];
      for(int i = 0;(st = br.readLine()) != null; i++) {
        System.out.println(st);
        String [] tmp = st.split(" ");
        trapezoidais[i] = new Trapezoidal(Double.parseDouble(tmp[0]),Double.parseDouble(tmp[1]) ,Double.parseDouble(tmp[2]));     
        //System.out.println(trapezoidais[i].getX2());
      }
      
      int x [] = new int [n]; /* Vetor de pecas */
      int y [] = new int [n]; /* Vetor de pecas */
      boolean pass1 [] = new boolean[n]; /* posicoes booleanas do vetor de pecas */
      boolean pass2 [] = new boolean[n]; /* posicoes booleanas do vetor de pecas */
      /* inicializando vetores */
      for(int i = 0; i < n; i++) {
        x[i] = 0;
        y[i] = 0;
        pass1[i] = false;
        pass2[i] = false;
      }

      /* Realizando Permutacao com pecas e melhor soulicao de acordo com permutacao */
      long begin = System.currentTimeMillis(); /* Tempo do sistema antes da permutacao */
      permutacao(n, x, pass1, 0);
      long end = System.currentTimeMillis(); /* Tempo do sistema apos permutacao */
      System.out.println("Tempo de execução: " + (end - begin)/1000);
      System.out.println("Melhor solução do problema usando permutação: " + solucao);
      //digite enter para blotar
      plotarPecas();
      
      IO.readString("Aperte Enter para continuar");
      int aux = -1;
      double desperdicioTemporario = Double.MAX_VALUE;
      for(int i = 0; i < n; i++) {		
		int [] auxVector = new int [1];
		auxVector[0] = y[i]; // peça que a gente quer colocar
		double desperdicio = calcularDesperdicio(auxVector);
		
		if(desperdicioTemporario > desperdicio) {
				desperdicioTemporario = desperdicio;
				aux = i;
		}			
	  }
	  pass2[aux] = true;
	  y[0] = aux;
	  
      /* Realizando Branch and Bound com pecas e melhor soulicao de acordo com permutacao */
      begin = System.currentTimeMillis(); /* Tempo do sistema antes da permutacao */
      branchAndBound(n, y, pass2, 1,Double.MAX_VALUE);
      end = System.currentTimeMillis(); /* Tempo do sistema apos Branch and Bound */
      System.out.println("Tempo de execução: " + (end - begin)/1000);
      System.out.println("Melhor solução do problema usando Branch and Bound: " + solucao);
      //digite enter para blotar
      plotarPecas();
      
    }catch(IOException ex) {
      ex.printStackTrace();
    }   
    
  }
}
