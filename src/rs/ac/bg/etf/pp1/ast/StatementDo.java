// generated with ast extension for cup
// version 0.8
// 15/1/2021 15:30:41


package rs.ac.bg.etf.pp1.ast;

public class StatementDo extends Statement {

    private BeginDoWhile BeginDoWhile;
    private Statement Statement;
    private FinishDoWhile FinishDoWhile;
    private DoCondition DoCondition;

    public StatementDo (BeginDoWhile BeginDoWhile, Statement Statement, FinishDoWhile FinishDoWhile, DoCondition DoCondition) {
        this.BeginDoWhile=BeginDoWhile;
        if(BeginDoWhile!=null) BeginDoWhile.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.FinishDoWhile=FinishDoWhile;
        if(FinishDoWhile!=null) FinishDoWhile.setParent(this);
        this.DoCondition=DoCondition;
        if(DoCondition!=null) DoCondition.setParent(this);
    }

    public BeginDoWhile getBeginDoWhile() {
        return BeginDoWhile;
    }

    public void setBeginDoWhile(BeginDoWhile BeginDoWhile) {
        this.BeginDoWhile=BeginDoWhile;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public FinishDoWhile getFinishDoWhile() {
        return FinishDoWhile;
    }

    public void setFinishDoWhile(FinishDoWhile FinishDoWhile) {
        this.FinishDoWhile=FinishDoWhile;
    }

    public DoCondition getDoCondition() {
        return DoCondition;
    }

    public void setDoCondition(DoCondition DoCondition) {
        this.DoCondition=DoCondition;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(BeginDoWhile!=null) BeginDoWhile.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(FinishDoWhile!=null) FinishDoWhile.accept(visitor);
        if(DoCondition!=null) DoCondition.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(BeginDoWhile!=null) BeginDoWhile.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(FinishDoWhile!=null) FinishDoWhile.traverseTopDown(visitor);
        if(DoCondition!=null) DoCondition.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(BeginDoWhile!=null) BeginDoWhile.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(FinishDoWhile!=null) FinishDoWhile.traverseBottomUp(visitor);
        if(DoCondition!=null) DoCondition.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementDo(\n");

        if(BeginDoWhile!=null)
            buffer.append(BeginDoWhile.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FinishDoWhile!=null)
            buffer.append(FinishDoWhile.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DoCondition!=null)
            buffer.append(DoCondition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementDo]");
        return buffer.toString();
    }
}
