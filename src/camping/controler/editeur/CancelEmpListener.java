package camping.controler.editeur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import camping.view.DrawableCamping;
import camping.view.dialog.AddEmplacement;

public class CancelEmpListener implements ActionListener{

	private AddEmplacement parent;
	private DrawableCamping grandparent;

	public CancelEmpListener(DrawableCamping dc,AddEmplacement a){
		super();
		parent=a;
		grandparent=dc;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		grandparent.cancelEmp(parent.getPreviousNB());

	}

}
