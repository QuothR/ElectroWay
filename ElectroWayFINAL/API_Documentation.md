  ## API Electroway Documentation 
  
  
   Acest API e folosit pentru a comunica cat mai eficient cu baza de date a aplicatiei Electroway , de a extrage informatiile necesare,  de a adauga sau de a realiza un update asupra lor . Toate cererile si raspunsurile catre API-ul Electroway vor fi in format JSON .
  Principalele functionalitati sunt :
  
1. **REGISTER**
2. **LOGIN**
3. **FORGOT PASSWORD**
4. **RESET PASSWORD**
5. **ADD STATION**
6. **MODIFY STATION**
7. **VIEW STATION**
8. **STATIONS**
9. **DELETE STATIONS**
10. **GET STATIONS** 
11. **GET CURRENT USER** 
12. **GET ALL USERS**
13. **MODIFY USER**
14. **ADD REVIEW**
15. **GET REVIEW STATION**
16. **ADD CAR**
17. **GET ALL CARS**
18. **ADD CHARGING PLUG**
19. **DELETE CHARGIN PLUG**
20. **GET CURRENT CHARGING PLUG**
21. **DELETE CHARGING PLUG**
22. **ADD CHARGING POINT**
23. **GET CURRENT CHARGING POINT**
24. **GET ALL CHARGING POINTS**
25. **GET CURRENT CHARGING POINT** 
26. **DELETE CHARGING POINT**


# REGISTER
   * E o metoda POST . 
   * E folosit pentru a inregistra utilizatorii .
   * URL : http://localhost:443/register
   * MODEL REQUEST : 
   ```json 
   {
    "username": "cxdsasasffdsvddsd",
    "password": "ds5safdsas!Aa1",
    "firstName": "vcxdsfasafsdsdda23dsdzx",
    "lastName": "xczdafsasfssdsxc",
    "phoneNumber": "66dssasaffdsdsff232ddssdsasss4335",
    "emailAddress": "electroway@mailinator.com",
    "address1": "xzcdsdsaf324dssxzasdsac",
    "city": "cxzsafcx",
    "country": "Rdsafcxzcxz",
    "zipcode": "73sfad0",
    "roles": ["ROLE_DRIVER","ROLE_OWNER"]
}
```
* MODEL RESPONSE
```json
{
    "username": "cxdsasasffdsvddsd",
    "password": "$2a$10$OzgezooSaE4/v6vsMuAeC.It/RdaIPkIspfC3TI6gAk3VT6s1Vwq6",
    "firstName": "vcxdsfasafsdsdda23dsdzx",
    "lastName": null,
    "phoneNumber": "66dssasaffdsdsff232ddssdsasss4335",
    "emailAddress": "electroway@mailinator.com",
    "address1": "xzcdsdsaf324dssxzasdsac",
    "city": "cxzsafcx",
    "country": "Rdsafcxzcxz",
    "zipcode": "73sfad0",
    "roles": [
        "ROLE_DRIVER",
        "ROLE_OWNER"
    ]
}
```
# LOGIN
 * E o metoda POST 
 * E folosit pentru autentificare , in urma careia se va obtine un token . Dupa ce ati inregistrat un cont pe platforma trebuie sa-l activati , activarea se face prin email .
 * URL : http://localhost:443/login
 * MODEL REQUEST 
 ```json
{
  "email" : "dsada@gmail.com",
  "password" :  "dasdasdadsade121"
}
  ```
 * MODEL RESPONSE
 ```json 
   {
      "token" : "12e23efsdfnsdunfsdfssfsdfsdfsdfsd"
   }
```
# FORGOT PASSWORD 
  * E o metoda POST
  * URL : http://localhost:443/forgot_password?email=email_user
  * Ca si request o sa avem niste parametrii : email 
  * Ca si reponse form-ul pentru forgot password 
  
# RESET PASSWORD 
   * E o metoda POST
   * Deobicei parola se reseteaza dupa ce am uitat-o sau pierdut-o , la forgot password user-ul va primi pe email-ul cu care si-a facut contul un email cu un token , acel token va fi folosit la resetarea parolei . 
   * URL :  http://localhost:443/reset_password?token=token_from_forgot
   * Parametrii : token-ul de la forgot si noua parola 
   * Response , deobicei un mesaj cu rezultatul procesului de resetare . 

# ADD STATION 
   * E o metoda POST 
   * URL : http://localhost:443/station
   * Model request : 
   ```json
   {
      "address" : "-",
      "latitude" : "-",
      "longitude" : "-"
   }
   ```
   * Model response:
   ``` json 
   {
      "id" : "valoare_numerica",
      "address" : "-",
      "latitude" : "-",
      "longitude" : "-",
      "user" : {
          "id" : "valoare_numerica" ,
          "username" : "-",
          "password" : "-",
          "firstname" : "-",
          "lastname" : "-",
          "phoneNumber" : "-",
          "emailAddress" : "-",
          "address1" : "-",
          "address2" : "-",
          "city" : "-",
          "region" : "-",
          "country" : "-",
          "zipcode" : "-",
          "passwordResetToken" : "-",
          "enabled": "valoare_booleana",
          "authorities" : "--",
          "accountNonExpired" : "valoare_booleana",
          "accountNonLocked" : "valoare_booleana",
          "credentialsNonExpired" : "valoare_booleana"
      }
   }
   ```
   # MODIFY STATION
   * E o metoda PUT . 
   * URL : http://localhost:443/station/id_statie .
   * Request-ul e fix acelasi cu cel de adaugarea unei statii .
   * Response-ul e acelasi cu cel de la adaugarea uneai statii .
   # DELETE STATION
   * E o metoda DELETE .
   * URL : http://localhost:443/station/id_ul_statie de sters.
   * Utilizatorul trebuie sa fie logat in ordine ca sa stearga o statie . 
   # GET STATION 
   * E o metoda GET .
   * URL :  http://localhost:443/station
   * Nu are un request body .
   * Response-ul e acelasi cu cel de la ADD STATION ,returnand informatii despre fiecare statie in parte.
   # GET LOGGED USER 
   * E o metoda GET care returneaza informatii despre utilizatorul logat .
   * URL : http://localhost:443/user
   * Nu are un request body .
   * Model response:
   ``` json 
   {
      "id" : "valoare_numerica" ,
      "username" : "-",
      "password" : "-",
      "firstname" : "-",
      "lastname" : "-",
      "phoneNumber" : "-",
      "emailAddress" : "-",
      "address1" : "-",
      "address2" : "-",
      "city" : "-",
      "region" : "-",
      "country" : "-",
      "zipcode" : "-",
      "passwordResetToken" : "-",
      "enabled": "valoare_booleana",
      "authorities" : "--",
      "accountNonExpired" : "valoare_booleana",
      "accountNonLocked" : "valoare_booleana",
      "credentialsNonExpired" : "valoare_booleana"
    }
 ```
  # GET ALL USERS
  * E o metoda GET , care returneaza date despre toti utilizatorii 
  * URL : http://localhost:443/users
  * Nu are un request body .
  * Ca si response, se va primi acelasi response-ul de la GET LOGGED USER aplicat pentru fiecare utilizator in parte . 
 # MODIFY CURRENT USER 
 * E o metoda PUT . 
 * URL : http://localhost:443/user
 * Ca si request body-ul si response body-ul e acelasi ca si ADD STATION
 # DELETE USER
 * E o metoda DELETE 
 * URL : http://localhost:443/id_ul_user_de_sters
 * Nu are un request body si nici un response . 
 # ADD REVIEW 
   * E o metoda POST .
   * URL : http://localhost:443/station/id_statie_la_care_Se_adauga_review
   * Model request body :
   ``` json
   {
       "textReview": "-",
        "rating": "-"
   }
   ```
   * Model response body : 
   ``` json 
   {
    "id": "valoare_numerica",
    "textReview": "-",
    "rating": "valoare_numerica"
    "user": {
        "username": "cxdsasasffdsvddsd",
        "password": "$2a$10$YeRzYn/glQX1k2fHVYqW5u3H2ZJn0ym2FE7iM.hFbVsOx4gkJtViG",
        "firstName": "vcxdsfasafsdsdda23dsdzx",
        "lastName": null,
        "phoneNumber": "66dssasaffdsdsff232ddssdsasss4335",
        "emailAddress": "iuliangeorge2017@gmail.com",
        "address1": "xzcdsdsaf324dssxzasdsac",
        "city": "cxzsafcx",
        "country": "Rdsafcxzcxz",
        "zipcode": "73sfad0",
        "roles": [],
        "id": 1,
        "address2": null,
        "region": null,
        "passwordResetToken": null,
        "enabled": true,
        "authorities": null,
        "accountNonExpired": true,
        "accountNonLocked": true,
        "credentialsNonExpired": true
    },
    "station": {
        "id": 1,
        "address": "sss",
        "latitude": 1.122231223E7,
        "longitude": 1.12232133E7,
        "user": {
            "username": "cxdsasasffdsvddsd",
            "password": "$2a$10$YeRzYn/glQX1k2fHVYqW5u3H2ZJn0ym2FE7iM.hFbVsOx4gkJtViG",
            "firstName": "vcxdsfasafsdsdda23dsdzx",
            "lastName": null,
            "phoneNumber": "66dssasaffdsdsff232ddssdsasss4335",
            "emailAddress": "iuliangeorge2017@gmail.com",
            "address1": "xzcdsdsaf324dssxzasdsac",
            "city": "cxzsafcx",
            "country": "Rdsafcxzcxz",
            "zipcode": "73sfad0",
            "roles": [],
            "id": 1,
            "address2": null,
            "region": null,
            "passwordResetToken": null,
            "enabled": true,
            "authorities": null,
            "accountNonExpired": true,
            "accountNonLocked": true,
            "credentialsNonExpired": true
        }
    }
}
```

