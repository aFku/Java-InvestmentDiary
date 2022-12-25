import unittest
import json
import requests
import sqlalchemy


class InvestmentDiaryBaseTestClass(unittest.TestCase):

    def setup_envs(self):
        self.DB_password = "billionaire"
        self.DB_user = "investor"
        self.DB_url = "localhost:3306"
        self.DB_name = "investment"
        self.DB_vendor = "mysql"

    def build_engine(self):
        creation_cmd = f"{self.DB_vendor}://{self.DB_user}:{self.DB_password}@{self.DB_url}/{self.DB_name}"
        return sqlalchemy.create_engine(creation_cmd, echo=False)

    def compare_dicts(self, dict1: dict, dict2: dict):
        dict1_parsed = json.dumps(dict1, sort_keys=True)
        dict2_parsed = json.dumps(dict2, sort_keys=True)
        self.assertEqual(dict1_parsed, dict2_parsed)

    def compare_lists(self, list1: list, list2: list):
        list1_sorted = json.dumps(sorted(list1))
        list2_sorted = json.dumps(sorted(list2))
        self.assertEqual(list1_sorted, list2_sorted)

    def validate_single_response(self, payload: dict, response: requests.Response, code: int):
        self.assertEqual(response.status_code, code)
        response_content = json.loads(response.text)["data"]
        self.compare_dicts(payload, response_content)

    def validate_delete_status_response(self, payload: dict, response: requests.Response, code: int):
        self.assertEqual(response.status_code, code)
        response_content = json.loads(response.text)["status"]
        self.compare_dicts(payload, response_content)

    def validate_error_response(self, payload: list, response: requests.Response, code: int):
        self.assertEqual(response.status_code, code)
        response_content = json.loads(response.text)["messages"]
        self.compare_lists(payload, response_content)
