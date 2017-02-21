package camping.view.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import camping.controler.editeur.AddEmpListener;
import camping.controler.editeur.CancelEmpListener;
import camping.model.database.EmplacementDB;
import camping.view.DrawableCamping;
import camping.view.tools.CampingColor;
import camping.view.tools.CustomButton;

public class AddEmplacement extends JDialog {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    //elements
    protected CustomButton ok;
    protected CustomButton annuler;

    private int previousNB;

    private int numEmp;
    protected JTextField numEmpData;
    private JLabel numEmpError;
    private boolean numEmpCorrect=true;

    protected JComboBox<Integer> nbPersData;
    protected JTextField prixData;
    protected JLabel prixError;
    protected boolean prixCorrect;
    protected double prixValue=0.0;

    protected String typeData="";
    protected JRadioButton loc;
    protected JRadioButton emp;
    protected boolean typeSelected=false;

    public AddEmplacement(DrawableCamping dc, int n){
    	previousNB=n;
    	numEmp=n;
        this.setSize(new Dimension(400,300));
        this.setResizable(false);
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.setTitle("Nouvel emplacement");
        this.createGUI(dc,n);
    }
    public void createGUI(DrawableCamping dc, int n){
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.setBackground(CampingColor.LIGHTGREY);
        content.setBorder(new EmptyBorder(5,5,5,5));

        //BOUTONS OK/ANNULER
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons,BoxLayout.X_AXIS));
        ok = new CustomButton("OK");
        ok.setPreferredSize(new Dimension(60,30));
        ok.addActionListener(new CloseDialogListener());
        ok.addActionListener(new AddEmpListener(dc,this));
        ok.setEnabled(false);
        annuler = new CustomButton("Annuler");
        annuler.setPreferredSize(new Dimension(80,30));
        annuler.addActionListener(new CloseDialogListener());
        annuler.addActionListener(new CancelEmpListener(dc,this));
        buttons.add(Box.createHorizontalGlue());
        buttons.add(ok);
        buttons.add(Box.createRigidArea(new Dimension(5,5)));
        buttons.add(annuler);
        buttons.setOpaque(false);

        //Champs à remplir
        JPanel champs = new JPanel();
        champs.setLayout(new BoxLayout(champs,BoxLayout.Y_AXIS));
        champs.setOpaque(false);

        JPanel numEmp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        numEmp.setOpaque(false);
        JPanel numEmpInfo = new JPanel();
        numEmpInfo.setOpaque(false);
        numEmpInfo.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JLabel numEmpInfoL = new JLabel("Numéro Emp. : ");
        numEmpInfo.add(numEmpInfoL);
        numEmpData = new JTextField(3);
        numEmpData.setText(Integer.toString(n));
        numEmpData.requestFocus();
        numEmpData.selectAll();
        numEmpData.addKeyListener(new FormNumEmpListener());
        numEmpError = new JLabel();
        numEmpError.setOpaque(false);
        numEmpError.setForeground(Color.RED);
        numEmp.add(numEmpInfo);
        numEmp.add(numEmpData);
        numEmp.add(numEmpError);

        JPanel type = new JPanel(new FlowLayout(FlowLayout.LEFT));
        type.setOpaque(false);
        JPanel typeInfo = new JPanel();
        typeInfo.setOpaque(false);
		typeInfo.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JLabel typeInfoL = new JLabel("Type : ");
		typeInfo.add(typeInfoL);
		loc = new JRadioButton("Location");
		loc.setOpaque(false);
		loc.addActionListener(new InternalTypeListener());
		emp = new JRadioButton("Emplacement");
		emp.setOpaque(false);
		emp.addActionListener(new InternalTypeListener());
		ButtonGroup group = new ButtonGroup();
		group.add(loc);
		group.add(emp);
		type.add(typeInfo);
		type.add(loc);
		type.add(emp);

        JPanel nbPers = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nbPers.setOpaque(false);
        JPanel nbPersInfo = new JPanel();
        nbPersInfo.setOpaque(false);
        nbPersInfo.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JLabel nbPersInfoL = new JLabel("Nb personnes max. : ");
        nbPersInfo.add(nbPersInfoL);
        Integer[] nbPersoPoss ={1,2,3,4,5,6,7,8,9,10};
        nbPersData = new JComboBox<Integer>(nbPersoPoss);
        nbPers.add(nbPersInfo);
        nbPers.add(nbPersData);

        JPanel prix = new JPanel(new FlowLayout(FlowLayout.LEFT));
        prix.setOpaque(false);
        JPanel prixInfo = new JPanel();
        prixInfo.setOpaque(false);
        prixInfo.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JLabel prixInfoL = new JLabel("Tarif : ");
        prixInfo.add(prixInfoL);
        prixData = new JTextField(5);
        prixData.addKeyListener(new FormPrixListener());
        JLabel prixAfter = new JLabel("euros/nuit");
        prixError = new JLabel();
        prixError.setOpaque(false);
        prixError.setForeground(Color.RED);
        prix.add(prixInfo);
        prix.add(prixData);
        prix.add(prixAfter);
        prix.add(prixError);


        //set size for every label :
      	JPanel[] panels = {numEmpInfo,typeInfo, nbPersInfo,prixInfo};
      	int maxW = 0;
      	for(JPanel l:panels){
      		int cL = l.getPreferredSize().width;
      		if(cL>maxW){
      			maxW=cL;
      		}
      	}
      	for(JPanel l:panels){
      		l.setPreferredSize(new Dimension(maxW,l.getPreferredSize().height));
      	}

        champs.add(numEmp);
        champs.add(type);
        champs.add(nbPers);
        champs.add(prix);

        //Implémentation finale
        content.add(champs,BorderLayout.CENTER);
        content.add(buttons,BorderLayout.SOUTH);
        this.setContentPane(content);
    }

    //GETTERS POUR ENREGISTRER LE RESULTAT
    public int getNumEmp(){return Integer.parseInt(numEmpData.getText());}
    public String getTypeSel(){return typeData;}
    public int getNbPers(){return (int) nbPersData.getSelectedItem();}
    public double getPrix(){return prixValue;}
    public int getPreviousNB(){return previousNB;}

    //SETTERS


    public class CloseDialogListener implements ActionListener{
        public void actionPerformed(ActionEvent arg0) {
            dispose();
        }
    }

    public class FormNumEmpListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent a) {

		}

		@Override
		public void keyReleased(KeyEvent a){
			try{
				int curr = Integer.parseInt(numEmpData.getText()/*+a.getKeyChar()*/);
				if(!EmplacementDB.isNumEmpTaken(curr)){
					numEmp=curr;
					numEmpError.setText("");
					numEmpCorrect=true;
				}else{
					numEmpCorrect=false;
					numEmpError.setText("Numéro déjà pris.");
				}
			}catch(NumberFormatException e){
				numEmpCorrect=false;
				numEmpError.setText("Entier attendu.");
			}
			if(numEmpCorrect&&typeSelected&&prixCorrect){
				ok.setEnabled(true);
			}else{
				ok.setEnabled(false);
			}
		}

		@Override
		public void keyTyped(KeyEvent arg0){}

    }
    public class FormPrixListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent a) {
			if(a.getKeyChar()==','){
				prixData.setText(prixData.getText()+'.');
			}
		}

		@Override
		public void keyReleased(KeyEvent a) {
			if(a.getKeyChar()==','){
				String res = (prixData.getText()+'.');
				prixData.setText(res.substring(0,res.length()-2));
			}
			try{
				prixValue=Double.parseDouble(prixData.getText());
				prixCorrect=true;
				prixError.setText("");
			}catch(NumberFormatException e){
				prixError.setText("Nombre décimal attendu.");
				prixCorrect=false;
			}
			if(numEmpCorrect&&typeSelected&&prixCorrect){
				ok.setEnabled(true);
			}else{
				ok.setEnabled(false);
			}
		}
		@Override
		public void keyTyped(KeyEvent arg0) {}
    }

    public class InternalTypeListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			typeData=e.getActionCommand();
			typeSelected=true;
			if(numEmpCorrect&&typeSelected&&prixCorrect){
				ok.setEnabled(true);
			}else{
				ok.setEnabled(false);
			}
		}
    }
}
