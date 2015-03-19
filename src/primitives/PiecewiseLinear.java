package primitives;

import java.util.List;

import general.SupportFunctions;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.Syntax;

import sets.PiecewiseLinearSet;

public class PiecewiseLinear extends DefaultReporter {
	
	public Syntax getSyntax(){
		return Syntax.reporterSyntax(new int[] {Syntax.ListType()}, Syntax.WildcardType());
	}

	@Override
	public Object report(Argument[] arg0, Context arg1)
			throws ExtensionException, LogoException {
		double[] universe = new double[] {Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY}; 
		//List<LogoList> sortingList = new ArrayList<LogoList>();
		/*
		 * CheckListFormat Checks:
		 * list
		 * list of lists
		 * list of 2 elements lists
		 * second of each element between 0 and 1
		 */
		//sortingList = SupportFunctions.checkListFormat(arg0[0].getList());
		//Ordenar por el primer elemento
		//Posible mejora: Si ya está ordenado evitar ordenarlo
		//Comprueba que se han proporcianado al menos 2 puntos
		if(arg0[0].getList().size() < 2){
			throw new ExtensionException("At least 2 points must be provided");
		}
		List<double[]> ej = SupportFunctions.checkListFormat(arg0[0].getList());
		universe[0] = ej.get(0)[0];
		universe[1] = ej.get(ej.size()-1)[0];
		return new PiecewiseLinearSet(ej,true,"piecewise",universe);
	}

}
