# parking-api

Parking-API is a RESTful API project developed on Java.
The goal of this application is to handle several toll parkings.

Each toll parkings can con contain multiple parking slots of different types:

* the standard parking slots for sedan cars (gasoline-powered);
* parking slots with 20kw power supply for electric cars;
* parking slots with 50kw power supply for electric cars.

Limitations:
20kw electric cars cannot use 50kw power supplies and vice-versa.

Every Parking is free to implement is own pricing policy :
* Some only bills their customer for each hour spent in the parking (nb hours * hour price);
* Some other bill a fixed amount + each hour spent in the parking (fixed amount + nb hours * hour price);

In the future, there will be other pricing policies

This API provides the following functionalities:
* Parking creation/deletion;
* Parking retrieval information;
* Assign a car to a parking slot;
* Bill the customer once the car leaves the parking.

# Technologies

This project uses the following technologies:

* Java 8;
* Spring Boot;
* Database H2 (Relational DBMS);
* Maven.

A Postman collection has been developed to consume the APIs.
The collection can be found under "attachments" directory.
To download Postman: https://www.postman.com/downloads/

# Build and run the application

## Before building
 
What do you need?
* JDK 1.8+;
* Maven 3.2+.

First you need to clone the repository:
`git clone https://github.com/sg2nr/parking-api.git`

Or download the code (ZIP format):
https://github.com/sg2nr/parking-api/archive/master.zip

Once you have cloned the repository, you can build an executable JAR.

## Build and execute

### Maven
If you have Maven already installed, you can build the application by using
`mvn clean install`.
To execute:
`mvn spring-boot:run`

### Maven wrapper
Alternatively, you can execute the Maven wrapper provided:
`./mvnw clean install`
To start the application, simply run:
`./mvnw spring-boot:run`

### JAR
In case you want to build the JAR, you can run:
`mvn clean package` or `./mvnw clean package`.
Then: `java -jar target/parking-api-0.0.1-SNAPSHOT.jar`

### URL
The API is reachable at `localhost:8080`.

### Database
In order to visualize the database, you can use the graphic consolle by typing the following URL on your web browser:
`http://localhost:8080/h2-console/`

No password is needed.

## API description

Here you can find an overview of the operations provided by **parking-api**.

### Retrieve all parkings
#### Request
````
GET /parkings
````
#### Response
`200 OK`
````
[
    {
        "id": 1,
        "name": "Parking de la Promenade",
        "address": "Promenade des Anglais",
        "city": "Nice",
        "statistics": {
            "parkingStatus": {
                "totalSlots": 6,
                "freeSlots": 3
            },
            "statusPerType": {
                "20KwPowerSupply": {
                    "totalSlots": 1,
                    "freeSlots": 0
                },
                "50KwPowerSupply": {
                    "totalSlots": 1,
                    "freeSlots": 0
                },
                "StandardParking": {
                    "totalSlots": 4,
                    "freeSlots": 3
                }
            }
        },
        "pricingPolicy": {
            "id": 2,
            "pricePerHour": 350,
            "policyCurrency": {
                "currencyCode": "EUR",
                "decimalPlaces": 2
            }
        }
    },
    {
        "id": 2,
        "name": "Parking des Pecheurs",
        "address": "Esplanade des Pecheurs",
        "city": "Antibes",
        "statistics": {
            "parkingStatus": {
                "totalSlots": 9,
                "freeSlots": 6
            },
            "statusPerType": {
                "20KwPowerSupply": {
                    "totalSlots": 2,
                    "freeSlots": 1
                },
                "50KwPowerSupply": {
                    "totalSlots": 2,
                    "freeSlots": 2
                },
                "StandardParking": {
                    "totalSlots": 5,
                    "freeSlots": 3
                }
            }
        },
        "pricingPolicy": {
            "id": 1,
            "pricePerHour": 50,
            "policyCurrency": {
                "currencyCode": "EUR",
                "decimalPlaces": 2
            }
        }
    },
    {
        "id": 3,
        "name": "Parking Monte Carlo",
        "address": "Place du Casino",
        "city": "Monaco",
        "statistics": {
            "parkingStatus": {
                "totalSlots": 9,
                "freeSlots": 8
            },
            "statusPerType": {
                "20KwPowerSupply": {
                    "totalSlots": 2,
                    "freeSlots": 1
                },
                "50KwPowerSupply": {
                    "totalSlots": 2,
                    "freeSlots": 2
                },
                "StandardParking": {
                    "totalSlots": 5,
                    "freeSlots": 5
                }
            }
        },
        "pricingPolicy": {
            "basePolicy": {
                "pricePerHour": 50,
                "policyCurrency": {
                    "currencyCode": "EUR",
                    "decimalPlaces": 2
                }
            },
            "id": 3,
            "fixedPrice": 100,
            "policyCurrency": {
                "currencyCode": "EUR",
                "decimalPlaces": 2
            }
        }
    }
]
````
### Retrieve one parking
#### Request
`GET /parkings/{parkinId}`

#### Response
`200 OK`
````
{
    "id": 1,
    "name": "Parking de la Promenade",
    "address": "Promenade des Anglais",
    "city": "Nice",
    "statistics": {
        "parkingStatus": {
            "totalSlots": 6,
            "freeSlots": 3
        },
        "statusPerType": {
            "20KwPowerSupply": {
                "totalSlots": 1,
                "freeSlots": 0
            },
            "50KwPowerSupply": {
                "totalSlots": 1,
                "freeSlots": 0
            },
            "StandardParking": {
                "totalSlots": 4,
                "freeSlots": 3
            }
        }
    },
    "pricingPolicy": {
        "id": 2,
        "pricePerHour": 350,
        "policyCurrency": {
            "currencyCode": "EUR",
            "decimalPlaces": 2
        }
    }
}
````

### Create a new parking 
#### Request
* `name`: String, mandatory;
* `address`: String, mandatory;
* `city`: String, mandatory;
* `requestedSlots` (mandatory): for each slot types specify the number of the slots to be allocated. Possible values of slot types are "50KwPowerSupply", "StandardParking" and "20KwPowerSupply".
* `pricingPolicyId`: Number, mandatory. It represents the id of the pricing policy applied.

`POST /parkings/`
````
{
    "name": "Parking de Sophia",
    "address": "Sophia Antipolis",
    "city": "Biot",
    "requestedSlots": {
        "50KwPowerSupply": 5,
        "StandardParking": 50,
        "20KwPowerSupply": 2
    },
    "pricingPolicyId": 1
}
````
#### Response
`201 Created`
````
{
    "id": 4,
    "name": "Parking de Sophia",
    "address": "Sophia Antipolis",
    "city": "Biot",
    "statistics": {
        "parkingStatus": {
            "totalSlots": 57,
            "freeSlots": 57
        },
        "statusPerType": {
            "20KwPowerSupply": {
                "totalSlots": 2,
                "freeSlots": 2
            },
            "50KwPowerSupply": {
                "totalSlots": 5,
                "freeSlots": 5
            },
            "StandardParking": {
                "totalSlots": 50,
                "freeSlots": 50
            }
        }
    },
    "pricingPolicy": {
        "id": 1,
        "pricePerHour": 50,
        "policyCurrency": {
            "currencyCode": "EUR",
            "decimalPlaces": 2
        }
    }
}
````

### Delete parking
#### Request
`DELETE /parkings/{parkingId}`

#### Response
`204 No Content`

### Retrieve all the pricing policies
#### Request
`GET /pricing-policies`

#### Response
`200 OK`
````
[
    {
        "id": 1,
        "pricePerHour": 50,
        "policyCurrency": {
            "currencyCode": "EUR",
            "decimalPlaces": 2
        }
    },
    {
        "id": 2,
        "pricePerHour": 350,
        "policyCurrency": {
            "currencyCode": "EUR",
            "decimalPlaces": 2
        }
    },
    {
        "basePolicy": {
            "pricePerHour": 50,
            "policyCurrency": {
                "currencyCode": "EUR",
                "decimalPlaces": 2
            }
        },
        "id": 3,
        "fixedPrice": 100,
        "policyCurrency": {
            "currencyCode": "EUR",
            "decimalPlaces": 2
        }
    }
]
 ````

### Retrieve a pricing policy
#### Request
`GET /pricing-policies/{pricingPolicyId}`

#### Response
`200 OK`
````
{
    "pricePerHour": 50,
    "id": 1,
    "policyCurrency": {
        "currencyCode": "EUR",
        "decimalPlaces": 2
    }
}
````

### Check-in car in parking
#### Request
`POST /parkings/{parkingId}/order`
````json
{
    "carPlate": "ZZ98709",
    "serviceRequested": "StandardParking"
}
````
#### Response
`201 Created`
````json
{
    "orderId": 8,
    "checkin": "2020-04-01T15:52:26.224+02:00",
    "carPlate": "ZZ98709",
    "parkingId": 1,
    "slotNumber": 3
}
````
#### Error examples
##### No parking slot available for the requested car type
`400 Bad Request`
````json
{
    "timestamp": "2020-04-01T13:20:21.919+0000",
    "status": 400,
    "error": "Bad Request",
    "message": "No slot available.",
    "path": "/parkings/1/order"
}
````
##### Check-in of a car already parked
`400 Bad Request`
````json
{
    "timestamp": "2020-04-01T13:20:24.515+0000",
    "status": 400,
    "error": "Bad Request",
    "message": "Vehicle is currently parked.",
    "path": "/parkings/1/order"
}
````

### Check-out car
#### Request
`POST /parkings/checkout?orderId={orderId}`

#### Response
`201 Created`
````json
{
    "orderDetails": {
        "orderId": 1,
        "checkin": "2020-03-31T10:23:15+02:00",
        "carPlate": "AA123AA",
        "parkingId": 1,
        "slotNumber": 2
    },
    "checkout": "2020-04-01T21:57:43.28+02:00",
    "amountToPay": {
        "amount": 12250,
        "currency": {
            "currencyCode": "EUR",
            "decimalPlaces": 2
        }
    }
}
````

#### Error example
##### Check-out of the same order twice
`400 Bad Request`
````json
{
    "timestamp": "2020-04-01T13:20:44.632+0000",
    "status": 400,
    "error": "Bad Request",
    "message": "This order has already been checked-out.",
    "path": "/parkings/checkout"
}
````