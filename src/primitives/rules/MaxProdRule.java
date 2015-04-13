package primitives.rules;

import java.util.List;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.Syntax;

import primitives.implication.Prod;
import sets.EmptySet;
import sets.FuzzySet;

public class MaxProdRule extends DefaultReporter{
	
	public Syntax getSyntax(){
		return Syntax.reporterSyntax(new int[]{Syntax.ListType(),Syntax.WildcardType()},Syntax.WildcardType());
	}

	@Override
	public Object report(Argument[] arg0, Context arg1) throws ExtensionException, LogoException {
		FuzzySet conseq =(FuzzySet) arg0[1].get();
		List<Double> evaluations = SupportRules.variadicRulesChecks(arg0[0].getList());
		double max = Double.NEGATIVE_INFINITY;
		for(double d : evaluations){
			if(d == Double.NaN){
				return new EmptySet();
			}
			if(d > max){
				max = d;
			}
		}
		Prod p = new Prod();
		return p.prodding(conseq, max);
	}

}
