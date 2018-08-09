from config import config
import mysql.connector

conn = mysql.connector.connect(user=config['username'],
                              password=config['password'],
                              host=config['hostname'],
                              database=config['database'],
                              auth_plugin='mysql_native_password')
