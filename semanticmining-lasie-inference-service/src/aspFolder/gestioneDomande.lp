%questo file specifica come le singole domande interagiscono tra loro, 
%se ad esempio una domanda ha risultato positivo o negativo, cosa succede


%-------------------------------instanze di domande--------------------------------------------------

domanda(bootStrap;lastSeen;wasSent;liveKnow;wasWith;findEvidence;sightTwo).
risultato(true;false).

% -------------------------------------gestione delle domande-------------------------------------

%condizioni di true e false per ogni domanda
faiDomanda(bootStrap).
faiDomanda(lastSeen):- risultatoDomanda(bootStrap, true). 
faiDomanda(wasSent) :- risultatoDomanda(lastSeen, true).
faiDomanda(liveKnow) :- risultatoDomanda(wasSent, true).
faiDomanda(wasWith) :- risultatoDomanda(liveKnow, true).
faiDomanda(findEvidence) :- risultatoDomanda(wasWith, true).
faiDomanda(sightTwo) :- risultatoDomanda(findEvidence, true).
%condizioni di false per ogni domanda
%--------------TO DO---------

% -------------------------------------gestione delle notifiche-------------------------------------
%notifiche
notifica("impossibile proseguire con l'investigazione") :- risultatoDomanda(bootStrap, false).
notifica("manca l'ultima volta in cui e' stata vista la vittima") :- risultatoDomanda(lastSeen, false).
notifica("manca l'ultima volta in cui la vittima ha interagito con qualcuno") :- risultatoDomanda(wasSent, false).
notifica("manca l'ultima volta in cui la vittima si e' vista con qualcuno") :- risultatoDomanda(liveKnow, false).
notifica("manca l'ultima volta in cui la vittima era con qualcuno") :- risultatoDomanda(wasWith, false).
notifica("mancano evidence"):- risultatoDomanda(findEvidence, false).