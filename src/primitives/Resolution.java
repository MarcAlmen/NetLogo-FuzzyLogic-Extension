package primitives;

import general.SupportFunctions;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultCommand;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.Syntax;



public class Resolution extends DefaultCommand{

	public Syntax getSyntax(){
		return Syntax.commandSyntax(new int[]{Syntax.NumberType()});
	}

	@Override
	public void perform(Argument[] arg0, Context arg1)
			throws ExtensionException, LogoException {
		SupportFunctions.setResolution(arg0[0].getDoubleValue());
	}




}
