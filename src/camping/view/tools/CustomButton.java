package camping.view.tools;

import javax.swing.Icon;
import javax.swing.JButton;

public class CustomButton extends JButton{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public CustomButton(String s){
		super(s);
		this.setUI(new CustomButtonUI());
		this.setForeground(CampingColor.GREEN);
	}

	public CustomButton(Icon i){
		super(i);
		this.setUI(new CustomButtonUI());
		this.setForeground(CampingColor.GREEN);
	}



}
