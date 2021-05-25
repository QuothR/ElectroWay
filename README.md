# LaboratorIP


Link documentatie:
https://docs.google.com/document/d/1SVMCBZBSY-kZaLvztw2mwiyWb7dKnzf9moRMKJVa5cE/edit?usp=sharing


Tutorial instalare React:
https://nodejs.org/en/ v 14.16.0
1) intalati oriunde
2) Creati un folder unde vreti (de ex pe Desktop)  cu orice nume (de preferat react)
3) In Visual Studio Code deschideti folderul creat anterior si apoi deschideti un terminal new si rulati: 
npx create-react-app “nume proiect (avand doar litere mici)”   (((exemplu: npx create-react-app “electroway” )))


!!!Aveti grija ca in terminal sa fiti in proiectul creat (adica ...\react\electroway, daca nu sunteti, scrieti in terminal comanda cd electroway) cand faceti instalarile de mai jos: (instalati tot ce are "npm install" in fata)

Instalare Router pentru management ul paginilor:      
- npm install react-router-dom


Instalare Bootstrap: 
- npm install react-bootstrap bootstrap

Pentru harti:
- npm install react react-dom leaflet
- npm install react-leaflet
- npm install -D @types/leaflet
 
Pt generare facturi:
- npm install easyinvoice --save
- sau mai bun
- npm i easyinvoice

Pentru instalare recharts:
- npm install recharts

Pentru o parte din icons(download):
- npm install react-icons

Pt cod icon: 
- https://react-icons.github.io/react-icons/icons?name=io
- https://www.npmjs.com/package/react-icons

Pentru comunicarea cu backend:
- npm install axios

Pentru gestionarea variabilelor globale (JWT Token ...):
- npm install redux react-redux

Pentru project v.1.1 :
- npm install jwt-decode
- npm install react-router
- npm install redux-thunk

Pentru project v.1.2 :
- npm install react-svg-star-rating
- npm install react-animated-slider@^1

Pentru v.1.3 v.1.4 v.1.5 :
- READ redux -> store.js 

---------------------
4) Inchideti visual studio code, intrati in folderul de pe desktop sau unde l-ati creat react, electroway si inlocuiti DOAR folderele "src" si "public" cu cu "src" si "public" din proiectul nostru de pe github.
---------------------

5) Deschideti visual studio code, iar pentru a rula proiectul, din terminal rulati comanda : npm start (aveti grija sa fiti in proiectul creat)

---------------------
- !!!!!: trebuie sa aveti versiunile astea de bootstrap in package.json
*"bootstrap": "^4.6.0",
*"react-bootstrap": "^1.5.2"
- !!!!!: daca primiti eroare, intrati in redux -> store.js si urmati "pasii"

