package primitives.Operators;

import general.DegreeOfFulfillment;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.LogoList;
import org.nlogo.api.Syntax;
import sets.DiscreteNumericSet;
import sets.FuzzySet;
import sets.PiecewiseLinearSet;
import sets.PointSet;
import sets.MaxOrSet;

public class MaxOr extends DefaultReporter{
	
	public Syntax getSyntax(){
		return Syntax.reporterSyntax(new int[]{Syntax.ListType()},Syntax.WildcardType());
	}

	@Override
	public Object report(Argument[] arg0, Context arg1) throws ExtensionException, LogoException {
		LogoList listOfSets = arg0[0].getList();
		int type = -1;
		//If there is only one set just report that set.
		if(listOfSets.size() == 1){
			return listOfSets.first();
		}
		type = SupportOperators.allType(listOfSets);
		switch (type) {
		case 0:
			throw new ExtensionException("The fuzzy sets must be all discrete or all continuous");
		case 1:
			return maxDiscrete(listOfSets);
		case 2:
			return maxPiecewise(listOfSets);
		case 3:
			return maxContinuous(listOfSets);
		default:
			return null;
		}
	}
	
	
	private FuzzySet maxDiscrete(LogoList l){
		Tuple<double[]> t = SupportOperators.discreteOperations(l, new Max());
		//Create a new Discrete numeric set with the resulting parameters
		return new DiscreteNumericSet(t.getParams(), false, "Max-discrete", t.getUniverse());
	}
	
	private FuzzySet maxPiecewise(LogoList l){
		PointSet setA = (PointSet) l.first();
		double[] universe = new double[2];
		PointSet setB;
		//Iterate over the sets
		for(int i = 1 ; i < l.size() ; i++){
			setB = (PointSet) l.get(i);
			//Calculate the andUniverse of the two sets
			universe = DegreeOfFulfillment.andInterval(setA.getUniverse(), setB.getUniverse());
			//Create a new set, the min piecewise of 2 piecewise.
			setA = new PiecewiseLinearSet(DegreeOfFulfillment.upperEnvelope(setA, setB), true, "Max-piecewise" , universe);
		}
		return setA;
	}
	
	private FuzzySet maxContinuous(LogoList l){
		Tuple<FuzzySet> t = SupportOperators.continuousParamsUniverse(l);
		return new MaxOrSet(t.getParams(),true,"Max-continuous",t.getUniverse());
	}

	public class Max implements Command{

		@Override
		public double[] execute(double[] pointA, double[] pointB) {
			double[] resultPoint = new double[2];
			resultPoint[0] = pointA[0];
				//find the min value
				if(pointA[1] >= pointB[1]){
					resultPoint[1] = pointA[1];
				}else{
					resultPoint[1] = pointB[1];
				}
			return resultPoint.clone();
		}
		
	}

	
}
