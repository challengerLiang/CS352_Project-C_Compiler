package simplec;

import java.util.*;
import simplec.parse.*;

public class Scope {
  public static Scope rootScope = null;
  public static Scope topScope = null;
  //add variable
  public Scope parent = null;
  public Hashtable<String, AST.Value> table = new Hashtable<String, AST.Value>();
  public Scope(Scope par) {
    // TODO: Implement scope/symbol tables
    // Feel free to implement this in any way you want
    // Just make sure that while parsing, topScope refers
    // to the current scope. Whenever we make a new variable
    // or statement, we just set that objects scope to be
    // Scope.topScope, which makes some other things easier.
    if (par == null)
    {
        rootScope = this;
        parent = null;
    }
    else
        parent = par;
        //System.out.println(parent +" " + 27 + topScope);

    //topScope = this;
  }

  public void insert(String id, AST.Value type)
  {
    if (type.scope.lookup(type.id.image) != null && !(type.id.image.equals("for"))
      && !(type.id.image.equals("while")) && !(type.id.image.equals("if")) &&
          !(type.id.image.equals("do while")))
    {
        Error.VariableRedefinition(type.id);
    }
    table.put(id, type);
  }

  public AST.Value lookup_check(String id)
  {
      return null;
  }

  public AST.Value lookup(String id) 
  {
    for (Scope scope = this; scope != null; scope = scope.parent) 
    {
        //System.out.println(scope + " " + Scope.topScope + " " + scope.parent + " " + scope.rootScope);
        AST.Value found = scope.table.get(id);
        if (found != null) 
          return found;
    }
    return null;
  }
}
