package camping.controler.home;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import camping.view.Home;
import camping.view.dialog.CustomDialog;

public class EditTitleListener implements ActionListener{

	public void actionPerformed(ActionEvent e) {
		String current = Home.getTitle();

		String newOne = CustomDialog.inputDialog("Renommer le camping", "Nouveau nom du camping :", current);
		if(!newOne.equals("")){
			Home.setTitle(newOne);
		}
	}

}