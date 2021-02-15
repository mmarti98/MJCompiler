package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;

public class Compiler {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}

	public static void main(String[] args) throws Exception {

		Logger log = Logger.getLogger(Compiler.class);

		Reader br = null;
		try {

			File sourceCode = null;
			if (args.length < 2) {
				log.error("Nema dovoljno argumenata. Potrebni: <source-file> <obj-file> ");
				sourceCode = new File("test/test302.mj");
			} else {
				sourceCode = new File("test/" + args[0]);
			}

			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);

			MJParser p = new MJParser(lexer);
			Symbol s = p.parse();
			if (!p.errorDetected) {
				Program prog = (Program) (s.value);

				Tab.init();
				log.info(prog.toString(""));
				log.info("================SEMANTICKA ANALIZA===================");

				SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
				prog.traverseBottomUp(semanticAnalyzer);

				log.info("Print count calls = " + semanticAnalyzer.printCallCount);
				log.info(" Deklarisanih promenljivih ima = " + semanticAnalyzer.varDeclCount);

				tsdump();

				if (semanticAnalyzer.passed() && !p.errorDetected) {
					File objFile;
					if (args.length < 2) {
						objFile = new File("test/program.obj");
					} else {
						objFile = new File("test/" + args[1]);
					}

					if (objFile.exists())
						objFile.delete();
					CodeGenerator codeGenerator = new CodeGenerator();
					prog.traverseBottomUp(codeGenerator);
					Code.dataSize = semanticAnalyzer.nVars;
					Code.mainPc = codeGenerator.get_mainPC();
					Code.write(new FileOutputStream(objFile));
					log.info("Generisanje koda uspjesno zavrseno");

				} else {
					log.error("Semanticka analiza nije uspjesno zavrseno");
				}
			} else {
				log.error("Parsiranje nije uspjesno zavrseno");
			}

		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e1) {
					log.error(e1.getMessage(), e1);
				}
		}

	}

	public static void tsdump() {
		CustomDumpSymbolTableVisitor myVisitor = new CustomDumpSymbolTableVisitor();
		Tab.dump(myVisitor);
	}

}
