import requests
import sqlalchemy
import contextlib

from test_base_class import InvestmentDiaryBaseTestClass


class TestMarketSubjects(InvestmentDiaryBaseTestClass):

    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)

    def setUp(self):
        self.clear_db()

    def test_market_subject_creation_success(self):
        payload = {
          "name": "test-subject",
          "infoSources": "No infos",
          "hasDividend": True
        }
        expected = {
            "id": 1,
            "name": "test-subject",
            "infoSources": "No infos",
            "hasDividend": True,
        }
        response = requests.post(self.subjects_url(), json=payload)
        self.validate_object_response(expected, response, 200)

    def test_market_subject_creation_failure_without_key(self):
        payload = {
            "name": "test-subject",
            "infoSources": "No infos"
        }
        expected = [
            "hasDividend : 'hasDividend' field is required"
        ]

        response = requests.post(self.subjects_url(), json=payload)
        self.validate_error_response(expected, response, 400)


    def test_market_subject_creation_failure_with_empty_value(self):
        payload = {
            "name": " ",
            "infoSources": "No infos",
            "hasDividend": True
        }
        expected = [
            "name : 'name' field is required",
            "name : 'name' field should be between 3 and 32 character long"
        ]

        response = requests.post(self.subjects_url(), json=payload)
        self.validate_error_response(expected, response, 400)

    def test_market_subject_creation_failure_with_null_value(self):
        payload = {
            "name": None,
            "infoSources": "",
            "hasDividend": True
        }
        expected = [
            "name : 'name' field is required"
        ]

        response = requests.post(self.subjects_url(), json=payload)
        self.validate_error_response(expected, response, 400)

    def test_market_subject_creation_failure_constraints_validation(self):
        payload = {
            "name": "GG",
            "infoSources": "x" * 256,
            "hasDividend": True
        }
        expected = [
            "name : 'name' field should be between 3 and 32 character long",
            "infoSources : 'infoSources' field can be max 255 characters long"
        ]

        response = requests.post(self.subjects_url(), json=payload)
        self.validate_error_response(expected, response, 400)

    def test_market_subject_delete_success(self):
        payload = {
            "name": "test-test",
            "infoSources": "No infos",
            "hasDividend": True
        }
        requests.post(self.subjects_url(), json=payload)

        expected = {
            "id": 1,
            "deleted": True,
            "kind": "marketSubject"
        }
        response = requests.delete(self.subjects_url("1"))
        self.validate_delete_status_response(expected, response, 200)

    def test_market_subject_delete_failure_not_found(self):
        expected = [
            "Market subject with id: 2 not found"
        ]
        response = requests.delete(self.subjects_url("2"))
        self.validate_error_response(expected, response, 404)

    def test_market_subjects_update_success(self):
        payload = {
            "name": "test-test",
            "infoSources": "No infos",
            "hasDividend": True
        }
        requests.post(self.subjects_url(), json=payload)
        payload = {
            "name": "test-notest",
            "infoSources": "",
            "hasDividend": False
        }
        response = requests.put(self.subjects_url("1"), json=payload)
        expected = {
            "id": 1,
            "name": "test-notest",
            "infoSources": "",
            "hasDividend": False,
        }
        self.validate_object_response(expected, response, 200)

    def test_market_subject_update_part_success(self):
        payload = {
            "name": "test-test",
            "infoSources": "No infos",
            "hasDividend": True
        }
        requests.post(self.subjects_url(), json=payload)
        payload = {
            "name": "test-test",
            "infoSources": "",
            "hasDividend": False
        }
        response = requests.put(self.subjects_url("1"), json=payload)
        expected = {
            "id": 1,
            "name": "test-test",
            "infoSources": "",
            "hasDividend": False,
        }
        self.validate_object_response(expected, response, 200)

    def test_market_subject_update_failure_not_found(self):
        payload = {
            "name": "test-test",
            "infoSources": "",
            "hasDividend": False
        }
        response = requests.put(self.subjects_url("10"), json=payload)
        expected = [
            "Market subject with id: 10 not found"
        ]
        self.validate_error_response(expected, response, 404)

    def test_market_subject_get_subject_successful(self):
        payload = {
            "name": "test-test",
            "infoSources": "",
            "hasDividend": False
        }
        requests.post(self.subjects_url(), json=payload)
        response = requests.get(self.subjects_url("1"))
        expected = {
            "id": 1,
            "name": "test-test",
            "infoSources": "",
            "hasDividend": False,
        }
        self.validate_object_response(expected, response, 200)

    def test_market_subjects_get_subject_not_found(self):
        response = requests.get(self.subjects_url("10"))
        expected = [
            "Market subject with id: 10 not found"
        ]
        self.validate_error_response(expected, response, 404)

    def test_market_subject_get_subject_page_successful(self):
        payload = {
            "name": "test-test",
            "infoSources": "",
            "hasDividend": False
        }
        requests.post(self.subjects_url(), json=payload)
        payload = {
            "name": "test-notest",
            "infoSources": "no info",
            "hasDividend": True
        }
        requests.post(self.subjects_url(), json=payload)
        payload = {
            "page": 0,
            "size": 2,
            "sorted": ["id"]
        }
        response = requests.get(self.subjects_url(), params=payload)
        expected = [
            {
                "id": 1,
                "name": "test-test",
                "infoSources": "",
                "hasDividend": False,
            },
            {
                "id": 2,
                "name": "test-notest",
                "infoSources": "no info",
                "hasDividend": True,
            }
        ]
        self.validate_object_response(expected, response, 200)

    def test_market_subject_get_subject_page_empty(self):
        payload = {
            "page": 0,
            "size": 2,
            "sorted": ["id"]
        }
        response = requests.get(self.subjects_url(), params=payload)
        expected = []
        self.validate_object_response(expected, response, 200)

    def test_market_subject_get_subject_page_not_found(self):
        payload = {
          "page": 10,
          "size": 2,
          "sort": ["id"]
        }
        response = requests.get(self.subjects_url(), params=payload)
        expected = ["There is no page with number 10 for this resource. Max page index with size 2 is 0"]
        self.validate_error_response(expected, response, 404)



