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
mvn compile exec:java "-Dexec.mainClass=de.emil.pr3.ui.WorkScheduleManager"
```
## 3. Beispiele (Schnelleinstieg & Live-Demo)
