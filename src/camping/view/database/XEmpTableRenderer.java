package camping.view.database;

import javax.swing.table.*;

import camping.model.database.EmplacementDB;
import camping.view.tools.CampingColor;

import javax.swing.*;
import java.awt.*;

public class XEmpTableRenderer extends DefaultTableCellRenderer {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private EmplacementPanel ep;

	public XEmpTableRenderer(EmplacementPanel e){
		super();
		ep=e;
	}

	public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column){
		Component componentCell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	    ((JLabel) componentCell).setHorizontalAlignment(JLabel.CENTER);
	    if(EmplacementDB.isOccupe(ep.getNumEmpAt(row))){
	    	componentCell.setBackground(CampingColor.GREY);
	    	componentCell.setForeground(CampingColor.LIGHTGREY);
	    }else{
	    	componentCell.setBackground(CampingColor.LIGHTGREY);
	    	componentCell.setForeground(CampingColor.GREY);
	    }

	    return this;
	}
}
