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
import sets.PointSet;
import sets.PowerSet;

public class Power extends DefaultReporter{
	
	public Syntax getSyntax(){
		return Syntax.reporterSyntax(new int[]{Syntax.WildcardType(),Syntax.NumberType()},Syntax.WildcardType());
	}

	@Override
	public Object report(Argument[] arg0, Context arg1) throws ExtensionException, LogoException {
		double exp = arg0[1].getDoubleValue();
		if(exp < 0){
			throw new ExtensionException("The value of the number must be greater than 0");
		}
		FuzzySet f =(FuzzySet) arg0[0].get();
		if(f.isContinuous()){
			return new PowerSet(f,exp, true, "continuous-power", f.getUniverse());
		}else{
			return powerDiscrete((PointSet) f, exp);
		}
	}
	
	private FuzzySet powerDiscrete(PointSet p, double exp){
		List<double[]> params = new ArrayList<double[]>();
		double[] resultPoint = new double[2];
		for(double[] point : p.getParameters()){
			resultPoint[0] = point[0];
			resultPoint[1] = Math.pow(point[1],exp);
			params.add(resultPoint.clone());
		}
		return new DiscreteNumericSet(params, false, "discrete-power" , p.getUniverse());
	}

}
