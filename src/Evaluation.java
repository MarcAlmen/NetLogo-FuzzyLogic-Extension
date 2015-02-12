

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.LogoList;
import org.nlogo.api.LogoListBuilder;
import org.nlogo.api.Syntax;

public class Evaluation extends DefaultReporter {
	
	public Syntax getSyntax(){
		return Syntax.reporterSyntax(new int[] {Syntax.WildcardType(),Syntax.WildcardType()},Syntax.ReadableType());
	}

	@Override
	public Object report(Argument[] arg0, Context arg1)
			throws ExtensionException, LogoException {
		//Checks the first argument is a FuzzySet and cast it
		if(!(arg0[0].get() instanceof FuzzySet)){
			throw new ExtensionException("The first argument must be a fuzzySet");
		}
		FuzzySet fuzzySet = (FuzzySet) arg0[0].get();
		Object obj = arg0[1].get();
		//If the second argument is a fuzzySet call degreeOfFulfillment
		//if its a Double call singleEvaluation
		//if its a LogoList call multipleEvaluation
		//if its any other class it throw a extension exception
		if(obj instanceof FuzzySet){
			return degreeOfFulfillment(fuzzySet, (FuzzySet) obj);
		}else if(obj instanceof Double){
			return singleEvaluation(fuzzySet,(Double) obj);
		}else if(obj instanceof LogoList){
			return multipleEvaluation(fuzzySet,(LogoList) obj);
		}else{
			throw new ExtensionException("The second argument must be a fuzzySet, a Number or a List");
		}
	}
	
	public Object degreeOfFulfillment(FuzzySet a,FuzzySet b){	
		String s = "Hola";
		return s;
	}
	
	public Object singleEvaluation(FuzzySet a,Double b){
		String s = "Hola";
		return s;
	}
	
	public Object multipleEvaluation(FuzzySet a, LogoList b) throws ExtensionException{
		LogoListBuilder result = new LogoListBuilder();
		//Iterate over the LogoList
		for(Object o:b){
			//If object is a fuzzySet call degreeOfFulfillment
			//if its a Double call singleEvaluation
			//if its any other class it throw a extension exception
			//Add the results to a LogoListBuilder to return a LogoList with all the evaluation results
			if(o instanceof FuzzySet){
				result.add(degreeOfFulfillment(a,(FuzzySet) o));
			}else if(o instanceof Double){
				result.add(singleEvaluation(a,(Double) o));
			}else{
				throw new ExtensionException("The list can only cointain FuzzySets or Numbers");
			}
		}
		return result.toLogoList();
	}

}
