package dk.aau.sw402F15.Preprocessor;

import dk.aau.sw402F15.Exception.CompilerInternalNodeException;
import dk.aau.sw402F15.SymbolProcessor.DeclarationSymbolProcessor;
import dk.aau.sw402F15.SymbolProcessor.FunctionPreprocessor;
import dk.aau.sw402F15.SymbolProcessor.StructPreprocessor;
import dk.aau.sw402F15.Symboltable.Scope;
import dk.aau.sw402F15.parser.analysis.DepthFirstAdapter;
import dk.aau.sw402F15.parser.node.*;

/**
 * Created by sahb on 22/04/15.
 */
public class Preprocessor extends DepthFirstAdapter {
    private Scope currentScope;

    public Preprocessor() {
        this(new Scope(null, null));
    }

    public Preprocessor(Scope currentScope) {
        this.currentScope = currentScope;
    }

    public Scope getScope() {
        return currentScope;
    }

    // Handle function declaration in seperate preprocessor
    @Override
    public void caseAFunctionRootDeclaration(AFunctionRootDeclaration node) {
        FunctionPreprocessor functionPreprocessor = new FunctionPreprocessor(currentScope.addSubScope(node));
        node.apply(functionPreprocessor);
    }

    // Struct declaration
    @Override
    public void caseAStructRootDeclaration(AStructRootDeclaration node) {
        StructPreprocessor structPreprocessor = new StructPreprocessor(currentScope.addSubScope(node));
        node.apply(structPreprocessor);
    }

    // Enum declaration
    @Override
    public void caseAEnumRootDeclaration(AEnumRootDeclaration node) {
        throw new CompilerInternalNodeException("Enum is not implemented", node);
    }

    // RootDeclaration
    @Override
    public void caseADeclarationRootDeclaration(ADeclarationRootDeclaration node) {
        DeclarationSymbolProcessor declarationPreprocessor = new DeclarationSymbolProcessor(currentScope);
        node.apply(declarationPreprocessor);
    }

    // Scope builder
    @Override
    public void inAScopeStatement(AScopeStatement node) {
        super.inAScopeStatement(node);
        currentScope = currentScope.addSubScope(node);
    }

    @Override
    public void outAScopeStatement(AScopeStatement node) {
        super.outAScopeStatement(node);
        currentScope = currentScope.getParentScope();
    }
}
