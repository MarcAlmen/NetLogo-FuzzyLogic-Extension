package primitives.implication;

import general.DegreeOfFulfillment;

import java.util.ArrayList;
import java.util.List;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.Syntax;

import sets.CutSet;
import sets.DiscreteNumericSet;
import sets.FuzzySet;
import sets.PiecewiseLinearSet;
import sets.PointSet;

public class Cut extends DefaultReporter{
	
	public Syntax getSyntax(){
		return Syntax.reporterSyntax(new int[]{Syntax.WildcardType(),Syntax.NumberType()},Syntax.WildcardType());
	}

	@Override
	public Object report(Argument[] arg0, Context arg1) throws ExtensionException, LogoException {
		double c = arg0[1].getDoubleValue();
		if(c < 0 || c > 1){
			throw new ExtensionException("The value of the number must be between 0 and 1");
		}
		FuzzySet f =(FuzzySet) arg0[0].get();
		if(f.isContinuous()){
			if(f instanceof PiecewiseLinearSet){
				return cutPiecewise((PointSet) f, c);
			}else{
				return new CutSet(f,c, true, "continuous-cut", f.getUniverse());
			}
		}else{
			return cutDiscrete((PointSet) f, c);
		}
	}
	
	private FuzzySet cutDiscrete(PointSet f, double c){
		List<double[]> params = new ArrayList<double[]>();
		double[] resultPoint = new double[2];
		for(double[] point : f.getParameters()){
			resultPoint[0] = point[0];
			if(point[1] > c){
				resultPoint[1] = c;
			}else{
				resultPoint[1] = point[1];
			}
			params.add(resultPoint.clone());
		}
		return new DiscreteNumericSet(params, false, "discrete-cut" , f.getUniverse());
	}
	
	private FuzzySet cutPiecewise(PointSet f, double c){
		//Create a line in the whole universe with y = c
		double[] firstPoint = new double[]{f.getUniverse()[0] , c};
		double[] lastPoint = new double[]{f.getUniverse()[1] , c};
		List<double[]> paramsLine = new ArrayList<double[]>();
		paramsLine.add(firstPoint);
		paramsLine.add(lastPoint);
		PointSet cutLine = new PiecewiseLinearSet(paramsLine, true, "cut-line", f.getUniverse());
		//Calculate the lower envelope and create the resulting set
		return new PiecewiseLinearSet(DegreeOfFulfillment.lowerEnvelope(f, cutLine), true, "piecewise-cut", f.getUniverse());
	}

}
