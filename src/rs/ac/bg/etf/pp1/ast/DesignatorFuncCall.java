// generated with ast extension for cup
// version 0.8
// 15/1/2021 15:30:41


package rs.ac.bg.etf.pp1.ast;

public class DesignatorFuncCall extends DesignatorStatement {

    private DesignatorFunc DesignatorFunc;
    private OptionalActPars OptionalActPars;

    public DesignatorFuncCall (DesignatorFunc DesignatorFunc, OptionalActPars OptionalActPars) {
        this.DesignatorFunc=DesignatorFunc;
        if(DesignatorFunc!=null) DesignatorFunc.setParent(this);
        this.OptionalActPars=OptionalActPars;
        if(OptionalActPars!=null) OptionalActPars.setParent(this);
    }

    public DesignatorFunc getDesignatorFunc() {
        return DesignatorFunc;
    }

    public void setDesignatorFunc(DesignatorFunc DesignatorFunc) {
        this.DesignatorFunc=DesignatorFunc;
    }

    public OptionalActPars getOptionalActPars() {
        return OptionalActPars;
    }

    public void setOptionalActPars(OptionalActPars OptionalActPars) {
        this.OptionalActPars=OptionalActPars;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorFunc!=null) DesignatorFunc.accept(visitor);
        if(OptionalActPars!=null) OptionalActPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorFunc!=null) DesignatorFunc.traverseTopDown(visitor);
        if(OptionalActPars!=null) OptionalActPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorFunc!=null) DesignatorFunc.traverseBottomUp(visitor);
        if(OptionalActPars!=null) OptionalActPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorFuncCall(\n");

        if(DesignatorFunc!=null)
            buffer.append(DesignatorFunc.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptionalActPars!=null)
            buffer.append(OptionalActPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorFuncCall]");
        return buffer.toString();
    }
}
