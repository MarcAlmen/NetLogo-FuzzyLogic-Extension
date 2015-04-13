package primitives.implication;

import java.util.ArrayList;
import java.util.List;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.Syntax;

import sets.DiscreteNumericSet;
import sets.FuzzySet;
import sets.PiecewiseLinearSet;
import sets.PointSet;
import sets.ProdSet;

public class Prod extends DefaultReporter{
	
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
		return prodding(f, c);
	}
	
	public FuzzySet prodding(FuzzySet f, double c){
		if(f.isContinuous()){
			if(f instanceof PiecewiseLinearSet){
				return probPiecewise((PointSet) f, c);
			}else{
				return new ProdSet(f,c, true, "continuous-prob", f.getUniverse());
			}
		}else{
			return probDiscrete((PointSet) f, c);
		}
	}
	
	private FuzzySet probPiecewise(PointSet p,double c){
		List<double[]> params = new ArrayList<double[]>();
		double[] resultPoint = new double[2];
		for(double[] point : p.getParameters()){
			resultPoint[0] = point[0];
			resultPoint[1] = point[1] * c;
			params.add(resultPoint.clone());
		}
		return new PiecewiseLinearSet(params,true,"piecewise-prod",p.getUniverse());
	}
	
	private FuzzySet probDiscrete(PointSet p,double c){
		List<double[]> params = new ArrayList<double[]>();
		double[] resultPoint = new double[2];
		for(double[] point : p.getParameters()){
			resultPoint[0] = point[0];
			resultPoint[1] = point[1] * c;
			params.add(resultPoint.clone());
		}
		return new DiscreteNumericSet(params, false, "discrete-prod" , p.getUniverse());
	}
	
	
}
