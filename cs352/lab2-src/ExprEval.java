
import java.lang.*;
import java.util.regex.*;
import java.util.*;

// enum TokenTypes 
// {
//     PLUS("\\+"),
//     MINUS("-"),
//     TIMES("\\*"),
//     DIVIDE("/"),
//     ID("x"),
//     INT("[0-9]+"),
//     LPAREN("\\("),
//     RPAREN("\\)"),
//     EXP("\\^"),
//     COMMA(","),
//     Function("sin\\(|cos\\(|tan\\(|atan\\(|atan2\\(|ln")
// };

class ExprEval {
  //i is the counter
  public static int count = 0;
  public static TokenClass t;
  public static Lexer lexerParser;
  public static double xvalue;

  public static int count_comma = 0;
  public static int count_atan2 = 0;


  public static void main(String args[]) throws Exception {
    if (args.length < 2) {
      System.out.println("Usage: java expression xval");
      System.exit(1);
    }
    double val = evaluate(args[0], Double.parseDouble(args[1]));
    if (count >=  lexerParser.tokens.size() - 1)
      System.out.println("y="+val);
    else
      throw new Exception("Bad format");
  }


  public static double evaluate(String expr, double xval) throws Exception {


    lexerParser = new Lexer(expr);
    xvalue = xval;
    //lexerParser.printTokens();
    // for (int i = 0; i < lexerParser.tokens.size(); i++)
    // {
    //   if (lexerParser.tokens.get(i).data.equals(","))
    //   {
    //       count_comma++;
    //       if (count_comma > count_atan2)
    //           throw new Exception("Bad format");
    //   }
    //   if (lexerParser.tokens.get(i).data.equals("atan2("))
    //       count_atan2++;

      // if (lexerParser.tokens.get(i).type.equals(TokenType.ID) || lexerParser.tokens.get(i).type.equals(TokenType.INT) 
      //     || lexerParser.tokens.get(i).type.equals(TokenType.DOUBLE))
      //     if (i >= 1 && i <= (lexerParser.tokens.size() - 2))
      //     {
      //         if (lexerParser.tokens.get(i - 1).type.equals(TokenType.DOUBLE)||lexerParser.tokens.get(i - 1).type.equals(TokenType.INT)
      //           ||lexerParser.tokens.get(i - 1).data.equals("ln")||lexerParser.tokens.get(i - 1).equals(TokenType.ID)
      //           ||lexerParser.tokens.get(i - 1).data.equals(")")
      //           ||lexerParser.tokens.get(i + 1).type.equals(TokenType.DOUBLE)||lexerParser.tokens.get(i + 1).type.equals(TokenType.INT)
      //           ||lexerParser.tokens.get(i + 1).type.equals(TokenType.FUNCTION)||lexerParser.tokens.get(i + 1).type.equals(TokenType.ID)
      //           ||lexerParser.tokens.get(i + 1).data.equals("("))
      //         {
      //             throw new Exception("Bad format");    
      //         }
      //     }
      //     else if (lexerParser.tokens.size() > 1 && i == (lexerParser.tokens.size() - 1))
      //     {
      //          if (lexerParser.tokens.get(i - 1).type.equals(TokenType.DOUBLE)||lexerParser.tokens.get(i - 1).type.equals(TokenType.INT)
      //             ||lexerParser.tokens.get(i - 1).data.equals("ln")||lexerParser.tokens.get(i - 1).type.equals(TokenType.ID)
      //             ||lexerParser.tokens.get(i - 1).data.equals(")"))
      //          {
      //             throw new Exception("Bad format");
      //          }

      //     }
    //}
    //if (count_comma != count_atan2)
    //    throw new Exception("Bad format");

    return expr();

  }

public static TokenClass nextToken() throws Exception
{
  if ((count + 1) < lexerParser.tokens.size())
  {
      TokenClass nextTok = m
  }
  else
      return null;
  // if (i < lexerParser.tokens.size())
  // {
  //   i--;
  //   return lexerParser.tokens.get(i);
  // }
  // else
  // {
  //     i--;
  //     return null;
  // }
  
}

public static double expr() throws Exception
{
  //Parse the first term

  double result = term();
  //System.out.println(result);
  TokenClass next_token = nextToken();
  if (next_token == null)
      return result;
  while (next_token.type.equals(TokenType.PLUS) 
      || next_token.type.equals(TokenType.MINUS))
  {
    //System.out.println(result + " 83");
    count = count + 2;
    if (count >= lexerParser.tokens.size())
        throw new Exception("Bad format");
    if (next_token.type.equals(TokenType.PLUS))
        result = result + term();
    else if (next_token.type.equals(TokenType.MINUS))
        result = result - term();
    else 
        throw new Exception("Code error in expr");  

    next_token = nextToken();
    if (next_token == null)
        return result;

  }
  return result;
}

public static double term() throws Exception
{
  // Function term
  // Parse the first factor
  double result = exponent();
  TokenClass next_token = nextToken();
  if (next_token == null)
      return result;
  while (next_token.type.equals(TokenType.TIMES)
     ||  next_token.type.equals(TokenType.DIVIDE))
  {
      count = count + 2;
      if (count >= lexerParser.tokens.size())
          throw new Exception("Bad format");
      if (next_token.type.equals(TokenType.TIMES))
          result = result * exponent();
      else if (next_token.type.equals(TokenType.DIVIDE))
          result = result / exponent();
      else 
          throw new Exception("Code error in term");

      next_token = nextToken();
      if (next_token == null)
          return result;
  }
  return result;
}

//deal with 'sign ^'
public static double exponent() throws Exception
{
    double result = factor();
    TokenClass next_token = nextToken();
    if (next_token == null)
        return result;
    while (next_token.type.equals(TokenType.EXPO))
    {
        count = count + 2;
        if (count >= lexerParser.tokens.size())
            throw new Exception("Bad format");
        result = Math.pow(result, factor()); 

        next_token = nextToken();
        if (next_token == null)
            return result;   
    }
    return result;

}

//Function factor
public static double factor() throws Exception
{
    TokenClass next_token = lexerParser.tokens.get(count);
    if (next_token.type.equals(TokenType. ID))
    {
        return xvalue;
    }
    else if (next_token.type.equals(TokenType. INT)
        ||   next_token.type.equals(TokenType. DOUBLE))
    {
        return Double.parseDouble(next_token.data);
    }
    else if (next_token.type.equals(TokenType.FUNCTION))
    {
        if (next_token.data.equals("sin("))
        {
            count++;
            double temp_value = expr();

            if (nextToken().type.equals(TokenType.RPAREN))
                count++; 
            else
                throw new Exception("Bad format");
            return Math.sin(temp_value);
        }
        else if (next_token.data.equals("cos("))
        {
            count++;
            double temp_value = expr();

            if (nextToken().type.equals(TokenType.RPAREN))
                count++; 
            else
                throw new Exception("Bad format");
            return Math.cos(temp_value);

        }
        else if (next_token.data.equals("tan("))
        {
            count++;
            double temp_value = expr();

            if (nextToken().type.equals(TokenType.RPAREN))
                count++; 
            else
                throw new Exception("Bad format");
            return Math.tan(temp_value);
        }
        else if (next_token.data.equals("atan("))
        {
            count++;
            double temp_value = expr();

            if (nextToken().type.equals(TokenType.RPAREN))
                count++; 
            else
                throw new Exception("Bad format");
            return Math.atan(temp_value);
        }
        else if (next_token.data.equals("atan2("))
        {
            count++;
            double temp_value = expr();
            
            if (nextToken().type.equals(TokenType.COMMA)) 
            {
                count = count + 2;
                double temp_value2 = expr();
                if (nextToken().type.equals(TokenType.RPAREN))
                    count++;
                else
                    throw new Exception("Bad format");
                return Math.atan2(temp_value, temp_value2);    
            }
            else
                throw new Exception("Bad format");
        }
        else if (next_token.data.equals("ln("))
        {
            count++;
            double temp_value = expr();

            if (nextToken().type.equals(TokenType.RPAREN))
                count++; 
            else
                throw new Exception("Bad format");
            return Math.log(temp_value);
        }
        else 
            throw new Exception("Bad format");    
    }   

    else if (next_token.type.equals(TokenType.LPAREN))
    {
        count++;
        double temp_value = expr();
        //System.out.println(nextToken());
        if (nextToken().type.equals(TokenType.RPAREN))
             count++; 
        else
            throw new Exception("Bad format");
        return temp_value;
    }
    else
    {
        throw new Exception("Bad format");
    }
    
}

 }


