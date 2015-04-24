/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ga;

/**
 *
 * @author BKP
 */
public class NewMain {


	public static void main(String[] args) {
		AG PrimeiroAG;
		PrimeiroAG = new AG(0.60,0.03,100);  
		PrimeiroAG.novaGeracao();  
		//PrimeiroAG.novaGeracao2();  Outra Forma de Seleção e Cruzamento
		PrimeiroAG.printMaioresAptidaoEMedia(System.out, 10);
	}

	public static int BinarioInt(int[] ind){

		int valorint=0; int acc=ind.length-1;
		for(int x=0;x<ind.length;x++){
			valorint = valorint+(ind[x]*(int)Math.pow(2,acc) );
			acc--;
		}
		return(valorint);
	}

	public static int Aptidao(int ind){

		return((int)Math.pow(ind, 2));
	}

	public static int[] AvaliaPop(int[][] pop){
		int[] retApt = new int[pop.length];
		for(int x=0;x<pop.length;x++){
			retApt[x]=Aptidao(BinarioInt(pop[x])  );
		} 
		return(retApt);
	}
	public static int[] CalAptRel(int[] Ap){
		int[] Aprel=new int[Ap.length];
		int acc=0;
		for(int x=0;x<Aprel.length;x++){
			acc+=Ap[x];
		}
		for(int x=0;x<Aprel.length;x++){
			Aprel[x]=Ap[x]/acc;
		}
		return(Aprel);

	}
	public static int[][] Selecao(int[][] popIn,int[] Aprel,double tx){
		int[][] tempPop = new int [popIn.length][popIn[0].length];
		int pos=0; int[][] Pais=new int [2][popIn[0].length];
		int id=0;double soma=0; double agulharoleta=0;
		do{
			agulharoleta=Math.random();
			for(int y=0;y<Aprel.length;y++){
				soma=soma+Aprel[y];
				if(agulharoleta<soma){
					id=y;
					break;
				}
				Pais[0]=popIn[id];
			}

			agulharoleta=Math.random();
			for(int y=0;y<Aprel.length;y++){
				soma=soma+Aprel[y];
				if(agulharoleta<soma){
					id=y;
					break;
				}
				Pais[1]=popIn[id];
			}

			if(Math.random()>tx){
				tempPop[pos]=Pais[0];
				tempPop[pos+1]=Pais[1];
				pos=pos+2;
			}

		}while(pos<=tempPop.length-2); 
		return(tempPop);

	}

}

