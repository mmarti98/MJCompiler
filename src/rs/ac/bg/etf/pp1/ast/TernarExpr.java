// generated with ast extension for cup
// version 0.8
// 15/1/2021 15:30:41


package rs.ac.bg.etf.pp1.ast;

public class TernarExpr extends Expr {

    private Expr1Cond Expr1Cond;
    private Expr1First Expr1First;
    private Expr1Second Expr1Second;

    public TernarExpr (Expr1Cond Expr1Cond, Expr1First Expr1First, Expr1Second Expr1Second) {
        this.Expr1Cond=Expr1Cond;
        if(Expr1Cond!=null) Expr1Cond.setParent(this);
        this.Expr1First=Expr1First;
        if(Expr1First!=null) Expr1First.setParent(this);
        this.Expr1Second=Expr1Second;
        if(Expr1Second!=null) Expr1Second.setParent(this);
    }

    public Expr1Cond getExpr1Cond() {
        return Expr1Cond;
    }

    public void setExpr1Cond(Expr1Cond Expr1Cond) {
        this.Expr1Cond=Expr1Cond;
    }

    public Expr1First getExpr1First() {
        return Expr1First;
    }

    public void setExpr1First(Expr1First Expr1First) {
        this.Expr1First=Expr1First;
    }

    public Expr1Second getExpr1Second() {
        return Expr1Second;
    }

    public void setExpr1Second(Expr1Second Expr1Second) {
        this.Expr1Second=Expr1Second;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr1Cond!=null) Expr1Cond.accept(visitor);
        if(Expr1First!=null) Expr1First.accept(visitor);
        if(Expr1Second!=null) Expr1Second.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr1Cond!=null) Expr1Cond.traverseTopDown(visitor);
        if(Expr1First!=null) Expr1First.traverseTopDown(visitor);
        if(Expr1Second!=null) Expr1Second.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr1Cond!=null) Expr1Cond.traverseBottomUp(visitor);
        if(Expr1First!=null) Expr1First.traverseBottomUp(visitor);
        if(Expr1Second!=null) Expr1Second.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("TernarExpr(\n");

        if(Expr1Cond!=null)
            buffer.append(Expr1Cond.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr1First!=null)
            buffer.append(Expr1First.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr1Second!=null)
            buffer.append(Expr1Second.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TernarExpr]");
        return buffer.toString();
    }
}
