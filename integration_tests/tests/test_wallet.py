from test_base_class import InvestmentDiaryBaseTestClass
from datetime import date
import requests


class TestWallet(InvestmentDiaryBaseTestClass):

    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)

    def setUp(self):
        self.clear_db()

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

    def build_account_payload(self, **kwargs):
        template = {
            "accountId": "ABC123",
            "provider": "testProvider"
        }
        for key, value in kwargs.items():
            template[key] = value
        return template

    def build_subject_payload(self, **kwargs):
        template = {
            "name": "subject1",
            "info": "testProvider",
            "hasDividend": True
        }
        for key, value in kwargs.items():
            template[key] = value
        return template

    def prepare_objects(self):
        account1 = self.build_account_payload()
        account2 = self.build_account_payload(accountId="QWE645", provider="t3st")
        subject1 = self.build_subject_payload()
        subject2 = self.build_subject_payload(name="subject2", hasDividend=False)
        subject3 = self.build_subject_payload(name="subject3")
        requests.post(self.accounts_url(), json=account1)
        requests.post(self.accounts_url(), json=account2)
        requests.post(self.subjects_url(), json=subject1)
        requests.post(self.subjects_url(), json=subject2)
        requests.post(self.subjects_url(), json=subject3)
        operation1 = self.build_operation_payload()
        operation2 = self.build_operation_payload(subjectId=2)
        operation3 = self.build_operation_payload(volume=20)
        operation4 = self.build_operation_payload(volume=50, subjectId=2, accountId=2)
        operation5 = self.build_operation_payload(subjectId=3, accountId=2)
        operation6 = self.build_operation_payload(volume=20, subjectId=3, accountId=2)
        requests.post(self.operations_url(), json=operation1)
        requests.post(self.operations_url(), json=operation2)
        requests.post(self.operations_url(), json=operation3)
        requests.post(self.operations_url(), json=operation4)
        requests.post(self.operations_url(), json=operation5)
        requests.post(self.operations_url(), json=operation6)

    def test_wallet_from_account(self):
        self.prepare_objects()

        pageable = {
            "page": 0,
            "size": 5,
            "sorted": ["id"]
        }

        expected = [
            {
                "name": "subject1",
                "volume": 30,
                "subjectId": 1
            },
            {
                "name": "subject2",
                "volume": 10,
                "subjectId": 2
            }
        ]

        response = requests.get(self.wallet_url(1), params=pageable)

        self.validate_object_response(expected, response, code=200)

    def test_wallet_from_all_accounts(self):
        self.prepare_objects()
        pageable = {
            "page": 0,
            "size": 5,
            "sorted": ["id"]
        }

        expected = [
            {
                "name": "subject1",
                "volume": 30,
                "subjectId": 1,
                "accountId": 1
            },
            {
                "name": "subject2",
                "volume": 10,
                "subjectId": 2,
                "accountId": 1
            },
            {
                "name": "subject2",
                "volume": 50,
                "subjectId": 2,
                "accountId": 2
            },
            {
                "name": "subject3",
                "volume": 30,
                "subjectId": 3,
                "accountId": 2
            }
        ]

        response = requests.get(self.wallet_url(), params=pageable)

        self.validate_object_response(expected, response, code=200)
