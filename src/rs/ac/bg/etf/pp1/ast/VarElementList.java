// generated with ast extension for cup
// version 0.8
// 15/1/2021 15:30:41


package rs.ac.bg.etf.pp1.ast;

public class VarElementList extends VarList {

    private VarList VarList;
    private VarIdentifier VarIdentifier;

    public VarElementList (VarList VarList, VarIdentifier VarIdentifier) {
        this.VarList=VarList;
        if(VarList!=null) VarList.setParent(this);
        this.VarIdentifier=VarIdentifier;
        if(VarIdentifier!=null) VarIdentifier.setParent(this);
    }

    public VarList getVarList() {
        return VarList;
    }

    public void setVarList(VarList VarList) {
        this.VarList=VarList;
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
        if(VarList!=null) VarList.accept(visitor);
        if(VarIdentifier!=null) VarIdentifier.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarList!=null) VarList.traverseTopDown(visitor);
        if(VarIdentifier!=null) VarIdentifier.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarList!=null) VarList.traverseBottomUp(visitor);
        if(VarIdentifier!=null) VarIdentifier.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarElementList(\n");

        if(VarList!=null)
            buffer.append(VarList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarIdentifier!=null)
            buffer.append(VarIdentifier.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarElementList]");
        return buffer.toString();
    }
}
