package camping.view.database;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import camping.model.database.CustomTableModel;
import camping.model.database.ReservDB;

public class ReservPanel extends JPanel{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static int ARRIVEES = 1;
	public static int DEPARTS = 2;

	private static Object[][] data;
	private static String  title[] = {"numLoc", "Client", "Emplacement", "Date arrivée","Date départ", "Etat"};
	private static CustomTableModel  model;
	private static JTable tableau;

	private static int rowSelected=-1;

	public ReservPanel(){
		this.setLayout(new BorderLayout());
		data = ReservDB.getData();
		model = new CustomTableModel(data,title);
		tableau  = new JTable(model);
		tableau.setSelectionMode(0);
		tableau.getTableHeader().setReorderingAllowed(false);
		tableau.setAutoCreateRowSorter(true);
		renderTable();
		this.add(new JScrollPane(tableau),BorderLayout.CENTER);
	}

	public ReservPanel(int v){
		this.setLayout(new BorderLayout());
		data = ReservDB.getData(v);
		model = new CustomTableModel(data,title);
		tableau  = new JTable(model);
		tableau.setSelectionMode(0);
		tableau.getTableHeader().setReorderingAllowed(false);
		tableau.setAutoCreateRowSorter(true);
		renderTable();
		this.add(new JScrollPane(tableau),BorderLayout.CENTER);
	}

	public void renderTable(){
		XTableRenderer rend = new XTableRenderer();
		for(int i=0; i<tableau.getColumnModel().getColumnCount(); i++)
			tableau.getColumnModel().getColumn(i).setCellRenderer(rend);
		tableau.repaint();
	}

	public void updateValues(){
		data = ReservDB.getData();
		model = new CustomTableModel(data,title);
		tableau.setModel(model);
		this.renderTable();
	}
	public void updateValues(int v){
		data = ReservDB.getData(v);
		model = new CustomTableModel(data,title);
		tableau.setModel(model);
		this.renderTable();
	}

	public static void main(String[] args){
		JFrame test = new JFrame();
		ReservPanel lol = new ReservPanel(ReservPanel.DEPARTS);
		test.setContentPane(lol);
		test.setLocationRelativeTo(null);
		test.setSize(new Dimension(600,300));
		test.setVisible(true);
	}
}
