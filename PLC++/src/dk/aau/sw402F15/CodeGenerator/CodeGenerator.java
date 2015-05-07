package dk.aau.sw402F15.CodeGenerator;

import dk.aau.sw402F15.Symboltable.Scope;
import dk.aau.sw402F15.Symboltable.ScopeDepthFirstAdapter;
import dk.aau.sw402F15.Symboltable.Symbol;
import dk.aau.sw402F15.Symboltable.Type.SymbolType;
import dk.aau.sw402F15.parser.node.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by Claus & Jimmi on 24-04-2015.
 */
public class CodeGenerator extends ScopeDepthFirstAdapter {
    private int jumpLabel = 0;
    private int returnlabel;
    private int nextDAddress = -4;
    private double nextWAddress = 0.00;
    private int startFunctionNumber = -1;

    PrintWriter instructionWriter;
    PrintWriter symbolWriter;

    public int getNextDAddress(boolean increment) {
        if (nextDAddress > 32763)
            throw new OutOfMemoryError();

        if (increment)
            return nextDAddress += 4;
        else
            return nextDAddress;
    }

    public double getNextWAddress(boolean increment) {
        if (nextWAddress > 508)
            throw new OutOfMemoryError();

        if (increment)
            return nextWAddress += 0.1;
        else
            return nextWAddress;
    }

    public int getFunctionNumber() {
        return startFunctionNumber += 1;
    }

    public CodeGenerator(Scope scope) {
        super(scope, scope);

        try {
            instructionWriter = new PrintWriter("InstructionList.txt", "UTF-8");
            symbolWriter = new PrintWriter("SymbolList.txt", "UTF-8");
            Emit("LD P_First_Cycle", true);
            Emit("SBS(091) 0", true);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void outStart(Start node){
        Emit("END(001)", true);

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

        Emit("ANDW(034) D" + (getNextDAddress(false) - 4) + " D" + getNextDAddress(false) + " D" + getNextDAddress(true), true);
    }
    
    @Override
    public void outACompareOrExpr(ACompareOrExpr node){
        super.outACompareOrExpr(node);

        Emit("ORW(035) D" + (getNextDAddress(false) - 4) + " D" + getNextDAddress(false) + " D" + getNextDAddress(true), true);
    }

    @Override
    public void outACompareEqualExpr(ACompareEqualExpr node){
        super.outACompareEqualExpr(node);

        Emit("AND=(300) D" + (getNextDAddress(false) - 4) + " D" + getNextDAddress(false), true);
        Emit("SET W" + getNextWAddress(true), true);
    }

    @Override
    public void outACompareGreaterExpr(ACompareGreaterExpr node){
        super.outACompareGreaterExpr(node);

        Emit("AND>(320) D" + (getNextDAddress(false) - 4) + " D" + getNextDAddress(false), true);
        Emit("SET W" + getNextWAddress(true), true);

    }

    @Override
    public void outACompareGreaterOrEqualExpr(ACompareGreaterOrEqualExpr node){
        super.outACompareGreaterOrEqualExpr(node);

        Emit("AND>=(325) D" + (getNextDAddress(false) - 4) + " D" + getNextDAddress(false), true);
        Emit("SET W" + getNextWAddress(true), true);
    }

    @Override
    public void outACompareLessExpr(ACompareLessExpr node){
        super.outACompareLessExpr(node);

        Emit("AND<(310) D" + (getNextDAddress(false) - 4) + " D" + getNextDAddress(false), true);
        Emit("SET W" + getNextWAddress(true), true);
    }

    @Override
    public void outACompareLessOrEqualExpr(ACompareLessOrEqualExpr node){
        super.outACompareLessOrEqualExpr(node);

        Emit("AND<=(315) D" + (getNextDAddress(false) - 4) + " D" + getNextDAddress(false), true);
        Emit("SET W" + getNextWAddress(true), true);
    }

    @Override
    public void outACompareNotEqualExpr(ACompareNotEqualExpr node){
        super.outACompareNotEqualExpr(node);

        Emit("AND<>(305) D" + (getNextDAddress(false) - 4) + " D" + getNextDAddress(false), true);
        Emit("SET W" + getNextWAddress(true), true);
    }

    @Override
    public void outAContinueStatement(AContinueStatement node){
        //throw new NotImplementedException();
    }

    @Override
    public void outADeclaration(ADeclaration node){
        Symbol symbol = currentScope.getSymbolOrThrow(node.getName().getText(), node);

        if (symbol.getType().getType() == SymbolType.Type.Boolean){

        } else if (symbol.getType().getType() == SymbolType.Type.Int){

        } else if (symbol.getType().getType() == SymbolType.Type.Char){

        } else if (symbol.getType().getType() == SymbolType.Type.Decimal){

        } else if (symbol.getType().getType() == SymbolType.Type.Timer){

        } else if (symbol.getType().getType() == SymbolType.Type.Array){

        } else if (symbol.getType().getType() == SymbolType.Type.Method){ // Method is a void function

        } else if (symbol.getType().getType() == SymbolType.Type.Function){

        } else if (symbol.getType().getType() == SymbolType.Type.Struct){

        } else {
            // throw new RuntimeException(); // TODO Need new Exception. Pretty unknown error though
        }

    }

    @Override
    public void outADefaultStatement(ADefaultStatement node){
        //throw new NotImplementedException();
    }

    @Override
    public void outAFunctionCallExpr(AFunctionCallExpr node){
        //throw new NotImplementedException();
    }

    @Override
    public void inAFunctionRootDeclaration(AFunctionRootDeclaration node){
        super.inAFunctionRootDeclaration(node);
        Emit("MCRO(099) " + getFunctionNumber() + " D" + getNextDAddress(true) + " D" + getNextDAddress(true), true);
        returnlabel = getNextJump();
    }

    @Override
    public void outAFunctionRootDeclaration(AFunctionRootDeclaration node) {
        super.outAFunctionRootDeclaration(node);
        //Emit("JME(005) #" + returnlabel, true);
        //Emit("RET(093)", true);
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

        Emit("NOT D" + getNextDAddress(false), true);
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

        Emit("MOV(021) #1 D" + getNextDAddress(true), true);
    }

    @Override
    public void outAFalseExpr(AFalseExpr node){
        super.outAFalseExpr(node);

        Emit("MOV(021) #0 D" + getNextDAddress(true), true);
    }

    @Override
    public void outATypeCastExpr(ATypeCastExpr node){
        //throw new NotImplementedException();
    }

    @Override
    public void caseABranchStatement(ABranchStatement node) {
        //Do not call super as this function handles calls of the child classes

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

        Emit("MOV(021) &" + node.getIntegerLiteral() + " D" + getNextDAddress(true), true);
    }

    @Override
    public void outADecimalExpr(ADecimalExpr node) {
        super.outADecimalExpr(node);

        Emit("+F(454) +0,0 +" + node.getDecimalLiteral().toString().replace(".", ",") + "D" + getNextDAddress(true) + "", true);
    }

    @Override
    public void outAAddExpr(AAddExpr node) {
        super.outAAddExpr(node);

        // TODO Different if float
        Emit("+(400) D" + getNextDAddress(false) + " D" + (getNextDAddress(false) - 4) + " D" + getNextDAddress(true), true);
    }

    @Override
    public void outADivExpr(ADivExpr node) {
        super.outADivExpr(node);

        // TODO Different if float

        Emit("/(430) D" + getNextDAddress(false) + " D" + (getNextDAddress(false) - 4) + " D" + getNextDAddress(true), true);
    }

    @Override
    public void outAMultiExpr(AMultiExpr node) {
        super.outAMultiExpr(node);

        // TODO Different if float

        Emit("*(420) D" + getNextDAddress(false) + " D" + (getNextDAddress(false) - 4) + " D" + getNextDAddress(true), true);
    }

    @Override
    public void outASubExpr(ASubExpr node) {
        super.outASubExpr(node);

        // TODO Different if float

        Emit("-(410) D" + getNextDAddress(false) + " D" + (getNextDAddress(false) - 4) + " D" + getNextDAddress(true), true);
    }

    private int getNextJump(){
        jumpLabel = jumpLabel + 1;
        if(jumpLabel > 255)
            throw new IndexOutOfBoundsException();
        return jumpLabel;
    }

    protected void Emit(String s, boolean instruction){
        if (instruction == true) { // Write to InstructionList, if instruction
            instructionWriter.println(s);
        } else { // Otherwise it's a symbol, then write to SymbolList
            symbolWriter.println(s);
        }
    }
}
