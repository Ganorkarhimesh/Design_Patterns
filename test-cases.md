# Manual test cases

| ID | Scenario | Steps | Expected result |
|---|---|---|---|
| TC-01 | Register patient | Enter valid name, age, gender, phone and condition; click Register / Update | Patient appears in the table and persists after restart |
| TC-02 | Validate age | Enter non-numeric or age above 130 | Validation message; no record saved |
| TC-03 | Update patient | Select a patient, change phone, click Register / Update | Existing row updates |
| TC-04 | Delete patient | Select a patient and confirm deletion | Patient and linked records are deleted |
| TC-05 | Schedule appointment | Select patient, valid ISO date, HH:MM time, doctor and reason | Appointment appears in list |
| TC-06 | Generate bill | Enter valid patient, date, description and non-negative amount | Bill appears with payment status |
| TC-07 | Treatment record | Enter patient, date, diagnosis, treatment and notes | Treatment record appears |
| TC-08 | Foreign key | Try adding an appointment without selecting a valid patient | Validation prevents submission |
| TC-09 | Persistence | Close and reopen application | Previously saved records remain |
| TC-10 | Empty required fields | Leave a required field blank | Error message; no record saved |
