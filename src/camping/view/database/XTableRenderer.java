package camping.view.database;

import javax.swing.table.*;
import javax.swing.*;
import java.awt.*;

public class XTableRenderer extends DefaultTableCellRenderer {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column){
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	    this.setHorizontalAlignment(JLabel.CENTER);
	    return this;
	}
}
