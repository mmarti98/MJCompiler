package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.FormParElem;
import rs.ac.bg.etf.pp1.ast.VarDecls;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class CounterVisitor extends VisitorAdaptor {

	protected int count;

	public int getCount() {
		return count;
	}

	public static class FormParamCounter extends CounterVisitor {
		public void visit(FormParElem formParamDecl) {
			count++;
		}
	}

	public static class VarCounter extends CounterVisitor {
		public void visit(VarDecls varDecl) {
			count++;
		}
	}
}
