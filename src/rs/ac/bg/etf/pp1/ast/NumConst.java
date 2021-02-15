// generated with ast extension for cup
// version 0.8
// 15/1/2021 15:30:41


package rs.ac.bg.etf.pp1.ast;

public class NumConst extends ConstIdentifier {

    private String intName;
    private Assignop Assignop;
    private Integer intValue;

    public NumConst (String intName, Assignop Assignop, Integer intValue) {
        this.intName=intName;
        this.Assignop=Assignop;
        if(Assignop!=null) Assignop.setParent(this);
        this.intValue=intValue;
    }

    public String getIntName() {
        return intName;
    }

    public void setIntName(String intName) {
        this.intName=intName;
    }

    public Assignop getAssignop() {
        return Assignop;
    }

    public void setAssignop(Assignop Assignop) {
        this.Assignop=Assignop;
    }

    public Integer getIntValue() {
        return intValue;
    }

    public void setIntValue(Integer intValue) {
        this.intValue=intValue;
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
        buffer.append("NumConst(\n");

        buffer.append(" "+tab+intName);
        buffer.append("\n");

        if(Assignop!=null)
            buffer.append(Assignop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+intValue);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NumConst]");
        return buffer.toString();
    }
}
