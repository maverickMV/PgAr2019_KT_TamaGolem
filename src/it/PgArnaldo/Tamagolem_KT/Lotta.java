package it.PgArnaldo.Tamagolem_KT;

import javax.swing.JPanel;

import it.unibs.fp.mylib.InputDati;//Libreria per l'input dati da tastiera

public class Lotta extends JPanel{

	private Squadra squadra1; //Squadra del giocatore 1
	private Squadra squadra2; //Squadra del giocatore 2
	
	private Tamagolem tama1; //Tamagolem del giocatore 1
	private Tamagolem tama2; //Tamagolem del giocatore 2
	
	private Equilibrio equilibrio1;
	
	private PanelSquadraBatt team1;
	private PanelSquadraBatt team2;
	
	private int numGolem;    //Numero di Tamagolem ammessi per squadra
	private int numPietre;
	
	int[] sfereTama1 = new int[3];
    int[] sfereTama2 = new int[3];	
	
	
    //--------------------------------------------------------------------
    
    /**
     * Gestisce la meccanica di lotta
     */
	public Lotta() {
		
		equilibrio1=new Equilibrio();
		equilibrio1.creaMatrice();
	
	}
	
	
	
	
	/**
	 * Attribuisce il danno prestabilito al Tamagolem e controlla se la sua vita è zero o negativa
	 * @param tama Il tamagolem che subisce danni
	 * @param danno Il danno da attribuire al tamagolem
	 */
	private void applicaDanno(Tamagolem tama,int danno) {
		
		danno = danno*2;
		
		int vita=tama.getVita();
		
		if(danno>0) {
		vita=vita-danno;
		}
		else vita=vita-((-1)*danno);
		
		if(vita<=0) {
			tama.setIsAlive(false);
			tama.setVita(0);
		}
		else {
			tama.setVita(vita);
		}
		
		
	}
	
	/**
	 * Il centro della lotta
	 */
	public void lanciaSfere(Tamagolem tama1,Tamagolem tama2,JPanel pSquadra1,JPanel pSquadra2,JPanel panelA) {
		
		int i=0;
		
		team1=new PanelSquadraBatt("Squadra1");
		team2=new PanelSquadraBatt("Squadra2");
		
		team1.disegnaTeam(pSquadra1);
		team1.disegnaVita(tama1.getVita());
		System.out.println("vitaTama1: "+tama1.getVita());
		passaSfere(tama1,1);
		
		
		team2.disegnaTeam(pSquadra2);
		team2.disegnaVita(tama2.getVita());
		System.out.println("vitaTama2: "+tama2.getVita());
		passaSfere(tama2,2);
		
		controlloUguali();
		
		passaSfereGrafica(1);
		passaSfereGrafica(2);
		
		panelA.validate();
		panelA.repaint();
		
		
		do {
			
			if(equilibrio1.getValore(sfereTama1[i], sfereTama2[i])>0) {
				
				applicaDanno(tama2, equilibrio1.getValore(sfereTama1[i], sfereTama2[i]));
				System.out.println("tam2:"+tama2.getVita());
				team2.disegnaVita(tama2.getVita());
			}
			else {
				
				applicaDanno(tama1, equilibrio1.getValore(sfereTama1[i], sfereTama2[i]));
				team1.disegnaVita(tama1.getVita());
				System.out.println("tam1:"+ tama1.getVita());
			}
			
			
			team1.Aggiorna();
			team1.disegnaVita(tama1.getVita());
			
			team1.disegnaVita(tama1.getVita());
			team2.Aggiorna();
			
			
			
			pausa1(1500);
			
			if(i<2) {
				i++;
			}
			
			else i=0;
			
		}while(tama1.getIsAlive()==true && tama2.getIsAlive()==true);
		
		team2.disegnaVita(tama2.getVita());
		
		team1.disegnaVita(tama1.getVita());
		
		panelA.validate();
		panelA.repaint();
	}
	
	
	//--------------------------------------------------------------------
	
	/**
	 *Passa le sfere alla classe per la grafica e le inserisce in un array 
	 * @param tama Tamagolem selezionato
	 * @param squadra 1 squadra1 2 squadra2
	 */
     public void passaSfere(Tamagolem tama,int squadra) {
		
		int elemento1=800;
		int elemento2=800;
		int elemento3=800;
		
		
		for(int i=0;i<6;i++) {
			
			switch(tama.getPietra(i)) {
			    
			case 1:
				
				if(elemento2==800)elemento2=i;
				else if(elemento1==800)elemento1=i;
				else if(elemento3==800)elemento3=i;
				break;
				
				
			case 2:
				elemento1=i;
				elemento3=i;
				break;
				
			case 3:
				elemento2=i;
				elemento1=i;
				elemento3=i;
				break;
				
			default:
			break;
			
			
			}
		}
		
		if(squadra==1) {
			
			//team1.disegnaSfere(elemento1, elemento2, elemento3);
			
			sfereTama1[0]=elemento1;
			sfereTama1[1]=elemento2;
			sfereTama1[2]=elemento3;
		}
		
		else {
			
			//team2.disegnaSfere(elemento1, elemento2, elemento3);
			
			sfereTama2[0]=elemento1;
			sfereTama2[1]=elemento2;
			sfereTama2[2]=elemento3;
		}
		
	}
     
     
     //------------------------------------------------------------------------------------
     
     public void passaSfereGrafica(int squadra) {
    	 
    	 if(squadra==1) {
 			
 			team1.disegnaSfere(sfereTama1[0], sfereTama1[1], sfereTama1[2]);
 			
 			
 		}
 		
 		else {
 			
 			team2.disegnaSfere(sfereTama2[0], sfereTama2[1], sfereTama2[2]);
 			
 			
 		}
    	 
     }
	

   //---------------------------------------------------------------------------
     
     /**
      * Pausa temporale
      * @param tempo la durata della pausa
      */
    private void pausa1(int tempo) {
	
	  try {
		Thread.sleep(tempo);
	  } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
    }
	
    
    //----------------------------------------------------------------------------
    
    public void controlloUguali() {
    	
    	if(sfereTama1[0]==sfereTama2[0] && sfereTama1[1]==sfereTama2[1] && sfereTama1[2]==sfereTama2[2]) {
    		
    		
    		if(sfereTama1[0]==0) {
    			
    			sfereTama1[0]++;
    		}
    		
    		else if(sfereTama1[0]==5) {
    			
    			sfereTama1[0]--;
    			
    		}
    		
    		else {
    			
    			sfereTama1[0]++;
    		}
    		
    		
    	}
    }
}
