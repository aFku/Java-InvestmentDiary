import requests
from datetime import date, timedelta
import json

from test_base_class import InvestmentDiaryBaseTestClass


class TestMarketOperations(InvestmentDiaryBaseTestClass):

    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)

    def setUp(self):
        self.clear_db()
        self.prepare_related_objects()

    def create_market_operations_for_search_test(self):
        with open('brokeraccounts.json', 'r') as f:
            data = json.load(f)
            for account_index in range(3):
                requests.post(self.accounts_url(), json=data[account_index])
        with open('marketsubjects.json', 'r') as f:
            data = json.load(f)
            for subject_index in range(3):
                requests.post(self.subjects_url(), json=data[subject_index])
        with open('marketoperations.json', 'r') as f:
            data = json.load(f)
            data = sorted(data, key=lambda d: d["operationType"])
            for operation in data:
                response = requests.post(self.operations_url(), json=operation)

    def prepare_related_objects(self):
        payload = {
            "provider": "testProvider",
            "accountId": "HGF123"
        }
        requests.post(self.accounts_url(), json=payload)

        payload = {
            "name": "testSubject",
            "infoSources": "",
            "hasDividend": True
        }
        requests.post(self.subjects_url(), json=payload)

    def build_operation_payload(self, **kwargs):
        template = {
          "operationDate": date.today().strftime("%Y-%m-%d"),
          "pricePerOne": 10.0,
          "volume": 10,
          "operationType": "BUY",
          "description": {
            "advantages": "",
            "disadvantages": "",
            "reason": "There is no reason"
          },
          "subjectId": 1,
          "accountId": 1
        }
        for key, value in kwargs.items():
            template[key] = value
        return template

    def test_market_operation_creation_success(self):
        payload = self.build_operation_payload()
        response = requests.post(self.operations_url(), json=payload)
        expected = payload
        expected["id"] = 1
        expected["creationDate"] = date.today().strftime("%Y-%m-%d")
        self.validate_object_response(expected, response, 200)

    def test_market_operation_creation_failure_without_key(self):
        payload = self.build_operation_payload()
        payload.pop("volume")
        expected = [
            "volume : 'volume' field is required"
        ]
        response = requests.post(self.operations_url(), json=payload)
        self.validate_error_response(expected, response, 400)

    def test_market_operation_creation_failure_with_empty_value(self):
        payload = self.build_operation_payload(operationType=" ")
        expected = [
            "operationType : 'operationType' field is required",
        ]

        response = requests.post(self.operations_url(), json=payload)
        self.validate_error_response(expected, response, 400)

    def test_market_operation_creation_failure_with_null_value(self):
        payload = self.build_operation_payload(pricePerOne=None)
        expected = [
            "pricePerOne : 'pricePerOne' field is required"
        ]
        response = requests.post(self.operations_url(), json=payload)
        self.validate_error_response(expected, response, 400)

    def test_market_operation_creation_failure_constraints_validation(self):
        payload = self.build_operation_payload(
            operationDate=(date.today() + timedelta(days=1)).strftime("%Y-%m-%d"),
            pricePerOne=-1,
            volume=0,
            subjectId=0,
            accountId=0
        )
        expected = [
            "operationDate : You cannot create operation from feature",
            "pricePerOne : Price cannot be negative number or zero",
            "volume : Volume cannot be negative number or zero",
            "subjectId : Subjects id is always positive integer",
            "accountId : Account id is always positive integer"
        ]
        response = requests.post(self.operations_url(), json=payload)
        self.validate_error_response(expected, response, 400)

    def test_market_operation_creation_failure_account_not_found(self):
        payload = self.build_operation_payload(accountId=10)
        response = requests.post(self.operations_url(), json=payload)
        expected = [
            "Broker account with id: 10 not found"
        ]
        self.validate_error_response(expected, response, 404)

    def test_market_operation_creation_failure_subject_not_found(self):
        payload = self.build_operation_payload(subjectId=15)
        response = requests.post(self.operations_url(), json=payload)
        expected = [
            "Market subject with id: 15 not found"
        ]
        self.validate_error_response(expected, response, 404)

    def test_market_operation_delete_success(self):
        payload = self.build_operation_payload()
        requests.post(self.operations_url(), json=payload)

        expected = {
            "id": 1,
            "deleted": True,
            "kind": "marketOperation"
        }
        response = requests.delete(self.operations_url("1"))
        self.validate_delete_status_response(expected, response, 200)

    def test_market_operation_delete_failure_not_found(self):
        expected = [
            "Market operation with id: 2 not found"
        ]
        response = requests.delete(self.operations_url("2"))
        self.validate_error_response(expected, response, 404)

    def test_market_operation_get_operation_successful(self):
        payload = self.build_operation_payload()
        requests.post(self.operations_url(), json=payload)
        response = requests.get(self.operations_url("1"))
        expected = payload
        expected["id"] = 1
        expected["creationDate"] = date.today().strftime("%Y-%m-%d")
        self.validate_object_response(expected, response, 200)

    def test_market_operations_get_operation_not_found(self):
        response = requests.get(self.operations_url("10"))
        expected = [
            "Market operation with id: 10 not found"
        ]
        self.validate_error_response(expected, response, 404)

    def test_market_operation_get_operation_page_successful(self):
        payload1 = self.build_operation_payload(volume=30)
        requests.post(self.operations_url(), json=payload1)
        expected1 = payload1
        expected1["id"] = 1
        expected1["creationDate"] = date.today().strftime("%Y-%m-%d")

        payload2 = self.build_operation_payload(operationType="SELL")
        requests.post(self.operations_url(), json=payload2)
        expected2 = payload2
        expected2["id"] = 2
        expected2["creationDate"] = date.today().strftime("%Y-%m-%d")

        payload = {
            "page": 0,
            "size": 2,
            "sorted": ["id"]
        }

        response = requests.get(self.operations_url(), params=payload)
        expected = [expected1, expected2]
        self.validate_object_response(expected, response, 200)

    def test_market_operation_get_operation_page_empty(self):
        payload = {
            "page": 0,
            "size": 2,
            "sorted": ["id"]
        }
        response = requests.get(self.operations_url(), params=payload)
        expected = []
        self.validate_object_response(expected, response, 200)

    def test_market_operation_get_operation_page_not_found(self):
        payload = {
          "page": 10,
          "size": 2,
          "sort": ["id"]
        }
        response = requests.get(self.operations_url(), params=payload)
        expected = ["There is no page with number 10 for this resource. Max page index with size 2 is 0"]
        self.validate_error_response(expected, response, 404)

    def test_market_operation_get_search_equality(self):
        self.create_market_operations_for_search_test()
        payload = {
          "page": 0,
          "size": 45,
          "sort": ["id"]
        }
        query = "?search=brokerAccount:2"
        response = requests.get(self.operations_url() + query, params=payload)
        response_parsed = json.loads(response.text)["data"]
        self.assertEqual(response.status_code, 200)
        self.assertEqual(len(response_parsed), 17)

    def test_market_operation_get_search_equality_operation_type(self):
        self.create_market_operations_for_search_test()
        payload = {
          "page": 0,
          "size": 45,
          "sort": ["id"]
        }
        query = "?search=operationType:SELL"
        response = requests.get(self.operations_url() + query, params=payload)
        response_parsed = json.loads(response.text)["data"]
        self.assertEqual(response.status_code, 200)
        self.assertEqual(len(response_parsed), 19)

    def test_market_operation_get_search_negation(self):
        self.create_market_operations_for_search_test()
        payload = {
          "page": 0,
          "size": 45,
          "sort": ["id"]
        }
        query = "?search=marketSubject!3"
        response = requests.get(self.operations_url() + query, params=payload)
        response_parsed = json.loads(response.text)["data"]
        self.assertEqual(response.status_code, 200)
        self.assertEqual(len(response_parsed), 26)

    def test_market_operation_get_search_greater_than(self):
        self.create_market_operations_for_search_test()
        payload = {
          "page": 0,
          "size": 45,
          "sort": ["id"]
        }
        query = "?search=volume>50"
        response = requests.get(self.operations_url() + query, params=payload)
        response_parsed = json.loads(response.text)["data"]
        self.assertEqual(response.status_code, 200)
        self.assertEqual(len(response_parsed), 23)

    def test_market_operation_get_search_lesser_than(self):
        self.create_market_operations_for_search_test()
        payload = {
          "page": 0,
          "size": 45,
          "sort": ["id"]
        }
        query = "?search=pricePerOne<50"
        response = requests.get(self.operations_url() + query, params=payload)
        response_parsed = json.loads(response.text)["data"]
        self.assertEqual(response.status_code, 200)
        self.assertEqual(len(response_parsed), 24)

    def test_add_market_operation_sell_with_not_enough_volume(self):
        payload = self.build_operation_payload()
        requests.post(self.operations_url(), json=payload)
        payload = self.build_operation_payload(operationType="SELL", volume=15)
        response = requests.post(self.operations_url(), json=payload)
        expected = ["Trying to sell: 15 when owned: 10"]
        self.validate_error_response(expected, response, 400)
