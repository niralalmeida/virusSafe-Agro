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
    #1. get question id from parameter
    choiceQuestionId = event['queryStringParameters']['choiceQuestionId']

    #2. get data from MySql database
    cursor = connection.cursor()
    sql = 'SELECT * FROM choicequestion WHERE choice_question_id ='
    sql += str(choiceQuestionId)

    cursor.execute(sql)
    rows = cursor.fetchall()

    #3. Construct the body of the response object
    transactionResponseArray = []
    transactionResponseObject = {}
    transactionResponseObject["choiceQuestionImageBinaryCode"] = rows[0][6]
    transactionResponseArray.append(transactionResponseObject)

    #4. Construct http response object
    responseObject = {}
    responseObject['statusCode'] = 200
    responseObject['headers'] = {}
    responseObject['headers']['Content-Type'] = 'application/json'
    responseObject['body'] = json.dumps(transactionResponseArray)

    #5. Return the response object
    return responseObject