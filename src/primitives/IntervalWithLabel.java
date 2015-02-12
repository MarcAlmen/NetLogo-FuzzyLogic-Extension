package primitives;

import general.SupportFunctions;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.LogoList;
import org.nlogo.api.Syntax;

import sets.IntervalWithPointsSet;

public class IntervalWithLabel  extends DefaultReporter {
	
	public Syntax getSyntax(){
		return Syntax.reporterSyntax(new int[] {Syntax.StringType(), Syntax.ListType()},Syntax.WildcardType());
	}

	@Override
	public Object report(Argument[] arg0, Context arg1) throws ExtensionException, LogoException {
		LogoList params = arg0[1].getList();
		double[] universe = SupportFunctions.IWPFormat(params);
		return new IntervalWithPointsSet(SupportFunctions.checkListFormat((LogoList) params.get(1)),true,arg0[0].getString(),universe);
	}

}
