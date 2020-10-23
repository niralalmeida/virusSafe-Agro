import boto3
from botocore.exceptions import NoCredentialsError

# access id and secret ket to connect s3 bucket
ACCESS_KEY = 'AKIAZYS4QXTYIHBW3X4K'
SECRET_KEY = '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'

# function which upload image to s3 bucket and give public access to images
def upload_to_aws(local_file, bucket, s3_file):

    # connect to s3 bucket 
    s3 = boto3.client('s3', aws_access_key_id=ACCESS_KEY,
                      aws_secret_access_key=SECRET_KEY)
    try:
        # upload image to a bucket and give public access to it
        s3.upload_file(local_file, bucket, s3_file,ExtraArgs={'ACL':'public-read'})
        base_url = "https://"+bucket+".s3.ap-south-1.amazonaws.com/"+s3_file
        return base_url
    except FileNotFoundError:
        return "The file was not found"
    except NoCredentialsError:
        return "Credentials not available"