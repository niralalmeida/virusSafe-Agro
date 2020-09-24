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

        # get description array
        description_sql = 'SELECT * from virusdescription where des_virus_id='
        description_sql += str(row[0])
        cursor.execute(description_sql)
        description_rows = cursor.fetchall()
        descriptionArray = []
        for description_row in description_rows:
            descriptionObject = {}
            descriptionObject["desId"] = description_row[0]
            descriptionObject["desContent"] = description_row[1]
            descriptionArray.append(descriptionObject)
        transactionResponseObject['description'] = descriptionArray

        # get symptom array
        symptom_sql = 'SELECT * from virussymptom where sym_virus_id='
        symptom_sql += str(row[0])
        cursor.execute(symptom_sql)
        symptom_rows = cursor.fetchall()
        symptomArray = []
        for symptom_row in symptom_rows:
            symptomObject = {}
            symptomObject["symId"] = symptom_row[0]
            symptomObject["symContent"] = symptom_row[1]
            symptomObject["symObjectType"] = symptom_row[2]
            symptomArray.append(symptomObject)
        transactionResponseObject['symptom'] = symptomArray

        # get cause array
        cause_sql = 'SELECT * from viruscause where cause_virus_id='
        cause_sql += str(row[0])
        cursor.execute(cause_sql)
        cause_rows = cursor.fetchall()
        causeArray = []
        for cause_row in cause_rows:
            causeObject = {}
            causeObject["cause_id"] = cause_row[0]
            causeObject["causeContent"] = cause_row[1]
            causeObject["causeType"] = cause_row[2]
            causeArray.append(causeObject)
        transactionResponseObject['cause'] = causeArray

        # get prevention array
        prevention_sql = 'SELECT * from virusprevention where pre_virus_id='
        prevention_sql += str(row[0])
        cursor.execute(prevention_sql)
        prevention_rows = cursor.fetchall()
        preventionArray = []
        for prevention_row in prevention_rows:
            preventionObject = {}
            preventionObject["preId"] = prevention_row[0]
            preventionObject["preContent"] = prevention_row[1]
            preventionObject["preType"] = prevention_row[2]
            preventionArray.append(preventionObject)
        transactionResponseObject['prevention'] = preventionArray

        transactionResponseArray.append(transactionResponseObject)

    # 3. Construct http response object
    responseObject = {}
    responseObject['statusCode'] = 200
    responseObject['headers'] = {}
    responseObject['headers']['Content-Type'] = 'application/json'
    responseObject['body'] = json.dumps(transactionResponseArray)

    # 4. Return the response object
    return responseObject
