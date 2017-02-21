package camping.controler.editeur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import camping.view.database.EmplacementPanel;

public class UpdateValuesListener implements ActionListener{

	private EmplacementPanel cible;

	public UpdateValuesListener(EmplacementPanel e){
		super();
		cible=e;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		cible.updateValues();
	}

}
