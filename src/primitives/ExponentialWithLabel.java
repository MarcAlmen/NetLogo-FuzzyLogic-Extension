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

import sets.ExponentialSet;
import sets.FuzzySet;

public class ExponentialWithLabel extends DefaultReporter{
	
	public Syntax getSyntax(){
		return Syntax.reporterSyntax(new int[] {Syntax.StringType(), Syntax.ListType()},Syntax.WildcardType());
	}

	@Override
	public Object report(Argument[] arg0, Context arg1) throws ExtensionException, LogoException {
		LogoList params = arg0[1].getList();
		List<Double> finalParams = new ArrayList<Double>();
		double[] universe = SupportFunctions.LGEFormat(params, 4);
		finalParams.add((Double) params.first());
		finalParams.add((Double) params.get(1));
		finalParams.add((Double) params.get(2));
		FuzzySet createdSet = new ExponentialSet(finalParams,true,arg0[0].getString(),universe);
		SupportFunctions.addToRegistry(createdSet, arg0[0].getString(),arg1);
		return createdSet;
	}

}
