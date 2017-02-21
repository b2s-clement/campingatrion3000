package camping.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import camping.controler.editeur.SaveDataListener;
import camping.model.database.CampingData;
import camping.model.database.EmplacementDB;
import camping.view.dialog.AddEmplacement;
import camping.view.dialog.CustomDialog;
import camping.view.dialog.DetailsEmplacement;
import camping.view.tools.CampingColor;
import camping.view.tools.CustomDarkButton;

public class DrawableCamping extends JPanel{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JPopupMenu lineToolMenu;
    private String selectedTool = "";
    private JLabel selectedToolLabel;
    private JPanel draw;

    //MENU BUTTONS
    CustomDarkButton selectTool;
    CustomDarkButton lineTool;
    CustomDarkButton lineEditTool;
    CustomDarkButton linePlusTool;
    CustomDarkButton lineCampTool;
    CustomDarkButton delTool;

    CustomDarkButton saveTool;
    CustomDarkButton blankTool;

    //ATTRIBUTS DE CONTROLE
        //CONTROLE CLAVIER
    private boolean shiftPressed = false;

    	//CAMPING
    private boolean campingEnEdition = false; //passe vrai quand on selectionne l'outil edition camping
    private boolean campingEdit2 = false; //passe vrai la première fois que l'on termine le polygone camping
    private boolean campingPointClicked = false; //passe vrai quand on clique sur un point du contour du camping
    private boolean campingPointDragged = false;
    private int draggedPointIndex;

        //SELECTION

    private int numEmpSelected=-1;

        //AJOUTER EMPLACEMENT
    private boolean empEnEdition = false;
    private Polygon tempBoundsEmp = new Polygon();
    private HashMap<Point,Point> empLines = new HashMap<Point,Point>();
    int nbEmp = 0;
    private Polygon selectedEmp = new Polygon();

    //Affichage du tracé en temps réel
    private Point previousPoint = null;
    private Point nextPoint = null;
    private HashMap<Point,Point> lines = new HashMap<Point,Point>();
    private HashMap<Integer,Point> points = new HashMap<Integer,Point>();

    //graphic elements
    private Polygon camping = new Polygon();
    private HashMap<Integer,Polygon> emplacements = new HashMap<Integer,Polygon>();

    public DrawableCamping(){
        this.setBackground(CampingColor.LIGHTGREEN);
        this.setOpaque(true);
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createMatteBorder(5,5,5,0, CampingColor.GREEN));

            //MENU DE BOUTONS POUR L'EDITION CARTE


        JPanel menucarte = new JPanel();
        menucarte.setLayout(new BorderLayout());
        menucarte.setPreferredSize(new Dimension(100,600));
        menucarte.setBackground(CampingColor.GREEN);
        menucarte.setOpaque(true);
        menucarte.setBorder(new EmptyBorder(25, 0, 0, 0));

        ImageIcon select = new ImageIcon("img/editeur/select.png");
        ImageIcon line = new ImageIcon("img/editeur/line.png");
        ImageIcon lineEdit = new ImageIcon("img/editeur/line-edit.png");
        ImageIcon linePlus = new ImageIcon("img/editeur/line-plus.png");
        ImageIcon lineCamp = new ImageIcon("img/editeur/line-camping.png");
        ImageIcon del = new ImageIcon("img/editeur/delete.png");

        ImageIcon selectSel = new ImageIcon("img/editeur/select-pushed.png");
        ImageIcon lineSel = new ImageIcon("img/editeur/line-pushed.png");
        ImageIcon lineEditSel = new ImageIcon("img/editeur/line-edit-pushed.png");
        ImageIcon linePlusSel = new ImageIcon("img/editeur/line-plus-pushed.png");
        ImageIcon lineCampSel = new ImageIcon("img/editeur/line-camping-pushed.png");
        ImageIcon delSel = new ImageIcon("img/editeur/delete-pushed.png");

        selectTool = new CustomDarkButton(select);
        selectTool.setPressedIcon(selectSel);
        selectTool.setToolTipText("Sélection");
        selectTool.addActionListener(new SelectToolListener("Sélection"));
        selectTool.setEnabled(false);
        lineTool = new CustomDarkButton(line);
        lineTool.setPressedIcon(lineSel);
        lineTool.setToolTipText("Edition");
        lineEditTool = new CustomDarkButton(lineEdit);
        lineEditTool.setPressedIcon(lineEditSel);
        lineEditTool.addActionListener(new SelectToolListener("Editer emplacement"));
        lineEditTool.setToolTipText("Editer emplacement");
        lineEditTool.setEnabled(false);
        linePlusTool = new CustomDarkButton(linePlus);
        linePlusTool.setPressedIcon(linePlusSel);
        linePlusTool.addActionListener(new SelectToolListener("Ajouter emplacement"));
        linePlusTool.setToolTipText("Ajouter emplacement");
        linePlusTool.setEnabled(false);
        lineCampTool = new CustomDarkButton(lineCamp);
        lineCampTool.setPressedIcon(lineCampSel);
        lineCampTool.addActionListener(new SelectToolListener("Editer camping"));
        lineCampTool.setToolTipText("Editer camping");
        delTool = new CustomDarkButton(del);
        delTool.setPressedIcon(delSel);
        delTool.setToolTipText("Suppresion");
        delTool.addActionListener(new SelectToolListener("Supprimer emplacement"));
        delTool.setEnabled(false);

        JLabel outils = new JLabel("Outils :");
        outils.setOpaque(false);
        outils.setForeground(CampingColor.LIGHTGREY);
        outils.setPreferredSize(new Dimension(80,25));
        outils.setBorder(BorderFactory.createMatteBorder(0,0,1,0, CampingColor.LIGHTGREY));

        lineToolMenu = new JPopupMenu();
        lineToolMenu.add(linePlusTool);
        lineToolMenu.add(lineEditTool);
        lineToolMenu.add(lineCampTool);
        lineTool.addMouseListener(new BringPopupListener());

        JPanel toolEdit = new JPanel();
        toolEdit.setOpaque(false);

        toolEdit.add(outils);
        toolEdit.add(Box.createRigidArea(new Dimension(80,12)));
        toolEdit.add(selectTool);
        toolEdit.add(lineTool);
        toolEdit.add(delTool);

        JPanel toolData = new JPanel();
        toolData.setOpaque(false);

        ImageIcon save = new ImageIcon("img/editeur/save.png");
        ImageIcon saveSel = new ImageIcon("img/editeur/save-pushed.png");
        ImageIcon blank = new ImageIcon("img/editeur/blank.png");
        ImageIcon blankSel = new ImageIcon("img/editeur/blank-pushed.png");
        saveTool = new CustomDarkButton(save);
        saveTool.setPressedIcon(saveSel);
        saveTool.setToolTipText("Sauvegarder");
        saveTool.setEnabled(false);
        saveTool.addActionListener(new SaveDataListener(this));
        blankTool = new CustomDarkButton(blank);
        blankTool.setPressedIcon(blankSel);
        blankTool.setToolTipText("Nouveau");
        blankTool.addActionListener(new BlankCampingListener());
        blankTool.setEnabled(false);

        toolData.add(saveTool);
        toolData.add(blankTool);
        toolData.setPreferredSize(new Dimension(30,130));

        menucarte.add(toolEdit,BorderLayout.CENTER);
        menucarte.add(toolData,BorderLayout.SOUTH);

        this.add(menucarte,BorderLayout.EAST);

        JPanel infoCarte = new JPanel();
        infoCarte.setBackground(CampingColor.GREEN);
        infoCarte.setLayout(new BorderLayout());
        selectedToolLabel = new JLabel("Outil sélectionné : Aucun");
        selectedToolLabel.setForeground(CampingColor.LIGHTGREY);
        selectedToolLabel.setBorder(new EmptyBorder(5,5,5,5));
        infoCarte.add(selectedToolLabel,BorderLayout.EAST);

        this.add(infoCarte,BorderLayout.SOUTH);

        //ZONE DE DESSIN
        draw = new JPanel(){
            /**
             *
             */
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                 super.paintComponent(g);
                 Graphics2D g2 = (Graphics2D) g;
                 g2.setColor(Color.BLUE);
                 //Affichage du polygone camping
                 if(campingEdit2){
                     g.setColor(CampingColor.LIGHTGREY);
                     g.fillPolygon(camping);
                     g2.setStroke(new BasicStroke(3));
                     g2.setColor(CampingColor.GREEN);
                     g2.drawPolygon(camping);
                     if(selectedTool.equals("Editer camping")){
                         g2.setColor(Color.BLUE);
                         for(Entry<Integer, Point> p : points.entrySet()){
                             g2.fillOval((int)p.getValue().getX()-5,(int)p.getValue().getY()-5,10,10);
                         }
                     }
                    // g2.setColor(Color.BLUE);
                    // g2.fillPolygon(tempBoundsEmp);
                   //AFFICHAGE DES EMPLACEMENTS
                     for(Entry<Integer,Polygon> e: emplacements.entrySet()){
                         g2.setColor(CampingColor.GREEN);
                         g2.setStroke(new BasicStroke(3));
                         g2.drawPolygon(e.getValue());
                         g2.setColor(CampingColor.MIDDLEGREEN);
                         g2.fillPolygon(e.getValue());
                         g2.setColor(CampingColor.GREEN);
                         g2.drawString(Integer.toString(e.getKey()), (int)e.getValue().getBounds().getCenterX(), (int)e.getValue().getBounds().getCenterY());
                     }

                     //AFFICHAGE DE L'EMPLACEMENT SELECTIONN2
                     if(selectedTool.equals("Editer emplacement")&&numEmpSelected!=-1){
                    	 g2.setColor(Color.BLUE);
                    	 for(int i=0;i<selectedEmp.npoints;i++){
                    		 g2.fillOval(selectedEmp.xpoints[i]-5,selectedEmp.ypoints[i]-5,10,10);
                    	 }
                     }
                 }
	                 //Affichage de tous les traits enregistrés
	                 g2.setStroke(new BasicStroke(3));
	                 for (Entry<Point, Point> e : lines.entrySet()) {
	                         g2.setColor(CampingColor.GREEN);
	                         g2.drawLine((int)e.getKey().getX(),(int)e.getKey().getY(),(int)e.getValue().getX(),(int)e.getValue().getY());
	                         g2.setColor(Color.BLUE);
	                         g2.fillOval((int)e.getKey().getX()-5,(int)e.getKey().getY()-5,10,10);
	                         g2.fillOval((int)e.getValue().getX()-5,(int)e.getValue().getY()-5,10,10);
	                 }

                 //Affichage du trait en cours
                 try{
                	 g2.setColor(Color.BLUE);
                     g2.drawLine((int)previousPoint.getX(), (int)previousPoint.getY(), (int)nextPoint.getX(), (int)nextPoint.getY());
                 }catch(Exception e){}


                 //RESCALING TO PARENT JPANEL
                    /*try{
                         double wPanel = draw.getSize().getWidth();
                         double hPanel = draw.getSize().getHeight();
                         double wActual = camping.getBounds().getWidth();
                         double hActual = camping.getBounds().getHeight();
                         double wRatio = wPanel/wActual;
                         double hRatio = hPanel/hActual;
                         g2.scale(wRatio,hRatio);
                         System.out.println("SUCCESS : w : "+wRatio+" / h : "+hRatio);
                     }catch(ArithmeticException e){
                         System.out.println("RESCALING CACA FAILED");
                     }*/
                 }
        };
        draw.setBackground(CampingColor.LIGHTGREEN);
        draw.addMouseListener(new EditCampingListener());
        draw.addMouseMotionListener(new RealTimeDrawListener());
        draw.setFocusable(true);
        draw.addKeyListener(new ForceLineListener());
        this.add(draw,BorderLayout.CENTER);
        this.setDataFromSave();

    }

    public void resetData(){
    	//RESET DES DONNEES
    	camping.reset();
    	emplacements.clear();
    	campingEnEdition=false;
    	campingEdit2=false;
    	campingPointClicked=false;
    	campingPointDragged=false;
        numEmpSelected=-1;
        empEnEdition = false;
        shiftPressed = false;
        tempBoundsEmp.reset();
        empLines.clear();
        nbEmp = 0;
        previousPoint = null;
        nextPoint = null;
        lines.clear();
        points.clear();
    	draw.repaint();

    	//DESATIVATION DES BOUTONS
    	selectTool.setEnabled(false);
    	linePlusTool.setEnabled(false);
    	lineEditTool.setEnabled(false);
    	delTool.setEnabled(false);
    	saveTool.setEnabled(false);

    	//RESET DE LA BDD
    	EmplacementDB.supprimerAllEmplacement();
    }

    public boolean isCampingEdit2(){
        int firstX = camping.xpoints[0];
        int firstY = camping.ypoints[0];
        int lastX = (int)nextPoint.getX();
        int lastY = (int)nextPoint.getY();
        return (Math.abs(lastX-firstX)<5)&&(Math.abs(lastY-firstY)<5);
    }
    public boolean isEmpEdit2(){
        int firstX = tempBoundsEmp.xpoints[0];
        int firstY = tempBoundsEmp.ypoints[0];
        int lastX = (int)nextPoint.getX();
        int lastY = (int)nextPoint.getY();
        return (Math.abs(lastX-firstX)<5)&&(Math.abs(lastY-firstY)<5);
    }

    public void finishAction(){
        selectedTool="Sélection";
        selectedToolLabel.setText("Outil sélectionné : "+selectedTool);
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        campingEnEdition=false;
    }

    public boolean isCampingBoundsClicked(Point e){
        boolean res=false;
        int clickX = (int)e.getX();
        int clickY = (int)e.getY();
        for(int i=0;i<camping.npoints&&!res;i++){
            res= (Math.abs(camping.xpoints[i]-clickX)<5)&&(Math.abs(camping.ypoints[i]-clickY)<5);
            if(res){
                draggedPointIndex=i;
            }
        }
        return res;
    }
    public int empSelectedPointClicked(Point e){
    	int res = -1;
    	int clickX = (int)e.getX();
        int clickY = (int)e.getY();
        int i=0;
        while(i<selectedEmp.npoints&&res==-1){
            if((Math.abs(selectedEmp.xpoints[i]-clickX)<5)&&(Math.abs(selectedEmp.ypoints[i]-clickY)<5)){
            	res=i;
            }
            i++;
        }
    	return res;
    }

    public int getEmpSelected(Point e){
    	int res = -1;
    	for(Entry<Integer,Polygon> entry : emplacements.entrySet()){
    		if(entry.getValue().contains(e)){
    			res=entry.getKey();
    			break;
    		}
    	}
    	return res;
    }


    //GETTERS UTILIS2S POUR LA SAUVEGARDE
    public Polygon getCamping(){
    	return camping;
    }
    public HashMap<Integer,Polygon> getEmplacements(){
    	return emplacements;
    }

    // RECUPERATION DES DONNEES DEPUIS LA SAUVEGARDE
    public void setDataFromSave(){
    	try{
	    	camping=CampingData.getCamping("camping.c3k");
	    	emplacements=CampingData.getEmplacements("emplacements.c3k");
	    	campingEdit2=true;
	    	draw.repaint();
	    	selectTool.setEnabled(true);
	    	saveTool.setEnabled(true);
	    	blankTool.setEnabled(true);
	    	linePlusTool.setEnabled(true);
	    	if(!emplacements.isEmpty()){
	    		lineEditTool.setEnabled(true);
	    		delTool.setEnabled(true);
	    	}
	    	for(Entry<Integer,Polygon> emp : emplacements.entrySet()){
        		if(emp.getKey()>nbEmp){
        			nbEmp=emp.getKey();
        		}
	    	}
	    	nbEmp++;
    	}catch(ClassNotFoundException e){
    	}catch(IOException e){}
    }

    public void setNewNum(int prevNB, int nextNB){
    	emplacements.put(nextNB, emplacements.get(prevNB));
    	emplacements.remove(prevNB);
    	draw.repaint();
    }

    public void cancelEmp(int n){
    	emplacements.remove(n);
    	draw.repaint();
    }

    public class BringPopupListener implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent arg0) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
        @Override
        public void mousePressed(MouseEvent e) {
            lineToolMenu.show(e.getComponent(),e.getX(), e.getY());
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            lineToolMenu.show(e.getComponent(),e.getX(), e.getY());
        }
    }
    public class SelectToolListener implements ActionListener{
        private String st;
        public SelectToolListener(String s){
            super();
            st=s;
        }
        @Override
        public void actionPerformed(ActionEvent arg0) {
            selectedTool=st;
            selectedToolLabel.setText("Outil sélectionné : "+st);
            lineToolMenu.setVisible(false);
            Cursor ncur;
            if(st.equals("Editer emplacement")||st.equals("Editer camping")||st.equals("Ajouter emplacement")){
                ncur= new Cursor(Cursor.CROSSHAIR_CURSOR);
            }else if(st.equals("Supprimer emplacement")){
                ncur = Toolkit.getDefaultToolkit().createCustomCursor(
                        new ImageIcon("img/editeur/delete-cursor.png").getImage(),
                        new Point(0,0),"custom cursor");
            }else{
                ncur = new Cursor(Cursor.DEFAULT_CURSOR);
            }
            DrawableCamping.this.setCursor(ncur);
            if(selectedTool.equals("Editer camping")&&campingEdit2){
                    for(int i=0;i<camping.npoints;i++){
                        points.put(i, new Point(camping.xpoints[i],camping.ypoints[i]));
                    }
                    draw.repaint();
                }else{
                    campingEnEdition=false;
                    points.clear();
                    draw.repaint();
               // }
            }
        }
    }
    public class RealTimeDrawListener implements MouseMotionListener{

        @Override
        public void mouseDragged(MouseEvent e) {

            //DEPLACEMENT DES POINTS DU CONTOUR DU CAMPING
        	if(selectedTool.equals("Editer camping")){

	            if(!campingPointClicked){
	                campingPointClicked=DrawableCamping.this.isCampingBoundsClicked(e.getPoint());
	                if(!campingPointClicked&&!campingPointDragged){
	                    draggedPointIndex=-1;
	                }
	            }
	            if(campingPointClicked&&draggedPointIndex!=-1){
	                camping.xpoints[draggedPointIndex]=(int)e.getPoint().getX();
	                camping.ypoints[draggedPointIndex]=(int)e.getPoint().getY();
	                points.replace(draggedPointIndex, e.getPoint());
	                draw.repaint();
	            }
	            draggedPointIndex=-1;
	            campingPointClicked=false;

        	}
            //DEPLACEMENT DES POINTS DES EMPLACEMENTS
        	else if(selectedTool.equals("Editer emplacement")){
        		int ind = DrawableCamping.this.empSelectedPointClicked(e.getPoint());
        		if(ind!=-1){
        			selectedEmp.xpoints[ind]=(int)e.getPoint().getX();
        			selectedEmp.ypoints[ind]=(int)e.getPoint().getY();
                    draw.repaint();
        		}
        	}
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            //SHIFT OR NOT SHIFT ? THAT'S THE QUESTION
        	Point currentPoint = e.getPoint();
        	try{
	        	if(shiftPressed){
	        		if(Math.abs(currentPoint.getX()-previousPoint.getX())>Math.abs(currentPoint.getY()-previousPoint.getY())){
	        			//currentPoint.setLocation(currentPoint.getX(), previousPoint.getY());
	        			currentPoint = new Point((int)currentPoint.getX(),(int)previousPoint.getY());
	        		}else{
	        			currentPoint=new Point((int)previousPoint.getX(),(int)currentPoint.getY());
	        		}
	        	}
        	}catch(NullPointerException error){}
        	//COMPORTEMENT
        	if(!campingEdit2||empEnEdition){
                nextPoint=currentPoint;
                draw.repaint();
            }
        }

    }
    public class EditCampingListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
        	draw.requestFocus();
        	//SHIFT OR NOT SHIFT ? THAT'S THE QUESTION
        	Point currentPoint = e.getPoint();
        	try{
	        	if(shiftPressed){
	        		if(Math.abs(currentPoint.getX()-previousPoint.getX())>Math.abs(currentPoint.getY()-previousPoint.getY())){
	        			currentPoint = new Point((int)currentPoint.getX(),(int)previousPoint.getY());
	        		}else{
	        			currentPoint=new Point((int)previousPoint.getX(),(int)currentPoint.getY());
	        		}
	        	}
        	}catch(NullPointerException error){}

            //OUTIL SELECTION
            if(selectedTool.equals("Sélection")){
            	numEmpSelected=DrawableCamping.this.getEmpSelected(e.getPoint());
                if(numEmpSelected!=-1){
                	DetailsEmplacement de = new DetailsEmplacement(DrawableCamping.this,numEmpSelected);
                	de.setVisible(true);
                }

            // OUTIL CAMPING
            }else if(selectedTool.equals("Editer camping")){

                //AVANT EDITION - PAS DE CAMPING DESSIN2
                if(!campingEnEdition&&!campingEdit2){
                    campingEnEdition=true;
                    previousPoint=currentPoint;
                    camping.addPoint((int)currentPoint.getX(), (int)currentPoint.getY());
                //EN COURS DE PREMIERE EDITION
                }else if(!campingEdit2){
                    camping.addPoint((int)currentPoint.getX(), (int)currentPoint.getY());
                    campingEdit2=DrawableCamping.this.isCampingEdit2();
                    if(!campingEdit2){
                        lines.put(previousPoint, nextPoint);
                    }else{
                        lines.clear();
                        linePlusTool.setEnabled(true);
                        selectTool.setEnabled(true);
                        saveTool.setEnabled(true);
                        blankTool.setEnabled(true);
                        DrawableCamping.this.finishAction();
                    }
                    draw.repaint();
                    previousPoint=nextPoint;
                }
                // APRES : RIEN


            // OUTIL AJOUTER EMPLACEMENT
            }else if(selectedTool.equals("Ajouter emplacement")){

                if(!empEnEdition){
                    empEnEdition=true;
                    previousPoint=currentPoint;
                    tempBoundsEmp.addPoint((int)currentPoint.getX(), (int)currentPoint.getY());
                }else{
                    tempBoundsEmp.addPoint((int)currentPoint.getX(), (int)currentPoint.getY());
                    lines.put(previousPoint, nextPoint);
                    draw.repaint();
                    previousPoint=nextPoint;
                    if(DrawableCamping.this.isEmpEdit2()){
                    	empEnEdition=false;
                    	lines.clear();
                    	nbEmp=0;
                    	while(EmplacementDB.isNumEmpTaken(nbEmp)){
                    		nbEmp++;
                    	}
                    	emplacements.put(nbEmp, new Polygon(tempBoundsEmp.xpoints,tempBoundsEmp.ypoints,tempBoundsEmp.npoints));
                    	AddEmplacement creator = new AddEmplacement(DrawableCamping.this, nbEmp);
                    	creator.setVisible(true);
                    	tempBoundsEmp.reset();
                    	draw.repaint();

                    	//Edition et suppression possibles
                    	lineEditTool.setEnabled(true);
                    	delTool.setEnabled(true);
                    }
                }

            //OUTIL EDITER EMPLACEMENT
            }else if(selectedTool.equals("Editer emplacement")){
                numEmpSelected=DrawableCamping.this.getEmpSelected(e.getPoint());
                if(numEmpSelected!=-1){
                	selectedEmp=emplacements.get(numEmpSelected);
                }else{
                	selectedEmp=new Polygon();
                }
                draw.repaint();

            //OUTIL SUPPRIMER
            }else if(selectedTool.equals("Supprimer emplacement")){
            	try{
	                for(Entry<Integer,Polygon> emp : emplacements.entrySet()){
	                		if(emp.getValue().contains(e.getPoint())){
	                            if(CustomDialog.confirmDialog("Supprimer emplacement", "Vous-voulez vous vraiment supprimer l'emplacement ?")==CustomDialog.commited){
		                            emplacements.remove(emp.getKey());
		                            EmplacementDB.supprimerEmplacement(emp.getKey());
		                            draw.repaint();
	                            }
	                        }

	                }
	                if(emplacements.isEmpty()){
	                	lineEditTool.setEnabled(false);
	                }
            	}catch(java.util.ConcurrentModificationException error){}

            }
        }

		@Override
        public void mouseEntered(MouseEvent arg0) {}
        @Override
        public void mouseExited(MouseEvent arg0) {}
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent arg0) {}
    }

    public class BlankCampingListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(CustomDialog.confirmDialog("Nouveau", "Voulez-vous supprimer ce camping et en démarrer un nouveau ?")==CustomDialog.commited){
				DrawableCamping.this.resetData();
				CustomDialog.infoDialog("Données supprimées", "Les données du camping ont bien été supprimées.");
			}
		}
    }

    public class ForceLineListener implements KeyListener{
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==16){   // si on appuie sur shift
				shiftPressed=true;
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {
			shiftPressed=false;
		}
		@Override
		public void keyTyped(KeyEvent e) {}
    }
}
