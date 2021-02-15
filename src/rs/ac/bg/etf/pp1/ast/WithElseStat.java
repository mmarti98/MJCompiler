// generated with ast extension for cup
// version 0.8
// 15/1/2021 15:30:41


package rs.ac.bg.etf.pp1.ast;

public class WithElseStat extends Then {

    private Statement Statement;
    private BeginElse BeginElse;
    private Statement Statement1;

    public WithElseStat (Statement Statement, BeginElse BeginElse, Statement Statement1) {
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.BeginElse=BeginElse;
        if(BeginElse!=null) BeginElse.setParent(this);
        this.Statement1=Statement1;
        if(Statement1!=null) Statement1.setParent(this);
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public BeginElse getBeginElse() {
        return BeginElse;
    }

    public void setBeginElse(BeginElse BeginElse) {
        this.BeginElse=BeginElse;
    }

    public Statement getStatement1() {
        return Statement1;
    }

    public void setStatement1(Statement Statement1) {
        this.Statement1=Statement1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Statement!=null) Statement.accept(visitor);
        if(BeginElse!=null) BeginElse.accept(visitor);
        if(Statement1!=null) Statement1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(BeginElse!=null) BeginElse.traverseTopDown(visitor);
        if(Statement1!=null) Statement1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(BeginElse!=null) BeginElse.traverseBottomUp(visitor);
        if(Statement1!=null) Statement1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("WithElseStat(\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(BeginElse!=null)
            buffer.append(BeginElse.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement1!=null)
            buffer.append(Statement1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [WithElseStat]");
        return buffer.toString();
    }
}
