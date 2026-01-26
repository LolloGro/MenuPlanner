# Uppstart av MenuPlanner

1. Börja med att klona projektet från GitHub och navigera till projektmappen.
2. Kör: mvn clean package
Applikationen innehåller ett plugin som automatisk installer Node.js och npm lokalt.
3. För Google-inloggning krävs egna OAuth-credentials.
Dessa anges via environment-variabler och delas inte i källkoden.
4. Starta Dock compose
docker compose up
Applikationen använder MySQL som databas och startas automatisk via docker-compose


## För att starta projektet för utveckling

### Frontend
1. Navigera till cd frontend/MenuPlannerFrontend.
2. Kör: npm run dev
3. Öpnna webbläsaren på:
localhost:5173
Under utveckling uppdateras gränssnittet automatiskt när koden ändras

### Backend
1. Starta application via: MenuPlannerApplication
2. I konfigurationen anger du aktiv profil: dev
3. Backend körs på localhost:8080

För att köra backend och frontend parallellt under utveckling används proxy-inställningar i React.
Det medför att frontend API-anrop vidarebefordras till backend och kommunikationen fungerar utan CORS-problem

## Sammanfoga backend och frontend

För att bygga samman Spring boot och React används ett Maven plugin som aktiveras med kommandot:
mvn clean package
Det startar automatisk npm run build och kopierar innehållet från dist mappen i frontend till target mappen i backend.
Då kan hela applikationen köras från samma server via localhost:8080 och proxy behöver inte längre användas.
