

%----------------Relazioni tra cose:  Questa parte ci deve essere----------------------------------
object(O):- person(O).
object(O):- location(O).
object(O):- evidence(O).
object(O):- group(O).
object(O):- timeEvent(O).
object(O) :- resource(O).



%person(P):-occupation(P).
person(P):- victim(P).
%person(P):-gender(P).
%person(P):-relationship(P).
%person(P):-label(P).
%-----------------------------------------------------------------------------------------------------



event(lastSeen(Victim,Location,TEvent)):- victim(Victim),location(Location), timeEvent(TEvent).%sighting
event(wasSent(Resource,Location,Person1,Person2)) :- resource(Resource),person(Person1),person(Person2),location(Location).
event(happensToKnow(Person1,Object)):- person(Person1), object(Object).
event(happensToLive(Object,Location)):- object(Object), location(Location).
event(happensToBelongTo(Object1,Object2)):- object(Object1), object(Object2).
event(happensToBeTheSame(Person1,Person2)):- person(Person1), person(Person2).
%una persona è complice di un'altra per un certo crimine
event(becomesAccomplices2(Person1,Person2,Crime)):- person(Person1), person(Person2), crime(Crime).
%una persona è perpetrator di un'altra per un certo crimine
event(becomesPerpetrator(Person,Victim,Crime)):-person(Person),victim(Victim), crime(Crime).
event(becomesWitness(Person,Crime)):- person(Person), crime(Crime).
event(runAway(Victim,Location)):- victim(Victim),location(Location).
event(was(Person1,Location,Person2,TEvent)):- person(Person1),person(Person2),location(Location),timeEvent(TEvent).%sighting
event(finding(Evidence,Location)):- evidence(Evidence), location(Location).
event(sighting(Person1,Location,Person2,TEvent)):-person(Person1),person(Person2),location(Location),timeEvent(TEvent).%sighting
event(planning(Person1,Person2,Location,Object,Crime)):- person(Person1), person(Person2), location(Location), object(Object),crime(Crime).
%event(meeting(Person1,Person2,Location,Object)):- object(Object),person(Person1),person(Person2),location(Location).
event(wasIn(Object1,Object2)):- object(Object1),object(Object2).
event(collects(Person1,Person2,Location,Object)):- person(Person1),person(Person2),location(Location),object(Object).
event(kidnapping(Person,Victim)):- person(Person), victim(Victim).
event(cheating(Person,Victim)):- person(Person),victim(Victim).



fluent(known(Person1,Object)):- person(Person1),object(Object).
fluent(live(Object,Location)):- object(Object), location(Location).
fluent(sent(Resource,Location,Person1,Person2)):- resource(Resource),person(Person1),person(Person2),location(Location).
fluent(findEvidence(Person1,Person2,Location)):- person(Person1),person(Person2),location(Location).
fluent(belongedTo(Object1,Object2)):- object(Object1), object(Object2).
fluent(complicity2(Person1,Person2,Crime)):- person(Person1), person(Person2), crime(Crime).
fluent(isTheSamePerson(Person1,Person2)):-person(Person1), person(Person2).
fluent(at(Person1,Location,Person2,TEvent)):- person(Person1), person(Person2),location(Location),timeEvent(TEvent).
fluent(planned(Person1,Person2,Location,Object,Crime)):- person(Person1), person(Person2), location(Location), object(Object),crime(Crime).
fluent(run(Person,Location)):-person(Person),location(Location).
fluent(perpetrator(Person,Victim,Crime)):-person(Person),victim(Victim), crime(Crime).
fluent(found(Evidence,Location)):- evidence(Evidence), location(Location).
fluent(withIn(Object1,Object2)):-object(Object1),object(Object2).
fluent(collected(Person1,Person2,Location,Object)):- person(Person1),person(Person2),location(Location),object(Object).
fluent(seen(Person,Location,TEvent)):-person(Person),location(Location),timeEvent(TEvent).
fluent(kidnapped(Person,Victim)):-person(Person),victim(Victim).
fluent(witnessed(Person,Crime)):- person(Person), crime(Crime).
fluent(cheated(Person,Victim)):- person(Person),victim(Victim).

%---------------------------------------------------------------precondtion effect axioms---------------------------------------------
initiates(lastSeen(Victim,Location,TEvent),seen(Victim,Location,TEvent),T):- victim(Victim),location(Location),timeEvent(TEvent),time(T).

initiates(happensToKnow(Person1,Object),known(Person1,Object),T):- person(Person1),object(Object),time(T).

initiates(happensToLive(Object,Location),live(Object,Location),T):- object(Object),location(Location),time(T).

initiates(becomesAccomplices2(Person1,Person2,Crime),complicity2(Person1,Person2,Crime),T):-person(Person1), person(Person2), crime(Crime), time(T).

initiates(wasSent(Resource,Location, Person1,Person2),sent(Resource,Location,Person1,Person2),T):- resource(Resource), location(Location), person(Person1), person(Person2),time(T).

initiates(happensToBelongTo(Object1,Object2),belongedTo(Object1,Object2),T):- object(Object1), object(Object2), time(T).

initiates(happensToBeTheSame(Person1,Person2),isTheSamePerson(Person1,Person2),T):- person(Person1), person(Person2), time(T).

initiates(was(Person1,Location,Person2,TEvent),at(Person1,Location,Person2,TEvent),T) :- person(Person1), person(Person2), location(Location), timeEvent(TEvent),time(T).

initiates(planning(Person1,Person2,Location, Object, Crime),planned(Person1,Person2,Location, Object, Crime),T) :- person(Person1), person(Person2), location(Location), object(Object), crime(Crime), time(T).

initiates(runAway(Person,Location),run(Person,Location),T) :- person(Person), location(Location), time(T).

initiates(becomesPerpetrator(Person,Victim,Crime),perpetrator(Person,Victim,Crime),T) :- person(Person), victim(Victim), crime(Crime), time(T).

initiates(finding(Evidence,Location),found(Evidence,Location),T) :- evidence(Evidence), location(Location), time(T).

initiates(wasIn(Object1,Object2),withIn(Object1,Object2),T) :- object(Object1), object(Object2), time(T).

initiates(collects(Person1,Person2,Location,Object),collected(Person1,Person2,Location,Object),T) :- person(Person1),person(Person2),location(Location),object(Object), time(T).

initiates(sighting(Person1,Location,Person2,TEvent),seen(Person1,Location,TEvent),T):- person(Person1),person(Person2), location(Location),timeEvent(TEvent), time(T).

%TOBETESTED---per dare la colpevolezza anche alla donna
initiates(sighting(Person1,Location,Person2,TEvent),seen(Person2,Location,TEvent),T):- person(Person1),person(Person2), location(Location),timeEvent(TEvent), time(T).


initiates(kidnapping(Person, Victim),kidnapped(Person,Victim),T):- person(Person),victim(Victim), time(T).

initiates(becomesWitness(Person,Crime),witnessed(Person,Crime),T) :- person(Person), crime(Crime), time(T).

initiates(cheating(Person,Victim),cheated(Person,Victim),T) :- person(Person), victim(Victim), time(T).

%----------------------------------------------------------------------------------------------------------------------

%una persona può essere scappata in una locazione dove si trova qualcuno che conosce (t.s. 2)
{happens(runAway(Person,Location),T)}1 :- happens(happensToLive(Group,Location),T), happens(happensToKnow(Person, Group),T), not holdsAt(run(Person,Location),T), person(Person), group(Group), location(Location), time(T).


%se la persona ha inviato qualcosa da quel posto, allora la persona vive (si trova) in quel posto (t.s. 2)
happens(happensToLive(Person1,Location),T):- holdsAt(sent(Resource,Location,Person1,Person2),T), not holdsAt(live(Person1,Location),T), person(Person1), person(Person2),location(Location), time(T), resource(Resource).

%se un gruppo vive in un posto e la persona ha inviato qualcosa da quel posto, allora la persona appartiene a quel gruppo (aggiungere ipotesi invalidante) (t.s. 3)
happens(happensToBelongTo(Person1,Group),T):- holdsAt(sent(Resource,Location,Person1,Person2),T), holdsAt(live(Group,Location),T), not holdsAt(belongedTo(Person1,Group),T), person(Person1), person(Person2), location(Location), time(T), group(Group), resource(Resource).

%una persona che scappa di casa diventa perpetrator di se stessa (t.s. 3) (OK)
happens(becomesPerpetrator(Victim,Victim,"runAway"),T):- holdsAt(run(Victim,Location),T), not holdsAt(perpetrator(Victim,Victim,"runAway"),T), victim(Victim),location(Location), time(T).


%se la vittima si trova con "un amico", questo ultimo può essere chiunque (il bar worker o il left-handed smoker) (t.s. 3)
{happens(was(Victim,Location,Person2,TEvent),T):person(Person2), Person2!="woman",Person2!="a friend", Person2!=chef, not victim(Person2), victim(Victim), location(Location),timeEvent(TEvent), time(T)}1:- happens(was(Victim,Location,"a friend",TEvent),T).

%se una persona appartiene ad un gruppo, la persona conosce il gruppo (t.s. 4)
happens(happensToKnow(Person,Group),T) :- holdsAt(belongedTo(Person,Group),T), not holdsAt(known(Person,Group),T), group(Group), person(Person), time(T).

%persona1 e persona2 si conoscono se persona1 conosce un gruppo e la persona2 appartiene a quel gruppo (t.s. 4)
happens(happensToKnow(Person1,Person2),T):- holdsAt(belongedTo(Person2,Group),T), not holdsAt(known(Person1,Person2),T), holdsAt(known(Person1,Group),T), person(Person1), person(Person2), group(Group), Person1!=Person2, time(T).


%se due persone sono con la vittima nello stesso tempo e uno di loro è "un amico", assumo che esse sono le stesse (t.s.4)
happens(happensToBeTheSame(Person1,Person2),T):- holdsAt(at(Victim,Location,Person1,TEvent),T),holdsAt(at(Victim,Location,Person2,TEvent),T), not holdsAt(isTheSamePerson(Person1,Person2),T),person(Person1), person(Person2), Person2=="a friend", timeEvent(TEvent), location(Location), victim(Victim), time(T), Person1!=Person2.

%se vale la relazione isTheSamePerson tra person1 e person2, con person2 che è un amico, allora la Lawrence conosce person1 (t.s. 5)
happens(happensToKnow(Victim, Person1),T):- holdsAt(isTheSamePerson(Person1,Person2),T), Person2=="a friend", Person1!=Person2, holdsAt(at(Victim,Location,Person1,TEvent),T), not holdsAt(known(Victim,Person1),T), victim(Victim), location(Location),timeEvent(TEvent), person(Person1), person(Person2), time(T).


%la persona diventa "complice" della fuga della vittima quando essa è scappata in un posto dove vive la persona che conosce (t.s. 5) 
happens(becomesAccomplices2(Person,Victim,"runAway"),T):- holdsAt(run(Victim,Location),T), holdsAt(known(Victim,Person),T), holdsAt(live(Person,Location),T), not holdsAt(complicity2(Person,Victim,"runAway"),T), person(Person),victim(Victim),location(Location), time(T).


%se è stato trovato un oggetto in un posto, allora l'oggetto è in quel posto (t.s. 5)
happens(wasIn(Evidence,Location),T):- holdsAt(found(Evidence,Location),T), not holdsAt(withIn(Evidence,Location),T), time(T), evidence(Evidence),location(Location).

%due persone stanno pianificando un crimine in un luogo in un tempo, se esse si trovavano in quel luogo in quel tempo, se una di loro è perpetrator di quel crimine e l'altra è complice della prima di quel crimine (t.s. 6)
happens(planning(Person1,Person2,Location,TEvent,Crime),T):- holdsAt(at(Person1,Location,Person2,TEvent),T), holdsAt(perpetrator(Person1,Victim,Crime),T), holdsAt(complicity2(Person2,Person1,Crime),T), not holdsAt(planned(Person1,Person2,Location,TEvent,Crime),T), person(Person1),person(Person2),victim(Victim), location(Location),timeEvent(TEvent),crime(Crime), time(T).


%se l'oggetto è in un posto, se l'oggetto appartiene a qualcuno, allora quel qualcuno è stato in quel posto (t.s. 6)
happens(wasIn(Person,Location),T):- holdsAt(withIn(Evidence,Location),T), holdsAt(belongedTo(Evidence,Person),T), not holdsAt(withIn(Person,Location),T),person(Person), evidence(Evidence), location(Location), time(T).


%una persona diventa (complice) witness della fuga di una vittima, se questa è stata vista in un posto vicino al luogo e al tempo della scomparsa della vittima e se la vittima è scappata (t.s. 6)
happens(becomesWitness(Person,"runAway"),T):-  holdsAt(seen(Person,Location1,TEvent),T), holdsAt(seen(Victim,Location1,TEvent),T), holdsAt(run(Victim,Location2),T), not holdsAt(witnessed(Person,"runAway"),T), victim(Victim), person(Person), location(Location1), location(Location2), Person!=Victim, timeEvent(TEvent), time(T).


%se una persona viene vista vicino al luogo della scomparsa della lawrence e la lawrence non è {mai} (per ogni t non si alza mai holdsAt(run(),T)) scappata di casa, questa persona l'ha rapita (t.s. 6)
happens(kidnapping(Person,Victim),T):- {holdsAt(run(Victim,Location1),T):location(Location1)}0, holdsAt(seen(Person,Location2,TEvent),T), holdsAt(seen(Victim,Location2,TEvent),T), not holdsAt(kidnapped(Person,Victim),T), location(Location2), person(Person),timeEvent(TEvent), not victim(Person), Person!=Victim, victim(Victim), time(T). 


%se una persona2 si trova in un posto/oggetto e quell'oggetto appartiene alla persona1, allora persona1 e persona2 si conoscono (t.s. 7)
%happens(happensToKnow(Person1,Person2),T):- holdsAt(withIn(Person2,Location),T), holdsAt(belongedTo(Location,Person1),T), not %holdsAt(known(Person1,Person2),T), person(Person1),person(Person2),location(Location),time(T).


%se una persona1 stava con persona2 in una location in un tempo (at t.s. 4)  e la persona2 stava nella "car"(t.s. 7), che appartiene alla persona1 (t.s. 5), inoltre le due persone si conoscono (t.s. 6), allora la persona1 può aver dato un passaggio alla persona2 in quella location in quel tempo (t.s. 7)
happens(collects(Person1,Person2,Location,TEvent),T):- holdsAt(at(Person1,Location,Person2,TEvent),T), holdsAt(belongedTo("car",Person1),T), holdsAt(withIn(Person2,"car"),T), holdsAt(known(Person1,Person2),T), not holdsAt(collected(Person1,Person2,Location,TEvent),T), person(Person1),location(Location),person(Person2),timeEvent(TEvent), time(T).


%se una persona rapisce un'altra, essa diventa il suo perpetrator (t.s. 7)
happens(becomesPerpetrator(Person,Victim,"kidnapped"),T):- holdsAt(kidnapped(Person,Victim),T), not holdsAt(perpetrator(Person,Victim,"kidnapped"),T), person(Person), victim(Victim), time(T).


%se la vittima e la persona che l'ha rapita si conoscevano (from t.s. 5), allora la vittma è stata ingannata dalla persona (t.s. 7)
happens(cheating(Person,Victim),T):- holdsAt(kidnapped(Person,Victim),T), holdsAt(known(Victim,Person),T), victim(Victim), not holdsAt(cheated(Person,Victim),T), person(Person), time(T). 


%se la vittima conosce una persona1, inoltre la vittima è stata rapita (o meglio esiste un rapitore), allora la persona1 diventa testimone/sospettato (t.s. 7)
happens(becomesWitness(Person1,"kidnapped"),T) :- holdsAt(known(Victim,Person1),T),1{holdsAt(kidnapped(Person2,Victim),T)}, {holdsAt(kidnapped(Person1,Victim),T):person(Person1)}0, not holdsAt(witnessed(Person1,"kidnapped"),T), Person1!=Person2, person(Person1), person(Person2), victim(Victim), time(T).




%due persone complici di uno stesso crimine ai danno di una stessa persona, si conoscono (t.s. 7)
%happens(happensToKnow(Person1,Person2),T):- holdsAt(complicity2(Person1,Person3,Crime),T), holdsAt(complicity2(Person2,Person3,Crime),T), crime(Crime), person(Person1),person(Person2), person(Person3),time(T).



%-----------------------------------------testing part---------------------------------



:- {not releasedAt(complicity2(Person1,Person2,Crime),T)}0, person(Person1), person(Person2), crime(Crime), time(T).

:- {not releasedAt(known(Person1,Object),T)}0, person(Person1), object(Object), time(T).

%:- {not releasedAt(areFriends(Person1,Person2),T)}0, person(Person1), person(Person2), time(T).

:- {not releasedAt(live(Object,Location),T)}0, object(Object), location(Location), time(T).

:- {not releasedAt(sent(Resource,Location,Person1,Person2),T)}0, resource(Resource), location(Location), person(Person1), person(Person2),time(T).

:- {not releasedAt(findEvidence(Person1,Person2,Location),T)}0, person(Person1), person(Person2),location(Location), time(T).

:- {not releasedAt(belongedTo(Object1, Object2),T)}0, object(Object1), object(Object2), time(T).

:- {not releasedAt(isTheSamePerson(Person1, Person2),T)}0, person(Person1), person(Person2), time(T).

:- {not releasedAt(at(Person1, Location, Person2, TEvent),T)}0, person(Person1), person(Person2), location(Location),timeEvent(TEvent),time(T).

:- {not releasedAt(planned(Person1, Person2, Location, Object,Crime),T)}0, person(Person1), person(Person2), location(Location),object(Object),crime(Crime), time(T).

:- {not releasedAt(run(Person, Location),T)}0, person(Person), location(Location), time(T).

:- {not releasedAt(perpetrator(Person, Victim, Crime),T)}0, person(Person), victim(Victim), crime(Crime), time(T).

:- {not releasedAt(found(Evidence, Location),T)}0, evidence(Evidence), location(Location), time(T).

:- {not releasedAt(withIn(Object1, Object2),T)}0, object(Object1), object(Object2), time(T).

:- {not releasedAt(collected(Person1,Person2,Location,Object),T)}0,  person(Person1),person(Person2),location(Location),object(Object), time(T).

:- {not releasedAt(seen(Person,Location,TEvent),T)}0,  person(Person),location(Location),timeEvent(TEvent), time(T).

:- {not releasedAt(kidnapped(Person,Victim),T)}0,  person(Person),victim(Victim), time(T).

:- {not releasedAt(witnessed(Person,Crime),T)}0,  person(Person),crime(Crime), time(T).

:- {not releasedAt(cheated(Person,Victim),T)}0,  person(Person),victim(Victim), time(T).


%----------------------------------------------------------------------------------------------------------------------------------

not holdsAt(complicity2(Person1,Person2,Crime),0) :- person(Person1),person(Person2),crime(Crime).
not holdsAt(known(Person1,Object),0) :- person(Person1), object(Object).
not holdsAt(live(Object,Location),0) :- object(Object), location(Location).
not holdsAt(sent(Resource,Location,Person1, Person2),0) :- resource(Resource), location(Location), person(Person1), person(Person2).
not holdsAt(findEvidence(Person1,Person2,Location),0) :- person(Person1), person(Person2), location(Location).
not holdsAt(belongedTo(Object1,Object2),0) :- object(Object1), object(Object2).
not holdsAt(isTheSamePerson(Person1,Person2),0) :- person(Person1), person(Person2).
not holdsAt(at(Person1,Location,Person2,TEvent),0) :- person(Person1), person(Person2), location(Location), timeEvent(TEvent).
not holdsAt(planned(Person1,Person2,Location,Object,Crime),0) :- person(Person1), person(Person2), location(Location), object(Object), crime(Crime).
not holdsAt(run(Person,Location),0) :- person(Person), location(Location).
not holdsAt(perpetrator(Person,Victim,Crime),0) :- person(Person),victim(Victim),crime(Crime).
not holdsAt(found(Evidence, Location),0):- evidence(Evidence),location(Location).
not holdsAt(withIn(Object1, Object2),0):- object(Object1),object(Object2).
not holdsAt(collected(Person1,Person2,Location,Object),0):- person(Person1),person(Person2),location(Location),object(Object).
not holdsAt(seen(Person,Location,TEvent),0):- person(Person),location(Location),timeEvent(TEvent).
not holdsAt(kidnapped(Person,Victim),0):- person(Person),victim(Victim).
not holdsAt(witnessed(Person,Crime),0):- person(Person), crime(Crime).
not holdsAt(cheated(Person,Victim),0):- person(Person), victim(Victim).



#show person/1.
%#show victim/1.
%#show crime/1.
%#show group/1.
%#show timeEvent/1.
%#show evidence/1.
%#show location/1.
%#show resource/1.

