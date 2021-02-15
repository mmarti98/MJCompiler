// generated with ast extension for cup
// version 0.8
// 15/1/2021 15:30:41


package rs.ac.bg.etf.pp1.ast;

public class BoolConst extends ConstIdentifier {

    private String boolName;
    private Assignop Assignop;
    private Boolean boolValue;

    public BoolConst (String boolName, Assignop Assignop, Boolean boolValue) {
        this.boolName=boolName;
        this.Assignop=Assignop;
        if(Assignop!=null) Assignop.setParent(this);
        this.boolValue=boolValue;
    }

    public String getBoolName() {
        return boolName;
    }

    public void setBoolName(String boolName) {
        this.boolName=boolName;
    }

    public Assignop getAssignop() {
        return Assignop;
    }

    public void setAssignop(Assignop Assignop) {
        this.Assignop=Assignop;
    }

    public Boolean getBoolValue() {
        return boolValue;
    }

    public void setBoolValue(Boolean boolValue) {
        this.boolValue=boolValue;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Assignop!=null) Assignop.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Assignop!=null) Assignop.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Assignop!=null) Assignop.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("BoolConst(\n");

        buffer.append(" "+tab+boolName);
        buffer.append("\n");

        if(Assignop!=null)
            buffer.append(Assignop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+boolValue);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [BoolConst]");
        return buffer.toString();
    }
}
