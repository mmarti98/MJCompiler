// generated with ast extension for cup
// version 0.8
// 15/1/2021 15:30:41


package rs.ac.bg.etf.pp1.ast;

public class VarElem extends VarList {

    private VarIdentifier VarIdentifier;

    public VarElem (VarIdentifier VarIdentifier) {
        this.VarIdentifier=VarIdentifier;
        if(VarIdentifier!=null) VarIdentifier.setParent(this);
    }

    public VarIdentifier getVarIdentifier() {
        return VarIdentifier;
    }

    public void setVarIdentifier(VarIdentifier VarIdentifier) {
        this.VarIdentifier=VarIdentifier;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarIdentifier!=null) VarIdentifier.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarIdentifier!=null) VarIdentifier.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarIdentifier!=null) VarIdentifier.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarElem(\n");

        if(VarIdentifier!=null)
            buffer.append(VarIdentifier.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarElem]");
        return buffer.toString();
    }
}
