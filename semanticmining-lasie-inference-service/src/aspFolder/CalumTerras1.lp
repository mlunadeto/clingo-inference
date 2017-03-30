%1)inserire una parte di script in cui vengono richieste le informazioni associate ad un caso di persona scomparsa



%2)inserire una parte di script per fare le chiamate rest e prelevare le informazioni




%3)parte logica
%------------------observations------------------------

happens(lastSeen("Calum Terras","Fleet Street", "january 5"),0).
happens(works("Calum Terras","a ward clerk"),1).
happens(isGoingTo("Calum Terras","Abdingdon street market"),2).
happens(isGoingTo("Calum Terras","his doctors"),2).
happens(wasNot("Calum Terras","his doctors"),3).
happens(liking("Calum Terras","camping"),4).
happens(couldBe("his family","Calum Terras","Manchester","friends"),5).
%happens(wasSent("the last text message","Cyprus", "a bar worker"),1).
%happens(happensToKnow("Calum Terras","several people"),2).
%happens(happensToLive("several people","Cyprus"),2).
%happens(was("Calum Terras","The Acomb","a friend","some weeks before her disappearence"),3).
%happens(finding("DNA","car"),4).
%happens(happensToBelongTo("DNA","left-handed smoker"),4).
%happens(happensToBelongTo("car","Calum Terras"),4).
%happens(sighting("left-handed smoker","Fleet Street","woman","january 5"),5).




%---------------------------------------------------------------------
object("january 5").
object(O):- person(O).
object(O):- location(O).
object(O):- clue(O).
object(O):- group(O).
object(O):- lifeHabit(O).
location("Fleet Street";"Abdingdon street market";"his doctors";"Manchester";"National Park").
clue("DNA").
group("friends").
crime("runAway";"kidnapped").
lifeHabit("camping").

occupation("a ward clerk").
victim("Calum Terras").
label("left-handed smoker").
gender("woman").
relationship("a friend").
family("his family").



person(P):-occupation(P).
person(P):-victim(P).
person(P):-gender(P).
person(P):-relationship(P).
person(P):-label(P).
person(P):- family(P).


event(lastSeen(Victim,Location,Object)):-victim(Victim),location(Location), object(Object).%sighting
event(works(Victim,Occupation)):-victim(Victim),occupation(Occupation).
event(isGoingTo(Victim,Location)):-victim(Victim),location(Location).
event(wasSent(Object1,Location,Object2)) :- object(Object1),object(Object2),location(Location).
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
event(was(Person1,Location,Person2,Object)):-person(Person1),person(Person2),location(Location),object(Object).%sighting
event(finding(Clue,Location)):- clue(Clue), location(Location).
event(sighting(Person1,Location,Person2,Object)):-person(Person1),person(Person2),location(Location),object(Object).%sighting
event(planning(Person1,Person2,Location,Object,Crime)):- person(Person1), person(Person2), location(Location), object(Object),crime(Crime).
%event(meeting(Person1,Person2,Location,Object)):- object(Object),person(Person1),person(Person2),location(Location).
event(wasIn(Object1,Object2)):- object(Object1),object(Object2).
event(wasNot(Object1,Object2)):- object(Object1),object(Object2).
event(collects(Person1,Person2,Location,Object)):- person(Person1),person(Person2),location(Location),object(Object).
event(kidnapping(Person,Victim)):- person(Person), victim(Victim).
event(cheating(Person,Victim)):- person(Person),victim(Victim).
event(laying(Victim,Location)):- victim(Victim),location(Location).
event(liking(Victim,LifeHabit)):- victim(Victim),lifeHabit(LifeHabit).
event(somethingWrong(Victim)):-victim(Victim).
event(death(Victim,Location)):-victim(Victim),location(Location).
event(couldBe(Family,Victim,Location,Group)):- family(Family), victim(Victim),location(Location), group(Group).


fluent(known(Person1,Object)):- person(Person1),object(Object).
fluent(live(Object,Location)):- object(Object), location(Location).
fluent(sent(Object1,Location,Object2)):- object(Object1),object(Object2),location(Location).
fluent(findClue(Person1,Person2,Location)):- person(Person1),person(Person2),location(Location).
fluent(belongedTo(Object1,Object2)):- object(Object1), object(Object2).
fluent(complicity2(Person1,Person2,Crime)):- person(Person1), person(Person2), crime(Crime).
fluent(isTheSamePerson(Person1,Person2)):-person(Person1), person(Person2).
fluent(at(Person1,Location,Person2,Object)):- person(Person1), person(Person2),location(Location),object(Object).
fluent(planned(Person1,Person2,Location,Object,Crime)):- person(Person1), person(Person2), location(Location), object(Object),crime(Crime).
fluent(run(Person,Location)):-person(Person),location(Location).
fluent(perpetrator(Person,Victim,Crime)):-person(Person),victim(Victim), crime(Crime).
fluent(found(Clue,Location)):- clue(Clue), location(Location).
fluent(withIn(Object1,Object2)):-object(Object1),object(Object2).
fluent(collected(Person1,Person2,Location,Object)):- person(Person1),person(Person2),location(Location),object(Object).
fluent(seen(Person,Location,Object)):-person(Person),location(Location),object(Object).
fluent(kidnapped(Person,Victim)):-person(Person),victim(Victim).
fluent(worked(Victim,Occupation)):-victim(Victim),occupation(Occupation).
fluent(gone(Victim,Location)):-victim(Victim),location(Location).
fluent(notIn(Victim,Location)):-victim(Victim),location(Location).
fluent(laid(Victim,Location)):-victim(Victim),location(Location).
fluent(liked(Victim,LifeHabit)):-victim(Victim),lifeHabit(LifeHabit).
fluent(dead(Victim,Location)):-victim(Victim),location(Location).



%precondtion effect axioms
initiates(lastSeen(Victim,Location,Object),seen(Victim,Location,Object),T):- victim(Victim),location(Location),object(Object),time(T).

initiates(happensToKnow(Person1,Object),known(Person1,Object),T):- person(Person1),object(Object),time(T).

initiates(happensToLive(Object,Location),live(Object,Location),T):- object(Object),location(Location),time(T).

initiates(becomesAccomplices2(Person1,Person2,Crime),complicity2(Person1,Person2,Crime),T):-person(Person1), person(Person2), crime(Crime), time(T).

initiates(wasSent(Object,Location, Person),sent(Object,Location,Person),T):- object(Object), location(Location), person(Person), time(T).

initiates(happensToBelongTo(Object1,Object2),belongedTo(Object1,Object2),T):- object(Object1), object(Object2), time(T).

initiates(happensToBeTheSame(Person1,Person2),isTheSamePerson(Person1,Person2),T):- person(Person1), person(Person2), time(T).

initiates(was(Person1,Location,Person2,Object),at(Person1,Location,Person2,Object),T) :- person(Person1), person(Person2), location(Location), object(Object),time(T).

initiates(planning(Person1,Person2,Location, Object, Crime),planned(Person1,Person2,Location, Object, Crime),T) :- person(Person1), person(Person2), location(Location), object(Object), crime(Crime), time(T).

initiates(runAway(Person,Location),run(Person,Location),T) :- person(Person), location(Location), time(T).

initiates(becomesPerpetrator(Person,Victim,Crime),perpetrator(Person,Victim,Crime),T) :- person(Person), victim(Victim), crime(Crime), time(T).

initiates(finding(Clue,Location),found(Clue,Location),T) :- clue(Clue), location(Location), time(T).

initiates(wasIn(Object1,Object2),withIn(Object1,Object2),T) :- object(Object1), object(Object2), time(T).

initiates(collects(Person1,Person2,Location,Object),collected(Person1,Person2,Location,Object),T) :- person(Person1),person(Person2),location(Location),object(Object), time(T).

initiates(sighting(Person1,Location,Person2,Object),seen(Person1,Location,Object),T):- person(Person1),person(Person2), location(Location),object(Object), time(T).

initiates(sighting(Person1,Location,Person2,Object),seen(Person2,Location,Object),T):- person(Person1),person(Person2), location(Location),object(Object), time(T).

initiates(kidnapping(Person, Victim),kidnapped(Person,Victim),T):- person(Person),victim(Victim), time(T).

initiates(works(Victim,Occupation),worked(Victim,Occupation),T):- victim(Victim), occupation(Occupation), time(T).

initiates(wasNot(Victim,Location),notIn(Victim,Location),T):- victim(Victim), location(Location), time(T).

ab1(Victim,Location):- 1{holdsAt(notIn(Victim,Location),T):time(T)}, victim(Victim),location(Location).

ab2(Victim,Location1):- 1{holdsAt(dead(Victim,Location2),T):time(T)}, victim(Victim),location(Location1), location(Location2), Location1!=Location2.

%gone si attiva solo se: 1) non esistono fluent notIn in nessun tempo per quella zona
%initiates(isGoingTo(Victim,Location),gone(Victim,Location),T):- {holdsAt(notIn(Victim,Location),T1):time(T1)}0, location(Location),victim(Victim), time(T).
initiates(isGoingTo(Victim,Location),gone(Victim,Location),T):- not ab1(Victim,Location), location(Location),victim(Victim), time(T), time(T1).


initiates(laying(Victim,Location),laid(Victim,Location),T):-victim(Victim), location(Location), time(T).

initiates(liking(Victim,LifeHabit),liked(Victim,LifeHabit),T):-victim(Victim), lifeHabit(LifeHabit), time(T).

initiates(death(Victim,Location),dead(Victim,Location),T):-victim(Victim), location(Location), time(T).

%----------------------------------------------------------------------------------------------------------------------

%una persona può essere scappata in una locazione dove si trova qualcuno che conosce (t.s. 2)
{happens(runAway(Person,Location),T)}1 :- happens(happensToLive(Group,Location),T), happens(happensToKnow(Person, Group),T), not holdsAt(run(Person,Location),T), person(Person), group(Group), location(Location), time(T).


%se la persona ha inviato qualcosa da quel posto, allora la persona vive (si trova) in quel posto (t.s. 2)
happens(happensToLive(Person,Location),T):- holdsAt(sent(Object,Location,Person),T), not holdsAt(live(Person,Location),T), person(Person), location(Location), time(T), object(Object).

%se un gruppo vive in un posto e la persona ha inviato qualcosa da quel posto, allora la persona appartiene a quel gruppo (aggiungere ipotesi invalidante) (t.s. 3)
happens(happensToBelongTo(Person,Group),T):- holdsAt(sent(Object,Location,Person),T), holdsAt(live(Group,Location),T), not holdsAt(belongedTo(Person,Group),T), person(Person), location(Location), time(T), group(Group), object(Object).

%una persona che scappa di casa diventa perpetrator di se stessa (t.s. 3)
happens(becomesPerpetrator(Victim,Victim,"runAway"),T):- holdsAt(run(Victim,Location),T), not holdsAt(perpetrator(Victim,Victim,"runAway"),T), victim(Victim),location(Location), time(T).


%se la vittima si trova con "un amico", questo ultimo può essere chiunque (il bar worker o il left-handed smoker) (t.s. 3)
{happens(was(Victim,Location,Person2,Object),T):person(Person2), not gender(Person2),Person2!="a friend", Person2!="a ward clerk", not victim(Person2), victim(Victim), location(Location),object(Object), time(T)}1:- happens(was(Victim,Location,"a friend",Object),T).


%persona1 e persona2 si conoscono se persona1 conosce un gruppo e la persona2 appartiene a quel gruppo (t.s. 4)
happens(happensToKnow(Person1,Person2),T):- holdsAt(belongedTo(Person2,Group),T), not holdsAt(known(Person1,Person2),T), holdsAt(known(Person1,Group),T), person(Person1), person(Person2), group(Group), Person1!=Person2, time(T).


%se due persone sono con la vittima nello stesso tempo e uno di loro è "un amico", assumo che esse sono le stesse (t.s.4)
happens(happensToBeTheSame(Person1,Person2),T):- holdsAt(at(Victim,Location,Person1,Object),T),holdsAt(at(Victim,Location,Person2,Object),T), not holdsAt(isTheSamePerson(Person1,Person2),T),person(Person1), person(Person2), Person2=="a friend", object(Object), location(Location), victim(Victim), time(T), Person1!=Person2.

%se vale la relazione isTheSamePerson tra person1 e person2, con person2 che è un amico, allora la "Calum Terras" conosce person1 (t.s. 5)
happens(happensToKnow(Victim, Person1),T):- holdsAt(isTheSamePerson(Person1,Person2),T), Person2=="a friend", Person1!=Person2, holdsAt(at(Victim,Location,Person1,Object),T), not holdsAt(known(Victim,Person1),T), victim(Victim), location(Location),object(Object), person(Person1), person(Person2), time(T).


%la persona diventa "complice" della fuga della vittima quando essa è scappata in un posto dove vive la persona che conosce (t.s. 5) 
happens(becomesAccomplices2(Person,Victim,"runAway"),T):- holdsAt(run(Victim,Location),T), holdsAt(known(Victim,Person),T), holdsAt(live(Person,Location),T), not holdsAt(complicity2(Person,Victim,"runAway"),T), person(Person),victim(Victim),location(Location), time(T).


%se è stato trovato un oggetto in un posto, allora l'oggetto è in quel posto (t.s. 5)
happens(wasIn(Clue,Location),T):- holdsAt(found(Clue,Location),T), not holdsAt(withIn(Clue,Location),T), time(T), clue(Clue),location(Location).

%due persone stanno pianificando un crimine in un luogo in un tempo, se esse si trovavano in quel luogo in quel tempo, se una di loro è perpetrator di quel crimine e l'altra è complice della prima di quel crimine (t.s. 6)
happens(planning(Person1,Person2,Location,Object,Crime),T):- holdsAt(at(Person1,Location,Person2,Object),T), holdsAt(perpetrator(Person1,Victim,Crime),T), holdsAt(complicity2(Person2,Person1,Crime),T), not holdsAt(planned(Person1,Person2,Location,Object,Crime),T), person(Person1),person(Person2),victim(Victim), location(Location),object(Object),crime(Crime), time(T).


%se l'oggetto è in un posto, se l'oggetto appartiene a qualcuno, allora quel qualcuno è stato in quel posto (t.s. 6)
happens(wasIn(Person,Location),T):- holdsAt(withIn(Clue,Location),T), holdsAt(belongedTo(Clue,Person),T), not holdsAt(withIn(Person,Location),T),person(Person), clue(Clue), location(Location), time(T).


%una persona diventa complice della fuga di una vittima, se questa è stata vista in un posto vicino al luogo e al tempo della scomparsa della vittima e se la vittima è scappata (t.s. 6)
happens(becomesAccomplices2(Person,Victim,"runAway"),T):-  holdsAt(seen(Person,Location1,Object),T), holdsAt(seen(Victim,Location1,Object),T), holdsAt(run(Victim,Location2),T), not holdsAt(complicity2(Person,Victim,"runAway"),T), victim(Victim), person(Person), location(Location1), location(Location2), Person!=Victim, object(Object), time(T).


%se una persona viene vista vicino al luogo della scomparsa della "Calum Terras" e la "Calum Terras" non è {mai} (per ogni t non si alza mai holdsAt(run(),T)) scappata di casa, questa persona l'ha rapita (t.s. 6)
happens(kidnapping(Person,Victim),T):- {holdsAt(run(Victim,Location1),T):location(Location1)}0, holdsAt(seen(Person,Location2,Object),T), holdsAt(seen(Victim,Location2,Object),T), not holdsAt(kidnapped(Person,Victim),T), location(Location2), person(Person),object(Object), not victim(Person), Person!=Victim, victim(Victim), time(T). 


%se una persona2 si trova in un posto/oggetto e quell'oggetto appartiene alla persona1, allora persona1 e persona2 si conoscono (t.s. 7)
happens(happensToKnow(Person1,Person2),T):- holdsAt(withIn(Person2,Location),T), holdsAt(belongedTo(Location,Person1),T), not holdsAt(known(Person1,Person2),T), person(Person1),person(Person2),location(Location),time(T).


%se una persona1 stava con persona2 in una location in un tempo (at t.s. 4)  e la persona2 stava nella "car"(t.s. 7), che appartiene alla persona1 (t.s. 5), inoltre le due persone si conoscono (t.s. 6), allora la persona1 può aver dato un passaggio alla persona2 in quella location in quel tempo (t.s. 7)
happens(collects(Person1,Person2,Location,Object),T):- holdsAt(at(Person1,Location,Person2,Object),T), holdsAt(belongedTo("car",Person1),T), holdsAt(withIn(Person2,"car"),T), holdsAt(known(Person1,Person2),T), not holdsAt(collected(Person1,Person2,Location,Object),T), person(Person1),location(Location),person(Person2),object(Object), time(T).


%se una persona rapisce un'altra, essa diventa il suo perpetrator (t.s. 7)
happens(becomesPerpetrator(Person,Victim,"kidnapped"),T):- holdsAt(kidnapped(Person,Victim),T), not holdsAt(perpetrator(Person,Victim,"kidnapped"),T), person(Person), victim(Victim), time(T).


%se la vittima e la persona che l'ha rapita si conoscevano (from t.s. 5), allora la vittma è stata ingannata dalla persona (t.s. 7)
happens(cheating(Person,Victim),T):- holdsAt(kidnapped(Person,Victim),T), holdsAt(known(Victim,Person),T), victim(Victim), person(Person), time(T). 


%se la vittima conosce una persona1, inoltre la vittima è stata rapita (o meglio esiste un rapitore), allora la persona1 diventa testimone/sospettato (t.s. 7)
happens(becomesWitness(Person1,"kidnapped"),T) :- holdsAt(known(Victim,Person1),T),1{holdsAt(kidnapped(Person2,Victim),T)}, {holdsAt(kidnapped(Person1,Victim),T):person(Person1)}0,Person1!=Person2, person(Person1), person(Person2), victim(Victim), time(T).




%due persone complici di uno stesso crimine ai danno di una stessa persona, si conoscono (t.s. 7)
%happens(happensToKnow(Person1,Person2),T):- holdsAt(complicity2(Person1,Person3,Crime),T), holdsAt(complicity2(Person2,Person3,Crime),T), crime(Crime), person(Person1),person(Person2), person(Person3),time(T).


%se una persona è andata da una parte, si trova in quella parte (a meno che non capita qualcosa di brutto ad un'altra parte - ab2) (t.s. 3)
happens(wasIn(Victim,Location1),T):- holdsAt(gone(Victim,Location1),T), not holdsAt(withIn(Victim,Location1),T), victim(Victim), location(Location1), not ab2(Victim,Location2), location(Location2), time(T), Location1!=Location2.


%se una persona dice di andare in un posto e non si trova in quel posto, allora ha mentito sul fatto di andare in quel posto (t.s. 2)
happens(laying(Victim,Location),T):- happens(isGoingTo(Victim,Location),T), 1{holdsAt(notIn(Victim,Location),T1):time(T1)}, victim(Victim),location(Location),time(T).


%se a una persona piace fare camping, allora può avere intenzione di andare al parco nazionale più vicino a dove è stato visto l'ultima volta (t.s. 4).  
{happens(isGoingTo(Victim,"National Park"),T)}1 :- happens(liking(Vctim,"camping"),T), victim(Victim), time(T).

%se uno dice che una persona potrebbe stare in un luogo, allora potrebbe avere intenzione di andare in quel luogo (t.s. 5)
{happens(isGoingTo(Victim,Location),T)}1 :- happens(couldBe(Family,Vctim,Location,Group),T), family(Family),victim(Victim),location(Location),group(Group), time(T).


%-----------------------------------------testing part---------------------------------



:- {not releasedAt(complicity2(Person1,Person2,Crime),T)}0, person(Person1), person(Person2), crime(Crime), time(T).

:- {not releasedAt(known(Person1,Object),T)}0, person(Person1), object(Object), time(T).

:- {not releasedAt(areFriends(Person1,Person2),T)}0, person(Person1), person(Person2), time(T).

:- {not releasedAt(live(Object,Location),T)}0, object(Object), location(Location), time(T).

:- {not releasedAt(sent(Object1,Location,Object2),T)}0, object(Object1), location(Location), object(Object2),time(T).

:- {not releasedAt(findClue(Person1,Person2,Location),T)}0, person(Person1), person(Person2),location(Location), time(T).

:- {not releasedAt(belongedTo(Object1, Object2),T)}0, object(Object1), object(Object2), time(T).

:- {not releasedAt(isTheSamePerson(Person1, Person2),T)}0, person(Person1), person(Person2), time(T).

:- {not releasedAt(at(Person1, Location, Person2, Object),T)}0, person(Person1), person(Person2), location(Location),object(Object),time(T).

:- {not releasedAt(planned(Person1, Person2, Location, Object,Crime),T)}0, person(Person1), person(Person2), location(Location),object(Object),crime(Crime), time(T).

:- {not releasedAt(run(Person, Location),T)}0, person(Person), location(Location), time(T).

:- {not releasedAt(perpetrator(Person, Victim, Crime),T)}0, person(Person), victim(Victim), crime(Crime), time(T).

:- {not releasedAt(found(Clue, Location),T)}0, clue(Clue), location(Location), time(T).

:- {not releasedAt(withIn(Object1, Object2),T)}0, object(Object1), object(Object2), time(T).

:- {not releasedAt(collected(Person1,Person2,Location,Object),T)}0,  person(Person1),person(Person2),location(Location),object(Object), time(T).

:- {not releasedAt(seen(Person,Location,Object),T)}0,  person(Person),location(Location),object(Object), time(T).

:- {not releasedAt(kidnapped(Person,Victim),T)}0,  person(Person),victim(Victim), time(T).

:- {not releasedAt(worked(Victim,Occupation),T)}0,  victim(Victim), occupation(Occupation), time(T).

:- {not releasedAt(gone(Victim,Location),T)}0,  victim(Victim), location(Location), time(T).

:- {not releasedAt(notIn(Victim,Location),T)}0,  victim(Victim), location(Location), time(T).

:- {not releasedAt(laid(Victim,Location),T)}0,  victim(Victim), location(Location), time(T).

:- {not releasedAt(liked(Victim,LifeHabit),T)}0,  victim(Victim), lifeHabit(LifeHabit), time(T).

:- {not releasedAt(dead(Victim,Location),T)}0,  victim(Victim), location(Location), time(T).

%----------------------------------------------------------------------------------------------------------------------------------

not holdsAt(complicity2(Person1,Person2,Crime),0) :- person(Person1),person(Person2),crime(Crime).
not holdsAt(known(Person1,Object),0) :- person(Person1), object(Object).
not holdsAt(live(Object,Location),0) :- object(Object), location(Location).
not holdsAt(sent(Object1,Location,Object2),0) :- object(Object1), location(Location), object(Object2).
not holdsAt(findClue(Person1,Person2,Location),0) :- person(Person1), person(Person2), location(Location).
not holdsAt(belongedTo(Object1,Object2),0) :- object(Object1), object(Object2).
not holdsAt(isTheSamePerson(Person1,Person2),0) :- person(Person1), person(Person2).
not holdsAt(at(Person1,Location,Person2,Object),0) :- person(Person1), person(Person2), location(Location), object(Object).
not holdsAt(planned(Person1,Person2,Location,Object,Crime),0) :- person(Person1), person(Person2), location(Location), object(Object), crime(Crime).
not holdsAt(run(Person,Location),0) :- person(Person), location(Location).
not holdsAt(perpetrator(Person,Victim,Crime),0) :- person(Person),victim(Victim),crime(Crime).
not holdsAt(found(Clue, Location),0):- clue(Clue),location(Location).
not holdsAt(withIn(Object1, Object2),0):- object(Object1),object(Object2).
not holdsAt(collected(Person1,Person2,Location,Object),0):- person(Person1),person(Person2),location(Location),object(Object).
not holdsAt(seen(Person,Location,Object),0):- person(Person),location(Location),object(Object).
not holdsAt(kidnapped(Person,Victim),0):- person(Person),victim(Victim).
not holdsAt(worked(Victim,Occupation),0):- victim(Victim),occupation(Occupation).
not holdsAt(gone(Victim,Location),0):- victim(Victim),location(Location).
not holdsAt(notIn(Victim,Location),0):- victim(Victim),location(Location).
not holdsAt(laid(Victim,Location),0):- victim(Victim),location(Location).
not holdsAt(liked(Victim,LifeHabit),0):- victim(Victim),lifeHabit(LifeHabit).
not holdsAt(dead(Victim,Location),0):- victim(Victim),location(Location).


