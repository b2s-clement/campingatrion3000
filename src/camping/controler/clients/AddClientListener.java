package camping.controler.clients;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import camping.view.dialog.AddClient;

public class AddClientListener implements ActionListener{

	public void actionPerformed(ActionEvent arg0) {
		AddClient ac = new AddClient();
		ac.setVisible(true);
	}
}
