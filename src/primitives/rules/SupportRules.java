package primitives.rules;

import java.util.ArrayList;
import java.util.List;

import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.LogoList;

import sets.FuzzySet;

public class SupportRules {
	
	public static double simpleRulesChecks(LogoList l) throws ExtensionException, LogoException{
		double evaluationNumber;
		FuzzySet evaluationSet;
		FuzzySet evaluationOwnerSet;
		double eval = 0;
		if(l.get(1) instanceof FuzzySet){
			evaluationOwnerSet = (FuzzySet) l.get(1);
		}else{
			throw new ExtensionException("The second element of the list must be a fuzzy set.");
		}
		if(l.first() instanceof Double){
			evaluationNumber =(Double) l.first();
			eval = evaluationOwnerSet.evaluate(evaluationNumber);
		}else if(l.first() instanceof FuzzySet){
			evaluationSet =(FuzzySet) l.first();
			eval = evaluationOwnerSet.evaluate(evaluationSet);
		}else{
			throw new ExtensionException("The first element of the list must be a number or a fuzzy set.");
		}
		return eval;
	}
	
	public static List<Double> variadicRulesChecks(LogoList l) throws ExtensionException, LogoException{
		LogoList element;
		FuzzySet evaluationSet;
		double evaluationNumber;
		FuzzySet evaluationOwnerSet;
		double eval = 0;
		List<Double> result = new ArrayList<Double>();
		for(Object o : l){
			element = (LogoList) o;
			if(element.get(1) instanceof FuzzySet){
				evaluationOwnerSet = (FuzzySet) element.get(1);
			}else{
				throw new ExtensionException("The second element of the list must be a fuzzy set.");
			}
			
			if(element.first() instanceof Double){
				evaluationNumber = (Double) element.first();
				eval = evaluationOwnerSet.evaluate(evaluationNumber);
				result.add(eval);
			}else if(element.first() instanceof FuzzySet){
				evaluationSet = (FuzzySet) element.first();
				eval = evaluationOwnerSet.evaluate(evaluationSet);
				result.add(eval);
			}else{
				throw new ExtensionException("The first element of the list must be a number or a fuzzy set.");
			}
		}
		return result;
	}

}
