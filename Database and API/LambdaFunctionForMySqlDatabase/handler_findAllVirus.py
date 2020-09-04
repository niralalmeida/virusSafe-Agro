import pymysql
import json

# Configuration values:
endpoint = 'tomato-virus-1.chavbdc9w8gh.us-east-1.rds.amazonaws.com'
username = 'hoyyang'
password = 'tomatovirus123'
database_name = 'tomatovirus_mysql_db'

# Connection
connection = pymysql.connect(endpoint, user=username, passwd=password, db=database_name)


def lambda_handler(event, context):

    # 1. get data from MySql database
    cursor = connection.cursor()
    cursor.execute('SELECT * from virus')
    rows = cursor.fetchall()

    # 2. Construct the body of the response object
    transactionResponseArray = []
    for row in rows:
        transactionResponseObject = {}
        transactionResponseObject['virusId'] = row[0]
        transactionResponseObject['virusFullName'] = row[1]
        transactionResponseObject['virusAbbreviation'] = row[2]
        transactionResponseObject['virusDescription'] = row[3]
        transactionResponseObject['symptoms'] = row[4]
        transactionResponseObject['causes'] = row[5]
        transactionResponseObject['spread'] = row[6]
        transactionResponseObject['prevention'] = row[7]
        transactionResponseObject['virusDistribution'] = row[8]
        transactionResponseArray.append(transactionResponseObject)

    # 3. Construct http response object
    responseObject = {}
    responseObject['statusCode'] = 200
    responseObject['headers'] = {}
    responseObject['headers']['Content-Type'] = 'application/json'
    responseObject['body'] = json.dumps(transactionResponseArray)

    # 4. Return the response object
    return responseObject
