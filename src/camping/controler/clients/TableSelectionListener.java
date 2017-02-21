package camping.controler.clients;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;

import camping.view.database.ClientPanel;
import camping.view.dialog.DetailsClient;

public class TableSelectionListener implements MouseListener{

	public void mouseClicked(MouseEvent e){
		JTable table =(JTable) e.getSource();
        java.awt.Point p = e.getPoint();
        int row = table.rowAtPoint(p);
        if(row!=-1){
        	ClientPanel.selectRow(row);
        }
	}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}

	public void mousePressed(MouseEvent e) {
		JTable table =(JTable) e.getSource();
        java.awt.Point p = e.getPoint();
        int row = table.rowAtPoint(p);
        if (e.getClickCount() == 2 && row!=-1) {
            DetailsClient dc = new DetailsClient(ClientPanel.getClientRow(row));
            dc.setVisible(true);
        }
    }
}
