package domain.params;

import java.util.Date;
import java.util.Vector;

import domain.Quote;
import domain.Registered;

public class DiruaSartuParams {
	public 	Registered u;
	public double dirua;
	public Date data;
	public String mota;

	public DiruaSartuParams(Registered u, Double dirua, Date data, String mota) {
		this.u = u;
		this.dirua = dirua;
		this.data = data;
		this.mota = mota;
	}

}
