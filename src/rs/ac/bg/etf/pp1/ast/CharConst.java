// generated with ast extension for cup
// version 0.8
// 15/1/2021 15:30:41


package rs.ac.bg.etf.pp1.ast;

public class CharConst extends ConstIdentifier {

    private String charName;
    private Assignop Assignop;
    private Character charValue;

    public CharConst (String charName, Assignop Assignop, Character charValue) {
        this.charName=charName;
        this.Assignop=Assignop;
        if(Assignop!=null) Assignop.setParent(this);
        this.charValue=charValue;
    }

    public String getCharName() {
        return charName;
    }

    public void setCharName(String charName) {
        this.charName=charName;
    }

    public Assignop getAssignop() {
        return Assignop;
    }

    public void setAssignop(Assignop Assignop) {
        this.Assignop=Assignop;
    }

    public Character getCharValue() {
        return charValue;
    }

    public void setCharValue(Character charValue) {
        this.charValue=charValue;
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
        buffer.append("CharConst(\n");

        buffer.append(" "+tab+charName);
        buffer.append("\n");

        if(Assignop!=null)
            buffer.append(Assignop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+charValue);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CharConst]");
        return buffer.toString();
    }
}
