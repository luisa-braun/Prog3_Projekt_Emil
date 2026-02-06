# Prog3_Projekt_Emil & Work Schedule Manager – Quickstart

Dieses Projekt ist eine Konsolenanwendung zur Verwaltung von Mitarbeitern und zur automatischen Erstellung von Wochen-Schichtplänen.

<br /> 

## Technische Voraussetzungen
- Java JDK 21
- Apache Maven 3.x
- SQLite
- jOOQ 3.19.x
- JUnit 5

## 1. Setup (Installation & Build)
Stellen Sie sicher, dass Java und Maven installiert sind. Das Projekt benutzt jOOQ zur Code-Generierung. Die jOOQ-Klassen werden automatisch während des Maven-Builds generiert.

#### 1. Öffnen Sie ein Terminal im Hauptverzeichnis des Projekts.

 <sup>(for example. cd C:\Users\User\IdeaProjects\Prog3_Projekt_Emil)</sup> <br /> 
 
#### 2. Führen Sie den Build-Prozess und die Test-Suite aus:

```PowerShell
mvn clean test
```
<br /> 

## 2. Start (Anwendung ausführen)

Die Anwendung wird über das Maven Exec Plugin gestartet. Dies garantiert, dass alle Abhängigkeiten und der Klassenpfad korrekt geladen werden.

Führen Sie im Hauptverzeichnis folgenden Befehl aus:

```PowerShell
mvn compile exec:java 
```
<br /> 

## 3. Anwendung
Die Funktionalität des Work Schedule Managers:

<br /> 

### 1.Mitarbeiterverwaltung

Mitarbeiter anlegen: Über das Menü können neue Mitarbeiter mit Vorname, Nachname und einer Arbeitskapazität (0-60 Std.) hinzugefügt werden.

Validierung: Das System prüft automatisch, ob Namen leer sind oder die Arbeitszeit außerhalb des zulässigen Bereichs liegt.

Listenansicht: Alle gespeicherten Mitarbeiter lassen sich sortiert nach ihrer ID anzeigen.

<br />


### 2.Dienstplan-Generierung

Automatischer Algorithmus: Das System generiert einen Wochenplan, indem es das Iterator Pattern nutzt.

Faire Verteilung: Der EmployeeIterator durchläuft die Mitarbeiterliste zyklisch, sodass jeder Mitarbeiter gleichmäßig für Schichten eingeteilt wird.

Sicherheitsprüfung: Bevor ein Plan erstellt wird, validiert der Iterator, dass die Mitarbeiterliste nicht leer ist und mindestens 2 Employees existieren.

<br />

## 4.Beispiel


Beispiel: Vollständiger Workflow
Mitarbeiter erfassen: Option 2 wählen -> Erstelle 2 Employees(VorName, Nachname und Arbeitskapazität)

Daten prüfen: Option 1 wählen -> Der neue Eintrag erscheint in der Liste.

Plan erstellen: Option 5 wählen -> Der EmployeeIterator weist beide Employees automatisch den Schichten zu.
<img width="994" height="353" alt="image" src="https://github.com/user-attachments/assets/7a186ea2-6ef9-48d7-922f-df71ccde07e5" />
<img width="1387" height="414" alt="image" src="https://github.com/user-attachments/assets/78420790-757f-41ec-8322-e27acd805991" />


