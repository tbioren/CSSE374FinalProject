	import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class FlightTableModel extends AbstractTableModel {

    private ArrayList<Flight> data = null;
    private String[] columnNames = {"Flight Number", "From", "To", "Departure Date Time", "Load"};

    public FlightTableModel(ArrayList<Flight> data ) {
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
    public String getColumnName(int column) {
        return this.columnNames[column];
    }
}
