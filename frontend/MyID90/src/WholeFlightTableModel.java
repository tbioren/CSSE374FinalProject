import javax.swing.table.AbstractTableModel;
import java.util.List;

public class WholeFlightTableModel extends AbstractTableModel {

	private List<WholeFlight> data;
	private String[] columnNames = { "Flight Number", "From", "To", "Departure Date Time", "Load", "Last Updated" };

	public WholeFlightTableModel(List<WholeFlight> data) {
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
