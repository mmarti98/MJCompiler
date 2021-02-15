package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticAnalyzer extends VisitorAdaptor {

	boolean errorDetected = false;
	boolean mainDefined = false;
	boolean returnDefined = false;

	int nVars;
	int doWhile = 0;
	int ifElse = 0;
	int printCallCount = 0;
	int varDeclCount = 0;

	private ArrayList<Struct> currentMethodActParams = new ArrayList<Struct>();
	Struct currentTypeStruct = Tab.noType;
	Obj currentMethodObj = Tab.noObj;
	Logger log = Logger.getLogger(getClass());

	public SemanticAnalyzer() {
		Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", new Struct(Struct.Bool)));
	}

	public boolean passed() {
		return !errorDetected;
	}

	public boolean checkPars(Obj funcObj, SyntaxNode syntaxNode) {
		if (currentMethodActParams.size() != funcObj.getLevel()) {
			report_error("Greska na liniji " 
					+ syntaxNode.getLine()
					+ ", broj proslijedjenih paramatera nije jednak broju formalnih ", null);
			return false;
		}
		int formParsNum = funcObj.getLevel();

		Iterator<Obj> formParsIt = funcObj.getLocalSymbols().iterator();

		for (int i = 0; i < formParsNum; ++i) {

			Struct formParType = formParsIt.next().getType();
			Struct actParType = currentMethodActParams.get(i);
			if (!actParType.assignableTo(formParType)) {
				report_error("Greska na liniji " 
						+ syntaxNode.getLine()
						+ ", proslijedjeni parametar nije moguce dodijeliti formalnom ", null);
				return false;
			}
		}

		return true;
	}

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}

	@Override
	public void visit(CondFact CondFact) {

		if (CondFact.getOptionalRelExpr() instanceof WithRelExpr) {
			Struct leftExpr = CondFact.getExpr().struct;
			WithRelExpr withRelExpr = (WithRelExpr) CondFact.getOptionalRelExpr();
			Struct rightExpr = withRelExpr.getExpr().struct;
			if (!leftExpr.compatibleWith(rightExpr)) {
				report_error("Greska na liniji " 
						+ CondFact.getLine() 
						+ ", CondFact expr nisu kompatibilni ", null);

			}
			Relop relop = withRelExpr.getRelop();
			if (leftExpr.isRefType() 
					&& rightExpr.isRefType()
					&& !(relop instanceof RelOpEqual || relop instanceof RelOpNotEqual)) {
				report_error("Greska na liniji " 
						+ CondFact.getLine()
						+ ", CondFact expr ako su tipa niz mogu samo da se porede sa == ili != ", null);
			}
		}

	}

	@Override
	public void visit(ActParsExpr ActParsExpr) {

		Struct exprStruct = ActParsExpr.getExpr().struct;
		currentMethodActParams.add(exprStruct);
	}

	@Override
	public void visit(ActParsExprList ActParsExpr) {
		Struct exprStruct = ActParsExpr.getExpr().struct;
		currentMethodActParams.add(exprStruct);
	}

	@Override
	public void visit(DesignatorArrElement DesignatorArrElement) {
		String designatorName = DesignatorArrElement.getArrayName();
		Obj desObj = Tab.find(designatorName);

		if (desObj.equals(Tab.noObj)) {
			report_error("Greska na liniji " 
					+ DesignatorArrElement.getLine() 
					+ ", simbol " 
					+ designatorName
					+ " nije deklarisan", null);
			DesignatorArrElement.obj = Tab.noObj;
			return;
		}
		if (desObj.getType().getKind() != Struct.Array) {
			report_error("Greska na liniji " 
					+ DesignatorArrElement.getLine() 
					+ ", simbol " 
					+ designatorName
					+ " nije tipa niz", null);
			DesignatorArrElement.obj = Tab.noObj;
			return;
		}
		if (DesignatorArrElement.getExpr().struct.getKind() != Struct.Int) {
			report_error("Greska na liniji " 
					+ DesignatorArrElement.getLine() 
					+ ", Expr kod pristupa nizu "
					+ designatorName + " nije tipa int", null);
			DesignatorArrElement.obj = Tab.noObj;
			return;
		} else {
			Struct elementType = desObj.getType().getElemType();
			DesignatorArrElement.obj = new Obj(Obj.Elem, designatorName, elementType);
		}

		CustomDumpSymbolTableVisitor visitor = new CustomDumpSymbolTableVisitor();
		visitor.visitObjNode(DesignatorArrElement.obj);

		Obj currentDesignatorObj = DesignatorArrElement.obj;
		if (currentMethodObj != Tab.noObj && !currentMethodObj.getName().equals("main")) {
			Collection<Obj> locals = currentMethodObj.getLocalSymbols();
			int numFormPars = currentMethodObj.getLevel();
			int i = 0;
			for (Iterator iterator = locals.iterator(); iterator.hasNext();) {
				Obj obj = (Obj) iterator.next();
				if (i < numFormPars && obj.equals(currentDesignatorObj)) {
					report_info("Formalni parametar " 
							+ currentDesignatorObj.getName() 
							+ " pronadjen na liniji "
							+ DesignatorArrElement.getLine() 
							+ " " + visitor.getOutput(), null);
				}
				i++;
			}
		} else {
			if (currentDesignatorObj.getKind() == Obj.Con) {
				report_info("Konstanta " 
						+ currentDesignatorObj.getName() 
						+ " pronadjena na liniji "
						+ DesignatorArrElement.getLine() 
						+ " " + visitor.getOutput(), null);
			}
			if (currentDesignatorObj.getKind() == Obj.Var) {
				if (currentDesignatorObj.getLevel() == 0) {
					report_info("Globalna "
							+ currentDesignatorObj.getName() 
							+ " pronadjena na liniji "
							+ DesignatorArrElement.getLine() 
							+ " " + visitor.getOutput(), null);
				}
				if (currentDesignatorObj.getLevel() == 1) {
					report_info("Lokalna " + currentDesignatorObj.getName() 
							+ " pronadjena na liniji "
							+ DesignatorArrElement.getLine() 
							+ " " + visitor.getOutput(), null);
				}
			}
			if (DesignatorArrElement.obj.getKind() == Obj.Elem) {
				report_info("Element  " 
						+ currentDesignatorObj.getName() 
						+ " pronadjen na liniji "
						+ DesignatorArrElement.getLine() 
						+ " " + visitor.getOutput(), null);
			}
		}
	}

	@Override
	public void visit(DesignatorVarIdent DesignatorVarIdent) {
		String designatorName = DesignatorVarIdent.getVariableName();
		Obj desObj = Tab.find(designatorName);

		if (desObj.equals(Tab.noObj)) {
			report_error("Greska na liniji " 
					+ DesignatorVarIdent.getLine() 
					+ ", simbol " + designatorName
					+ " nije deklarisan", null);
			DesignatorVarIdent.obj = Tab.noObj;
			return;
		}
		DesignatorVarIdent.obj = desObj;
		CustomDumpSymbolTableVisitor visitor = new CustomDumpSymbolTableVisitor();
		visitor.visitObjNode(DesignatorVarIdent.obj);
		Obj currentDesignatorObj = DesignatorVarIdent.obj;

		if (currentMethodObj != Tab.noObj && !currentMethodObj.getName().equals("main")) {
			Collection<Obj> locals = currentMethodObj.getLocalSymbols();
			int numFormPars = currentMethodObj.getLevel();
			int i = 0;
			for (Iterator iterator = locals.iterator(); iterator.hasNext();) {
				Obj obj = (Obj) iterator.next();
				if (i < numFormPars && obj.equals(currentDesignatorObj)) {
					report_info("Formalni parametar " 
							+ currentDesignatorObj.getName() 
							+ " pronadjen na liniji "
							+ DesignatorVarIdent.getLine() 
							+ " " + visitor.getOutput(), null);
				}
				i++;
			}
		} else {
			if (currentDesignatorObj.getKind() == Obj.Con) {
				report_info("Konstanta " 
						+ currentDesignatorObj.getName() 
						+ " pronadjena na liniji "
						+ DesignatorVarIdent.getLine() 
						+ " " + visitor.getOutput(), null);
			}
			if (currentDesignatorObj.getKind() == Obj.Var) {
				if (currentDesignatorObj.getLevel() == 0) {
					report_info("Globalna " 
							+ currentDesignatorObj.getName() 
							+ " pronadjena na liniji "
							+ DesignatorVarIdent.getLine() 
							+ " " + visitor.getOutput(), null);
				}
				if (currentDesignatorObj.getLevel() == 1) {
					report_info("Lokalna " 
							+ currentDesignatorObj.getName()
							+ " pronadjena na liniji "
							+ DesignatorVarIdent.getLine() 
							+ " " + visitor.getOutput(), null);
				}
			}
			if (DesignatorVarIdent.obj.getKind() == Obj.Elem) {
				report_info("Element  " 
						+ currentDesignatorObj.getName() 
						+ " pronadjen na liniji "
						+ DesignatorVarIdent.getLine() 
						+ " " + visitor.getOutput(), null);
			}

		}
	}

	@Override
	public void visit(FactorNew FactorNew) {
		Struct exprType = FactorNew.getExpr().struct;
		if (!(exprType.equals(Tab.intType))) {
			report_error("Greska na liniji " 
					+ FactorNew.getLine() 
					+ ", expr za velicinu niza nije tipa int", null);
			return;
		}
		FactorNew.struct = new Struct(Struct.Array, currentTypeStruct);
	}

	@Override
	public void visit(FactorBool FactorBool) {
		FactorBool.struct = Tab.find("bool").getType();
	}

	@Override
	public void visit(FactorParenExpr FactorParenExpr) {
		FactorParenExpr.struct = FactorParenExpr.getExpr().struct;
	}

	@Override
	public void visit(FactorChar FactorChar) {
		FactorChar.struct = Tab.charType;
	}

	@Override
	public void visit(FactorNum FactorNum) {
		FactorNum.struct = Tab.intType;
	}

	@Override
	public void visit(FactorDesignatorFunction FactorDesignatorFunction) {
		if (FactorDesignatorFunction.getDesignator().obj.getKind() != Obj.Meth) {
			report_error("Greska na liniji "
					+ FactorDesignatorFunction.getLine() 
					+ ", ocekivan designator tipa metode",
					null);
			return;
		}
		if (checkPars(FactorDesignatorFunction.getDesignator().obj, FactorDesignatorFunction)) {
			CustomDumpSymbolTableVisitor visitor = new CustomDumpSymbolTableVisitor();
			visitor.visitObjNode(FactorDesignatorFunction.getDesignator().obj);

			String methName = FactorDesignatorFunction.getDesignator().obj.getName();

			report_info("Pronadjena na liniji " 
					+ FactorDesignatorFunction.getLine() 
					+ ", upotreba (factor) " + methName
					+ " metode " + visitor.getOutput(), null);

			FactorDesignatorFunction.struct = FactorDesignatorFunction.getDesignator().obj.getType();
			currentMethodActParams.clear();
		}
	}

	@Override
	public void visit(FactorDesignatorVar FactorDesignatorVar) {

		if (FactorDesignatorVar.getDesignator().obj.getKind() == Obj.Var
				|| FactorDesignatorVar.getDesignator().obj.getKind() == Obj.Con
				|| FactorDesignatorVar.getDesignator().obj.getKind() == Obj.Elem) {

			FactorDesignatorVar.struct = FactorDesignatorVar.getDesignator().obj.getType();
		} else {
			report_error("Greska na liniji" 
					+ FactorDesignatorVar.getLine() 
					+ ", factorDesignator  "
					+ FactorDesignatorVar.getDesignator().obj.getName()
					+ "nije ni var ni const", null);
		}

	}

	@Override
	public void visit(TermFactor TermFactor) {
		TermFactor.struct = TermFactor.getFactor().struct;
	}

	@Override
	public void visit(TermMulopFactor TermMulopFactor) {

		Struct termStruct = TermMulopFactor.getTerm().struct;
		Struct factorStruct = TermMulopFactor.getFactor().struct;

		if ((termStruct.equals(Tab.intType) || termStruct.getElemType().equals(Tab.intType))
				&& (factorStruct.equals(Tab.intType) || factorStruct.getElemType().equals(Tab.intType))) {
			TermMulopFactor.struct = Tab.intType;
		} else {
			TermMulopFactor.struct = Tab.noType;
			report_error("Greska na liniji " 
					+ TermMulopFactor.getLine() 
					+ ", term i factor kod mulop nisu tipa int",
					null);
		}
	}

	@Override
	public void visit(Expr1AddopTerm Expr1AddopTerm) {

		Struct termStruct = Expr1AddopTerm.getTerm().struct;
		Struct exprStruct = Expr1AddopTerm.getExpr1().struct;

		if (termStruct.compatibleWith(exprStruct)
				&& (termStruct.equals(Tab.intType) || termStruct.getElemType().equals(Tab.intType))
				&& (exprStruct.equals(Tab.intType) || exprStruct.getElemType().equals(Tab.intType))) {
			Expr1AddopTerm.struct = Tab.intType;
		} else {
			Expr1AddopTerm.struct = Tab.noType;
			report_error("Greska na liniji " 
					+ Expr1AddopTerm.getLine()
					+ ", term i expr1 kod addop nisu tipa int/kompatibilni", null);
		}
	}

	@Override
	public void visit(Expr1Term Expr1Term) {
		OptionalMinus optionalMinusParent = Expr1Term.getOptionalMinus();

		if (optionalMinusParent instanceof WithMinus) {
			if (!(Expr1Term.getTerm().struct).equals(Tab.intType) || ((Expr1Term.getTerm().struct.getElemType() != null)
					&& !(Expr1Term.getTerm().struct.getElemType()).equals(Tab.intType))) {
				report_error("Greska na liniji " 
						+ Expr1Term.getLine() 
						+ ", expr ispred kog je minus nije tipa int",
						null);
				Expr1Term.struct = Tab.noType;
				return;
			}
			Expr1Term.struct = Expr1Term.getTerm().struct;

		} else if (optionalMinusParent instanceof NoMinus) {
			Expr1Term.struct = Expr1Term.getTerm().struct;
		}
	}

	@Override
	public void visit(OneExpr OneExpr) {
		OneExpr.struct = OneExpr.getExpr1().struct;
	}

	@Override
	public void visit(TernarExpr TernarExpr) {
		Struct firstExprStruct = TernarExpr.getExpr1First().getExpr1().struct;
		Struct secondExprStruct = TernarExpr.getExpr1Second().getExpr1().struct;
		if (firstExprStruct.equals(secondExprStruct)) {
			TernarExpr.struct = firstExprStruct;

		} else {
			report_error("Greska na liniji " 
					+ TernarExpr.getLine()
					+ ", expr kod ternarnog moraju biti istog tipa",
					null);
		}

	}

	@Override
	public void visit(DesignatorAssignExpr DesignatorAssignExpr) {
		Obj desObj = DesignatorAssignExpr.getDesignatorBeforeAssign().getDesignator().obj;
		if (desObj.getKind() != Obj.Elem && desObj.getKind() != Obj.Var) {
			report_error("Greska na liniji " 
					+ DesignatorAssignExpr.getLine()
					+ ", " + desObj.getName()
					+ " nije promjenljiva ni element niza!", null);

			return;
		}

		Struct expr = DesignatorAssignExpr.getExpr().struct;

		if (!expr.assignableTo(desObj.getType())) {
			report_error("Greska na liniji "
					+ DesignatorAssignExpr.getLine()
					+ ", tipovi designatora i expr nisu kompatibilni pri dodjeli", null);
			return;
		}
		DesignatorAssignExpr.struct = DesignatorAssignExpr.getExpr().struct;
	}

	@Override
	public void visit(DesignatorFuncCall DesignatorFuncCall) {

		Obj methObj = DesignatorFuncCall.getDesignatorFunc().getDesignator().obj;
		String methName = methObj.getName();

		if (methObj == Tab.noObj) {

			report_error("Greska na liniji " 
					+ DesignatorFuncCall.getLine() 
					+ ", ne postoji metoda sa imenom " + methName,
					null);

			return;
		}

		if (methObj.getKind() != Obj.Meth) {

			report_error("Greska na liniji " 
					+ DesignatorFuncCall.getLine() 
					+ ",  " + methName
					+ "postoji ali nije metoda",
					null);

			return;
		}

		if (!checkPars(methObj, DesignatorFuncCall)) {
			return;
		}
		CustomDumpSymbolTableVisitor visitor = new CustomDumpSymbolTableVisitor();
		visitor.visitObjNode(methObj);

		report_info("Pronadjena na liniji " 
				+ DesignatorFuncCall.getLine() 
				+ ", upotreba " + methName + "metode "
				+ visitor.getOutput(), null);
		currentMethodActParams.clear();

	}

	@Override
	public void visit(DesignatorDec DesignatorDec) {
		Obj desObj = DesignatorDec.getDesignator().obj;
		if (!desObj.getType().equals(Tab.intType)) {
			report_error("Greska na liniji " 
					+ DesignatorDec.getLine() 
					+ ", tip " + desObj.getName()
					+ " nije int!",
					null);

			return;
		}
		if (desObj.getKind() != Obj.Elem && desObj.getKind() != Obj.Var) {
			report_error("Greska na liniji " 
					+ DesignatorDec.getLine() 
					+ ", " + desObj.getName()
					+ " nije promjenljiva ni element niza!", null);

			return;
		}
	}

	@Override
	public void visit(DesignatorInc DesignatorInc) {
		Obj desObj = DesignatorInc.getDesignator().obj;
		if (!desObj.getType().equals(Tab.intType)) {
			report_error("Greska na liniji "
					+ DesignatorInc.getLine() 
					+ ", tip " + desObj.getName()
					+ " nije int!",
					null);

			return;
		}

		if (desObj.getKind() != Obj.Elem && desObj.getKind() != Obj.Var) {
			report_error("Greska na liniji " 
					+ DesignatorInc.getLine() 
					+ ", " + desObj.getName()
					+ " nije promjenljiva ni element niza!", null);

			return;
		}
	}

	@Override
	public void visit(NoReturnStatement NoReturnStatement) {
		if (currentMethodObj == Tab.noObj) {
			report_error("Greska na liniji "
					+ NoReturnStatement.getLine()
					+ " return statement moze da se pojavi samo unutar metode ", null);
			return;
		}
		if (currentMethodObj.getType() != Tab.noType) {
			report_error("Greska na liniji " 
					+ NoReturnStatement.getLine()
					+ " return statement bez expr moze samo u void metodama ", null);
			return;
		}
		returnDefined = true;
	}

	@Override
	public void visit(StatementReturn StatementReturn) {
		if (currentMethodObj == Tab.noObj) {
			report_error("Greska na liniji " 
					+ StatementReturn.getLine()
					+ " return statement moze da se pojavi samo unutar metode  ", null);
			return;
		}
		Struct exprStruct = StatementReturn.getExpr().struct;
		if (!currentMethodObj.getType().equals(exprStruct)) {
			report_error("Greska na liniji "
					+ StatementReturn.getLine()
					+ " returnStatement expr mora biti ekvivalentan povratnom tipu metode ", null);
			return;
		}
		returnDefined = true;
	}

	@Override
	public void visit(StatementContinue StatementContinue) {
		if (doWhile == 0) {
			report_error("Greska na liniji "
					+ StatementContinue.getLine()
					+ " continue statement moze da se pojavi samo unutar do-while  ", null);

			return;
		}
	}

	@Override
	public void visit(StatementBreak StatementBreak) {
		if (doWhile == 0) {
			report_error("Greska na liniji " 
					+ StatementBreak.getLine()
					+ " break statement moze da se pojavi samo unutar do-while  ", null);

			return;
		}
	}

	@Override
	public void visit(StatementDo StatementDo) {
		doWhile--;
	}

	@Override
	public void visit(BeginDoWhile startDoWhile) {
		doWhile++;
	}

	@Override
	public void visit(StatementIfElse StatementIfElse) {
		ifElse--;
	}

	@Override
	public void visit(BeginIf startIf) {
		ifElse++;
	}

	@Override
	public void visit(StatementPrint StatementPrint) {

		printCallCount++;
		Struct exprType = StatementPrint.getExpr().struct;
		if (exprType.getKind() == Struct.Array) {

			exprType = exprType.getElemType();
		}
		Struct boolType = Tab.find("bool").getType();

		if (exprType != Tab.noType) {
			if (exprType.equals(Tab.intType) 
					|| exprType.equals(boolType)
					|| exprType.equals(Tab.charType)) {
				return;
			} else {
				report_error("Greska na liniji " 
						+ StatementPrint.getLine()
						+ ", expr nije tipa int/char/bool", null);
			}
		}
	}

	@Override
	public void visit(StatementRead StatementRead) {
		Obj designatorObj = StatementRead.getDesignator().obj;
		int designatorKind = designatorObj.getKind();

		if (designatorKind != Obj.Var
				&& designatorKind != Obj.Elem) {
			report_error("Greska na liniji " 
					+ StatementRead.getLine() 
					+ ", designator nije ni promjenljiva ni element niza",
					null);
			return;
		}

		Struct designatorType = designatorObj.getType();
		Struct boolType = Tab.find("bool").getType();

		if (designatorType != Tab.noType) {
			if (designatorType.equals(Tab.intType)
					|| designatorType.equals(boolType)
					|| designatorType.equals(Tab.charType)) {
				return;
			} else {
				report_error("Greska na liniji " 
						+ StatementRead.getLine() 
						+ ", designator nije tipa int/char/bool",
						null);
			}
		}
	}

	@Override
	public void visit(BoolConst BoolConst) {
		Struct currentConstantTypeStruct = Tab.find("bool").getType();

		if (currentTypeStruct == Tab.noType) {
			return;
		}

		String constName = BoolConst.getBoolName();

		if (Tab.find(constName) != Tab.noObj) {
			report_error("Greska na liniji " 
					+ BoolConst.getLine() 
					+ " pri deklaraciji konstante, simbol " + constName
					+ " je vec definisan!", null);
			return;
		}

		if (!currentConstantTypeStruct.equals(currentTypeStruct)
				&& !currentConstantTypeStruct.assignableTo(currentTypeStruct)) {
			report_error("Greska na liniji " 
					+ BoolConst.getLine() 
					+ ", ne poklapa se tip pri dodjeli konstante "
					+ constName, null);

			return;
		} else {
			Obj constObj = Tab.insert(Obj.Con, constName, currentConstantTypeStruct);
			constObj.setLevel(0);
			if (BoolConst.getBoolValue() == true) {
				constObj.setAdr(1);
			} else {
				constObj.setAdr(0);
			}
		}
	}

	@Override
	public void visit(CharConst CharConst) {
		Struct currentConstantTypeStruct = Tab.charType;

		if (currentTypeStruct == Tab.noType) {
			return;
		}

		String constName = CharConst.getCharName();

		if (Tab.find(constName) != Tab.noObj) {
			report_error("Greska na liniji " 
					+ CharConst.getLine() 
					+ " pri deklaraciji konstante, simbol " + constName
					+ " je vec definisan!", null);
			return;
		}

		if (!currentConstantTypeStruct.assignableTo(currentTypeStruct)) {
			report_error("Greska na liniji "
					+ CharConst.getLine() 
					+ ", ne poklapa se tip pri dodjeli konstante "
					+ constName, null);
			return;
		} else {
			Obj constObj = Tab.insert(Obj.Con, constName, currentConstantTypeStruct);

			constObj.setLevel(0);
			constObj.setAdr(CharConst.getCharValue());

		}

	}

	@Override
	public void visit(NumConst NumConst) {
		Struct currentConstantTypeStruct = Tab.intType;

		if (currentTypeStruct == Tab.noType) {
			return;
		}

		String constName = NumConst.getIntName();
		if (Tab.find(constName) != Tab.noObj) {
			report_error("Greska na liniji " 
					+ NumConst.getLine()
					+ " pri deklaraciji konstante, simbol " + constName
					+ " je vec definisan!", null);
			return;
		}

		if (!currentConstantTypeStruct.equals(currentTypeStruct)
				&& !currentConstantTypeStruct.assignableTo(currentTypeStruct)) {
			report_error("Greska na liniji " 
					+ NumConst.getLine() 
					+ ", ne poklapa se tip pri dodjeli konstante " + constName,
					null);

			return;
		} else {
			Obj constObj = Tab.insert(Obj.Con, constName, currentConstantTypeStruct);
			constObj.setLevel(0);
			constObj.setAdr(NumConst.getIntValue());
		}
	}

	@Override
	public void visit(ParameterArray ParameterArray) {
		String formParName = ParameterArray.getI2();
		Obj formParObj = Tab.currentScope.findSymbol(formParName);
		if (formParObj == Tab.noObj 
				&& currentMethodObj != Tab.noObj) {

			currentMethodObj.setLevel(currentMethodObj.getLevel() + 1);
			Tab.insert(Obj.Var, formParName, new Struct(Struct.Array, currentTypeStruct));
			currentMethodObj.setLocals(Tab.currentScope().getLocals());

		} else if (formParObj != Tab.noObj) {
			report_error("Greska na liniji "
					+ ParameterArray.getLine() 
					+ " pri deklaraciji formalnog parametra, simbol "
					+ formParName + " je vec definisan u trenutnom opsegu",
					null);
		}
	}

	@Override
	public void visit(ParameterVar ParameterVar) {
		String formParName = ParameterVar.getI2();
		Obj formParObj = Tab.currentScope.findSymbol(formParName);
		if (formParObj == null && currentMethodObj != Tab.noObj) {

			currentMethodObj.setLevel(currentMethodObj.getLevel() + 1);
			Tab.insert(Obj.Var, formParName, currentTypeStruct);
			currentMethodObj.setLocals(Tab.currentScope().getLocals());

		} else if (formParObj != null) {
			report_error("Greska na liniji "
					+ ParameterVar.getLine()
					+ " pri deklaraciji formalnog parametra, simbol "
					+ formParName + " je vec definisan u trenutnom opsegu", null);
		}
	}

	@Override
	public void visit(VarArrayIdent VarArrayIdent) {
		if (currentTypeStruct == Tab.noType) {
			return;
		}
		varDeclCount++;
		String varName = VarArrayIdent.getArrayName();
		if (Tab.currentScope.findSymbol(varName) != null) {
			report_error("Greska na liniji " 
					+ VarArrayIdent.getLine() 
					+ " pri deklaraciji varijable, simbol " + varName
					+ " je vec definisan u trenutnom opsegu", null);
			return;
		}

		Struct arrayStruct = new Struct(Struct.Array, currentTypeStruct);
		Tab.insert(Obj.Var, varName, arrayStruct);

	}

	@Override
	public void visit(VarIdent VarIdent) {
		if (currentTypeStruct == Tab.noType) {
			return;
		}
		varDeclCount++;
		String varName = VarIdent.getVarName();
		if (Tab.currentScope.findSymbol(varName) != null) {
			report_error("Greska na liniji " 
					+ VarIdent.getLine() 
					+ " pri deklaraciji varijable, simbol " + varName
					+ " je vec definisan u trenutnom opsegu", null);
			return;
		}

		Tab.insert(Obj.Var, varName, currentTypeStruct);

	}

	@Override
	public void visit(Type Type) {
		String typeName = Type.getTypeName();
		Obj typeObj = Tab.find(typeName);

		if (typeObj == Tab.noObj) {
			report_error("Greska na liniji " 
					+ Type.getLine() 
					+ ", tip " + typeName 
					+ " ne postoji!", null);
			currentTypeStruct = Tab.noType;
			Type.struct = Tab.noType;
			return;
		} else if (typeObj.getKind() != Obj.Type) {
			report_error("Greska na liniji " 
					+ Type.getLine() 
					+ ", " + typeName 
					+ " nije tip!", null);
			currentTypeStruct = Tab.noType;
			Type.struct = Tab.noType;
			return;
		}
		currentTypeStruct = typeObj.getType();
		Type.struct = typeObj.getType();

	}

	@Override
	public void visit(VoidMethod VoidMethod) {
		Obj pom = Tab.find(VoidMethod.getMethName());
		if (pom != Tab.noObj) {
			report_error("Greska na liniji " 
					+ VoidMethod.getLine() 
					+ ", metoda "
					+ VoidMethod.getMethName()
					+ " vec postoji u tabeli simbola!", null);
			return;
		}
		VoidMethod.obj = Tab.insert(Obj.Meth, VoidMethod.getMethName(), Tab.noType);
		currentMethodObj = VoidMethod.obj;
		Tab.openScope();
	}

	@Override
	public void visit(MType MType) {
		Obj pom = Tab.find(MType.getMethName());
		if (pom != Tab.noObj) {
			report_error("Greska na liniji "
					+ MType.getLine() 
					+ ", metoda " 
					+ MType.getMethName()
					+ " vec postoji u tabeli simbola!", null);
			return;
		}
		MType.obj = Tab.insert(Obj.Meth, MType.getMethName(), MType.getType().struct);
		currentMethodObj = MType.obj;
		Tab.openScope();

	}

	@Override
	public void visit(MethodDecl MethodDecl) {

		if (currentMethodObj != Tab.noObj) {
			String methodName = MethodDecl.getMethodTypeName().obj.getName();

			if (methodName.equals("main") 
					&& MethodDecl.getOptionalFormPars() instanceof NoFormPars
					&& MethodDecl.getMethodTypeName() instanceof VoidMethod) {
				mainDefined = true;
			}

			if (currentMethodObj.getType() != Tab.noType 
					&& returnDefined == false) {
				report_error("Greska na liniji "
						+ MethodDecl.getLine()
						+ ", " + methodName 
						+ " nema returnStatemet!",
						null);
			}

			Tab.chainLocalSymbols(currentMethodObj);
			currentMethodObj = Tab.noObj;
		}

		Tab.closeScope();
		returnDefined = false;

	}

	@Override
	public void visit(ProgramName ProgramName) {
		ProgramName.obj = Tab.insert(Obj.Prog, ProgramName.getProgramName(), Tab.noType);
		Tab.openScope();
	}

	@Override
	public void visit(Program Program) {
		Obj programObj = Tab.find(Program.getProgName().obj.getName());
		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(programObj);
		Tab.closeScope();

		if (!mainDefined) {
			report_error("Fatalna greska: main metoda nije definisana!", null);
		}
		mainDefined = false;
	}

}