
# Uber For Electric Cars Api Route Data

This is the documentation for Uber For Electric Cars Api Route Data.

# Route Module

## Search Locations

### Request body
| FieldName | Datatype | Required |
| ----------- | ----------- | ----- |
| Limit | Integer |  yes |
| Query | String |  yes |

### Example Request Body:
```
{
  "limit": 5,
  "query": "lidl Dorohoi"
}
```

### Response

| FiledName | Datatype | 
| ----------- | ----------- |
| results| Array |
| name | String |
| adress | String |
| country | String |
| municipality | String |
| lat | Float |
| lon | Float |

### Example Response Body:
```
{
"results": [
{
  "Name": "Lidl",
  "Adress": "Strada Constantin Dobrogeanu Gherea 25, Dorohoi, 715200",
  "Country": "Romania",
  "Municipality": "Dorohoi",
  "Latitude": 47.95512,
  "Longitude": 26.39868
 },
 {
  "Name": "Parcare Lidl",
  "Adress": "Strada Constantin Dobrogeanu Gherea 25, Dorohoi, 715200",
  "Country": "Romania",
  "Municipality": "Dorohoi"
 }
 ]
}
```

## Generate Route

### Request body
| FieldName | Datatype | Required |
| ----------- | ----------- | ----- |
| locationsCoords | Array |  yes |
|  lat | Double |  yes |
|  lon | Double |  yes |
| computeBestOrder | String |  no |`enter code here`
| routeType | String |  no |
| avoid | String |  no |
| travelMode | String |  no |
| car | Car |  no |
| vehicleMaxSpeed | Integer |  no |
| constantSpeedConsumptionInkWhPerHundredkm | Array |  no |
| speed | Integer |  yes |
| consumptionRate | Integer |  yes |
| currentChargeInkWh | Integer |  no |
| maxChargeInkWh | Integer |  no |
| auxiliaryInkW | Integer |  no |

### Example Request Body:
```
{
  "limit": 5,
  "query": "lidl Dorohoi"
}
```

### Response

| FiledName | Datatype | 
| ----------- | ----------- |
| results| Array |
| name | String |
| adress | String |
| country | String |
| municipality | String |
| lat | Float |
| lon | Float |

### Example Response Body:
```
{
"results": [
{
  "Name": "Lidl",
  "Adress": "Strada Constantin Dobrogeanu Gherea 25, Dorohoi, 715200",
  "Country": "Romania",
  "Municipality": "Dorohoi",
  "Latitude": 47.95512,
  "Longitude": 26.39868
 },
 {
  "Name": "Parcare Lidl",
  "Adress": "Strada Constantin Dobrogeanu Gherea 25, Dorohoi, 715200",
  "Country": "Romania",
  "Municipality": "Dorohoi"
 }
 ]
}`

