package primitives;

import general.SupportFunctions;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.LogoList;
import org.nlogo.api.Syntax;

import sets.PiecewiseLinearSet;

public class Trapezoidal extends DefaultReporter {
	
	public Syntax getSyntax(){
		return Syntax.reporterSyntax(new int[] {Syntax.ListType()}, Syntax.WildcardType());
	} 

	@Override
	public Object report(Argument[] arg0, Context arg1) throws ExtensionException, LogoException {
		LogoList args = arg0[0].getList(); 
		return new PiecewiseLinearSet(SupportFunctions.trapezoidalFormat(args),true,"piecewise", new double[]{(Double) args.first(),(Double) args.get(5)});
	}
}
