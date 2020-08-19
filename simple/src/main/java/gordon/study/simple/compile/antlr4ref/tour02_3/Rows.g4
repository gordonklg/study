grammar Rows;

@parser::members { // add members to generated RowsParser
    int col;
    public RowsParser(TokenStream input, int col) { // custom constructor
        this(input);
        this.col = col;
    }
}

file: (row NL)+ ;

row
locals [int i=0] // added to RowContext // public int i = 0;
    : (   STUFF
          {
          $i++; // _localctx.i++;
          if ( $i == col ) System.out.println($STUFF.text); // if ( _localctx.i == col ) System.out.println((((RowContext)_localctx).STUFF!=null?((RowContext)_localctx).STUFF.getText():null));
          }
      )+
    ;

TAB  :  '\t' -> skip ;   // match but don't pass to the parser
NL   :  '\r'? '\n' ;     // match and pass to the parser
STUFF:  ~[\t\r\n]+ ;     // match any chars except tab, newline
