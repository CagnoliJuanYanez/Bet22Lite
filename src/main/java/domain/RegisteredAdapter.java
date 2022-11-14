package domain;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class RegisteredAdapter extends AbstractTableModel{

private Registered registered;
private  List<Apustua> apustuak= new ArrayList();

public RegisteredAdapter(Registered r) {
	this.registered = r;
}
	
public int getRowCount() {
	for(ApustuAnitza aa: registered.getApustuAnitzak()) {
        for(Apustua a: aa.getApustuak()) {
                apustuak.add(a);
        }
}
	return apustuak.size();
}

public int getColumnCount() {
	return 4;
}

public Object getValueAt(int rowIndex, int columnIndex) {
	for(Apustua a: apustuak) {
		if(columnIndex==0) {
            return a.getKuota().getQuestion().getEvent();
		}
		else if(columnIndex==1) {
            return a.getKuota().getQuestion();
		}
		else if(columnIndex==2) {
            return a.getApustuAnitza().getData();
		}
		else {
            return a.getApustuAnitza().getBalioa();
		}
	}
	
	
	return null;
}

}