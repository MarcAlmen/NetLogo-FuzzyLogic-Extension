package general;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.Syntax;

public class SetFinder extends DefaultReporter {
	
	public Syntax getSyntax(){
		return Syntax.reporterSyntax(new int[]{Syntax.StringType()},Syntax.WildcardType());
	}

	@Override
	public Object report(Argument[] arg0, Context arg1)
			throws ExtensionException, LogoException {
		if(FuzzyLogic.getRegistry().containsKey(arg0[0].getString())){
			return FuzzyLogic.getRegistry().get(arg0[0].getString());
		}else{
			throw new ExtensionException("There is no Fuzzy Set with label: " + arg0[0].getString());
		}
	}

}
