package camping.view.tools;

import javax.swing.Icon;
import javax.swing.JButton;

public class CustomDarkButton extends JButton{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public CustomDarkButton(String s){
		super(s);
		this.setUI(new CustomDarkButtonUI());
		this.setForeground(CampingColor.LIGHTGREY);
	}

	public CustomDarkButton(Icon i){
		super(i);
		this.setUI(new CustomDarkButtonUI());
		this.setForeground(CampingColor.LIGHTGREY);
	}



}
