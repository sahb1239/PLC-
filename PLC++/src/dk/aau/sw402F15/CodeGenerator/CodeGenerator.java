package dk.aau.sw402F15.CodeGenerator;

import dk.aau.sw402F15.Symboltable.Scope;
import dk.aau.sw402F15.Symboltable.Symbol;
import dk.aau.sw402F15.Symboltable.Type.SymbolType;
import dk.aau.sw402F15.parser.analysis.DepthFirstAdapter;
import dk.aau.sw402F15.parser.node.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Claus & Jimmi on 24-04-2015.
 */
public class CodeGenerator extends DepthFirstAdapter {
    int jumpLabel = 0;
    int returnlabel;
    PrintWriter instructionWriter;
    PrintWriter symbolWriter;
    Scope scope;
    public CodeGenerator(Scope scope) {
        try {
            instructionWriter = new PrintWriter("InstructionList.txt", "UTF-8");
            symbolWriter = new PrintWriter("SymbolList.txt", "UTF-8");
            Emit("LD P_First_Cycle", true);
            Emit("SSET(630) W0 &5", true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.scope = scope;
    }

    @Override
    public void outStart(Start node){
        instructionWriter.close();
        symbolWriter.close();
    }

    @Override
    public void outAAssignmentExpr(AAssignmentExpr node) {
        super.outAAssignmentExpr(node);
        // Get location of symbol in memory
        // Set memory to value of TOS
    }

    @Override
    public void caseAArrayDefinition(AArrayDefinition node){
        //throw new NotImplementedException();
    }

    @Override
    public void caseAArrayExpr(AArrayExpr node){
        //throw new NotImplementedException();
    }

    @Override
    public void caseADeclaration(ADeclaration node){
        super.caseADeclaration(node);
    }

    @Override
    public void outABreakStatement(ABreakStatement node){
        super.outABreakStatement(node);
        Emit("BREAK(514)", true);
    }

    @Override
    public void outACaseStatement(ACaseStatement node){
        //throw new NotImplementedException();
    }

    @Override
    public void outACompareAndExpr(ACompareAndExpr node){
        super.outACompareAndExpr(node);
        PopFromStack();
        Emit("LD b1", true);
        Emit("AND b2", true);
        Emit("SET b1", true);
    }

    @Override
    public void outACompareEqualExpr(ACompareEqualExpr node){
        super.outACompareEqualExpr(node);

        PopFromStack();
        Emit("=(300) r1 r2", true);
    }

    @Override
    public void outACompareGreaterExpr(ACompareGreaterExpr node){
        super.outACompareGreaterExpr(node);

        PopFromStack();
        Emit(">(320) r1 r2", true);
    }

    @Override
    public void outACompareGreaterOrEqualExpr(ACompareGreaterOrEqualExpr node){
        super.outACompareGreaterOrEqualExpr(node);

        PopFromStack();
        Emit(">=(325) r1 r2", true);
    }

    @Override
    public void outACompareLessExpr(ACompareLessExpr node){
        super.outACompareLessExpr(node);

        PopFromStack();
        Emit("<(310) r1 r2", true);
    }

    @Override
    public void outACompareLessOrEqualExpr(ACompareLessOrEqualExpr node){
        super.outACompareLessOrEqualExpr(node);

        PopFromStack();
        Emit("<=(315) r1 r2", true);
    }

    @Override
    public void outACompareNotEqualExpr(ACompareNotEqualExpr node){
        super.outACompareNotEqualExpr(node);

        PopFromStack();
        Emit("<>(305) r1 r2", true);
    }

    @Override
    public void outACompareOrExpr(ACompareOrExpr node){
        super.outACompareOrExpr(node);
        PopFromStack();
        Emit("LD b1", true);
        Emit("OR b2", true);
        Emit("SET b1", true);
    }

    @Override
    public void outAContinueStatement(AContinueStatement node){
        //throw new NotImplementedException();
    }

    @Override
    public void outADeclaration(ADeclaration node){
        //throw new NotImplementedException();
        Symbol symbol = scope.getSymbol(node.getName().getText());

        if (symbol.getType() == SymbolType.Boolean()){
            //Emit(symbol.getName() + " BOOL W0", false);
        } else if (symbol.getType() == SymbolType.Int()){

        } else if (symbol.getType() == SymbolType.Char()){

        } else if (symbol.getType() == SymbolType.Decimal()){

        } else if (symbol.getType() == SymbolType.Timer()){

        } else if (symbol.getType() == SymbolType.Array()){

        } else if (symbol.getType() == SymbolType.Method()){

        } else if (symbol.getType() == SymbolType.Function("tmp")){

        } else if (symbol.getType() == SymbolType.Struct("tmp")){

        } else {
            throw new NotImplementedException();
        }

        //throw new NotImplementedException();
    }

    @Override
    public void outADefaultStatement(ADefaultStatement node){
        //throw new NotImplementedException();
    }

    @Override
    public void outAFalseExpr(AFalseExpr node){
        super.outAFalseExpr(node);
        Emit("LD P_Off", true);
    }

    @Override
    public void outAFunctionCallExpr(AFunctionCallExpr node){
        //throw new NotImplementedException();
    }

    @Override
    public void inAFunctionRootDeclaration(AFunctionRootDeclaration node){
       super.inAFunctionRootDeclaration(node);
        returnlabel = getNextJump();
    }

    @Override
    public void outAFunctionRootDeclaration(AFunctionRootDeclaration node) {
        super.outAFunctionRootDeclaration(node);
        Emit("JME(005) #" + returnlabel, true);
        Emit("RET(093)", true);
    }

    @Override
    public void outAIdentifierExpr(AIdentifierExpr node){
        //throw new NotImplementedException();
    }

    @Override
    public void outAMemberExpr(AMemberExpr node){
        //throw new NotImplementedException();
    }

    @Override
    public void outANegationExpr(ANegationExpr node){
        super.outANegationExpr(node);

        PopFromStack();
        Emit("NOT r1", true);

    }

    @Override
    public void outAPortAnalogInputExpr(APortAnalogInputExpr node){
        //throw new NotImplementedException();
    }

    @Override
    public void outAPortAnalogOutputExpr(APortAnalogOutputExpr node){
        //throw new NotImplementedException();
    }

    @Override
    public void outAPortInputExpr(APortInputExpr node){
        //throw new NotImplementedException();
    }

    @Override
    public void outAPortMemoryExpr(APortMemoryExpr node){
        //throw new NotImplementedException();
    }

    @Override
    public void outAPortOutputExpr(APortOutputExpr node){
        //throw new NotImplementedException();
    }

    @Override
    public void outAReturnStatement(AReturnStatement node){
        super.outAReturnStatement(node);
        Emit("JMP(004) #" + returnlabel, true);
    }

    @Override
    public void outASwitchStatement(ASwitchStatement node){
        //throw new NotImplementedException();
    }

    @Override
    public void outATrueExpr(ATrueExpr node){
        super.outATrueExpr(node);
        Emit("LD P_On", true);
    }

    @Override
    public void outATypeCastExpr(ATypeCastExpr node){
        //throw new NotImplementedException();
    }

    @Override
    public void caseABranchStatement(ABranchStatement node) {
        super.caseABranchStatement(node);

        if (node.getRight() != null) {
            // If - else statement
            int ifLabel = getNextJump();
            int elseLabel = getNextJump();

            node.getCondition().apply(this);
            Emit("CJP(510) #" + ifLabel, true);
            node.getRight().apply(this);
            Emit("JMP(004) #" + elseLabel, true);
            Emit("JME(005) #" + ifLabel, true);
            node.getLeft().apply(this);
            Emit("JME(005) #" + elseLabel, true);
        }
        else {
            // If statement
            int label = getNextJump();

            node.getCondition().apply(this);
            Emit("CJPN(511) #" + label, true);
            node.getLeft().apply(this);
            Emit("JME(005) #" + label, true);
        }
    }

    @Override
    public void caseAForStatement(AForStatement node){
        // Not needed since we convert For-loops til While-loops
    }

    @Override
    public void caseASwitchStatement(ASwitchStatement node){
        //throw new NotImplementedException();
    }

    @Override
    public void caseAWhileStatement(AWhileStatement node){
        Emit("LD b1", true);
        int jumpLabel = getNextJump();
        int loopLabel = getNextJump();

        Emit("JMP(004) #" + jumpLabel, true);
        Emit("JME(005) #" + loopLabel, true);
        node.getStatement().apply(this);
        Emit("JME(005) #" + jumpLabel, true);
        node.getCondition().apply(this);
        Emit("CJP(510) #" + loopLabel, true);
    }

    @Override
    public void outAIntegerExpr(AIntegerExpr node) {
        super.outAIntegerExpr(node);

        Emit("PUSH(632) W0 #" + node.getIntegerLiteral().getText(), true);
    }

    @Override
    public void outADecimalExpr(ADecimalExpr node) {
        super.outADecimalExpr(node);

        Emit("PUSH(632) W0 #" + node.getDecimalLiteral().getText(), true);
    }

    @Override
    public void outAAddExpr(AAddExpr node) {
        super.outAAddExpr(node);

        PopFromStack();
        Emit("+(400) r1 r2 r1", true);
        Emit("PUSH(632) W0 r1", true);
    }

    @Override
    public void outADivExpr(ADivExpr node) {
        super.outADivExpr(node);

        PopFromStack();
        Emit("/(430) r1 r2 r1", true);
        Emit("PUSH(632) W0 r1", true);
    }

    @Override
    public void outAMultiExpr(AMultiExpr node) {
        super.outAMultiExpr(node);
        PopFromStack();
        Emit("*(420) r1 r2 r1", true);
        Emit("PUSH(632) W0 r1", true);
    }

    @Override
    public void outASubExpr(ASubExpr node) {
        super.outASubExpr(node);

        PopFromStack();
        Emit("-(410) r1 r2 r1", true);
        Emit("PUSH(632) W0 r1", true);
    }

    private void PopFromStack() {
        Emit("r1\tINT\tW4\t\t0", false);
        Emit("r2\tINT\tW5\t\t0", false);
        Emit("LIFO(634) W0 r1", true);
        Emit("LIFO(634) W0 r2", true);
    }

    private int getNextJump(){
        jumpLabel = jumpLabel + 1;
        if(jumpLabel > 255)
            throw new IndexOutOfBoundsException();
        return jumpLabel;
    }

    protected void Emit(String s, boolean inst){
        if (inst == true) {
            instructionWriter.println(s);
        } else {
            symbolWriter.println(s);
        }
    }
}
