package primitives.Operators;

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
import sets.NotSet;
import sets.PiecewiseLinearSet;
import sets.PointSet;

public class Not extends DefaultReporter{
	
	public Syntax getSyntax(){
		return Syntax.reporterSyntax(new int[]{Syntax.WildcardType()},Syntax.WildcardType());
	}

	@Override
	public Object report(Argument[] arg0, Context arg1) throws ExtensionException, LogoException {
		FuzzySet f =(FuzzySet) arg0[0].get();
		if(f.isContinuous()){
			if(f instanceof PiecewiseLinearSet){
				return notPiecewise((PointSet) f);
			}else{
				return notContinuous(f);
			}
		}else{
			return notDiscrete((PointSet) f);
		}
	}
	
	private FuzzySet notDiscrete(PointSet f){
		List<double[]> params = new ArrayList<double[]>();
		double[] createPoint = new double[2];
		for(double[] point : f.getParameters()){
			createPoint[0] = point[0];
			createPoint[1] = 1 - point[1];
			params.add(createPoint.clone());	
		}
		return new DiscreteNumericSet(params, false, "Not of set: " + f.getLabel(), f.getUniverse());
	}
	
	private FuzzySet notPiecewise(PointSet f){
		List<double[]> params = new ArrayList<double[]>();
		double[] createPoint = new double[2];
		for(double[] point : f.getParameters()){
			createPoint[0] = point[0];
			createPoint[1] = 1 - point[1];
			params.add(createPoint.clone());	
		}
		return new PiecewiseLinearSet(params, true, "Not of set: " + f.getLabel() , f.getUniverse());
	}
	
	private FuzzySet notContinuous(FuzzySet f){
		List<FuzzySet> params = new ArrayList<FuzzySet>();
		params.add(f);
		return new NotSet(params, true,"Not of set: " + f.getLabel(), f.getUniverse());
	}

}
