# AddresssBook

This was done as a "Coding Challenge" that was done in around 11 hours for an interview process with a company.
Below were the requirements:

*Address Book API
We would like you to build a RESTful API for an address book with an Elasticsearch data store.  

A RESTful API is one that exposes it's interface over HTTP, and follows standard conventions for how data should be retrieved.

*Spec Definition

The form of the endpoint spec will be defined as:

VERB /path/{pathParam}/subpath?queryParam={}

The VERB is part of the HTTP spec and can be defined as GET, POST, PUT, DELETE (and a few others).  We will only be using the ones specified.

The path definition defines the URL path that we expect the api to be tied to.  

{pathParam} defines a variable element that should be interpreted by the API.  As an example:

Given the simple spec:

GET /user
GET /user/{name}

These endpoints would allow for the following URL paths:

/user
/user/bob
/user/jane
/user/sam

Additionally query parameters (?key=value) should be interpreted similarly but in the query string (everything after the ? in the URL).

Each endpoint should have a defined input and output, and should make sense to the person using it.



*API Definition

So for this API, the endpoints (aka methods) that we want in the api are as follows:

GET /contact?pageSize={}&page={}&query={}

This endpoint will providing a listing of all contacts, you will need to allow for a defined pageSize (number of results allowed back), and the ability to offset by page number to get multiple pages. Query also should be a query for queryStringQuery as defined by Elasticsearch that you can pass directly in the Elasticsearch call.

POST /contact

This endpoint should create the contact.  Given that name should be unique, this may need to be enforced manually.  

GET /contact/{name}

This endpoint should return the contact by a unique name. This name should be specified by the person entering the data.  

PUT /contact/{name}

This endpoint should update the contact by a unique name (and should error if not found)

DELETE /contact/{name}

This endpoint should delete the contact by a unique name (and should error if not found)

*Testability
The application you build should be testable.  It should be structured in a way to allow for easy verification of all logic (think functions vs methods).  
Technical Requirements

The following define what is expected and what the constraints are:

The project should be hosted in a repository on github
You can implement this problem in any language you desire.
The API needs to implement all of the above and be backed by Elasticsearch. 
The data model of a contact needs to be defined, and should make reasonable assumptions.  Additionally bounds of input values you should be defined for the data model.  E.g. A phone number should not allow 1000 digits (unless there is a good reason).
The API needs to be exposed over HTTP (as defined by RESTful architecture)
The host/port for the elasticsearch server should be configurable to allow an evaluator to run it locally.
The code should have automated unit tests (i.e. additional code you write) verifying the storage/retrieval aspect of the code (you do not need to test the REST request handlers) and any business logic that was added.  This means there should be a layer of separation between the REST handler and your actual logic


Use one of the following:
Java
Spark - A simple JVM based framework for web backends
Elasticsearch - The primary elasticsearch API
JUnit - The standard Java unit testing framework






