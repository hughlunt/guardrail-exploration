package persistence

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest

class Test {
  val dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.standard.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-east-1")).build)

  def createTableIfNotExist(create: CreateTableRequest): Unit = dynamoDB.createTable(create)

  def readFromTable(db: DynamoDB) = db.getTable("budget")

}