import unittest
import json
import requests
import sqlalchemy
import contextlib
import os

class InvestmentDiaryBaseTestClass(unittest.TestCase):

    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.setup_envs()
        self.engine = self.build_engine()
        self.db_connection = self.engine.connect()
        self.tables = sqlalchemy.inspect(self.engine).get_table_names()
        self.app_endpoint = f"{self.APP_location}:{self.APP_port}/{self.API_version}"

    def setup_envs(self):
        self.DB_password = os.getenv("DB_PASSWORD")
        self.DB_user = os.getenv("DB_USER")
        self.DB_url = os.getenv("DB_HOST") + ":" + os.getenv("DB_PORT")
        self.DB_name = os.getenv("DB_NAME")
        self.DB_vendor = os.getenv("DB_VENDOR")

        self.APP_location = os.getenv("APP_LOCATION")
        self.APP_port = os.getenv("APP_PORT")
        self.API_version = os.getenv("API_VERSION")

    def build_engine(self):
        creation_cmd = f"{self.DB_vendor}://{self.DB_user}:{self.DB_password}@{self.DB_url}/{self.DB_name}"
        return sqlalchemy.create_engine(creation_cmd, echo=False)

    def clear_db(self):
        with contextlib.closing(self.db_connection) as con:
            trans = con.begin()
            for table in reversed(self.tables):
                con.execute(f'''TRUNCATE TABLE {table}''')
            trans.commit()

    def compare_objects(self, obj1, obj2):
        obj1_parsed = json.dumps(obj1, sort_keys=True)
        obj2_parsed = json.dumps(obj2, sort_keys=True)
        self.assertEqual(obj1_parsed, obj2_parsed)

    def compare_lists(self, list1: list, list2: list):
        list1_sorted = json.dumps(sorted(list1))
        list2_sorted = json.dumps(sorted(list2))
        self.assertEqual(list1_sorted, list2_sorted)

    def validate_object_response(self, payload, response: requests.Response, code: int):
        self.assertEqual(response.status_code, code)
        response_content = json.loads(response.text)["data"]
        self.compare_objects(payload, response_content)

    def validate_delete_status_response(self, payload: dict, response: requests.Response, code: int):
        self.assertEqual(response.status_code, code)
        response_content = json.loads(response.text)["status"]
        self.compare_objects(payload, response_content)

    def validate_error_response(self, payload: list, response: requests.Response, code: int):
        self.assertEqual(response.status_code, code)
        response_content = json.loads(response.text)["messages"]
        self.compare_lists(payload, response_content)
    def operations_url(self, suffix=None):
        url = f"http://{self.app_endpoint}/operations"
        url += f"/{suffix}" if suffix else ""
        return url
    def subjects_url(self, suffix=None):
        url = f"http://{self.app_endpoint}/subjects"
        url += f"/{suffix}" if suffix else ""
        return url
    def accounts_url(self, suffix=None):
        url = f"http://{self.app_endpoint}/accounts"
        url += f"/{suffix}" if suffix else ""
        return url