package camping.view.database;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import camping.model.database.*;

public class EmplacementPanel extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static boolean LIBRE = false;
	public static boolean OCCUPE = true;

	private Object[][] data;
	private String[] title = {"Numéro","Type","Etat","Nb. places","Tarif"};
	private CustomTableModel model;
	private JTable tableau;

	public EmplacementPanel(){
		this.setLayout(new BorderLayout());
		data = EmplacementDB.getData();
		model = new CustomTableModel(data,title);
		tableau = new JTable(model);
		tableau.setSelectionMode(0);
		tableau.getTableHeader().setReorderingAllowed(false);
		tableau.setAutoCreateRowSorter(true);
		tableau.getTableHeader().addMouseListener(new InternalRefreshListener());

		renderTable();
		this.add(new JScrollPane(tableau),BorderLayout.CENTER);
		//tableau.addMouseListener();
	}

	public EmplacementPanel(boolean b){
		String[] altTitle = {"Numéro","Type","Nb. places","Tarif"};
		this.setLayout(new BorderLayout());
		data = EmplacementDB.getData(b);
		model = new CustomTableModel(data,altTitle);
		tableau = new JTable(model);
		tableau.setSelectionMode(0);
		tableau.getTableHeader().setReorderingAllowed(false);
		tableau.setAutoCreateRowSorter(true);
		XTableRenderer rend = new XTableRenderer();
		for(int i=0; i<tableau.getColumnModel().getColumnCount(); i++)
			tableau.getColumnModel().getColumn(i).setCellRenderer(rend);
		this.add(new JScrollPane(tableau),BorderLayout.CENTER);
	}

	public void renderTable(){
		XEmpTableRenderer rend = new XEmpTableRenderer(this);
		for(int i=0; i<tableau.getColumnModel().getColumnCount(); i++)
			tableau.getColumnModel().getColumn(i).setCellRenderer(rend);
		tableau.repaint();
	}

	public void updateValues(){
		data = EmplacementDB.getData();
		model = new CustomTableModel(data,title);
		tableau.setModel(model);
		this.renderTable();
	}

	public int getNumEmpAt(int ind){
		return (int) tableau.getValueAt(ind,0);
	}

	public class InternalRefreshListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			EmplacementPanel.this.renderTable();
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {}
		@Override
		public void mouseExited(MouseEvent arg0) {}
		@Override
		public void mousePressed(MouseEvent arg0) {}
		@Override
		public void mouseReleased(MouseEvent arg0) {}
	}
}