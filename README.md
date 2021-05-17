# Uber For Electric Cars Api Route Data

This is the documentation for ElectroWay, Uber For Electric Cars Api Route Data.

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
    "locationsCoords" :
    [
        {
            "lat" : 47.15751,
            "lon" : 27.58978
        },
        {
            "lat" : 47.174,
            "lon" : 27.575
        }
    ],
    "computeBestOrder" : true,
    "routeType" : "shortest",
    "avoid" : "unpavedRoads",
    "travelMode" : "car",
    "car" :
    {
        "vehicleMaxSpeed" : 160,
        "constantSpeedConsumptionInkWhPerHundredkm" :
        [
            {
                "speed" : 50,
                "consumptionRate" : 8.2
            },
            {
                "speed" : 130,
                "consumptionRate" : 12.6
            }
        ],
        "currentChargeInkWh" : 40,
        "maxChargeInkWh" : 90,
        "auxiliaryInkW" : 5
    }
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
    "routes": [
        {
            "summary": {
                "lengthInMeters": 3216,
                "travelTimeInSeconds": 581,
                "trafficDelayInSeconds": 0,
                "departureTime": "2021-04-02T08:15:36+03:00",
                "arrivalTime": "2021-04-02T08:25:16+03:00",
                "batteryConsumptionInkWh": 0.959574
            },
            "legs": [
                {
                    "summary": {
                        "lengthInMeters": 3216,
                        "travelTimeInSeconds": 581,
                        "trafficDelayInSeconds": 0,
                        "departureTime": "2021-04-02T08:15:36+03:00",
                        "arrivalTime": "2021-04-02T08:25:16+03:00",
                        "batteryConsumptionInkWh": 0.959574
                    },
                    "points": [
                        {
                            "latitude": 47.15727,
                            "longitude": 27.59052
                        },
                        {
                            "latitude": 47.15715,
                            "longitude": 27.59044
                        },
                        ..........
                        ],
            "sections": [
                {
                    "startPointIndex": 0,
                    "endPointIndex": 94,
                    "sectionType": "TRAVEL_MODE",
                    "travelMode": "car"
                }
            ]
        }
    ],
    "formatVersion": "0.0.12"
}
