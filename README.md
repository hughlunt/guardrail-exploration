[![Build Status](https://travis-ci.com/mdsol/site_invoices.svg?token=zK8xycCPcHekxvAf2NMp&branch=master)](https://travis-ci.com/mdsol/site_invoices)

# Site Invoices

Wiki Page: https://learn.mdsol.com/display/PAYMENTS/Site+Invoices+Service+API

# Developers
Please add the following line into your `~/.bash_profile` or `~/.profile`
```bash
export SBT_OPTS="-Xms2048m -Xmx2048m -XX:ReservedCodeCacheSize=256m -XX:MaxMetaspaceSize=512m"
```

## API Testing DSL

Sometimes when writing Cucumber tests for the API, we may need to refer to a value created in a previous step.

As an example, assuming that we want to:
- create an Invoice with a random comment
- approve that invoice, and submitting a comment too
- verify that the comment has been kept after the approval

We have a problem though: in order to approve an invoice, we need to know its id. But the creation of the invoice and the approval happen in different steps.  
So we could store the id in a variable (or state) and use it later on, right? But then what if we need to access to another field. Do we store that one as well? You can imagine that this might get easily confusing and repetitive.  
 
For this purpose a new approach has been taken: for each HTTP request we make, we store its response and a simple (and still WIP) DSL has been created to navigate those JSON responses.

The syntax is quite easy, it starts with the response index (starting from zero), followed by a dotted notation that will let us navigate through the json structure)



Example:  
Assuming we have this json returned in the very first http request:

```json
{
    "_links": {
        "self": {
            "href": "/v1/invoices/3c09ec79-8b73-4ba2-8664-3365bb74588e"
        },
        "approve": {
            "href": "/v1/invoices/3c09ec79-8b73-4ba2-8664-3365bb74588e/approve"
        }
    },
    "id": "com:mdsol:site-invoices:3c09ec79-8b73-4ba2-8664-3365bb74588e",
    "origin": "Payee",
    "status": "AwaitingApproval",
    "number": "I123/06",
    "date": "2017-06-08T05:53:43Z",
    "document_sent_date": "2017-06-07T15:12:11Z",
    "file_id": null,
    "study_id": "com:mdsol:ctms-studies:a8a2fdc4-6c27-11e5-a411-85ba1332c235",
    "site_id": "com:mdsol:ctms-sites:b8a2fdc4-6c27-11e5-a411-85ba1332c235",
    "payee_id": "com:mdsol:ctms-payees:c8a2fdc4-6c27-11e5-a411-85ba1332c235",
    "invoice_currency": "USD",
    "budget_currency": "USD",
    "total_invoiced": 55.65,
    "costs": [],
    "comments": [
        {
            "user_id": "com:mdsol:users:df24ca40-be33-4d87-8196-43eab79260e7",
            "date": "2017-09-11T13:09:18.001Z",
            "text": "Please review as soon as possible",
            "resulting_invoice_status": "AwaitingApproval"
        }
    ]
}
```
we could point to the comment's date in this way `%response0.comments(0).date%`

 - `response<index>` `index` is a progressive integer, starting from zero, which lets you point to the exact response in history
 - `.comments` it lets you navigate down the field `comments`
 - `(<index>)` since `comments` is an array, `index` will let you point to the right item inside it
 - `.date` it lets you navigate down the field `date`
 
 In general, we could point to a previous:
 - `request<index>` points to a previous HTTP request
 - `response<index>` points to a previous HTTP response
 - `dataTable<index>` points to a previous Cucumber DataTable, given that we've stored it with `store(dataTable)`. Example:
    ```scala
     When("""blah blah blah""") { dataTable: DataTable =>
        store(dataTable)
     }
    ```
 
 #### Random generation 
 To generate a random uuid for example using the DSL:
 ```
 %random.uuid%
 ```
This could be extended to work for additional items. 

 
 
Let's imagine now we want to retrieve the uuid of the invoice. We could just reference it as `response0.uuid`.  
This is a special case and it works by reading the `id` field and stripping out the URN prefix

#### Matching 
 The placeholder replacement happens also when we want to verify that a response contains certain fields.  
  
 To match any UUID, you could use:  
 ```
 %any.uuid%
 ```


  
### Example's implementation

Let's implement the previous example.  
The feature file for it will be:

```
    Given An Invoice has been created with a random comment
    When the Client sends a POST request to /v1/invoices/%response0.uuid%/approve with the Body
    """
    {
      "comment": "Please review as soon as possible"
    }
    """
    Then the response HTTP status code should be 200
    And the response body should contain:
    """
    {
      "_links":{
         "self":{
            "href":"/v1/invoices/%response0.uuid%"
         },
         "add":{
            "href":"/v1/invoices"
         }
      },
       "id":"com:mdsol:site-invoices:%response0.uuid%",
       "origin":"Payee",
       "status":"Approved",
       "number":"I123/06",
       "date":"%response0.date%",
       "study_id":"com:mdsol:ctms-studies:a8a2fdc4-6c27-11e5-a411-85ba1332c235",
       "site_id":"com:mdsol:ctms-sites:b8a2fdc4-6c27-11e5-a411-85ba1332c235",
       "payee_id":"com:mdsol:ctms-payees:c8a2fdc4-6c27-11e5-a411-85ba1332c235",
       "invoice_currency":"USD",
       "budget_currency":"USD",
       "total_invoiced":128.41,
       "comments":[
         {
            "user_id":"com:mdsol:users:df24ca40-be33-4d87-8196-43eab79260e7",
            "date":"%response1.comments(0).date%",
            "text":"Please review as soon as possible",
            "resulting_invoice_status":"Approved"
         },
         {
           "user_id":"com:mdsol:users:df24ca40-be33-4d87-8196-43eab79260e7",
           "date":"%response0.comments(0).date%",
            "text":"%response0.comments(0).text",
            "resulting_invoice_status":"AwaitingApproval"
         }
        ]
  }
    """

```

Let's comment it

#### Given An Invoice has been created with a random comment
This will create the Invoice and return the response below, that will be stored for later use. Being the first, it will be stored as `response0`

```scala
Given("""^One Invoice has been created with a random comment$""") {
  processPost("/v1/invoices", CreateInvoice.request(comment = "This is a random comment " + Math.random().toInt))
}
```

```json
{
    "_links": {
        "self": {
            "href": "/v1/invoices/3c09ec79-8b73-4ba2-8664-3365bb74588e"
        },
        "approve": {
            "href": "/v1/invoices/3c09ec79-8b73-4ba2-8664-3365bb74588e/approve"
        }
    },
    "id": "com:mdsol:site-invoices:3c09ec79-8b73-4ba2-8664-3365bb74588e",
    "origin": "Payee",
    "status": "AwaitingApproval",
    "number": "I123/06",
    "date": "2017-06-08T05:53:43Z",
    "document_sent_date": "2017-06-07T15:12:11Z",
    "file_id": null,
    "study_id": "com:mdsol:ctms-studies:a8a2fdc4-6c27-11e5-a411-85ba1332c235",
    "site_id": "com:mdsol:ctms-sites:b8a2fdc4-6c27-11e5-a411-85ba1332c235",
    "payee_id": "com:mdsol:ctms-payees:c8a2fdc4-6c27-11e5-a411-85ba1332c235",
    "invoice_currency": "USD",
    "budget_currency": "USD",
    "total_invoiced": 55.65,
    "costs": [],
    "comments": [
        {
            "user_id": "com:mdsol:users:df24ca40-be33-4d87-8196-43eab79260e7",
            "date": "2017-09-11T13:09:18.001Z",
            "text": "Please review as soon as possible",
            "resulting_invoice_status": "AwaitingApproval"
        }
    ]
}
```

#### When the Client sends a POST request to /v1/invoices/%response0.uuid%/approve with the Body
This step will approve the Invoice, but we need to know the previously created Invoice's id.  
Fear no more, we can reference it by writing `%response0.uuid%`
Also, this request will return another response that will be stored ad `response1` (we're not going to use it though for this example)


#### And the response body should contain:

As you can see, in the last step we're just referencing the fields we created in `reponse0` navigating through them with dotted notation.

