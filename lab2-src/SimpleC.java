/* SimpleC.java */
/* Generated By:JavaCC: Do not edit this line. SimpleC.java */
/** Simple brace matcher.*/
public class SimpleC implements SimpleCConstants {

  /** Main entry point. */
  public static void main(String args[]) throws ParseException {
    SimpleC parser = new SimpleC(System.in);
    /*parser.Input();**/
    try
    {
    parser.goal();
    System.out.println("Program compiled Successfully!");
    }
    catch (ParseException e)
    {
        System.out.println(e.currentToken.beginLine +": Syntax Error");
    }
  }

/**Root production. */
  static final public void Input() throws ParseException {int count;
    count = anytoken();
    jj_consume_token(0);
System.out.println("The number of tokens is " + count);
  }

  static final public int anytoken() throws ParseException {Token t;
  int count=0;
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case CHARSTAR:{
        /** KEY_WORD */
           t = jj_consume_token(CHARSTAR);
System.out.println("CHARSTAR, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case CHARSTARSTAR:{
        t = jj_consume_token(CHARSTARSTAR);
System.out.println("CHARSTARSTAR, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case LONGSTAR:{
        t = jj_consume_token(LONGSTAR);
System.out.println("LONGSTAR, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case LONG:{
        t = jj_consume_token(LONG);
System.out.println("LONG, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case VOID:{
        t = jj_consume_token(VOID);
System.out.println("VOID, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case IF:{
        t = jj_consume_token(IF);
System.out.println("IF, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case ELSE:{
        t = jj_consume_token(ELSE);
System.out.println("ELSE, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case WHILE:{
        t = jj_consume_token(WHILE);
System.out.println("WHILE, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case DO:{
        t = jj_consume_token(DO);
System.out.println("DO, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case FOR:{
        t = jj_consume_token(FOR);
System.out.println("FOR, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case CONTINUE:{
        t = jj_consume_token(CONTINUE);
System.out.println("CONTINUE, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case BREAK:{
        t = jj_consume_token(BREAK);
System.out.println("BREAK, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case RETURN:{
        t = jj_consume_token(RETURN);
System.out.println("RETURN, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case DOUBLESTAR:{
        t = jj_consume_token(DOUBLESTAR);
System.out.println("DOUBLESTAR, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case DOUBLE:{
        t = jj_consume_token(DOUBLE);
System.out.println("DOUBLE, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case DEVIDESTAR:{
        /** Comments */
           t = jj_consume_token(DEVIDESTAR);
System.out.println("DEVIDESTAR, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case STARDEVIDE:{
        t = jj_consume_token(STARDEVIDE);
System.out.println("STARDEVIDE, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case DEVIDEDEVIDE:{
        t = jj_consume_token(DEVIDEDEVIDE);
System.out.println("DEVIDEDEVIDE, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case LPARENT:{
        /** OPERATORS */
           t = jj_consume_token(LPARENT);
System.out.println("LPARENT, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case RPARENT:{
        t = jj_consume_token(RPARENT);
System.out.println("RPARENT, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case LBRACE:{
        t = jj_consume_token(LBRACE);
System.out.println("LBRACE, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case RBRACE:{
        t = jj_consume_token(RBRACE);
System.out.println("RBRACE, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case LCURLY:{
        t = jj_consume_token(LCURLY);
System.out.println("LCURLY, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case RCURLY:{
        t = jj_consume_token(RCURLY);
System.out.println("RCURLY, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case COMMA:{
        t = jj_consume_token(COMMA);
System.out.println("COMMA, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case SEMICOLON:{
        t = jj_consume_token(SEMICOLON);
System.out.println("SEMICOLON, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case OROR:{
        t = jj_consume_token(OROR);
System.out.println("OROR, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case ANDAND:{
        t = jj_consume_token(ANDAND);
System.out.println("ANDAND, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case EQUALEQUAL:{
        t = jj_consume_token(EQUALEQUAL);
System.out.println("EQUALEQUAL, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case NOTEQUAL:{
        t = jj_consume_token(NOTEQUAL);
System.out.println("NOTEQUAL, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case LESSEQUAL:{
        t = jj_consume_token(LESSEQUAL);
System.out.println("LESSEQUAL, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case GREATEQUAL:{
        t = jj_consume_token(GREATEQUAL);
System.out.println("GREATEQUAL, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case EQUAL:{
        t = jj_consume_token(EQUAL);
System.out.println("EQUAL, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case AMPERSAND:{
        t = jj_consume_token(AMPERSAND);
System.out.println("AMPERSAND, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case LESS:{
        t = jj_consume_token(LESS);
System.out.println("LESS, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case GREAT:{
        t = jj_consume_token(GREAT);
System.out.println("GREAT, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case PLUS:{
        t = jj_consume_token(PLUS);
System.out.println("PLUS, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case MINUS:{
        t = jj_consume_token(MINUS);
System.out.println("MINUS, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case TIMES:{
        t = jj_consume_token(TIMES);
System.out.println("TIMES, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case DIVIDE:{
        t = jj_consume_token(DIVIDE);
System.out.println("DIVIDE, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case PERCENT:{
        t = jj_consume_token(PERCENT);
System.out.println("PERCENT, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case ID:{
        /**TOKEN PRODUCTION*/
           t = jj_consume_token(ID);
System.out.println("ID, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case CHAR_CONST:{
        t = jj_consume_token(CHAR_CONST);
System.out.println("CHAR_CONST, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case STRING_CONST:{
        t = jj_consume_token(STRING_CONST);
System.out.println("STRING_CONST, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case INTEGER_CONST:{
        t = jj_consume_token(INTEGER_CONST);
System.out.println("INTEGER_CONST, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case DOUBLE_CONST:{
        t = jj_consume_token(DOUBLE_CONST);
System.out.println("DOUBLE_CONST, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case DIGIT:{
        t = jj_consume_token(DIGIT);
System.out.println("DIGIT \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case OCTAL:{
        t = jj_consume_token(OCTAL);
System.out.println("OCTAL, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case HEX_DIGIT:{
        t = jj_consume_token(HEX_DIGIT);
System.out.println("HEX_DIGIT, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case LETTER:{
        t = jj_consume_token(LETTER);
System.out.println("LETTER, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      case OTHER_CHAR:{
        t = jj_consume_token(OTHER_CHAR);
System.out.println("OTHER_CHAR, \u005c""+t.image+"\u005c"" ); count++;
        break;
        }
      default:
        jj_la1[0] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case CHARSTAR:
      case CHARSTARSTAR:
      case LONGSTAR:
      case LONG:
      case VOID:
      case IF:
      case ELSE:
      case WHILE:
      case DO:
      case FOR:
      case CONTINUE:
      case BREAK:
      case RETURN:
      case DOUBLESTAR:
      case DOUBLE:
      case DEVIDESTAR:
      case STARDEVIDE:
      case DEVIDEDEVIDE:
      case LPARENT:
      case RPARENT:
      case LBRACE:
      case RBRACE:
      case LCURLY:
      case RCURLY:
      case COMMA:
      case SEMICOLON:
      case OROR:
      case ANDAND:
      case EQUALEQUAL:
      case NOTEQUAL:
      case LESSEQUAL:
      case GREATEQUAL:
      case EQUAL:
      case AMPERSAND:
      case LESS:
      case GREAT:
      case PLUS:
      case MINUS:
      case TIMES:
      case DIVIDE:
      case PERCENT:
      case ID:
      case CHAR_CONST:
      case STRING_CONST:
      case INTEGER_CONST:
      case DOUBLE_CONST:
      case DIGIT:
      case OCTAL:
      case HEX_DIGIT:
      case LETTER:
      case OTHER_CHAR:{
        ;
        break;
        }
      default:
        jj_la1[1] = jj_gen;
        break label_1;
      }
    }
{if ("" != null) return count;}
    throw new Error("Missing return statement in function");
  }

/**add more function*/
  /**parse the syntax*/
  static final public   void goal() throws ParseException {
    program();
    jj_consume_token(0);
  }

  static final public void program() throws ParseException {
    function_or_var_list();
  }

  static final public void function_or_var_list() throws ParseException {
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case CHARSTAR:
      case CHARSTARSTAR:
      case LONGSTAR:
      case LONG:
      case VOID:
      case DOUBLESTAR:
      case DOUBLE:{
        ;
        break;
        }
      default:
        jj_la1[2] = jj_gen;
        break label_2;
      }
      var_type();
      jj_consume_token(ID);
      function_or_var_list_prime();
    }
  }

/**add to deal with left factor*/
  static final public   void function_or_var_list_prime() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LPARENT:{
      function();
      break;
      }
    case COMMA:
    case SEMICOLON:{
      global_var();
      break;
      }
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void function() throws ParseException {
    jj_consume_token(LPARENT);
    arguments();
    jj_consume_token(RPARENT);
    compound_statement();
  }

  static final public void arg_list() throws ParseException {
    arg();
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case COMMA:{
        ;
        break;
        }
      default:
        jj_la1[4] = jj_gen;
        break label_3;
      }
      jj_consume_token(COMMA);
      arg();
    }
  }

  static final public void arguments() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case CHARSTAR:
    case CHARSTARSTAR:
    case LONGSTAR:
    case LONG:
    case VOID:
    case DOUBLESTAR:
    case DOUBLE:{
      arg_list();
      break;
      }
    default:
      jj_la1[5] = jj_gen;

    }
  }

  static final public void arg() throws ParseException {
    var_type();
    jj_consume_token(ID);
  }

  static final public void global_var() throws ParseException {
    global_var_list();
    jj_consume_token(SEMICOLON);
  }

  static final public void global_var_list() throws ParseException {
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case COMMA:{
        ;
        break;
        }
      default:
        jj_la1[6] = jj_gen;
        break label_4;
      }
      jj_consume_token(COMMA);
      jj_consume_token(ID);
    }
  }

  static final public void var_type() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case CHARSTAR:{
      jj_consume_token(CHARSTAR);
      break;
      }
    case CHARSTARSTAR:{
      jj_consume_token(CHARSTARSTAR);
      break;
      }
    case DOUBLE:{
      jj_consume_token(DOUBLE);
      break;
      }
    case DOUBLESTAR:{
      jj_consume_token(DOUBLESTAR);
      break;
      }
    case LONG:{
      jj_consume_token(LONG);
      break;
      }
    case LONGSTAR:{
      jj_consume_token(LONGSTAR);
      break;
      }
    case VOID:{
      jj_consume_token(VOID);
      break;
      }
    default:
      jj_la1[7] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void assignment() throws ParseException {
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case LBRACE:
      case EQUAL:{
        ;
        break;
        }
      default:
        jj_la1[8] = jj_gen;
        break label_5;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case EQUAL:{
        jj_consume_token(EQUAL);
        expression();
        break;
        }
      case LBRACE:{
        jj_consume_token(LBRACE);
        expression();
        jj_consume_token(RBRACE);
        jj_consume_token(EQUAL);
        expression();
        break;
        }
      default:
        jj_la1[9] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
  }

  static final public void call() throws ParseException {
    jj_consume_token(LPARENT);
    call_arguments();
    jj_consume_token(RPARENT);
  }

  static final public void call_arg_list() throws ParseException {
    expression();
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case COMMA:{
        ;
        break;
        }
      default:
        jj_la1[10] = jj_gen;
        break label_6;
      }
      jj_consume_token(COMMA);
      expression();
    }
  }

  static final public void call_arguments() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LPARENT:
    case AMPERSAND:
    case PLUS:
    case MINUS:
    case TIMES:
    case ID:
    case CHAR_CONST:
    case STRING_CONST:
    case INTEGER_CONST:
    case DOUBLE_CONST:{
      call_arg_list();
      break;
      }
    default:
      jj_la1[11] = jj_gen;

    }
  }

  static final public void expression() throws ParseException {
    logical_or_expr();
  }

  static final public void logical_or_expr() throws ParseException {
    logical_and_expr();
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case OROR:{
        ;
        break;
        }
      default:
        jj_la1[12] = jj_gen;
        break label_7;
      }
      jj_consume_token(OROR);
      logical_and_expr();
    }
  }

  static final public void logical_and_expr() throws ParseException {
    equality_expr();
    label_8:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case ANDAND:{
        ;
        break;
        }
      default:
        jj_la1[13] = jj_gen;
        break label_8;
      }
      jj_consume_token(ANDAND);
      equality_expr();
    }
  }

  static final public void equality_expr() throws ParseException {
    relational_expr();
    label_9:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case EQUALEQUAL:
      case NOTEQUAL:{
        ;
        break;
        }
      default:
        jj_la1[14] = jj_gen;
        break label_9;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case EQUALEQUAL:{
        jj_consume_token(EQUALEQUAL);
        relational_expr();
        break;
        }
      case NOTEQUAL:{
        jj_consume_token(NOTEQUAL);
        relational_expr();
        break;
        }
      default:
        jj_la1[15] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
  }

  static final public void relational_expr() throws ParseException {
    additive_expr();
    label_10:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case LESSEQUAL:
      case GREATEQUAL:
      case LESS:
      case GREAT:{
        ;
        break;
        }
      default:
        jj_la1[16] = jj_gen;
        break label_10;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case LESS:{
        jj_consume_token(LESS);
        break;
        }
      case GREAT:{
        jj_consume_token(GREAT);
        break;
        }
      case LESSEQUAL:{
        jj_consume_token(LESSEQUAL);
        break;
        }
      case GREATEQUAL:{
        jj_consume_token(GREATEQUAL);
        break;
        }
      default:
        jj_la1[17] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      additive_expr();
    }
  }

  static final public void additive_expr() throws ParseException {
    multiplicative_expr();
    label_11:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case PLUS:
      case MINUS:{
        ;
        break;
        }
      default:
        jj_la1[18] = jj_gen;
        break label_11;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case PLUS:{
        jj_consume_token(PLUS);
        multiplicative_expr();
        break;
        }
      case MINUS:{
        jj_consume_token(MINUS);
        multiplicative_expr();
        break;
        }
      default:
        jj_la1[19] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
  }

  static final public void multiplicative_expr() throws ParseException {
    unary_expr();
    label_12:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case TIMES:
      case DIVIDE:
      case PERCENT:{
        ;
        break;
        }
      default:
        jj_la1[20] = jj_gen;
        break label_12;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case TIMES:{
        jj_consume_token(TIMES);
        unary_expr();
        break;
        }
      case DIVIDE:{
        jj_consume_token(DIVIDE);
        unary_expr();
        break;
        }
      case PERCENT:{
        jj_consume_token(PERCENT);
        unary_expr();
        break;
        }
      default:
        jj_la1[21] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
  }

  static final public void unary_expr() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LPARENT:
    case ID:
    case CHAR_CONST:
    case STRING_CONST:
    case INTEGER_CONST:
    case DOUBLE_CONST:{
      primary_expr();
      break;
      }
    case PLUS:{
      jj_consume_token(PLUS);
      unary_expr();
      break;
      }
    case MINUS:{
      jj_consume_token(MINUS);
      unary_expr();
      break;
      }
    case AMPERSAND:{
      jj_consume_token(AMPERSAND);
      unary_expr();
      break;
      }
    case TIMES:{
      jj_consume_token(TIMES);
      unary_expr();
      break;
      }
    default:
      jj_la1[22] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void primary_expr() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case STRING_CONST:{
      jj_consume_token(STRING_CONST);
      break;
      }
    case CHAR_CONST:{
      jj_consume_token(CHAR_CONST);
      break;
      }
    case ID:{
      jj_consume_token(ID);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case LPARENT:{
        call();
        break;
        }
      case LBRACE:{
        jj_consume_token(LBRACE);
        expression();
        jj_consume_token(RBRACE);
        break;
        }
      default:
        jj_la1[23] = jj_gen;

      }
      break;
      }
    case INTEGER_CONST:{
      jj_consume_token(INTEGER_CONST);
      break;
      }
    case DOUBLE_CONST:{
      jj_consume_token(DOUBLE_CONST);
      break;
      }
    case LPARENT:{
      jj_consume_token(LPARENT);
      expression();
      jj_consume_token(RPARENT);
      break;
      }
    default:
      jj_la1[24] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void compound_statement() throws ParseException {
    jj_consume_token(LCURLY);
    statement_list();
    jj_consume_token(RCURLY);
  }

  static final public void statement_list() throws ParseException {
    label_13:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case CHARSTAR:
      case CHARSTARSTAR:
      case LONGSTAR:
      case LONG:
      case VOID:
      case IF:
      case WHILE:
      case DO:
      case FOR:
      case CONTINUE:
      case BREAK:
      case RETURN:
      case DOUBLESTAR:
      case DOUBLE:
      case LCURLY:
      case ID:{
        ;
        break;
        }
      default:
        jj_la1[25] = jj_gen;
        break label_13;
      }
      statement();
    }
  }

  static final public void local_var() throws ParseException {
    var_type();
    local_var_list();
    jj_consume_token(SEMICOLON);
  }

  static final public void local_var_list() throws ParseException {
    jj_consume_token(ID);
    label_14:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case COMMA:{
        ;
        break;
        }
      default:
        jj_la1[26] = jj_gen;
        break label_14;
      }
      jj_consume_token(COMMA);
      jj_consume_token(ID);
    }
  }

  static final public void statement() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ID:{
      jj_consume_token(ID);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case LBRACE:
      case SEMICOLON:
      case EQUAL:{
        assignment();
        jj_consume_token(SEMICOLON);
        break;
        }
      case LPARENT:{
        call();
        jj_consume_token(SEMICOLON);
        break;
        }
      default:
        jj_la1[27] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
      }
    case CHARSTAR:
    case CHARSTARSTAR:
    case LONGSTAR:
    case LONG:
    case VOID:
    case DOUBLESTAR:
    case DOUBLE:{
      local_var();
      break;
      }
    case LCURLY:{
      compound_statement();
      break;
      }
    case IF:{
      jj_consume_token(IF);
      jj_consume_token(LPARENT);
      expression();
      jj_consume_token(RPARENT);
      statement();
      else_optional();
      break;
      }
    case WHILE:{
      jj_consume_token(WHILE);
      jj_consume_token(LPARENT);
      expression();
      jj_consume_token(RPARENT);
      statement();
      break;
      }
    case DO:{
      jj_consume_token(DO);
      statement();
      jj_consume_token(WHILE);
      jj_consume_token(LPARENT);
      expression();
      jj_consume_token(RPARENT);
      jj_consume_token(SEMICOLON);
      break;
      }
    case FOR:{
      jj_consume_token(FOR);
      jj_consume_token(LPARENT);
      jj_consume_token(ID);
      assignment();
      jj_consume_token(SEMICOLON);
      expression();
      jj_consume_token(SEMICOLON);
      jj_consume_token(ID);
      assignment();
      jj_consume_token(RPARENT);
      statement();
      break;
      }
    case CONTINUE:
    case BREAK:
    case RETURN:{
      jump_statement();
      break;
      }
    default:
      jj_la1[28] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void else_optional() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ELSE:{
      jj_consume_token(ELSE);
      statement();
      break;
      }
    default:
      jj_la1[29] = jj_gen;

    }
  }

  static final public void jump_statement() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case CONTINUE:{
      jj_consume_token(CONTINUE);
      jj_consume_token(SEMICOLON);
      break;
      }
    case BREAK:{
      jj_consume_token(BREAK);
      jj_consume_token(SEMICOLON);
      break;
      }
    case RETURN:{
      jj_consume_token(RETURN);
      return_val_opt();
      jj_consume_token(SEMICOLON);
      break;
      }
    default:
      jj_la1[30] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void return_val_opt() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LPARENT:
    case AMPERSAND:
    case PLUS:
    case MINUS:
    case TIMES:
    case ID:
    case CHAR_CONST:
    case STRING_CONST:
    case INTEGER_CONST:
    case DOUBLE_CONST:{
      expression();
      break;
      }
    default:
      jj_la1[31] = jj_gen;

    }
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public SimpleCTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[32];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0xffffffe0,0xffffffe0,0xc03e0,0x60800000,0x20000000,0xc03e0,0x20000000,0xc03e0,0x2000000,0x2000000,0x20000000,0x800000,0x80000000,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x800000,0x2800000,0x800000,0x80ff7e0,0x20000000,0x42800000,0x80ff7e0,0x800,0x38000,0x800000,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x1f7ffff,0x1f7ffff,0x0,0x0,0x0,0x0,0x0,0x0,0x20,0x20,0x0,0x7ce40,0x0,0x1,0x6,0x6,0x198,0x198,0x600,0x600,0x3800,0x3800,0x7ce40,0x0,0x7c000,0x4000,0x0,0x20,0x4000,0x0,0x0,0x7ce40,};
   }

  /** Constructor with InputStream. */
  public SimpleC(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public SimpleC(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new SimpleCTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 32; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 32; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public SimpleC(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new SimpleCTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 32; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 32; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public SimpleC(SimpleCTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 32; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(SimpleCTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 32; i++) jj_la1[i] = -1;
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk_f() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[57];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 32; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 57; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

}
