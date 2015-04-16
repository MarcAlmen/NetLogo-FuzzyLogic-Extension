package primitives.Operators;


import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.LogoList;
import org.nlogo.api.Syntax;

import sets.DiscreteNumericSet;
import sets.FuzzySet;
import sets.ProbOrSet;

public class ProbOr extends DefaultReporter{
	
	public Syntax getSyntax(){
		return Syntax.reporterSyntax(new int[]{Syntax.ListType()},Syntax.WildcardType());
	}

	@Override
	public Object report(Argument[] arg0, Context arg1) throws ExtensionException, LogoException {
		FuzzySet set;
		LogoList listOfSets = arg0[0].getList();
		if(listOfSets.size() == 1){
			return listOfSets.first();
		}
		boolean allContinuous = true;
		boolean allDiscrete = true;
		for(Object o : listOfSets){
			set = (FuzzySet) o;
			allContinuous &= set.isContinuous();
			allDiscrete &= !set.isContinuous();
		}
		if(allContinuous){
			return probContinuous(listOfSets);
		}else if(allDiscrete){
			return probDiscrete(listOfSets);
		}else{
			throw new ExtensionException("The sets must be all continuous or all discrete");
		}
	}
	
	
	private FuzzySet probDiscrete(LogoList l){
		//First discrete set and his parameters
		Tuple<double[]> t = SupportOperators.discreteOperations(l, new Prob());
		return new DiscreteNumericSet(t.getParams(), false, SupportOperators.buildLabel(l, "Prob-or"), t.getUniverse());
	}
	
	
	private FuzzySet probContinuous(LogoList l){
		Tuple<FuzzySet> t = SupportOperators.continuousParamsUniverse(l);
		return new ProbOrSet(t.getParams(),true,SupportOperators.buildLabel(l, "Prob-or"),t.getUniverse());
	}
	
	
	public class Prob implements Command{

		@Override
		public double[] execute(double[] pointA, double[] pointB) {
			double[] resultPoint = new double[2];
			resultPoint[0] = pointA[0];
			resultPoint[1] = pointA[1] + pointB[1] - pointA[1] * pointB[1];
			return resultPoint.clone();
		}
		
	}

}
