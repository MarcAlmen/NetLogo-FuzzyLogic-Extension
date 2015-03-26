package primitives.rules;

import java.util.ArrayList;
import java.util.List;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.Syntax;

import primitives.implication.Prod;
import sets.FuzzySet;
import sets.PiecewiseLinearSet;

public class MinProdRule extends DefaultReporter{

	public Syntax getSyntax(){
		return Syntax.reporterSyntax(new int[]{Syntax.ListType(),Syntax.WildcardType()},Syntax.WildcardType());
	}

	@Override
	public Object report(Argument[] arg0, Context arg1) throws ExtensionException, LogoException {
		FuzzySet conseq =(FuzzySet) arg0[1].get();
		List<Double> evaluations = SupportRules.variadicRulesChecks(arg0[0].getList());
		double min = Double.POSITIVE_INFINITY;
		for(double d : evaluations){
			if(d == Double.NaN){
				return new PiecewiseLinearSet(new ArrayList<double[]>(), true, "empty", new double[]{});
			}
			if(d < min){
				min = d;
			}
		}
		Prod p = new Prod();
		return p.prodding(conseq, min);
	}

}
