# Caleb Hanselman Backend Coding Challenge

## Backend Challenge
Build a REST API (Ruby on Rails preferred) that allows users to submit IP addresses. Fetch the country & city from GeoJS and store the results in a server memory cache. The IP addresses and their data _should not_ be persisted to a database. A second end point should allow users to see all previously queried IP addresses alongside relevant data. The user should also be able to filter these IP addresses by country & city. Each API should return JSON.

## Summary of Approach
I used spring boot because i currently use it every day and i believe it the best robust foundational way to build websites core micro services. As other needs pop up i prefer to make smaller services in django or flask and can learn to do so in RoR. 

I believe that strongly polymorphic design as required by dependancy injection also alows for easy extensibility. For example if i wanted to make new implementation of our persistence layer to use something like redis that would be easily doable and plugin-able via the abstraction interface required by Spring. To read the code quickly here is the general structure of any backend ap i build. 

Controllers - Package that implements API's in a various number of ways (kafka, rest, amqp, sftp)
Domain - Package that holds all domain public information anyone should need. This includes API's and DTO's that will be passed to and from the application
Persistence - Anything that will store objects in memory on or off prem
Service - Any internal micro service that performs one specific functionality. New implementations of the interface can be created for infra specific or version increments of the service.

## Future Features and improvements
I tried to limit myself to under 3 hours of personal time including research on what geojs.io was and testing it via postman. As a result i cut corners in certian parts of my code to get a fully functional and test via unit and integration tests app. If i were to take more time the below things are what i would have added or improved.

### Kafka load balancing for horizontal scaling
Any app that wants to be scalable but uses in cache memory runs into an issue of not being able to scale via pods easily. To get around this i would create an internal kafka topic which upon the persistence layer writing to its cache would have a message sent to that a new additional geodata has been added. Then whenever a pod comes up it will read from the begging of the topic and be able to populate the cache the same as all other running pods. Furthermore if under heavy load/load balancing two pods may receive two seperate requests meaning in their cache they each will be missing the others geo data. By each one publishing onto the topic when adding to their own cache they can pick up the other pods cache rights by listening for any incoming traffic on kafka and adding to their cache at runtime. This will enable horizontal scalability as all pods will be able to stay in sync despite having a load balance on multiple pods

### Multi threading calls to GeoJs
I ran out of time and this was my other key feature i would like to add. A thread pool executor with a configurable start amount of threads and top amount of threads that will take all rest requests to the GeoJsCommunicationServioce and put them on a future. This would allow the controller to service responses much faster. This is not pivatol however as spring boots tomcat server internally puts all controller calls onto its own threadpool by default so in this app there actually is multithreading already going under the hood. Just not optimized

### More thurough unit tests
I only unit tested the controller and the geojs service. I would have liked to have more time to write more edge cases for the IpLocator service and the persistence layer.

### Better error handling
Currently the controller never returns specific 500 errors if an error occurs in the IP address or the query. I would like to wrap this into a restResponseErrorcontroller and give specific server errors with useful messages.