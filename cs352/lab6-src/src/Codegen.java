package simplec;

import java.math.BigInteger;
import java.util.*;

import simplec.parse.*;
import static simplec.AST.*;
import static simplec.Reg.*;
import static simplec.Assem.*;

public class Codegen {
  public static int MAX_LOCALS = 64;
  public static int nlocals = 0;
  private Map<String, Integer> localVarMap = 
    new LinkedHashMap<String, Integer>();
  private Map<String, Integer> localVarType = 
    new LinkedHashMap<String, Integer>();

  private int MAX_GLOBALS = 100;
  private int nglobals = 0;
  private Map<String, Integer> globalVarMap = 
    new LinkedHashMap<String, Integer>();
  private Map<String, Integer> globalVarType =
    new LinkedHashMap<String, Integer>();

  private Map<String, Label> stringMap;
  private Map<Double, Label> doubleMap;
  private Map<Label, String> labelMap;

  private List<String> funcList;

  public static LinkedList<ASM> instrs = new LinkedList<ASM>();

  public static int countExtraReg = 0;

  public static class RegStack {
    public static int top = 0;

    public static Reg[] regStk = { RBX, R10, R13, R14, R15 };
    public static int size = regStk.length;
    public static String pop() {
      if (top > size) {
        // TODO implement spilling
        // Otherwise this will just crash
        // if you need more than 5 registers
        instrs.add(COMMENT("Spilling for pop"));

        countExtraReg--;
        String reg = OFFSET(RSP, 8*(MAX_LOCALS - nlocals - countExtraReg));
        top--;
        //countExtraReg--;
        return reg;
      }
      else
      {
        String reg = regStk[top - 1].toString();
        top--;
        return reg;
      }
    }

    public static String peek() {
      if (top > size) {
        // TODO implement spilling
        // Otherwise this will just crash
        // if you need more than 5 registers
        instrs.add(COMMENT("Spilling for peek"));

        String reg = OFFSET(RSP, 8*(MAX_LOCALS - nlocals - countExtraReg + 1));

        //instrs.add(COMMENT(Integer.toString(8*(MAX_LOCALS - nlocals - countExtraReg + 1))));
        return reg;
      }
      else
      {
          String reg = regStk[top - 1].toString();
          return reg;
      }
    }

    public static String push() {
      if (top >= size) {
        // TODO implement spilling
        // Otherwise this will just crash
        // if you need more than 5 registers
        //countExtraReg++;
        instrs.add(COMMENT("Start spilling for push"));
        top++;
        String reg = OFFSET(RSP, 8*(MAX_LOCALS - nlocals - countExtraReg));
        countExtraReg++;
        
        //instrs.add(COMMENT(Integer.toString(8*(MAX_LOCALS - nlocals - countExtraReg))));
        return reg;
      }
      else
      {
        String reg = regStk[top].toString();
        top++;
        return reg;
      }
    }
  }

  // In case you need to get the type of something
  public CType getType(Value v) {
    return Semant.getType(v);
  }

  public CType getType(Expression expr) {
    return Semant.getType(expr);
  }

  // Assume all of our types are 64-bit/8-byte
  // Except for chars, which are 1 byte
  public int sizeof(CType type) {
    if (type == CType.CHAR)
      return 1;
    else
      return 8;
  }

  public void addLocalVar(String id, CType type) {
    localVarMap.put(id, nlocals);
    localVarType.put(id, sizeof(type));
    nlocals++;
  }

  public int lookupLocalVar(String id) {
    if (localVarMap.containsKey(id))
      return localVarMap.get(id);
    return -1;
  }

  public void addGlobalVar(String id, CType type) {
    globalVarMap.put(id, nglobals);
    globalVarType.put(id, sizeof(type));
    nglobals++;
  }

  public int lookupGlobalVar(String id) {
    if (globalVarMap.containsKey(id))
      return globalVarMap.get(id);
    return -1;
  }

  public Codegen() {
    this.stringMap = new LinkedHashMap<String, Label>();
    this.doubleMap = new LinkedHashMap<Double, Label>();
    this.labelMap  = new LinkedHashMap<Label, String>();
    this.funcList  = new ArrayList<String>();
  }

  public static void main(String... args) {
    try {
      // Build AST
      Value.Unit goal = new SimpleC(System.in).goal();

      // Perform typechecking on our AST
      Semant.typeCheck(goal);

      // Print scopes
      //for (Scope s : Scope.allScopes)
      //System.err.println(s);

      // Run tree optimizer 150 times
      // Feel free to change this number to whatever
      for (int i = 0; i < 150; i++) {
        Optimize.optimize(goal);
      }

      // If no errors reported, run Codegen on our AST
      if (Error.nErrors() == 0) {
        Codegen codegen = new Codegen();
        codegen.codeGen(goal);
        System.out.println(codegen);
      } else {
        // Exit and don't generate code if we have errors
        System.exit(-1);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // Set this to true if you don't want comments in the output
  // It doesn't matter for testing either way
  public static boolean disableComments = false;
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (ASM asm : instrs) {
      String str = asm.toString().trim();
      if (disableComments && 
          str.length() > 0 && 
          str.charAt(0) != '#')
        sb.append(asm + "\n");
      else if (!disableComments)
        sb.append(asm + "\n");
    }
    sb.setLength(sb.length() - 1);
    return sb.toString();
  }

  void codeGen(Statement stmt) {
    if (stmt == null) return;
    stmt.accept(new Statement.Visitor<Void>() {
      public Void visit(Statement.CompoundStatement stmt) {
        for (Statement childStmt : stmt.stmtList) {
          codeGen(childStmt);
        }
        return null;
      }

      public Void visit(Statement.VariableDecls decls) {
        for (Value.Variable var : decls.vars) {
          codeGen(var);
        }
        return null;
      }

      public Void visit(Statement.AssignStatement stmt) {
        Value.VarAccess var = stmt.var;
        Expression expr = stmt.expression;

        String id = var.id.image;

        // If i is negative, then its a global variable
        // otherwise we have its index in our call stack
        int i = lookupLocalVar(id);

        if (stmt.index == null) { // Assigning to a variable
          codeGen(expr);
          if (i >= 0) {
            // TODO implement local variables
            instrs.add(COMMENT("Assign local variables"));
            //instrs.add(INSTR("movq", RegStack.pop(), (i * 8 + 40) + "(" + RSP + ")"));
            instrs.add(INSTR("movq", RegStack.pop(), OFFSET(RSP, 8*(MAX_LOCALS - i))));

          } else {
            instrs.add(COMMENT("assign to global var " + id));
            instrs.add(INSTR("movq", 
              RegStack.pop(), 
              id
            ));
          }
        } else { // Assigning to an array
          if (stmt.index != null)
            codeGen(stmt.index);
          if (i >= 0) {
            // TODO implement local array assigning

            instrs.add(COMMENT("Assign local array"));

            //get address
            instrs.add(INSTR("movq", OFFSET(RSP, 8*(MAX_LOCALS - i)), RegStack.push()));

            
          } else {
          // TODO implement global array assigning
          instrs.add(COMMENT("Assign global array"));

          //get address
          instrs.add(INSTR("movq", id, RegStack.push()));
           
          }

          //This line was comment out by myself
          //codeGen(expr);


          // deref char*/long* and get type of resulting type (char/long)
          int len = sizeof(getType(var).deref());
          // TODO implement array assigning

          //pop address
          RegStack.pop();
          //pop index
          //RegStack.pop();

          instrs.add(INSTR("imulq", CONST(len), RegStack.peek()));
          //push address
          RegStack.push();
          instrs.add(INSTR("addq", RegStack.pop(), RegStack.pop()));
          //push final address
          RegStack.push();

          codeGen(expr);

          if (RegStack.top <= RegStack.size)
            instrs.add(INSTR("movq", RegStack.pop(), "(" + RegStack.pop() + ")"));
          else
          {
            RegStack.pop();
            instrs.add(INSTR("movq", RegStack.peek(), RAX));
            RegStack.push();
            instrs.add(INSTR("movq", RegStack.pop(), "(" + RAX + ")"));
            RegStack.pop();
          }
        }

        return null;
      }

      public Void visit(Statement.ForStatement stmt) {

        codeGen(stmt.init);
        // TODO
        instrs.add(LABEL("start_" + stmt.label.name));

        codeGen(stmt.cond);
        // TODO
        instrs.add(COMMENT("for condition"));
        instrs.add(INSTR("cmpq", CONST(0), RegStack.pop()));
        instrs.add(INSTR("je", "end_" + stmt.label.name));

        /*codeGen(stmt.update);
        // TODO
*/
        codeGen(stmt.body);

        codeGen(stmt.x);
        //TODO

        instrs.add(INSTR("jmp", "start_" + stmt.label.name));
        instrs.add(LABEL("end_" + stmt.label.name));

        return null;
      }

      public Void visit(Statement.WhileStatement stmt) {
        instrs.add(LABEL("start_" + stmt.label.name));
        codeGen(stmt.cond);

        instrs.add(COMMENT("while condition"));
        instrs.add(INSTR("cmpq", CONST(0), RegStack.pop()));
        instrs.add(INSTR("je", "end_" + stmt.label.name));

        codeGen(stmt.body);
        instrs.add(INSTR("jmp", "start_" + stmt.label.name));
        instrs.add(LABEL("end_" + stmt.label.name));

        return null;
      }

      public Void visit(Statement.DoWhileStatement stmt) {
        instrs.add(LABEL("start_" + stmt.label.name));
        instrs.add(COMMENT("do_while condition"));

        codeGen(stmt.body);
        // TODO

        codeGen(stmt.cond);
        // TODO
        instrs.add(INSTR("cmpq", CONST(0), RegStack.pop()));
        instrs.add(INSTR("je", "end_" + stmt.label.name));

        instrs.add(INSTR("jmp", "start_" + stmt.label.name));
        instrs.add(LABEL("end_" + stmt.label.name));


        return null;
      }

      public Void visit(Statement.IfStatement stmt) {

        codeGen(stmt.cond);
        // TODO
        instrs.add(COMMENT("if condition"));
        instrs.add(INSTR("cmpq", CONST(0), RegStack.pop()));
        instrs.add(INSTR("je", "end_" + stmt.label.name));
        codeGen(stmt.body);
        // TODO
        instrs.add(INSTR("jmp", "end_else_" + stmt.label.name));

        instrs.add(LABEL("end_" + stmt.label.name));

        if (stmt.elseStmt != null) {
          codeGen(stmt.elseStmt);
        }

        instrs.add(LABEL("end_else_" + stmt.label.name));

        return null;
      }

      public Void visit(Statement.ElseStatement stmt) {
        codeGen(stmt.body);
        return null;
      }

      public Void visit(Statement.CallStatement stmt) {
        codeGen(stmt.callExpr);
        RegStack.top = 0;
        return null;
      }

      public Void visit(Statement.ContinueStatement stmt) {
        // This will get the loop (for/while/etc) that
        // this continue statement is a part of
        Statement loop = stmt.scope.getLoop();

        // TODO
        instrs.add(INSTR("jmp", "start_" + stmt.label.name));

        return null;
      }

      public Void visit(Statement.BreakStatement stmt) {
        // This will get the loop (for/while/etc) that
        // this break statement is a part of
        Statement loop = stmt.scope.getLoop();

        // TODO
        instrs.add(INSTR("jmp", "end_" + loop.label.name));

        return null;
      }

      public Void visit(Statement.ReturnStatement stmt) {
        // This will get the function that
        // this return statement is a part of
        // or null if there isn't one
        Value.Function func = stmt.scope.getFunc();


        if (stmt.retVal != null) {
          instrs.add(COMMENT("enter returnStatement"));

          //return value need to be fixed
          codeGen(stmt.retVal);
          // TODO set return value
          instrs.add(COMMENT("set return value"));
          instrs.add(INSTR("movq", RegStack.pop(), RAX));
          

        }

        // TODO jmp
        instrs.add(INSTR("jmp", "return_" + func.id.image));

        return null;
      }
    });
  }

  public Void codeGen(Value var) {
    if (var == null) return null;
    return var.accept(new Value.Visitor<Void>() {
      public Void visit(Value.Unit v) {
        for (Value fov : v.fovList) {
          codeGen(fov);
        }

        // Strings
        for (String stringConst : stringMap.keySet()) {
          Label label = stringMap.get(stringConst);
          instrs.add(label);
          instrs.add(STRING(stringConst));
        }

        // COMPILER DIRECTIVES

        // Functions
        if (funcList.size() > 0)
          instrs.add(DIRECTIVE("text"));
        for (String func : funcList) {
          instrs.add(DIRECTIVE("globl", func));
        }

        // Doubles
        if (doubleMap.size() > 0)
          instrs.add(DIRECTIVE("section .data"));
        for (Double d : doubleMap.keySet()) {
          Label label = doubleMap.get(d);
          String id = label.name;
          instrs.add(label);
          instrs.add(DIRECTIVE("double", d.toString()));
        }

        // Globals
        if (nglobals > 0)
          instrs.add(DIRECTIVE("bss"));
        for (String id : globalVarMap.keySet()) {
          int len = globalVarType.get(id);
          instrs.add(DIRECTIVE("globl", id));
          instrs.add(LABEL(id));
          instrs.add(DIRECTIVE("quad", "0"));
          instrs.add(DIRECTIVE("size", id, len + ""));
        }

        return null;
      }

      public Void visit(Value.Variable v) {
        if (v.scope == Scope.rootScope) {
          addGlobalVar(v.id.image, v.type.type);
          instrs.add(COMMENT("adding global var " + v.id.image + " of type " + v.type.type));
        } else {
          // TODO - Implement local variables
          instrs.add(COMMENT("adding local var " + v.id.image + " of type " + v.type.type));
          addLocalVar(v.id.image, v.type.type);
        }
        return null;
      }

      public Void visit(Value.Argument v) {
        // Saves this argument + its type so we can put it in the stack later
        addLocalVar(v.id.image, v.type.type);
        instrs.add(COMMENT("adding argument var " + v.id.image + " of type " + v.type.type));
        return null;
      }

      public Void visit(Value.Type v) {
        return null;
      }

      public Void visit(Value.VarAccess v) { 
        return null;
      }

      public Void visit(Value.Function v) {
        // Add this function name to our function list
        // This will be used to print .globl funcName
        // at the end of the file
        funcList.add(v.id.image);

        // Add our function label
        instrs.add(LABEL(v.id.image));

        // Push our virtual stack registers
        instrs.add(INSTR("pushq", RBX));
        instrs.add(INSTR("pushq", R10));
        instrs.add(INSTR("pushq", R13));
        instrs.add(INSTR("pushq", R14));
        instrs.add(INSTR("pushq", R15));
        instrs.add(INSTR("subq", CONST(512), RSP));

        RegStack.top = 0;

        nlocals = 0;

        // Save locals/arguments
        instrs.add(COMMENT("save argument"));
        List<Value.Argument> argList = v.argList;

        // Generate code for our arguments
        for (int i = 0; i < argList.size(); i++) {
          codeGen(v.argList.get(i));
        }

        // Move each argument into a local place on the stack
        for (int i = argList.size() - 1; i >= 0; i--) {
          instrs.add(INSTR("movq", 
                regArgs[i], 
                OFFSET(RSP, 8*(MAX_LOCALS - i))));
        }

        // Generate code for anything in our function compound stmt
        codeGen(v.cStmt);
        
        // TODO make return label
        //make return lanel
        instrs.add(LABEL("return_" + v.id.image));

        // Restore the stack, then the registers
        instrs.add(INSTR("addq", CONST(512), RSP));
        instrs.add(COMMENT("restore registers"));
        instrs.add(INSTR("popq", R15));
        instrs.add(INSTR("popq", R14));
        instrs.add(INSTR("popq", R13));
        instrs.add(INSTR("popq", R10));
        instrs.add(INSTR("popq", RBX));
        instrs.add(INSTR("retq"));

        return null;
      }

      public Void visit(Value.VariableList v) {
        for (Value.Variable var : v.vars) {
          codeGen(var);
        }
        return null;
      }
    });

  }

  public Void codeGen(Expression expr) {
    return expr.accept(new Expression.Visitor<Void>() {
      public Void visit(Expression.Or expr) {
        codeGen(expr.left);
        codeGen(expr.right);
        instrs.add(COMMENT(expr.id.image));
        // TODO
        instrs.add(COMMENT("Execute '&&' relation"));
        instrs.add(INSTR("or", RegStack.pop(),RegStack.pop()));
        RegStack.push();

        return null;
      }

      public Void visit(Expression.And expr) {
        codeGen(expr.left);
        codeGen(expr.right);
        instrs.add(COMMENT(expr.id.image));
        // TODO
        instrs.add(COMMENT("Execute '&&' relation"));
        instrs.add(INSTR("and", RegStack.pop(),RegStack.pop()));
        RegStack.push();

        return null;
      }

      public Void visit(Expression.Eq expr) {
        codeGen(expr.left);
        codeGen(expr.right);
        instrs.add(COMMENT(expr.id.image));
        // TODO
        if (expr.id.image.equals("=="))
        {
            instrs.add(COMMENT("Execute '==' relation"));
            instrs.add(INSTR("cmpq",RegStack.pop(),RegStack.pop()));
            RegStack.push();
            //change the initial flag to '0' first
            instrs.add(INSTR("movq",CONST(0),RegStack.pop()));
            RegStack.push();
            //change flag end
            RegStack.push();
            instrs.add(INSTR("movq",CONST(1),RegStack.pop()));
            RegStack.push();
            instrs.add(INSTR("cmove",RegStack.pop(),RegStack.pop()));
            RegStack.push();
        }
        else if (expr.id.image.equals("!="))
        {
            instrs.add(COMMENT("Execute '!=' relation"));
            instrs.add(INSTR("cmpq",RegStack.pop(),RegStack.pop()));
            RegStack.push();
            //change the initial flag to '0' first
            instrs.add(INSTR("movq",CONST(0),RegStack.pop()));
            RegStack.push();
            //change flag end
            RegStack.push();
            instrs.add(INSTR("movq",CONST(1),RegStack.pop()));
            RegStack.push();
            instrs.add(INSTR("cmovne",RegStack.pop(),RegStack.pop()));
            RegStack.push();
        }
        
        return null;
      }

      public Void visit(Expression.Rel expr) {
        codeGen(expr.left);
        codeGen(expr.right);
        instrs.add(COMMENT(expr.id.image));
        // TODO
        if (expr.id.image.equals(">"))
        {
            instrs.add(COMMENT("Execute '>' relation"));
            instrs.add(INSTR("cmpq",RegStack.pop(),RegStack.pop()));
            RegStack.push();
            //change the initial flag to '0' first
            instrs.add(INSTR("movq",CONST(0),RegStack.pop()));
            RegStack.push();
            //change flag end
            RegStack.push();
            instrs.add(INSTR("movq",CONST(1),RegStack.pop()));
            RegStack.push();
            instrs.add(INSTR("cmovg",RegStack.pop(),RegStack.pop()));
            RegStack.push();  
        }
        else if (expr.id.image.equals("<"))
        {
            instrs.add(COMMENT("Execute '<' relation"));
            instrs.add(INSTR("cmpq",RegStack.pop(),RegStack.pop()));
            RegStack.push();
            //change the initial flag to '0' first
            instrs.add(INSTR("movq",CONST(0),RegStack.pop()));
            RegStack.push();
            //change flag end
            RegStack.push();
            instrs.add(INSTR("movq",CONST(1),RegStack.pop()));
            RegStack.push();
            instrs.add(INSTR("cmovl",RegStack.pop(),RegStack.pop()));
            RegStack.push();
        }
        else if (expr.id.image.equals("<="))
        {
            instrs.add(COMMENT("Execute '<=' relation"));
            instrs.add(INSTR("cmpq",RegStack.pop(),RegStack.pop()));
            RegStack.push();
            //change the initial flag to '0' first
            instrs.add(INSTR("movq",CONST(0),RegStack.pop()));
            RegStack.push();
            //change flag end
            RegStack.push();
            instrs.add(INSTR("movq",CONST(1),RegStack.pop()));
            RegStack.push();
            instrs.add(INSTR("cmovle",RegStack.pop(),RegStack.pop()));
            RegStack.push();
        }
        else if (expr.id.image.equals(">="))
        {
            instrs.add(COMMENT("Execute '>=' relation"));
            instrs.add(INSTR("cmpq",RegStack.pop(),RegStack.pop()));
            RegStack.push();
            //change the initial flag to '0' first
            instrs.add(INSTR("movq",CONST(0),RegStack.pop()));
            RegStack.push();
            //change flag end
            RegStack.push();
            instrs.add(INSTR("movq",CONST(1),RegStack.pop()));
            RegStack.push();
            instrs.add(INSTR("cmovge",RegStack.pop(),RegStack.pop()));
            RegStack.push();
        }


        return null;
      }

      public Void visit(Expression.Add expr) {
        codeGen(expr.left);
        codeGen(expr.right);
        instrs.add(COMMENT(expr.id.image));
        // TODO
        CType left = getType(expr.left);
        CType right = getType(expr.right);

        if (left == CType.LONGSTAR || left == CType.CHARSTARSTAR)
        {
            instrs.add(INSTR("imulq",CONST(sizeof(left)),RegStack.peek()));
        }
        else if (right == CType.LONGSTAR || right == CType.CHARSTARSTAR)
        {

            RegStack.pop();
            instrs.add(INSTR("imulq",CONST(sizeof(right)),RegStack.peek()));
            RegStack.push();
        }

        if (expr.id.image.equals("+"))
        {
            instrs.add(INSTR("addq",RegStack.pop(),RegStack.pop()));
            RegStack.push();
        }
        else if (expr.id.image.equals("-"))
        {
            instrs.add(INSTR("subq",RegStack.pop(),RegStack.pop()));
            RegStack.push();
        }

        return null;
      }

      public Void visit(Expression.Mul expr) {
        codeGen(expr.left);
        codeGen(expr.right);
        // TODO, see Add

        instrs.add(COMMENT(expr.id.image));
        if (expr.id.image.equals("*"))
        {
            instrs.add(INSTR("imulq",RegStack.pop(),RegStack.pop()));
            RegStack.push();
        }
        else if (expr.id.image.equals("/"))
        {
            instrs.add(INSTR("movq",CONST(0), RDX));
            RegStack.pop();
            instrs.add(INSTR("movq",RegStack.pop(), RAX));
            RegStack.push();
            RegStack.push();
            instrs.add(INSTR("idivq",RegStack.pop(),RAX));
            instrs.add(INSTR("movq",RAX, RegStack.pop()));
            RegStack.push();
        }
 
        return null;
      }

      public Void visit(Expression.Ref expr) {
        codeGen(expr.expr);
        String id = expr.expr.id.image;
        int i = lookupLocalVar(id);
        instrs.add(COMMENT("push &" + id));
        if (i >= 0) { // Local
          // TODO implement local variables
          instrs.add(INSTR("leaq", 
                OFFSET(RSP, 8*(MAX_LOCALS - i)), 
                  RegStack.push()));

        } else { // Global
          instrs.add(INSTR("leaq", 
                id, 
                  RegStack.push()));
        }
        return null;
      }

      public Void visit(Expression.Deref expr) {
        codeGen(expr.expr);

        String id = expr.expr.id.image;
        int i = lookupLocalVar(id);

        instrs.add(COMMENT("push *" + id));
        if (i >= 0) {
            // TODO implement local variables
             instrs.add(COMMENT("deref local variable" + id));
            //get address
            //instrs.add(INSTR("movq", OFFSET(RSP, 8*(MAX_LOCALS - i)), RegStack.peek()));
        
            instrs.add(INSTR("movq","(" + RegStack.peek() + ")", RegStack.peek()));

        } else {
          // TODO implement dereferencing
          instrs.add(COMMENT("deref global variable" + id));
          instrs.add(INSTR("movq","(" + RegStack.peek() + ")", RegStack.peek()));
        }
        return null;
      }

      public Void visit(Expression.Negative expr) {
        codeGen(expr.expr);
        instrs.add(INSTR("negq", RegStack.peek()));
        return null;
      }

      public Void visit(Expression.Positive expr) {
        codeGen(expr.expr);
        return null;
      }

      public Void visit(Expression.Char expr) {
        instrs.add(COMMENT("push char " + expr.ch + " top=" + RegStack.top));
        // TODO implement chars, see: Int
        instrs.add(INSTR("movq", CONST(expr.ch), RegStack.push()));

        return null;
      }

      public Void visit(Expression.Text expr) {
        Label label;
        if (!stringMap.containsKey(expr.text)) {
          label = new Label();
          labelMap.put(label, expr.text);
          stringMap.put(expr.text, label);
        } else {
          label = stringMap.get(expr.text);
        }

        // Pushing our string label
        instrs.add(INSTR("movq", CONST(label), RegStack.push()));

        return null;
      }

      public Void visit(Expression.Int expr)   {
        instrs.add(COMMENT("push num " + expr.value + " top=" + RegStack.top));
        instrs.add(INSTR("movq", CONST(expr.value), RegStack.push()));
        return null;
      }

      public Void visit(Expression.Double expr) {
        Double value = expr.value;
        Label label = new Label('d');
        doubleMap.put(value, label); 
        // BONUS TODO - implement doubles

        int i = (int)Math.round(value);
        instrs.add(COMMENT("convert double to int and push num " + expr.value + " top=" + RegStack.top));
        instrs.add(INSTR("movq", CONST(i), RegStack.push()));

        return null;
      }

      public Void visit(Expression.ID id) {
        int i = lookupLocalVar(id.id.image);
        if (i >= 0) {
          // TODO implement local variables
          instrs.add(COMMENT("push local variables"));
          //instrs.add(COMMENT(Integer.toString(i)));
          //instrs.add(INSTR("movq",  (i * 8 + 40) + "(" + RSP + ")", RegStack.push()));
          instrs.add(INSTR("movq",  OFFSET(RSP, 8*(MAX_LOCALS - i)), RegStack.push()));
          

        } else {
          instrs.add(COMMENT("push global var " + id.id.image));
          instrs.add(INSTR("movq", id.id.image, RegStack.push()));
        }
        return null;
      }

      public Void visit(Expression.Call expr) {
        List<Expression> args = expr.args;
        // Generate code for our arguments
        for (Expression arg : args) {
          codeGen(arg);
        }
        
        // Pop values into the argument registers
        for (int i = args.size() - 1; i >= 0; i--) {
          instrs.add(INSTR("movq", RegStack.pop(), regArgs[i])); 
        }

        // Move the # of var args into RAX
        // TODO BONUS - change this so it doesn't just hardcode 0
        if (expr.id.image.equals("printf")) {
          instrs.add(INSTR("movq", CONST(0), RAX));
        }

        instrs.add(INSTR("call", expr.id));

        // Move the result of our function call into a stack register
        instrs.add(INSTR("movq", RAX, RegStack.push()));

        return null;
      }

      public Void visit(Expression.Array expr) {
        codeGen(expr.index);
        String id = expr.id.image;
        int i = lookupLocalVar(id);
        int len = sizeof(getType(expr));

        if (i >= 0) {
          // TODO implement locals
           instrs.add(COMMENT("find local var for array"));
           //stroe index * len 
           if (RegStack.top <= RegStack.size)  
              instrs.add(INSTR("imulq",  CONST(len), RegStack.peek()));
            else
              {
                   instrs.add(COMMENT("here"));
                   instrs.add(INSTR("movq",  RegStack.peek(), RAX));
                   instrs.add(INSTR("imulq",  CONST(len), RAX));
                   instrs.add(INSTR("movq",  RAX, RegStack.peek()));
              }

           instrs.add(INSTR("movq",  OFFSET(RSP, 8*(MAX_LOCALS - i)), RegStack.push()));
           //pop address, pop index * len, store address + index * len
           instrs.add(INSTR("addq",  RegStack.pop(), RegStack.pop()));
           RegStack.push();

            if (RegStack.top <= RegStack.size)
              instrs.add(INSTR("movq",  "(" + RegStack.peek() + ")", RegStack.peek()));
            else
            {
              instrs.add(INSTR("movq",  RegStack.peek(), RAX));
              instrs.add(INSTR("movq",  "(" + RAX + ")", RAX));
              instrs.add(INSTR("movq", RAX, RegStack.peek()));
            }

        } else {
          i = lookupGlobalVar(id);
          // TODO implement arrays
           instrs.add(COMMENT("find global var for array"));
           //stroe index * len
           if (RegStack.top <= RegStack.size)  
              instrs.add(INSTR("imulq",  CONST(len), RegStack.peek()));
            else
              {
                   instrs.add(INSTR("movq",  RegStack.peek(), RAX));
                   instrs.add(INSTR("imulq",  CONST(len), RAX));
                   instrs.add(INSTR("movq",  RAX, RegStack.peek()));
              }

           instrs.add(INSTR("movq",  id, RegStack.push()));
           //pop address, pop index * len, store address + index * len
           instrs.add(INSTR("addq",  RegStack.pop(), RegStack.pop()));
           RegStack.push();

            if (RegStack.top <= RegStack.size)
              instrs.add(INSTR("movq",  "(" + RegStack.peek() + ")", RegStack.peek()));
            else
            {
              instrs.add(INSTR("movq",  RegStack.peek(), RAX));
              instrs.add(INSTR("movq",  "(" + RAX + ")", RAX));
              instrs.add(INSTR("movq", RAX, RegStack.peek()));
            }

        }
        return null;
      }
    });
  }
}

