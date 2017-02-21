package camping.controler.clients;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import camping.view.database.ClientPanel;
import camping.view.dialog.DetailsClient;

public class DetailsClientListener implements ActionListener{

	public void actionPerformed(ActionEvent arg0) {
		int row = ClientPanel.getRowSelected();
        if (row!=-1) {
            DetailsClient dc = new DetailsClient(ClientPanel.getClientRow(row));
            dc.setVisible(true);
        }
	}

}
