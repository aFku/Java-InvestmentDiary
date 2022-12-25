import json

import requests
import sqlalchemy
import contextlib

from test_base_class import InvestmentDiaryBaseTestClass
from datetime import date


class TestBrokerAccounts(InvestmentDiaryBaseTestClass):

    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.setup_envs()
        self.engine = self.build_engine()
        self.db_connection = self.engine.connect()
        self.tables = sqlalchemy.inspect(self.engine).get_table_names()

    def setUp(self):
        with contextlib.closing(self.db_connection) as con:
            trans = con.begin()
            for table in reversed(self.tables):
                con.execute(f'''TRUNCATE TABLE {table}''')
            trans.commit()

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

        response = requests.post("http://localhost:8080/v1/accounts", json=payload)
        self.validate_single_response(expected, response, 200)

    def test_broker_account_creation_failure_without_key(self):
        payload = {
            "provider": "ASDFGH",
        }

        expected = [
            "accountId : 'accountId' field is required"
        ]

        response = requests.post("http://localhost:8080/v1/accounts", json=payload)
        self.validate_error_response(expected, response, 400)


    def test_broker_account_creation_failure_with_empty_value(self):
        payload = {
            "provider": "",
            "accountId": "123456A"
        }
        expected = [
            "provider : 'provider' field is required",
            "provider : 'provider' field should be between 3 and 32 character long"
        ]

        response = requests.post("http://localhost:8080/v1/accounts", json=payload)
        self.validate_error_response(expected, response, 400)

    def test_broker_account_creation_failure_with_null_value(self):
        payload = {
            "provider": None,
            "accountId": "123456A"
        }
        expected = [
            "provider : 'provider' field is required"
        ]

        response = requests.post("http://localhost:8080/v1/accounts", json=payload)
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

        response = requests.post("http://localhost:8080/v1/accounts", json=payload)
        self.validate_error_response(expected, response, 400)

    def test_broker_account_delete_success(self):
        payload = {
            "provider": "ASDFGH",
            "accountId": "123456A"
        }
        requests.post("http://localhost:8080/v1/accounts", json=payload)

        expected = {
            "id": 1,
            "deleted": True,
            "kind": "brokerAccount"
        }
        response = requests.delete("http://localhost:8080/v1/accounts/1")
        self.validate_delete_status_response(expected, response, 200)

    def test_broker_account_delete_failure_not_found(self):
        expected = [
            "Broker account with id: 2 not found"
        ]
        response = requests.delete("http://localhost:8080/v1/accounts/2")
        self.validate_error_response(expected, response, 404)
