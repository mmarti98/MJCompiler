// generated with ast extension for cup
// version 0.8
// 15/1/2021 15:30:41


package rs.ac.bg.etf.pp1.ast;

public class StatementPrint extends Statement {

    private Expr Expr;
    private OptionalCommaNum OptionalCommaNum;

    public StatementPrint (Expr Expr, OptionalCommaNum OptionalCommaNum) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.OptionalCommaNum=OptionalCommaNum;
        if(OptionalCommaNum!=null) OptionalCommaNum.setParent(this);
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public OptionalCommaNum getOptionalCommaNum() {
        return OptionalCommaNum;
    }

    public void setOptionalCommaNum(OptionalCommaNum OptionalCommaNum) {
        this.OptionalCommaNum=OptionalCommaNum;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
        if(OptionalCommaNum!=null) OptionalCommaNum.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(OptionalCommaNum!=null) OptionalCommaNum.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(OptionalCommaNum!=null) OptionalCommaNum.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementPrint(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptionalCommaNum!=null)
            buffer.append(OptionalCommaNum.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementPrint]");
        return buffer.toString();
    }
}
