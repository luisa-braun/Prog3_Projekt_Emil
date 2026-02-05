# Prog3_Projekt_Emil & Work Schedule Manager – Quickstart



## 1. Setup (Installation & Build)
Stellen Sie sicher, dass Java und Maven installiert sind. Da das Projekt jOOQ zur Code-Generierung nutzt, müssen die Datenbank-Klassen vor dem ersten Start generiert werden.

#### 1. Öffnen Sie ein Terminal im Hauptverzeichnis des Projekts.
 <sup>(for example. cd C:\Users\User\IdeaProjects\Prog3_Projekt_Emil)</sup> 
#### 2. Führen Sie den Build-Prozess und die Test-Suite aus:
```PowerShell
mvn clean test
```

## 2. Start (Anwendung ausführen)

Die Anwendung wird über das Maven Exec Plugin gestartet. Dies garantiert, dass alle Abhängigkeiten und der Klassenpfad korrekt geladen werden.

Führen Sie im Hauptverzeichnis folgenden Befehl aus:

```PowerShell
mvn compile exec:java 
```

## 3. Usage (Anwendung)
Die Funktionalität des Work Schedule Managers:

### 1.Mitarbeiterverwaltung

Mitarbeiter anlegen: Über das Menü können neue Mitarbeiter mit Vorname, Nachname und einer Arbeitskapazität (0-60 Std.) hinzugefügt werden.

Validierung: Das System prüft automatisch, ob Namen leer sind oder die Arbeitszeit außerhalb des zulässigen Bereichs liegt.

Listenansicht: Alle gespeicherten Mitarbeiter lassen sich sortiert nach ihrer ID anzeigen.

### 2.Dienstplan-Generierung

Automatischer Algorithmus: Das System generiert einen Wochenplan, indem es das Iterator Pattern nutzt.

Faire Verteilung: Der EmployeeIterator durchläuft die Mitarbeiterliste zyklisch, sodass jeder Mitarbeiter gleichmäßig für Schichten eingeteilt wird.

Sicherheitsprüfung: Bevor ein Plan erstellt wird, validiert der Iterator, dass die Mitarbeiterliste nicht leer ist.

