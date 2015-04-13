package primitives;

import java.util.ArrayList;
import java.util.List;

import general.SupportFunctions;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.LogoList;
import org.nlogo.api.Syntax;

import sets.FuzzySet;
import sets.GaussianSet;

public class GaussianWithLabel extends DefaultReporter {
	
	public Syntax getSyntax(){
		return Syntax.reporterSyntax(new int[]{Syntax.StringType(),Syntax.ListType()},Syntax.WildcardType());
	}

	@Override
	public Object report(Argument[] arg0, Context arg1) throws ExtensionException, LogoException {
		LogoList params = arg0[1].getList();
		List<Double> resultParams = new ArrayList<Double>();
		double[] universe = SupportFunctions.LGEFormat(params, 3);
		resultParams.add((Double) params.first());
		resultParams.add((Double) params.get(1));
		FuzzySet createdSet = new GaussianSet(resultParams, true, arg0[0].getString(), universe);
		SupportFunctions.addToRegistry(createdSet, arg0[0].getString());
		return createdSet;
	}
}
