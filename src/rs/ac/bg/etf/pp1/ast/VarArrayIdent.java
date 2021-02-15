// generated with ast extension for cup
// version 0.8
// 15/1/2021 15:30:41


package rs.ac.bg.etf.pp1.ast;

public class VarArrayIdent extends VarIdentifier {

    private String arrayName;

    public VarArrayIdent (String arrayName) {
        this.arrayName=arrayName;
    }

    public String getArrayName() {
        return arrayName;
    }

    public void setArrayName(String arrayName) {
        this.arrayName=arrayName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarArrayIdent(\n");

        buffer.append(" "+tab+arrayName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarArrayIdent]");
        return buffer.toString();
    }
}
