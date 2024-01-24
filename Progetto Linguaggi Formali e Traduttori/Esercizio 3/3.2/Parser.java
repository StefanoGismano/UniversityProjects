import java.io.*;

public class Parser {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Parser(Lexer l, BufferedReader br) {
        lex = l;
        pbr = br;
        move();
    }

    void move() {
        look = lex.lexical_scan(pbr);
        System.out.println("token = " + look);
    }

    void error(String s) {
	throw new Error("near line " + lex.line + ": " + s);
    }

    void match(int t) {
	if (look.tag == t) {
	    if (look.tag != Tag.EOF) move();
	} else error("syntax error");
    }

    public void prog() {
	if(look.tag == '=' || look.tag == Tag.PRINT || look.tag == Tag.READ || look.tag == Tag.COND
	   || look.tag == Tag.WHILE || look.tag == '{') {
		statlist();
		match(Tag.EOF);
	}
	else error("Error in prog");
    }

    public void statlist() {
	if(look.tag == '=' || look.tag == Tag.PRINT || look.tag == Tag.READ || look.tag == Tag.COND
	   || look.tag == Tag.WHILE || look.tag == '{') {
		stat();
		statlistp();
	}
	else error("Error in statlist");
	}
	
	public void statlistp() {
	switch(look.tag) {
	case ';':
	    match(';');
		stat();
		statlistp();
		break;
	case '}':
	case Tag.EOF:
	    break;
	default:
        error("Error in statlistp");
	}
	}
	
	public void stat() {
	switch(look.tag) {
	case '=':
	    match('=');
		match(Tag.ID);
		expr();
		break;
	case Tag.PRINT:
	    match(Tag.PRINT);
		match('(');
		exprlist();
		match(')');
		break;
	case Tag.READ:
	    match(Tag.READ);
		match('(');
		match(Tag.ID);
		match(')');
		break;
	case Tag.COND:
	    match(Tag.COND);
		whenlist();
		match(Tag.ELSE);
		stat();
		break;
	case Tag.WHILE:
	    match(Tag.WHILE);
		match('(');
		bexpr();
		match(')');
		stat();
		break;
	case '{':
	    match('{');
		statlist();
		match('}');
		break;
	default:
	    error("Error in stat");
	}	
	}
	
	public void whenlist() {
	if(look.tag == Tag.WHEN) {
		whenitem();
		whenlistp();
	}
	else error("Error in whenlist");
	}
	
	public void whenlistp() {
	switch(look.tag) {
	case Tag.WHEN:
	    whenitem();
		whenlistp();
		break;
	case Tag.ELSE:
	    break;
	default:
	    error("Error in whenlistp");
	}	
	}
	
	public void whenitem() {
	if(look.tag == Tag.WHEN) {
		match(Tag.WHEN);
		match('(');
		bexpr();
		match(')');
		match(Tag.DO);
		stat();
	}
	else error("Error in whenitem");
	}
	
	public void bexpr() {
	if(look.tag == Tag.RELOP) {
		match(Tag.RELOP);
		expr();
		expr();
	}
	else error("Error in bexpr");
	}
	
	public void expr() {
	switch(look.tag) {
	case '+':
	    match('+');
		match('(');
		exprlist();
		match(')');
		break;
	case '-':
	    match('-');
		expr();
		expr();
		break;
	case '*':
	    match('*');
		match('(');
		exprlist();
		match(')');
		break;
	case '/':
	    match('/');
		expr();
		expr();
		break;
	case Tag.NUM:
	    match(Tag.NUM);
		break;
	case Tag.ID:
	    match(Tag.ID);
		break;
	default:
	    error("Error in expr");
	}	
	}
	
	public void exprlist() {
	if(look.tag == '+' || look.tag == '-' || look.tag == '*' || look.tag == '/'
	   || look.tag == Tag.NUM || look.tag == Tag.ID) {
		expr();
		exprlistp();
	    }
	else error("Error in exprlist");	
	}
	
	public void exprlistp() {
	switch(look.tag) {
	case '+':
	case '-':
	case '*':
	case '/':
	case Tag.NUM:
	case Tag.ID:
	    expr();
		exprlistp();
		break;
	case ')':
	    break;
	default:
	    error("Error in exprlistp");
	}	
	}
		
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "Prova.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser parser = new Parser(lex, br);
            parser.prog();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}