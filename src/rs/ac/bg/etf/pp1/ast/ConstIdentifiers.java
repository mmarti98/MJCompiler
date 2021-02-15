// generated with ast extension for cup
// version 0.8
// 15/1/2021 15:30:41


package rs.ac.bg.etf.pp1.ast;

public class ConstIdentifiers extends ConstIdentifierList {

    private ConstIdentifierList ConstIdentifierList;
    private ConstIdentifier ConstIdentifier;

    public ConstIdentifiers (ConstIdentifierList ConstIdentifierList, ConstIdentifier ConstIdentifier) {
        this.ConstIdentifierList=ConstIdentifierList;
        if(ConstIdentifierList!=null) ConstIdentifierList.setParent(this);
        this.ConstIdentifier=ConstIdentifier;
        if(ConstIdentifier!=null) ConstIdentifier.setParent(this);
    }

    public ConstIdentifierList getConstIdentifierList() {
        return ConstIdentifierList;
    }

    public void setConstIdentifierList(ConstIdentifierList ConstIdentifierList) {
        this.ConstIdentifierList=ConstIdentifierList;
    }

    public ConstIdentifier getConstIdentifier() {
        return ConstIdentifier;
    }

    public void setConstIdentifier(ConstIdentifier ConstIdentifier) {
        this.ConstIdentifier=ConstIdentifier;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstIdentifierList!=null) ConstIdentifierList.accept(visitor);
        if(ConstIdentifier!=null) ConstIdentifier.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstIdentifierList!=null) ConstIdentifierList.traverseTopDown(visitor);
        if(ConstIdentifier!=null) ConstIdentifier.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstIdentifierList!=null) ConstIdentifierList.traverseBottomUp(visitor);
        if(ConstIdentifier!=null) ConstIdentifier.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstIdentifiers(\n");

        if(ConstIdentifierList!=null)
            buffer.append(ConstIdentifierList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstIdentifier!=null)
            buffer.append(ConstIdentifier.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstIdentifiers]");
        return buffer.toString();
    }
}
