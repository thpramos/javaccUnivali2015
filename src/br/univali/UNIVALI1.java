/* Generated By:JavaCC: Do not edit this line. UNIVALI1.java */
package br.univali;

import java.io.*;
import java.util.ArrayList;


public class UNIVALI1 implements UNIVALI1Constants {

  final public String input() throws ParseException {
StringBuilder s = new StringBuilder("");
    try {
      label_1:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case CONSTANTE_NUMERICA_INTEIRA:
        case CONSTANTE_NUMERICA_REAL:
        case EXPRESSAO_REGULAR:
        case SIMBOLOS:
        case CONSTANTE_LITERAL:
        case IDENTIFICADOR:
        case OPERADOR:
          ;
          break;
        default:
          jj_la1[0] = jj_gen;
          break label_1;
        }
        identifica(s);
      }
            {if (true) return s.toString();}
    } catch (TokenMgrError e) {
                error_skipto(PONTO);
    } catch (ParseException e) {
        error_skipto(PONTO);
    }
    throw new Error("Missing return statement in function");
  }

  void error_skipto(int kind) throws ParseException {
  ParseException e = generateParseException();  // generate the exception object.
  System.out.println(e.toString());  // print the error message
  Token t;
  do {
    t = getNextToken();
  } while (t.kind != kind);
  }

  final public void identifica(StringBuilder s) throws ParseException {
  Token k;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case EXPRESSAO_REGULAR:
      k = jj_consume_token(EXPRESSAO_REGULAR);
    s.append("\u005cn Palavra reservada -> " + k.image +
    ", na linha " + k.beginLine + " e na coluna " + k.beginColumn);
      break;
    case CONSTANTE_NUMERICA_INTEIRA:
      k = jj_consume_token(CONSTANTE_NUMERICA_INTEIRA);
    s.append("\u005cn Constante inteira -> " + k.image +
    ", na linha " + k.beginLine + " e na coluna " + k.beginColumn);
      break;
    case CONSTANTE_NUMERICA_REAL:
      k = jj_consume_token(CONSTANTE_NUMERICA_REAL);
    s.append("\u005cn Constante real -> " + k.image +
    ", na linha " + k.beginLine + " e na coluna " + k.beginColumn);
      break;
    case CONSTANTE_LITERAL:
      k = jj_consume_token(CONSTANTE_LITERAL);
    s.append("\u005cn Constante literal -> " + k.image +
    ", na linha " + k.beginLine + " e na coluna " + k.beginColumn);
      break;
    case IDENTIFICADOR:
      k = jj_consume_token(IDENTIFICADOR);
    s.append("\u005cn Identificador -> " + k.image +
    ", na linha " + k.beginLine + " e na coluna " + k.beginColumn);
      break;
    case OPERADOR:
      k = jj_consume_token(OPERADOR);
    s.append("\u005cn Operador -> " + k.image +
    ", na linha " + k.beginLine + " e na coluna " + k.beginColumn);
      break;
    case SIMBOLOS:
      k = jj_consume_token(SIMBOLOS);
    s.append("\u005cn Simbolo Especial -> " + k.image +
    ", na linha " + k.beginLine + " e na coluna " + k.beginColumn);
      break;
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  /** Generated Token Manager. */
  public UNIVALI1TokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[2];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x1980,0x1980,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x12800,0x12800,};
   }

  /** Constructor with InputStream. */
  public UNIVALI1(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public UNIVALI1(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new UNIVALI1TokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public UNIVALI1(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new UNIVALI1TokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public UNIVALI1(UNIVALI1TokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(UNIVALI1TokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
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
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[52];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 2; i++) {
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
    for (int i = 0; i < 52; i++) {
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
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}