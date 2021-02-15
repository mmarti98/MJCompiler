// generated with ast extension for cup
// version 0.8
// 15/1/2021 15:30:41


package rs.ac.bg.etf.pp1.ast;

public class StatementIfElse extends Statement {

    private BeginIf BeginIf;
    private IfCondition IfCondition;
    private BeginThen BeginThen;
    private Then Then;

    public StatementIfElse (BeginIf BeginIf, IfCondition IfCondition, BeginThen BeginThen, Then Then) {
        this.BeginIf=BeginIf;
        if(BeginIf!=null) BeginIf.setParent(this);
        this.IfCondition=IfCondition;
        if(IfCondition!=null) IfCondition.setParent(this);
        this.BeginThen=BeginThen;
        if(BeginThen!=null) BeginThen.setParent(this);
        this.Then=Then;
        if(Then!=null) Then.setParent(this);
    }

    public BeginIf getBeginIf() {
        return BeginIf;
    }

    public void setBeginIf(BeginIf BeginIf) {
        this.BeginIf=BeginIf;
    }

    public IfCondition getIfCondition() {
        return IfCondition;
    }

    public void setIfCondition(IfCondition IfCondition) {
        this.IfCondition=IfCondition;
    }

    public BeginThen getBeginThen() {
        return BeginThen;
    }

    public void setBeginThen(BeginThen BeginThen) {
        this.BeginThen=BeginThen;
    }

    public Then getThen() {
        return Then;
    }

    public void setThen(Then Then) {
        this.Then=Then;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(BeginIf!=null) BeginIf.accept(visitor);
        if(IfCondition!=null) IfCondition.accept(visitor);
        if(BeginThen!=null) BeginThen.accept(visitor);
        if(Then!=null) Then.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(BeginIf!=null) BeginIf.traverseTopDown(visitor);
        if(IfCondition!=null) IfCondition.traverseTopDown(visitor);
        if(BeginThen!=null) BeginThen.traverseTopDown(visitor);
        if(Then!=null) Then.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(BeginIf!=null) BeginIf.traverseBottomUp(visitor);
        if(IfCondition!=null) IfCondition.traverseBottomUp(visitor);
        if(BeginThen!=null) BeginThen.traverseBottomUp(visitor);
        if(Then!=null) Then.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementIfElse(\n");

        if(BeginIf!=null)
            buffer.append(BeginIf.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(IfCondition!=null)
            buffer.append(IfCondition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(BeginThen!=null)
            buffer.append(BeginThen.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Then!=null)
            buffer.append(Then.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementIfElse]");
        return buffer.toString();
    }
}
