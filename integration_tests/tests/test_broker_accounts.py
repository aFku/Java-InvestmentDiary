import requests
import sqlalchemy
import contextlib

from test_base_class import InvestmentDiaryBaseTestClass
from datetime import date


class TestBrokerAccounts(InvestmentDiaryBaseTestClass):

    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)

    def setUp(self):
        self.clear_db()

    def test_broker_account_creation_success(self):
        payload = {
            "provider": "ASDFGH",
            "accountId": "123456A"
        }
        expected = {
            "id": 1,
            "provider": "ASDFGH",
            "accountId": "123456A",
            "creationDate": date.today().strftime("%Y-%m-%d")
        }

        response = requests.post(self.accounts_url(), json=payload)
        self.validate_object_response(expected, response, 200)

    def test_broker_account_creation_failure_without_key(self):
        payload = {
            "provider": "ASDFGH",
        }

        expected = [
            "accountId : 'accountId' field is required"
        ]

        response = requests.post(self.accounts_url(), json=payload)
        self.validate_error_response(expected, response, 400)


    def test_broker_account_creation_failure_with_empty_value(self):
        payload = {
            "provider": " ",
            "accountId": "123456A"
        }
        expected = [
            "provider : 'provider' field is required",
            "provider : 'provider' field should be between 3 and 32 character long"
        ]

        response = requests.post(self.accounts_url(), json=payload)
        self.validate_error_response(expected, response, 400)

    def test_broker_account_creation_failure_with_null_value(self):
        payload = {
            "provider": None,
            "accountId": "123456A"
        }
        expected = [
            "provider : 'provider' field is required"
        ]

        response = requests.post(self.accounts_url(), json=payload)
        self.validate_error_response(expected, response, 400)

    def test_broker_account_creation_failure_constraints_validation(self):
        payload = {
            "provider": "PR",
            "accountId": "A" * 33
        }
        expected = [
            "provider : 'provider' field should be between 3 and 32 character long",
            "accountId : 'accountId' field should be between 1 and 32 character long"
        ]

        response = requests.post(self.accounts_url(), json=payload)
        self.validate_error_response(expected, response, 400)

    def test_broker_account_delete_success(self):
        payload = {
            "provider": "ASDFGH",
            "accountId": "123456A"
        }
        requests.post(self.accounts_url(), json=payload)

        expected = {
            "id": 1,
            "deleted": True,
            "kind": "brokerAccount"
        }
        response = requests.delete(self.accounts_url("1"))
        self.validate_delete_status_response(expected, response, 200)

    def test_broker_account_delete_failure_not_found(self):
        expected = [
            "Broker account with id: 2 not found"
        ]
        response = requests.delete(self.accounts_url("2"))
        self.validate_error_response(expected, response, 404)

    def test_broker_account_update_success(self):
        payload = {
            "provider": "testprovider",
            "accountId": "123456A"
        }
        requests.post(self.accounts_url(), json=payload)
        payload = {
            "provider": "provider",
            "accountId": "97865A"
        }
        response = requests.put(self.accounts_url("1"), json=payload)
        expected = {
            "id": 1,
            "provider": "provider",
            "accountId": "97865A",
            "creationDate": date.today().strftime("%Y-%m-%d")
        }
        self.validate_object_response(expected, response, 200)

    def test_broker_account_update_part_success(self):
        payload = {
            "provider": "testprovider",
            "accountId": "123456A"
        }
        requests.post(self.accounts_url(), json=payload)
        payload = {
            "provider": "provider-part"
        }
        response = requests.put(self.accounts_url("1"),json=payload)
        expected = {
            "id": 1,
            "provider": "provider-part",
            "accountId": "123456A",
            "creationDate": date.today().strftime("%Y-%m-%d")
        }
        self.validate_object_response(expected, response, 200)

    def test_broker_accounts_update_failure_not_found(self):
        payload = {
            "provider": "provider",
            "accountId": "97865A"
        }
        response = requests.put(self.accounts_url("10"), json=payload)
        expected = [
            "Broker account with id: 10 not found"
        ]
        self.validate_error_response(expected, response, 404)

    def test_broker_accounts_get_account_successful(self):
        payload = {
            "provider": "testprovider",
            "accountId": "123456A"
        }
        requests.post(self.accounts_url(), json=payload)
        response = requests.get(self.accounts_url("1"))
        expected = {
            "id": 1,
            "provider": "testprovider",
            "accountId": "123456A",
            "creationDate": date.today().strftime("%Y-%m-%d")
        }
        self.validate_object_response(expected, response, 200)

    def test_broker_accounts_get_account_not_found(self):
        response = requests.get(self.accounts_url("10"))
        expected = [
            "Broker account with id: 10 not found"
        ]
        self.validate_error_response(expected, response, 404)

    def test_broker_accounts_get_account_page_successful(self):
        payload = {
            "provider": "provider",
            "accountId": "97865A"
        }
        requests.post(self.accounts_url(), json=payload)
        payload = {
            "provider": "provider2",
            "accountId": "HGS321"
        }
        requests.post(self.accounts_url(), json=payload)
        payload = {
            "page": 0,
            "size": 2,
            "sorted": ["id"]
        }
        response = requests.get(self.accounts_url(), params=payload)
        expected = [
            {
                "id": 1,
                "provider": "provider",
                "accountId": "97865A",
                "creationDate": date.today().strftime("%Y-%m-%d")
            },
            {
                "id": 2,
                "provider": "provider2",
                "accountId": "HGS321",
                "creationDate": date.today().strftime("%Y-%m-%d")
            }
        ]
        self.validate_object_response(expected, response, 200)

    def test_broker_account_get_account_page_empty(self):
        payload = {
            "page": 0,
            "size": 2,
            "sorted": ["id"]
        }
        response = requests.get(self.accounts_url(), params=payload)
        expected = []
        self.validate_object_response(expected, response, 200)

    def test_broker_account_get_account_page_not_found(self):
        payload = {
          "page": 10,
          "size": 2,
          "sort": ["id"]
        }
        response = requests.get(self.accounts_url(), params=payload)
        expected = ["There is no page with number 10 for this resource. Max page index with size 2 is 0"]
        self.validate_error_response(expected, response, 404)
