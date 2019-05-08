package it.PgArnaldo.Tamagolem_KT;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class IGestioneSquad implements ActionListener{

	private JButton avanti;
	private JPanel panel3;
	private JPanel panel4;
	private JLabel label1;
	private JLabel label2;
	
	private ArrayList<PanelScelta> scelte= new ArrayList<PanelScelta>();
	private String [] nomi= { "Gaetano","Perleka","Ciro","Artjam","Cerriso","Foxtrot","Kilo","Hantress","Iamme-Ya","Cromosomum"};
	private BorderLayout bordi;
	private  BorderLayout bordi2;
	private int clicked=0;
	private int conteggio=0;
	private String nomeGiocatore;
	private Squadra squad;
	
	private  int numTamaSquad=6;
	private int exit=0;
	
	
	//---------------------------------------------------------------------------
	
	/**
	 * Classe adibita all' inizializzazione delle squadre e alla selezione del nuvo tamagolem una volta morto il precedente
	 * @param _nomeGiocatore Nome del giocatore
	 * @param _squad Squadra del giocatore
	 */
	public IGestioneSquad(String _nomeGiocatore,Squadra _squad) {
		// TODO Auto-generated constructor stub
	
		nomeGiocatore=_nomeGiocatore;
		squad=_squad;
	
	}
	
	//-----------------------------------------------------------------------------------------------------
	
	/**
	 * Disegna i vari blocchi a schermo riponendoli sul JPanel di riferimento
	 * @param panelA JPanel di riferimento su cui inserire i componenti
	 */
	public void AggiungiElementi(JPanel panelA) {
		
		
		bordi=new BorderLayout();
	    bordi2=new BorderLayout();
		
		avanti = new JButton("Conferma");
		avanti.setSize(80, 80);
		
		bordi.setHgap(70);
		bordi2.setVgap(70);
		
		panelA.setLayout(bordi);
	
		panel3=new JPanel(new GridLayout(5,2));
		panel4=new JPanel(bordi2);
		
		
		
		panelA.add(panel3,BorderLayout.CENTER);
		panelA.add(panel4,BorderLayout.EAST);
		
		
		
		
		label1 = new JLabel(nomeGiocatore);
		label2 = new JLabel("dic");
		
		
		
		 
		inizArray(0);
		
		
	    for(int i=0;i<scelte.size();i++) {
		
	    	panel3.add(scelte.get(i));
	    }
         
           
        panel4.add(label1,BorderLayout.NORTH);
        panel4.add(avanti,BorderLayout.SOUTH);
       
        avanti.setVisible(false);
		
		panel3.setVisible(true);
		panel4.setVisible(true);
		
		panelA.validate();
		panelA.repaint();
		
		avanti.addActionListener(this);
		
		controlNumSquad();
		aggiungiTama();
		
		panelA.removeAll();
		
	}
	
	//--------------------------------------------------------------------------------
    
	/**
	 * Inizializza l' array scelta creando le rispettive caselle per la scelta dei Tamagolem
	 * @param select Inserire 0 se si deve inizializzare la squadra per la prima volta. Inserire 1 se si vuole cambiare il Tamagolem
	 */
	private void inizArray(int select) {
		
		if (select==0) {
		for(int i=0;i<nomi.length;i++) {
		scelte.add(new PanelScelta(nomi[i]));
		}   
		}
		
		else if(select==1) {
			for(int i=0;i<squad.returnSize();i++) {
				scelte.add(new PanelScelta(squad.getTama(i).getNome()));
				}   
		}
	
    }
	
	
	//--------------------------------------------------------------------------------
	
	/**
	 * Controlla il numero di volte che una chekBox è stata selezionata e quindi il numero massimo di tamagolem selezionabili. Una volta raggiunto fa apparire il pulsante per continuare
	 */
	private void controlNumSquad(){
		
		vivoL:
		do {
			if(exit==1)break vivoL;
		for(int i=0;i<scelte.size();i++) {
			
			if(exit==1)break vivoL;
	    	
			if(scelte.get(i).getIsSelected()==true  && scelte.get(i).giaP()==false) {
	    		
	    		conteggio++;
	    		scelte.get(i).setgiaP(true);
	    		
	    	}
	    	else if(scelte.get(i).getSelection() ==true && scelte.get(i).giaP()==true ) {
	    		
	    		scelte.get(i).setgiaP(false);
	    		conteggio--;
	    		
	    	}
	    	
	    }
		
		if (conteggio==numTamaSquad) {
			
			avanti.setVisible(true);
		}
		
		else avanti.setVisible(false);
		
		}while(clicked==0);
		}

	//-------------------------------------------------------------------------------------
	
	/**
	 * Funzione per rilevare se il pulsante è stato premuto
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		clicked++;
		
		
	}
	
	
	//--------------------------------------------------------------------------------
	
	/**
	 * Aggiunge il Tamagolem selezionata alla squadra
	 */
	public void aggiungiTama() {
		
		vivo1:
		for(int i=0;i<scelte.size();i++) {
			
			if(exit==1)break vivo1;
			
			if(scelte.get(i).getIsSelected()==true) {
				squad.addTama(scelte.get(i).getTamaName());
			}
		}
		
		
		
		
	}
	
	
	//------------------------------------------------------------------------------------
	
	/**
	 * Seleziona un altro tamagolem presente nella squadra
	 * @param panelA JPanel su cui disegnare 
	 * @return Ritorna il Tamagolem selezionato
	 */
	public int evocaAltroTama(JPanel panelA) {
		
		int t1=0;
		numTamaSquad=1;
		
		bordi=new BorderLayout();
	    bordi2=new BorderLayout();
		
		avanti = new JButton("Conferma");
		avanti.setSize(80, 80);
		
		bordi.setHgap(70);
		bordi2.setVgap(70);
		
		panelA.setLayout(bordi);
	
		panel3=new JPanel(new GridLayout(5,2));
		panel4=new JPanel(bordi2);
		
		
		
		panelA.add(panel3,BorderLayout.CENTER);
		panelA.add(panel4,BorderLayout.EAST);
		
		
		
		
		label1 = new JLabel(nomeGiocatore+" evoca un altro Tamagolem");
		label2 = new JLabel("dic");
		
		inizArray(1);
		
		vivo2:
		for(int i=0;i<scelte.size();i++) {
			
			if(exit==1)break vivo2;
	    	
			panel3.add(scelte.get(i));
	    }
		
		panel4.add(label1,BorderLayout.NORTH);
        panel4.add(avanti,BorderLayout.SOUTH);
       
        avanti.setVisible(false);
		
		panel3.setVisible(true);
		panel4.setVisible(true);
		
		panelA.validate();
		panelA.repaint();
		
        avanti.addActionListener(this);
		
		controlNumSquad();
		
		vivo3:
            for(int i=0;i<scelte.size();i++) {
            	
            	if(exit==1)break vivo3;
            	
			if(scelte.get(i).getIsSelected()==true) {
				t1=i;
			}
		}

            panelA.removeAll();
            
		return t1;
	}
	
	
	//---------------------------------------------------------------------------------------------------------
	
	/**
	 * Resetta le risorse del programma
	 */
	public void resetRis() {
		
		
		 scelte.clear();
		 clicked=0;
		 conteggio=0;
	
	}

	
}
