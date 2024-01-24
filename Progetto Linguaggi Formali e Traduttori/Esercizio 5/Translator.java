import java.io.*;

public class Translator {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;
    
    SymbolTable st = new SymbolTable();
    CodeGenerator code = new CodeGenerator();
    int count=0;

    public Translator(Lexer l, BufferedReader br) {
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
        int lnext_prog = code.newLabel();
        statlist(lnext_prog);
        code.emitLabel(lnext_prog);
        match(Tag.EOF);
        try {
        	code.toJasmin();
        }
        catch(java.io.IOException e) {
        	System.out.println("IO error\n");
        };
	}
	else error("Error in prog");
    }
	
	public void statlist(int lnext_statlist) { //parametro: etichetta a cui andare alla fine
	if(look.tag == '=' || look.tag == Tag.PRINT || look.tag == Tag.READ || look.tag == Tag.COND
	   || look.tag == Tag.WHILE || look.tag == '{') {
		stat();
		statlistp();
		code.emit(OpCode.GOto , lnext_statlist);
	}
	else error("Error in statlist");
	}
	
	public void statlistp() { //uguale al parser
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
				if (look.tag==Tag.ID) { 
                    int id_addr = st.lookupAddress(((Word)look).lexeme); //controllo se l'ID esiste già
                    if (id_addr==-1) {     //se non esiste
                        id_addr = count;   //lo creo
                        st.insert(((Word)look).lexeme,count++);
                    }                    
                    match(Tag.ID);
					expr();                                //calcolo il valore dell'espressione
					code.emit(OpCode.istore , id_addr);    //e la salvo nell'ID specificato
				}
				else
                    error("Error in grammar (stat) after read( with " + look);
			    break;
				
			case Tag.PRINT:
			    match(Tag.PRINT);
				match('(');
				int i = exprlist(); //i c'è solo perché exprlist ritorna un intero, in questo caso non serve
				match(')');
				code.emit(OpCode.invokestatic,1);
			    break;
				
            case Tag.READ:
                match(Tag.READ);
                match('(');
                if (look.tag==Tag.ID) {  //controllo presenza ID, come sopra
                    int id_addr = st.lookupAddress(((Word)look).lexeme);
                    if (id_addr==-1) {
                        id_addr = count;
                        st.insert(((Word)look).lexeme,count++);
                    }                    
                    match(Tag.ID);
                    match(')');
                    code.emit(OpCode.invokestatic,0); //invoco read
                    code.emit(OpCode.istore,id_addr); //e salvo ciò che leggo nell'ID specificato
                }
                else
                    error("Error in grammar (stat) after read( with " + look);
                break;
				
			case Tag.COND:
			    int lnext_stat = code.newLabel(); //etichetta a cui saltano i whenitem di whenlist
			    match(Tag.COND);                  //se la loro condizione si verifica dopo che il loro corpo
				whenlist(lnext_stat);             //viene eseguito
				match(Tag.ELSE);                  //se nessuno dei whenitem si verifica, viene eseguita stat()
				stat();
				code.emitLabel(lnext_stat);
			    break;
				
			case Tag.WHILE:
			    match(Tag.WHILE);
			    match('(');
				int lbexpr_if = code.newLabel();   //etichetta che indica l'inizio del ciclo
				int lbexpr_then = code.newLabel(); //etichetta a cui si salta se la condizione del ciclo si verifica
				int l_end = code.newLabel();       //etichetta a cui si salta a fine ciclo
				code.emitLabel(lbexpr_if);         //all'inizio di ogni ciclo torno qua per verificare la condizione
				bexpr(lbexpr_then);                //verifica condizione
				match(')');
				code.emit(OpCode.GOto , l_end);    //se bexpr() non è vera, esco dal ciclo
				code.emitLabel(lbexpr_then);       //se bexpr() si verifica, salto qua
				stat();                            //corpo del ciclo
				code.emit(OpCode.GOto , lbexpr_if);//dopo aver eseguito stat() torno a prima di bexpr()
				code.emitLabel(l_end);             //fine ciclo
			    break;
				
			case '{':
			    int lnext_statlist = code.newLabel();
			    match('{');
				statlist(lnext_statlist);
				match('}');
				code.emitLabel(lnext_statlist);
			    break;
				
			default:
			    error("Error in stat");
        }
     }
	 
	public void whenlist(int lnext_whenlist) {     //il parametro è l'etichetta a cui salto dopo che uno dei
	if(look.tag == Tag.WHEN) {                     //whenitem si verifica
		int lnext_whenitem = code.newLabel();      //etichetta a cui salta il whenitem se la sua condizione
		whenitem(lnext_whenitem , lnext_whenlist); //non si verifica (cioè è l'etichetta del whenitem successivo)
		whenlistp(lnext_whenlist);
	}
	else error("Error in whenlist");
	}
	
	public void whenlistp(int lnext_whenlistp) {  //come per whenlist
	switch(look.tag) {
	case Tag.WHEN:
	    int lnext_whenitem = code.newLabel();
	    whenitem(lnext_whenitem , lnext_whenlistp);
		whenlistp(lnext_whenlistp);
		break;
	case Tag.ELSE:   //quando leggo ELSE, non faccio niente, vado automaticamente all'istruzione successiva, che,
	    break;       //all'interno di una COND, è lo stat() presente dopo ELSE
	default:
	    error("Error in whenlistp");
	}	
	}
	
	public void whenitem(int lnext_whenitem, int lnext_stat) { //1° parametro: whenitem successivo della lista
	if(look.tag == Tag.WHEN) {                                 //2° parametro: uscita dalla serie di whenitem
		match(Tag.WHEN);
		match('(');
		int lnext_bexpr = code.newLabel();       //etichetta a cui salto se la condizione di bexpr è vera
		bexpr(lnext_bexpr);                      //verifica della condizione
		code.emit(OpCode.GOto , lnext_whenitem); //se non si verifica la condizione, salto al prossimo whenitem (o a stat() se questo è l'ultimo elemento della lista)
		code.emitLabel(lnext_bexpr);             //salto qui se bexpr è vera
		match(')');                              
		match(Tag.DO);
		stat();                                  //corpo del whenitem
		code.emit(OpCode.GOto , lnext_stat);     //dopo aver eseguito il corpo, esco dalla whenlist
		code.emitLabel(lnext_whenitem);          //emetto l'etichetta del prossimo elemento della lista
	}
	else error("Error in whenitem");
	}
	
	public void bexpr(int lnext_bexpr) {
	if(look.tag == Tag.RELOP) {
		String symbol = ((Word)look).lexeme; //salvo il tipo di RELOP in una variabile per dopo
		match(Tag.RELOP);
		expr();                              //calcolo le 2 espressioni da valutare
		expr();
		switch(symbol) {                     //uso la stringa salvata prima per decidere che tipo di salto emettere
			case "<":
			    code.emit(OpCode.if_icmplt , lnext_bexpr);
				break;
			case ">":
			    code.emit(OpCode.if_icmpgt , lnext_bexpr);
				break;
			case "==":
			    code.emit(OpCode.if_icmpeq , lnext_bexpr);
				break;
			case "<=":
			    code.emit(OpCode.if_icmple , lnext_bexpr);
				break;
			case "<>":
			    code.emit(OpCode.if_icmpne , lnext_bexpr);
				break;
			case ">=":
			    code.emit(OpCode.if_icmpge , lnext_bexpr);
			default:
			    error("Error: symbol doesn't exist");
		}
	}
	else error("Error in bexpr");
	}

    public void expr() {
        switch(look.tag) {
			case '+':
	            match('+');
		        match('(');
		        int i = exprlist(); //numero di elementi della exprlist oltre al primo
		        match(')');
				while( i>0 ){    //emetto un numero di 'iadd' pari al numero di elementi della lista oltre al primo
				    code.emit(OpCode.iadd);
					i--;
				}
		        break;
				
            case '-':
                match('-');
                expr();
                expr();
                code.emit(OpCode.isub);
                break;
				
			case '*':
	            match('*');
		        match('(');
		        int j=exprlist(); //vedi caso di 'iadd'
		        match(')');
				while( j>0 ) {
				    code.emit(OpCode.imul);
					j--;
				}
		        break;
			case '/':
   			    match('/');
                expr();
                expr();
                code.emit(OpCode.idiv);
                break;
			case Tag.NUM:
			    code.emit(OpCode.ldc , ((NumberTok)look).lexeme);
	            match(Tag.NUM);
		        break;
	        case Tag.ID:
				if (look.tag==Tag.ID) {  //verifica esistenza dell'ID ed eventuale aggiunta se non esiste
                    int id_addr = st.lookupAddress(((Word)look).lexeme);
                    if (id_addr==-1) {
                        id_addr = count;
                        st.insert(((Word)look).lexeme,count++);
                    }
					match(Tag.ID);
				    code.emit(OpCode.iload , id_addr);	
				}
				else
                    error("Error in grammar (expr) after read( with " + look);
		        break;
	        default:
	            error("Error in expr");
        }
    }
	
	public int exprlist(){
		if(look.tag == '+' || look.tag == '-' || look.tag == '*' || look.tag == '/'
	   || look.tag == Tag.NUM || look.tag == Tag.ID) {
		    expr(); 
		    return exprlistp();
	    }
	    else error("Error in exprlist");
        return 0;		
	}
	
	public int exprlistp() {
	    switch(look.tag) {
	    case '+':
	    case '-':
	    case '*':
	    case '/':
	    case Tag.NUM:
	    case Tag.ID:
	        expr();
		    return 1+exprlistp(); //ogni volta che chiamo exprlistp() aggiungo 1 al conteggio degli operandi
	    case ')':
			return 0;            //quando finiscono gli exprlistp faccio partire il conteggio ritornando 0
	    default:
	        error("Error in exprlistp");
			return 0;
	    }	
	}
	
	public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "Prova.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Translator translator = new Translator(lex, br);
            translator.prog();
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}