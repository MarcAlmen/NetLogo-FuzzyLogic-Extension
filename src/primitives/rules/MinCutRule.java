package primitives.rules;

import java.util.List;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.Syntax;

import primitives.implication.Cut;
import sets.EmptySet;
import sets.FuzzySet;

public class MinCutRule extends DefaultReporter{
	
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
				return new EmptySet();
			}
			if(d < min){
				min = d;
			}
		}
		Cut c = new Cut();
		return c.cutting(conseq, min);
	}

}
