package primitives.Operators;

import java.util.List;



import general.DegreeOfFulfillment;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.LogoList;
import org.nlogo.api.Syntax;

import primitives.implication.Cut;
import sets.DiscreteNumericSet;
import sets.FuzzySet;
import sets.PiecewiseLinearSet;
import sets.PointSet;
import sets.SumSet;

public class Sum extends DefaultReporter{
	
	public Syntax getSyntax(){
		return Syntax.reporterSyntax(new int[]{Syntax.ListType()},Syntax.WildcardType());
	}

	@Override
	public Object report(Argument[] arg0, Context arg1) throws ExtensionException, LogoException {
		//Podría ir en support functions <--------------------------------------------------------------------------------------------Desde aqui
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
			return sumDiscrete(listOfSets);
		case 2:
			return sumPiecewise(listOfSets);
		case 3:
			return sumContinuous(listOfSets);
		default:
			return null;
		}
	}
	
	private FuzzySet sumDiscrete(LogoList l){
		Tuple<double[]> t = SupportOperators.discreteOperations(l, new Add());
		return new DiscreteNumericSet(t.getParams(), false, "Sum-discrete", t.getUniverse());
	}
	
	
	private FuzzySet sumPiecewise(LogoList l){
		PointSet setA = (PointSet) l.first();
		PointSet setB;
		List<Double> points;
		double[] universe = setA.getUniverse();
		List<double[]> result = setA.getParameters();
		//Im not proud at all about this implementation
		for(int i = 1 ; i < l.size() ; i++){
			setB = (PointSet) l.get(i);
			universe = DegreeOfFulfillment.andInterval(universe, setB.getUniverse());
			points = DegreeOfFulfillment.pointsToEvaluate(result, setB.getParameters(), universe);
			result.clear();
			for(double d : points){
				result.add(new double[]{d,setA.evaluate(d) + setB.evaluate(d)});
			}
		}
		Cut c = new Cut();
		return c.cutting(new PiecewiseLinearSet(result, true, "sum-piecewise", universe),1.0);
	}
	
	
	private FuzzySet sumContinuous(LogoList l){
		Tuple<FuzzySet> t = SupportOperators.continuousParamsUniverse(l);
		return new SumSet(t.getParams(),true,"Sum-continuous",t.getUniverse());
	}
	
	
	public class Add implements Command{

		@Override
		public double[] execute(double[] pointA, double[] pointB) {
			double[] resultPoint = new double[2];
			resultPoint[0] = pointA[0];
			resultPoint[1] = pointA[1] + pointB[1];
				if(resultPoint[1] > 1){
					resultPoint[1] = 1;					
				}
			return resultPoint.clone();
		}
		
	}

}
