package camping.view.tools;

import javax.swing.Icon;
import javax.swing.JButton;

public class CustomHomeTitleButton extends JButton{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public CustomHomeTitleButton(String s){
		super(s);
		this.setUI(new CustomHomeTitleButtonUI());
	}

	public CustomHomeTitleButton(Icon i){
		super(i);
		this.setUI(new CustomHomeTitleButtonUI());
	}
}
