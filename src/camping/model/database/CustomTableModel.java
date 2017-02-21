package camping.model.database;

import javax.swing.table.DefaultTableModel;

public class CustomTableModel extends DefaultTableModel {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public CustomTableModel(Object[][] data, String[] title) {
		super(data,title);
	}

	@Override
	public boolean isCellEditable(int row, int column){
        return false;
    }

}