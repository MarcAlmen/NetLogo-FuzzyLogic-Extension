package general;

import java.util.List;


import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.LogoListBuilder;
import org.nlogo.api.Syntax;

import sets.FuzzySet;
import sets.PiecewiseLinearSet;

public class LowerEnvelope extends DefaultReporter {
	
	public Syntax getSyntax(){
		return Syntax.reporterSyntax(new int[]{Syntax.ListType(),Syntax.ListType()},Syntax.ListType());
	}

	@Override
	public Object report(Argument[] arg0, Context arg1) throws ExtensionException, LogoException {
		LogoListBuilder endAux = new LogoListBuilder();
		LogoListBuilder end = new LogoListBuilder();
		List<double[]> result;
		FuzzySet a = new PiecewiseLinearSet(arg0[0].getList(), true, "uno", SupportFunctions.universe(arg0[0].getList()));
		FuzzySet b = new PiecewiseLinearSet(arg0[1].getList(), true, "otro", SupportFunctions.universe(arg0[1].getList()));
		result = DegreeOfFulfillment.lowerEnvelope(a, b);
		for(double[] point : result){
			endAux = new LogoListBuilder();
			endAux.add(point[0]);
			endAux.add(point[1]);
			end.add(endAux.toLogoList());
		}
		return end.toLogoList();
	}

}
