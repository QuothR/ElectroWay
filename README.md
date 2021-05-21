# LaboratorIP


Link documentatie:
https://docs.google.com/document/d/1SVMCBZBSY-kZaLvztw2mwiyWb7dKnzf9moRMKJVa5cE/edit?usp=sharing


Tutorial instalare React:
https://nodejs.org/en/ v 14.16.0
1) intalati oriunde
2) Creati un folder unde vreti  cu orice nume (de preferat react)
3) Din VS deschideti un terminal new si rulati: 
npx create-react-app “nume proiect (avand doar litere mici)” 
4) Pentru a rula proiectul, din terminal rulati comanda : npm start

!!!Aveti grija sa fiti in folderul creat cand faceti instalarile de mai jos:

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

Pentru v.1.6 :
- am setat la axios ca default host, hostul de la server, de acum pentru orice request axios trebuie sa puneti doar path-ul de dupa host 
- de ex: axios.post("http://localhost:443/login", data ...) s a transformat in axios.post("/login", data ...) 

Pentru ro-eng
- npm install react-flags-select --save

---------------------
- !!!!!: trebuie sa aveti versiunile astea de bootstrap in package.json
*"bootstrap": "^4.6.0",
*"react-bootstrap": "^1.5.2"
- !!!!!: daca primiti eroare, intrati in redux -> store.js si urmati "pasii"

