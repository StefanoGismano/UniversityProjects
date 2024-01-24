import java.io.*; 
import java.util.*;

public class Lexer {

    public static int line = 1;
    private char peek = ' ';
    
    private void readch(BufferedReader br) {
        try {
            peek = (char) br.read();
        } catch (IOException exc) {
            peek = (char) -1; // ERROR
        }
    }

    public Token lexical_scan(BufferedReader br) {
        while (peek == ' ' || peek == '\t' || peek == '\n'  || peek == '\r') {
            if (peek == '\n') line++;
            readch(br);
        }
        
        switch (peek) {
            case '!':
                peek = ' ';
                return Token.not;
				
			case '(':
			    peek = ' ';
				return Token.lpt;
				
			case ')':
			    peek = ' ';
				return Token.rpt;
				
			case '{':
			    peek = ' ';
				return Token.lpg;
				
			case '}':
  			    peek = ' ';
				return Token.rpg;
				
			case '+':
			    peek = ' ';
				return Token.plus;
				
			case '-':
			    peek = ' ';
				return Token.minus;
				
			case '*':
			    peek = ' ';
				return Token.mult;
				
			case '/':
                peek = ' ';    
				return Token.div;
				
				
			case ';':
			    peek = ' ';
				return Token.semicolon;	

            case '&':
                readch(br);
                if (peek == '&') {
                    peek = ' ';
                    return Word.and;
                } else {
                    System.err.println("Erroneous character"
                            + " after & : "  + peek );
                    return null;
                }
			
			case '|':
			    readch(br);
				if (peek == '|') {
					peek = ' ';
					return Word.or;
				} else {
					System.err.println("Erroneous character"
					        + " after | : " + peek );
					return null;		
				}
				
			case '<':
			    readch(br);
				if (peek == '=') {
					peek = ' ';
					return Word.le;
				} else if(peek == '>') {
					peek = ' ';
					return Word.ne;
				} else {
					return Word.lt;
				}
				
			case '>':
			    readch(br);
				if(peek == '=') {
					peek = ' ';
					return Word.ge;
			    } else {
				    return Word.gt;
			    }
			
			case '=':
			    readch(br);
				if(peek == '=') {
					peek = ' ';
					return Word.eq;
			    } else {
				    return Token.assign;
			    }
          
            case (char)-1:
                return new Token(Tag.EOF);

            default:
			
                if (Character.isLetter(peek)) {
					String parola="";    //leggo lettere, cifre e '_' e le salvo in una stringa
					do{ 
						parola+=peek;
						readch(br);
					} while(Character.isLetter(peek) || Character.isDigit(peek) || peek == '_');
					
					switch(parola){             //confronto la stringa con le parole chiave
						
						case "cond":
						return Word.cond;
						
						case "when":
						return Word.when;
						
						case "then":
						return Word.then;
						
						case "else":
						return Word.elsetok;
						
						case "while":
						return Word.whiletok;
						
						case "do":
						return Word.dotok;
						
						case "seq":
						return Word.seq;
						
						case "print":
						return Word.print;
						
						case "read":
						return Word.read;
						
						default:
						return new Word(Tag.ID, parola); //se non è una parola chiave allora
					}                                    //è un identificatore 
                }
				
                else if(peek == '_'){
					String parola = ""; //stringa che conterrà l'identificatore
					int i=0;            //contatore di caratteri presenti nell'identificatore
					while(peek=='_' || Character.isDigit(peek) || Character.isLetter(peek)){
						if(peek != '_') //se il prossimo carattere non è '_' allora aumento i 
						    i++;
						parola+=peek;
						readch(br);
					}
					if(i==0){ //alla fine del ciclo, se ho letto solo '_', l'ID non è valido
						System.err.println("Error: IDs must contain characters different from '_'");
						return null;
					} else return new Word(Tag.ID, parola); //altrimenti lo ritorno
				}
				
				else if (Character.isDigit(peek)) {
					String numero=""; //stringa che conterrà il numero letto
					int i=0;          //contatore di cifre presenti nel numero
					do{
						numero+=peek;
						if(i==0 && peek == '0'){ //se la prima cifra è 0...
							readch(br);          //e ci sono altre cifre dopo di essa
							if(Character.isDigit(peek)){
								System.err.println("Error: numbers can't start with '0'");
								return null;     //allora è un errore
							}
							else return new NumberTok(Tag.NUM, 0); //altrimenti ritorno 0
						} else                 //se la prima cifra non è 0 semplicemente leggo
						readch(br);            //le cifre finché non sono finite
						i++;
					} while(Character.isDigit(peek));
					                           //uso una funzione che prende una stringa di
                                               //caratteri e ritorna l'intero corrispondente											   
					return new NumberTok(Tag.NUM, Integer.parseInt(numero));
					
                } else {
                        System.err.println("Erroneous character: " 
                                + peek );
                        return null;
                }
         }
    }
	
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "Prova.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Token tok;
            do {
                tok = lex.lexical_scan(br);
                System.out.println("Scan: " + tok);
            } while (tok.tag != Tag.EOF);
            br.close();
        } catch (IOException e) {e.printStackTrace();}    
    }

}