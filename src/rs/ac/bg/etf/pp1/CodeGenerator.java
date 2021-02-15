package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

	private int mainPC;
	private int pcAfterFirst, pcAfterSecond = 0;
	private Stack<ArrayList<Integer>> jump_if_true_cond = new Stack<>();
	private ArrayList<Integer> jump_over_else = new ArrayList<Integer>();
	private ArrayList<Integer> jump_on_do_again = new ArrayList<Integer>();
	private Stack<ArrayList<Integer>> jump_with_break = new Stack<>();
	private Stack<ArrayList<Integer>> jump_with_continue = new Stack<>();
	private Stack<ArrayList<Integer>> jump_if_false_cond = new Stack<>();

	public int get_mainPC() {
		return mainPC;
	}

	public int getCodeRelOp(Relop relop) {
		int codeRelOp = Code.ne;
		if (relop instanceof RelOpEqual) {
			codeRelOp = Code.eq;
		} else if (relop instanceof RelOpNotEqual) {
			codeRelOp = Code.ne;
		} else if (relop instanceof RelOpGreater) {
			codeRelOp = Code.gt;
		} else if (relop instanceof RelOpGreaterEqual) {
			codeRelOp = Code.ge;
		} else if (relop instanceof RelOpLess) {
			codeRelOp = Code.lt;
		} else if (relop instanceof RelOpLessEqual) {
			codeRelOp = Code.le;
		}
		return codeRelOp;
	}

	@Override
	public void visit(CondFact CondFact) {
		if (CondFact.getOptionalRelExpr() instanceof NoOptionalRelExpr) {
			Code.loadConst(0);
			Code.putFalseJump(Code.ne, 0);
		} else if (CondFact.getOptionalRelExpr() instanceof WithRelExpr) {

			int codeRelOp = getCodeRelOp(((WithRelExpr) CondFact.getOptionalRelExpr()).getRelop());
			Code.putFalseJump(codeRelOp, 0);
		}
		jump_if_false_cond.peek().add(Code.pc - 2);
	}

	@Override
	public void visit(OrPoint OrPoint) {
		Code.putJump(0);
		jump_if_true_cond.peek().add((Code.pc - 2));

		for (int i = 0; i < jump_if_false_cond.peek().size(); i++) {
			Code.fixup((jump_if_false_cond.peek()).get(i));
		}
		jump_if_false_cond.peek().clear();

	}

	@Override
	public void visit(IfCondition ifCondition) {

		for (int i = 0; i < jump_if_true_cond.peek().size(); i++) {
			Code.fixup((jump_if_true_cond.peek()).get(i));
		}
		jump_if_true_cond.peek().clear();
	}

	@Override
	public void visit(DoCondition DoCondition) {

		Code.putJump(jump_on_do_again.get(jump_on_do_again.size() - 1));

		for (int i = 0; i < jump_if_false_cond.peek().size(); i++) {
			Code.fixup((jump_if_false_cond.peek()).get(i));
		}
		jump_if_false_cond.peek().clear();

		for (int i = 0; i < jump_with_break.peek().size(); i++) {
			Code.fixup((jump_with_break.peek()).get(i));
		}
		jump_with_break.peek().clear();

		for (int addrToPatch : jump_if_true_cond.peek()) {
			int doStartAddr = jump_on_do_again.get(jump_on_do_again.size() - 1);
			Code.put2(addrToPatch, doStartAddr - addrToPatch + 1);
		}

		jump_if_true_cond.peek().clear();

	}

	@Override
	public void visit(DesignatorArrElement DesignatorArrElement) {

		Obj desObj = Tab.find(DesignatorArrElement.getArrayName());
		Code.load(desObj);
		Code.put(Code.dup_x1);
		Code.put(Code.pop);

	}

	@Override
	public void visit(DesignatorVarIdent DesignatorVarIdent) {
		Obj desObj = Tab.find(DesignatorVarIdent.getVariableName());
		if (desObj.getKind() != Obj.Meth) {
			Code.load(desObj);

		}
	}

	@Override
	public void visit(FactorNew FactorNew) {
		Code.put(Code.newarray);
		if (FactorNew.getType().struct.equals(Tab.charType)) {
			Code.put(0);
		} else {
			Code.put(1);
		}
	}

	@Override
	public void visit(FactorBool FactorBool) {
		Code.loadConst(FactorBool.getB1() ? 1 : 0);
	}

	@Override
	public void visit(FactorChar FactorChar) {
		Code.loadConst(FactorChar.getC1());
	}

	@Override
	public void visit(FactorNum FactorNum) {

		Code.loadConst(FactorNum.getN1());
	}

	@Override
	public void visit(FactorDesignatorFunction FactorDesignatorFunction) {
		int offset = FactorDesignatorFunction.getDesignator().obj.getAdr() - Code.pc;
		Code.put(Code.call);

		Code.put2(offset);
		FactorDesignatorFunction.struct = FactorDesignatorFunction.getDesignator().obj.getType();
	}

	@Override
	public void visit(FactorDesignatorVar FactorDesignatorVar) {
		Obj designatorObj = FactorDesignatorVar.getDesignator().obj;
		if (designatorObj.getKind() == Obj.Elem) {
			if (designatorObj.getType() == Tab.intType)
				Code.put(Code.aload);
			else
				Code.put(Code.baload);
		}
	}

	@Override
	public void visit(TermMulopFactor TermMulopFactor) {
		Mulop op = TermMulopFactor.getMulop();
		if (op instanceof OpMul) {
			Code.put(Code.mul);
		} else if (op instanceof OpDiv) {
			Code.put(Code.div);
		} else {
			Code.put(Code.rem);
		}
	}

	@Override
	public void visit(WithMinus WithMinus) {
		Code.put(Code.neg);
	}

	@Override
	public void visit(Expr1AddopTerm Expr1AddopTerm) {
		Addop op = Expr1AddopTerm.getAddop();
		if (op instanceof OpPlus) {
			Code.put(Code.add);
		} else {
			Code.put(Code.sub);
		}
	}

	@Override
	public void visit(Expr1Cond TernarExpr) {
		Code.loadConst(0);
		Code.putFalseJump(Code.ne, 0);
		pcAfterFirst = Code.pc - 2;
	}

	@Override
	public void visit(Expr1First TernarExpr) {
		Code.putJump(0);
		pcAfterSecond = Code.pc - 2;
		Code.fixup(pcAfterFirst);
	}

	@Override
	public void visit(Expr1Second TernarExpr) {
		Code.fixup(pcAfterSecond);
	}

	@Override
	public void visit(DesignatorAssignExpr DesignatorAssignExpr) {
		Obj designatorObj = DesignatorAssignExpr.getDesignatorBeforeAssign().getDesignator().obj;
		if (designatorObj.getKind() != Obj.Elem)
			Code.store(designatorObj);
		else if (designatorObj.getType() == Tab.intType)
			Code.put(Code.astore);
		else
			Code.put(Code.bastore);
	}

	@Override
	public void visit(DesignatorFuncCall DesignatorFuncCall) {
		Obj functionObj = DesignatorFuncCall.getDesignatorFunc().getDesignator().obj;
		int offset = functionObj.getAdr() - (Code.pc - 1);
		Code.put(Code.call);
		Code.put2(offset);
		if (DesignatorFuncCall.getDesignatorFunc().getDesignator().obj.getType() != Tab.noType) {
			Code.put(Code.pop);
		}
	}

	@Override
	public void visit(DesignatorDec DesignatorDec) {
		Designator designator = DesignatorDec.getDesignator();
		if (designator.obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		if (designator.obj.getKind() != Obj.Elem)
			Code.load(designator.obj);
		else if (designator.obj.getType() == Tab.intType)
			Code.put(Code.aload);
		else
			Code.put(Code.baload);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(designator.obj);
	}

	@Override
	public void visit(DesignatorInc DesignatorInc) {
		Designator designator = DesignatorInc.getDesignator();
		if (designator.obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}

		if (designator.obj.getKind() != Obj.Elem)
			Code.load(designator.obj);
		else if (designator.obj.getType() == Tab.intType)
			Code.put(Code.aload);
		else
			Code.put(Code.baload);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(designator.obj);

	}

	@Override
	public void visit(WithElseStat WithElseStat) {
		Code.fixup(jump_over_else.remove(jump_over_else.size() - 1));
	}

	@Override
	public void visit(NoElseStat NoElseStat) {

		for (int i = 0; i < jump_if_false_cond.peek().size(); i++) {
			Code.fixup((jump_if_false_cond.peek()).get(i));

		}
		jump_if_false_cond.peek().clear();
	}

	@Override
	public void visit(BeginIf StartIf) {
		jump_if_true_cond.push(new ArrayList<Integer>());
		jump_if_false_cond.push(new ArrayList<Integer>());
	}

	@Override
	public void visit(BeginElse beginElse) {
		Code.putJump(0);
		jump_over_else.add(Code.pc - 2);

		for (int i = 0; i < jump_if_false_cond.peek().size(); i++) {
			Code.fixup((jump_if_false_cond.peek()).get(i));

		}
		jump_if_false_cond.peek().clear();
	}

	@Override
	public void visit(BeginDoWhile StartDoWhile) {
		jump_on_do_again.add(Code.pc);
		jump_with_break.push(new ArrayList<Integer>());
		jump_with_continue.push(new ArrayList<Integer>());
		jump_if_true_cond.push(new ArrayList<Integer>());
		jump_if_false_cond.push(new ArrayList<Integer>());

	}

	@Override
	public void visit(NoReturnStatement NoReturnStatement) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	@Override
	public void visit(StatementReturn StatementReturn) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	@Override
	public void visit(StatementContinue StatementContinue) {
		Code.putJump(0);
		jump_with_continue.peek().add(Code.pc - 2);
	}

	@Override
	public void visit(StatementBreak StatementBreak) {
		Code.putJump(0);
		jump_with_break.peek().add(Code.pc - 2);
	}

	@Override
	public void visit(FinishDoWhile FinishDoWhile) {
		jump_with_continue.peek().forEach(Code::fixup);
	}

	@Override
	public void visit(StatementDo StatementDo) {
		jump_on_do_again.remove(jump_on_do_again.size() - 1);
		jump_with_continue.pop();
		jump_with_break.pop();
		jump_if_true_cond.pop();
		jump_if_false_cond.pop();

	}

	@Override
	public void visit(StatementIfElse StatementIfElse) {
		jump_if_true_cond.pop();
		jump_if_false_cond.pop();
	}

	@Override
	public void visit(StatementPrint StatementPrint) {

		OptionalCommaNum num = StatementPrint.getOptionalCommaNum();
		int value = 3;
		if (num instanceof WithCommaNum) {
			value = ((WithCommaNum) num).getN1();
		}
		Code.loadConst(value);
		if (StatementPrint.getExpr().struct.getKind() == Struct.Char
				|| StatementPrint.getExpr().struct.getElemType() == Tab.charType) {
			Code.put(Code.bprint);
		} else {
			Code.put(Code.print);
		}
	}

	@Override
	public void visit(StatementRead StatementRead) {
		Obj d = StatementRead.getDesignator().obj;

		if (d.getType() == Tab.charType) {
			Code.put(Code.bread);
		} else {
			Code.put(Code.read);
		}

		Code.store(d);
	}

	@Override
	public void visit(VoidMethod VoidMethod) {
		Obj methodObj = Tab.find(VoidMethod.getMethName());
		Tab.openScope();

		methodObj.getLocalSymbols().forEach(Tab.currentScope()::addToLocals);

		if ("main".equals(VoidMethod.getMethName())) {
			mainPC = Code.pc;
		}
		VoidMethod.obj.setAdr(Code.pc);

		Code.put(Code.enter);
		Code.put(methodObj.getLevel());
		Code.put(methodObj.getLocalSymbols().size());

	}

	@Override
	public void visit(MType MType) {
		Obj methodObj = Tab.find(MType.getMethName());
		Tab.openScope();

		methodObj.getLocalSymbols().forEach(Tab.currentScope()::addToLocals);

		MType.obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(methodObj.getLevel());
		Code.put(methodObj.getLocalSymbols().size());
	}

	@Override
	public void visit(MethodDecl MethodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	@Override
	public void visit(ProgramName ProgramName) {
		Obj programObj = Tab.find(ProgramName.getProgramName());
		Tab.openScope();
		Collection<Obj> locals = programObj.getLocalSymbols();
		locals.forEach(Tab.currentScope()::addToLocals);

		Obj chrObj = Tab.find("chr");
		chrObj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(chrObj.getLevel());
		Code.put(chrObj.getLocalSymbols().size());
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);

		Obj ordObj = Tab.find("ord");
		ordObj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(ordObj.getLevel());
		Code.put(ordObj.getLocalSymbols().size());
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);

		Obj lenObj = Tab.find("len");
		lenObj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(lenObj.getLevel());
		Code.put(lenObj.getLocalSymbols().size());
		Code.put(Code.load_n);
		Code.put(Code.arraylength);
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	@Override
	public void visit(Program Program) {
		Tab.closeScope();
	}

}