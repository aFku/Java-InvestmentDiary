from test_base_class import InvestmentDiaryBaseTestClass
from datetime import date, datetime
import requests


class TestWallet(InvestmentDiaryBaseTestClass):

    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)

    def setUp(self):
        self.clear_db()
        self.common_date = datetime.now()
        self.common_date = self.common_date.replace(hour=0, minute=0, second=0, microsecond=0)
        self.common_date = self.common_date.strftime("%Y-%m-%dT%H:%M:%S.%f")
        self.common_date = self.common_date[:-3] + "+00:00"
        self.request_params = {
            "startDate": datetime.now().strftime("%Y-%m-%d"),
            "stopDate": datetime.now().strftime("%Y-%m-%d")
        }

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
        operation7 = self.build_operation_payload(volume=5, subjectId=3, accountId=2, operationType="SELL")
        operation8 = self.build_operation_payload(volume=20, operationType="SELL")
        requests.post(self.operations_url(), json=operation1)
        requests.post(self.operations_url(), json=operation2)
        requests.post(self.operations_url(), json=operation3)
        requests.post(self.operations_url(), json=operation4)
        requests.post(self.operations_url(), json=operation5)
        requests.post(self.operations_url(), json=operation6)
        requests.post(self.operations_url(), json=operation7)
        requests.post(self.operations_url(), json=operation8)

    def test_statistics_all_accounts_no_operations(self):
        expected = {
            "dateRange": {
                "startDate": self.common_date,
                "stopDate": self.common_date
            },
            "volumesBought": 0,
            "volumesSold": 0,
            "moneySpent": 0,
            "moneyEarned": 0,
            "uniqueSubjectsOperated": 0
        }

        response = requests.get(self.stats_url(), params=self.request_params)

        self.validate_object_response(payload=expected, response=response, code=200)

    def test_statistics_all_accounts(self):
        self.prepare_objects()
        response = requests.get(self.stats_url(), params=self.request_params)

        expected = {
            "dateRange":{
                "startDate": self.common_date,
                "stopDate": self.common_date
            },
            "volumesBought": 120,
            "volumesSold": 25,
            "moneySpent": float(120 * 10),
            "moneyEarned": float(25 * 10),
            "uniqueSubjectsOperated": 3
        }

        self.validate_object_response(payload=expected, response=response, code=200)

    def test_statistics_by_id(self):
        self.prepare_objects()

        response = requests.get(self.stats_url(1), params=self.request_params)

        expected = {
            "dateRange": {
                "startDate": self.common_date,
                "stopDate": self.common_date
            },
            "volumesBought": 40,
            "volumesSold": 20,
            "moneySpent": float(40 * 10),
            "moneyEarned": float(20 * 10),
            "uniqueSubjectsOperated": 2
        }
        self.validate_object_response(payload=expected, response=response, code=200)

        response = requests.get(self.stats_url(2), params=self.request_params)

        expected = {
            "dateRange": {
                "startDate": self.common_date,
                "stopDate": self.common_date
            },
            "volumesBought": 80,
            "volumesSold": 5,
            "moneySpent": float(80 * 10),
            "moneyEarned": float(5 * 10),
            "uniqueSubjectsOperated": 2
        }
        self.validate_object_response(payload=expected, response=response, code=200)


