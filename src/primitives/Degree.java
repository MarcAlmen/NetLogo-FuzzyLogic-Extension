package primitives;

import general.DegreeOfFulfillment;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.Syntax;

import sets.FuzzySet;
import sets.PiecewiseLinearSet;
import sets.PointSet;

//TODO comprobar que funciona por separado
public class Degree extends DefaultReporter{
	
	public Syntax getSyntax(){
		return Syntax.reporterSyntax(new int[]{Syntax.WildcardType(), Syntax.WildcardType()},Syntax.NumberType());
	}

	@Override
	public Object report(Argument[] arg0, Context arg1) throws ExtensionException, LogoException {
		FuzzySet a = (FuzzySet) arg0[0].get();
		FuzzySet b = (FuzzySet) arg0[1].get();
		if(!a.isContinuous() && !b.isContinuous()){
			return DegreeOfFulfillment.discreteFulfillment((PointSet) a,(PointSet) b);
		}else if(a instanceof PiecewiseLinearSet && b instanceof PiecewiseLinearSet){
			return DegreeOfFulfillment.piecewiseFulfillment((PointSet) a,(PointSet) b);
		}else{
			return DegreeOfFulfillment.mixedFulfillment(a, b);
		}
	}

}
