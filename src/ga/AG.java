/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ga;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import java.util.Random;

/**
 *
 * @author BKP
 */
public class AG {

    int TamPop = 100;
    int TamInd = 22;
    int Min = -100;
    int Max = 100;
    int[][] Pop, NovaPop,PopSel;
    double[] Aptidao,AptidaoR;
    double TxRep, TxMut;
    int NumCiclos;
    double AptidaoTotal;

    public AG(double TRep, double TMut, int Ciclos) {
        TxRep = TRep;
        TxMut = TMut;
        NumCiclos = Ciclos;
        CriarPop();
        //Aptidao = Aptidao(Pop);
        //AptidaoR = AptidaoRelativa(Aptidao);
        Aptidao = new double[Pop.length];
        AptidaoR = new double[Pop.length];
        CalcFuncao(); 
        
        
    }

    public void CriarPop() {
        
        Pop = new int[TamPop][2 * TamInd];
        NovaPop = new int[TamPop][2 * TamInd];
        PopSel = new int[TamPop][2 * TamInd];
        int popR;
        
        for (int i = 0; i < Pop.length; i++) {
            for (int j = 0; j < Pop[0].length; j++) {
                popR = (int) (Math.random()*100);
                if (popR % 2 == 0) {
                    Pop[i][j] = 0;
                } else {
                    Pop[i][j] = 1;
                }
            }
        }

    }

    private void Mutacao() {
        Random random = new Random();
        double valorAleatorio = 0;
        for (int i = 0; i < Pop.length; i++) {
            for (int j = 0; j < Pop[0].length; j++) {
                valorAleatorio = random.nextDouble();
                if (valorAleatorio < TxMut) {
                    Pop[i][j] = (Pop[i][j] == 1 ? 0 : 1);
                }
            }
        }
    }

    public double[] Aptidao(int[][] pop) {
        double[] aptidao = new double[pop.length];
        int individuo = 0;
        int calculox = 0;
        int calculoy = 0;
        int[] x = new int[TamInd];
        int[] y = new int[TamInd];
        for (int i = 0; i < pop.length; i++) {
            for (int j = 0; j < TamInd - 1; j++) {
                x[j] = pop[i][j];
                y[j] = pop[i][j + TamInd];

            }
            calculox = calc(x);
            calculoy = calc(y);

            aptidao[i] = calApt(Calcula(calculox), Calcula(calculoy));

        }

        return aptidao;
    }
    
    public double[] AptidaoRelativa(double [] AptReal){
       double [] Aptr =new double [AptReal.length];
       double [] Aptid =new double [AptReal.length];
       double somatorio=0;
       for(int s=0;s<AptReal.length;s++){
           somatorio=somatorio+AptReal[s];
       }
       
        for(int x=0;x<AptReal.length;x++){
              
            Aptr[x]=AptReal[x]/somatorio;
            
        }
        Aptid[0]=Aptr[0];
        for(int id=1;id<Aptr.length;id++){
              
            Aptid[id]=Aptid[id-1]+Aptr[id];
            
        }
        return(Aptid);
    }

    public int calc(int[] x) {
        int resultado = 0;
        int acc = x.length - 1;
        for (int i = 0; i < x.length; i++) {
            resultado = resultado + (int) Math.pow(2, acc) * x[i];
            acc--;
        }
        return resultado;
    }

    public double Calcula(int x) {
        double resultado = 0;
        resultado = Min + (Max - Min / (Math.pow(2, 22) - 1)) * x;
        return resultado;
    }

    public double calApt(double x, double y) {
        double resultado = 0;
        resultado = Math.abs(x * y * (Math.sin((y * Math.PI) / 4)));
        return (resultado);
    }

    public void Reproducao() {

        Random r = new Random();
        int[] pai = new int[2 * TamInd];
        int[] mae = new int[2 * TamInd];
        int[] filho_1 = new int[2 * TamInd];
        int[] filho_2 = new int[2 * TamInd];
        int crossover, cont = 0;
        //System.out.println("=" + this.TamPop * (1 - this.TxRep));
        for (int i = 0; i < (int) this.TamPop * (1 - this.TxRep); i++) {
            //System.out.println("i=" + i);
            this.NovaPop[(this.TamPop - 1) - i] = this.Pop[r.nextInt(TamPop)];

        }

        for (int i = 0; i < (TamPop * TxRep) / 2; i++) {
            crossover = r.nextInt(2 * TamInd - 1);
            pai = this.Pop[Sorteado()];
            mae = this.Pop[Sorteado()];

            for (int j = 0; j <= crossover; j++) {
                filho_1[j] = pai[j];
                filho_2[j] = mae[j];
            }
            for (int k = (crossover + 1); k < 2 * TamInd; k++) {
                filho_1[k] = mae[k];
                filho_2[k] = pai[k];
            }

            this.NovaPop[cont] = filho_1;
            cont++;
            this.NovaPop[cont] = filho_2;
            cont++;
        }

    }
    
    public int Sorteado() {
        Random r = new Random();
        double aleatorio = r.nextDouble();
        double soma = 0;

        for (int i = 0; i < this.TamPop; i++) {
            soma += this.Aptidao[i] / this.AptidaoTotal;
            if (aleatorio <= soma) {
                return i;
            }
        }
        return 101;
    }

    public void Selecao(){
     double prob; boolean flag;
     int s[][]=new int [2][TamInd*2];//44
     for(int x=0;x<PopSel.length;x=x+2){//ps.length
       do{ 
        flag=true;   
        for(int z=0;z<2;z++){ 
         prob=Math.random();
         if (prob<=AptidaoR[0]) { s[z]=Pop[0];    }
         else{
            
            for(int y=1;y<AptidaoR.length;y++){
                
              if( (AptidaoR[y-1]<prob) &&  (prob<=AptidaoR[y]) ) { s[z]=Pop[y];break; }  
            } 
             
         }
     
        }
         if( TxRep<Math.random() ){flag=false;}
        
       }while(flag);      
       PopSel[x]=s[0]; PopSel[x+1]=s[1];
       
     }//fecha For
     
    }
    
    public void Cruzamento(){
        
        int pontocruza=0;
        int [][] pnew;
        pnew = new int[TamPop][2*TamInd]; 
        for(int x=0;x<PopSel.length;x=x+2){
          pontocruza = (int)(Math.random()*(43));
          for(int id=0;id<=pontocruza;id++){
             pnew[x][id]=PopSel[x][id];
             pnew[x+1][id]=PopSel[x+1][id];
          }
           
          for(int id=pontocruza+1;id<PopSel[0].length;id++){
             pnew[x][id]=PopSel[x+1][id];
             pnew[x+1][id]=PopSel[x][id];
          }
          
        }
        Pop=pnew; //transforma a nova população na população atual
        
    }
    public int MaiorFx() {
        int maior = 0;
        for (int x = 0; x < Aptidao.length; x++) {
            if (Aptidao[x] > Aptidao[maior]) {
                maior = x;
            }
        }
        return (maior);
    }

    public void novaGeracao() {
        //Verificar as Aptidões da População
        int MaiorFxid = MaiorFx();
        System.out.println("Valor da maior aptidão =" + Aptidao[MaiorFxid]);
        for (int i = 0; i < NumCiclos; i++) {
            System.out.println("ComecaRepro");
            Reproducao();
            Pop = NovaPop;
            Mutacao();
            this.Aptidao = Aptidao(this.Pop);
            MaiorFxid = MaiorFx();
            System.out.println("Valor da maior aptidão =" + Aptidao[MaiorFxid]);
        }
    }
    
    public void novaGeracao2() {
        //Verificar as Aptidões da População
        int MaiorFxid = MaiorFx();
        System.out.println("Valor da maior aptidão =" + Aptidao[MaiorFxid]);
        for (int i = 0; i < NumCiclos; i++) {
            //System.out.println("ComecaRepro");
            Selecao();
            Cruzamento();
            Mutacao();
            //Aptidao = Aptidao(Pop);
            //AptidaoR = AptidaoRelativa(Aptidao);
            CalcFuncao();
            MaiorFxid = MaiorFx();
            System.out.println("Valor da maior aptidão =" + (Aptidao[MaiorFxid]));
        }
    }
    
    
       public double[] BinarioReal(int[] N){
        
       double[] Preal= new double[2];       
       double Pint=0; //double Pfrac=0.0;
       int exp=TamInd-1;          //21;  
       for(int x=0;x<=(TamInd-1);x++){ //21
        Pint=Pint+( N[x]*Math.pow(2,exp) );
        exp--;
       }
       
       Preal[0] = Pint;
       //término da transformação do primeiro número
       
       Pint=0; //Pfrac=0;
       exp=TamInd-1;
       for(int x=22;x<=(2*TamInd-1);x++){ //43
        Pint=Pint+( N[x]*Math.pow(2,exp) );
        exp--;
       }
       
       Preal[1] = Pint;
       for(int id=0;id<Preal.length;id++){
           Preal[id]=((Max-Min)/(Math.pow(2,TamInd)-1)) * Preal[id] + Min;
       }
       
       return(Preal);
        
    }
    

    public double Funcao(double[] xy){
        
       double fx; 
      
        fx = Math.abs(xy[0] * xy[1] * (Math.sin((xy[1] * Math.PI) / 4)));
        return (fx);
        
    }
    
    public void CalcFuncao(){
        double somatorio=0; 
        double[] Fid = new double[Aptidao.length];
        for(int x=0;x<Pop.length;x++){
            Aptidao[x]=Funcao( BinarioReal( Pop[x] )  );           
            somatorio=somatorio+Aptidao[x];
        }
        
        for(int x=0;x<Aptidao.length;x++){
              
            AptidaoR[x]=Aptidao[x]/somatorio;
            
        }
        Fid[0]=AptidaoR[0];
        for(int x=1;x<AptidaoR.length;x++){
              
            Fid[x]=Fid[x-1]+AptidaoR[x];
            
        }
        AptidaoR=Fid;
    }
   
}