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
    cursor.execute('SELECT * from nutrient')
    rows = cursor.fetchall()

    # 2. Construct the body of the response object
    transactionResponseArray = []
    for row in rows:
        transactionResponseObject = {}
        transactionResponseObject['nutrientId'] = row[0]
        transactionResponseObject['nutrientName'] = row[1]

        # get symptom array
        symptom_sql = 'SELECT * from nutrientsymptom where sym_nutrient_id='
        symptom_sql += str(row[0])
        cursor.execute(symptom_sql)
        symptom_rows = cursor.fetchall()
        symptomArray = []
        for symptom_row in symptom_rows:
            symptomObject = {}
            symptomObject["symId"] = symptom_row[0]
            symptomObject["symContent"] = symptom_row[1]
            symptomObject["symType"] = symptom_row[2]
            symptomArray.append(symptomObject)
        transactionResponseObject['symptom'] = symptomArray

        # get reason array
        reason_sql = 'SELECT * from nutrientreason where reason_nutrient_id='
        reason_sql += str(row[0])
        cursor.execute(reason_sql)
        reason_rows = cursor.fetchall()
        reasonArray = []
        for reason_row in reason_rows:
            reasonObject = {}
            reasonObject["reasonId"] = reason_row[0]
            reasonObject["reasonContent"] = reason_row[1]
            reasonArray.append(reasonObject)
        transactionResponseObject['reason'] = reasonArray

        # get factor array
        factor_sql = 'SELECT * from nutrientfactor where factor_nutrient_id='
        factor_sql += str(row[0])
        cursor.execute(factor_sql)
        factor_rows = cursor.fetchall()
        factorArray = []
        for factor_row in factor_rows:
            factorObject = {}
            factorObject["factorId"] = factor_row[0]
            factorObject["factorContent"] = factor_row[1]
            factorArray.append(factorObject)
        transactionResponseObject['factor'] = factorArray

        # get method array
        method_sql = 'SELECT * from nutrientcorrectionmethod where method_nutrient_id='
        method_sql += str(row[0])
        cursor.execute(method_sql)
        method_rows = cursor.fetchall()
        methodArray = []
        for method_row in method_rows:
            methodObject = {}
            methodObject["methodId"] = method_row[0]
            methodObject["methodContent"] = method_row[1]
            methodArray.append(methodObject)
        transactionResponseObject['method'] = methodArray

        transactionResponseArray.append(transactionResponseObject)

    # 3. Construct http response object
    responseObject = {}
    responseObject['statusCode'] = 200
    responseObject['headers'] = {}
    responseObject['headers']['Content-Type'] = 'application/json'
    responseObject['body'] = json.dumps(transactionResponseArray)

    # 4. Return the response object
    return responseObject
