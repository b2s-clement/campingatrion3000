package camping.view.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import camping.view.tools.CampingColor;
import camping.view.tools.CustomButton;

public class CustomDialog extends JDialog {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static int CONFIRM = 1;
	public static int INPUT = 2;
	public static int SAVE = 3;
	public static int YES_NO = 4;
	public static int INFO_ERROR = 5;

	public static ImageIcon warning = new ImageIcon("img/dialog/warning.png");
	public static ImageIcon error = new ImageIcon("img/dialog/error.png");
	public static ImageIcon question = new ImageIcon("img/dialog/question.png");
	public static ImageIcon info = new ImageIcon("img/dialog/info.png");

	public static int commited = 1;
	public static int cancelled = -1;
	public static int ignored = 0 ;

	private CustomButton ok = new CustomButton("OK");
	private CustomButton cancel = new CustomButton("Annuler");
	private CustomButton save = new CustomButton("Enregistrer");
	private CustomButton ignore = new CustomButton("Ignorer");
	private CustomButton yes = new CustomButton("Oui");
	private CustomButton no = new CustomButton("Non");

	private JTextField inputField;
	private int t;
	private int res=ignored;

	public CustomDialog(int type,ImageIcon i,String title,String message) {
		setModal(true);
		this.t = type;
		//propriétés
		this.setResizable(false);
		this.setTitle(title);

		//container
		JPanel diagContain = new JPanel();
		diagContain.setLayout(new BorderLayout());
		diagContain.setBackground(CampingColor.LIGHTGREY);
		diagContain.setBorder(new EmptyBorder(10,10,10,10));

		//Icone
		JLabel icon = new JLabel(i);
		icon.setBorder(new EmptyBorder(10,10,10,10));
		icon.setOpaque(false);
		diagContain.add(icon,BorderLayout.WEST);

		//texte
		JLabel text = new JLabel(message);
		text.setOpaque(false);
		text.setForeground(CampingColor.GREEN);
		if(t==2){
			inputField = new JTextField();
			inputField.getDocument().addDocumentListener(new InputFieldListener());
			JPanel inputPanel = new JPanel();
			inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
			inputPanel.add(text);
			inputPanel.add(Box.createRigidArea(new Dimension(0,10)));
			inputPanel.add(inputField);
			inputPanel.add(Box.createRigidArea(new Dimension(0,20)));
			inputPanel.setOpaque(false);
			diagContain.add(inputPanel,BorderLayout.CENTER);
		}else{
			diagContain.add(text,BorderLayout.CENTER);
		}

		//Buttons
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));

		ok.setPreferredSize(new Dimension(80,30));
		cancel.setPreferredSize(new Dimension(80,30));
		save.setPreferredSize(new Dimension(100,30));
		ignore.setPreferredSize(new Dimension(80,30));
		yes.setPreferredSize(new Dimension(80,30));
		no.setPreferredSize(new Dimension(80,30));

		ok.addActionListener(new CommitListener());
		cancel.addActionListener(new CancelListener());
		save.addActionListener(new CommitListener());
		ignore.addActionListener(new NothingListener());
		yes.addActionListener(new CommitListener());
		no.addActionListener(new CancelListener());

		buttons.setOpaque(false);
		buttons.add(Box.createHorizontalGlue());
		switch(type){
		case 1: //CONFIRM
			buttons.add(ok);
			buttons.add(Box.createRigidArea(new Dimension(10,0)));
			buttons.add(cancel);
			break;
		case 2: //INPUT
			buttons.add(save);
			buttons.add(Box.createRigidArea(new Dimension(10,0)));
			buttons.add(cancel);
			break;
		case 3 : //SAVE
			buttons.add(save);
			buttons.add(Box.createRigidArea(new Dimension(10,0)));
			buttons.add(ignore);
			buttons.add(Box.createRigidArea(new Dimension(10,0)));
			buttons.add(cancel);
			break;
		case 4 : //YES_NO
			buttons.add(yes);
			buttons.add(Box.createRigidArea(new Dimension(10,0)));
			buttons.add(no);
			break;
		case 5: //INFO_ERROR
			buttons.add(ok);
			break;
		default:
			System.out.println("Erreur de type de boite de dialogue");
			break;

		}
		diagContain.add(buttons,BorderLayout.SOUTH);

		//finalisation
		int h = diagContain.getPreferredSize().height + 20;
		int w = diagContain.getPreferredSize().width + 60;
		this.setSize(w,h);
		this.add(diagContain);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}

	public int getRes(){
		return res;
	}

	public void setField(String s){
		if(this.t==2){
			inputField.setText(s);
			inputField.requestFocus();
			inputField.selectAll();
		}
		if(s.equals("")){
			save.setEnabled(false);
		}
	}

	public static String inputDialog(String title,String message,String current){
		String outputField;
		CustomDialog dialog = new CustomDialog(CustomDialog.INPUT,CustomDialog.question,title,message);
		dialog.setField(current);
		dialog.setVisible(true);
		if(dialog.getRes()==CustomDialog.commited){
			outputField = dialog.inputField.getText();
		}else{
			outputField = "";
		}
		return outputField;
	}

	public static void errorDialog(String title,String message){
		CustomDialog dialog = new CustomDialog(CustomDialog.INFO_ERROR,CustomDialog.error,title,message);
		dialog.setVisible(true);
	}

	public static void infoDialog(String title,String message){
		CustomDialog dialog = new CustomDialog(CustomDialog.INFO_ERROR,CustomDialog.info,title,message);
		dialog.setVisible(true);
	}

	public static int saveDialog(String title,String message){
		CustomDialog dialog = new CustomDialog(CustomDialog.SAVE,CustomDialog.question,title,message);
		dialog.setVisible(true);
		return dialog.getRes();
	}

	public static int confirmDialog(String title, String message){
		CustomDialog dialog = new CustomDialog(CustomDialog.CONFIRM,CustomDialog.warning,title,message);
		dialog.setVisible(true);
		return dialog.getRes();
	}

	public static int yesNoDialog(String title,String message){
		CustomDialog dialog = new CustomDialog(CustomDialog.YES_NO,CustomDialog.question,title,message);
		dialog.setVisible(true);
		return dialog.getRes();
	}

	public class InputFieldListener implements DocumentListener{
		public void changedUpdate(DocumentEvent e){
		    enableButton();
		}
		public void removeUpdate(DocumentEvent e){
		    enableButton();
		}
		public void insertUpdate(DocumentEvent e){
		    enableButton();
		}
		public void enableButton(){
		     if (inputField.getText().equals("")){
		        save.setEnabled(false);
		     }else{
		        save.setEnabled(true);
		     }
		  }
	}
	public class CancelListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			CustomDialog.this.res = CustomDialog.cancelled;
			CustomDialog.this.dispose();
		}
	}
	public class CommitListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			CustomDialog.this.res=CustomDialog.commited;
			CustomDialog.this.dispose();
		}
	}
	public class NothingListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			CustomDialog.this.dispose();
		}
	}
}