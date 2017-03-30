%-------------------------------regole per i link-------------------------------
%ad ogni evento corrispondono una serie di link che vanno dagli eventi alle entita che coinvolge



%lastSeen
%link(involvesEntity, Victim, lastSeen(Victim,Location,TEvent)) :- happens(lastSeen(Victim,Location,TEvent),T), time(T), %location(Location), timeEvent(TEvent), victim(Victim).
%link(involvesEntity, Location, lastSeen(Victim,Location,TEvent)) :- happens(lastSeen(Victim,Location,TEvent),T), time(T), %location(Location), timeEvent(TEvent), victim(Victim).
link(involvesEntity, Victim, sighting(Victim,Location,TEvent)) :- happens(lastSeen(Victim,Location,TEvent),T), time(T), location(Location), timeEvent(TEvent), victim(Victim).
link(involvesEntity, Location, sighting(Victim,Location,TEvent)) :- happens(lastSeen(Victim,Location,TEvent),T), time(T), location(Location), timeEvent(TEvent), victim(Victim).

%was
%link(involvesEntity, Person1, was(Person1,Location,Person2,TEvent)) :- happens(was(Person1,Location,Person2,TEvent),T), time(T), %person(Person1), person(Person2), timeEvent(TEvent), location(Location).
%link(hasLocation, Location, was(Person1,Location,Person2,TEvent)) :- happens(was(Person1,Location,Person2,TEvent),T), %time(T),person(Person1), person(Person2), timeEvent(TEvent), location(Location).
%link(involvesEntity, Person2, was(Person1,Location,Person2,TEvent)) :- happens(was(Person1,Location,Person2,TEvent),T), time(T), %person(Person1), person(Person2), timeEvent(TEvent), location(Location).

link(involvesEntity, Person1, sighting(Person1,Location,Person2,TEvent)) :- happens(was(Person1,Location,Person2,TEvent),T), time(T), person(Person1), person(Person2), timeEvent(TEvent), location(Location).
link(hasLocation, Location, sighting(Person1,Location,Person2,TEvent)) :- happens(was(Person1,Location,Person2,TEvent),T), time(T),person(Person1), person(Person2), timeEvent(TEvent), location(Location).
link(involvesEntity, Person2, sighting(Person1,Location,Person2,TEvent)) :- happens(was(Person1,Location,Person2,TEvent),T), time(T), person(Person1), person(Person2), timeEvent(TEvent), location(Location).


%sighting
link(involvesEntity, Person1, sighting(Person1,Location,Person2,TEvent)) :- happens(sighting(Person1,Location,Person2,TEvent),T), time(T), person(Person1), location(Location),person(Person2), timeEvent(TEvent). 
link(involvesEntity, Person2, sighting(Person1,Location,Person2,TEvent)) :- happens(sighting(Person1,Location,Person2,TEvent),T), time(T), person(Person1), location(Location),person(Person2), timeEvent(TEvent). 
link(hasLocation, Location, sighting(Person1,Location,Person2,TEvent)) :- happens(sighting(Person1,Location,Person2,TEvent),T), time(T), person(Person1), location(Location),person(Person2), timeEvent(TEvent). 

%kidnapping
link(isPerpetrator, Person, kidnapping(Person,Victim)) :- happens(kidnapping(Person,Victim),T), time(T), person(Person), victim(Victim).
link(isVictimOf,  Victim, kidnapping(Person,Victim)) :- happens(kidnapping(Person,Victim),T), time(T), person(Person), victim(Victim).

%runAway
link(isPerpetrator,Victim, runAway(Victim, Location)) :- happens(runAway(Victim, Location),T), time(T), location(Location), victim(Victim).

%planning
link(metaLevel, planning(Victim,Person,Location1,Object,"runAway"), runAway(Victim,Location2)) :- happens(planning(Victim,Person,Location1,Object,"runAway"),T1), happens(runAway(Victim,Location2),T2), location(Location1), location(Location2), time(T1), time(T2), T1!=T2, victim(Victim), object(Object).
%link(metaLevel, planning(Person1,Person2,Location,Object,"kidnapped"), kidnapping(Person,Victim)) :- kidnapping(Person,Victim).
link(involvesEntity, Victim, planning(Victim,Person,Location,Object,Crime)) :- happens(planning(Victim,Person,Location,Object,Crime), T), object(Object).
link(involvesEntity, Person, planning(Victim,Person,Location,Object,Crime)) :- happens(planning(Victim,Person,Location,Object,Crime), T), object(Object).

%becomesAccomplices
link(isAccomplice, Person, runAway(Victim, Location)) :- happens(runAway(Victim, Location),T1), happens(becomesAccomplices2(Person,Victim,"runAway"),T2), time(T1), time(T2), T1!=T2, person(Person), victim(Victim), location(Location).

%becomesWitness
link(isWitnessOf, Person1, kidnapping(Person2,Victim)) :- happens(becomesWitness(Person1,"kidnapped"),T1), happens(kidnapping(Person2,Victim),T2), time(T1), time(T2), person(Person1), person(Person2), T1!=T2, Person1!=Person2.
link(isWitnessOf, Person, runAway(Victim, Location)) :- happens(becomesWitness(Person,"runAway"),T1), happens(runAway(Victim, Location),T2), time(T1), time(T2), person(Person), location(Location), T1!=T2.

%wasSent
%link(isSubmitter, Person1, wasSent(Resource,Location, Person1,Person2) ):- happens(wasSent(Resource,Location,Person1, Person2),T), %resource(Resource),person(Person2),person(Person1),location(Location), time(T).
%link(involvesEntity, Resource, wasSent(Resource,Location, Person1,Person2) ):- happens(wasSent(Resource,Location, Person1, Person2),T), %resource(Resource),person(Person1), person(Person2),location(Location), time(T).
%link(isReceiver, Person2, wasSent(Resource,Location, Person1,Person2) ):- happens(wasSent(Resource,Location, Person1, Person2),T), %resource(Resource), person(Person1), person(Person2),location(Location), time(T).

link(isSubmitter, Person1, sending(Resource,Location, Person1,Person2) ):- happens(wasSent(Resource,Location,Person1, Person2),T), resource(Resource),person(Person2),person(Person1),location(Location), time(T).
link(involvesEntity, Resource, sending(Resource,Location, Person1,Person2) ):- happens(wasSent(Resource,Location, Person1, Person2),T), resource(Resource),person(Person1), person(Person2),location(Location), time(T).
link(isReceiver, Person2, sending(Resource,Location, Person1,Person2) ):- happens(wasSent(Resource,Location, Person1, Person2),T), resource(Resource), person(Person1), person(Person2),location(Location), time(T).




%happensToKnow
link(knows, Person, Object) :- happens(happensToKnow(Person,Object),T), object(Object), person(Person), time(T).

%happensToLive
link(livesIn, Object, Location) :- happens(happensToLive(Object, Location),T), object(Object), location(Location), time(T).

%finding
link(involvesEntity, Evidence, finding(Evidence,Location)) :- happens(finding(Evidence, Location),T), evidence(Evidence), location(Location), time(T).
link(within, Evidence, Location) :- happens(finding(Evidence, Location),T), evidence(Evidence), location(Location), time(T).

%happensToBelongTo
link(isPropertyOf, Evidence, Person) :- happens(happensToBelongTo(Evidence,Person),T), evidence(Evidence), person(Person), time(T).
link(isPropertyOf, Location, Person) :- happens(happensToBelongTo(Location,Person),T), location(Location), person(Person), time(T).

%happensToBeTheSame
link(sameAs, Person1, Person2) :- happens(happensToBeTheSame(Person1,Person2),T), time(T).

%cheating
link(involvesEntity, Person, cheating(Person,Victim)) :- happens(cheating(Person,Victim),T).
link(isVictimOf, Victim, cheating(Person,Victim)) :- happens(cheating(Person,Victim),T).
link(metaLevel, cheating(Person,Victim),kidnapping(Person,Victim)) :- happens(cheating(Person,Victim),T1),happens(kidnapping(Person,Victim),T2), time(T1), time(T2).






#show link/3.
