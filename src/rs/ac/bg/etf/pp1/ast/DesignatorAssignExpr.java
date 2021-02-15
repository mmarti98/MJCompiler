// generated with ast extension for cup
// version 0.8
// 15/1/2021 15:30:41


package rs.ac.bg.etf.pp1.ast;

public class DesignatorAssignExpr extends DesignatorStatement {

    private DesignatorBeforeAssign DesignatorBeforeAssign;
    private Assignop Assignop;
    private Expr Expr;

    public DesignatorAssignExpr (DesignatorBeforeAssign DesignatorBeforeAssign, Assignop Assignop, Expr Expr) {
        this.DesignatorBeforeAssign=DesignatorBeforeAssign;
        if(DesignatorBeforeAssign!=null) DesignatorBeforeAssign.setParent(this);
        this.Assignop=Assignop;
        if(Assignop!=null) Assignop.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public DesignatorBeforeAssign getDesignatorBeforeAssign() {
        return DesignatorBeforeAssign;
    }

    public void setDesignatorBeforeAssign(DesignatorBeforeAssign DesignatorBeforeAssign) {
        this.DesignatorBeforeAssign=DesignatorBeforeAssign;
    }

    public Assignop getAssignop() {
        return Assignop;
    }

    public void setAssignop(Assignop Assignop) {
        this.Assignop=Assignop;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorBeforeAssign!=null) DesignatorBeforeAssign.accept(visitor);
        if(Assignop!=null) Assignop.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorBeforeAssign!=null) DesignatorBeforeAssign.traverseTopDown(visitor);
        if(Assignop!=null) Assignop.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorBeforeAssign!=null) DesignatorBeforeAssign.traverseBottomUp(visitor);
        if(Assignop!=null) Assignop.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorAssignExpr(\n");

        if(DesignatorBeforeAssign!=null)
            buffer.append(DesignatorBeforeAssign.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Assignop!=null)
            buffer.append(Assignop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorAssignExpr]");
        return buffer.toString();
    }
}
