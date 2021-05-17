  ## API Electroway Documentation 
  
  
   Acest API e folosit pentru a comunica cat mai eficient cu baza de date a aplicatiei Electroway , de a extrage informatiile necesare,  de a adauga sau de a realiza un update asupra lor . Toate cererile si raspunsurile catre API-ul Electroway vor fi in format JSON .
  Principalele functionalitati sunt :
  
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
 # ADD NEW ROLE 
   * Metoda POST 
   * URL : https://localhost:443/user/addrole?roleName=Nume_rol
   * Nu are un request body si nici un response body , insa la parametrii trebuie sa dati numele rolului care poate fi "ROLE_OWNER","ROLE_DRIVER","ROLE_ADMIN"
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
    "rating": "valoare_numerica",
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
# GET REVIEW 
  * E o metoda GET 
  * URL : https://localhost:443/review/id_review/station/id_statie
  * Nu are un request body . 
  * Response-ul :
  ``` json
  {
    "id": 1,
    "textReview": "prea blana statia",
    "rating": 10,
    "user": {
        "username": "cxdsassasffdsvddsd",
        "password": "$2a$10$dwZ5qS.GB/Rc/pYjQKgHlupzhzsrPcjNh0mqH5yzd1/RwUzAbjr8e",
        "firstName": "vcxdsfasafsdsdda23dsdzx",
        "lastName": null,
        "phoneNumber": "66sdssasaffdsdsff232ddssdsasss4335",
        "emailAddress": "electroway@mailinator.com",
        "address1": "xzcdsdsaf324dssxzasdsac",
        "city": "cxzsafcx",
        "country": "Rdsafcxzcxz",
        "zipcode": "73sfad0",
        "roles": [
            {
                "id": 3,
                "name": "ROLE_OWNER"
            }
        ],
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
        "address": "sssss",
        "latitude": 1.1222222223231223E14,
        "longitude": 1.122222222232133E14,
        "user": {
            "username": "cxdsassasffdsvddsd",
            "password": "$2a$10$dwZ5qS.GB/Rc/pYjQKgHlupzhzsrPcjNh0mqH5yzd1/RwUzAbjr8e",
            "firstName": "vcxdsfasafsdsdda23dsdzx",
            "lastName": null,
            "phoneNumber": "66sdssasaffdsdsff232ddssdsasss4335",
            "emailAddress": "electroway@mailinator.com",
            "address1": "xzcdsdsaf324dssxzasdsac",
            "city": "cxzsafcx",
            "country": "Rdsafcxzcxz",
            "zipcode": "73sfad0",
            "roles": [
                {
                    "id": 3,
                    "name": "ROLE_OWNER"
                }
            ],
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
# GET ALL REVIEWS FOR A STATION
  * Metoda GET
  * URL :https://localhost:443/review/all/station/id_statie
  * Response :
  ``` json
  [
    {
        "id": 1,
        "textReview": "prea blana statia",
        "rating": 10,
        "user": {
            "username": "cxdsassasffdsvddsd",
            "password": "$2a$10$dwZ5qS.GB/Rc/pYjQKgHlupzhzsrPcjNh0mqH5yzd1/RwUzAbjr8e",
            "firstName": "vcxdsfasafsdsdda23dsdzx",
            "lastName": null,
            "phoneNumber": "66sdssasaffdsdsff232ddssdsasss4335",
            "emailAddress": "electroway@mailinator.com",
            "address1": "xzcdsdsaf324dssxzasdsac",
            "city": "cxzsafcx",
            "country": "Rdsafcxzcxz",
            "zipcode": "73sfad0",
            "roles": [
                {
                    "id": 3,
                    "name": "ROLE_OWNER"
                }
            ],
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
            "address": "sssss",
            "latitude": 1.1222222223231223E14,
            "longitude": 1.122222222232133E14,
            "user": {
                "username": "cxdsassasffdsvddsd",
                "password": "$2a$10$dwZ5qS.GB/Rc/pYjQKgHlupzhzsrPcjNh0mqH5yzd1/RwUzAbjr8e",
                "firstName": "vcxdsfasafsdsdda23dsdzx",
                "lastName": null,
                "phoneNumber": "66sdssasaffdsdsff232ddssdsasss4335",
                "emailAddress": "electroway@mailinator.com",
                "address1": "xzcdsdsaf324dssxzasdsac",
                "city": "cxzsafcx",
                "country": "Rdsafcxzcxz",
                "zipcode": "73sfad0",
                "roles": [
                    {
                        "id": 3,
                        "name": "ROLE_OWNER"
                    }
                ],
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
]  
  ```
# UPDATE REVIEW 
  * E o metoda PUT
  * URL : https://localhost443:/review/update/id_review/station/id_statie
  * Request : 
  ``` json 
  {
    "textReview": "meh",
    "rating": "5"
  }
```
  * Reponse 
  ``` json 
   {
    "textReview": "meh",
    "rating": "5"
  }
  ``` 
# ADD CAR 
  * E o metoda POST 
  * URL :  http://localhost:443/user/id_user
  * Request body :
  ``` json
  {
    "model": "Tesla Model 3 Standard Range",
    "year": 2019,
    "batteryCapacity": 54,
    "chargingCapacity": 7,
    "plugType": "Type 2",
    "vehicleMaxSpeed": 209,
    "auxiliaryKwh": 1.0
 }
  ```
  * Model response : 
  ```json 
  {
    "id": 1,
    "model": "Tesla Model 3 Standard Range",
    "year": 2019,
    "batteryCapacity": 54.0,
    "chargingCapacity": 7.0,
    "plugType": "Type 2",
    "vehicleMaxSpeed": 209,
    "auxiliaryKwh": 1.0,
    "user": {
        "username": "cxdsassasffdsvddsd",
        "password": "$2a$10$dwZ5qS.GB/Rc/pYjQKgHlupzhzsrPcjNh0mqH5yzd1/RwUzAbjr8e",
        "firstName": "vcxdsfasafsdsdda23dsdzx",
        "lastName": null,
        "phoneNumber": "66sdssasaffdsdsff232ddssdsasss4335",
        "emailAddress": "electroway@mailinator.com",
        "address1": "xzcdsdsaf324dssxzasdsac",
        "city": "cxzsafcx",
        "country": "Rdsafcxzcxz",
        "zipcode": "73sfad0",
        "roles": [
            {
                "id": 2,
                "name": "ROLE_DRIVER"
            },
            {
                "id": 3,
                "name": "ROLE_OWNER"
            },
            {
                "id": 3,
                "name": "ROLE_OWNER"
            }
        ],
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
```
# UPDATE CAR 
 * Metoda PUT 
 * URL : https://localhost:443/car/update/id_masina
 * Request-ul si response-ul au aceeasi forma ca la ADD CAR
# GET ALL CARS FOR USER
  * Metoda GET
  * URL : https://localhost:443/car/all
  * Nu are un response body iar response-ul e o lista de dimensiune variabila de response-uri similare cu ADD CAR
# DELETE CAR 
* Metoda DELETE 
* URL : https://localhost:443/car/delete/id_car
* Nu are un request body si nici un response body

# ADD CHARGING POINT 
* Metoda POST
* URL : https://localhost:443/station/1/points
* Nu are un request body
* Response body :
``` json
{
    "id": 1,
    "station": {
        "id": 1,
        "address": "sssss",
        "latitude": 1.1222222223231223E14,
        "longitude": 1.122222222232133E14,
        "user": {
            "username": "cxdsassasffdsvddsd",
            "password": "$2a$10$dwZ5qS.GB/Rc/pYjQKgHlupzhzsrPcjNh0mqH5yzd1/RwUzAbjr8e",
            "firstName": "vcxdsfasafsdsdda23dsdzx",
            "lastName": null,
            "phoneNumber": "66sdssasaffdsdsff232ddssdsasss4335",
            "emailAddress": "electroway@mailinator.com",
            "address1": "xzcdsdsaf324dssxzasdsac",
            "city": "cxzsafcx",
            "country": "Rdsafcxzcxz",
            "zipcode": "73sfad0",
            "roles": [
                {
                    "id": 2,
                    "name": "ROLE_DRIVER"
                },
                {
                    "id": 3,
                    "name": "ROLE_OWNER"
                },
                {
                    "id": 3,
                    "name": "ROLE_OWNER"
                }
            ],
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
        "hibernateLazyInitializer": {}
    }
}
```
# GET ALL CHARGING POINT FROM A STATION
* Metoda GET 
* URL :https://localhost:443/station/id_statie/points
* Request : - 
* Response similar cu cel de la ADD CHARGING POINT 

# GET CURRENT CHARGING POINT 
* Metoda GET 
* URL : https://localhost:443/station/id_statie/points/id_punct
* Request : -
* Response similar cu cel de la ADD CHARGING POINT 

# DELETE CHARGING POINT 
* Metoda DELETE 
* URL : https://localhost:443/station/id_statie/points/id_punct
* Nu are un request body si nici response body 

# ADD CHARGING PLUG
* Metoda POST 
* URL : https://localhost:443/station/1/points/1
* Request body : 
```json
{
    "status":11,
    "level":1,
    "connectorType":"xd",
    "priceKw":12.2,
    "chargingSpeedKw":12
}
```
 * Response body :
 ```json 
 {
    "id": 1,
    "status": 11,
    "level": 1,
    "connectorType": "xd",
    "priceKw": 12.2,
    "chargingSpeedKw": 12.0,
    "chargingPoint": {
        "id": 1,
        "station": {
            "id": 1,
            "address": "sssss",
            "latitude": 1.1222222223231223E14,
            "longitude": 1.122222222232133E14,
            "user": {
                "username": "cxdsassasffdsvddsd",
                "password": "$2a$10$dwZ5qS.GB/Rc/pYjQKgHlupzhzsrPcjNh0mqH5yzd1/RwUzAbjr8e",
                "firstName": "vcxdsfasafsdsdda23dsdzx",
                "lastName": null,
                "phoneNumber": "66sdssasaffdsdsff232ddssdsasss4335",
                "emailAddress": "electroway@mailinator.com",
                "address1": "xzcdsdsaf324dssxzasdsac",
                "city": "cxzsafcx",
                "country": "Rdsafcxzcxz",
                "zipcode": "73sfad0",
                "roles": [
                    {
                        "id": 2,
                        "name": "ROLE_DRIVER"
                    },
                    {
                        "id": 3,
                        "name": "ROLE_OWNER"
                    },
                    {
                        "id": 3,
                        "name": "ROLE_OWNER"
                    }
                ],
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
}
```
# GET ALL CHARGING PLUG FROM CHARGING POINT 
* Metoda GET 
* URL : https://localhost:443/station/id_statie/points/id_punct/plugs
* Request : -
* Response similar cu cel de la ADD CHARGING PLUG 

# GET CURRENT CHARGING PLUG
* Metoda GET
* URL : https://localhost:443/station/id_statie/points/id_punct/plugs/id_plug
* Nu are un request body : - 
* Response-ul e similar cu cel de la ADD CHARGING PLUG 

# DELETE PLUG 
* Metoda DELETE 
* URL :  https://localhost:443/station/id_statie/points/id_punct/plugs/id_plug
* Nu are un request body si nici response body 

# GET HOME PAYMENT :
 * Metoda GET 
 * URL : https://localhost:443/payment
 * Nu are request body iar response-ul va fi un pagina pentru plati 

# PAY : 
  * Metoda POST 
  * URL : https://localhost:443/payment/pay
  * Request : 
  ```json
 {
    "price": 1,
    "currency": "EUR",
    "method": "paypal",
    "intent": "sale",
    "description": "testing"
}
 ``` 
  * Response : un link catre locul unde se va face plata
  
 # SUCCES PAY 
 * Metoda GET 
 * URL : https://localhost:443/payment/pay/success?paymentId=id_Payment&PayerID=id_payer
 * Ca si parametrii vor fi paymentId si id_payer
 * Response : - 
 
 # CANCEL PAYMENT 
 * Metoda GET 
 * URL : https://localhost:443/payment/pay/cancel
 * Request : - 
 * Response : un link 
 
 # ADD TEMPLATE CAR 
 * Metoda POST 
 * URL : https://localhost:443/templatecar/create
 * Request ca la ADD CAR 
 * Response :
 ```json
 {
    "id": 1,
    "model": "NicuMobil",
    "year": 2019,
    "batteryCapacity": 54.0,
    "chargingCapacity": 7.0,
    "plugType": "Type 2",
    "vehicleMaxSpeed": 209,
    "auxiliaryKwh": 1.0
}
```
# GET ALL TEMPLATE CARS 
* Metoda GET 
* URL : https://localhost:443/templatecar/all
* Request : - 
* Response-ul similar cu cel de la ADD TEMPLATE CAR 
# UPDATE TEMPLATE CAR
* Metoda PUT 
* URL : https://localhost:443/templatecar/all
* Response si request ca la ADD TEMPLATE CAR 

# DELETE TEMPLATE CAR 
 * Metoda DELETE 
 * URL : https://localhost:443/templatecar/delete/id_template 
 * Request : - 
 * Response : - 

