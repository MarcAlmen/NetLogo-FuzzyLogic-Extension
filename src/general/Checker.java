package general;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.LogoListBuilder;
import org.nlogo.api.Syntax;

import sets.FunctionSet;
import sets.FuzzySet;
import sets.OperatorSet;
import sets.PointSet;

public class Checker extends DefaultReporter {
	
	public Syntax getSyntax(){
		return Syntax.reporterSyntax(new int[] {Syntax.WildcardType()},Syntax.ListType());
	}

	@Override
	public Object report(Argument[] arg0, Context arg1)
			throws ExtensionException, LogoException {
		//Devuelve una lista con todas las propiedades de los FuzzySets
		FuzzySet setToCheck = (FuzzySet) arg0[0].get();
		LogoListBuilder list = new LogoListBuilder();
		LogoListBuilder param = new LogoListBuilder();
		LogoListBuilder aux = new LogoListBuilder();
		list.add(setToCheck.getDescription());
		if(setToCheck instanceof PointSet){
			PointSet ps = (PointSet) setToCheck;
			for(double[] point : ps.getParameters()){
				aux = new LogoListBuilder();
				aux.add(point[0]);
				aux.add(point[1]);
				param.add(aux.toLogoList());
			}
		}else if(setToCheck instanceof FunctionSet){
			FunctionSet ps = (FunctionSet) setToCheck;
			for(double point : ps.getParameters()){
				param.add(point);
			}
		}else{
			OperatorSet ms = (OperatorSet) setToCheck;
			for(FuzzySet set : ms.getParameters()){
					param.add(set.getDescription());
					if(set instanceof PointSet){
						PointSet ps = (PointSet) set;
						for(double[] point : ps.getParameters()){
							aux = new LogoListBuilder();
							aux.add(point[0]);
							aux.add(point[1]);
							param.add(aux.toLogoList());
						}
					}else{
						
					}
			}
		}
		list.add(param.toLogoList());
		LogoListBuilder universe = new LogoListBuilder();
		universe.add(setToCheck.getUniverse()[0]);
		universe.add(setToCheck.getUniverse()[1]);
		list.add(universe.toLogoList());
		list.add(setToCheck.getLabel());
		return list.toLogoList();
	}

}
