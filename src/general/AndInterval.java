package general;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.LogoList;
import org.nlogo.api.LogoListBuilder;
import org.nlogo.api.Syntax;

public class AndInterval extends DefaultReporter{
	
	public Syntax getSyntax(){
		return Syntax.reporterSyntax(new int[]{Syntax.ListType(),Syntax.ListType()},Syntax.ListType());
	}

	@Override
	public Object report(Argument[] arg0, Context arg1) throws ExtensionException, LogoException {
		LogoList universe1 = arg0[0].getList();
		LogoList universe2 = arg0[1].getList();
		double[] univ1 = new double[]{(Double) universe1.first(),(Double) universe1.get(1)};
		double[] univ2 = new double[]{(Double) universe2.first(),(Double) universe2.get(1)};
		double[] fin = DegreeOfFulfillment.andInterval(univ1, univ2);
		LogoListBuilder log = new LogoListBuilder();
		if(fin.length == 2){
			log.add(fin[0]);
			log.add(fin[1]);
		}else{
			throw new ExtensionException("Vacio");
		}

		return log.toLogoList();
	}

}
