import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class LoadRequestTableModel extends AbstractTableModel {

    private ArrayList<LoadRequest> data = null;
    private String[] columnNames = {"Flight Number", "From", "To", "Departure Date Time", "Load", "Token"};

    public LoadRequestTableModel(ArrayList<LoadRequest> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return this.data.size();
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return this.data.get(rowIndex).getValue(this.columnNames[columnIndex]);
    }

    @Override
    public String getColumnName(int index) {
        return this.columnNames[index];
    }
}
