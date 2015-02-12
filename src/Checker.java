

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.LogoListBuilder;
import org.nlogo.api.Syntax;

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
		list.add(setToCheck.getDescription().name());
		list.add(setToCheck.getParameters());
		LogoListBuilder universe = new LogoListBuilder();
		universe.add(setToCheck.getUniverse()[0]);
		universe.add(setToCheck.getUniverse()[1]);
		list.add(universe.toLogoList());
		list.add(setToCheck.getLabel());
		return list.toLogoList();
	}

}
