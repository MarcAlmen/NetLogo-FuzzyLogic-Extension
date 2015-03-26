package primitives.rules;

import java.util.ArrayList;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.Syntax;

import primitives.implication.Cut;
import sets.FuzzySet;
import sets.PiecewiseLinearSet;

public class CutRule extends DefaultReporter{
	
	public Syntax getSyntax(){
		return Syntax.reporterSyntax(new int[]{Syntax.ListType(),Syntax.WildcardType()},Syntax.WildcardType());
	}

	@Override
	public Object report(Argument[] arg0, Context arg1) throws ExtensionException, LogoException {
		double eval = 0;
		FuzzySet conseq =(FuzzySet) arg0[1].get();
		eval = SupportRules.simpleRulesChecks(arg0[0].getList());
		if(eval == Double.NaN){
			return new PiecewiseLinearSet(new ArrayList<double[]>(), true, "empty", new double[]{});
		}else{
			Cut c = new Cut();
			return c.cutting(conseq, eval);
		}
	}

}
