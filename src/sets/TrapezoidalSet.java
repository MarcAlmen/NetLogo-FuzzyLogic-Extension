package sets;

import org.nlogo.api.LogoList;

public class TrapezoidalSet extends FuzzySet {

	TrapezoidalSet(LogoList param, boolean continuous,
			String label, Double[] universe) {
		super("Trapezoidal", param, continuous, label, universe);
	}

	@Override
	public double evaluate(Double d) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double evaluate(FuzzySet f) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getNLTypeName() {
		// TODO Auto-generated method stub
		return null;
	}

}
