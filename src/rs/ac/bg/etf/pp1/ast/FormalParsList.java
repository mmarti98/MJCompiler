// generated with ast extension for cup
// version 0.8
// 15/1/2021 15:30:41


package rs.ac.bg.etf.pp1.ast;

public class FormalParsList extends FormPars {

    private FormPars FormPars;
    private ParameterIdent ParameterIdent;

    public FormalParsList (FormPars FormPars, ParameterIdent ParameterIdent) {
        this.FormPars=FormPars;
        if(FormPars!=null) FormPars.setParent(this);
        this.ParameterIdent=ParameterIdent;
        if(ParameterIdent!=null) ParameterIdent.setParent(this);
    }

    public FormPars getFormPars() {
        return FormPars;
    }

    public void setFormPars(FormPars FormPars) {
        this.FormPars=FormPars;
    }

    public ParameterIdent getParameterIdent() {
        return ParameterIdent;
    }

    public void setParameterIdent(ParameterIdent ParameterIdent) {
        this.ParameterIdent=ParameterIdent;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormPars!=null) FormPars.accept(visitor);
        if(ParameterIdent!=null) ParameterIdent.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormPars!=null) FormPars.traverseTopDown(visitor);
        if(ParameterIdent!=null) ParameterIdent.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormPars!=null) FormPars.traverseBottomUp(visitor);
        if(ParameterIdent!=null) ParameterIdent.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormalParsList(\n");

        if(FormPars!=null)
            buffer.append(FormPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ParameterIdent!=null)
            buffer.append(ParameterIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormalParsList]");
        return buffer.toString();
    }
}
