
import java.io.InputStream;
import java.io.InputStreamReader;

class FloatParser {
  public static void main(String args[]) throws Exception {
  if (args.length==0) {
            System.out.println("Usage: java NumberParser value");
    System.exit(1);
        }
        double val = MyParseFloat(args[0]);
  System.out.println("Value="+val);
  }

  enum StateFloat { SSTART, SSIGN, SINTEGER, SDECIMAL, STEXPNOTE, SSIGN2, STEXPNUM, SEND };

  public static double MyParseFloat(String s) throws Exception {
    // Using the code in DecimalParser.java write a finite state 
    // machine that parses a floating point number of the form 
    //             [-+]?[0-9]*\.?[0-9]+([eE][-+]?[0-9]+)? 
 
    //add code
    StateFloat state;

    state = StateFloat.SSTART;

    int i = 0;
    double divider = 10;
    double value = 0;
    //declare my own variable here
    //if the number is non negative, sign will be true
    boolean sign = true;
    //if the exponent part is nonnegative, signexp will be true;
    boolean signexp = true;
    //the exponent index and is initially 0
    double expIndex = 0;
    //whether e exist or not
    //boolean expSign = false;
    boolean indicator = false;

    if (s.length() == 0)
        throw new Exception("Bad format");

    while ( i < s.length() && state != StateFloat.SEND) {
  char ch = s.charAt(i);
      switch (state) {
        case SSTART: 
      if (Character.isDigit(ch)) {
        state = StateFloat.SINTEGER;
        value = Character.getNumericValue(ch);  
        i++;
      }
      else if (ch == '.')
      {
        state = StateFloat.SDECIMAL;
        i++;
        if (i == s.length())
        {
          throw new Exception("Bad format");
        }
      }
      else if (ch == '+' || ch == '-')
      {
        state = StateFloat.SSIGN;
        i++;
        if (ch == '-')
        {
          sign = false;
        }
        if (i == s.length())
        {
          throw new Exception("Bad format");
        }
      }
      else {
        throw new Exception("Bad format");
      }
      break;
    //add new state
    case SSIGN:
      if (ch == '.')
      {
        state = StateFloat.SDECIMAL;
        i++;
        if (i == s.length())
        {
          throw new Exception("Bad format");
        }
      }
      else if (Character.isDigit(ch)) 
      {
        state = StateFloat.SINTEGER;
        value = Character.getNumericValue(ch); 
        i++;
      }
      else
      {
        throw new Exception("Bad format");
      }
      break;
    //add new state end
    case SINTEGER:
      if (ch == 'E' || ch == 'e')
      {
        state = StateFloat.STEXPNOTE;
        i++;
        if (i == s.length())
        {
          throw new Exception("Bad format");
        }
      }
      else if (Character.isDigit(ch)) {
                                state = StateFloat.SINTEGER;
                                value = 10.0*value + Character.getNumericValue(ch);
                                i++;
                        }
      else if (ch == '.') {
        state = StateFloat.SDECIMAL;
        i++;
        if (i == s.length())
        {
          throw new Exception("Bad format");
        }  
      }
      else 
      {
        throw new Exception("Bad format");
      }
      break;
    case SDECIMAL:
      //System.out.println(123);
      if (Character.isDigit(ch)) {
                                value = value + Character.getNumericValue(ch)/divider;
        divider = divider * 10;
                                i++;
        indicator = true;
                        }
      else if (ch == 'E' || ch == 'e')
      {
        if (indicator == false)
        {
          throw new Exception("Bad format");
        }
        state = StateFloat.STEXPNOTE;
        i++;
        if (i == s.length())
        {
          throw new Exception("Bad format");
        }
      } 
      else {
        throw new Exception("Bad format");
      }
      break;
      
    //add new state
    case STEXPNOTE:
      if (ch == '+' || ch == '-')
      {
        state = StateFloat.SSIGN2;
        i++;
        if (ch== '-')
        {
          signexp = false;
        }
        if (i == s.length())
        {
          throw new Exception("Bad format");
        }
      }
      else if (Character.isDigit(ch))
      {
        state = StateFloat.STEXPNUM;
        expIndex = Character.getNumericValue(ch);  
        i++;
      }
      else 
      {
        throw new Exception("Bad format");
      }
      break;

      case SSIGN2:
      if (Character.isDigit(ch)) 
      {
        state = StateFloat.STEXPNUM;
        expIndex = Character.getNumericValue(ch); 
        i++;
        //System.out.println(expIndex);
      }
      else
      {
        throw new Exception("Bad format");
      }
      break;

      case STEXPNUM:
      if (Character.isDigit(ch)) 
      {
        state = StateFloat.STEXPNUM;
        expIndex = 10.0*expIndex + Character.getNumericValue(ch);
        i++;
        //System.out.println(expIndex);
      }
      else
      {
        throw new Exception("Bad format");
      }
      break;
    //add new state end
  }
}

    //check sign
    if (sign == false)
    {
      value = -value;
    }

    if (signexp == true)
      value *= Math.pow(10, expIndex);
    else
      value *= Math.pow(10, (expIndex * (-1)));

    return value;
    //add code end
  }

}


