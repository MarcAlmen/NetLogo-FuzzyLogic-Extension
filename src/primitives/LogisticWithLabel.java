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

import sets.LogisticSet;

public class LogisticWithLabel extends DefaultReporter {
	
	public Syntax getSyntax(){
		return Syntax.reporterSyntax(new int[] {Syntax.StringType(), Syntax.ListType()},Syntax.WildcardType());
	}

	@Override
	public Object report(Argument[] arg0, Context arg1) throws ExtensionException, LogoException {
		LogoList params = arg0[0].getList();
		List<Double> resultParams = new ArrayList<Double>();
		double[] universe = SupportFunctions.LGEFormat(params, 4);
		resultParams.add((Double) params.first());
		resultParams.add((Double) params.get(1));
		resultParams.add((Double) params.get(2));
		return new LogisticSet(resultParams,true,arg0[0].getString(),universe);
	}

}
