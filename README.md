# BlogApplication


## Popis tehnologija korištenih u izradi aplikacije:

- Java 17 
- Spring Boot 3.0.2
- Model View Controller (MVC) architecture
- Spring Data JPA
- Spring Security
- H2 Database (for in-memory database usage during development)
- Thymeleaf (for server-side templating)
- Maven (as a build tool)

## Pokretanje i workflow

### Pokretanje aplikacije
Svrha ovog dokumenta je opisati način pokretanja aplikacije pod nazivom BlogApplication i njezinog workflowa. 
Kako bi se pokrenula aplikacija, uvjet je Java 17. Za buildanje aplikacije potreban je instalirani maven i pristup 
Internetu. 
Nakon što se klonira repozitorij s kodom, potrebno je pomoću mavena buildati izvorni kod i generirati izvodljivu 
(executable) jar datoteku:

```mvnw clean install```

Lokacija izvodljive jar datoteke je
```target/BloggingApp.jar```

Aplikacija se pokreće s naredbom:
```java -jar target/BloggingApp.jar```

Napomene: 

- U uputama za izradu zadatka piše kako stranice aplikacije ne moraju izgledati "lijepo" te da su stranice bez 
ikakvog CSS-a prihvatljive. U aplikaciji u nastavku je CSS primijenjen na jednoj stranici, a to je stranica za 
registraciju.  
- Napravljeni su ogledni primjerci testova. 

### Workflow 
Početna stranica je
```http://localhost:3000```

Funkcije aplikacije su registracija novih korisnika, prijava korisnika, izmjena korisničkih podataka, 
kreiranje blog stranice, uređivanje postojeće blog stranice, pregled blog stranice te brisanje blog stranice. Važno je 
napomenuti da blog može uređivati i brisati samo autor bloga, dok svi korisnici blog mogu pregledavati. 
Prilikom ulaska na početnu stranicu aplikacije, potrebno je kreirati račun odabirom opcije "Register". Klikom na opciju 
"Register" otvara se forma za registraciju u kojoj se unose podaci: ime, prezime, e-mail, lozinka. Nakon što korisnik 
kreira račun, vraća se na formu za prijavu gdje je potrebno prijaviti se odabirom opcije "Login". Kako bi se korisnik 
prijavio u aplikciju, potrebno je unijeti e-mail adresu i lozinku. Nakon što se korisnik prijavi, otvaraju se mogućnosti 
ažuriranja računa, kreiranja novog bloga te odjave. Odabirom opcije ažuriranja računa, otvara se forma gdje postoji 
mogućnost izmjene imena i prezimena. Klikom na gumb "Save", spremaju se podaci o korisniku. Kako bi se kreirao novi blog 
potrebno je odabrati opciju "New Blog" gdje korisnik upisuje naziv bloga te njegov sadržaj. Kako bi se isti spremio i 
objavio, potrebno je kliknuti na gumb "Publish Blog". Nakon objave bloga, isti je moguće uređivati i brisati, ali samo 
od strane vlasnika, odnosno, prijavljenog korisnika koji ga je kreirao. Blog je moguće izmijeniti odabirom opcije "Edit", 
koja korisnika vraća na formu za uređivanje, a kako bi se promjene spremile, potrebno je odabrati gumb "Update Blog". 
Blog je moguće izbrisati odabirom opcije "Delete". Nakon što je korisnik gotov s objavom bloga, može se odjaviti iz 
aplikacije odabirom gumba "Logout". 
