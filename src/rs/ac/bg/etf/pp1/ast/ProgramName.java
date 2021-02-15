// generated with ast extension for cup
// version 0.8
// 15/1/2021 15:30:41


package rs.ac.bg.etf.pp1.ast;

public class ProgramName extends ProgName {

    private String programName;

    public ProgramName (String programName) {
        this.programName=programName;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName=programName;
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
        buffer.append("ProgramName(\n");

        buffer.append(" "+tab+programName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ProgramName]");
        return buffer.toString();
    }
}
