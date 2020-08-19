grammar Data;

file : group+ ;

group: INT sequence[$INT.int] ;

sequence[int n]  // SequenceContext 构造函数多一个 int 参数（及成员变量）
locals [int i = 1;] // added to SequenceContext // public int i = 0;
     : ( {$i<=$n}? INT {$i++;} )* // if (!(_localctx.i<=_localctx.n)) throw new FailedPredicateException(this, "$i<=$n");
     ;
     
INT :   [0-9]+ ;
WS  :   [ \t\n\r]+ -> skip ;
