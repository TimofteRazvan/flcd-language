%{
  #include <ctype.h> 
  #include <stdio.h> 

  #define YYDEBUG 1

  int productions[1024];
  int production_index = 0;

  void addProduction(int production) 
  {
    productions[production_index++] = production;
  }

  void printProductions() 
  {
    int i;
    for (i = 0; i < production_index; i++) 
    {
      printf("%d ", productions[i]);
    }
    printf("\n");
  }
%}

%token INT
%token STRING
%token CHAR
%token BOOL
%token RETURN
%token READ
%token WRITE
%token IF
%token ELSE
%token WHILE
%token ARRAY
%token AND
%token OR

%token IDENTIFIER
%token NUMBER_CONST
%token STRING_CONST
%token CHAR_CONST

%token ADD
%token SUB
%token MUL
%token DIV
%token MOD

%token EQ
%token NE
%token LT
%token GT
%token LE
%token GE

%token ASSIGN

%token OPEN_CURLY
%token CLOSE_CURLY
%token OPEN_BRACKET
%token CLOSE_BRACKET
%token OPEN_SQUARE
%token CLOSE_SQUARE

%token SEMICOLON
%token COMMA

%%
	
stmtlist : stmt SEMICOLON {addProduction(1);}
	 | stmt SEMICOLON stmtlist {addProduction(2);}
	 ;

stmt : assignstmt {addProduction(3);}
     | declrstmt {addProduction(4);}
     | iostmt {addProduction(5);}
     | ifstmt {addProduction(6);}
     | whilestmt {addProduction(8);}
     | returnstmt {addProduction(9);}
     ;
     
exp : exp ADD term { addProduction(10); }
    | exp SUB term { addProduction(11); }
    | term { addProduction(12); }
    ;

term : term MUL factor { addProduction(13); }
     | term DIV factor { addProduction(14); }
     | term MOD factor { addProduction(15); }
     | factor { addProduction(16); }
     ;

factor : OPEN_BRACKET exp CLOSE_BRACKET { addProduction(17); }
       | IDENTIFIER {addProduction(18);}
       | NUMBER_CONST {addProduction(19);}
       ;

assignstmt : identifier ASSIGN exp { addProduction(20); }
           ;

declrstmt : type identifierlist { addProduction(28); }
          ;

identifier : IDENTIFIER { addProduction(29); }
           ;
           
identifierlist : identifier { addProduction(30); }
              | identifier COMMA identifierlist { addProduction(31); }
              ;

type : simpletype { addProduction(32); }
     | usertype { addProduction(33); }
     ;

simpletype : INT { addProduction(34); }
           | STRING { addProduction(35); }
           | CHAR { addProduction(36); }
           ;
		   
usertype : ARRAY OPEN_BRACKET simpletype COMMA NUMBER_CONST CLOSE_BRACKET { addProduction(37); }
           ;

iostmt : READ OPEN_BRACKET identifier CLOSE_BRACKET { addProduction(38); }
       | WRITE OPEN_BRACKET identifier CLOSE_BRACKET { addProduction(39); }
       | WRITE OPEN_BRACKET STRING_CONST CLOSE_BRACKET { addProduction(40); }
       ;

ifstmt : IF OPEN_BRACKET condition CLOSE_BRACKET OPEN_CURLY stmtlist CLOSE_CURLY { addProduction(41); }
       | IF OPEN_BRACKET condition CLOSE_BRACKET OPEN_CURLY stmtlist CLOSE_CURLY ELSE OPEN_CURLY stmtlist CLOSE_CURLY { addProduction(43); }
       ;

condition : exp relation exp { addProduction(45); }
          ;

relation : LT { addProduction(46); }
         | GT { addProduction(47); }
         | EQ { addProduction(48); }
         | NE { addProduction(49); }
         | LE { addProduction(50); }
         | GE { addProduction(51); }
         ;

whilestmt : WHILE OPEN_BRACKET condition CLOSE_BRACKET OPEN_CURLY stmtlist CLOSE_CURLY { addProduction(54); }
          ;

returnstmt : RETURN exp { addProduction(55); }
           ;

%%

yyerror(char *s)
{
    printf("%s\n", s);
}

extern FILE *yyin;

int main(int argc, char** argv) 
{
  if (argc > 1) 
  {
    yyin = fopen(argv[1], "r");
    if (!yyin) 
    {
      printf("'%s': Could not open specified file\n", argv[1]);
      return 1;
    }
  }

  if (argc > 2 && strcmp(argv[2], "-d") == 0) 
  {
    printf("Debug mode on\n");
    yydebug = 1;
  }

  printf("Starting parsing...\n");

  if (yyparse() == 0) 
  {
    printf("Parsing successful!\n");
    printProductions();
    return 0;
  }

  printf("Parsing failed!\n");
  return 0;
} 