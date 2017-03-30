%-----questo file possiede le regole e i vincoli a cui devono sottostare le domande------------------------


%-------------------------------------generating part-----------------------
%dice che se e' stata fatta la domanda, il risultato può essere true o false, ma solo uno dei due
1{risultatoDomanda(Domanda, RIS): risultato(RIS)}1 :- eStataFattaLaDomanda(Domanda), domanda(Domanda).

%se si fa una domanda la domanda puo essere o meno fatta al Db. Se non è stata fatta la domanda non dovrei vedere le altre domande
%last comment: {eStataFattaLaDomanda(Domanda)}1:- faiDomanda(Domanda), domanda(Domanda).


%--------------------------------constraint part---------------------------------------------

%non puoi avere un risultato se prima non hai fatto la domanda
%:- 0{faiDomanda(Domanda):domanda(domanda)}0, 1{risultatoDomanda(Domanda,RIS):risultato(RIS);domanda(Domanda)}1.

%non non può essere che non e' stata fatta la domanda e invece ho il risultato della domanda
:- 0{eStataFattaLaDomanda(Domanda)}0, 1{risultatoDomanda(Domanda,RIS):risultato(RIS)}1, domanda(Domanda).

%non puoi dire che è stata fatta la domanda, se non fai la domanda (ultima constraint)
:- 0{faiDomanda(Domanda)}0, 1{eStataFattaLaDomanda(Domanda)}1, domanda(Domanda).

%non puoi fare la stessa domanda più di una volta



%---------------------------------------visualization part------------------------------------------------

#show faiDomanda/1.
#show notifica/1.
%#show risultatoDomanda/2.
%#show eStataFattaLaDomanda/1.