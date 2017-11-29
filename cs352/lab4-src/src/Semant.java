package simplec;

import java.math.BigInteger;
import java.util.*;

import simplec.parse.*;
import static simplec.AST.*;

public class Semant {

  private static void usage() {
    throw new java.lang.Error("Usage: java simplec.Semant <source>.c");
  }

  private Semant() { }

  private Value current_FOV;
  private CType resultDeref;
  private CType resultRef;

  public static void main(String... args) {
    try {
      Value.Unit goal = new SimpleC(System.in).goal();
      //Uncomment this if you want the AST to print
      //new Print(goal);
      Semant semant = new Semant();
      semant.typeCheck(goal);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  void typeCheck(Value v) {
    if (v == null) return;
    v.accept(new Value.Visitor<Void>() {
      public Void visit(Value.Unit v) {
        for (Value fov : v.fovList) {
          //System.out.println(fov.token);
          current_FOV = fov;
          typeCheck(fov);
        }
        return null;
      }

      public Void visit(Value.Variable v) {
        typeCheck(v.type);

        //if (v.scope.lookup(v.id.image) != null)
            //Error.VariableRedefinition(v.id);
        return null;
      }

      public Void visit(Value.Argument v) {
        typeCheck(v.type);
        return null;
      }

      public Void visit(Value.Type v) {
        return null;
      }

      public Void visit(Value.ScalarAccess v) {
        return null;
      }

      public Void visit(Value.Function v) {
        typeCheck(v.varType);
        for (Value arg : v.argList) {
          typeCheck(arg);
        }
        typeCheck(v.cStmt);

        //if (v.scope.lookup(v.id.image) != null)
            //Error.FunctionRedefinition(v.id, v);
        return null;
      }

      public Void visit(Value.VariableList v) {
        for (Value value : v.vars) {
          typeCheck(value);
        }
        return null;
      }
    });
  }

  void typeCheck(Statement stmt) {
    if (stmt == null) return;
    stmt.accept(new Statement.Visitor<Void>() {
      public Void visit(Statement.CompoundStatement stmt) {
        // SimpleC forbids mixed declarations and code (just like C90)
        // So we need to set a flag once we've seen code, and make sure
        // no variable declarations follow
        boolean seenNonDecl = false;
        int i = 0;
        for (Statement s : stmt.stmtList) {
          if (s instanceof Statement.VariableDecls) {
            if (seenNonDecl) {
              Error.MixedDeclarations(stmt.id);
            }
          } else {
            seenNonDecl = true;
          }
          typeCheck(s);
          i++;
        }
        return null;
      }

      public Void visit(Statement.VariableDecls decls) {

        for (Value.Variable var : decls.vars) {
          typeCheck(var);
        }
        return null;
      }

      public Void visit(Statement.AssignStatement stmt) {
        CType lhs = getType(stmt.var);
        CType rhs = getType(stmt.expression);

        //check array
        if (stmt.index != null && (! getType(stmt.index).toString().equals("long")) &&
            (! getType(stmt.index).toString().equals("char")) )
          Error.NonIntSubscript(stmt.id);

        //related to array
        if (stmt.index != null)
        {
        }
        if (lhs != null && rhs != null && stmt.index != null && 
            lhs.toString().equals("long*") && rhs.toString().equals("long"))
        {
            //System.err.println("here");
        }
        else if (lhs != null && rhs != null && stmt.index != null && 
            lhs.toString().equals("double*") && rhs.toString().equals("double"))
        {}
        else if (lhs != null && rhs != null && stmt.index != null && 
            lhs.toString().equals("char*") && rhs.toString().equals("char"))
        {}
        else if (lhs != null && rhs != null && stmt.index != null && 
            lhs.toString().equals("char**") && rhs.toString().equals("char*"))
        {}


        else if (lhs != null && rhs != null && lhs.toString().equals("long") &&
            (rhs.toString().contains("*")) )
        {
            Error.AssignPointerToInt(stmt.id);
        }
        else if (lhs != null && rhs != null && lhs.toString().equals("double") 
              && rhs.toString().equals("long"))
        {}
        else if (lhs != null && rhs != null && rhs.toString().equals("double") 
              && lhs.toString().equals("long"))
        {}
        else if (lhs != null && rhs != null && lhs.toString().equals("char") 
              && rhs.toString().equals("long"))
        {}
        else if (lhs != null && rhs != null && lhs.toString().equals("long") 
              && rhs.toString().equals("char"))
        {}
        else if (lhs != null && rhs != null && lhs.toString().equals("double") 
              && rhs.toString().equals("char"))
        {}
        else if (lhs != null && rhs != null && lhs != rhs)
            Error.IncompatibleAssignType(stmt.id, lhs, rhs) ;

        if (lhs == null) {
          //modeified code by myself
          //System.err.println(Scope.topScope + " 108");
          if (stmt.scope.lookup(stmt.var.id.image) == null)
          {
              // Note that until you add scope, this might
              // say every variable is undeclared
              //Error.UndeclaredVariable(stmt.id, stmt.var);
              Error.UndeclaredAssign(stmt.id, stmt.var);
          }
          //modefied code by myself end

          return null;
        }
        
        return null;
      }

      public Void visit(Statement.ForStatement stmt) {
        Scope.topScope.insert("for", new AST.Value.Type(new Token(1, "for")));
        typeCheck(stmt.init);
        CType condType = getType(stmt.cond);
        typeCheck(stmt.update);
        typeCheck(stmt.body);
        Scope.topScope.table.remove("for");
        return null;
      }

      public Void visit(Statement.WhileStatement stmt) {
        Scope.topScope.insert("while", new AST.Value.Type(new Token(1, "while")));
        CType condType = getType(stmt.cond);
        typeCheck(stmt.body);
        Scope.topScope.table.remove("while");
        return null;
      }

      public Void visit(Statement.DoWhileStatement stmt) {
        Scope.topScope.insert("do while", new AST.Value.Type(new Token(1, "while")));
        CType condType = getType(stmt.cond);
        typeCheck(stmt.body);
        Scope.topScope.table.remove("do while");
        return null;
      }

      public Void visit(Statement.IfStatement stmt) {
        CType condType = getType(stmt.cond);
        typeCheck(stmt.body);
        if (stmt.elseStmt != null)
          typeCheck(stmt.body);
        return null;
      }

      public Void visit(Statement.ElseStatement stmt) {
        typeCheck(stmt.body);
        return null;
      }

      public Void visit(Statement.CallStatement stmt) {
        CType exprType = getType(stmt.callExpr);
        return null;
      }

      public Void visit(Statement.ContinueStatement stmt) {
        AST.Value check1 = Scope.topScope.lookup("while");
        AST.Value check2 = Scope.topScope.lookup("for");
        AST.Value check3 = Scope.topScope.lookup("do while");
        if (check1 == null && check2 == null && check3 ==null) 
             Error.ContinueNotInLoop(stmt.id);
        return null;
      }

      public Void visit(Statement.BreakStatement stmt) {
        AST.Value check1 = Scope.topScope.lookup("while");
        AST.Value check2 = Scope.topScope.lookup("for");
        AST.Value check3 = Scope.topScope.lookup("do while");
        if (check1 == null && check2 == null && check3 == null) 
             Error.BreakNotInLoop(stmt.id);
        return null;
      }

      public Void visit(Statement.ReturnStatement stmt) {
        Value.Function cur_function = (Value.Function) current_FOV;

        if (stmt.retVal == null)
        {
            if (cur_function.varType.type.toString().equals("void"))
            {}
            else
                Error.IncompatibleReturnType(stmt.id, CType.VOID, cur_function.varType.type);    
        }
        else
        {
            CType type = getType(stmt.retVal);
            
            if (cur_function.varType.type.toString().equals("void") &&
                (!type.toString().equals("void") ) )
                Error.ReturnValueInVoidFunc(stmt.id);
            //System.err.println(Scope.parentScope
            else if (cur_function.varType.type == type) 
            {}
            else if (cur_function.varType.type == null || type == null)
            {
              //System.err.println(cur_function.varType.type + "   " + type);
            }   
            else if (cur_function.varType.type.toString().equals("long") && type.toString().equals("char") )
            {}
            else if (type.toString().equals("long") && cur_function.varType.type.toString().equals("char") )
            {}
            else if (type.toString().equals("long") && cur_function.varType.type.toString().equals("double") )
            {}
            else if  (type.toString().equals("double") && cur_function.varType.type.toString().equals("long") )
            {}
            else if (type.toString().contains("*") && cur_function.varType.type.toString().equals("long"))
                Error.AssignPointerToInt(stmt.id);
            else
                Error.IncompatibleReturnType(stmt.id, type, cur_function.varType.type);
          }
        return null;
      }
    });

  }

  public CType getType(Value var) {
    if (var == null) return null;
    return var.accept(new Value.Visitor<CType>() {
      public CType visit(Value.Unit v) {
        return null;
      }

      public CType visit(Value.Variable v) {
        return getType(v.type);
      }

      public CType visit(Value.Argument v) {
        return null;
      }

      public CType visit(Value.Type v) {
        return v.type;
      }

      public CType visit(Value.ScalarAccess v) {
        //return null;
        //modified by myself
        AST.Value val = v.scope.lookup(v.id.image);
        if (val == null)
        {
            //System.err.println("Error occurred in Semant 207");
        }
        return getType(val);
      }

      public CType visit(Value.Function v) {
        return getType(v.varType);
      }

      public CType visit(Value.VariableList v) {
        if (v.vars.size() > 0)
          return getType(v.vars.get(0));
        return null;
      }
    });
     
  }

  public CType getType(Expression expr) {
    return expr.accept(new Expression.Visitor<CType>() {
      public CType visit(Expression.Or expr) {
        CType lhs = getType(expr.left);
        CType rhs = getType(expr.right);

        if (lhs == null || rhs == null) {   }
        if (lhs == rhs)
          return lhs;
        else if ( (lhs!= null && lhs.toString().equals("long") && rhs.toString().contains("*"))
                || (lhs != null && rhs.toString().equals("long") && lhs.toString().contains("*")) )
            Error.AssignPointerToInt(expr.id);
        else
          {
          //System.err.println("Error need to be handled further");
          }
        return null;
      }

      public CType visit(Expression.And expr) {
        CType lhs = getType(expr.left);
        CType rhs = getType(expr.right);

        if (lhs == null || rhs == null) {   }
        if (lhs == rhs)
          return lhs;
        else if ( (lhs!= null && lhs.toString().equals("long") && rhs.toString().contains("*"))
                || (lhs != null && rhs.toString().equals("long") && lhs.toString().contains("*")) )
            Error.AssignPointerToInt(expr.id);
        else
          {
          //System.err.println("Error need to be handled further");
          }
        return null;
      }

      public CType visit(Expression.Eq expr) {
        CType lhs = getType(expr.left);
        CType rhs = getType(expr.right);

        if (lhs == null || rhs == null) {   }
       if (lhs == rhs)
          return lhs;
        else if ( (lhs!= null && lhs.toString().equals("long") && rhs.toString().contains("*"))
                || (lhs != null && rhs.toString().equals("long") && lhs.toString().contains("*")) )
            Error.AssignPointerToInt(expr.id);
        else
          {
          //System.err.println("Error need to be handled further");
          }
        return null;
      }

      public CType visit(Expression.Rel expr) {
        CType lhs = getType(expr.left);
        CType rhs = getType(expr.right);

        if (lhs == null || rhs == null) {   }
       if (lhs == rhs)
          return lhs;
        else if ( (lhs!= null && lhs.toString().equals("long") && rhs.toString().contains("*"))
                || (lhs != null && rhs.toString().equals("long") && lhs.toString().contains("*")) )
            Error.AssignPointerToInt(expr.id);
        else if ((lhs!= null && lhs.toString().equals("long") && rhs.toString().equals("double"))
                || (lhs != null && rhs.toString().equals("long") && lhs.toString().equals("double")) )
          {}
        else if ((lhs!= null && lhs.toString().equals("char") && rhs.toString().equals("double"))
                || (lhs != null && rhs.toString().equals("char") && lhs.toString().equals("double")) )
        {}
        else
          {
          Error.WrongTypeBinary(expr.id, expr.id.image, lhs, rhs);
          }
        return null;
      }

      public CType visit(Expression.Add expr) {

        CType lhs = getType(expr.left);
        CType rhs = getType(expr.right);


        if (lhs == null || rhs == null) {   }
        else if (lhs == rhs)
          return lhs;
        else if ( (lhs!= null && lhs.toString().equals("long") && rhs.toString().contains("*"))
                || (lhs != null && rhs.toString().equals("long") && lhs.toString().contains("*")) )
            Error.AssignPointerToInt(expr.id);
        else if ( (lhs!= null && (lhs.toString().equals("long") || lhs.toString().equals("char") 
                  || lhs.toString().equals("double") )&& rhs.toString().contains("*"))
            ||    (lhs!= null && (rhs.toString().equals("long") || rhs.toString().equals("char") 
                  || rhs.toString().equals("double") )&& lhs.toString().contains("*")) )
            Error.WrongTypeBinary(expr.id, expr.id.image, lhs, rhs);
        else
          {
          //System.err.println("Error need to be handled further");
          }        
        return null;
      }

      public CType visit(Expression.Mul expr) {
        CType lhs = getType(expr.left);
        CType rhs = getType(expr.right);

        //Add code by myself
        //Divide by 0 done
        if (expr.id.image.equals("/") && expr.right.id.image.equals("0"))
            Error.DivideByZero(expr.id);


        if (lhs == null || rhs == null) {   }
        else if (lhs == rhs)
          return lhs;
        else if ( (lhs!= null && lhs.toString().equals("long") && rhs.toString().contains("*"))
                || (lhs != null && rhs.toString().equals("long") && lhs.toString().contains("*")) )
            Error.AssignPointerToInt(expr.id);
        else if ( (lhs!= null && (lhs.toString().equals("long") || lhs.toString().equals("char") 
                  || lhs.toString().equals("double") )&& rhs.toString().contains("*"))
            ||    (lhs!= null && (rhs.toString().equals("long") || rhs.toString().equals("char") 
                  || rhs.toString().equals("double") )&& lhs.toString().contains("*")) )
            Error.WrongTypeBinary(expr.id, expr.id.image, lhs, rhs);
        else
          {
          //System.err.println("Error need to be handled further");
          }
        return null;
      }

      public CType visit(Expression.Ref expr) {
        CType inside = getType(expr.expr);
        if (inside.toString().equals("char*"))
            return CType.CHARSTARSTAR;
        else if (inside.toString().equals("char"))
            return CType.CHARSTAR;
        else if (inside.toString().equals("long"))
            return CType.LONGSTAR;
        else if (inside.toString().equals("double"))
            return CType.DOUBLESTAR;
        else
            return null;
      }

      public CType visit(Expression.Deref expr) {
        CType inside = getType(expr.expr);
        if (inside != null && (!inside.toString().contains("*")))
          Error.WrongTypeUnary(expr.id, expr.id.image);

        if (inside.toString().equals("char**"))
            return CType.CHARSTAR;
        else if(inside.toString().equals("char*"))
            return CType.CHAR;
        else if (inside.toString().equals("long*"))
            return CType.LONG;
        else if (inside.toString().equals("double*"))
            return CType.DOUBLE;
        else
            return null;
      }

      public CType visit(Expression.Negative expr) {
        CType inside = getType(expr.expr);
        if (inside.toString().contains("*"))
            Error.WrongTypeUnary(expr.id, expr.id.image);
        return null;
      }

      public CType visit(Expression.Positive expr) {
        CType inside = getType(expr.expr);
        if (inside.toString().contains("*"))
            Error.WrongTypeUnary(expr.id, expr.id.image);
        return null;
      }

      public CType visit(Expression.Char expr) { return CType.CHAR; }
      public CType visit(Expression.Text expr) { return CType.CHARSTAR; }
      public CType visit(Expression.Int expr)  { return CType.LONG; }
      public CType visit(Expression.Double expr) { return CType.DOUBLE; }

      public CType visit(Expression.ID id) {
        AST.Value val = id.scope.lookup(id.id.image);
        if (val == null)
        {
          Error.UndeclaredVariable(id.id, new Value.Variable(new Token(2, id.id.image), new Value.Type(id.id)));
        }

        return getType(val);
      }

      public CType visit(Expression.Call expr) {
        //System.err.println(expr.args.size());
        AST.Value.Function val = (AST.Value.Function)expr.scope.lookup(expr.id.image);
        if (val == null)
        {
            //System.err.println("Error for undeclaredFunction");
        }
        else if (expr.args.size() < val.argList.size())
        {
            Error.TooFewArguments(expr.id, val);
        }
        else if (expr.args.size() > val.argList.size())
        {
            Error.TooManyArguments(expr.id, val);
        }
        else 
        {
          for (int i = 0; i < expr.args.size(); i++)
          { 
              String str1 = val.argList.get(i).type.type.toString();
              String str2 = null;
              CType t = getType(expr.args.get(i));
              if (t != null)
                  str2 = t.toString();
              if (str2 ==null)
              {}
              else if (str1.equals("long") && str2.equals("char"))
              {}
              else if (str2.equals("long") && str1.equals("char"))
              {}
              else if (str1.equals("double") && str2.equals("long"))
              {}
              else if (str2.equals("double") && str1.equals("long"))
              {}
              else if (str1.equals("double") && str2.equals("char"))
              {}
              else if (str2.equals("double") && str1.equals("char"))
              {}
              else if (str1.equals(str2) == false)
                  Error.IncompatibleArgs(expr.id, i + 1, val);
          }
        }
        return null;
      }

      public CType visit(Expression.Array expr) {
        CType array_type = getType(expr.index);
        if (array_type.toString().equals("long"))
        {}
        else if (array_type.toString().equals("char"))
        {}
        else 
            Error.NonIntSubscript(expr.id);

        AST.Value val = expr.scope.lookup(expr.id.image);

        if (val == null)
        {
          Error.UndeclaredVariable(expr.id, new Value.Variable(new Token(2, expr.id.image), new Value.Type(expr.id)));
          return null;
        }

        CType temp = getType(val);

        if (temp.toString().equals("long*"))
            return CType.LONG;
         else if (temp.toString().equals("double*"))
            return  CType.DOUBLE;
         else if (temp.toString().equals("char*")) {
            return CType.CHAR;
         }
         else if (temp.toString().equals("char**")) {
            return CType.CHARSTAR;
         }

        return temp;
      }
    });
  }
}
