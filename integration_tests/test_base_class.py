import unittest
import json
import requests
import sqlalchemy
import contextlib

class InvestmentDiaryBaseTestClass(unittest.TestCase):

    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.setup_envs()
        self.engine = self.build_engine()
        self.db_connection = self.engine.connect()
        self.tables = sqlalchemy.inspect(self.engine).get_table_names()

    def setup_envs(self):
        self.DB_password = "billionaire"
        self.DB_user = "investor"
        self.DB_url = "localhost:3306"
        self.DB_name = "investment"
        self.DB_vendor = "mysql"

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
        url = "http://localhost:8080/v1/operations"
        url += f"/{suffix}" if suffix else ""
        return url
    def subjects_url(self, suffix=None):
        url = "http://localhost:8080/v1/subjects"
        url += f"/{suffix}" if suffix else ""
        return url
    def accounts_url(self, suffix=None):
        url = "http://localhost:8080/v1/accounts"
        url += f"/{suffix}" if suffix else ""
        return url