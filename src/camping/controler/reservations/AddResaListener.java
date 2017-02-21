package camping.controler.reservations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import camping.view.dialog.AddReservation;

public class AddResaListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent arg0) {
		AddReservation a = new AddReservation();
		a.setVisible(true);
	}

}
